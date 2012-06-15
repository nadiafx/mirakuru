/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hshieh.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;

import android.view.animation.DecelerateInterpolator;

/**
 * GotoAnimator: for View whose properties 
 * cannot be animated by ViewPropertyAnimator 
 * e.g. the CellLayout views
 * A GotoAnimation based on ValueAnimator 
 * 
 */
public class GotoAnimator extends ValueAnimator {
    
    private static final String TAG = "GotoAnimator";
    private static boolean DEBUG = false;
    
    public static final TimeInterpolator DEFAULT_INTERPOLATOR = new DecelerateInterpolator(2.5f);
    
    
    enum State {
        STOPPED,
        RUNNING,
        CANCEL
    };
    
    
    /**
     * Object being animated
     */
    protected View mView = null;
    
    // Translation 
    protected Point mStart = new Point();
    protected Point mCurrent = new Point();
    protected Point mGoal = new Point();
    protected int mDeltaX = 0;
    protected int mDeltaY = 0;
    
    // Size 
    protected Point mStartSize = new Point();
    protected Point mCurrentSize = new Point();
    protected Point mGoalSize = new Point();
    protected int mDeltaWidth = 0;
    protected int mDeltaHeight = 0;
    
    // Alpha
    protected float mStartAlpha= 1.0f;
    protected float mCurrentAlpha = 1.0f;
    protected float mGoalAlpha= 1.0f;
    protected float mDeltaAlpha = 0;
    
    // Rotation
    protected float mStartRotation = 0.0f;
    protected float mCurrentRotation = 0.0f;
    protected float mGoalRotation = 0.0f;
    protected float mDeltaRotation = 0.0f;
    
    // State data
    protected Object mTag = null; //tag for storing extra info
    protected State mState = State.STOPPED;

    
    
    public GotoAnimator(View view) {        
        super();

        /**
         * Use the same animator, so 
         */
        setFloatValues(0.0f, 1.0f);
        mView = view;
        initStartValues(view);
        
        addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                onUpdate(animation);
            }
        });
        
        addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                onStart(animation);
            }
            @Override
            public void onAnimationCancel(Animator animation) {
                onCancel(animation);
            }
            
            @Override
            public void onAnimationEnd(Animator animation) {
                onEnd(animation);
            }
            
        });
    }
    
    
    protected void onUpdate(ValueAnimator animation) {
        if (mState != State.RUNNING) {
            return;
        }
        
        compute((Float)animation.getAnimatedValue());
        commit();
    }
    
    protected void onStart(Animator animation) {
        
        if (!hasChanges()) {
            animation.end();
            return;
        }
        
        mDeltaX = mGoal.x - mStart.x;
        mDeltaY = mGoal.y - mStart.y;
        mDeltaWidth = mGoalSize.x - mStartSize.x;
        mDeltaHeight = mGoalSize.y - mStartSize.y;
        mDeltaAlpha = mGoalAlpha - mStartAlpha;
        mDeltaRotation = mGoalRotation - mStartRotation;
        
        mState = State.RUNNING;     
    }
    
    protected void onCancel(Animator animation) {
        mStart.x = mCurrent.x;
        mStart.y = mCurrent.y;
        mStartAlpha = mCurrentAlpha;
        mStartSize.x = mCurrentSize.x;
        mStartSize.y = mCurrentSize.y;
        mStartRotation = mCurrentRotation;
    }
    
    protected void onEnd(Animator animation) {
        mState = State.STOPPED;

        mStart.x = mCurrent.x;
        mStart.y = mCurrent.y;
        mStartAlpha = mCurrentAlpha;
        mStartSize.x = mCurrentSize.x;
        mStartSize.y = mCurrentSize.y;
        mStartRotation = mCurrentRotation;
    }
    
    protected void initStartValues(View view) {
        if (view != null) {
            mStart.x = mCurrent.x = mGoal.x = (int) view.getX();
            mStart.y = mCurrent.y = mGoal.y = (int) view.getY();
            mStartAlpha = mCurrentAlpha = mGoalAlpha = view.getAlpha();
            mStartSize.x = mCurrentSize.x = mGoalSize.x = view.getWidth();
            mStartSize.y = mCurrentSize.y = mGoalSize.y = view.getHeight();
            mStartRotation = view.getRotation();
            
        } else {
            mStart.x = mCurrent.x = mGoal.x = 0;
            mStart.y = mCurrent.y = mGoal.y = 0;
            mStartAlpha = mCurrentAlpha = mGoalAlpha = 1.0f;
            mStartSize.x = mCurrentSize.x = mGoalSize.x = 0;
            mStartSize.y = mCurrentSize.y = mGoalSize.y = 0;
            mStartRotation = mCurrentRotation = mGoalRotation = 0;
        }
    }
    
    
    /**
     * compute current offsets.
     * Make public so another animator can access it
     * @param percent
     */
    protected void compute(float percent) {
        mCurrent.x = mStart.x + (int) (percent * mDeltaX);
        mCurrent.y = mStart.y + (int) (percent * mDeltaY);
        mCurrentAlpha = mStartAlpha + percent * mDeltaAlpha;
        mCurrentSize.x = mStartSize.x + (int) (percent * mDeltaWidth);
        mCurrentSize.y = mStartSize.y + (int) (percent * mDeltaHeight);
        mCurrentRotation = mStartRotation + (int) (percent * mDeltaRotation);
    }
    
    /**
     * Commit. tell view to change to current offsets
     * 
     */
    protected void commit() {
        
        if (mView != null) {
            //TODO: make sure there is only one invalidate call... how to do that?
            mView.setTranslationX(mCurrent.x);
            mView.setTranslationY(mCurrent.y);
            if (mCurrentAlpha != mStartAlpha) {
                mView.setAlpha(mCurrentAlpha);
            }
            if (mCurrentRotation != mStartRotation) {
                mView.setRotation(mCurrentRotation);
            }
            
            final ViewGroup.LayoutParams params = mView.getLayoutParams();
            if (params != null) {
                boolean changed = false;
                
                if (mCurrentSize.x != mStartSize.x) {
                    params.width = mCurrentSize.x;
                    changed = true;
                }
                
                if (mCurrentSize.y != mStartSize.y) {
                    params.height = mCurrentSize.y;
                    changed = true;
                }
                if (changed) {
                    mView.setLayoutParams(params);
                }
            }
         }
    }
    
    public GotoAnimator getAnimator() {
        return this;
    }
    
    public Point getCurrent() {
        return mCurrent;
    }
    
    public Object getTag() {
        return mTag;
    }
    
    public void setTag(Object tag) {
        mTag = tag;
    }
 
    public View getView() {
        return mView;
    }
    
    public void setView(View view) {
        
        if (mView != view) {
            if (isRunning()) {
                cancel();
            }
            mView = view;
            initStartValues(view);
        }
    }
    
    /**
     * resets Animation start/current/goal positions
     * based on view's current params
     */
    public void reset() {
        final View view = mView;
        setView(null);
        setView(view);
    }

    /**
     * Whether this animation has changes
     * @return
     */
    public boolean hasChanges() {
        //Logger.d(TAG, "hasChanges mView=", mView, " start=[", mStart, "]", ", Goal=[", mGoal, "]");
        return (mView != null) && (!mGoal.equals(mStart) || mGoalAlpha != mStartAlpha || !mGoalSize.equals(mStartSize) || mGoalRotation != mStartRotation);
    }

    /**
     * setStartTranslation
     * Should be called after setting goal but before starting
     * @param x
     * @param y
     */
    public void setStartTranslation(int x, int y) {
        mStart.x = x;
        mStart.y = y;
    }
    
    /**
     * setStartAlpha
     * Should be called after setting goal but before starting
     * @param x
     * @param y
     */
    public void setStartAlpha(float alpha) {
        mStartAlpha = alpha;
    }
    
    /**
     * setStartScale
     * Should be called after setting goal but before starting
     * @param x
     * @param y
     */
    public void setStartRotation(float rotation) {
        mStartRotation= rotation;
    }
    /**
     * setStartSize
     * Should be called after setting goal but before starting
     * @param width
     * @param height
     */
    public void setStartSize(int width, int height) {
        mStartSize.x = width;
        mStartSize.y = height;
    }
    
    
    public void setGoalTranslation(int x, int y) {
        if (mGoal.x != x || mGoal.y != y) {
            mGoal.x = x;
            mGoal.y = y;
            cancel();
        }
    }
    
    public void setGoalAlpha(float alpha) {
        if (mGoalAlpha != alpha) {
            mGoalAlpha = alpha;
            cancel();
        }
    }
    
    public void setGoalSize(int width, int height) {
        if (mGoalSize.x != width || mGoalSize.y != height) {
            mGoalSize.x = width;
            mGoalSize.y = height;
            cancel();
        }
    }
    
    public void setGoalRotation(float rotation) {
        if (mGoalRotation != rotation) {
            
        }
    }
    
    /**
     * animateTo a translation
     * @param x
     * @param y
     * @param duration
     */
    public void animateTo(int x, int y, long duration) {
        setGoalTranslation(x, y);
        setDuration(duration);
        start();
    }


    /**
     * Stop mid-way
     */
    @Override
    public void cancel() {
        if (isRunning()) {
            mState = State.CANCEL;
        }
        super.cancel();
    }

}
