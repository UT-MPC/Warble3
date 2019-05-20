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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.service.BTScanService;
import edu.utexas.mpc.warble3.util.Beacon;
import edu.utexas.mpc.warble3.warble.selector.CtxSelector.Agent.MobileAgent;
import edu.utexas.mpc.warble3.warble.selector.CtxSelector.Context.BinContext;
import edu.utexas.mpc.warble3.warble.selector.CtxSelector.Context.CategoricalContext;
import edu.utexas.mpc.warble3.warble.selector.CtxSelector.Context.DynamicContext;
import edu.utexas.mpc.warble3.warble.selector.CtxSelector.Context.Point2D;
import edu.utexas.mpc.warble3.warble.selector.CtxSelector.Context.TimeContext;
import edu.utexas.mpc.warble3.warble.selector.CtxSelector.ThingAction.LightOnOffAction;
import edu.utexas.mpc.warble3.warble.selector.CtxSelector.ThingAction.ThingAction;
import edu.utexas.mpc.warble3.warble.selector.CtxSelector.ThingAction.VirtualLight;
import edu.utexas.mpc.warble3.warble.thing.component.Light;
import edu.utexas.mpc.warble3.warble.thing.component.LightState;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;
import edu.utexas.mpc.warble3.warble.thing.component.ThingState;
import edu.utexas.mpc.warble3.warble.vendor.PhilipsHue.component.PhilipsHueLight;

import static edu.utexas.mpc.warble3.service.BTScanService.DISABLE_SCAN;
import static edu.utexas.mpc.warble3.service.BTScanService.ENABLE_SCAN;
import static edu.utexas.mpc.warble3.util.Constants.REQUEST_ENABLE_BT;
import static edu.utexas.mpc.warble3.util.Constants.SNAPSHOT_LOOKBACK_MS;

public class BluetoothFragment extends Fragment {
    private static final String TAG = "BluetoothFragment";
    private SimpleDateFormat defaultFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ArrayList<Thing> IoTThings;
    private MobileAgent myAgent;
    double catHyper1 = 0.4, catHyper2 = 0.05, catHyper3 = 0.05;
    double binHyper1 = 0.4, binHyper2 = 0.05, binHyper3 = 0.05;

    long totalResponseTime = 0;
    int responseNum = 0;
    long totalFeedbackTime = 0;
    int feedbackNum = 0;
//    private static DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

    private BTScanService mScanService;
    private Switch mScanSwitch;
    private boolean mBound = false;
    private BluetoothAdapter mBTAdapter;
    private Button mSnapBtn;
    private Button mTestBtn;
    private ArrayList<Long> responseTime;
    private ArrayList<Long> feedbackTime;
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
        mTestBtn = view.findViewById(R.id.test_btn);
        mTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runTest(1);
            }
        });
        return view;
    }

    private int calToSec(Calendar c){
        long now = c.getTimeInMillis();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long passed = now - c.getTimeInMillis();
        long secondsPassed = passed / 1000;
        return (int)secondsPassed;
    }
    private void createLights(){
        IoTThings = new ArrayList<>();
        for (int i = 0; i <10; i++){
            IoTThings.add(new VirtualLight());
            IoTThings.get(i).setUuid("riotTest" + i);
        }
    }

    int handleRequest(LightOnOffAction truth, DynamicContext ctx, MobileAgent agent){
        int noBefore = 0;
        long startTime;
        long endTime;
        while (true){
            startTime = System.nanoTime();
            ThingAction res = myAgent.getConfiguration(ctx, Light.class, null);
            endTime = System.nanoTime();
            responseTime.add((endTime - startTime) / 1000000);
            res.doAction();
            if (res.getActions().get(0).isSame(truth)){
                startTime = System.nanoTime();
                myAgent.getPraised();
                endTime = System.nanoTime();
                feedbackTime.add((endTime - startTime) / 1000000);
                break;
            }else {
                startTime = System.nanoTime();
                myAgent.getPunished();
                endTime = System.nanoTime();
                feedbackTime.add((endTime - startTime) / 1000000);
                noBefore += 1;
            }
        }
        return noBefore;
    }

    private void runTest(int opt){
        String ret = "";
        int lightIdx = 0;
        createLights();
        Log.i("riotTest","Create lights!!");
        switch (opt){
            //categorical
            case 0:
                myAgent = new MobileAgent(IoTThings, new DynamicContext.Alpha(new ArrayList<Double>(Arrays.asList(new Double[]{catHyper1, catHyper3, catHyper2}))));
                break;
            case 1:
                Double[] newAlpha = new Double[37];
                newAlpha[0] = binHyper3;
                for (int i = 0; i < 18; i++){
                    newAlpha[i + 1] = binHyper1;
                }
                for (int i = 0; i < 18; i++){
                    newAlpha[i + 19] = binHyper2;
                }
                myAgent = new MobileAgent(IoTThings, new DynamicContext.Alpha(new ArrayList<Double>(Arrays.asList(newAlpha))));
                break;
            default:
                myAgent = new MobileAgent(IoTThings, new DynamicContext.Alpha(new ArrayList<Double>(Arrays.asList(new Double[]{catHyper1, catHyper3, catHyper2}))));
                break;
        }
        int actorSensor = 0;
        int previousActorSensor = 0;
        responseTime = new ArrayList<>();
        feedbackTime = new ArrayList<>();
        try {
            InputStream inputStream = new FileInputStream(new File("/data/data/edu.utexas.mpc.warble3/ann.txt"));
            BufferedWriter output = new BufferedWriter(new FileWriter("/data/data/edu.utexas.mpc.warble3/timing.csv"));
            output.write("response,feedback\n");
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String lineStr = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ( (lineStr = bufferedReader.readLine()) != null ) {
                    String[] arrStr = lineStr.split(" ", 3);
                    String[] time = (arrStr[0] + " " + arrStr[1]).split("\\.");
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(defaultFormatter.parse(time[0]));
                    int now = calToSec(cal);
                    String[] fields = arrStr[2].split(" ");
                    String sensor = fields[0].split("[0-9]")[0];
                    int num = Integer.parseInt(fields[0].replaceAll("[^0-9]",""));
                    String state = fields[1];
//                    Log.i("riotTest", "!!!!!!!!!!!!!!!" + now + " " + sensor);

                    if (sensor.equals("M") || sensor.equals("MA")){
                        if (state.equals("ON") ) {
//                        actor.jumpTo(pixels.get(2 * x), pixels.get( 2 * x + 1), now.toLocalTime());
                            actorSensor = num;
                        }else{
                            previousActorSensor = num;
                        }
                    }

                    if (sensor.equals("L")) {
                        Light mx = (Light) IoTThings.get(lightIdx + num - 1);
                        LightState nowState = (LightState) mx.getState();
                        LightOnOffAction newAction = null;
                        if (state.equals("ON")) {
                            if (nowState.getActive() == ThingState.ACTIVE_STATE.ON) {
                                continue;
                            }
                            newAction = new LightOnOffAction(mx, true);
                        }
                        if (state.equals("OFF")) {
                            if (nowState.getActive() == ThingState.ACTIVE_STATE.OFF) {
                                continue;
                            }
                            newAction = new LightOnOffAction(mx, false);
                        }
                        if (newAction == null) {
                            continue;
                        }
                        //Build context
                        DynamicContext nowCtx = null;
                        switch (opt){
                            case 0:
                                nowCtx = new DynamicContext();
                                ArrayList<Integer> newCate = new ArrayList<>();
                                newCate.add(actorSensor);
                                nowCtx.addContext(new CategoricalContext(newCate));
                                nowCtx.addContext(new TimeContext(now));
                                newCate = new ArrayList<>();
                                newCate.add(previousActorSensor);
                                nowCtx.addContext(new CategoricalContext(newCate));
                                break;
                            case 1:
                                nowCtx = new DynamicContext();
                                nowCtx.addContext(new TimeContext(now));

                                for (int i = 0; i < 18; i++){
                                    if (i == actorSensor - 1){
                                        nowCtx.addContext(new BinContext(1));
                                    }else{
                                        nowCtx.addContext(new BinContext(0));
                                    }
                                }
                                for (int i = 0; i < 18; i++){
                                    if (i == previousActorSensor - 1){
                                        nowCtx.addContext(new BinContext(1));
                                    }else{
                                        nowCtx.addContext(new BinContext(0));
                                    }
                                }
                                break;
                        }

                        int no = handleRequest(newAction,nowCtx, myAgent);
                        Log.i("riotTest","Finish request handling!!" + no);
                    }
                }
                inputStream.close();
                for (int i = 0; i < responseTime.size(); i++){
                    output.write(responseTime.get(i)+","+feedbackTime.get(i)+"\n");
                }
                output.close();
                Log.i("riotTest", "!!!finished");
//                Log.i("riotTest","!!!!Average Response Time: " + (totalResponseTime/responseNum));
            }
        }
        catch (ParseException e){
            Log.e("riotTest", "Parse: " + e.toString());
        }
        catch (FileNotFoundException e) {
            Log.e("riotTest", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("riotTest", "Can not read file: " + e.toString());
        }
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
