package edu.utexas.mpc.warble3.frontend.main_activity_fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.frontend.WelcomeActivity;

public class SettingsFragment extends Fragment {
    public static SettingsFragment getNewInstance() {
        SettingsFragment settingsFragment = new SettingsFragment();
        return settingsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, null);

        TextView logout_settingsFragment_textView = view.findViewById(R.id.logout_settingsFragment_textView);
        logout_settingsFragment_textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(WelcomeActivity.SHARED_PREFS_CURRENT_USER_SETTINGS, Context.MODE_PRIVATE).edit();
                editor.putString(WelcomeActivity.SHARED_PREFS_USERNAME, null);
                editor.apply();

                Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        return view;
    }
}
