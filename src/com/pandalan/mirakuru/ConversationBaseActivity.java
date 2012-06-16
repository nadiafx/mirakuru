package com.pandalan.mirakuru;

import android.app.Activity;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import android.view.MotionEvent;
import android.os.Bundle;
import android.media.MediaPlayer;
import java.util.ArrayList;
import java.util.Collections;


public class ConversationBaseActivity extends Activity implements View.OnTouchListener {
    
    private MediaPlayer mMediaPlayer;
    private ArrayList<String> mStringLoop;
    private int mStringLoopIndex = 0;
    private int mCurrentStringLoopId = 0;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
	mMediaPlayer = MediaPlayer.create(this , R.raw.button_pressed);
	
	//set default loop
	mStringLoop = new ArrayList<String>();
	mStringLoop.add("<NO CONTENT SET>");
	
	TextView textBox = (TextView)findViewById(R.id.conversation_text);
	textBox.setOnTouchListener(this); 
    }
    
    //responds when the textview is clicked
    public void onTextViewClicked(){
	TextView text = (TextView)findViewById(R.id.conversation_text);
	mMediaPlayer.start();//play sound on click
	if (mStringLoopIndex < mStringLoop.size()){
	    text.setText(mStringLoop.get(getStringLoopIndex()));
	    mStringLoopIndex++;
	}
    }
    
    @Override 
    public boolean onTouch(View v, MotionEvent event) {
	onTextViewClicked();
	return false; 
    }
    
    
    //Returns the current string index
    private int getStringLoopIndex(){
	if (mStringLoopIndex >= mStringLoop.size()){//TODO: this doesnt work yet
	    mStringLoopIndex = 0;
	}
	return mStringLoopIndex;
    }
    
    //get current string loop
    public ArrayList<String> getStringLoop(){
	return mStringLoop;
    }
    
    //setStringLoop
    /**
     *stringLoopToSet is the array that we want to iterate through
     *currentStringLoopId is a unique identifier so that classes that extent can identify what arraylist is being displayed quickly
     */
    public void setStringLoop(ArrayList<String> stringLoopToSet, int currentStringLoopId){
	mCurrentStringLoopId = currentStringLoopId;
	mStringLoop = stringLoopToSet;
    }
    
    //setStringLoop, the ideal version, since classes that inherit from this can more easily create string arrays to pass in
    /**
     *stringLoopToSet is the array that we want to iterate through
     *currentStringLoopId is a unique identifier so that classes that extent can identify what arraylist is being displayed quickly
     */
    public void setStringLoop(String[] stringArrayToSetAsStringArray, int currentStringLoopId, boolean appendContent){
	mCurrentStringLoopId = currentStringLoopId;
	if (!appendContent){
	    mStringLoop.clear();
	}
	Collections.addAll(mStringLoop, stringArrayToSetAsStringArray);
    }
    
    //returns current loop Id
    public int getCurrentStringLoopId(){
	return mCurrentStringLoopId;
    }
    
    //provide the resource id of a new audio file to play when the text view is clicked
    public void setTextViewTappedAudioResource(int resourceId){
	mMediaPlayer = MediaPlayer.create(this , resourceId);
    }

}