package edu.utexas.mpc.warble3.frontend.main_activity_fragments;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.frontend.MainActivity;
import edu.utexas.mpc.warble3.service.BTScanService;

import static edu.utexas.mpc.warble3.service.BTScanService.DISABLE_SCAN;
import static edu.utexas.mpc.warble3.service.BTScanService.ENABLE_SCAN;
import static edu.utexas.mpc.warble3.util.Constants.REQUEST_ENABLE_BT;

public class BluetoothFragment extends Fragment {
    private static final String TAG = "BluetoothFragment";

    private BTScanService mScanService;
    private Switch mScanSwitch;
    private boolean mBound = false;
    private BluetoothAdapter mBTAdapter;

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
        return view;
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
}
