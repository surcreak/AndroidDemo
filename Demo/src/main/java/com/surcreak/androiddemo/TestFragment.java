package com.surcreak.androiddemo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.designsample.DesignSampleActivity;
import com.example.musicpicker.MusicPickActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoliang on 2017/6/19.
 */

public class TestFragment extends Fragment {

    private static final int REQUEST_CODE_MUSIC_PICK = 1001;

    Button launchMusicPlayerBtn;
    Button externalMedia;
    Button tempTestBtn;
    Button designSampleBtn;
    TextView showView;
    Context mContext;
    AudioManager mAudioManager;

    private static final String[] MEDIA_COLUMNS = new String[] {
            MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,
            "\"" + MediaStore.Audio.Media.EXTERNAL_CONTENT_URI + "\"",
            MediaStore.Audio.Media.TITLE_KEY
    };
    private final List<String> mFilterColumns = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_temp_test, container, false);
        setupView(rootView);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        return rootView;
    }

    private void setupView(View v){
        launchMusicPlayerBtn = (Button) v.findViewById(R.id.launcher_music_play);
        launchMusicPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMusicPlayer();
            }
        });
        externalMedia = (Button) v.findViewById(R.id.external_meida);
        externalMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testExternalMedia();
            }
        });
        tempTestBtn = (Button) v.findViewById(R.id.temp_test);
        tempTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //testBattery();
                //testRingerMode();
                testMusicPick();
            }
        });
        designSampleBtn = (Button) v.findViewById(R.id.design_sample);
        designSampleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDesignSample();
            }
        });
        showView = (TextView) v.findViewById(R.id.show);
    }

    private void openDesignSample() {
        startActivity(new Intent(getActivity(), DesignSampleActivity.class));
    }

    private void testMusicPick(){
        Intent i = new Intent(getActivity(), MusicPickActivity.class);
        startActivityForResult(i, REQUEST_CODE_MUSIC_PICK);
    }

    private void testExternalMedia(){
        Cursor cursor = getActivity().getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        Log.d("gaol", "cursor.getcurent()"+cursor.getCount());
    }

    private void testRingerMode() {
        showView.setText("ringerMode="+mAudioManager.getRingerMode());
    }

    private void testBattery(){
        showView.setText("battery:"+getBattery());
    }

    private float getBattery(){
        Intent intent = getContext().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        if(intent == null){
            return 0f;
        }
        int level = intent.getIntExtra("level", 0);
        int scale = intent.getIntExtra("scale", 100);
        Toast.makeText(getContext(), R.string.app_name, Toast.LENGTH_LONG).show();
        return ((float)level) / scale;
    }


    private static String constructBooleanTrueWhereClause(List<String> columns) {
        if (columns == null) return null;
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = columns.size() - 1; i >= 0; i--) {
            sb.append(columns.get(i)).append("=1 or ");
        }
        if (columns.size() > 0) {
            // Remove last ' or '
            sb.setLength(sb.length() - 4);
        }

        sb.append(")");

        return sb.toString();
    }

    public void launchMusicPlayer() {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            Log.d("gaol", "onActivityResult: data="+data);
        }
    }
}
