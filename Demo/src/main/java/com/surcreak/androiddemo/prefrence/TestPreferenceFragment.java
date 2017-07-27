package com.surcreak.androiddemo.prefrence;


import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.surcreak.androiddemo.R;

/**
 * Created by gaoliang on 2017/6/5.
 */

public class TestPreferenceFragment extends PreferenceFragmentCompat {
    final private static String KEY_SEND_BROADCASE_PREFERENCE = "send_broadcast";
    Preference sendBrodcasePreferece;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.test_preference);
        sendBrodcasePreferece = getPreferenceScreen().findPreference(KEY_SEND_BROADCASE_PREFERENCE);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference == sendBrodcasePreferece) {
//            Intent intent = new Intent(Settings.ACTION_SHOW_INPUT_METHOD_PICKER);
//            mImeSwitchPendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
        }
        return super.onPreferenceTreeClick(preference);
    }

}
