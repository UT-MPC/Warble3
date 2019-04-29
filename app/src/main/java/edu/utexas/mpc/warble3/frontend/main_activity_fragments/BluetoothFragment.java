package edu.utexas.mpc.warble3.frontend.main_activity_fragments;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.service.BTScanService;
import edu.utexas.mpc.warble3.util.Beacon;

import static edu.utexas.mpc.warble3.service.BTScanService.DISABLE_SCAN;
import static edu.utexas.mpc.warble3.service.BTScanService.ENABLE_SCAN;
import static edu.utexas.mpc.warble3.util.Constants.REQUEST_ENABLE_BT;
import static edu.utexas.mpc.warble3.util.Constants.SNAPSHOT_LOOKBACK_MS;

public class BluetoothFragment extends Fragment {
    private static final String TAG = "BluetoothFragment";

    private BTScanService mScanService;
    private Switch mScanSwitch;
    private boolean mBound = false;
    private BluetoothAdapter mBTAdapter;
    private Button mSnapBtn;

    public static BluetoothFragment getNewInstance() {
        return new BluetoothFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ble, null);
        mScanSwitch = view.findViewById(R.id.scan_switch);
        mScanSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!mBound) {
                    return;
                }
                checkBTPermission();
                mScanService.scan(isChecked ? ENABLE_SCAN : DISABLE_SCAN);
            }
        });
        mSnapBtn = view.findViewById(R.id.snap_btn);
        mSnapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeSnap();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        if (!mBound) {
            // Bind to the service
            getActivity().bindService(new Intent(getActivity(), BTScanService.class), mConnection,
                    Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        if (mBound) {
            getActivity().unbindService(mConnection);
            mBound = false;
        }
        super.onDestroy();
    }

    private void checkBTPermission() {
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBTAdapter == null) {
            throw new RuntimeException("Device does not support Bluetooth.");
        }
        if (!mBTAdapter.isEnabled() || ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            Log.d(TAG, "Requesting permission.");
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ENABLE_BT);
        }
    }

    private void takeSnap() {
        if (!mBound) {
            Toast.makeText(getActivity(), "Scan service is not bound.",
                    Toast.LENGTH_LONG).show();
            return;
        }
//        List<Beacon> res =
//                mScanService.mBeaconQueue.stream().filter(b -> b.getDate().before(start)).collect(
//                        Collectors.toList());
        List<Beacon> res = new ArrayList<>();
        Date start = new Date(System.currentTimeMillis() - SNAPSHOT_LOOKBACK_MS);
        for (Beacon b : mScanService.mBeaconQueue) {
            if (b.getDate().after(start)) {
                res.add(b);
            }
        }
        Log.d(TAG, "res size: " + res.size());
        //TODO: res is the beacons recieved in the past 10 seconds.
    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override public void onServiceConnected(ComponentName className, IBinder service) {
            BTScanService.LocalBinder binder = (BTScanService.LocalBinder) service;
            mScanService = binder.getService();
            mBound = true;
        }

        @Override public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}
