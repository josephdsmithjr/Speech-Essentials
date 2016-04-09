package com.speechessentials.speechessentials.Utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.speechessentials.speechessentials.MenuActivity;
import com.speechessentials.speechessentials.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by josephdsmithjr on 5/5/15.
 */
public class LayoutHelper {

    public float[] getScreenWidthAndHeightInDP(Context context){
        float[] widthAndHeightInDP = new float[2];
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        widthAndHeightInDP[0] = dpHeight;
        widthAndHeightInDP[1] = dpWidth;
        return widthAndHeightInDP;
    }

    public int getMainMenuSoundLetterFontSize(Context context){
        int fontSize = 0;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if(displayMetrics.heightPixels > 1020){
            fontSize = 55;
        } else if(displayMetrics.heightPixels > 700){
            fontSize = 45;
        } else if(displayMetrics.heightPixels > 500){
            fontSize = 35;
        } else if(displayMetrics.heightPixels > 300){
            fontSize = 25;
        } else {
            fontSize = 15;
        }
        return fontSize;
    }

    public LinearLayout createLinearLayoutAddUser(Dialog dialog, boolean isSelected, boolean isAddName, String username, Typeface boldTypeface, Typeface normalTypeface){
        LinearLayout linearLayout = new LinearLayout(dialog.getContext());
        View view1 = new View(dialog.getContext());
        TextView textView = new TextView(dialog.getContext());
        ImageView imageView = new ImageView(dialog.getContext());
        View view2 = new View(dialog.getContext());
        float scale = dialog.getContext().getResources().getDisplayMetrics().density;
        int linearLayoutHeight = (int) (50 * scale + 0.5f);
        LinearLayout.LayoutParams linearLayoutLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, linearLayoutHeight);
        LinearLayout.LayoutParams view1LayoutLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 5f);
        LinearLayout.LayoutParams textViewLayoutLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 85f);
        LinearLayout.LayoutParams textViewAddNamesLayoutLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 91f);
        LinearLayout.LayoutParams imageViewLayoutLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 6f);
        LinearLayout.LayoutParams view2LayoutLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 4f);
        linearLayout.setLayoutParams(linearLayoutLayoutParams);
        view1.setLayoutParams(view1LayoutLayoutParams);
        textView.setLayoutParams(textViewLayoutLayoutParams);
        imageView.setLayoutParams(imageViewLayoutLayoutParams);
        view2.setLayoutParams(view2LayoutLayoutParams);

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        textView.setText(username);
        textView.setTypeface(boldTypeface);
        textView.setTextSize(35);
        textView.setGravity(Gravity.LEFT | Gravity.CENTER);
        if(isAddName){
            textView.setLayoutParams(textViewAddNamesLayoutLayoutParams);
            textView.setText("add name");
            textView.setTypeface(null, Typeface.BOLD_ITALIC);
            linearLayout.setBackgroundResource(R.drawable.scoreslightblue_box);
            textView.setTextColor(Color.parseColor("#2B6DB9"));
            imageView.setImageResource(R.drawable.plussign_darkblue);
        } else {
            if(isSelected){
                linearLayout.setBackgroundResource(R.drawable.blue_box);
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                imageView.setImageResource(R.drawable.checkmark);
            } else {
                linearLayout.setBackgroundResource(R.drawable.scoreslightblue_box);
                textView.setTextColor(Color.parseColor("#2B6DB9"));
                imageView.setImageResource(R.drawable.plussign_darkblue);
            }
        }
        linearLayout.addView(view1);
        linearLayout.addView(textView);
        linearLayout.addView(imageView);
        linearLayout.addView(view2);
        return linearLayout;
    }

    public LinearLayout populateActivityScoresDialogScore(Dialog dialog, String date, String soundPosition, String score, String scoreColor, String percentage, Typeface typeface, int textSize){
        LinearLayout linearLayout = new LinearLayout(dialog.getContext());
        LinearLayout trashLinearLayout = new LinearLayout(dialog.getContext());
        View view1 = new View(dialog.getContext());
        View trashView1 = new View(dialog.getContext());
        View trashView2 = new View(dialog.getContext());
        View view2 = new View(dialog.getContext());
        TextView dateTextView = new TextView(dialog.getContext());
        TextView soundPositionTextView = new TextView(dialog.getContext());
        TextView scoreTextView = new TextView(dialog.getContext());
        TextView percentageTextView = new TextView(dialog.getContext());
        ImageView trashImageView = new ImageView(dialog.getContext());

        float scale = dialog.getContext().getResources().getDisplayMetrics().density;
        int linearLayoutHeight = (int) (50 * scale + 0.5f);

        LinearLayout.LayoutParams linearLayoutLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, linearLayoutHeight);
        LinearLayout.LayoutParams trashLinearLayoutLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 5f);
        LinearLayout.LayoutParams view1LayoutLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 3f);
        LinearLayout.LayoutParams view2LayoutLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 2f);
        LinearLayout.LayoutParams trashView1LayoutLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 25f);
        LinearLayout.LayoutParams trashView2LayoutLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 25f);
        LinearLayout.LayoutParams trashImageViewLayoutLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 50f);
        LinearLayout.LayoutParams dateTextViewLayoutLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 18f);
        LinearLayout.LayoutParams soundPositionTextViewLayoutLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 50f);
        LinearLayout.LayoutParams scoreTextViewLayoutLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 17f);
        LinearLayout.LayoutParams percentageTextViewLayoutLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 12f);

        linearLayout.setLayoutParams(linearLayoutLayoutParams);
        trashLinearLayout.setLayoutParams(trashLinearLayoutLayoutParams);
        view1.setLayoutParams(view1LayoutLayoutParams);
        trashView1.setLayoutParams(trashView1LayoutLayoutParams);
        trashView2.setLayoutParams(trashView2LayoutLayoutParams);
        view2.setLayoutParams(view2LayoutLayoutParams);
        dateTextView.setLayoutParams(dateTextViewLayoutLayoutParams);
        soundPositionTextView.setLayoutParams(soundPositionTextViewLayoutLayoutParams);
        scoreTextView.setLayoutParams(scoreTextViewLayoutLayoutParams);
        percentageTextView.setLayoutParams(percentageTextViewLayoutLayoutParams);
        trashImageView.setLayoutParams(trashImageViewLayoutLayoutParams);

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        dateTextView.setText(date);
        dateTextView.setTypeface(typeface);
        dateTextView.setTextSize(textSize);
        dateTextView.setTextColor(Color.parseColor("#2B6DB9"));
        dateTextView.setGravity(Gravity.LEFT | Gravity.CENTER);

        soundPositionTextView.setText(soundPosition);
        soundPositionTextView.setTypeface(typeface);
        soundPositionTextView.setTextSize(textSize);
        soundPositionTextView.setTextColor(Color.parseColor(getSoundPositionColor(soundPosition.substring(0, 1))));
        soundPositionTextView.setGravity(Gravity.LEFT | Gravity.CENTER);

        scoreTextView.setText(score);
        scoreTextView.setTypeface(typeface);
        scoreTextView.setTextSize(textSize);
        scoreTextView.setTextColor(Color.parseColor(scoreColor));
        scoreTextView.setGravity(Gravity.CENTER);

        percentageTextView.setText(percentage);
        percentageTextView.setTypeface(typeface);
        percentageTextView.setTextSize(textSize);
        percentageTextView.setTextColor(Color.parseColor(scoreColor));
        percentageTextView.setGravity(Gravity.CENTER);

        trashImageView.setImageResource(R.drawable.trash_icon);

        linearLayout.addView(view1);
        linearLayout.addView(dateTextView);
        linearLayout.addView(soundPositionTextView);
        linearLayout.addView(scoreTextView);
        linearLayout.addView(percentageTextView);
        linearLayout.addView(trashLinearLayout);
        linearLayout.addView(view2);

        trashLinearLayout.addView(trashView1);
        trashLinearLayout.addView(trashImageView);
        trashLinearLayout.addView(trashView2);
        return linearLayout;
    }


    public LinearLayout addUserToScoresLayout(final MenuActivity menuActivity, final Dialog dialog, final LinearLayout parentLayout, String username, boolean isSelected, final int textSize){
        String[] userInfo = username.split("USERID"); //|USERID|
        final int userId = Integer.valueOf(userInfo[1]);
        username = userInfo[0];
        LinearLayout linearLayout = new LinearLayout(dialog.getContext());
        View view1 = new View(dialog.getContext());
        ImageView infoImageView = new ImageView(dialog.getContext());
        View view2 = new View(dialog.getContext());
        TextView nameTextView = new TextView(dialog.getContext());
        LinearLayout arrowLinearLayout = new LinearLayout(dialog.getContext());
        View view3 = new View(dialog.getContext());
        ImageView arrowImageView = new ImageView(dialog.getContext());
        View view4 = new View(dialog.getContext());
        View view5 = new View(dialog.getContext());
        View view6 = new View(dialog.getContext());

        float scale = dialog.getContext().getResources().getDisplayMetrics().density;
        int linearLayoutHeight = (int) (50 * scale + 0.5f);
        int view6Height = (int) (2 * scale + 0.5f);
        int view6MarginLeft = (int) (2 * scale + 0.5f);

        LinearLayout.LayoutParams linearLayoutLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, linearLayoutHeight);
        LinearLayout.LayoutParams view1LayoutLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 10f);
        LinearLayout.LayoutParams infoImageViewLayoutLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 10f);
        LinearLayout.LayoutParams view2LayoutLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 3f);
        LinearLayout.LayoutParams nameTextViewLayoutLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 58f);
        LinearLayout.LayoutParams linearLayout2LayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 10f);
        LinearLayout.LayoutParams view3LayoutLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 30f);
        LinearLayout.LayoutParams arrowImageViewLayoutLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 40f);
        LinearLayout.LayoutParams view4LayoutLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 30f);
        LinearLayout.LayoutParams view5LayoutLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 10f);
        LinearLayout.LayoutParams view6LayoutLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, view6Height);
        view6LayoutLayoutParams.setMargins(view6MarginLeft, 0, 0, 0);

        linearLayout.setLayoutParams(linearLayoutLayoutParams);
        view1.setLayoutParams(view1LayoutLayoutParams);
        infoImageView.setLayoutParams(infoImageViewLayoutLayoutParams);
        view2.setLayoutParams(view2LayoutLayoutParams);
        nameTextView.setLayoutParams(nameTextViewLayoutLayoutParams);
        arrowLinearLayout.setLayoutParams(linearLayout2LayoutParams);
        view3.setLayoutParams(view3LayoutLayoutParams);
        arrowImageView.setLayoutParams(arrowImageViewLayoutLayoutParams);
        view4.setLayoutParams(view4LayoutLayoutParams);
        view5.setLayoutParams(view5LayoutLayoutParams);
        view6.setLayoutParams(view6LayoutLayoutParams);

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        infoImageView.setImageResource(R.drawable.blueinfo_icon);
        infoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuActivity.openUserDialog(userId);
            }
        });
        String backgroundColor = "#2B6DB9";
        if(isSelected){
            linearLayout.setBackgroundColor(Color.parseColor(backgroundColor));
            nameTextView.setTextColor(Color.parseColor("#FFFFFF"));
            arrowImageView.setImageResource(R.drawable.whitearrow_right);
        } else {
            nameTextView.setTextColor(Color.parseColor(backgroundColor));
            arrowImageView.setImageResource(R.drawable.bluearrow_right);
        }
        nameTextView.setGravity(Gravity.LEFT | Gravity.CENTER);
        nameTextView.setTextSize(textSize);
        nameTextView.setText(username);
        nameTextView.setInputType(InputType.TYPE_CLASS_TEXT); //android:inputType="text"
        nameTextView.setTypeface(null, Typeface.BOLD);
        arrowLinearLayout.setOrientation(LinearLayout.VERTICAL);
        view6.setBackgroundColor(Color.parseColor("#E7E1E1"));
        linearLayout.addView(view1);
        linearLayout.addView(infoImageView);
        linearLayout.addView(view2);
        linearLayout.addView(nameTextView);
        arrowLinearLayout.addView(view3);
        arrowLinearLayout.addView(arrowImageView);
        arrowLinearLayout.addView(view4);
        linearLayout.addView(arrowLinearLayout);
        linearLayout.addView(view5);
        parentLayout.addView(linearLayout);
        parentLayout.addView(view6);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // public void updateScoreUsers(Dialog dialog, LinearLayout parentLayout, int textSize, boolean useUserIdForSelected, int userId){
                menuActivity.updateScoreUsers(dialog, parentLayout, textSize, true, userId);
            }
        });
        return linearLayout;
    }

    /*
    <LinearLayout
        android:layout_width="fill_parent"
        android:layout_height="110dp"
        android:orientation="horizontal"
        android:gravity="center">
    <LinearLayout
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:background="@drawable/rounded_dialog_box_small_blue_matching"
        android:orientation="vertical"
        android:padding="5dp"
        android:layout_marginRight="5dp">
        <TextView
            android:layout_width="fill_parent"
            android:layout_height="0dp"
            android:layout_weight="10"
            android:text="b - begginning"
            android:textSize="8sp"
            android:textStyle="bold"
            android:textColor="#2B6DB9"
            android:gravity="center"/>
        <TextView
            android:layout_width="fill_parent"
            android:layout_height="0dp"
            android:layout_weight="30"
            android:text="ball"
            android:textSize="15sp"
            android:textStyle="bold"
            android:textColor="#2B6DB9"
            android:gravity="center"/>
        <ImageView
            android:layout_width="fill_parent"
            android:layout_height="0dp"
            android:layout_weight="60"
            android:padding="10dp"
            android:src="@drawable/ball"/>
    </LinearLayout>
     */

    public void populateSoundImageLayout(Context context, String sound, LinearLayout parentLayout, Typeface typeface, int soundTypeTextSize, int wordTextSize, int numberOfWordsPerLayout){
        //can add 6 per layout
        try{
            parentLayout.removeAllViews();
        } catch(Exception e){
            e.printStackTrace();
        }
        DatabaseOperationsHelper _databaseOperationsHelper = new DatabaseOperationsHelper(context);
        ArrayList<String> soundInfoList = _databaseOperationsHelper.getSoundInfoList(_databaseOperationsHelper, sound);
        ArrayList<String> wordList = new ArrayList<>();
        ArrayList<String> wordTypeList = new ArrayList<>();
        ArrayList<Bitmap> imageBackgroundList = new ArrayList<>();
        ArrayList<String> imageAudioList = new ArrayList<>();
        for(int i = 0; i < soundInfoList.size(); i++){
            //"b - beginning","back","R.drawable.back","R.raw.back"
            //String soundInfo = soundType + "|" + soundWord + "|" + imageResource + "|" + imageAudioResource;
            String[] info = soundInfoList.get(i).split("\\|");
            wordTypeList.add(info[0]);
            wordList.add(info[1]);
            String imagePath = info[1].toLowerCase().replace(" ","").replace("-","").replace("(","").replace(")", "");
            String imageAudioPath = info[1].toLowerCase().replace(" ","").replace("-","").replace("(","").replace(")","");
            ContextWrapper cw = new ContextWrapper(context);
            File directory = cw.getDir("audioDir", Context.MODE_PRIVATE);
            String path = directory.getPath() + "/" + imageAudioPath + ".mp3";
            if(info[0].contains("sentence")){
                imagePath = info[2].toLowerCase().replace(" ","").replace("-","").replace("(","").replace(")", "");
                imageAudioPath = "s_" + info[2].toLowerCase().replace(" ","").replace("-","").replace("(","").replace(")","");
                directory = cw.getDir("audioDir", Context.MODE_PRIVATE);
                path = directory.getPath() + "/" + imageAudioPath + ".mp3";
            }
            FileHelper fileHelper = new FileHelper();
            Bitmap soundImageBitmap = fileHelper.getBitmapFromInternalStorage(context, imagePath);
            imageBackgroundList.add(soundImageBitmap);
            imageAudioList.add(path);
        }
        ArrayList<String> wordListCarrier = new ArrayList<>();
        ArrayList<String> wordTypeListCarrier = new ArrayList<>();
        ArrayList<Bitmap> imageBackgroundListCarrier = new ArrayList<>();
        ArrayList<String> imageAudioListCarrier = new ArrayList<>();
        int remainingWords = wordList.size() - (wordList.size() % numberOfWordsPerLayout);
        for(int i = 1; i <= wordList.size(); i++){
            wordListCarrier.add(wordList.get(i-1));
            wordTypeListCarrier.add(wordTypeList.get(i-1));
            imageBackgroundListCarrier.add(imageBackgroundList.get(i-1));
            imageAudioListCarrier.add(imageAudioList.get(i - 1));
            if(i % numberOfWordsPerLayout == 0){
                LinearLayout childLayout = populateSoundImageLayoutList(context, wordTypeListCarrier, wordListCarrier, imageBackgroundListCarrier, typeface, soundTypeTextSize, wordTextSize, imageAudioListCarrier);
                parentLayout.addView(childLayout);
                wordListCarrier.clear();
                wordTypeListCarrier.clear();
                imageBackgroundListCarrier.clear();
                imageAudioListCarrier.clear();
            }
        }
        LinearLayout childLayout = populateSoundImageLayoutList(context, wordTypeListCarrier, wordListCarrier, imageBackgroundListCarrier, typeface, soundTypeTextSize, wordTextSize, imageAudioListCarrier);
        parentLayout.addView(childLayout);
    }

    public void setImageSoundAndAudio(final Activity activity, String soundImage, ImageView imageView){
        FileHelper _fileHelper = new FileHelper();
        final DatabaseOperationsHelper _databaseOperationsHelper = new DatabaseOperationsHelper(activity);
        final String imageName = soundImage.toLowerCase().replace(" ","").replace("-","").replace("(", "").replace(")", "");
        Bitmap soundImageBitmap = _fileHelper.getBitmapFromInternalStorage(activity, imageName);
        imageView.setImageBitmap(soundImageBitmap);
        try{
            ContextWrapper cw = new ContextWrapper(activity);
            File directory = cw.getDir("audioDir", Context.MODE_PRIVATE);
            String mpPath = directory.getPath() + "/" + imageName + ".mp3";
            final MediaPlayer _mp = MediaPlayer.create(activity,Uri.parse(mpPath));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(_databaseOperationsHelper.getSettingValue(_databaseOperationsHelper, "voice_on") == 1) {
                        if (_mp != null) {
                            _mp.start();
                            _mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.release();
                                }
                            });
                        } else {
                            Toast.makeText(activity, "Sound does not exist for word: " + imageName, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public LinearLayout populateSoundImageLayoutList(Context context, ArrayList<String> soundTypeList, ArrayList<String> wordList, ArrayList<Bitmap> imageBackgroundList, Typeface typeface, int soundTypeTextSize, int wordTextSize, ArrayList<String> imageSoundList){
        LinearLayout linearLayout = new LinearLayout(context);
        float scale = context.getResources().getDisplayMetrics().density;
        int linearLayoutHeight = (int) (110 * scale + 0.5f);
        LinearLayout.LayoutParams linearLayoutLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, linearLayoutHeight);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setLayoutParams(linearLayoutLayoutParams);
        for(int i = 0; i < wordList.size(); i++){
            LinearLayout childLayout = populateSoundImageLayout(context, soundTypeList.get(i), wordList.get(i), imageBackgroundList.get(i), typeface, soundTypeTextSize, wordTextSize, imageSoundList.get(i));
            linearLayout.addView(childLayout);
        }
        return linearLayout;
    }

    public LinearLayout populateSoundImageLayout(final Context context, String soundType, final String word, Bitmap imageBackground, Typeface typeface, int soundTypeTextSize, int wordTextSize, final String imageSound){
        LinearLayout linearLayout = new LinearLayout(context);
        TextView tvSoundType = new TextView(context);
        TextView tvWord = new TextView(context);
        ImageView ivImage = new ImageView(context);
        View view1 = new View(context);

        float scale = context.getResources().getDisplayMetrics().density;
        int linearLayoutHeight = (int) (100 * scale + 0.5f);
        int linearLayoutWidth = (int) (100 * scale + 0.5f);
        int linearPadding = (int) (5 * scale + 0.5f);
        int linearMarginRight = (int) (5 * scale + 0.5f);

        LinearLayout.LayoutParams linearLayoutLayoutParams = new LinearLayout.LayoutParams(linearLayoutWidth, linearLayoutHeight);
        linearLayoutLayoutParams.rightMargin = linearMarginRight;
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(0,0,linearPadding,0);
        LinearLayout.LayoutParams soundTypeTextViewLayoutLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 10f);
        LinearLayout.LayoutParams wordTextViewLayoutLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 30f);
        LinearLayout.LayoutParams ivImageViewLayoutLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 55f);
        LinearLayout.LayoutParams viewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 5f);

        linearLayout.setBackgroundResource(getLayoutBackground(soundType.substring(0, 1)));

        linearLayout.setLayoutParams(linearLayoutLayoutParams);
        tvSoundType.setLayoutParams(soundTypeTextViewLayoutLayoutParams);
        tvWord.setLayoutParams(wordTextViewLayoutLayoutParams);
        ivImage.setLayoutParams(ivImageViewLayoutLayoutParams);

        tvSoundType.setText(soundType);
        tvSoundType.setTypeface(typeface);
        tvSoundType.setTextSize(soundTypeTextSize);
        tvSoundType.setTextColor(Color.parseColor(getSoundPositionColor(soundType.substring(0, 1))));
        tvSoundType.setGravity(Gravity.CENTER);

        tvWord.setText(word);
        tvWord.setTypeface(typeface);
        tvWord.setTextSize(wordTextSize);
        tvWord.setTextColor(Color.parseColor(getSoundPositionColor(soundType.substring(0, 1))));
        tvWord.setGravity(Gravity.CENTER);
        ImageHelper imageHelper = new ImageHelper();
        try{
            ivImage.setImageBitmap(imageBackground);
            ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        final MediaPlayer mp = MediaPlayer.create(context, Uri.parse(imageSound));
                        if (mp != null) {
                            mp.start();
                        } else {
                            Toast.makeText(context,"Sound does not exist for word: " + word,Toast.LENGTH_SHORT);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch(Exception e){
            e.printStackTrace();
            Log.d("ERROR!!!",word);
        }

        view1.setLayoutParams(viewLayoutParams);

        linearLayout.addView(tvSoundType);
        linearLayout.addView(tvWord);
        linearLayout.addView(ivImage);
        linearLayout.addView(view1);

        Log.d("Word", word);
        return linearLayout;
    }

    public void addUserScoreLayoutToParentLayout(final Context context, final LinearLayout parentLayout, final int scoreId, final String[] scoreData, final DatabaseOperationsHelper databaseOperationsHelper){
        String scoreDate = scoreData[0];
        String[] scoreList = scoreData[1].split(",");
        //date, f-ending words,6/6,100%
        String scoreSoundPosition = scoreList[0];
        String scoreScore = scoreList[1];
        String scorePercentage = scoreList[2];
        LinearLayout scoresLinearLayout = new LinearLayout(context);
        View view1 = new View(context);
        TextView tvDate = new TextView(context);
        tvDate.setTextColor(Color.parseColor("#2B6DB9"));
        tvDate.setTypeface(null, Typeface.BOLD);
        tvDate.setGravity(Gravity.LEFT | Gravity.CENTER);
        tvDate.setText(scoreDate);
        TextView tvSoundPosition = new TextView(context);
        tvSoundPosition.setTypeface(null, Typeface.BOLD);
        tvSoundPosition.setGravity(Gravity.LEFT | Gravity.CENTER);
        tvSoundPosition.setText(scoreSoundPosition);
        tvSoundPosition.setTextColor(Color.parseColor(getSoundPositionColor(scoreSoundPosition.substring(0, 1))));
        TextView tvScore = new TextView(context);
        tvScore.setTypeface(null, Typeface.BOLD);
        tvScore.setGravity(Gravity.CENTER);
        tvScore.setText(scoreScore);
        tvScore.setTextColor(Color.parseColor("#2B6DB9"));
        TextView tvPercentage = new TextView(context);
        tvPercentage.setTypeface(null, Typeface.BOLD);
        tvPercentage.setGravity(Gravity.CENTER);
        String percentageColor = "#2B6DB9";
        float floatPercent = Float.valueOf(scorePercentage.replace("%",""));
        int intPercent = Math.round(floatPercent);
        if(intPercent < 80){
            percentageColor = "#E7212E";
        }
        tvPercentage.setTextColor(Color.parseColor(percentageColor));
        tvPercentage.setText(scorePercentage);
        LinearLayout llTrash = new LinearLayout(context);
        View vTrash1 = new View(context);
        ImageView ivTrash = new ImageView(context);
        ivTrash.setImageResource(R.drawable.trash_icon);
        View vTrash2 = new View(context);
        View view2 = new View(context);
        View vSeparator = new View(context);
        vSeparator.setBackgroundColor(Color.parseColor("#E7E1E1"));

        float scale = context.getResources().getDisplayMetrics().density;
        int linearLayoutHeight = (int) (50 * scale + 0.5f);
        int vSeparatorHeight = (int) (5 * scale + 0.5f);

        LinearLayout.LayoutParams scoresLinearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, linearLayoutHeight);
        LinearLayout.LayoutParams view1LayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 3f);
        LinearLayout.LayoutParams tvDateLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 18f);
        LinearLayout.LayoutParams tvSoundPositionLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 50f);
        LinearLayout.LayoutParams tvScoreLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 17f);
        LinearLayout.LayoutParams tvPercentageLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 12f);
        LinearLayout.LayoutParams llTrashLinearLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 5f);
        LinearLayout.LayoutParams vTrash1LayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 25f);
        LinearLayout.LayoutParams ivTrashLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 50f);
        LinearLayout.LayoutParams vTrash2LayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 25f);
        LinearLayout.LayoutParams view2LayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 2f);
        LinearLayout.LayoutParams vSeparatorLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, vSeparatorHeight);

        scoresLinearLayout.setLayoutParams(scoresLinearLayoutParams);
        scoresLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        view1.setLayoutParams(view1LayoutParams);
        tvDate.setLayoutParams(tvDateLayoutParams);
        tvSoundPosition.setLayoutParams(tvSoundPositionLayoutParams);
        tvScore.setLayoutParams(tvScoreLayoutParams);
        tvPercentage.setLayoutParams(tvPercentageLayoutParams);
        llTrash.setLayoutParams(llTrashLinearLayoutParams);
        llTrash.setOrientation(LinearLayout.VERTICAL);
        vTrash1.setLayoutParams(vTrash1LayoutParams);
        ivTrash.setLayoutParams(ivTrashLayoutParams);
        vTrash2.setLayoutParams(vTrash2LayoutParams);
        view2.setLayoutParams(view2LayoutParams);
        vSeparator.setLayoutParams(vSeparatorLayoutParams);

        llTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuActivity menuActivity = (MenuActivity) context;
                menuActivity.deleteScore(databaseOperationsHelper, scoreId, parentLayout);
            }
        });
        llTrash.addView(vTrash1);
        llTrash.addView(ivTrash);
        llTrash.addView(vTrash2);
        scoresLinearLayout.addView(view1);
        scoresLinearLayout.addView(tvDate);
        scoresLinearLayout.addView(tvSoundPosition);
        scoresLinearLayout.addView(tvScore);
        scoresLinearLayout.addView(tvPercentage);
        scoresLinearLayout.addView(llTrash);
        scoresLinearLayout.addView(view2);
        parentLayout.addView(scoresLinearLayout);
        parentLayout.addView(vSeparator);
    }

    public int getLayoutBackground(String sound) {
        int background = R.drawable.rounded_dialog_box_small_blue;
        switch (sound) {
            case "t-":
                background = R.drawable.rounded_dialog_box_small_yellow;
                break;
            case "d-":
                background = R.drawable.rounded_dialog_box_small_yellow;
                break;
            case "f-":
                background = R.drawable.rounded_dialog_box_small_yellow;
                break;
            case "v-":
                background = R.drawable.rounded_dialog_box_small_yellow;
                break;
            case "ch":
                background = R.drawable.rounded_dialog_box_small_yellow;
                break;
            case "y-":
                background = R.drawable.rounded_dialog_box_small_yellow;
                break;
            case "ng":
                background = R.drawable.rounded_dialog_box_small_yellow;
                break;
            case "s-":
                background = R.drawable.rounded_dialog_box_small_red;
                break;
            case "z-":
                background = R.drawable.rounded_dialog_box_small_red;
                break;
            case "l-":
                background = R.drawable.rounded_dialog_box_small_red;
                break;
            case "r-":
                background = R.drawable.rounded_dialog_box_small_red;
                break;
            case "sh":
                background = R.drawable.rounded_dialog_box_small_red;
                break;
            case "th":
                background = R.drawable.rounded_dialog_box_small_red;
                break;
            case "zh":
                background = R.drawable.rounded_dialog_box_small_red;
                break;
        }
        return background;
    }

    public String getSoundPositionColor(String sound){
        String color = "#2B6DB9";
        switch(sound){
            case "t-":
                color = "#FBB812";
                break;
            case "d-":
                color = "#FBB812";
                break;
            case "f-":
                color = "#FBB812";
                break;
            case "v-":
                color = "#FBB812";
                break;
            case "ch":
                color = "#FBB812";
                break;
            case "y-":
                color = "#FBB812";
                break;
            case "ng":
                color = "#FBB812";
                break;
            case "s-":
                color = "#E7212E";
                break;
            case "z-":
                color = "#E7212E";
                break;
            case "l-":
                color = "#E7212E";
                break;
            case "r-":
                color = "#E7212E";
                break;
            case "sh":
                color = "#E7212E";
                break;
            case "th":
                color = "#E7212E";
                break;
            case "zh":
                color = "#E7212E";
                break;
        }
        return color;
    }
}
