package edu.utexas.mpc.warble3.frontend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import edu.utexas.mpc.warble3.R;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_setup:
                    mTextMessage.setText(R.string.fragmentLabelSetup_mainPage);
                    return true;
                case R.id.navigation_manual:
                    mTextMessage.setText(R.string.fragmentLabelManual_mainPage);
                    return true;
                case R.id.navigation_control:
                    mTextMessage.setText(R.string.fragmentLabelControl_mainPage);
                    return true;
                case R.id.navigation_4:
                    mTextMessage.setText(R.string.fragmentLabel4_mainPage);
                    return true;
                case R.id.navigation_settings:
                    mTextMessage.setText(R.string.fragmentLabelSettings_mainPage);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
