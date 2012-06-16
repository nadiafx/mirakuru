package com.pandalan.mirakuru;

import android.app.Activity;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import android.os.Bundle;

public class Mirakuru extends Activity {
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void onStartButton(View v){
	Toast.makeText(this, "START BUTTON PRESSED", Toast.LENGTH_SHORT).show();
    }
    
    public void onAboutButton(View v){
	Toast.makeText(this, "MIKURU was made by ALAN SIEN WEI HSHIEH and ALAN CHOU", Toast.LENGTH_SHORT).show();
    }

}