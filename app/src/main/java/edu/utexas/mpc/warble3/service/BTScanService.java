package edu.utexas.mpc.warble3.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.utexas.mpc.warble3.util.Beacon;

import static edu.utexas.mpc.warble3.util.Beacon.verifyBeacon;
import static edu.utexas.mpc.warble3.util.Constants.BEACON_QUEUE_SIZE;
import static edu.utexas.mpc.warble3.util.Constants.OPERATION_FAIL;
import static edu.utexas.mpc.warble3.util.Constants.OPERATION_SUCCEED;
import static edu.utexas.mpc.warble3.util.Constants.REPEAT_BEACON_FILTER_MS;
import static edu.utexas.mpc.warble3.util.Constants.SCAN_INTERVAL_MS;
import static edu.utexas.mpc.warble3.util.Constants.SCAN_PERIOD_MS;

public class BTScanService extends Service {
    private static final String TAG = "BTScanService";

    private static final int BLEND_OFFSET = 5;

    public static final int ENABLE_SCAN = 0;
    public static final int DISABLE_SCAN = 1;

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();

    private boolean mServiceEnabled = false;

    private boolean mScanning = false;

    private Handler mHandler = new Handler();

    private BluetoothAdapter mBTAdapter;

    private BluetoothLeScanner mBTLeScanner;

    private List<ScanFilter> mScanFilters;

    private ScanSettings mScanSettings;

    private ScanCallback mScanCallback;

    private CircularFifoQueue<Beacon> mBeaconQueue;

    private Map<String, Long> mTimeFilter;

    private Runnable startRunnable = new Runnable() {
        @Override
        public void run() {
            BTScanService.this.startScan();
        }
    };

    private Runnable stopRunnable = new Runnable() {
        @Override
        public void run() {
            BTScanService.this.stopScan();
        }
    };

    PowerManager mPowerManager;
    PowerManager.WakeLock mWakeLock;

    public class LocalBinder extends Binder {
        public BTScanService getService() {
            return BTScanService.this;
        }
    }

    public BTScanService() {
        super();
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
        ScanSettings.Builder scanSettingsBuilder = new ScanSettings.Builder();
        scanSettingsBuilder.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY);    // scan mode
        this.mScanSettings = scanSettingsBuilder.build();
        this.mScanFilters = new ArrayList<>();
        this.mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        this.mWakeLock = mPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyApp::MyWakelockTag");
        this.mBeaconQueue = new CircularFifoQueue<>(BEACON_QUEUE_SIZE);
        this.mTimeFilter = new HashMap<>();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        mWakeLock.acquire();
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        if (mScanning) {
            mScanning = false;
        }
        if (mWakeLock.isHeld()) {
            mWakeLock.release();
        }
        return true;
    }

    public int scan(int command) {
        switch (command) {
            case ENABLE_SCAN:
                Log.d(TAG, "Enable Scan");
                startScan();
                mServiceEnabled = true;
                break;
            case DISABLE_SCAN:
                if (mServiceEnabled) {
                    Log.d(TAG, "Disable Scan");
                    mServiceEnabled = false;
                    stopScan();
                }
                break;
            default:
                Log.e(TAG, "receives unknown command (" + command + ").");
                return OPERATION_FAIL;
        }
        return OPERATION_SUCCEED;
    }

    private int startScan() {
        Log.d(TAG, "Start scanning.");
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();
        mScanCallback = new BTScanCallback();
        mBTLeScanner = mBTAdapter.getBluetoothLeScanner();
        mBTLeScanner.startScan(mScanFilters, mScanSettings, mScanCallback);
        mScanning = true;
        mHandler.removeCallbacks(stopRunnable);
        mHandler.postDelayed(stopRunnable, SCAN_PERIOD_MS);
        return OPERATION_SUCCEED;
    }

    private int stopScan() {
        Log.d(TAG, "Stop scanning.");
        if (mScanning && mBTAdapter != null && mBTAdapter.isEnabled() && mBTLeScanner != null) {
            mBTLeScanner.stopScan(mScanCallback);
            finishScan();
        }
        mScanCallback = null;
        mScanning = false;
        if (mServiceEnabled) {
            mHandler.removeCallbacks(startRunnable);
            mHandler.postDelayed(startRunnable, SCAN_INTERVAL_MS - SCAN_PERIOD_MS);
        }
        return OPERATION_SUCCEED;
    }

    private void finishScan() {
        Log.d(TAG, "Queue size: " + mBeaconQueue.size());
    }

    private class BTScanCallback extends ScanCallback {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            saveBeacon(result);
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            for (ScanResult result : results) {
                saveBeacon(result);
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            Log.e(TAG, "BLE Scan Failed with code " + errorCode);
        }

        private void saveBeacon(ScanResult result) {
            if (verifyBeacon(result)) {
                Beacon bcn = new Beacon();
                String addr = result.getDevice().getAddress();
                Long curTime = System.currentTimeMillis();
                if (mTimeFilter.containsKey(addr) && mTimeFilter
                        .get(addr) > curTime - REPEAT_BEACON_FILTER_MS) {
                    return;
                }
                mTimeFilter.put(addr, curTime);
                bcn.setDate(new Date(System.currentTimeMillis()));
                bcn.setDeviceAddress(addr);
                bcn.setRssi(result.getRssi());
                bcn.setPayload(result.getScanRecord().getBytes());
                mBeaconQueue.offer(bcn);
            }
        }
    }
}
