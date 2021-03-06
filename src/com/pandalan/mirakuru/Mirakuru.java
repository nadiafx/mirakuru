package com.pandalan.mirakuru;

import android.app.Activity;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.content.Intent;

public class Mirakuru extends Activity {
    protected final String TAG = Mirakuru.class.getSimpleName();

    private MediaPlayer mMediaPlayer = null;
    private MediaPlayer mBgMusic = null;
    private int mBgMusicPosMs = 0;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaPlayer = MediaPlayer.create(this , R.raw.button_pressed);
        mBgMusic = MediaPlayer.create(this , R.raw.title_bg);
        mBgMusic.setLooping(true);
        mBgMusic.seekTo(mBgMusicPosMs);
        mBgMusic.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBgMusic.pause();
        mBgMusicPosMs = mBgMusic.getCurrentPosition();
        mBgMusic.stop();
        mBgMusic.release();
        mMediaPlayer.release();
    }

    public void onStartButton(View v){
        mMediaPlayer.start();
        Intent conversationXIntent = new Intent(Mirakuru.this, ConversationX.class);
        startActivity(conversationXIntent);
    }

    public void onAboutButton(View v){
        Toast.makeText(this, "MIKURU was made by ALAN SIEN WEI HSHIEH and ALAN CHOU", Toast.LENGTH_SHORT).show();
        mMediaPlayer.start();
    }

}