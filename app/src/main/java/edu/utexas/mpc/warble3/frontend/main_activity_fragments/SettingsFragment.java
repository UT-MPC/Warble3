/*
 * MIT License
 *
 * Copyright (c) 2018 Yosef Saputra
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package edu.utexas.mpc.warble3.frontend.main_activity_fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import edu.utexas.mpc.warble3.R;
import edu.utexas.mpc.warble3.database.AppDatabase;
import edu.utexas.mpc.warble3.frontend.WelcomeActivity;
import edu.utexas.mpc.warble3.util.Logging;
import edu.utexas.mpc.warble3.util.SharedPreferenceHandler;

public class SettingsFragment extends Fragment {
    private static String TAG = "SettingsFragment";

    public static SettingsFragment getNewInstance() {
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, null);

        final TextView logout_settingsFragment_textView = view.findViewById(R.id.logout_settingsFragment_textView);
        logout_settingsFragment_textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = SharedPreferenceHandler.getSharedPrefsEditorCurrentUserSettings(getActivity());
                editor.putString(SharedPreferenceHandler.SHARED_PREFS_USERNAME, null);
                editor.apply();

                Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        TextView dbPrint_settingsFragment_textView = view.findViewById(R.id.dbPrint_settingsFragment_textView);
        dbPrint_settingsFragment_textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                List<String> databaseStrings = Arrays.asList(AppDatabase.getDatabase().toString().split("\n"));
                for (String string : databaseStrings) {
                    if (Logging.INFO) Log.i(TAG, string);
                }
            }
        });

        TextView dbReset_settingsFragment_textView = view.findViewById(R.id.dbReset_settingsFragment_textView);
        dbReset_settingsFragment_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase.getDatabase().deleteAllUsers();
                AppDatabase.getDatabase().deleteAllThings();
                AppDatabase.getDatabase().deleteAllConnections();
                AppDatabase.getDatabase().deleteAllThingAccessCredentials();

                logout_settingsFragment_textView.performClick();
                logout_settingsFragment_textView.invalidate();
            }
        });
        return view;
    }
}
