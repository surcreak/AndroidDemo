package com.surcreak.androiddemo;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.testretrofit.TestRetrofitFragment;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.surcreak.androiddemo.prefrence.TestPreferenceFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MainFragment.OnListItemClickListerner{

    TextView tempTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempTest = (TextView) findViewById(R.id.temp_test);
        tempTest();

        if(savedInstanceState == null) {
            MainFragment fragment = new MainFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_content, fragment)
                    .commit();
        }

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */}
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();
    }

    @Override
    public void OnListItemClick(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new TestPreferenceFragment();
                break;
            case 1:
                fragment = new TempTestFragment();
                break;
            case 2:
                fragment = new TestHorizontalGridViewFragment();
                break;
            case 3:
                fragment = new TestRetrofitFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_content, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void tempTest(){
        Calendar calendar = Calendar.getInstance();
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        SimpleDateFormat  simpleDateFormat = new SimpleDateFormat("DD MM YYYY");
        tempTest.setText(simpleDateFormat.format(calendar.getTime()));
    }

}
