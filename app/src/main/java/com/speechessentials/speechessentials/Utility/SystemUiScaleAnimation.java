package com.speechessentials.speechessentials.Utility;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.speechessentials.speechessentials.R;

/**
 * Created by josephdsmithjr on 6/13/2014.
 */
public class SystemUiScaleAnimation {
    int _musicSwitch, _soundSwitch, _vibrationSwitch;
    MediaPlayer _buttonClick;
    FileHelper _fileHelper = new FileHelper();

    public void setBounceAnimation(ImageView imageView, Activity activity, int animationXml){
        Animation animationScale = AnimationUtils.loadAnimation(activity, animationXml);
        imageView.startAnimation(animationScale);
    }

    public void setBounceAnimation(Button button, Activity activity, int animationXml){
        Animation animationScale = AnimationUtils.loadAnimation(activity, animationXml);
        button.startAnimation(animationScale);
    }

    public void setBounceAnimation(TextView textView, Activity activity, int animationXml){
        Animation animationScale = AnimationUtils.loadAnimation(activity, animationXml);
        textView.startAnimation(animationScale);
    }

    public void setBounceAnimation(TextView textView, Context context, int animationXml){
        Animation animationScale = AnimationUtils.loadAnimation(context, animationXml);
        textView.startAnimation(animationScale);
    }

    public View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            _musicSwitch = Integer.parseInt(_fileHelper.getStringFromFile("MusicFile", "0", v.getContext()));
            _soundSwitch = Integer.parseInt(_fileHelper.getStringFromFile("SoundFile", "0", v.getContext()));
            _vibrationSwitch = Integer.parseInt(_fileHelper.getStringFromFile("VibrationFile", "0", v.getContext()));
            _buttonClick = MediaPlayer.create(v.getContext(), R.raw.click1);
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (v.getAnimation() == null) {
                    Animation a = AnimationUtils.loadAnimation(v.getContext(), R.anim.scale);
                    a.setFillAfter(true);
                    v.startAnimation(a);if(_soundSwitch == 1){
                        _buttonClick.start();
                    }
                } else {
                    v.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v1,
                                               MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                Animation a1 = AnimationUtils.loadAnimation(v1.getContext(), R.anim.scale);
                                a1.setFillAfter(true);
                                v1.startAnimation(a1);if(_soundSwitch == 1){
                                    _buttonClick.start();
                                }
                            }
                            return false;
                        }
                    });
                }
            }
            return false;
        }
    };

    public Animation moveLinearLayoutOutLeftAnimation(Context context, final LinearLayout llLayout){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_out_left);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return animation;
    }

    public Animation moveLinearLayoutInLeftAnimation(Context context, final LinearLayout llLayout){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return animation;
    }

    public Animation wordsAnimation(Context context, final LinearLayout llLayout1, final LinearLayout llLayout2, final LinearLayout llLayout3, final LinearLayout llLayout4){
        final Animation flashMatchAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_up);
        flashMatchAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        final Animation wordsAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_down);
        wordsAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llLayout1.setVisibility(View.GONE);
                llLayout2.setVisibility(View.GONE);
                llLayout3.setVisibility(View.VISIBLE);
                llLayout4.setVisibility(View.VISIBLE);
                llLayout3.setAnimation(flashMatchAnimation);
                llLayout4.setAnimation(flashMatchAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return wordsAnimation;
    }

    public Animation sentencesMoveUpAnimation(Context context, final LinearLayout llSentences){
        final float linearLayoutMovement = llSentences.getHeight() + 30;
        final TranslateAnimation moveSentencesIntoTopPosition = new TranslateAnimation(0, 0, 0, -linearLayoutMovement);
        moveSentencesIntoTopPosition.setStartOffset(400);
        moveSentencesIntoTopPosition.setDuration(400);
        return moveSentencesIntoTopPosition;
    }

    public Animation sentencesAnimation(Context context, final LinearLayout llWords, final LinearLayout llStories, final LinearLayout llSentences, final LinearLayout llSentencesRotating, final LinearLayout llSentencesUnique){
        final Animation moveWordsUp = AnimationUtils.loadAnimation(context, R.anim.slide_in_up);
        final float linearLayoutMovement = llWords.getHeight() + 10;
        final TranslateAnimation moveSentencesIntoTopPosition = new TranslateAnimation(0, 0, 0, -linearLayoutMovement);
        moveSentencesIntoTopPosition.setDuration(400);
        moveSentencesIntoTopPosition.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llSentencesRotating.setVisibility(View.VISIBLE);
                llSentencesUnique.setVisibility(View.VISIBLE);
                llSentencesUnique.setAnimation(moveWordsUp);
                llSentencesRotating.setAnimation(moveWordsUp);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        moveWordsUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                llWords.setAnimation(moveWordsUp);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llWords.setVisibility(View.GONE);
                llSentences.setAnimation(moveSentencesIntoTopPosition);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        final Animation moveStoriesDown = AnimationUtils.loadAnimation(context, R.anim.slide_out_down);
        moveStoriesDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                llWords.setAnimation(moveWordsUp);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llStories.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return moveStoriesDown;
    }

}
