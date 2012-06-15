package com.hshieh.animation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.WindowManager;
import android.content.Context;
import android.view.Display;
import android.content.res.Resources;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import android.view.Gravity;
import com.hshieh.animation.GotoAnimator;
import android.view.animation.TranslateAnimation;
import android.animation.AnimatorSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.CycleInterpolator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Rect;
import android.content.Intent;
import android.app.AlertDialog;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class Gallery extends Activity {
    
    GridView gridView;
    int curheight=0;
    int curwidth=0;
    int iconX;
    int iconY;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = this.getIntent();
        final Rect touchPoint = intent.getSourceBounds();
        TextView text = new TextView(this);
        text.setText("Derp");
        this.overridePendingTransition(0,0);
        final ImageView halo = new ImageView(this);
        halo.setImageResource(R.drawable.qv_halo_background);
        //setContentView(text);
        setContentView(R.layout.quickview);
        final TextView ind = (TextView) findViewById(R.id.widget_title);
        final WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        final Resources res = getResources();
        final int horizMargin = res.getDimensionPixelOffset(R.dimen.quickview_horiz_margin);
        final int vertMargin = res.getDimensionPixelOffset(R.dimen.quickview_vert_margin);
        final int width = display.getWidth() - 2 * horizMargin;
        final int height = display.getHeight() - 2 * vertMargin;
        
        //set icon location
        iconX = touchPoint.left;
        iconY = touchPoint.top;
        
        final FrameLayout box = (FrameLayout) findViewById(R.id.box);
        final FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(display.getWidth(), display.getHeight());
        p.gravity = Gravity.LEFT | Gravity.TOP;
        box.setLayoutParams(p);
        ind.setText("touchPointTop" + touchPoint.top + " touchPointLeft" + touchPoint.left);
        final FrameLayout box2 = (FrameLayout) findViewById(R.id.box2);
        box2.addView(halo);
        final FrameLayout.LayoutParams p1 = new FrameLayout.LayoutParams(width, height);
        p1.gravity = Gravity.LEFT | Gravity.TOP;
        box2.setLayoutParams(p1);
        ValueAnimator animX = ValueAnimator.ofInt(touchPoint.left-width/2, display.getWidth()/2-width/2);
        animX.setDuration(400);
        animX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        public void onAnimationUpdate(ValueAnimator animation) {
            int value = (Integer) animation.getAnimatedValue();
            LayoutParams params = getWindow().getAttributes();
            p1.leftMargin=value;
            box2.setLayoutParams(p1);
        }
        });
        ValueAnimator animY = ValueAnimator.ofInt(touchPoint.top-height/2, display.getHeight()/2-height/2);
        animY.setDuration(400);
        //animY.setInterpolator(new OvershootInterpolator());
        animY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        public void onAnimationUpdate(ValueAnimator animation) {
            int value = (Integer) animation.getAnimatedValue();
            LayoutParams params = getWindow().getAttributes();
            p1.topMargin=value;
             ind.setText("touchPointTop" + touchPoint.top);
            box2.setLayoutParams(p1);
        }
        });
        ValueAnimator fadeOut = ValueAnimator.ofFloat(1f, 0f);
        fadeOut.setDuration(500);
        fadeOut.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        public void onAnimationUpdate(ValueAnimator animation) {
            Float value = (Float) animation.getAnimatedValue();
            halo.setAlpha(value);
        }
        });
        
        AnimatorSet animSet = new AnimatorSet();
        //animSet.play(anim).with(anim1).with(anim2).with(anim3);
        //animSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animSet.play(animX).with(animY).before(fadeOut);
        animSet.start();
    }
    
    
    @Override
    public void onPause(){
        super.onPause();
        this.overridePendingTransition(0,0);
        final ImageView halo = new ImageView(this);
        halo.setImageResource(R.drawable.qv_halo_background);
        //setContentView(text);
        setContentView(R.layout.quickview);
        final TextView ind = (TextView) findViewById(R.id.widget_title);
        final WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        final Resources res = getResources();
        final int horizMargin = res.getDimensionPixelOffset(R.dimen.quickview_horiz_margin);
        final int vertMargin = res.getDimensionPixelOffset(R.dimen.quickview_vert_margin);
        final int width = display.getWidth() - 2 * horizMargin;
        final int height = display.getHeight() - 2 * vertMargin;
        
        final FrameLayout box2 = (FrameLayout) findViewById(R.id.box2);
        box2.addView(halo);
        final FrameLayout.LayoutParams p1 = new FrameLayout.LayoutParams(width, height);
        p1.gravity = Gravity.LEFT | Gravity.TOP;
        box2.setLayoutParams(p1);
        
        ValueAnimator animY = ValueAnimator.ofInt(400, 500);
        animY.setDuration(400);
        //animY.setInterpolator(new OvershootInterpolator());
        animY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        public void onAnimationUpdate(ValueAnimator animation) {
            int value = (Integer) animation.getAnimatedValue();
            LayoutParams params = getWindow().getAttributes();
            p1.height = value;
            box2.setLayoutParams(p1);
        }
        });
        AnimatorSet animSet = new AnimatorSet();
        //animSet.play(anim).with(anim1).with(anim2).with(anim3);
        //animSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animSet.play(animY);
        animSet.start();
    }
    
    
    
    static final String[] numbers = new String[] { 
			"1", "2", "3", "4", "5",
			"6", "7", "8", "9", "*",
			"0", "#", 
			"o_O", "O_o", "T_T", ">_<"};
}