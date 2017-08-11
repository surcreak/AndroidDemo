package com.example.musicpicker;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by gaoliang on 2017/7/14.
 * For Music pick.
 */

public class MusicPickActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String[] AUDIO_CURSOR_COLS = new String[] {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.TITLE_KEY,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.TRACK
    };

    private Button okBtn;
    private Button cancelBtn;
    private RecyclerView recyclerView;
    private RecycleCursorAdapter<AudioViewHolder> mAdapter;
    private Uri selectUri;
    private MediaPlayer mMediaPlayer;
    private long curSelectID = -1;
    private boolean playError = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_pick);
        setupView();
        getLoaderManager().initLoader(0, null, this);
        mAdapter = new AudioRecyclerAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setupView(){
        okBtn = (Button) findViewById(R.id.ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMeidaPlay();
                setResult(RESULT_OK, new Intent().setData(selectUri));
                finish();
            }
        });
        cancelBtn = (Button) findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMeidaPlay();
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        StringBuilder where = new StringBuilder();
        where.append(MediaStore.Audio.Media.TITLE + " != ''");
        return new CursorLoader(this, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                AUDIO_CURSOR_COLS, where.toString(), null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.changeCursor(data);
    }

    void stopMeidaPlay() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.changeCursor(null);
    }

    private class AudioViewHolder extends RecyclerView.ViewHolder{
        public TextView nameView;
        public TextView durationView;
        public TextView artistView;
        public TextView albunView;
        public ImageView playView;

        public AudioViewHolder(View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.name);
            durationView = (TextView) itemView.findViewById(R.id.duration);
            artistView = (TextView) itemView.findViewById(R.id.artist);
            albunView = (TextView) itemView.findViewById(R.id.album);
            playView = (ImageView) itemView.findViewById(R.id.play);
        }
    }

    private class AudioRecyclerAdapter extends RecycleCursorAdapter<AudioViewHolder> implements MediaPlayer.OnCompletionListener {

        private Context mContext;
        private LayoutInflater inflater;
        private int oldPosition = -1;

        public AudioRecyclerAdapter(Context context){
            super(context, null);
            mContext = context;
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public AudioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AudioViewHolder(inflater.inflate(R.layout.audio_list_item, null));
        }

        @Override
        public void onBindViewHolder(final AudioViewHolder holder, Cursor cursor) {
            holder.nameView.setText(mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            //String duration = mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            //holder.durationView.setText(DateUtils.formatDateTime(mContext, Long.valueOf(duration), DateUtils.FORMAT_SHOW_TIME));
            holder.artistView.setText(mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            holder.albunView.setText(mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
            if (holder.getItemId() != curSelectID) {
                holder.playView.setVisibility(View.INVISIBLE);
            }else {
                holder.playView.setVisibility(View.VISIBLE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playError = false;
                    stopMeidaPlay();
                    mMediaPlayer = new MediaPlayer();
                    curSelectID = holder.getItemId();
                    notifyDataSetChanged();
                    try {
                        selectUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, holder.getItemId());
                        mMediaPlayer.setDataSource(mContext, selectUri);
                        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
                        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                            @Override
                            public boolean onError(MediaPlayer mp, int what, int extra) {
                                curSelectID = -1;
                                selectUri = null;
                                notifyDataSetChanged();
                                playError = true;
                                return false;
                            }
                        });
                        if (playError) {
                            stopMeidaPlay();
                            return;
                        }
                        mMediaPlayer.prepare();
                        mMediaPlayer.start();
                    } catch (IOException e) {
                        curSelectID = -1;
                        selectUri = null;
                        notifyDataSetChanged();
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void onCompletion(MediaPlayer mp) {
            if (mp == mMediaPlayer) {
                stopMeidaPlay();
            }
        }
    }
}
