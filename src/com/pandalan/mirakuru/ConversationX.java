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
import android.media.MediaPlayer;
import java.util.ArrayList;

public class ConversationX extends ConversationBaseActivity {
    
    private static final int STRING_LOOP_INTRO_ID = 1;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //set background
        ImageView background = (ImageView)findViewById(R.id.conversation_background);
	background.setBackgroundResource(R.drawable.classroom);
	//set character
	ImageView character = (ImageView)findViewById(R.id.character_right);
	character.setImageResource(R.drawable.asahina);
        //set String loop
        String[] firstStringLoop = new String[] {"私は朝比奈と申します〜　どうぞ宜しくお願い申し上げます。", "私たちは今クラスでミクちゃんの楽しいビヂオを見てます〜。すごく面白いですよね〜", "一緒に見ましょうか？ご主人様がミクちゃん好きじゃないでしょうか？", "私はぜひご主人様と一緒にミクちゃんのビヂオ見たいのですよ〜"}; 
        setStringLoop(firstStringLoop, STRING_LOOP_INTRO_ID, false);
    }

}