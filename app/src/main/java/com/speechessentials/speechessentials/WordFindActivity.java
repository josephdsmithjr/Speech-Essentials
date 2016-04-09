package com.speechessentials.speechessentials;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.speechessentials.speechessentials.Utility.DatabaseOperationsHelper;
import com.speechessentials.speechessentials.Utility.FileHelper;
import com.speechessentials.speechessentials.Utility.ScoreHelper;
import com.speechessentials.speechessentials.Utility.SettingsHelper;
import com.speechessentials.speechessentials.Utility.SoundHelper;
import com.speechessentials.speechessentials.Utility.SystemUiScaleAnimation;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by josephdsmithjr on 4/14/2014.
 */
public class WordFindActivity extends Activity {

    DatabaseOperationsHelper _databaseOperationsHelper;
    ScoreHelper _scoreHelper;
    String _currentUserName;
    String _currentImageName;
    ArrayList<TextView> _usernameTextViewList;
    ArrayList<LinearLayout> _linearLayoutUserList;
    EditText _etBirthday;
    FileHelper _fileHelper;
    ArrayList<Integer> _soundImageIdList, _incorrectImageNameIdList, _correctImageNameIdList, _approximateImageNameIdList;
    ArrayList<String> _imageWordList, _soundPositionNameList, _soundWordTypeList, _imageDrawableList;
    String _soundName;
    int _uiOptions;

    boolean _beginningSelected, _middleSelected, _endingSelected, _verticalRatioIsHigher, _longWordGoesInFirstSpot, _atLeastOneWordHasSixLetters, _okayToOpenMirror;
    float _density, x1, x2, y1, y2;
    ImageView _ivWhiteGraphCircle, _ivBeginningIcon, _ivMiddleIcon, _ivEndingIcon, _ivBottomBackground, _ivDone, _ivMirror, _ivFlashcards, _ivSoundImage, _ivSettingsIcon,
    _ivMatchingArrow1, _ivMatchingArrow2, _ivMatchingArrow3, _ivMatchingArrow4, _ivMatchingArrow5, _ivMatchingArrow6;
    int _imageListSize, _imageListPosition, _screenWidth, _screenHeight, _totalCorrect, _totalScore, _percentCorrect, _numberOfWordsWithSixLetters, _numberOfVerticalSpaces, _numberOfHorizontalSpaces, _soundNumber;
    ArrayList<String> _wordFindSpacesList;
    ArrayList<TextView> _remainingTextViewList, _wordListTextViews, _firstWordTextViews, _secondWordTextViews, _thirdWordTextViews, _fourthWordTextViews, _fifthWordTextViews, _sixthWordTextViews;
    HashMap<TextView, String> _selectedWordColorsMap;
    HashMap<Integer, String> _wordColorsMap;
    HashMap<String, String> _soundWordTypeMap, _soundDrawableMap;
    HashMap<String, Integer> _wordToColorNumberMap;
    HashMap<String, TextView> _letterToTextViewMap, _selectedWordToTextViewMap;
    LinearLayout _llBeginning, _llMiddle, _llEnding, _llDialogInstructions, _llDialogFlashCards, _llSettingsButton, _llSettings, _llWordList, _llStart, _llGraphButtons, _llMirrorButton, _llFeedbackButton,
            _llWordList1, _llWordList2, _llWordList3, _llWordList4, _llWordList5, _llWordList6;
    RelativeLayout _rlDialogWordFind, _rlDialogFlashCardsGoodJob;
    SystemUiScaleAnimation _scaleAnimation;
    SoundHelper _soundHelper;
    TextView _tvLetter, _tvLetterSound, _tvRatioCorrect, _tvBeginning, _tvMiddle, _tvEnding, _tvDone, _tvInstructionsTitle, _tvInstructionsSelect, _tvInstructionsBegMidEnd, _tvInstructionsCreateWordList,
            _tvCorrect, _tvWrong, _tvSettings, _tvMirror, _tvStartText, _tvStart,
            _tvLetterA1, _tvLetterA2, _tvLetterA3, _tvLetterA4, _tvLetterA5, _tvLetterA6,
            _tvLetterB1, _tvLetterB2, _tvLetterB3, _tvLetterB4, _tvLetterB5, _tvLetterB6,
            _tvLetterC1, _tvLetterC2, _tvLetterC3, _tvLetterC4, _tvLetterC5, _tvLetterC6,
            _tvLetterD1, _tvLetterD2, _tvLetterD3, _tvLetterD4, _tvLetterD5, _tvLetterD6,
            _tvLetterE1, _tvLetterE2, _tvLetterE3, _tvLetterE4, _tvLetterE5, _tvLetterE6,
            _tvLetterF1, _tvLetterF2, _tvLetterF3, _tvLetterF4, _tvLetterF5, _tvLetterF6,
            _tvMatching1, _tvMatching2, _tvMatching3, _tvMatching4, _tvMatching5, _tvMatching6;
    Typeface _typeFaceAnjaBold, _typeFaceAnja;
    View _decorView, _vGraphScale, _vGraphBackground;
    SettingsHelper _settingsHelper;
    boolean _settingsAreScoringSoundsOn, _settingsAreScoringButtonsVisible, _settingsIsVoiceOn, _settingsIsMirrorFunctionOn;

    ArrayList<String> _listOfSoundNamesForGame;
    RelativeLayout _rlBeginningOptions;
    LinearLayout _llRBeginningOptions, _llRMiddleOptions, _llREndingOptions, _llLBeginningOptions, _llSBlendOptions, _llLBlendOptions, _llRBlendOptions, _llBlends;
    ImageView _ivBeginningArrow, _ivMiddleArrow, _ivEndingArrow, _ivBlendsArrow;
    TextView _tvBlends, tvAIR, tvAR, tvBR, tvKR, tvTR, tvDR, tvEAR, tvER, tvFR, tvGR, tvIRE, tvOR, tvPR, tvMiddleAIR, tvMiddleAR, tvMiddleEAR, tvMiddleER, tvMiddleIRE, tvMiddleOR, tvMiddleRL, tvEndingAIR, tvEndingAR, tvEndingEAR, tvEndingER, tvEndingIRE, tvEndingOR, tvEndingRL, tvR, tvMiddleR, tvEndingR;
    TextView tvBL, tvFL, tvKL, tvGL, tvPL, tvLSL;
    TextView tvSK, tvSL, tvSM, tvSN, tvSP, tvST, tvSW, tvKS, tvNS, tvPS, tvTS;
    boolean tvAIRSelected, tvARSelected, tvBRSelected, tvKRSelected, tvTRSelected, tvDRSelected, tvEARSelected, tvERSelected, tvFRSelected, tvGRSelected, tvIRESelected, tvORSelected, tvPRSelected, tvMiddleAIRSelected, tvMiddleARSelected, tvMiddleEARSelected, tvMiddleERSelected, tvMiddleIRESelected, tvMiddleORSelected, tvMiddleRLSelected, tvEndingAIRSelected, tvEndingARSelected, tvEndingEARSelected, tvEndingERSelected, tvEndingIRESelected, tvEndingORSelected, tvEndingRLSelected;
    boolean tvBLSelected, tvFLSelected, tvKLSelected, tvGLSelected, tvPLSelected, tvLSLSelected;
    boolean tvSKSelected, tvSLSelected, tvSMSelected, tvSNSelected, tvSPSelected, tvSTSelected, tvSWSelected, tvKSSelected, tvNSSelected, tvPSSelected, tvTSSelected;
    boolean tvRSelected, tvMiddleRSelected, tvEndingRSelected;
    ImageView ivBeginningArrow, ivMiddleArrow, ivEndingArrow, ivBlendsArrow, _ivBlendsIcon;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordfind);
        _decorView = getWindow().getDecorView();
        _uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        _decorView.setSystemUiVisibility(_uiOptions);
        _decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            _decorView.setSystemUiVisibility(_uiOptions);
                        } else {
                            _decorView.setSystemUiVisibility(_uiOptions);
                        }
                    }
                });
        _soundName = getIntent().getStringExtra("LETTER");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initiateVariables();
        initializeDatabaseResources();
        setGoogleAnalytics();
    }

    float _dpHeight, _dpWidth;
    public int getTextSize(int originalTextSize){
        //6" phone: 1440 * 2413 = 3474720
        int convertedTextSize = 0;
        double percentage = originalTextSize / 411.42856;
        double letterTextSize = _dpHeight * percentage;
        Double letterTextSizeD = new Double(letterTextSize);
        convertedTextSize = letterTextSizeD.intValue();
        return convertedTextSize;
    }

    public static GoogleAnalytics analytics;
    public static Tracker tracker;
    public void setGoogleAnalytics(){
        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);
        tracker = analytics.newTracker("UA-65570011-1");
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
        // All subsequent hits will be send with screen name = "main screen"
        tracker.setScreenName("Word Fine");
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("UX")
                .setAction("click")
                .setLabel("submit")
                .build());

        // Builder parameters can overwrite the screen name set on the tracker.
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("UX")
                .setAction("click")
                .setLabel("help popup")
                        //.set(Fields.SCREEN_NAME, "help popup dialog")
                .build());
    }

    public void initiateVariables(){
        _databaseOperationsHelper = new DatabaseOperationsHelper(this);
        _settingsHelper = new SettingsHelper();
        _settingsAreScoringSoundsOn = _settingsHelper.getSettingsAreScoringSoundsOn(this);
        _settingsAreScoringButtonsVisible = _settingsHelper.getSettingsAreScoringButtonsVisible(this);
        _settingsIsVoiceOn = _settingsHelper.getSettingsIsVoiceOn(this);
        _settingsIsMirrorFunctionOn = _settingsHelper.getSettingsIsMirrorFunctionOn(this);
        _soundHelper = new SoundHelper();
        _fileHelper = new FileHelper();
        _soundNumber = 2;

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density = getResources().getDisplayMetrics().density;
        _dpHeight = outMetrics.heightPixels / density;//411.42856
        _dpWidth = outMetrics.widthPixels / density;//689.4286
        //TODO: pass the letter number from menu
//        _soundDrawableList = _soundHelper.getDrawableList(1);
        _imageListPosition = 0;
        _scaleAnimation = new SystemUiScaleAnimation();
        _typeFaceAnjaBold = Typeface.createFromAsset(getAssets(), "font/anja.ttf");
        _typeFaceAnja = Typeface.createFromAsset(getAssets(), "font/anja.ttf");
        _firstWordTextViews = new ArrayList<>();
        _secondWordTextViews = new ArrayList<>();
        _thirdWordTextViews = new ArrayList<>();
        _fourthWordTextViews = new ArrayList<>();
        _fifthWordTextViews = new ArrayList<>();
        _sixthWordTextViews = new ArrayList<>();
        _selectedWordColorsMap = new HashMap<>();
        _selectedWordToTextViewMap = new HashMap<>();
        _beginningSelected = false;
        _middleSelected = false;
        _endingSelected = false;
        _tvMirror = (TextView) findViewById(R.id.tvMirror);
        _llMirrorButton = (LinearLayout) findViewById(R.id.llMirrorButton);
        _llFeedbackButton = (LinearLayout) findViewById(R.id.llFeedbackButton);
        _llFeedbackButton.setOnTouchListener(_scaleAnimation.onTouchListener);
        _llFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WordFindActivity.this, "Please start the game first.", Toast.LENGTH_SHORT).show();
            }
        });
        if(_settingsIsMirrorFunctionOn){
            _llMirrorButton.setVisibility(View.VISIBLE);
            _tvMirror.setVisibility(View.VISIBLE);
        } else {
            _llMirrorButton.setVisibility(View.INVISIBLE);
            _tvMirror.setVisibility(View.INVISIBLE);
        }
        _ivWhiteGraphCircle = (ImageView) findViewById(R.id.ivWhiteGraphCircle);
        _ivBeginningIcon = (ImageView) findViewById(R.id.ivBeginningIcon);
        _ivMiddleIcon = (ImageView) findViewById(R.id.ivMiddleIcon);
        _ivEndingIcon = (ImageView) findViewById(R.id.ivEndingIcon);
        _ivFlashcards = (ImageView) findViewById(R.id.ivFlashcards);
        _ivSoundImage = (ImageView) findViewById(R.id.ivSoundImage);
        _ivSettingsIcon = (ImageView) findViewById(R.id.ivSettingsIcon);
        _ivMirror = (ImageView) findViewById(R.id.ivMirror);
        _ivMirror.setOnTouchListener(_scaleAnimation.onTouchListener);
        _ivMirror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WordFindActivity.this, "Please start the game first.", Toast.LENGTH_SHORT).show();
            }
        });

        TextView tvPlayAgain = (TextView) findViewById(R.id.tvPlayAgain);
        TextView tvPlayAgainYes = (TextView) findViewById(R.id.tvPlayAgainYes);
        TextView tvPlayAgainNo = (TextView) findViewById(R.id.tvPlayAgainNo);
        tvPlayAgain.setTypeface(_typeFaceAnja);
        tvPlayAgainYes.setTypeface(_typeFaceAnja);
        tvPlayAgainNo.setTypeface(_typeFaceAnja);
        tvPlayAgainYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScoresDialog(true);
            }
        });
        tvPlayAgainNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScoresDialog(false);
            }
        });
        _ivDone = (ImageView) findViewById(R.id.ivDone);
        _ivDone.setOnTouchListener(_scaleAnimation.onTouchListener);
        _ivDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScoresDialog(false);
            }
        });
        _ivMatchingArrow1 = (ImageView) findViewById(R.id.ivMatchingArrow1);
        _ivMatchingArrow2 = (ImageView) findViewById(R.id.ivMatchingArrow2);
        _ivMatchingArrow3 = (ImageView) findViewById(R.id.ivMatchingArrow3);
        _ivMatchingArrow4 = (ImageView) findViewById(R.id.ivMatchingArrow4);
        _ivMatchingArrow5 = (ImageView) findViewById(R.id.ivMatchingArrow5);
        _ivMatchingArrow6 = (ImageView) findViewById(R.id.ivMatchingArrow6);
        _ivMatchingArrow1.setVisibility(View.INVISIBLE);
        _ivMatchingArrow2.setVisibility(View.INVISIBLE);
        _ivMatchingArrow3.setVisibility(View.INVISIBLE);
        _ivMatchingArrow4.setVisibility(View.INVISIBLE);
        _ivMatchingArrow5.setVisibility(View.INVISIBLE);
        _ivMatchingArrow6.setVisibility(View.INVISIBLE);
        Point size = new Point();
        display.getSize(size);
        _screenWidth = size.x;
        _screenHeight = size.y;
        _density = getResources().getDisplayMetrics().density;

        _percentCorrect = 0;
        _totalCorrect = 0;
        _totalScore = 0;

        _llSettings = (LinearLayout) findViewById(R.id.rlSettings);
        _llGraphButtons = (LinearLayout) findViewById(R.id.llGraphButtons);
        _llStart = (LinearLayout) findViewById(R.id.llStart);
        _llBeginning = (LinearLayout) findViewById(R.id.llBeginning);
        _llMiddle = (LinearLayout) findViewById(R.id.llMiddle);
        _llEnding = (LinearLayout) findViewById(R.id.llEnding);
        _llWordList = (LinearLayout) findViewById(R.id.llWordList);
        _llWordList1 = (LinearLayout) findViewById(R.id.llWordList1);
        _llWordList2 = (LinearLayout) findViewById(R.id.llWordList2);
        _llWordList3 = (LinearLayout) findViewById(R.id.llWordList3);
        _llWordList4 = (LinearLayout) findViewById(R.id.llWordList4);
        _llWordList5 = (LinearLayout) findViewById(R.id.llWordList5);
        _llWordList6 = (LinearLayout) findViewById(R.id.llWordList6);
        _llBeginning.setOnTouchListener(_scaleAnimation.onTouchListener);
        _llMiddle.setOnTouchListener(_scaleAnimation.onTouchListener);
        _llEnding.setOnTouchListener(_scaleAnimation.onTouchListener);

        _llBeginning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectStartOptions(1);
            }
        });
        _llMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectStartOptions(2);
            }
        });
        _llEnding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectStartOptions(3);
            }
        });
        _llDialogInstructions = (LinearLayout) findViewById(R.id.llDialogInstructions);
        _llDialogFlashCards = (LinearLayout) findViewById(R.id.llDialogFlashCards);
        _llSettingsButton = (LinearLayout) findViewById(R.id.llSettingsButton);
        _llSettingsButton.setOnTouchListener(_scaleAnimation.onTouchListener);
        _llSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WordFindActivity.this, "Please start the game first.", Toast.LENGTH_SHORT).show();
            }
        });

        _rlDialogWordFind = (RelativeLayout) findViewById(R.id.rlDialogWordFind);

        _rlDialogFlashCardsGoodJob = (RelativeLayout) findViewById(R.id.rlDialogFlashCardsGoodJob);
        _rlBeginningOptions = (RelativeLayout) findViewById(R.id.rlBeginningOptions);
        _tvLetter = (TextView) findViewById(R.id.tvLetter);
        _tvLetterSound = (TextView) findViewById(R.id.tvLetterSound);
        _tvRatioCorrect = (TextView) findViewById(R.id.tvRatioCorrect);
        _tvBeginning = (TextView) findViewById(R.id.tvBeginning);
        _tvMiddle = (TextView) findViewById(R.id.tvMiddle);
        _tvEnding = (TextView) findViewById(R.id.tvEnding);
        _tvDone = (TextView) findViewById(R.id.tvDone);
        _tvInstructionsTitle = (TextView) findViewById(R.id.tvInstructionsTitle);
        _tvInstructionsSelect = (TextView) findViewById(R.id.tvInstructionsSelect);
        _tvInstructionsBegMidEnd = (TextView) findViewById(R.id.tvInstructionsBegMidEnd);
        _tvInstructionsCreateWordList = (TextView) findViewById(R.id.tvInstructionsCreateWordList);
        _tvCorrect = (TextView) findViewById(R.id.tvCorrect);
        _tvWrong = (TextView) findViewById(R.id.tvWrong);
        _tvMatching1 = (TextView) findViewById(R.id.tvMatching1);
        _tvMatching2 = (TextView) findViewById(R.id.tvMatching2);
        _tvMatching3 = (TextView) findViewById(R.id.tvMatching3);
        _tvMatching4 = (TextView) findViewById(R.id.tvMatching4);
        _tvMatching5 = (TextView) findViewById(R.id.tvMatching5);
        _tvMatching6 = (TextView) findViewById(R.id.tvMatching6);
        _tvSettings = (TextView) findViewById(R.id.tvSettings);
        _tvLetterA1 = (TextView) findViewById(R.id.tvLetterA1);
        _tvLetterA2 = (TextView) findViewById(R.id.tvLetterA2);
        _tvLetterA3 = (TextView) findViewById(R.id.tvLetterA3);
        _tvLetterA4 = (TextView) findViewById(R.id.tvLetterA4);
        _tvLetterA5 = (TextView) findViewById(R.id.tvLetterA5);
        _tvLetterA6 = (TextView) findViewById(R.id.tvLetterA6);
        _tvLetterB1 = (TextView) findViewById(R.id.tvLetterB1);
        _tvLetterB2 = (TextView) findViewById(R.id.tvLetterB2);
        _tvLetterB3 = (TextView) findViewById(R.id.tvLetterB3);
        _tvLetterB4 = (TextView) findViewById(R.id.tvLetterB4);
        _tvLetterB5 = (TextView) findViewById(R.id.tvLetterB5);
        _tvLetterB6 = (TextView) findViewById(R.id.tvLetterB6);
        _tvLetterC1 = (TextView) findViewById(R.id.tvLetterC1);
        _tvLetterC2 = (TextView) findViewById(R.id.tvLetterC2);
        _tvLetterC3 = (TextView) findViewById(R.id.tvLetterC3);
        _tvLetterC4 = (TextView) findViewById(R.id.tvLetterC4);
        _tvLetterC5 = (TextView) findViewById(R.id.tvLetterC5);
        _tvLetterC6 = (TextView) findViewById(R.id.tvLetterC6);
        _tvLetterD1 = (TextView) findViewById(R.id.tvLetterD1);
        _tvLetterD2 = (TextView) findViewById(R.id.tvLetterD2);
        _tvLetterD3 = (TextView) findViewById(R.id.tvLetterD3);
        _tvLetterD4 = (TextView) findViewById(R.id.tvLetterD4);
        _tvLetterD5 = (TextView) findViewById(R.id.tvLetterD5);
        _tvLetterD6 = (TextView) findViewById(R.id.tvLetterD6);
        _tvLetterE1 = (TextView) findViewById(R.id.tvLetterE1);
        _tvLetterE2 = (TextView) findViewById(R.id.tvLetterE2);
        _tvLetterE3 = (TextView) findViewById(R.id.tvLetterE3);
        _tvLetterE4 = (TextView) findViewById(R.id.tvLetterE4);
        _tvLetterE5 = (TextView) findViewById(R.id.tvLetterE5);
        _tvLetterE6 = (TextView) findViewById(R.id.tvLetterE6);
        _tvLetterF1 = (TextView) findViewById(R.id.tvLetterF1);
        _tvLetterF2 = (TextView) findViewById(R.id.tvLetterF2);
        _tvLetterF3 = (TextView) findViewById(R.id.tvLetterF3);
        _tvLetterF4 = (TextView) findViewById(R.id.tvLetterF4);
        _tvLetterF5 = (TextView) findViewById(R.id.tvLetterF5);
        _tvLetterF6 = (TextView) findViewById(R.id.tvLetterF6);

        _tvLetterA1.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterA2.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterA3.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterA4.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterA5.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterA6.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterB1.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterB2.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterB3.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterB4.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterB5.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterB6.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterC1.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterC2.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterC3.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterC4.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterC5.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterC6.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterD1.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterD2.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterD3.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterD4.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterD5.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterD6.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterE1.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterE2.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterE3.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterE4.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterE5.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterE6.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterF1.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterF2.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterF3.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterF4.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterF5.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvLetterF6.setOnTouchListener(_scaleAnimation.onTouchListener);
        _vGraphBackground = findViewById(R.id.vGraphBackground);
        _vGraphScale = findViewById(R.id.vGraphScale);

        _tvLetter.setTypeface(_typeFaceAnja);
        _tvLetter.setText(getIntent().getStringExtra("LETTER"));
        _tvLetterSound.setText(getIntent().getStringExtra("LETTERSOUND"));
        _tvRatioCorrect.setText("");

        _tvCorrect.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setScore(true);
            }
        });
        _tvWrong.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setScore(false);
            }
        });
        _tvStart = (TextView) findViewById(R.id.tvStart);
        _tvStart.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFlashCards();
            }
        });
        _tvStartText = (TextView) findViewById(R.id.tvStartText);
        _tvStartText.setVisibility(View.INVISIBLE);
        _incorrectImageNameIdList = new ArrayList<>();
        _correctImageNameIdList = new ArrayList<>();
        _approximateImageNameIdList = new ArrayList<>();
        _currentUserName = "";
        _currentImageName = "";
        _linearLayoutUserList = new ArrayList<>();

        _listOfSoundNamesForGame = new ArrayList<>();
        if(_soundName.equals("r") || _soundName.equals("s") || _soundName.equals("l")){

        } else {
            _listOfSoundNamesForGame.add(_soundName);
        }
        _ivBlendsIcon = (ImageView) findViewById(R.id.ivBlendsIcon);
        _rlBeginningOptions = (RelativeLayout) findViewById(R.id.rlBeginningOptions);
        _tvBlends = (TextView) findViewById(R.id.tvBlends);
        _llRBeginningOptions = (LinearLayout) findViewById(R.id.llRBeginningOptions);
        _llRMiddleOptions = (LinearLayout) findViewById(R.id.llRMiddleOptions);
        _llREndingOptions = (LinearLayout) findViewById(R.id.llREndingOptions);
        _llSBlendOptions = (LinearLayout) findViewById(R.id.llSBlendOptions);
        _llRBlendOptions = (LinearLayout) findViewById(R.id.llRBlendOptions);
        _llLBlendOptions = (LinearLayout) findViewById(R.id.llLBlendOptions);
        _ivBeginningArrow = (ImageView) findViewById(R.id.ivBeginningArrow);
        _ivMiddleArrow = (ImageView) findViewById(R.id.ivMiddleArrow);
        _ivEndingArrow = (ImageView) findViewById(R.id.ivEndingArrow);
        _ivBlendsArrow = (ImageView) findViewById(R.id.ivBlendsArrow);

        tvAIR = (TextView) findViewById(R.id.tvAIR);
        tvAR = (TextView) findViewById(R.id.tvAR);
        tvBR = (TextView) findViewById(R.id.tvBR);
        tvKR = (TextView) findViewById(R.id.tvKR);
        tvTR = (TextView) findViewById(R.id.tvTR);
        tvDR = (TextView) findViewById(R.id.tvDR);
        tvEAR = (TextView) findViewById(R.id.tvEAR);
        tvER = (TextView) findViewById(R.id.tvER);
        tvFR = (TextView) findViewById(R.id.tvFR);
        tvGR = (TextView) findViewById(R.id.tvGR);
        tvIRE = (TextView) findViewById(R.id.tvIRE);
        tvOR = (TextView) findViewById(R.id.tvOR);
        tvPR = (TextView) findViewById(R.id.tvPR);
        tvR = (TextView) findViewById(R.id.tvR);
        tvMiddleR = (TextView) findViewById(R.id.tvMiddleR);
        tvEndingR = (TextView) findViewById(R.id.tvEndingR);
        tvMiddleR.setVisibility(View.INVISIBLE);
        tvEndingR.setVisibility(View.INVISIBLE);
        tvMiddleAIR = (TextView) findViewById(R.id.tvMiddleAIR);
        tvMiddleAR = (TextView) findViewById(R.id.tvMiddleAR);
        tvMiddleEAR = (TextView) findViewById(R.id.tvMiddleEAR);
        tvMiddleER = (TextView) findViewById(R.id.tvMiddleER);
        tvMiddleIRE = (TextView) findViewById(R.id.tvMiddleIRE);
        tvMiddleOR = (TextView) findViewById(R.id.tvMiddleOR);
        tvMiddleRL = (TextView) findViewById(R.id.tvMiddleRL);
        tvEndingAIR = (TextView) findViewById(R.id.tvEndingAIR);
        tvEndingAR = (TextView) findViewById(R.id.tvEndingAR);
        tvEndingEAR = (TextView) findViewById(R.id.tvEndingEAR);
        tvEndingER = (TextView) findViewById(R.id.tvEndingER);
        tvEndingIRE = (TextView) findViewById(R.id.tvEndingIRE);
        tvEndingOR = (TextView) findViewById(R.id.tvEndingOR);
        tvEndingRL = (TextView) findViewById(R.id.tvEndingRL);
        tvBL = (TextView) findViewById(R.id.tvBL);
        tvFL = (TextView) findViewById(R.id.tvFL);
        tvKL = (TextView) findViewById(R.id.tvKL);
        tvGL = (TextView) findViewById(R.id.tvGL);
        tvPL = (TextView) findViewById(R.id.tvPL);
        tvLSL = (TextView) findViewById(R.id.tvLSL);
        tvSK = (TextView) findViewById(R.id.tvSK);
        tvSL = (TextView) findViewById(R.id.tvSL);
        tvSM = (TextView) findViewById(R.id.tvSM);
        tvSN = (TextView) findViewById(R.id.tvSN);
        tvSP = (TextView) findViewById(R.id.tvSP);
        tvST = (TextView) findViewById(R.id.tvST);
        tvSW = (TextView) findViewById(R.id.tvSW);
        tvKS = (TextView) findViewById(R.id.tvKS);
        tvNS = (TextView) findViewById(R.id.tvNS);
        tvPS = (TextView) findViewById(R.id.tvPS);
        tvTS = (TextView) findViewById(R.id.tvTS);
        tvAIRSelected = false;
        tvARSelected = false;
        tvBRSelected = false;
        tvKRSelected = false;
        tvTRSelected = false;
        tvDRSelected = false;
        tvEARSelected = false;
        tvERSelected = false;
        tvFRSelected = false;
        tvGRSelected = false;
        tvIRESelected = false;
        tvORSelected = false;
        tvPRSelected = false;
        tvRSelected = false;
        tvMiddleRSelected = false;
        tvEndingRSelected = false;
        tvMiddleAIRSelected = false;
        tvMiddleARSelected = false;
        tvMiddleEARSelected = false;
        tvMiddleERSelected = false;
        tvMiddleIRESelected = false;
        tvMiddleORSelected = false;
        tvMiddleRLSelected = false;
        tvEndingAIRSelected = false;
        tvEndingARSelected = false;
        tvEndingEARSelected = false;
        tvEndingERSelected = false;
        tvEndingIRESelected = false;
        tvEndingORSelected = false;
        tvEndingRLSelected = false;
        tvBLSelected = false;
        tvFLSelected = false;
        tvKLSelected = false;
        tvGLSelected = false;
        tvPLSelected = false;
        tvLSLSelected = false;
        tvSKSelected = false;
        tvSLSelected = false;
        tvSMSelected = false;
        tvSNSelected = false;
        tvSPSelected = false;
        tvSTSelected = false;
        tvSWSelected = false;
        tvKSSelected = false;
        tvNSSelected = false;
        tvPSSelected = false;
        tvTSSelected = false;
        ivBeginningArrow = (ImageView) findViewById(R.id.ivBeginningArrow);
        ivMiddleArrow = (ImageView) findViewById(R.id.ivMiddleArrow);
        ivEndingArrow = (ImageView) findViewById(R.id.ivEndingArrow);
        _llRBeginningOptions = (LinearLayout) findViewById(R.id.llRBeginningOptions);
        _llRMiddleOptions = (LinearLayout) findViewById(R.id.llRMiddleOptions);
        _llREndingOptions = (LinearLayout) findViewById(R.id.llREndingOptions);
        _llBeginning = (LinearLayout) findViewById(R.id.llBeginning);
        _llMiddle = (LinearLayout) findViewById(R.id.llMiddle);
        _llEnding = (LinearLayout) findViewById(R.id.llEnding);
        _llBlends = (LinearLayout) findViewById(R.id.llBlends);
        _llBeginning.setOnTouchListener(_scaleAnimation.onTouchListener);
        _llMiddle.setOnTouchListener(_scaleAnimation.onTouchListener);
        _llEnding.setOnTouchListener(_scaleAnimation.onTouchListener);
        _llBlends.setOnTouchListener(_scaleAnimation.onTouchListener);
        if(_soundName.equals("s") || _soundName.equals("r") || _soundName.equals("l")){
            _llBlends.setVisibility(View.VISIBLE);
            _ivBlendsArrow.setVisibility(View.VISIBLE);
            _ivBlendsArrow.setRotation(0);
        } else {
            _llBlends.setVisibility(View.GONE);
        }
        if(_soundName.equals("y")){
            _llBlends.setVisibility(View.INVISIBLE);
            _llMiddle.setVisibility(View.INVISIBLE);
            _llEnding.setVisibility(View.INVISIBLE);
        }
        if(_soundName.equals("r")){
            _ivBeginningArrow.setVisibility(View.VISIBLE);
            _ivBeginningArrow.setRotation(0);
            _ivMiddleArrow.setVisibility(View.VISIBLE);
            _ivMiddleArrow.setRotation(0);
            _ivEndingArrow.setVisibility(View.VISIBLE);
            _ivEndingArrow.setRotation(0);
        }
        _llBeginning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //selectStartOptions(1);
                if (_soundName.equals("r")) {
                    setupRSoundLayout(1);
                } else if (_soundName.equals("l")) {
                    setupLSoundLayout(1);
                } else if (_soundName.equals("s")) {
                    setupSSoundLayout(1);
                } else {
                    selectStartOptions(1);
                }
            }
        });
        _llMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //selectStartOptions(2);
                if (_soundName.equals("r")) {
                    setupRSoundLayout(2);
                } else if (_soundName.equals("l")) {
                    setupLSoundLayout(2);
                } else if (_soundName.equals("s")) {
                    setupSSoundLayout(2);
                } else {
                    selectStartOptions(2);
                }
            }
        });
        _llEnding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //selectStartOptions(3);
                if (_soundName.equals("r")) {
                    setupRSoundLayout(3);
                } else if (_soundName.equals("l")) {
                    setupLSoundLayout(3);
                } else if (_soundName.equals("s")) {
                    setupSSoundLayout(3);
                } else {
                    selectStartOptions(3);
                }
            }
        });
        _llBlends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_soundName.equals("r")) {
                    setupRSoundLayout(4);
                } else if (_soundName.equals("l")) {
                    setupLSoundLayout(4);
                } else if (_soundName.equals("s")) {
                    setupSSoundLayout(4);
                } else {
                    selectStartOptions(4);
                }
            }
        });
        if(!checkIfTablet()){
            _tvLetter.setTextSize(getTextSize(30));
            _tvLetterSound.setTextSize(getTextSize(15));
            _tvDone.setTextSize(getTextSize(13));
            _tvSettings.setTextSize(getTextSize(13));
            _tvMirror.setTextSize(getTextSize(13));
            _tvStart.setTextSize(getTextSize(35));
            _tvMatching1.setTextSize(getTextSize(12));
            _tvMatching2.setTextSize(getTextSize(12));
            _tvMatching3.setTextSize(getTextSize(12));
            _tvMatching4.setTextSize(getTextSize(12));
            _tvMatching5.setTextSize(getTextSize(12));
            _tvMatching6.setTextSize(getTextSize(12));
            _tvLetterA1.setTextSize(getTextSize(40));
            _tvLetterA2.setTextSize(getTextSize(40));
            _tvLetterA3.setTextSize(getTextSize(40));
            _tvLetterA4.setTextSize(getTextSize(40));
            _tvLetterA5.setTextSize(getTextSize(40));
            _tvLetterA6.setTextSize(getTextSize(40));
            _tvLetterB1.setTextSize(getTextSize(40));
            _tvLetterB2.setTextSize(getTextSize(40));
            _tvLetterB3.setTextSize(getTextSize(40));
            _tvLetterB4.setTextSize(getTextSize(40));
            _tvLetterB5.setTextSize(getTextSize(40));
            _tvLetterB6.setTextSize(getTextSize(40));
            _tvLetterC1.setTextSize(getTextSize(40));
            _tvLetterC2.setTextSize(getTextSize(40));
            _tvLetterC3.setTextSize(getTextSize(40));
            _tvLetterC4.setTextSize(getTextSize(40));
            _tvLetterC5.setTextSize(getTextSize(40));
            _tvLetterC6.setTextSize(getTextSize(40));
            _tvLetterD1.setTextSize(getTextSize(40));
            _tvLetterD2.setTextSize(getTextSize(40));
            _tvLetterD3.setTextSize(getTextSize(40));
            _tvLetterD4.setTextSize(getTextSize(40));
            _tvLetterD5.setTextSize(getTextSize(40));
            _tvLetterD6.setTextSize(getTextSize(40));
            _tvLetterE1.setTextSize(getTextSize(40));
            _tvLetterE2.setTextSize(getTextSize(40));
            _tvLetterE3.setTextSize(getTextSize(40));
            _tvLetterE4.setTextSize(getTextSize(40));
            _tvLetterE5.setTextSize(getTextSize(40));
            _tvLetterE6.setTextSize(getTextSize(40));
            _tvLetterF1.setTextSize(getTextSize(40));
            _tvLetterF2.setTextSize(getTextSize(40));
            _tvLetterF3.setTextSize(getTextSize(40));
            _tvLetterF4.setTextSize(getTextSize(40));
            _tvLetterF5.setTextSize(getTextSize(40));
            _tvLetterF6.setTextSize(getTextSize(40));
        }
    }

    public void openMirror(){
        if(_okayToOpenMirror & !_currentImageName.equals("")) {
            Intent myIntent = new Intent(WordFindActivity.this, MirrorActivity.class);
            myIntent.putExtra("IMAGENAME", _currentImageName);
            WordFindActivity.this.startActivity(myIntent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }
    }

    public void openFeedback(){
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_feedback);
        _decorView.setSystemUiVisibility(_uiOptions);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        tvTitle.setTypeface(_typeFaceAnja);
        TextView tvLoveFeedback = (TextView) dialog.findViewById(R.id.tvLoveFeedback);
        final EditText etFeedback = (EditText) dialog.findViewById(R.id.etFeedback);
        TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
        tvCancel.setTypeface(_typeFaceAnja);
        TextView tvSendFeedback = (TextView) dialog.findViewById(R.id.tvSendFeedback);
        tvSendFeedback.setTypeface(_typeFaceAnja);
        if(!checkIfTablet()) {
            tvTitle.setTextSize(getTextSize(30));
            tvLoveFeedback.setTextSize(getTextSize(20));
            etFeedback.setTextSize(getTextSize(14));
            tvCancel.setTextSize(getTextSize(22));
            tvSendFeedback.setTextSize(getTextSize(22));
        }
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback = etFeedback.getText().toString();
                if(feedback.isEmpty()){
                    Toast.makeText(WordFindActivity.this, "Please enter feedback", Toast.LENGTH_SHORT).show();
                } else {
                    sendFeedback(feedback);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public void sendFeedback(String feedback){
        String[] TO = {"josephdsmithjr@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        String activityName = this.getClass().getSimpleName();
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Speech Essentials: " + activityName + " Feedback!");
        emailIntent.putExtra(Intent.EXTRA_TEXT, feedback);
        Toast.makeText(WordFindActivity.this, "Thank you for your feedback!!", Toast.LENGTH_SHORT).show();
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(WordFindActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void setupSSoundLayout(int options){
        switch(options){
            case 1:
                switchSBeginningOnOff();
                break;
            case 2:
                switchSMiddleOnOff();
                break;
            case 3:
                switchSEndingOnOff();
                break;
            case 4:
                if(_llSBlendOptions.getVisibility() == View.VISIBLE){
                    _llSBlendOptions.setVisibility(View.GONE);
                    _ivBlendsArrow.setRotation(0);
                } else {
                    _llSBlendOptions.setVisibility(View.VISIBLE);
                    _ivBlendsArrow.setRotation(90);
                }
                switchSBlendsOnOff();
                break;
        }
        tvSK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvSKSelected) {
                    tvSKSelected = false;
                    tvSK.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvSK.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("sk_beginning");
                    _listOfSoundNamesForGame.remove("sk_middle");
                    _listOfSoundNamesForGame.remove("sk_ending");
                } else {
                    tvSKSelected = true;
                    tvSK.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvSK.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("sk_beginning");
                    _listOfSoundNamesForGame.add("sk_middle");
                    _listOfSoundNamesForGame.add("sk_ending");
                }
                switchSBlendsOnOff();
            }
        });
        tvSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvSLSelected) {
                    tvSLSelected = false;
                    tvSL.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvSL.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("sl_beginning");
                    _listOfSoundNamesForGame.remove("sl_middle");
                    _listOfSoundNamesForGame.remove("sl_ending");
                } else {
                    tvSLSelected = true;
                    tvSL.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvSL.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("sl_beginning");
                    _listOfSoundNamesForGame.add("sl_middle");
                    _listOfSoundNamesForGame.add("sl_ending");
                }
                switchSBlendsOnOff();
            }
        });
        tvSM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvSMSelected) {
                    tvSMSelected = false;
                    tvSM.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvSM.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("sm_beginning");
                    _listOfSoundNamesForGame.remove("sm_middle");
                    _listOfSoundNamesForGame.remove("sm_ending");
                } else {
                    tvSMSelected = true;
                    tvSM.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvSM.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("sm_beginning");
                    _listOfSoundNamesForGame.add("sm_middle");
                    _listOfSoundNamesForGame.add("sm_ending");
                }
                switchSBlendsOnOff();
            }
        });
        tvSN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvSNSelected) {
                    tvSNSelected = false;
                    tvSN.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvSN.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("sn_beginning");
                    _listOfSoundNamesForGame.remove("sn_middle");
                    _listOfSoundNamesForGame.remove("sn_ending");
                } else {
                    tvSNSelected = true;
                    tvSN.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvSN.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("sn_beginning");
                    _listOfSoundNamesForGame.add("sn_middle");
                    _listOfSoundNamesForGame.add("sn_ending");
                }
                switchSBlendsOnOff();
            }
        });
        tvSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvSPSelected) {
                    tvSPSelected = false;
                    tvSP.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvSP.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("sp_beginning");
                    _listOfSoundNamesForGame.remove("sp_middle");
                    _listOfSoundNamesForGame.remove("sp_ending");
                } else {
                    tvSPSelected = true;
                    tvSP.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvSP.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("sp_beginning");
                    _listOfSoundNamesForGame.add("sp_middle");
                    _listOfSoundNamesForGame.add("sp_ending");
                }
                switchSBlendsOnOff();
            }
        });
        tvST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvSTSelected) {
                    tvSTSelected = false;
                    tvST.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvST.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("st_beginning");
                    _listOfSoundNamesForGame.remove("st_middle");
                    _listOfSoundNamesForGame.remove("st_ending");
                } else {
                    tvSTSelected = true;
                    tvST.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvST.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("st_beginning");
                    _listOfSoundNamesForGame.add("st_middle");
                    _listOfSoundNamesForGame.add("st_ending");
                }
                switchSBlendsOnOff();
            }
        });
        tvSW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvSWSelected) {
                    tvSWSelected = false;
                    tvSW.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvSW.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("sw_beginning");
                    _listOfSoundNamesForGame.remove("sw_middle");
                    _listOfSoundNamesForGame.remove("sw_ending");
                } else {
                    tvSWSelected = true;
                    tvSW.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvSW.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("sw_beginning");
                    _listOfSoundNamesForGame.add("sw_middle");
                    _listOfSoundNamesForGame.add("sw_ending");
                }
                switchSBlendsOnOff();
            }
        });
        tvKS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvKSSelected) {
                    tvKSSelected = false;
                    tvKS.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvKS.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("ks_beginning");
                    _listOfSoundNamesForGame.remove("ks_middle");
                    _listOfSoundNamesForGame.remove("ks_ending");
                } else {
                    tvKSSelected = true;
                    tvKS.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvKS.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("ks_beginning");
                    _listOfSoundNamesForGame.add("ks_middle");
                    _listOfSoundNamesForGame.add("ks_ending");
                }
                switchSBlendsOnOff();
            }
        });
        tvNS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvNSSelected) {
                    tvNSSelected = false;
                    tvNS.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvNS.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("ns_beginning");
                    _listOfSoundNamesForGame.remove("ns_middle");
                    _listOfSoundNamesForGame.remove("ns_ending");
                } else {
                    tvNSSelected = true;
                    tvNS.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvNS.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("ns_beginning");
                    _listOfSoundNamesForGame.add("ns_middle");
                    _listOfSoundNamesForGame.add("ns_ending");
                }
                switchSBlendsOnOff();
            }
        });
        tvPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvPSSelected) {
                    tvPSSelected = false;
                    tvPS.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvPS.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("ps_beginning");
                    _listOfSoundNamesForGame.remove("ps_middle");
                    _listOfSoundNamesForGame.remove("ps_ending");
                } else {
                    tvPSSelected = true;
                    tvPS.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvPS.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("ps_beginning");
                    _listOfSoundNamesForGame.add("ps_middle");
                    _listOfSoundNamesForGame.add("ps_ending");
                }
                switchSBlendsOnOff();
            }
        });
        tvTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvTSSelected) {
                    tvTSSelected = false;
                    tvTS.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvTS.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("ts_beginning");
                    _listOfSoundNamesForGame.remove("ts_middle");
                    _listOfSoundNamesForGame.remove("ts_ending");
                } else {
                    tvTSSelected = true;
                    tvTS.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvTS.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("ts_beginning");
                    _listOfSoundNamesForGame.add("ts_middle");
                    _listOfSoundNamesForGame.add("ts_ending");
                }
                switchSBlendsOnOff();
            }
        });
    }

    public void switchSBeginningOnOff(){
        if(!_beginningSelected){
            _llBeginning.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
            _tvBeginning.setTextColor(Color.parseColor("#FFFFFF"));
            _ivBeginningArrow.setImageResource(R.drawable.white_caret);
            _ivBeginningIcon.setImageResource(R.drawable.checkmark);
            _listOfSoundNamesForGame.add("s_beginning");
            if(checkIfEnoughSoundsAreChecked()) {
                _llStart.setVisibility(View.VISIBLE);
            }
            _beginningSelected = true;
        } else {
            _llBeginning.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
            _tvBeginning.setTextColor(Color.parseColor("#82BBE6"));
            _ivBeginningArrow.setImageResource(R.drawable.blue_caret);
            _ivBeginningIcon.setImageResource(R.drawable.plus_icon);
            _beginningSelected = false;
            _listOfSoundNamesForGame.remove("s_beginning");
        }
        if(!checkIfAllSelected()){
            _llStart.setVisibility(View.INVISIBLE);
        } else {
            if(checkIfEnoughSoundsAreChecked()) {
                _llStart.setVisibility(View.VISIBLE);
            }
        }
    }

    public void switchSMiddleOnOff(){
        if(!_middleSelected){
            _llMiddle.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
            _tvMiddle.setTextColor(Color.parseColor("#FFFFFF"));
            _ivMiddleArrow.setImageResource(R.drawable.white_caret);
            _ivMiddleIcon.setImageResource(R.drawable.checkmark);
            _listOfSoundNamesForGame.add("s_middle");
            if(checkIfEnoughSoundsAreChecked()) {
                _llStart.setVisibility(View.VISIBLE);
            }
            _middleSelected = true;
        } else {
            _llMiddle.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
            _tvMiddle.setTextColor(Color.parseColor("#82BBE6"));
            _ivMiddleArrow.setImageResource(R.drawable.blue_caret);
            _ivMiddleIcon.setImageResource(R.drawable.plus_icon);
            _middleSelected = false;
            _listOfSoundNamesForGame.remove("s_middle");
        }
        if(!checkIfAllSelected()){
            _llStart.setVisibility(View.INVISIBLE);
        } else {
            if(checkIfEnoughSoundsAreChecked()) {
                _llStart.setVisibility(View.VISIBLE);
            }
        }
    }

    public void switchSEndingOnOff(){
        if(!_endingSelected){
            _llEnding.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
            _tvEnding.setTextColor(Color.parseColor("#FFFFFF"));
            _ivEndingArrow.setImageResource(R.drawable.white_caret);
            _ivEndingIcon.setImageResource(R.drawable.checkmark);
            _listOfSoundNamesForGame.add("s_ending");
            if(checkIfEnoughSoundsAreChecked()) {
                _llStart.setVisibility(View.VISIBLE);
            }
            _endingSelected = true;
        } else {
            _llEnding.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
            _tvEnding.setTextColor(Color.parseColor("#82BBE6"));
            _ivEndingArrow.setImageResource(R.drawable.blue_caret);
            _ivEndingIcon.setImageResource(R.drawable.plus_icon);
            _endingSelected = false;
            _listOfSoundNamesForGame.remove("s_ending");
        }
        if(!checkIfAllSelected()){
            _llStart.setVisibility(View.INVISIBLE);
        } else {
            if(checkIfEnoughSoundsAreChecked()) {
                _llStart.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setupRSoundLayout(int option){
        switch(option){
            case 1:
                if(_llRBeginningOptions.getVisibility() == View.VISIBLE){
                    _llRBeginningOptions.setVisibility(View.GONE);
                    _ivBeginningArrow.setRotation(0);
                } else {
                    _llRBeginningOptions.setVisibility(View.VISIBLE);
                    _ivBeginningArrow.setRotation(90);
                }
                break;
            case 2:
                if(_llRMiddleOptions.getVisibility() == View.VISIBLE){
                    _llRMiddleOptions.setVisibility(View.GONE);
                    _ivMiddleArrow.setRotation(0);
                } else {
                    _llRMiddleOptions.setVisibility(View.VISIBLE);
                    _ivMiddleArrow.setRotation(90);
                }
                break;
            case 3:
                if(_llREndingOptions.getVisibility() == View.VISIBLE){
                    _llREndingOptions.setVisibility(View.GONE);
                    _ivEndingArrow.setRotation(0);
                } else {
                    _llREndingOptions.setVisibility(View.VISIBLE);
                    _ivEndingArrow.setRotation(90);
                }
                break;
            case 4:
                if(_llRBlendOptions.getVisibility() == View.VISIBLE){
                    _llRBlendOptions.setVisibility(View.GONE);
                    _ivBlendsArrow.setRotation(0);
                } else {
                    _llRBlendOptions.setVisibility(View.VISIBLE);
                    _ivBlendsArrow.setRotation(90);
                }
                break;
        }
        tvBR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvBRSelected) {
                    tvBRSelected = false;
                    tvBR.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvBR.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("br_beginning");
                } else {
                    tvBRSelected = true;
                    tvBR.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvBR.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("br_beginning");
                }
                switchRBlendsOnOff();
            }
        });
        tvDR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvDRSelected) {
                    tvDRSelected = false;
                    tvDR.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvDR.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("dr_beginning");
                } else {
                    tvDRSelected = true;
                    tvDR.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvDR.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("dr_beginning");
                }
                switchRBlendsOnOff();
            }
        });
        tvFR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvFRSelected) {
                    tvFRSelected = false;
                    tvFR.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvFR.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("fr");
                } else {
                    tvFRSelected = true;
                    tvFR.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvFR.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("fr_beginning");
                }
                switchRBlendsOnOff();
            }
        });
        tvGR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvGRSelected) {
                    tvGRSelected = false;
                    tvGR.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvGR.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("gr_beginning");
                } else {
                    tvGRSelected = true;
                    tvGR.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvGR.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("gr_beginning");
                }
                switchRBlendsOnOff();
            }
        });
        tvKR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvKRSelected) {
                    tvKRSelected = false;
                    tvKR.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvKR.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("kr_beginning");
                } else {
                    tvKRSelected = true;
                    tvKR.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvKR.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("kr_beginning");
                }
                switchRBlendsOnOff();
            }
        });
        tvPR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvPRSelected) {
                    tvPRSelected = false;
                    tvPR.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvPR.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("pr_beginning");
                } else {
                    tvPRSelected = true;
                    tvPR.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvPR.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("pr_beginning");
                }
                switchRBlendsOnOff();
            }
        });
        tvR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvRSelected) {
                    tvRSelected = false;
                    tvR.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvR.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("r_beginning");
                } else {
                    tvRSelected = true;
                    tvR.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvR.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("r_beginning");
                }
                switchRBeginningOnOff();
            }
        });
        tvTR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvTRSelected) {
                    tvTRSelected = false;
                    tvTR.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvTR.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("tr_beginning");
                } else {
                    tvTRSelected = true;
                    tvTR.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvTR.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("tr_beginning");
                }
                switchRBlendsOnOff();
            }
        });
        tvAIR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvAIRSelected) {
                    tvAIRSelected = false;
                    tvAIR.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvAIR.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("air_beginning");
                } else {
                    tvAIRSelected = true;
                    tvAIR.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvAIR.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("air_beginning");
                }
                switchRBeginningOnOff();
            }
        });
        tvAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvARSelected) {
                    tvARSelected = false;
                    tvAR.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvAR.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("ar_beginning");
                } else {
                    tvARSelected = true;
                    tvAR.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvAR.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("ar_beginning");
                }
                switchRBeginningOnOff();
            }
        });
        tvEAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvEARSelected) {
                    tvEARSelected = false;
                    tvEAR.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvEAR.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("ear_beginning");
                } else {
                    tvEARSelected = true;
                    tvEAR.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvEAR.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("ear_beginning");
                }
                switchRBeginningOnOff();
            }
        });
        tvER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvERSelected) {
                    tvERSelected = false;
                    tvER.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvER.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("er_beginning");
                } else {
                    tvERSelected = true;
                    tvER.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvER.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("er_beginning");
                }
                switchRBeginningOnOff();
            }
        });
        tvIRE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvIRESelected) {
                    tvIRESelected = false;
                    tvIRE.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvIRE.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("ire_beginning");
                } else {
                    tvIRESelected = true;
                    tvIRE.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvIRE.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("ire_beginning");
                }
                switchRBeginningOnOff();
            }
        });
        tvOR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvORSelected) {
                    tvORSelected = false;
                    tvOR.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvOR.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("or_beginning");
                } else {
                    tvORSelected = true;
                    tvOR.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvOR.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("or_beginning");
                }
                switchRBeginningOnOff();
            }
        });
        tvMiddleAIR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvMiddleAIRSelected) {
                    tvMiddleAIRSelected = false;
                    tvMiddleAIR.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvMiddleAIR.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("air_middle");
                } else {
                    tvMiddleAIRSelected = true;
                    tvMiddleAIR.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvMiddleAIR.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("air_middle");
                }
                switchRMiddleOnOff();
            }
        });
        tvMiddleR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvMiddleRSelected) {
                    tvMiddleRSelected = false;
                    tvMiddleR.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvMiddleR.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("r_middle");
                } else {
                    tvMiddleRSelected = true;
                    tvMiddleR.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvMiddleR.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("r_middle");
                }
                switchRMiddleOnOff();
            }
        });
        tvMiddleAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvMiddleARSelected) {
                    tvMiddleARSelected = false;
                    tvMiddleAR.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvMiddleAR.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("ar_middle");
                } else {
                    tvMiddleARSelected = true;
                    tvMiddleAR.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvMiddleAR.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("ar_middle");
                }
                switchRMiddleOnOff();
            }
        });
        tvMiddleEAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvMiddleEARSelected) {
                    tvMiddleEARSelected = false;
                    tvMiddleEAR.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvMiddleEAR.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("ear_middle");
                } else {
                    tvMiddleEARSelected = true;
                    tvMiddleEAR.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvMiddleEAR.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("ear_middle");
                }
                switchRMiddleOnOff();
            }
        });
        tvMiddleER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvMiddleERSelected) {
                    tvMiddleERSelected = false;
                    tvMiddleER.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvMiddleER.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("er_middle");
                } else {
                    tvMiddleERSelected = true;
                    tvMiddleER.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvMiddleER.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("er_middle");
                }
                switchRMiddleOnOff();
            }
        });
        tvMiddleIRE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvMiddleIRESelected) {
                    tvMiddleIRESelected = false;
                    tvMiddleIRE.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvMiddleIRE.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("ire_middle");
                } else {
                    tvMiddleIRESelected = true;
                    tvMiddleIRE.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvMiddleIRE.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("ire_middle");
                }
                switchRMiddleOnOff();
            }
        });
        tvMiddleOR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvMiddleORSelected) {
                    tvMiddleORSelected = false;
                    tvMiddleOR.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvMiddleOR.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("or_middle");
                } else {
                    tvMiddleORSelected = true;
                    tvMiddleOR.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvMiddleOR.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("or_middle");
                }
                switchRMiddleOnOff();
            }
        });
        tvMiddleRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvMiddleRLSelected) {
                    tvMiddleRLSelected = false;
                    tvMiddleRL.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvMiddleRL.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("rl_ending");
                } else {
                    tvMiddleRLSelected = true;
                    tvMiddleRL.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvMiddleRL.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("rl_ending");
                }
                switchRMiddleOnOff();
            }
        });
        tvEndingAIR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvEndingAIRSelected) {
                    tvEndingAIRSelected = false;
                    tvEndingAIR.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvEndingAIR.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("air_ending");
                } else {
                    tvEndingAIRSelected = true;
                    tvEndingAIR.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvEndingAIR.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("air_ending");
                }
                switchREndingOnOff();
            }
        });
        tvEndingR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvEndingRSelected) {
                    tvEndingRSelected = false;
                    tvEndingR.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvEndingR.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("r_ending");
                } else {
                    tvEndingRSelected = true;
                    tvEndingR.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvEndingR.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("r_ending");
                }
                switchREndingOnOff();
            }
        });
        tvEndingAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvEndingARSelected) {
                    tvEndingARSelected = false;
                    tvEndingAR.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvEndingAR.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("ar_ending");
                } else {
                    tvEndingARSelected = true;
                    tvEndingAR.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvEndingAR.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("ar_ending");
                }
                switchREndingOnOff();
            }
        });
        tvEndingEAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvEndingEARSelected) {
                    tvEndingEARSelected = false;
                    tvEndingEAR.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvEndingEAR.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("ear_ending");
                } else {
                    tvEndingEARSelected = true;
                    tvEndingEAR.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvEndingEAR.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("ear_ending");
                }
                switchREndingOnOff();
            }
        });
        tvEndingER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvEndingERSelected) {
                    tvEndingERSelected = false;
                    tvEndingER.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvEndingER.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("er_ending");
                } else {
                    tvEndingERSelected = true;
                    tvEndingER.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvEndingER.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("er_ending");
                }
                switchREndingOnOff();
            }
        });
        tvEndingIRE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvEndingIRESelected) {
                    tvEndingIRESelected = false;
                    tvEndingIRE.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvEndingIRE.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("ire_ending");
                } else {
                    tvEndingIRESelected = true;
                    tvEndingIRE.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvEndingIRE.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("ire_ending");
                }
                switchREndingOnOff();
            }
        });
        tvEndingOR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvEndingORSelected) {
                    tvEndingORSelected = false;
                    tvEndingOR.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvEndingOR.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("or_ending");
                } else {
                    tvEndingORSelected = true;
                    tvEndingOR.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvEndingOR.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("or_ending");
                }
                switchREndingOnOff();
            }
        });
        tvEndingRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvEndingRLSelected) {
                    tvEndingRLSelected = false;
                    tvEndingRL.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvEndingRL.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("rl_ending");
                } else {
                    tvEndingRLSelected = true;
                    tvEndingRL.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvEndingRL.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("rl_ending");
                }
                switchREndingOnOff();
            }
        });
    }

    public void setupLSoundLayout(int options){
        switch(options){
            case 1:
                switchLBeginningOnOff();
                break;
            case 2:
                switchLMiddleOnOff();
                break;
            case 3:
                switchLEndingOnOff();
                break;
            case 4:
                if(_llLBlendOptions.getVisibility() == View.VISIBLE){
                    _llLBlendOptions.setVisibility(View.GONE);
                    _ivBlendsArrow.setRotation(0);
                } else {
                    _llLBlendOptions.setVisibility(View.VISIBLE);
                    _ivBlendsArrow.setRotation(90);
                }
                switchLBlendsOnOff();
                break;
        }
        tvBL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvBLSelected) {
                    tvBLSelected = false;
                    tvBL.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvBL.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("bl_beginning");
                    _listOfSoundNamesForGame.remove("bl_middle");
                    _listOfSoundNamesForGame.remove("bl_ending");
                } else {
                    tvBLSelected = true;
                    tvBL.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvBL.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("bl_beginning");
                    _listOfSoundNamesForGame.add("bl_middle");
                    _listOfSoundNamesForGame.add("bl_ending");
                }
                switchLBlendsOnOff();
            }
        });
        tvFL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvFLSelected) {
                    tvFLSelected = false;
                    tvFL.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvFL.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("fl_beginning");
                    _listOfSoundNamesForGame.remove("fl_middle");
                    _listOfSoundNamesForGame.remove("fl_ending");
                } else {
                    tvFLSelected = true;
                    tvFL.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvFL.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("fl_beginning");
                    _listOfSoundNamesForGame.add("fl_middle");
                    _listOfSoundNamesForGame.add("fl_ending");
                }
                switchLBlendsOnOff();
            }
        });
        tvGL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvGLSelected) {
                    tvGLSelected = false;
                    tvGL.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvGL.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("gl_beginning");
                    _listOfSoundNamesForGame.remove("gl_middle");
                    _listOfSoundNamesForGame.remove("gl_ending");
                } else {
                    tvGLSelected = true;
                    tvGL.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvGL.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("gl_beginning");
                    _listOfSoundNamesForGame.add("gl_middle");
                    _listOfSoundNamesForGame.add("gl_ending");
                }
                switchLBlendsOnOff();
            }
        });
        tvKL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvKLSelected) {
                    tvKLSelected = false;
                    tvKL.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvKL.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("kl_beginning");
                    _listOfSoundNamesForGame.remove("kl_middle");
                    _listOfSoundNamesForGame.remove("kl_ending");
                } else {
                    tvKLSelected = true;
                    tvKL.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvKL.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("kl_beginning");
                    _listOfSoundNamesForGame.add("kl_middle");
                    _listOfSoundNamesForGame.add("kl_ending");
                }
                switchLBlendsOnOff();
            }
        });
        tvPL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvPLSelected) {
                    tvPLSelected = false;
                    tvPL.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvPL.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("pl_beginning");
                    _listOfSoundNamesForGame.remove("pl_middle");
                    _listOfSoundNamesForGame.remove("pl_ending");
                } else {
                    tvPLSelected = true;
                    tvPL.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvPL.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("pl_beginning");
                    _listOfSoundNamesForGame.add("pl_middle");
                    _listOfSoundNamesForGame.add("pl_ending");
                }
                switchLBlendsOnOff();
            }
        });
        tvLSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvLSLSelected) {
                    tvLSLSelected = false;
                    tvLSL.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    tvLSL.setTextColor(Color.parseColor("#82BBE6"));
                    _listOfSoundNamesForGame.remove("sl_beginning");
                    _listOfSoundNamesForGame.remove("sl_middle");
                    _listOfSoundNamesForGame.remove("sl_ending");
                } else {
                    tvLSLSelected = true;
                    tvLSL.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    tvLSL.setTextColor(Color.parseColor("#FFFFFF"));
                    _listOfSoundNamesForGame.add("sl_beginning");
                    _listOfSoundNamesForGame.add("sl_middle");
                    _listOfSoundNamesForGame.add("sl_ending");
                }
                switchLBlendsOnOff();
            }
        });
    }

    public boolean checkIfEnoughSoundsAreChecked(){
        boolean enoughSoundsAreChecked = false;
        ArrayList<Integer> soundIds = _databaseOperationsHelper.getListOfImageIds(_databaseOperationsHelper, _listOfSoundNamesForGame, _soundPositionNameList);
        if(soundIds.size() >= 6){
            enoughSoundsAreChecked = true;
        }
        return enoughSoundsAreChecked;
    }

    public void switchRBeginningOnOff(){
        if(tvAIRSelected || tvARSelected || tvEARSelected || tvERSelected || tvIRESelected || tvORSelected || tvRSelected){
            _llBeginning.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
            _tvBeginning.setTextColor(Color.parseColor("#FFFFFF"));
            _ivBeginningArrow.setImageResource(R.drawable.white_caret);
            _ivBeginningIcon.setImageResource(R.drawable.checkmark);
            if(checkIfEnoughSoundsAreChecked()){
                _llStart.setVisibility(View.VISIBLE);
            }
        } else {
            _llBeginning.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
            _tvBeginning.setTextColor(Color.parseColor("#82BBE6"));
            _ivBeginningArrow.setImageResource(R.drawable.blue_caret);
            _ivBeginningIcon.setImageResource(R.drawable.plus_icon);
            if(!checkIfAllSelected()){
                _llStart.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void switchRMiddleOnOff(){
        if(tvMiddleAIRSelected || tvMiddleARSelected || tvMiddleEARSelected || tvMiddleERSelected || tvMiddleIRESelected || tvMiddleORSelected || tvMiddleRLSelected || tvMiddleRSelected){
            _llMiddle.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
            _tvMiddle.setTextColor(Color.parseColor("#FFFFFF"));
            _ivMiddleArrow.setImageResource(R.drawable.white_caret);
            _ivMiddleIcon.setImageResource(R.drawable.checkmark);
            if(checkIfEnoughSoundsAreChecked()){
                _llStart.setVisibility(View.VISIBLE);
            }
        } else {
            _llMiddle.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
            _tvMiddle.setTextColor(Color.parseColor("#82BBE6"));
            _ivMiddleArrow.setImageResource(R.drawable.blue_caret);
            _ivMiddleIcon.setImageResource(R.drawable.plus_icon);
            if(!checkIfAllSelected()){
                _llStart.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void switchREndingOnOff(){
        if(tvEndingAIRSelected || tvEndingARSelected || tvEndingEARSelected || tvEndingERSelected || tvEndingIRESelected || tvEndingORSelected || tvEndingRLSelected || tvEndingRSelected){
            _llEnding.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
            _tvEnding.setTextColor(Color.parseColor("#FFFFFF"));
            _ivEndingArrow.setImageResource(R.drawable.white_caret);
            _ivEndingIcon.setImageResource(R.drawable.checkmark);
            if(checkIfEnoughSoundsAreChecked()){
                _llStart.setVisibility(View.VISIBLE);
            }
        } else {
            _llEnding.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
            _tvEnding.setTextColor(Color.parseColor("#82BBE6"));
            _ivEndingArrow.setImageResource(R.drawable.blue_caret);
            _ivEndingIcon.setImageResource(R.drawable.plus_icon);
            if(!checkIfAllSelected()){
                _llStart.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void switchLBeginningOnOff(){
        if(!_beginningSelected){
            _llBeginning.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
            _tvBeginning.setTextColor(Color.parseColor("#FFFFFF"));
            _ivBeginningArrow.setImageResource(R.drawable.white_caret);
            _ivBeginningIcon.setImageResource(R.drawable.checkmark);
            if(checkIfEnoughSoundsAreChecked()){
                _llStart.setVisibility(View.VISIBLE);
            }
            _beginningSelected = true;
            _listOfSoundNamesForGame.add("l_beginning");
        } else {
            _llBeginning.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
            _tvBeginning.setTextColor(Color.parseColor("#82BBE6"));
            _ivBeginningArrow.setImageResource(R.drawable.blue_caret);
            _ivBeginningIcon.setImageResource(R.drawable.plus_icon);
            _beginningSelected = false;
            _listOfSoundNamesForGame.remove("l_beginning");
        }
        if(!checkIfAllSelected()){
            _llStart.setVisibility(View.INVISIBLE);
        } else {
            if(checkIfEnoughSoundsAreChecked()){
                _llStart.setVisibility(View.VISIBLE);
            }
        }
    }

    public void switchLMiddleOnOff(){
        if(!_middleSelected){
            _llMiddle.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
            _tvMiddle.setTextColor(Color.parseColor("#FFFFFF"));
            _ivMiddleArrow.setImageResource(R.drawable.white_caret);
            _ivMiddleIcon.setImageResource(R.drawable.checkmark);
            if(checkIfEnoughSoundsAreChecked()){
                _llStart.setVisibility(View.VISIBLE);
            }
            _middleSelected = true;
            _listOfSoundNamesForGame.add("l_middle");
        } else {
            _llMiddle.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
            _tvMiddle.setTextColor(Color.parseColor("#82BBE6"));
            _ivMiddleArrow.setImageResource(R.drawable.blue_caret);
            _ivMiddleIcon.setImageResource(R.drawable.plus_icon);
            _middleSelected = false;
            _listOfSoundNamesForGame.remove("l_middle");
        }
        if(!checkIfAllSelected()){
            _llStart.setVisibility(View.INVISIBLE);
        } else {
            if(checkIfEnoughSoundsAreChecked()){
                _llStart.setVisibility(View.VISIBLE);
            }
        }
    }

    public void switchLEndingOnOff(){
        if(!_endingSelected){
            _llEnding.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
            _tvEnding.setTextColor(Color.parseColor("#FFFFFF"));
            _ivEndingArrow.setImageResource(R.drawable.white_caret);
            _ivEndingIcon.setImageResource(R.drawable.checkmark);
            if(checkIfEnoughSoundsAreChecked()){
                _llStart.setVisibility(View.VISIBLE);
            }
            _endingSelected = true;
            _listOfSoundNamesForGame.add("l_ending");
        } else {
            _llEnding.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
            _tvEnding.setTextColor(Color.parseColor("#82BBE6"));
            _ivEndingArrow.setImageResource(R.drawable.blue_caret);
            _ivEndingIcon.setImageResource(R.drawable.plus_icon);
            _endingSelected = false;
            _listOfSoundNamesForGame.remove("l_ending");
        }
        if(!checkIfAllSelected()){
            _llStart.setVisibility(View.INVISIBLE);
        } else {
            if(checkIfEnoughSoundsAreChecked()){
                _llStart.setVisibility(View.VISIBLE);
            }
        }
    }

    public boolean checkIfAllSelected(){
        boolean allSelected = false;
        if(!_beginningSelected && !_middleSelected && !_endingSelected && !
                tvAIRSelected && !tvARSelected && !tvBRSelected && !tvKRSelected && !tvTRSelected && !tvDRSelected &&
                !tvEARSelected && !tvERSelected && !tvFRSelected && !tvGRSelected && !tvIRESelected && !tvORSelected &&
                !tvPRSelected && !tvMiddleAIRSelected && !tvMiddleARSelected && !tvMiddleEARSelected && !tvMiddleERSelected &&
                !tvRSelected && !tvMiddleRSelected && !tvEndingRSelected &&
                !tvMiddleIRESelected && !tvMiddleORSelected && !tvMiddleRLSelected && !tvEndingAIRSelected && !tvEndingARSelected &&
                !tvEndingEARSelected && !tvEndingERSelected && !tvEndingIRESelected && !tvEndingORSelected && !tvEndingRLSelected &&
                !tvBLSelected && !tvFLSelected && !tvKLSelected && !tvGLSelected && !tvPLSelected && !tvLSLSelected && !tvSKSelected &&
                !tvSLSelected && !tvSMSelected && !tvSNSelected && !tvSPSelected && !tvSTSelected && !tvSWSelected && !tvKSSelected &&
                !tvNSSelected && !tvPSSelected && !tvTSSelected){
        } else {
            allSelected = true;
        }
        return allSelected;
    }

    public void switchLBlendsOnOff(){
        if(tvBLSelected || tvFLSelected || tvGLSelected || tvKLSelected || tvPLSelected || tvLSLSelected){
            _llBlends.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
            _tvBlends.setTextColor(Color.parseColor("#FFFFFF"));
            _ivBlendsArrow.setImageResource(R.drawable.white_caret);
            _ivBlendsIcon.setImageResource(R.drawable.checkmark);
            if(checkIfEnoughSoundsAreChecked()){
                _llStart.setVisibility(View.VISIBLE);
            }
        } else {
            _llBlends.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
            _tvBlends.setTextColor(Color.parseColor("#82BBE6"));
            _ivBlendsArrow.setImageResource(R.drawable.blue_caret);
            _ivBlendsIcon.setImageResource(R.drawable.plus_icon);
            if(!checkIfAllSelected()){
                _llStart.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void switchRBlendsOnOff(){
        if(tvBRSelected || tvDRSelected || tvFRSelected || tvGRSelected || tvKRSelected || tvPRSelected ||tvTRSelected){
            _llBlends.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
            _tvBlends.setTextColor(Color.parseColor("#FFFFFF"));
            _ivBlendsArrow.setImageResource(R.drawable.white_caret);
            _ivBlendsIcon.setImageResource(R.drawable.checkmark);
            if(checkIfEnoughSoundsAreChecked()){
                _llStart.setVisibility(View.VISIBLE);
            }
        } else {
            _llBlends.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
            _tvBlends.setTextColor(Color.parseColor("#82BBE6"));
            _ivBlendsArrow.setImageResource(R.drawable.blue_caret);
            _ivBlendsIcon.setImageResource(R.drawable.plus_icon);
            if(!checkIfAllSelected()){
                _llStart.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void switchSBlendsOnOff(){
        if(tvSKSelected || tvSLSelected || tvSMSelected || tvSNSelected || tvSPSelected || tvSTSelected ||tvSWSelected || tvKSSelected || tvNSSelected || tvPSSelected || tvTSSelected){
            _llBlends.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
            _tvBlends.setTextColor(Color.parseColor("#FFFFFF"));
            _ivBlendsArrow.setImageResource(R.drawable.white_caret);
            _ivBlendsIcon.setImageResource(R.drawable.checkmark);
            if(checkIfEnoughSoundsAreChecked()){
                _llStart.setVisibility(View.VISIBLE);
            }
        } else {
            _llBlends.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
            _tvBlends.setTextColor(Color.parseColor("#82BBE6"));
            _ivBlendsArrow.setImageResource(R.drawable.blue_caret);
            _ivBlendsIcon.setImageResource(R.drawable.plus_icon);
            if(!checkIfAllSelected()){
                _llStart.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void selectStartOptions(int optionNumber){
        switch (optionNumber){
            case 1:
                if(!_beginningSelected) {
                    _llBeginning.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    _tvBeginning.setTextColor(Color.parseColor("#FFFFFF"));
                    _ivBeginningIcon.setImageResource(R.drawable.checkmark);
                    _soundPositionNameList.add("beginning");
                    _beginningSelected = true;
                } else {
                    _llBeginning.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    _tvBeginning.setTextColor(Color.parseColor("#82BBE6"));
                    _ivBeginningIcon.setImageResource(R.drawable.plus_icon);
                    _soundPositionNameList.remove("beginning");
                    _beginningSelected = false;
                }
                break;
            case 2:
                if(!_middleSelected) {
                    _llMiddle.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    _tvMiddle.setTextColor(Color.parseColor("#FFFFFF"));
                    _ivMiddleIcon.setImageResource(R.drawable.checkmark);
                    _soundPositionNameList.add("middle");
                    _middleSelected = true;
                } else {
                    _llMiddle.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    _tvMiddle.setTextColor(Color.parseColor("#82BBE6"));
                    _ivMiddleIcon.setImageResource(R.drawable.plus_icon);
                    _soundPositionNameList.remove("middle");
                    _middleSelected = false;
                }
                break;
            case 3:
                if(!_endingSelected) {
                    _llEnding.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
                    _tvEnding.setTextColor(Color.parseColor("#FFFFFF"));
                    _ivEndingIcon.setImageResource(R.drawable.checkmark);
                    _soundPositionNameList.add("ending");
                    _endingSelected = true;
                } else {
                    _llEnding.setBackgroundResource(R.drawable.rounded_corners_textbox_lightblue);
                    _tvEnding.setTextColor(Color.parseColor("#82BBE6"));
                    _ivEndingIcon.setImageResource(R.drawable.plus_icon);
                    _soundPositionNameList.remove("ending");
                    _endingSelected = false;
                }
                break;
        }
        if(!_beginningSelected && !_middleSelected && !_endingSelected && !
                tvAIRSelected && !tvARSelected && !tvBRSelected && !tvKRSelected && !tvTRSelected && !tvDRSelected && !tvEARSelected && !tvERSelected && !tvFRSelected && !tvGRSelected && !tvIRESelected && !tvORSelected && !tvPRSelected && !tvMiddleAIRSelected && !tvMiddleARSelected && !tvMiddleEARSelected && !tvMiddleERSelected && !tvMiddleIRESelected && !tvMiddleORSelected && !tvMiddleRLSelected && !tvEndingAIRSelected && !tvEndingARSelected && !tvEndingEARSelected && !tvEndingERSelected && !tvEndingIRESelected && !tvEndingORSelected && !tvEndingRLSelected &&
                !tvRSelected && !tvMiddleRSelected && !tvEndingRSelected &&
                !tvBLSelected && !tvFLSelected && !tvKLSelected && !tvGLSelected && !tvPLSelected && !tvLSLSelected &&
                !tvSKSelected && !tvSLSelected && !tvSMSelected && !tvSNSelected && !tvSPSelected && !tvSTSelected && !tvSWSelected && !tvKSSelected && !tvNSSelected && !tvPSSelected && !tvTSSelected){
            _tvStart.setOnTouchListener(null);
            _llStart.setVisibility(View.INVISIBLE);
        } else {
            //zh and ng do not have beginning sounds
            if(_soundName.equals("zh") || _soundName.equals("ng")){
                if(_middleSelected || _endingSelected){
                    _tvStart.setOnTouchListener(_scaleAnimation.onTouchListener);
                    if(checkIfEnoughSoundsAreChecked()){
                        _llStart.setVisibility(View.VISIBLE);
                    }
                } else {
                    _tvStart.setOnTouchListener(null);
                    _llStart.setVisibility(View.INVISIBLE);
                }
            } else {
                _tvStart.setOnTouchListener(_scaleAnimation.onTouchListener);
                if(checkIfEnoughSoundsAreChecked()){
                    _llStart.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void initializeDatabaseResources(){
        _scoreHelper = new ScoreHelper();
        _soundPositionNameList = new ArrayList<>();
        _soundImageIdList = new ArrayList<>();
        _imageWordList = new ArrayList<>();
        _soundWordTypeList = new ArrayList<>();
        _imageListPosition = 0;
    }

    public void populateSoundResources(){
        ArrayList<Integer> soundIds = _databaseOperationsHelper.getListOfWordFindImageIds(_databaseOperationsHelper, _listOfSoundNamesForGame, _soundPositionNameList);
        _soundImageIdList = getSixRandomIds(soundIds);
        _imageDrawableList = _databaseOperationsHelper.getListOfImages(this, _databaseOperationsHelper, _soundImageIdList);
        _imageWordList = _databaseOperationsHelper.getListOfImageNames(_databaseOperationsHelper, _soundImageIdList);
        _soundWordTypeList = _databaseOperationsHelper.getListOfWordTypes(_databaseOperationsHelper, _soundImageIdList);
        _imageListSize = _imageDrawableList.size();
    }

    public ArrayList<Integer> getSixRandomIds(ArrayList<Integer> listOfAllIds){
        ArrayList<Integer> twentyRandomIds = new ArrayList<>();
        ArrayList<Integer> randomIndexList = new ArrayList<>();
        Random random = new Random();
        int max = listOfAllIds.size() - 1;
        int min = 0;
        int iterationMax = 6;
        //generate 20 random numbers
        if(listOfAllIds.size() <= 6){
            iterationMax = listOfAllIds.size();
        }
        for(int i = 0; i < iterationMax; i++){
            int randomNumber = random.nextInt(max - min + 1) + min;
            while(randomIndexList.contains(randomNumber)){
                randomNumber = random.nextInt(max - min + 1) + min;
            }
            randomIndexList.add(randomNumber);
            twentyRandomIds.add(listOfAllIds.get(randomNumber));
        }
        return twentyRandomIds;
    }

    public void populateWordListWords(){
        _tvMatching1.setText(_imageWordList.get(0).toLowerCase());
        _tvMatching2.setText(_imageWordList.get(1).toLowerCase());
        _tvMatching3.setText(_imageWordList.get(2).toLowerCase());
        _tvMatching4.setText(_imageWordList.get(3).toLowerCase());
        _tvMatching5.setText(_imageWordList.get(4).toLowerCase());
        _tvMatching6.setText(_imageWordList.get(5).toLowerCase());
        _soundDrawableMap = new HashMap<>();
        _soundDrawableMap.put(_imageWordList.get(0), _imageDrawableList.get(0));
        _soundDrawableMap.put(_imageWordList.get(1), _imageDrawableList.get(1));
        _soundDrawableMap.put(_imageWordList.get(2), _imageDrawableList.get(2));
        _soundDrawableMap.put(_imageWordList.get(3), _imageDrawableList.get(3));
        _soundDrawableMap.put(_imageWordList.get(4), _imageDrawableList.get(4));
        _soundDrawableMap.put(_imageWordList.get(5), _imageDrawableList.get(5));
        _soundWordTypeMap = new HashMap<>();
        _soundWordTypeMap.put(_imageWordList.get(0), _soundWordTypeList.get(0));
        _soundWordTypeMap.put(_imageWordList.get(1), _soundWordTypeList.get(1));
        _soundWordTypeMap.put(_imageWordList.get(2), _soundWordTypeList.get(2));
        _soundWordTypeMap.put(_imageWordList.get(3), _soundWordTypeList.get(3));
        _soundWordTypeMap.put(_imageWordList.get(4), _soundWordTypeList.get(4));
        _soundWordTypeMap.put(_imageWordList.get(5), _soundWordTypeList.get(5));
        _wordColorsMap = new HashMap<>();
        _wordColorsMap.put(1, "#FF0000");
        _wordColorsMap.put(2, "#F47D20");
        _wordColorsMap.put(3, "#FBB812");
        _wordColorsMap.put(4, "#04A650");
        _wordColorsMap.put(5, "#2B6DB9");
        _wordColorsMap.put(6, "#85357C");
        _wordToColorNumberMap = new HashMap<>();
        _wordToColorNumberMap.put(_imageWordList.get(0),1);
        _wordToColorNumberMap.put(_imageWordList.get(1),2);
        _wordToColorNumberMap.put(_imageWordList.get(2),3);
        _wordToColorNumberMap.put(_imageWordList.get(3),4);
        _wordToColorNumberMap.put(_imageWordList.get(4),5);
        _wordToColorNumberMap.put(_imageWordList.get(5),6);
    }

    //1 Get list of 6 words no longer than 6 characters long
    //2 Decide how many V / H (5:1)
    //3 Sort by size of words desc
    //4 If # of (words.size = 6) > 2, all same orientation
    //5 If word.size = 6, Row 1,6 or Column A,F
    //6 Check 1st word column/row
    //7 Choose Row/Column
    //8 Based on word.size, choose starting position within Row/Column
    //9 Fill Word, eliminate spaces
    //10 When done, iterate through remaining open spaces and assign random letters that are not the sound
    public void setUpWordFind(){
        //1 Get list of 6 words no longer than 6 characters long
        populateSoundResources();
        populateWordListWords();
        //2 Decide how many V / H (5:1)
        setVerticalHorizontalRatios();
        ArrayList<String> columnList = getColumnList();
        ArrayList<Integer> rowList = getRowList();
        _wordFindSpacesList = getWordFindSpacesList();
        populateWordFindTextViewList();
        //3 Sort by size of words desc
        //4 If # of (words.size = 6) > 2, all same orientation
        setWordsToTextViewMap();
        changeLeftOverLettersToRandomLetters();
        //5 If word.size = 6, Row 1,6 or Column A,F
        //6 Check 1st word column/row
        //7 Choose Row/Column
        //8 Based on word.size, choose starting position within Row/Column
        //9 Fill Word, eliminate spaces
        //10 When done, iterate through remaining open spaces and assign random letters that are not the sound

    }

    public void openScoresDialog(final boolean restart) {
        if(_totalScore > 0){
            final Dialog dialog = new Dialog(this);
            dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.activity_savescores_dialog);
            _decorView.setSystemUiVisibility(_uiOptions);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setAttributes(lp);
            LinearLayout llParentLayout = (LinearLayout) dialog.findViewById(R.id.llParentLayout);
            addUserNamesLayouts(dialog, llParentLayout);
            TextView tvScoreRatio = (TextView) dialog.findViewById(R.id.tvScoreRatio);
            TextView tvScorePercent = (TextView) dialog.findViewById(R.id.tvScorePercent);
            final TextView tvSave = (TextView) dialog.findViewById(R.id.tvTryAgain);
            TextView tvDontSave = (TextView) dialog.findViewById(R.id.tvQuit);
            tvSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!_currentUserName.equals("")) {
                        if (saveScore(_currentUserName)) {
                            Toast.makeText(WordFindActivity.this, "Score Added!", Toast.LENGTH_SHORT);
                            if(!restart){
                                goToMenu();
                            } else {
                                Intent myIntent = new Intent(WordFindActivity.this, WordFindActivity.class);
                                myIntent.putExtra("LETTER", _soundName);
                                myIntent.putExtra("LETTERSOUND", _soundName);
                                WordFindActivity.this.startActivity(myIntent);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                finish();
                            }
                        } else {
                            Toast.makeText(WordFindActivity.this, "Score was not added :(", Toast.LENGTH_SHORT);
                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(WordFindActivity.this);
                        builder.setTitle("No User Selected");
                        builder.setMessage("Please choose a username");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
            });
            tvSave.setOnTouchListener(_scaleAnimation.onTouchListener);
            tvDontSave.setOnTouchListener(_scaleAnimation.onTouchListener);
            tvDontSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToMenu();
                }
            });
            tvScoreRatio.setText(_totalCorrect + "/" + _totalScore);
            tvScorePercent.setText(" (" + _percentCorrect + "%)");
            TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
            tvTitle.setTypeface(_typeFaceAnja);
            if(!checkIfTablet()){
                tvTitle.setTextSize(getTextSize(30));
                tvScoreRatio.setTextSize(getTextSize(35));
                tvScorePercent.setTextSize(getTextSize(35));
                tvDontSave.setTextSize(getTextSize(30));
                tvSave.setTextSize(getTextSize(30));
            }
            View v1 = (View) dialog.findViewById(R.id.v1);
            v1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            View v2 = (View) dialog.findViewById(R.id.v2);
            v2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            View v3 = (View) dialog.findViewById(R.id.v3);
            v3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            View v4 = (View) dialog.findViewById(R.id.v4);
            v4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else {
            if(!restart){
                goToMenu();
            } else {
                Intent myIntent = new Intent(WordFindActivity.this, WordFindActivity.class);
                myIntent.putExtra("LETTER", _soundName);
                myIntent.putExtra("LETTERSOUND", _soundName);
                WordFindActivity.this.startActivity(myIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();
            }
        }
    }

    public boolean saveScore(String username){
        return _scoreHelper.saveScore(this, username, _correctImageNameIdList, _approximateImageNameIdList, _incorrectImageNameIdList, "find");
    }

    public LinearLayout createLinearLayoutUser(Dialog dialog, boolean isSelected, boolean isAddName, String username){
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
        if(!checkIfTablet()) {
            textView.setTextSize(getTextSize(35));
        } else {
            textView.setTextSize(35);
        }
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

    public void addUserNamesLayouts(final Dialog dialog, final LinearLayout parentLinearLayout){
        _usernameTextViewList = new ArrayList<>();
        ArrayList<String> currentUsers = _databaseOperationsHelper.getListOfUsers(_databaseOperationsHelper);
        parentLinearLayout.removeAllViews();
        LinearLayout userLayout = null;
        if(currentUsers.size() == 0){
            userLayout = createLinearLayoutUser(dialog, false, true, "add name");
            userLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openAddUserDialog(dialog, parentLinearLayout);
                }
            });
            parentLinearLayout.addView(userLayout);
            _linearLayoutUserList.add(userLayout);
        } else {
            for(int i = 0; i <= currentUsers.size(); i++){
                if(i == 0){
                    _currentUserName = currentUsers.get(i);
                    userLayout = createLinearLayoutUser(dialog, true, false, currentUsers.get(i));
                    TextView textView = getTextViewFromLinearLayout(userLayout);
                    _usernameTextViewList.add(textView);
                    userLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            handleUserNameSelected((LinearLayout) v);
                        }
                    });
                    parentLinearLayout.addView(userLayout);
                    View view1 = new View(dialog.getContext());
                    float scale = dialog.getContext().getResources().getDisplayMetrics().density;
                    int linearLayoutHeight = (int) (5 * scale + 0.5f);
                    LinearLayout.LayoutParams viewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, linearLayoutHeight);
                    view1.setLayoutParams(viewLayoutParams);
                    parentLinearLayout.addView(view1);
                    _linearLayoutUserList.add(userLayout);
                } else if(i == currentUsers.size()){
                    userLayout = createLinearLayoutUser(dialog, false, true, "add name");
                    userLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openAddUserDialog(dialog, parentLinearLayout);
                        }
                    });
                    parentLinearLayout.addView(userLayout);
                    _linearLayoutUserList.add(userLayout);
                } else {
                    userLayout = createLinearLayoutUser(dialog, false, false, currentUsers.get(i));
                    TextView textView = getTextViewFromLinearLayout(userLayout);
                    _usernameTextViewList.add(textView);
                    userLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            handleUserNameSelected((LinearLayout) v);
                        }
                    });
                    parentLinearLayout.addView(userLayout);
                    View view1 = new View(dialog.getContext());
                    float scale = dialog.getContext().getResources().getDisplayMetrics().density;
                    int linearLayoutHeight = (int) (5 * scale + 0.5f);
                    LinearLayout.LayoutParams viewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, linearLayoutHeight);
                    view1.setLayoutParams(viewLayoutParams);
                    parentLinearLayout.addView(view1);
                    _linearLayoutUserList.add(userLayout);
                }
            }
        }

        //TODO: ADD layout to Parentlayout next!
        //1 Get list of all users
        //2 Create linearlayout as above for each user, highlighting the first user
        //3 add onClickListener pointed to handleUserNameSelected(textView) for each layout
        //4 Set the _currentUserName to the first user
        //5 add textView to _usernameTextViewList
    }

    public TextView getTextViewFromLinearLayout(LinearLayout linearLayout){
        TextView textView = null;
        int childcount = linearLayout.getChildCount();
        for (int j=0; j < childcount; j++){
            View v = linearLayout.getChildAt(j);
            if(v instanceof TextView){
                textView = (TextView) v;
            }
        }
        return textView;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        // onDateSet method
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date_selected = String.valueOf(monthOfYear+1)+"/"+String.valueOf(dayOfMonth)+"/"+String.valueOf(year);
            //Toast.makeText(getApplicationContext(), "Selected Date is ="+date_selected, Toast.LENGTH_SHORT).show();
            //dob.setText(date_selected);
            _etBirthday.setText(date_selected);
        }
    };

    public void openDatePicker(){
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        int cday = c.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog dialog = new DatePickerDialog(this, mDateSetListener, cyear, cmonth, cday);
//        dialog.getDatePicker().setSpinnersShown(true);
        dialog.getDatePicker().setCalendarViewShown(false);
//        final DatePickerDialog dialog = new DatePickerDialog(this, R.style.Base_Theme_AppCompat, mDateSetListener, cyear, cmonth, cday);
        //final DatePickerDialog dialog = new DatePickerDialog(this, R.style.AppTheme, mDateSetListener, cyear, cmonth, cday);
        //final DatePickerDialog dialog = new DatePickerDialog(this, R.style.AppTheme, mDateSetListener, cyear, cmonth, cday);
        dialog.show();
    }

    public void openAddUserDialog(final Dialog parentDialog, final LinearLayout parentLinearLayout) {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_adduser_dialog);
        _decorView.setSystemUiVisibility(_uiOptions);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //dialog.getWindow().setLayout((int) _dpWidth, (int) (_dpHeight / 2));
        dialog.getWindow().setLayout(_screenWidth, _screenHeight / 2);

        View v1 = (View) dialog.findViewById(R.id.v1);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v2 = (View) dialog.findViewById(R.id.v2);
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v3 = (View) dialog.findViewById(R.id.v3);
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final EditText etUserName = (EditText) dialog.findViewById(R.id.etUserName);
        etUserName.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        final EditText etUserEmail = (EditText) dialog.findViewById(R.id.etUserEmail);
        etUserEmail.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        _etBirthday = (EditText) dialog.findViewById(R.id.etBirthday);
        _etBirthday.setInputType(InputType.TYPE_NULL);
        _etBirthday.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        _etBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });
        final RadioGroup radioGrp = (RadioGroup) dialog.findViewById(R.id.radioGrp);
        final LinearLayout llBoyGirlImage = (LinearLayout) dialog.findViewById(R.id.llBoyGirlImage);
        radioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioM){
                    llBoyGirlImage.setBackgroundResource(R.drawable.boy_avatar);
                } else {
                    llBoyGirlImage.setBackgroundResource(R.drawable.girl_avatar);
                }
            }
        });
        final TextView tvSave = (TextView) dialog.findViewById(R.id.tvTryAgain);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUserName.getText().toString().equals("")) {
                    Toast.makeText(WordFindActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                } else {
                    String gender = "male";
                    switch (radioGrp.getCheckedRadioButtonId()) {
                        case R.id.radioM:
                            gender = "male";
                            break;
                        case R.id.radioF:
                            gender = "female";
                            break;
                    }
                    boolean addedNewUser = saveUser(etUserName.getText().toString(), etUserEmail.getText().toString().trim(), _etBirthday.getText().toString().trim(), gender);
                    if (addedNewUser) {
                        Toast.makeText(WordFindActivity.this, etUserName.getText().toString() + " added as a new user!", Toast.LENGTH_SHORT).show();
                        addUserNamesLayouts(parentDialog, parentLinearLayout);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(WordFindActivity.this, etUserName.getText().toString() + " already exists, updated user info.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            }
        });
        tvSave.setOnTouchListener(_scaleAnimation.onTouchListener);
        dialog.show();
    }

    public boolean saveUser(String username, String userEmail, String userBirthday, String userGender){
        boolean addedNewUser = _databaseOperationsHelper.addUser(_databaseOperationsHelper, username, userEmail, userGender, userBirthday);
        return addedNewUser;
    }

    public void handleUserNameSelected(LinearLayout linearLayout){
        for(int i = 0; i < _usernameTextViewList.size(); i++){
            LinearLayout linLayout = (LinearLayout) _usernameTextViewList.get(i).getParent();
            linLayout.setBackgroundResource(R.drawable.scoreslightblue_box);
            int childcount = linLayout.getChildCount();
            ImageView imageView = null;
            for (int j=0; j < childcount; j++){
                View v = linLayout.getChildAt(j);
                if(v instanceof ImageView){
                    imageView = (ImageView) v;
                }
            }
            imageView.setImageResource(R.drawable.plussign_darkblue);
            _usernameTextViewList.get(i).setTextColor(Color.parseColor("#2B6DB9"));
        }
        int childcount = linearLayout.getChildCount();
        TextView textView = null;
        ImageView imageView = null;
        for (int j=0; j < childcount; j++){
            View v = linearLayout.getChildAt(j);
            if(v instanceof TextView){
                textView = (TextView) v;
            }
            if(v instanceof ImageView){
                imageView = (ImageView) v;
            }
        }
        _currentUserName = textView.getText().toString();
        linearLayout.setBackgroundResource(R.drawable.blue_box);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        imageView.setImageResource(R.drawable.checkmark);
    }

    public void setWordsToTextViewMap(){
        _letterToTextViewMap = new HashMap<>();
        //_imageWordList
        //1 get size of word
        //2 get potential starting points
        //3 choose randomly the starting point
        //4 build list of textViews
        //A 1-6
        //B 7-12
        //C 13-18
        //D 19-24
        //E 25-30
        //F 31-36
        switch (_numberOfVerticalSpaces){
            case 0:
                //ALL HORIZONTAL
                assignWordToColumnOrRowTextViews(_imageWordList.get(0), 0, 1, true);
                assignWordToColumnOrRowTextViews(_imageWordList.get(1), 0, 2, true);
                assignWordToColumnOrRowTextViews(_imageWordList.get(2), 0, 3, true);
                assignWordToColumnOrRowTextViews(_imageWordList.get(3), 0, 4, true);
                assignWordToColumnOrRowTextViews(_imageWordList.get(4), 0, 5, true);
                assignWordToColumnOrRowTextViews(_imageWordList.get(5), 0, 6, true);
                break;
            case 1:
                //HORIZONTAL 5:1
                if(_longWordGoesInFirstSpot){
                    int rowNumber = 1;
                    for(int i = 0; i < _imageWordList.size(); i++){
                        if(_imageWordList.get(i).length() == 6){
                            assignWordToColumnOrRowTextViews(_imageWordList.get(i), 0, 1, true);
                        } else {
                            rowNumber++;
                            assignWordToColumnOrRowTextViews(_imageWordList.get(i), 1, rowNumber, false);
                        }
                    }
                } else {
                    int rowNumber = 0;
                    for(int i = 0; i < _imageWordList.size(); i++){
                        if(_imageWordList.get(i).length() == 6){
                            assignWordToColumnOrRowTextViews(_imageWordList.get(i), 0, 6, false);
                        } else {
                            rowNumber++;
                            assignWordToColumnOrRowTextViews(_imageWordList.get(i), 6, rowNumber, true);
                        }
                    }
                }
                break;
            case 5:
                //VERTICAL 5:1
                if(_longWordGoesInFirstSpot){
                    int columnNumber = 1;
                    if(_atLeastOneWordHasSixLetters){
                        for(int i = 0; i < _imageWordList.size(); i++){
                            if(_imageWordList.get(i).length() == 6){
                                assignWordToColumnOrRowTextViews(_imageWordList.get(i), 0, 1, false);
                            } else {
                                columnNumber++;
                                assignWordToColumnOrRowTextViews(_imageWordList.get(i), 1, columnNumber, true);
                            }
                        }
                    } else {
                        for(int i = 0; i < _imageWordList.size(); i++){
                            if(i == 3){
                                assignWordToColumnOrRowTextViews(_imageWordList.get(i), 0, 1, false);
                            } else {
                                columnNumber++;
                                assignWordToColumnOrRowTextViews(_imageWordList.get(i), 1, columnNumber, true);
                            }
                        }
                    }

                } else {
                    int columnNumber = 0;
                    if(_atLeastOneWordHasSixLetters){
                        for(int i = 0; i < _imageWordList.size(); i++){
                            if(_imageWordList.get(i).length() == 6){
                                assignWordToColumnOrRowTextViews(_imageWordList.get(i), 0, 6, true);
                            } else {
                                columnNumber++;
                                assignWordToColumnOrRowTextViews(_imageWordList.get(i), 6, columnNumber, false);
                            }
                        }
                    } else {
                        for(int i = 0; i < _imageWordList.size(); i++){
                            if(i == 3){
                                assignWordToColumnOrRowTextViews(_imageWordList.get(i), 0, 6, true);
                            } else {
                                columnNumber++;
                                assignWordToColumnOrRowTextViews(_imageWordList.get(i), 6, columnNumber, false);
                            }
                        }
                    }
                }
                break;
            case 6:
                //ALL VERTICAL
                assignWordToColumnOrRowTextViews(_imageWordList.get(0), 0, 1, false);
                assignWordToColumnOrRowTextViews(_imageWordList.get(1), 0, 2, false);
                assignWordToColumnOrRowTextViews(_imageWordList.get(2), 0, 3, false);
                assignWordToColumnOrRowTextViews(_imageWordList.get(3), 0, 4, false);
                assignWordToColumnOrRowTextViews(_imageWordList.get(4), 0, 5, false);
                assignWordToColumnOrRowTextViews(_imageWordList.get(5), 0, 6, false);
                break;
        }
    }

    public void assignWordToColumnOrRowTextViews(String word, int rowOrColumnNotAvail, int rowOrColumnToPutWord, boolean isARow){
        int wordSize = word.length();
        String[] brokenUpWord = word.split("");
        int startingPoint = chooseStartingPoint(rowOrColumnNotAvail, wordSize);
        TextView textView;
        ArrayList<Integer> listOfTextViewSpaces = null;
        ArrayList<TextView> listOfFilledTextViews = new ArrayList<>();
        if(isARow){
            //Dealing with rows
            switch (rowOrColumnToPutWord){
                case 1:
                    listOfTextViewSpaces = getListOfTextViewSpaces(1, 7, 13, 19, 25, 31);
                    textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint-1));
                    _letterToTextViewMap.put(brokenUpWord[1], textView);
                    listOfFilledTextViews.add(textView);
                    //bagle
                    //7:  b
                    //13: a
                    //19: g
                    //25: l
                    //31: e
                    for(int i = 2; i < brokenUpWord.length; i++){
                        textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint));
                        _letterToTextViewMap.put(brokenUpWord[i], textView);
                        listOfFilledTextViews.add(textView);
                        startingPoint++;
                    }
                    break;
                case 2:
                    listOfTextViewSpaces = getListOfTextViewSpaces(2, 8, 14, 20, 26, 32);
                    textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint-1));
                    _letterToTextViewMap.put(brokenUpWord[1], textView);
                    listOfFilledTextViews.add(textView);
                    for(int i = 2; i < brokenUpWord.length; i++){
                        textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint));
                        _letterToTextViewMap.put(brokenUpWord[i], textView);
                        listOfFilledTextViews.add(textView);
                        startingPoint++;
                    }
                    break;
                case 3:
                    listOfTextViewSpaces = getListOfTextViewSpaces(3,9,15,21,27,33);
                    textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint-1));
                    _letterToTextViewMap.put(brokenUpWord[1], textView);
                    listOfFilledTextViews.add(textView);
                    for(int i = 2; i < brokenUpWord.length; i++){
                        textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint));
                        _letterToTextViewMap.put(brokenUpWord[i], textView);
                        listOfFilledTextViews.add(textView);
                        startingPoint++;
                    }
                    break;
                case 4:
                    listOfTextViewSpaces = getListOfTextViewSpaces(4, 10, 16, 22, 28, 34);
                    textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint-1));
                    _letterToTextViewMap.put(brokenUpWord[1], textView);
                    listOfFilledTextViews.add(textView);
                    for(int i = 2; i < brokenUpWord.length; i++){
                        textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint));
                        _letterToTextViewMap.put(brokenUpWord[i], textView);
                        listOfFilledTextViews.add(textView);
                        startingPoint++;
                    }
                    break;
                case 5:
                    listOfTextViewSpaces = getListOfTextViewSpaces(5, 11, 17, 23, 29, 35);
                    textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint-1));
                    _letterToTextViewMap.put(brokenUpWord[1], textView);
                    listOfFilledTextViews.add(textView);
                    for(int i = 2; i < brokenUpWord.length; i++){
                        textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint));
                        _letterToTextViewMap.put(brokenUpWord[i], textView);
                        listOfFilledTextViews.add(textView);
                        startingPoint++;
                    }
                    break;
                case 6:
                    listOfTextViewSpaces = getListOfTextViewSpaces(6,12,18,24,30,36);
                    textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint-1));
                    _letterToTextViewMap.put(brokenUpWord[1], textView);
                    listOfFilledTextViews.add(textView);
                    for(int i = 2; i < brokenUpWord.length; i++){
                        textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint));
                        _letterToTextViewMap.put(brokenUpWord[i], textView);
                        listOfFilledTextViews.add(textView);
                        startingPoint++;
                    }
                    break;
            }
        } else {
            //Dealing with columns
            switch (rowOrColumnToPutWord){
                case 1:
                    listOfTextViewSpaces = getListOfTextViewSpaces(1, 2, 3, 4, 5, 6);
                    textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint-1));
                    _letterToTextViewMap.put(brokenUpWord[1], textView);
                    listOfFilledTextViews.add(textView);
                    for(int i = 2; i < brokenUpWord.length; i++){
                        textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint));
                        _letterToTextViewMap.put(brokenUpWord[i], textView);
                        listOfFilledTextViews.add(textView);
                        startingPoint++;
                    }
                    break;
                case 2:
                    listOfTextViewSpaces = getListOfTextViewSpaces(7, 8, 9, 10, 11, 12);
                    textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint-1));
                    _letterToTextViewMap.put(brokenUpWord[1], textView);
                    listOfFilledTextViews.add(textView);
                    for(int i = 2; i < brokenUpWord.length; i++){
                        textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint));
                        _letterToTextViewMap.put(brokenUpWord[i], textView);
                        listOfFilledTextViews.add(textView);
                        startingPoint++;
                    }
                    break;
                case 3:
                    listOfTextViewSpaces = getListOfTextViewSpaces(13,14,15,16,17,18);
                    textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint-1));
                    _letterToTextViewMap.put(brokenUpWord[1], textView);
                    listOfFilledTextViews.add(textView);
                    for(int i = 2; i < brokenUpWord.length; i++){
                        textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint));
                        _letterToTextViewMap.put(brokenUpWord[i], textView);
                        listOfFilledTextViews.add(textView);
                        startingPoint++;
                    }
                    break;
                case 4:
                    listOfTextViewSpaces = getListOfTextViewSpaces(19,20,21,22,23,24);
                    textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint-1));
                    _letterToTextViewMap.put(brokenUpWord[1], textView);
                    listOfFilledTextViews.add(textView);
                    for(int i = 2; i < brokenUpWord.length; i++){
                        textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint));
                        _letterToTextViewMap.put(brokenUpWord[i], textView);
                        listOfFilledTextViews.add(textView);
                        startingPoint++;
                    }
                    break;
                case 5:
                    listOfTextViewSpaces = getListOfTextViewSpaces(25,26,27,28,29,30);
                    textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint-1));
                    _letterToTextViewMap.put(brokenUpWord[1], textView);
                    listOfFilledTextViews.add(textView);
                    for(int i = 2; i < brokenUpWord.length; i++){
                        textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint));
                        _letterToTextViewMap.put(brokenUpWord[i], textView);
                        listOfFilledTextViews.add(textView);
                        startingPoint++;
                    }
                    break;
                case 6:
                    listOfTextViewSpaces = getListOfTextViewSpaces(31,32,33,34,35,36);
                    textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint-1));
                    _letterToTextViewMap.put(brokenUpWord[1], textView);
                    listOfFilledTextViews.add(textView);
                    for(int i = 2; i < brokenUpWord.length; i++){
                        textView = getWordFindTextView(listOfTextViewSpaces.get(startingPoint));
                        _letterToTextViewMap.put(brokenUpWord[i], textView);
                        listOfFilledTextViews.add(textView);
                        startingPoint++;
                    }
                    break;
            }
        }
        setWordInWordFind(word, listOfFilledTextViews, listOfTextViewSpaces);
    }

    public ArrayList<Integer> getListOfTextViewSpaces(int first, int second, int third, int fourth, int fifth, int sixth){
        ArrayList<Integer> listOfTextViewSpaces = new ArrayList<>();
        listOfTextViewSpaces.add(first);
        listOfTextViewSpaces.add(second);
        listOfTextViewSpaces.add(third);
        listOfTextViewSpaces.add(fourth);
        listOfTextViewSpaces.add(fifth);
        listOfTextViewSpaces.add(sixth);
        return listOfTextViewSpaces;
    }

    public int chooseStartingPoint(int spaceNotAvailable, int wordLength){
        //spaceNotAvailable = 1
        //wordLength = 3
        int startingPoint = 0;
        //6 spaces
        // find out
        Random random = new Random();
        int min = 0;
        int max = 0;
        switch (spaceNotAvailable){
            case 0:
                switch (wordLength){
                    case 2:
                        //available starting spaces = 1,2,3,4,5
                        min = 1;
                        max = 5;
                        break;
                    case 3:
                        //available starting spaces = 1,2,3,4
                        min = 1;
                        max = 4;
                        break;
                    case 4:
                        //available starting spaces = 1,2,3
                        min = 1;
                        max = 3;
                        break;
                    case 5:
                        //available starting spaces = 1,2
                        min = 1;
                        max = 2;
                        break;
                }
                break;
            case 1:
                switch (wordLength){
                    case 2:
                        //available starting spaces = 1,2,3,4,5
                        min = 2;
                        max = 5;
                        break;
                    case 3:
                        //available starting spaces = 1,2,3,4
                        min = 2;
                        max = 4;
                        break;
                    case 4:
                        //available starting spaces = 1,2,3
                        min = 2;
                        max = 3;
                        break;
                    case 5:
                        //available starting spaces = 1,2
                        min = 2;
                        max = 2;
                        break;
                }
                break;
            case 6:
                switch (wordLength){
                    case 2:
                        //available starting spaces = 1,2,3,4
                        min = 1;
                        max = 4;
                        break;
                    case 3:
                        //available starting spaces = 1,2,3
                        min = 1;
                        max = 3;
                        break;
                    case 4:
                        //available starting spaces = 1,2
                        min = 1;
                        max = 2;
                        break;
                    case 5:
                        //available starting spaces = 1
                        min = 1;
                        max = 1;
                        break;
                }
                break;
        }

        if(wordLength != 6){
            startingPoint = random.nextInt(max - min + 1) + min;
        } else {
             startingPoint = 1;
        }
        return startingPoint;
    }
    
    public TextView getWordFindTextView(int textViewNumber){
        TextView textView = null;
        switch (textViewNumber){
            case 1: textView = _tvLetterA1; break;
            case 2: textView = _tvLetterA2; break;
            case 3: textView = _tvLetterA3; break;
            case 4: textView = _tvLetterA4; break;
            case 5: textView = _tvLetterA5; break;
            case 6: textView = _tvLetterA6; break;
            case 7: textView = _tvLetterB1; break;
            case 8: textView = _tvLetterB2; break;
            case 9: textView = _tvLetterB3; break;
            case 10: textView = _tvLetterB4; break;
            case 11: textView = _tvLetterB5; break;
            case 12: textView = _tvLetterB6; break;
            case 13: textView = _tvLetterC1; break;
            case 14: textView = _tvLetterC2; break;
            case 15: textView = _tvLetterC3; break;
            case 16: textView = _tvLetterC4; break;
            case 17: textView = _tvLetterC5; break;
            case 18: textView = _tvLetterC6; break;
            case 19: textView = _tvLetterD1; break;
            case 20: textView = _tvLetterD2; break;
            case 21: textView = _tvLetterD3; break;
            case 22: textView = _tvLetterD4; break;
            case 23: textView = _tvLetterD5; break;
            case 24: textView = _tvLetterD6; break;
            case 25: textView = _tvLetterE1; break;
            case 26: textView = _tvLetterE2; break;
            case 27: textView = _tvLetterE3; break;
            case 28: textView = _tvLetterE4; break;
            case 29: textView = _tvLetterE5; break;
            case 30: textView = _tvLetterE6; break;
            case 31: textView = _tvLetterF1; break;
            case 32: textView = _tvLetterF2; break;
            case 33: textView = _tvLetterF3; break;
            case 34: textView = _tvLetterF4; break;
            case 35: textView = _tvLetterF5; break;
            case 36: textView = _tvLetterF6; break;
        }
        return textView;
    }

    public void showMatchDialog(final String word, final String imageDrawable, final String imageSound, String wordType) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_match_dialog);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        ImageView ivClose = (ImageView) dialog.findViewById(R.id.ivClose);
        TextView tvWord = (TextView) dialog.findViewById(R.id.tvWord);
        TextView tvWordType = (TextView) dialog.findViewById(R.id.tvWordType);
        tvWordType.setText(wordType);
        tvWord.setText(word);
        if(!checkIfTablet()) {
            tvWord.setTextSize(getTextSize(45));
            tvWordType.setTextSize(getTextSize(20));
        }
        _currentImageName = word;
        Bitmap soundImageBitmap = _fileHelper.getBitmapFromInternalStorage(this, word.toLowerCase().replace(" ","").replace("-","").replace("(", "").replace(")",""));
        String soundImageName = _imageDrawableList.get(_imageListPosition).toLowerCase().replace(" ", "").replace("-", "").replace("(", "").replace(")", "");
        String lastCharacterOfSoundImageName = soundImageName.substring(soundImageName.length() - 1);
        if(soundImageBitmap == null){
            if(lastCharacterOfSoundImageName.equals("s")){
                soundImageName = soundImageName.substring(0, soundImageName.length() - 1);
            } else {
                soundImageName += "s";
            }
            soundImageBitmap = _fileHelper.getBitmapFromInternalStorage(WordFindActivity.this, soundImageName);
        }
        ImageView ivSoundImage = (ImageView) dialog.findViewById(R.id.ivSoundImage);
        LinearLayout llGraphButtons = (LinearLayout) dialog.findViewById(R.id.llGraphButtons);
        if(_settingsAreScoringButtonsVisible){
            llGraphButtons.setVisibility(View.VISIBLE);
        } else {
            llGraphButtons.setVisibility(View.INVISIBLE);
        }
        ivSoundImage.setImageBitmap(soundImageBitmap);
        if(imageSound != ""){
            try{
                ContextWrapper cw = new ContextWrapper(this);
                File directory = cw.getDir("audioDir", Context.MODE_PRIVATE);
                String path = directory.getPath() + "/" + word.toLowerCase().replace(" ","").replace("-","").replace("(", "").replace(")","") + ".mp3";
                final MediaPlayer mp = MediaPlayer.create(this, Uri.parse(path));
                ivSoundImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(_databaseOperationsHelper.getSettingValue(_databaseOperationsHelper, "voice_on") == 1) {
                            if(mp!= null){
                                try{
                                    if(!mp.isPlaying()) {
                                        mp.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
                                        mp.start();
                                    }
                                } catch(Exception ex){
                                    try{
                                        ContextWrapper cw = new ContextWrapper(WordFindActivity.this);
                                        File directory = cw.getDir("audioDir", Context.MODE_PRIVATE);
                                        String path = directory.getPath() + "/" + word.toLowerCase().replace(" ","").replace("-","").replace("(", "").replace(")","") + ".mp3";
                                        MediaPlayer mediaPlayer = MediaPlayer.create(WordFindActivity.this, Uri.parse(path));
                                        if(!mediaPlayer.isPlaying()) {
                                            mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
                                            mediaPlayer.start();
                                        }
                                    } catch(Exception e){

                                    }
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Sound does not exist for word: " + word.toLowerCase().replace(" ","").replace("-","").replace("(", "").replace(")",""),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            } catch(Exception e){
                e.printStackTrace();
            }
        } else {
            ivSoundImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Sound does not exist for word: " + word.toLowerCase().replace(" ","").replace("-","").replace("(", "").replace(")",""), Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(!checkIfTablet()){
            tvWordType.setTextSize(getTextSize(25));
            tvWord.setTextSize(getTextSize(50));
        }

        TextView tvCorrect = (TextView) dialog.findViewById(R.id.tvCorrect);
        tvCorrect.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setScore(true);
            }
        });
        TextView tvWrong = (TextView) dialog.findViewById(R.id.tvWrong);
        tvWrong.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setScore(false);
            }
        });
        ivClose.setOnTouchListener(_scaleAnimation.onTouchListener);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().getDecorView().setSystemUiVisibility(WordFindActivity.this.getWindow().getDecorView().getSystemUiVisibility());
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                _tvCorrect.setVisibility(View.VISIBLE);
                _tvWrong.setVisibility(View.VISIBLE);
            }
        });
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                _tvCorrect.setVisibility(View.INVISIBLE);
                _tvWrong.setVisibility(View.INVISIBLE);
            }
        });
        dialog.show();

    }

//    public void colorWord(int wordNumber){
//        switch (wordNumber){
//            case 1:
//
//                break;
//        }
//    }

    public void populateWordFindTextViewList(){
        _remainingTextViewList = new ArrayList<>();
        TextView textView = null;
            _remainingTextViewList.add(_tvLetterA1);
            _remainingTextViewList.add(_tvLetterA2);
            _remainingTextViewList.add(_tvLetterA3);
            _remainingTextViewList.add(_tvLetterA4);
            _remainingTextViewList.add(_tvLetterA5);
            _remainingTextViewList.add(_tvLetterA6);
            _remainingTextViewList.add(_tvLetterB1);
            _remainingTextViewList.add(_tvLetterB2);
            _remainingTextViewList.add(_tvLetterB3);
            _remainingTextViewList.add(_tvLetterB4);
            _remainingTextViewList.add(_tvLetterB5);
            _remainingTextViewList.add(_tvLetterB6);
            _remainingTextViewList.add(_tvLetterC1);
            _remainingTextViewList.add(_tvLetterC2);
            _remainingTextViewList.add(_tvLetterC3);
            _remainingTextViewList.add(_tvLetterC4);
            _remainingTextViewList.add(_tvLetterC5);
            _remainingTextViewList.add(_tvLetterC6);
            _remainingTextViewList.add(_tvLetterD1);
            _remainingTextViewList.add(_tvLetterD2);
            _remainingTextViewList.add(_tvLetterD3);
            _remainingTextViewList.add(_tvLetterD4);
            _remainingTextViewList.add(_tvLetterD5);
            _remainingTextViewList.add(_tvLetterD6);
            _remainingTextViewList.add(_tvLetterE1);
            _remainingTextViewList.add(_tvLetterE2);
            _remainingTextViewList.add(_tvLetterE3);
            _remainingTextViewList.add(_tvLetterE4);
            _remainingTextViewList.add(_tvLetterE5);
            _remainingTextViewList.add(_tvLetterE6);
            _remainingTextViewList.add(_tvLetterF1);
            _remainingTextViewList.add(_tvLetterF2);
            _remainingTextViewList.add(_tvLetterF3);
            _remainingTextViewList.add(_tvLetterF4);
            _remainingTextViewList.add(_tvLetterF5);
            _remainingTextViewList.add(_tvLetterF6);
    }

    public void setWordInWordFind(String word, ArrayList<TextView> textViewList, ArrayList<Integer> wordFindSpaceList){
        String[] wordLetters = word.split("");
        String wordFindSpace = "";
        final int wordColorArrayListNumber = _wordToColorNumberMap.get(word);
        for(int i = 0; i < wordLetters.length-1; i++){
            wordFindSpace = getWordFindSpace(wordFindSpaceList.get(i));
            textViewList.get(i).setText(wordLetters[i + 1].toLowerCase());
            textViewList.get(i).setTextColor(Color.parseColor("#4D4C48"));
            textViewList.get(i).setBackgroundColor(Color.parseColor("#FFFFFF"));
            textViewList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAllLettersInFoundWordToSelectedColor(wordColorArrayListNumber);
                }
            });
            assignTextViewToColorArray(wordColorArrayListNumber, textViewList.get(i));
            _selectedWordColorsMap.put(textViewList.get(i), word);
            _selectedWordToTextViewMap.put(word, textViewList.get(i));
            _wordFindSpacesList.remove(wordFindSpace);
            _remainingTextViewList.remove(textViewList.get(i));
        }
    }

    public void assignTextViewToColorArray(int colorNumber, TextView textView){
        switch (colorNumber){
            case 1:
                _firstWordTextViews.add(textView);
                break;
            case 2:
                _secondWordTextViews.add(textView);
                break;
            case 3:
                _thirdWordTextViews.add(textView);
                break;
            case 4:
                _fourthWordTextViews.add(textView);
                break;
            case 5:
                _fifthWordTextViews.add(textView);
                break;
            case 6:
                _sixthWordTextViews.add(textView);
                break;
        }
    }

    public void setAllLettersInFoundWordToSelectedColor(int colorNumber){
        String color = _wordColorsMap.get(colorNumber);
        String word = "";
        ArrayList<TextView> textViewList = null;
        switch (colorNumber){
            case 1: textViewList = _firstWordTextViews; break;
            case 2: textViewList = _secondWordTextViews; break;
            case 3: textViewList = _thirdWordTextViews; break;
            case 4: textViewList = _fourthWordTextViews; break;
            case 5: textViewList = _fifthWordTextViews; break;
            case 6: textViewList = _sixthWordTextViews; break;
        }
        switch (colorNumber){
            case 1: word = _imageWordList.get(0); break;
            case 2: word = _imageWordList.get(1); break;
            case 3: word = _imageWordList.get(2); break;
            case 4: word = _imageWordList.get(3); break;
            case 5: word = _imageWordList.get(4); break;
            case 6: word = _imageWordList.get(5); break;
        }
        switch (colorNumber){
            case 1:_ivMatchingArrow1.setVisibility(View.VISIBLE);break;
            case 2:_ivMatchingArrow2.setVisibility(View.VISIBLE);break;
            case 3:_ivMatchingArrow3.setVisibility(View.VISIBLE);break;
            case 4:_ivMatchingArrow4.setVisibility(View.VISIBLE);break;
            case 5:_ivMatchingArrow5.setVisibility(View.VISIBLE);break;
            case 6:_ivMatchingArrow6.setVisibility(View.VISIBLE);break;
        }
        final String wordString = word;
        _currentImageName = word;
        for(int i = 0; i < textViewList.size(); i++){
            textViewList.get(i).setTextColor(Color.parseColor("#FFFFFF"));
            textViewList.get(i).setBackgroundColor(Color.parseColor(color));
            textViewList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMatchDialog(wordString,_soundDrawableMap.get(wordString), _soundDrawableMap.get(wordString), _soundWordTypeMap.get(wordString));
                }
            });
        }
    }

//    public void colorFoundWord(TextView textView){
//        String wordFound = _selectedWordColorsMap.get(textView);
//        int colorNumber = _wordToColorNumberMap.get(wordFound);
//        switch (colorNumber){
//            case 1:
//                _wordToColorNumberMap.get(colorNumber);
//
//                _wordColorsMap.get(colorNumber);
//                break;
//        }
//    }

    public void changeLeftOverLettersToRandomLetters(){
        for(int i = 0; i < _remainingTextViewList.size(); i++){
            _remainingTextViewList.get(i).setTextColor(Color.parseColor("#4D4C48"));
            _remainingTextViewList.get(i).setBackgroundColor(Color.parseColor("#FFFFFF"));
            Random random = new Random();
            int max = 26;
            int min = 1;
            int randomNumber = random.nextInt(max - min + 1) + min;
            while(randomNumber == _soundNumber){
                randomNumber = random.nextInt(max - min + 1) + min;
            }
            _remainingTextViewList.get(i).setText(getLetter(randomNumber).toLowerCase());
        }
    }

    public String getWordFindSpace(int wordFindSpaceNumber){
        String wordFindSpace = "";
        switch (wordFindSpaceNumber){
            case 1: wordFindSpace = "A1"; break;
            case 2: wordFindSpace = "A2"; break;
            case 3: wordFindSpace = "A3"; break;
            case 4: wordFindSpace = "A4"; break;
            case 5: wordFindSpace = "A5"; break;
            case 6: wordFindSpace = "A6"; break;
            case 7: wordFindSpace = "B1"; break;
            case 8: wordFindSpace = "B2"; break;
            case 9: wordFindSpace = "B3"; break;
            case 10: wordFindSpace = "B4"; break;
            case 11: wordFindSpace = "B5"; break;
            case 12: wordFindSpace = "B6"; break;
            case 13: wordFindSpace = "C1"; break;
            case 14: wordFindSpace = "C2"; break;
            case 15: wordFindSpace = "C3"; break;
            case 16: wordFindSpace = "C4"; break;
            case 17: wordFindSpace = "C5"; break;
            case 18: wordFindSpace = "C6"; break;
            case 19: wordFindSpace = "D1"; break;
            case 20: wordFindSpace = "D2"; break;
            case 21: wordFindSpace = "D3"; break;
            case 22: wordFindSpace = "D4"; break;
            case 23: wordFindSpace = "D5"; break;
            case 24: wordFindSpace = "D6"; break;
            case 25: wordFindSpace = "E1"; break;
            case 26: wordFindSpace = "E2"; break;
            case 27: wordFindSpace = "E3"; break;
            case 28: wordFindSpace = "E4"; break;
            case 29: wordFindSpace = "E5"; break;
            case 30: wordFindSpace = "E6"; break;
            case 31: wordFindSpace = "F1"; break;
            case 32: wordFindSpace = "F2"; break;
            case 33: wordFindSpace = "F3"; break;
            case 34: wordFindSpace = "F4"; break;
            case 35: wordFindSpace = "F5"; break;
            case 36: wordFindSpace = "F6"; break;
        }
        return wordFindSpace;
    }

    public ArrayList<String> getColumnList(){
        ArrayList<String> columns = new ArrayList<>();
        columns.add("A");
        columns.add("B");
        columns.add("C");
        columns.add("D");
        columns.add("E");
        columns.add("F");
        return columns;
    }

    public ArrayList<Integer> getRowList(){
        ArrayList<Integer> rows = new ArrayList<>();
        rows.add(1);
        rows.add(2);
        rows.add(3);
        rows.add(4);
        rows.add(5);
        rows.add(6);
        return rows;
    }

    public ArrayList<String> getWordFindSpacesList(){
        ArrayList<String> wordFindSpacesList = new ArrayList<>();
        wordFindSpacesList.add("A1");
        wordFindSpacesList.add("A2");
        wordFindSpacesList.add("A3");
        wordFindSpacesList.add("A4");
        wordFindSpacesList.add("A5");
        wordFindSpacesList.add("A6");
        wordFindSpacesList.add("B1");
        wordFindSpacesList.add("B2");
        wordFindSpacesList.add("B3");
        wordFindSpacesList.add("B4");
        wordFindSpacesList.add("B5");
        wordFindSpacesList.add("B6");
        wordFindSpacesList.add("C1");
        wordFindSpacesList.add("C2");
        wordFindSpacesList.add("C3");
        wordFindSpacesList.add("C4");
        wordFindSpacesList.add("C5");
        wordFindSpacesList.add("C6");
        wordFindSpacesList.add("D1");
        wordFindSpacesList.add("D2");
        wordFindSpacesList.add("D3");
        wordFindSpacesList.add("D4");
        wordFindSpacesList.add("D5");
        wordFindSpacesList.add("D6");
        wordFindSpacesList.add("E1");
        wordFindSpacesList.add("E2");
        wordFindSpacesList.add("E3");
        wordFindSpacesList.add("E4");
        wordFindSpacesList.add("E5");
        wordFindSpacesList.add("E6");
        wordFindSpacesList.add("F1");
        wordFindSpacesList.add("F2");
        wordFindSpacesList.add("F3");
        wordFindSpacesList.add("F4");
        wordFindSpacesList.add("F5");
        wordFindSpacesList.add("F6");
        return  wordFindSpacesList;
    }

    public String getLetter(int letterNumber){
        String letter = "";
        switch (letterNumber){
            case 1:letter = "a";break;
            case 2:letter = "b";break;
            case 3:letter = "c";break;
            case 4:letter = "d";break;
            case 5:letter = "e";break;
            case 6:letter = "f";break;
            case 7:letter = "g";break;
            case 8:letter = "h";break;
            case 9:letter = "i";break;
            case 10:letter = "j";break;
            case 11:letter = "k";break;
            case 12:letter = "l";break;
            case 13:letter = "m";break;
            case 14:letter = "n";break;
            case 15:letter = "o";break;
            case 16:letter = "p";break;
            case 17:letter = "q";break;
            case 18:letter = "r";break;
            case 19:letter = "s";break;
            case 20:letter = "t";break;
            case 21:letter = "u";break;
            case 22:letter = "v";break;
            case 23:letter = "w";break;
            case 24:letter = "x";break;
            case 25:letter = "y";break;
            case 26:letter = "z";break;
        }
        return letter;
    }

    public boolean checkIfAnyWordHasSix(){
        boolean aWordHasSix = false;
        for(int i = 0; i < _imageWordList.size(); i++){
            if(_imageWordList.get(i).length() == 6){
                aWordHasSix = true;
            }
        }
        return aWordHasSix;
    }

    public boolean checkIfLongWordGoesInFirstSpot(){
        boolean longWordGoesInFirstSpot = false;
        Random random = new Random();
        int max = 100;
        int min = 1;
        int randomNumber = random.nextInt(max - min + 1) + min;
        if(randomNumber >= 50){
            longWordGoesInFirstSpot = true;
        }
        return longWordGoesInFirstSpot;
    }

    public void setVerticalHorizontalRatios(){
        _verticalRatioIsHigher = verticalRatioIsMoreThanHorizontal();
        _numberOfVerticalSpaces = 0;
        _numberOfHorizontalSpaces = 0;
        _longWordGoesInFirstSpot = checkIfLongWordGoesInFirstSpot();
        _atLeastOneWordHasSixLetters = checkIfAnyWordHasSix();
        _numberOfWordsWithSixLetters = getNumberOfWordsWithSixLetters();
        if(_numberOfWordsWithSixLetters > 1){
            if(_verticalRatioIsHigher){
                _numberOfHorizontalSpaces = 0;
                _numberOfVerticalSpaces = 6;
            } else {
                _numberOfHorizontalSpaces = 6;
                _numberOfVerticalSpaces = 0;
            }
        } else {
            if(_verticalRatioIsHigher){
                _numberOfHorizontalSpaces = 1;
                _numberOfVerticalSpaces = 5;
            } else {
                _numberOfHorizontalSpaces = 5;
                _numberOfVerticalSpaces = 1;
            }
        }
    }

    public int getNumberOfWordsWithSixLetters(){
        int numberOfWordsWithSixLetters = 0;
        for(int i=0; i < _imageWordList.size(); i++){
            if(_imageWordList.get(i).length() == 6){
                numberOfWordsWithSixLetters++;
            }
        }
        return numberOfWordsWithSixLetters;
    }

    public boolean verticalRatioIsMoreThanHorizontal(){
        boolean verticalRatioMore = false;
        Random random = new Random();
        int max = 100;
        int min = 1;
        int randomNumber = random.nextInt(max - min + 1) + min;
        if(randomNumber >= 50){
            verticalRatioMore = true;
        }
        return verticalRatioMore;
    }

    public void startFlashCards(){
        _llSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsDialog();
            }
        });
        _ivMirror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _settingsIsMirrorFunctionOn = _settingsHelper.getSettingsIsMirrorFunctionOn(WordFindActivity.this);
                if (_settingsIsMirrorFunctionOn) {
                    openMirror();
                }
            }
        });
        _llFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeedback();
            }
        });
        _llStart.setVisibility(View.GONE);
        if(_settingsAreScoringButtonsVisible){
            _llGraphButtons.setVisibility(View.VISIBLE);
        } else {
            _llGraphButtons.setVisibility(View.INVISIBLE);
        }
        _llWordList.setVisibility(View.VISIBLE);
        startFlashCardGame();
        setUpWordFind();
        //_rlBeginningOptions.setVisibility(View.GONE);
        //_rlDialogFlashCards.setVisibility(View.VISIBLE);
        _llBeginning.setOnClickListener(null);
        _llMiddle.setOnClickListener(null);
        _llEnding.setOnClickListener(null);
        _llBeginning.setOnTouchListener(null);
        _llMiddle.setOnTouchListener(null);
        _llEnding.setOnTouchListener(null);
        _okayToOpenMirror = true;
    }

    public void startFlashCardGame(){
        final float linearLayoutMovement = _rlBeginningOptions.getHeight() + 30;
        final TranslateAnimation moveFlashCardOut = new TranslateAnimation(0, 0, 0, linearLayoutMovement);
        moveFlashCardOut.setStartOffset(100);
        moveFlashCardOut.setDuration(400);
        final TranslateAnimation moveFlashCardIn = new TranslateAnimation(0, 0, -linearLayoutMovement, 0);
        moveFlashCardIn.setStartOffset(0);
        moveFlashCardIn.setDuration(400);
        moveFlashCardIn.setAnimationListener(new Animation.AnimationListener() {
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
        moveFlashCardOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                _rlBeginningOptions.setVisibility(View.GONE);
                _rlDialogWordFind.setVisibility(View.VISIBLE);
                _rlDialogWordFind.startAnimation(moveFlashCardIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        _rlBeginningOptions.startAnimation(moveFlashCardOut);
    }

    public void finishLevel(){
        //TODO: Animation Station!
        _llGraphButtons.setVisibility(View.INVISIBLE);
        if(_databaseOperationsHelper.getSettingValue(_databaseOperationsHelper, "voice_on") == 1) {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.success);
            if(!mp.isPlaying()) {
                mp.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
                mp.start();
            }
        }
        _rlDialogWordFind.setVisibility(View.GONE);
        _rlDialogFlashCardsGoodJob.setVisibility(View.VISIBLE);
    }

    public void setGraph(float graphPercent){
        TableRow.LayoutParams whiteGraphParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.FILL_PARENT, graphPercent);
        TableRow.LayoutParams backgroundGraphParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.FILL_PARENT, (100 - graphPercent));
        if(checkIfTablet()){
            backgroundGraphParams.bottomMargin = 8;
            backgroundGraphParams.topMargin = 8;
            whiteGraphParams.bottomMargin = 8;
            whiteGraphParams.topMargin = 8;
        } else {
            backgroundGraphParams.bottomMargin = 12;
            backgroundGraphParams.topMargin = 12;
            whiteGraphParams.bottomMargin = 12;
            whiteGraphParams.topMargin = 12;
        }
        _vGraphBackground.setLayoutParams(backgroundGraphParams);
        _vGraphScale.setLayoutParams(whiteGraphParams);
    }

    public void setScore(boolean correctPressed){
        _totalScore++;
        int currentImageId = _databaseOperationsHelper.getSoundImageId(_databaseOperationsHelper, _currentImageName, _soundName);
        if(correctPressed){
            playCorrectSound();
            if(!_correctImageNameIdList.contains(currentImageId)){
                _correctImageNameIdList.add(currentImageId);
            }
            _totalCorrect++;
        } else {
            playIncorrectSound();
            if(!_incorrectImageNameIdList.contains(currentImageId)) {
                _incorrectImageNameIdList.add(currentImageId);
            }
        }
        float perCorrect = (float)_totalCorrect / _totalScore;
        _percentCorrect = (int) (perCorrect * 100);
        setGraph(_percentCorrect);
        _tvRatioCorrect.setText(_totalCorrect + "/" + _totalScore + " (" + _percentCorrect + "%)");
        if(!checkIfTablet()){
            _tvRatioCorrect.setTextSize(getTextSize(15));
        }
    }

    public void playCorrectSound(){
        if(_databaseOperationsHelper.getSettingValue(_databaseOperationsHelper, "scoring_sounds") == 1) {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.correct);
            if(!mp.isPlaying()) {
                mp.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
                mp.start();
            }
        }
    }

    public void playIncorrectSound(){
        if(_databaseOperationsHelper.getSettingValue(_databaseOperationsHelper, "scoring_sounds") == 1) {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.incorrect);
            if(!mp.isPlaying()) {
                mp.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
                mp.start();
            }
        }
    }

    public boolean checkIfTablet() {
        String screenSize = getScreenResolution(this);
        return (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    private static String getScreenResolution(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        return "{" + width + "," + height + "}";
    }

    public void openSettingsDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_settings_dialog);
        _decorView.setSystemUiVisibility(_uiOptions);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        TextView tvMirrorFunction = (TextView) dialog.findViewById(R.id.tvMirrorFunction);
        tvMirrorFunction.setTypeface(_typeFaceAnja);
        TextView tvScoringButtons = (TextView) dialog.findViewById(R.id.tvScoringButtons);
        tvScoringButtons.setTypeface(_typeFaceAnja);
        TextView tvScoringSounds = (TextView) dialog.findViewById(R.id.tvScoringSounds);
        tvScoringSounds.setTypeface(_typeFaceAnja);
        TextView tvVoiceAudio = (TextView) dialog.findViewById(R.id.tvVoiceAudio);
        tvVoiceAudio.setTypeface(_typeFaceAnja);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        tvTitle.setTypeface(_typeFaceAnja);
        final ImageView ivScoringButton = (ImageView) dialog.findViewById(R.id.ivScoringButton);
        ivScoringButton.setOnTouchListener(_scaleAnimation.onTouchListener);
        final ImageView ivScoringSound = (ImageView) dialog.findViewById(R.id.ivScoringSound);
        ivScoringSound.setOnTouchListener(_scaleAnimation.onTouchListener);
        final ImageView ivVoiceAudio = (ImageView) dialog.findViewById(R.id.ivVoiceAudio);
        ivVoiceAudio.setOnTouchListener(_scaleAnimation.onTouchListener);
        final ImageView ivMirrorFunction = (ImageView) dialog.findViewById(R.id.ivMirrorFunction);
        ivMirrorFunction.setOnTouchListener(_scaleAnimation.onTouchListener);
        if(_databaseOperationsHelper.getSettingValue(_databaseOperationsHelper, "voice_on") == 1){
            ivVoiceAudio.setImageResource(R.drawable.check_button);
        } else {
            ivVoiceAudio.setImageResource(R.drawable.plus_button);
        }
        if(_databaseOperationsHelper.getSettingValue(_databaseOperationsHelper, "scoring_buttons_visible") == 1){
            ivScoringButton.setImageResource(R.drawable.check_button);
        } else {
            ivScoringButton.setImageResource(R.drawable.plus_button);
        }
        if(_databaseOperationsHelper.getSettingValue(_databaseOperationsHelper, "scoring_sounds") == 1){
            ivScoringSound.setImageResource(R.drawable.check_button);
        } else {
            ivScoringSound.setImageResource(R.drawable.plus_button);
        }
        if(_databaseOperationsHelper.getSettingValue(_databaseOperationsHelper, "mirror_function_on") == 1){
            ivMirrorFunction.setImageResource(R.drawable.check_button);
        } else {
            ivMirrorFunction.setImageResource(R.drawable.plus_button);
        }
//        if(_databaseOperationsHelper.getSettingValue(_databaseOperationsHelper, "mirror_function_on") == 1){
//            ivScoringSound.setBackgroundResource(R.drawable.check_button);
//        } else {
//            ivScoringSound.setBackgroundResource(R.drawable.plus_button);
//        }

        ivScoringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSetting(1, ivScoringButton);
            }
        });
        ivScoringSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSetting(2, ivScoringSound);
            }
        });
        ivVoiceAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSetting(3, ivVoiceAudio);
            }
        });
        ivMirrorFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSetting(4, ivMirrorFunction);
            }
        });
        TextView tvShowScoringButtons = (TextView) dialog.findViewById(R.id.tvShowScoringButtons);
        TextView tvShowScoringSounds = (TextView) dialog.findViewById(R.id.tvShowScoringSounds);
        TextView tvPlaysVoiceAudio = (TextView) dialog.findViewById(R.id.tvPlaysVoiceAudio);
        TextView tvTurnOnMirror = (TextView) dialog.findViewById(R.id.tvTurnOnMirror);
        if(!checkIfTablet()) {
            tvTitle.setTextSize(getTextSize(35));
            tvScoringButtons.setTextSize(getTextSize(20));
            tvShowScoringButtons.setTextSize(getTextSize(13));
            tvScoringSounds.setTextSize(getTextSize(20));
            tvShowScoringSounds.setTextSize(getTextSize(13));
            tvVoiceAudio.setTextSize(getTextSize(20));
            tvPlaysVoiceAudio.setTextSize(getTextSize(13));
            tvMirrorFunction.setTextSize(getTextSize(20));
            tvTurnOnMirror.setTextSize(getTextSize(13));
        }

        View v1 = (View) dialog.findViewById(R.id.v1);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v2 = (View) dialog.findViewById(R.id.v2);
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v3 = (View) dialog.findViewById(R.id.v3);
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v4 = (View) dialog.findViewById(R.id.v4);
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                _decorView.setSystemUiVisibility(_uiOptions);
                _settingsAreScoringButtonsVisible = _settingsHelper.getSettingsAreScoringButtonsVisible(WordFindActivity.this);
                _settingsIsMirrorFunctionOn = _settingsHelper.getSettingsIsMirrorFunctionOn(WordFindActivity.this);
                if (_settingsAreScoringButtonsVisible) {
                    _llGraphButtons.setVisibility(View.VISIBLE);
                } else {
                    _llGraphButtons.setVisibility(View.INVISIBLE);
                }
                if (_settingsIsMirrorFunctionOn) {
                    _llMirrorButton.setVisibility(View.VISIBLE);
                    _tvMirror.setVisibility(View.VISIBLE);
                } else {
                    _llMirrorButton.setVisibility(View.INVISIBLE);
                    _tvMirror.setVisibility(View.INVISIBLE);
                }
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                _decorView.setSystemUiVisibility(_uiOptions);
                _settingsAreScoringButtonsVisible = _settingsHelper.getSettingsAreScoringButtonsVisible(WordFindActivity.this);
                _settingsIsMirrorFunctionOn = _settingsHelper.getSettingsIsMirrorFunctionOn(WordFindActivity.this);
                if (_settingsAreScoringButtonsVisible) {
                    _llGraphButtons.setVisibility(View.VISIBLE);
                } else {
                    _llGraphButtons.setVisibility(View.INVISIBLE);
                }
                if (_settingsIsMirrorFunctionOn) {
                    _llMirrorButton.setVisibility(View.VISIBLE);
                    _tvMirror.setVisibility(View.VISIBLE);
                } else {
                    _llMirrorButton.setVisibility(View.INVISIBLE);
                    _tvMirror.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void changeSetting(int settingNumber, ImageView imageView){
        switch (settingNumber){
            case 1:
                if(_databaseOperationsHelper.getSettingValue(_databaseOperationsHelper, "scoring_buttons_visible") == 1){
                    imageView.setImageResource(R.drawable.plus_button);
                    _databaseOperationsHelper.changeSetting(_databaseOperationsHelper, "scoring_buttons_visible", 0);
                } else {
                    imageView.setImageResource(R.drawable.check_button);
                    _databaseOperationsHelper.changeSetting(_databaseOperationsHelper, "scoring_buttons_visible", 1);
                }
                break;
            case 2:
                if(_databaseOperationsHelper.getSettingValue(_databaseOperationsHelper, "scoring_sounds") == 1){
                    imageView.setImageResource(R.drawable.plus_button);
                    _databaseOperationsHelper.changeSetting(_databaseOperationsHelper, "scoring_sounds", 0);
                } else {
                    imageView.setImageResource(R.drawable.check_button);
                    _databaseOperationsHelper.changeSetting(_databaseOperationsHelper, "scoring_sounds", 1);
                }
                break;
            case 3:
                if(_databaseOperationsHelper.getSettingValue(_databaseOperationsHelper, "voice_on") == 1){
                    imageView.setImageResource(R.drawable.plus_button);
                    _databaseOperationsHelper.changeSetting(_databaseOperationsHelper, "voice_on", 0);
                } else {
                    imageView.setImageResource(R.drawable.check_button);
                    _databaseOperationsHelper.changeSetting(_databaseOperationsHelper, "voice_on", 1);
                }
                break;
            case 4:
                if(_databaseOperationsHelper.getSettingValue(_databaseOperationsHelper, "mirror_function_on") == 1){
                    imageView.setImageResource(R.drawable.plus_button);
                    _databaseOperationsHelper.changeSetting(_databaseOperationsHelper, "mirror_function_on", 0);
                } else {
                    imageView.setImageResource(R.drawable.check_button);
                    _databaseOperationsHelper.changeSetting(_databaseOperationsHelper, "mirror_function_on", 1);
                }
                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goToMenu();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void goToMenu(){
        Intent myIntent = new Intent(WordFindActivity.this, MenuActivity.class);
        WordFindActivity.this.startActivity(myIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            _decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LOW_PROFILE
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }
}

