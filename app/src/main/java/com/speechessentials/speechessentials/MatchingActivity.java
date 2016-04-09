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
import android.view.animation.AnimationUtils;
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
public class MatchingActivity extends Activity {

    DatabaseOperationsHelper _databaseOperationsHelper;
    FileHelper _fileHelper;
    String _wordType;
    ArrayList<Integer> _soundImageIdList, _imageAudioList;
    ArrayList<String> _imageWordList, _soundPositionNameList, _soundWordTypeList, _imageDrawableList, _listOfSoundNamesForGame;
    String _soundName, _currentWord;
    boolean _beginningSelected, _middleSelected, _endingSelected, _okayToOpenMirror;
    TextView _tvStartText, _tvStart;
    LinearLayout _llBeginningOptions, _llMatchingGame, _llStart, _llGraphButtons, _llMirrorButton, _llBlends, _llFeedbackButton;
    RelativeLayout _rlDialogFlashCardsGoodJob, _rlBeginningOptions;
    String _soundPosition;
    float _density;
    ImageView _ivWhiteGraphCircle, _ivBeginningIcon, _ivMiddleIcon, _ivEndingIcon, _ivBlendsIcon, _ivDone, _ivFlashcards, _ivSoundImage, _ivSettingsIcon, _ivBottomBackground,
            _ivCardImage1, _ivCardImage2, _ivCardImage3, _ivCardImage4, _ivCardImage5, _ivCardImage6, _ivCardImage7, _ivCardImage8, _ivCardImage9, _ivCardImage10, _ivCardImage11, _ivCardImage12, _ivMirror;
    int _imageListSize, _imageListPosition, _screenWidth, _screenHeight, _totalCorrect, _totalScore, _percentCorrect, _soundNumber, _numberOfUnMatchedCardsFlippedOver;
    HashMap<Integer, Integer> _matchNumbersMap, _matchAudioMap;
    HashMap<Integer, String> _matchImagesMap;
    HashMap<Integer, String> _matchWordsMap, _matchWordTypesMap;
    ArrayList<Integer> _imageNumberList, _imageWordNumberList, _unMatchCardsFlippedOverList, _matchedCardsList;
    ArrayList<Integer> _incorrectImageNameIdList, _correctImageNameIdList, _approximateImageNameIdList;
    LinearLayout _llBeginning, _llMiddle, _llEnding, _llDialogInstructions, _llDialogFlashCards, _llSettingsButton, _llSettings,
            _llMatchingCard1, _llMatchingCard2, _llMatchingCard3, _llMatchingCard4, _llMatchingCard5, _llMatchingCard6, _llMatchingCard7, _llMatchingCard8, _llMatchingCard9, _llMatchingCard10, _llMatchingCard11, _llMatchingCard12,
            _llMatchingCardFlip1, _llMatchingCardFlip2, _llMatchingCardFlip3, _llMatchingCardFlip4, _llMatchingCardFlip5, _llMatchingCardFlip6, _llMatchingCardFlip7, _llMatchingCardFlip8, _llMatchingCardFlip9, _llMatchingCardFlip10, _llMatchingCardFlip11, _llMatchingCardFlip12;
    SystemUiScaleAnimation _scaleAnimation;
    SoundHelper _soundHelper;
    TextView _tvLetter, _tvLetterSound, _tvRatioCorrect, _tvBeginning, _tvMiddle, _tvMirror, _tvEnding, _tvDone, _tvInstructionsTitle, _tvInstructionsSelect, _tvInstructionsBegMidEnd, _tvInstructionsCreateWordList,
            _tvCorrect, _tvWrong, _tvSettings,
            _tvCardWord1, _tvCardWord2, _tvCardWord3, _tvCardWord4, _tvCardWord5, _tvCardWord6, _tvCardWord7, _tvCardWord8, _tvCardWord9, _tvCardWord10, _tvCardWord11, _tvCardWord12;
    Typeface _typeFaceAnjaBold, _typeFaceAnja;
    View _decorView, _vGraphScale, _vGraphBackground;
    int _uiOptions;
    SettingsHelper _settingsHelper;
    boolean _settingsAreScoringSoundsOn, _settingsAreScoringButtonsVisible, _settingsIsVoiceOn, _settingsIsMirrorFunctionOn;
    ScoreHelper _scoreHelper;
    String _currentUserName;
    ArrayList<TextView> _usernameTextViewList;
    ArrayList<LinearLayout> _linearLayoutUserList;
    EditText _etBirthday;
    View _vFiller;
    LinearLayout _llRBeginningOptions, _llRMiddleOptions, _llREndingOptions, _llLBeginningOptions, _llSBlendOptions, _llLBlendOptions, _llRBlendOptions;
    ImageView _ivBeginningArrow, _ivMiddleArrow, _ivEndingArrow, _ivBlendsArrow;
    TextView tvAIR, tvAR, tvBR, tvKR, tvTR, tvDR, tvEAR, tvER, tvFR, tvGR, tvIRE, tvOR, tvPR, tvMiddleAIR, tvMiddleAR, tvMiddleEAR, _tvBlends, tvMiddleER, tvMiddleIRE, tvMiddleOR, tvMiddleRL, tvEndingAIR, tvEndingAR, tvEndingEAR, tvEndingER, tvEndingIRE, tvEndingOR, tvEndingRL, tvR, tvMiddleR, tvEndingR;
    TextView tvBL, tvFL, tvKL, tvGL, tvPL, tvLSL;
    TextView tvSK, tvSL, tvSM, tvSN, tvSP, tvST, tvSW, tvKS, tvNS, tvPS, tvTS;
    boolean tvAIRSelected, tvARSelected, tvBRSelected, tvKRSelected, tvTRSelected, tvDRSelected, tvEARSelected, tvERSelected, tvFRSelected, tvGRSelected, tvIRESelected, tvORSelected, tvPRSelected, tvMiddleAIRSelected, tvMiddleARSelected, tvMiddleEARSelected, tvMiddleERSelected, tvMiddleIRESelected, tvMiddleORSelected, tvMiddleRLSelected, tvEndingAIRSelected, tvEndingARSelected, tvEndingEARSelected, tvEndingERSelected, tvEndingIRESelected, tvEndingORSelected, tvEndingRLSelected;
    boolean tvBLSelected, tvFLSelected, tvKLSelected, tvGLSelected, tvPLSelected, tvLSLSelected;
    boolean tvSKSelected, tvSLSelected, tvSMSelected, tvSNSelected, tvSPSelected, tvSTSelected, tvSWSelected, tvKSSelected, tvNSSelected, tvPSSelected, tvTSSelected;
    boolean tvRSelected, tvMiddleRSelected, tvEndingRSelected;
    ImageView ivBeginningArrow, ivMiddleArrow, ivEndingArrow, ivBlendsArrow;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);

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
//        if(checkIfTablet()){
//            initTabletVariables();
//        } else {
//            initPhoneVariables();
//        }
    }

    float _dpHeight, _dpWidth;
    public int getTextSize(int originalTextSize){
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
        tracker.setScreenName("Matching Activity");
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
        _databaseOperationsHelper = new DatabaseOperationsHelper(this);
        _fileHelper = new FileHelper();
        _scaleAnimation = new SystemUiScaleAnimation();
        _listOfSoundNamesForGame = new ArrayList<>();
        if(_soundName.equals("r") || _soundName.equals("s") || _soundName.equals("l")){

        } else {
            _listOfSoundNamesForGame.add(_soundName);
        }
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density = getResources().getDisplayMetrics().density;
        _dpHeight = outMetrics.heightPixels / density;//411.42856
        _dpWidth = outMetrics.widthPixels / density;//689.4286
        //TODO: get this number from previous activity
        _soundNumber = 1;
        _soundHelper = new SoundHelper();
        _numberOfUnMatchedCardsFlippedOver = 0;
        _matchWordsMap = new HashMap<>();
        _matchWordTypesMap = new HashMap<>();
        _matchImagesMap = new HashMap<>();
        _matchAudioMap = new HashMap<>();
        _imageListPosition = 0;
        _typeFaceAnjaBold = Typeface.createFromAsset(getAssets(), "font/anja.ttf");
        _typeFaceAnja = Typeface.createFromAsset(getAssets(), "font/anja.ttf");
        _llMirrorButton = (LinearLayout) findViewById(R.id.llMirrorButton);
        _llFeedbackButton = (LinearLayout) findViewById(R.id.llFeedbackButton);
        _llFeedbackButton.setOnTouchListener(_scaleAnimation.onTouchListener);
        _llFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MatchingActivity.this, "Please start the game first.", Toast.LENGTH_SHORT).show();
            }
        });
        _tvMirror = (TextView) findViewById(R.id.tvMirror);
        if(_settingsIsMirrorFunctionOn){
            _llMirrorButton.setVisibility(View.VISIBLE);
            _tvMirror.setVisibility(View.VISIBLE);
        } else {
            _llMirrorButton.setVisibility(View.INVISIBLE);
            _tvMirror.setVisibility(View.INVISIBLE);
        }
        _beginningSelected = false;
        _middleSelected = false;
        _endingSelected = false;
        _ivMirror = (ImageView) findViewById(R.id.ivMirror);
        _ivMirror.setOnTouchListener(_scaleAnimation.onTouchListener);
        _ivMirror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _settingsIsMirrorFunctionOn = _settingsHelper.getSettingsIsMirrorFunctionOn(MatchingActivity.this);
                if (_settingsIsMirrorFunctionOn) {
                    openMirror();
                }
            }
        });
        _rlDialogFlashCardsGoodJob = (RelativeLayout) findViewById(R.id.rlDialogFlashCardsGoodJob);
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
        _rlBeginningOptions = (RelativeLayout) findViewById(R.id.rlBeginningOptions);
        _llMatchingGame = (LinearLayout) findViewById(R.id.llMatchingGame);
        _llGraphButtons = (LinearLayout) findViewById(R.id.llGraphButtons);
        _llStart = (LinearLayout) findViewById(R.id.llStart);
        _tvBlends = (TextView) findViewById(R.id.tvBlends);
        _ivBlendsIcon = (ImageView) findViewById(R.id.ivBlendsIcon);
        _ivWhiteGraphCircle = (ImageView) findViewById(R.id.ivWhiteGraphCircle);
        _ivBeginningIcon = (ImageView) findViewById(R.id.ivBeginningIcon);
        _ivMiddleIcon = (ImageView) findViewById(R.id.ivMiddleIcon);
        _ivEndingIcon = (ImageView) findViewById(R.id.ivEndingIcon);
        _ivFlashcards = (ImageView) findViewById(R.id.ivFlashcards);
        _ivSoundImage = (ImageView) findViewById(R.id.ivSoundImage);
        _ivSettingsIcon = (ImageView) findViewById(R.id.ivSettingsIcon);
        _ivCardImage1 = (ImageView) findViewById(R.id.ivCardImage1);
        _ivCardImage2 = (ImageView) findViewById(R.id.ivCardImage2);
        _ivCardImage3 = (ImageView) findViewById(R.id.ivCardImage3);
        _ivCardImage4 = (ImageView) findViewById(R.id.ivCardImage4);
        _ivCardImage5 = (ImageView) findViewById(R.id.ivCardImage5);
        _ivCardImage6 = (ImageView) findViewById(R.id.ivCardImage6);
        _ivCardImage7 = (ImageView) findViewById(R.id.ivCardImage7);
        _ivCardImage8 = (ImageView) findViewById(R.id.ivCardImage8);
        _ivCardImage9 = (ImageView) findViewById(R.id.ivCardImage9);
        _ivCardImage10 = (ImageView) findViewById(R.id.ivCardImage10);
        _ivCardImage11 = (ImageView) findViewById(R.id.ivCardImage11);
        _ivCardImage12 = (ImageView) findViewById(R.id.ivCardImage12);
        _llMatchingCard1 = (LinearLayout) findViewById(R.id.llMatchingCard1);
        _llMatchingCard2 = (LinearLayout) findViewById(R.id.llMatchingCard2);
        _llMatchingCard3 = (LinearLayout) findViewById(R.id.llMatchingCard3);
        _llMatchingCard4 = (LinearLayout) findViewById(R.id.llMatchingCard4);
        _llMatchingCard5 = (LinearLayout) findViewById(R.id.llMatchingCard5);
        _llMatchingCard6 = (LinearLayout) findViewById(R.id.llMatchingCard6);
        _llMatchingCard7 = (LinearLayout) findViewById(R.id.llMatchingCard7);
        _llMatchingCard8 = (LinearLayout) findViewById(R.id.llMatchingCard8);
        _llMatchingCard9 = (LinearLayout) findViewById(R.id.llMatchingCard9);
        _llMatchingCard10 = (LinearLayout) findViewById(R.id.llMatchingCard10);
        _llMatchingCard11 = (LinearLayout) findViewById(R.id.llMatchingCard11);
        _llMatchingCard12 = (LinearLayout) findViewById(R.id.llMatchingCard12);
        _llMatchingCardFlip1 = (LinearLayout) findViewById(R.id.llMatchingCardFlip1);
        _llMatchingCardFlip2 = (LinearLayout) findViewById(R.id.llMatchingCardFlip2);
        _llMatchingCardFlip3 = (LinearLayout) findViewById(R.id.llMatchingCardFlip3);
        _llMatchingCardFlip4 = (LinearLayout) findViewById(R.id.llMatchingCardFlip4);
        _llMatchingCardFlip5 = (LinearLayout) findViewById(R.id.llMatchingCardFlip5);
        _llMatchingCardFlip6 = (LinearLayout) findViewById(R.id.llMatchingCardFlip6);
        _llMatchingCardFlip7 = (LinearLayout) findViewById(R.id.llMatchingCardFlip7);
        _llMatchingCardFlip8 = (LinearLayout) findViewById(R.id.llMatchingCardFlip8);
        _llMatchingCardFlip9 = (LinearLayout) findViewById(R.id.llMatchingCardFlip9);
        _llMatchingCardFlip10 = (LinearLayout) findViewById(R.id.llMatchingCardFlip10);
        _llMatchingCardFlip11 = (LinearLayout) findViewById(R.id.llMatchingCardFlip11);
        _llMatchingCardFlip12 = (LinearLayout) findViewById(R.id.llMatchingCardFlip12);

        _llMatchingCard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard(1);
            }
        });
        _llMatchingCardFlip1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCardBack(1);
            }
        });
        _llMatchingCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard(2);
            }
        });
        _llMatchingCardFlip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCardBack(2);
            }
        });
        _llMatchingCard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard(3);
            }
        });
        _llMatchingCardFlip3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCardBack(3);
            }
        });
        _llMatchingCard4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard(4);
            }
        });
        _llMatchingCardFlip4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCardBack(4);
            }
        });
        _llMatchingCard5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard(5);
            }
        });
        _llMatchingCardFlip5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCardBack(5);
            }
        });
        _llMatchingCard6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard(6);
            }
        });
        _llMatchingCardFlip6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCardBack(6);
            }
        });
        _llMatchingCard7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard(7);
            }
        });
        _llMatchingCardFlip7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCardBack(7);
            }
        });
        _llMatchingCard8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard(8);
            }
        });
        _llMatchingCardFlip8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCardBack(8);
            }
        });
        _llMatchingCard9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard(9);
            }
        });
        _llMatchingCardFlip9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCardBack(9);
            }
        });
        _llMatchingCard10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard(10);
            }
        });
        _llMatchingCardFlip10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCardBack(10);
            }
        });
        _llMatchingCard11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard(11);
            }
        });
        _llMatchingCardFlip11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCardBack(11);
            }
        });
        _llMatchingCard12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard(12);
            }
        });
        _llMatchingCardFlip12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCardBack(12);
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

        Point size = new Point();
        display.getSize(size);
        _screenWidth = size.x;
        _screenHeight = size.y;
        _density = getResources().getDisplayMetrics().density;

        _percentCorrect = 0;
        _totalCorrect = 0;
        _totalScore = 0;

        double perCorrect = (double)_screenHeight * .1;
        _percentCorrect = (int) (perCorrect * 100);
        //TableRow.LayoutParams bottomBackgroundParams = new TableRow.LayoutParams((int) perCorrect, TableRow.LayoutParams.FILL_PARENT);
        //_ivBottomBackground.setLayoutParams(bottomBackgroundParams);

        _llSettings = (LinearLayout) findViewById(R.id.rlSettings);
        _llDialogInstructions = (LinearLayout) findViewById(R.id.llDialogInstructions);
        _llDialogFlashCards = (LinearLayout) findViewById(R.id.llDialogFlashCards);
        _llSettingsButton = (LinearLayout) findViewById(R.id.llSettingsButton);
        _llSettingsButton.setOnTouchListener(_scaleAnimation.onTouchListener);
        _llSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MatchingActivity.this, "Please start the game first.", Toast.LENGTH_SHORT).show();
            }
        });

        _tvLetter = (TextView) findViewById(R.id.tvLetter);
        _tvLetterSound = (TextView) findViewById(R.id.tvLetterSound);
        _tvLetter.setText(getIntent().getStringExtra("LETTER"));
        _tvLetterSound.setText(getIntent().getStringExtra("LETTERSOUND"));
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
        _tvSettings = (TextView) findViewById(R.id.tvSettings);
        _tvCardWord1 = (TextView) findViewById(R.id.tvCardWord1);
        _tvCardWord2 = (TextView) findViewById(R.id.tvCardWord2);
        _tvCardWord3 = (TextView) findViewById(R.id.tvCardWord3);
        _tvCardWord4 = (TextView) findViewById(R.id.tvCardWord4);
        _tvCardWord5 = (TextView) findViewById(R.id.tvCardWord5);
        _tvCardWord6 = (TextView) findViewById(R.id.tvCardWord6);
        _tvCardWord7 = (TextView) findViewById(R.id.tvCardWord7);
        _tvCardWord8 = (TextView) findViewById(R.id.tvCardWord8);
        _tvCardWord9 = (TextView) findViewById(R.id.tvCardWord9);
        _tvCardWord10 = (TextView) findViewById(R.id.tvCardWord10);
        _tvCardWord11 = (TextView) findViewById(R.id.tvCardWord11);
        _tvCardWord12 = (TextView) findViewById(R.id.tvCardWord12);

        _vGraphBackground = findViewById(R.id.vGraphBackground);
        _vGraphScale = findViewById(R.id.vGraphScale);

        _tvLetter.setTypeface(_typeFaceAnja);

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
        _unMatchCardsFlippedOverList = new ArrayList<>();
        _matchedCardsList = new ArrayList<>();
        _tvStart = (TextView) findViewById(R.id.tvStart);
        _tvStart.setOnTouchListener(_scaleAnimation.onTouchListener);
        _tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
        _tvStartText = (TextView) findViewById(R.id.tvStartText);
        _tvStartText.setVisibility(View.INVISIBLE);
        _incorrectImageNameIdList = new ArrayList<>();
        _correctImageNameIdList = new ArrayList<>();
        _approximateImageNameIdList = new ArrayList<>();
        _scoreHelper = new ScoreHelper();
        _currentUserName = "";
        _linearLayoutUserList = new ArrayList<>();
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
        if(_soundName.equals("r")){
            _ivBeginningArrow.setVisibility(View.VISIBLE);
            _ivBeginningArrow.setRotation(0);
            _ivMiddleArrow.setVisibility(View.VISIBLE);
            _ivMiddleArrow.setRotation(0);
            _ivEndingArrow.setVisibility(View.VISIBLE);
            _ivEndingArrow.setRotation(0);
        }
        if(_soundName.equals("y")){
            _llBlends.setVisibility(View.INVISIBLE);
            _llMiddle.setVisibility(View.INVISIBLE);
            _llEnding.setVisibility(View.INVISIBLE);
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
        if(!checkIfTablet()) {
            _tvLetter.setTextSize(getTextSize(30));
            _tvLetterSound.setTextSize(getTextSize(20));
            _tvDone.setTextSize(getTextSize(13));
            _tvSettings.setTextSize(getTextSize(13));
            _tvMirror.setTextSize(getTextSize(13));
            _tvStart.setTextSize(getTextSize(35));
            //_tvWordType.setTextSize(getTextSize(18));
            //_tvWord.setTextSize(getTextSize(40));
        }
    }

    public void startGame(){
        _llSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsDialog();
            }
        });
        _ivMirror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _settingsIsMirrorFunctionOn = _settingsHelper.getSettingsIsMirrorFunctionOn(MatchingActivity.this);
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
        populateSoundResources();
        _llStart.setVisibility(View.GONE);
        if(_settingsAreScoringButtonsVisible){
            _llGraphButtons.setVisibility(View.VISIBLE);
        } else {
            _llGraphButtons.setVisibility(View.INVISIBLE);
        }
        setMatches();
        _rlBeginningOptions.setVisibility(View.GONE);
        _llMatchingGame.setVisibility(View.VISIBLE);
        _llBeginning.setOnClickListener(null);
        _llMiddle.setOnClickListener(null);
        _llEnding.setOnClickListener(null);
        _llBeginning.setOnTouchListener(null);
        _llMiddle.setOnTouchListener(null);
        _llEnding.setOnTouchListener(null);
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
            if(checkIfEnoughSoundsAreChecked()) {
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
            if(checkIfEnoughSoundsAreChecked()) {
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
            if(checkIfEnoughSoundsAreChecked()) {
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
            if(checkIfEnoughSoundsAreChecked()) {
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
            if(checkIfEnoughSoundsAreChecked()) {
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
            if(checkIfEnoughSoundsAreChecked()) {
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
            if(checkIfEnoughSoundsAreChecked()) {
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
            if(checkIfEnoughSoundsAreChecked()) {
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
            if(checkIfEnoughSoundsAreChecked()) {
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
            if(checkIfEnoughSoundsAreChecked()) {
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
        if(tvBRSelected || tvDRSelected || tvFRSelected || tvGRSelected || tvKRSelected || tvPRSelected ||tvTRSelected || tvRSelected){
            _llBlends.setBackgroundResource(R.drawable.rounded_corners_textbox_darkblue);
            _tvBlends.setTextColor(Color.parseColor("#FFFFFF"));
            _ivBlendsArrow.setImageResource(R.drawable.white_caret);
            _ivBlendsIcon.setImageResource(R.drawable.checkmark);
            if(checkIfEnoughSoundsAreChecked()) {
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
            if(checkIfEnoughSoundsAreChecked()) {
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
                    if(checkIfEnoughSoundsAreChecked()) {
                        _llStart.setVisibility(View.VISIBLE);
                    }
                } else {
                    _tvStart.setOnTouchListener(null);
                    _llStart.setVisibility(View.INVISIBLE);
                }
            } else {
                _tvStart.setOnTouchListener(_scaleAnimation.onTouchListener);
                if(checkIfEnoughSoundsAreChecked()) {
                    _llStart.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void openMirror(){
        if(_okayToOpenMirror) {
            Intent myIntent = new Intent(MatchingActivity.this, MirrorActivity.class);
            myIntent.putExtra("IMAGENAME", _currentWord);
            MatchingActivity.this.startActivity(myIntent);
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
                    Toast.makeText(MatchingActivity.this, "Please enter feedback", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(MatchingActivity.this, "Thank you for your feedback!!", Toast.LENGTH_SHORT).show();
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MatchingActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void initializeDatabaseResources(){
        _soundPositionNameList = new ArrayList<>();
        _soundImageIdList = new ArrayList<>();
        _imageDrawableList = new ArrayList<>();
        _imageAudioList = new ArrayList<>();
        _soundPositionNameList = new ArrayList<>();
        _imageWordList = new ArrayList<>();
        _soundWordTypeList = new ArrayList<>();
        _imageListPosition = 0;
    }

    public void populateSoundResources(){
        ArrayList<Integer> soundIds = _databaseOperationsHelper.getListOfImageIds(_databaseOperationsHelper, _listOfSoundNamesForGame, _soundPositionNameList);
        _soundImageIdList = getSixRandomIds(soundIds);
        _imageDrawableList = _databaseOperationsHelper.getListOfImages(this, _databaseOperationsHelper, _soundImageIdList);
        _imageAudioList = _databaseOperationsHelper.getListOfImageAudioPaths(this, _databaseOperationsHelper, _soundImageIdList);
        _imageWordList = _databaseOperationsHelper.getListOfImageNames(_databaseOperationsHelper, _soundImageIdList);
        _soundWordTypeList = _databaseOperationsHelper.getListOfWordTypes(_databaseOperationsHelper, _soundImageIdList);
        _imageListSize = _imageDrawableList.size();
    }

    public ArrayList<Integer> getSixRandomIds(ArrayList<Integer> listOfAllIds){
        ArrayList<Integer> sixRandomIds = new ArrayList<>();
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
            sixRandomIds.add(listOfAllIds.get(randomNumber));
        }
        return sixRandomIds;
    }

    public void setMatches(){
        populateSoundResources();
        //2nd: build a list 1-12 (0-13)  0,0| 1,6 | 2,5 | 3,6 | 4,5 | 5,2 | 6,2 | etc
        _imageNumberList = new ArrayList<Integer>();
        _imageNumberList = getListOfRandomNumbers(1, 12);
        _matchNumbersMap = getRandomMatchNumbersMap(1, 12);
        ArrayList<Integer> imagesDoneList = new ArrayList<>();
        int realIncrementer = 0;
        for(int i = 1; i <= _matchNumbersMap.size(); i++){
           if(!imagesDoneList.contains(i)) {
               int alternateNumber = _matchNumbersMap.get(i);
               try {
                   setCardWordAndImage(i, _imageDrawableList.get(realIncrementer), _imageWordList.get(realIncrementer), _imageAudioList.get(realIncrementer), _soundWordTypeList.get(realIncrementer));
                   setCardWordAndImage(alternateNumber, _imageDrawableList.get(realIncrementer), _imageWordList.get(realIncrementer), _imageAudioList.get(realIncrementer), _soundWordTypeList.get(realIncrementer));
               } catch (Exception e){
                   e.printStackTrace();
               }
               imagesDoneList.add(i);
               imagesDoneList.add(alternateNumber);
               realIncrementer++;
           }
        }
    }

    public HashMap<Integer, Integer> getRandomMatchNumbersMap(int min, int max){
        HashMap<Integer, Integer> randomMatchNumbersMap = new HashMap<>();
        Random random = new Random();
        for(int i = 1; i <= 12; i++){
            int randomNumber = random.nextInt(max - min + 1) + min;
            while(randomMatchNumbersMap.containsKey(randomNumber) || randomNumber == i){
                randomNumber = random.nextInt(max - min + 1) + min;
                if(randomMatchNumbersMap.size() >= 12){
                    break;
                }
            }
            if(!randomMatchNumbersMap.containsKey(i)) {
                randomMatchNumbersMap.put(i, randomNumber);
                randomMatchNumbersMap.put(randomNumber, i);
            }
        }
        return randomMatchNumbersMap;
    }

    public ArrayList<Integer> getListOfRandomNumbers(int min, int max){
        ArrayList<Integer> listOfRandomNumbers = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < max; i++){
            int randomNumber = random.nextInt(max - min + 1) + min;
            while(listOfRandomNumbers.contains(randomNumber)){
                randomNumber = random.nextInt(max - min + 1) + min;
            }
            listOfRandomNumbers.add(randomNumber);
        }
        return listOfRandomNumbers;
    }

    public void setCardWordAndImage(int cardNumber, String cardDrawable, String cardWord, int cardAudio, String cardWordType){
        _matchImagesMap.put(cardNumber, cardDrawable);
        _matchAudioMap.put(cardNumber, cardAudio);
        _matchWordsMap.put(cardNumber, cardWord);
        _matchWordTypesMap.put(cardNumber, cardWordType);
        int imageWidth = 120;
        int imageHeight = 100;
        if(checkIfTablet()){
            imageWidth = 200;
            imageHeight = 180;
        }
        Bitmap bitmap = _fileHelper.getBitmapFromInternalStorage(this, cardDrawable);
        switch (cardNumber){
            case 1:
                _ivCardImage1.setImageBitmap(Bitmap.createScaledBitmap(bitmap,imageWidth,imageHeight,false));
                _tvCardWord1.setText(cardWord);
                break;
            case 2:
                _ivCardImage2.setImageBitmap(Bitmap.createScaledBitmap(bitmap,imageWidth,imageHeight,false));
                _tvCardWord2.setText(cardWord);
                break;
            case 3:
                _ivCardImage3.setImageBitmap(Bitmap.createScaledBitmap(bitmap,imageWidth,imageHeight,false));
                _tvCardWord3.setText(cardWord);
                break;
            case 4:
                _ivCardImage4.setImageBitmap(Bitmap.createScaledBitmap(bitmap,imageWidth,imageHeight,false));
                _tvCardWord4.setText(cardWord);
                break;
            case 5:
                _ivCardImage5.setImageBitmap(Bitmap.createScaledBitmap(bitmap,imageWidth,imageHeight,false));
                _tvCardWord5.setText(cardWord);
                break;
            case 6:
                _ivCardImage6.setImageBitmap(Bitmap.createScaledBitmap(bitmap,imageWidth,imageHeight,false));
                _tvCardWord6.setText(cardWord);
                break;
            case 7:
                _ivCardImage7.setImageBitmap(Bitmap.createScaledBitmap(bitmap,imageWidth,imageHeight,false));
                _tvCardWord7.setText(cardWord);
                break;
            case 8:
                _ivCardImage8.setImageBitmap(Bitmap.createScaledBitmap(bitmap,imageWidth,imageHeight,false));
                _tvCardWord8.setText(cardWord);
                break;
            case 9:
                _ivCardImage9.setImageBitmap(Bitmap.createScaledBitmap(bitmap,imageWidth,imageHeight,false));
                _tvCardWord9.setText(cardWord);
                break;
            case 10:
                _ivCardImage10.setImageBitmap(Bitmap.createScaledBitmap(bitmap,imageWidth,imageHeight,false));
                _tvCardWord10.setText(cardWord);
                break;
            case 11:
                _ivCardImage11.setImageBitmap(Bitmap.createScaledBitmap(bitmap,imageWidth,imageHeight,false));
                _tvCardWord11.setText(cardWord);
                break;
            case 12:
                _ivCardImage12.setImageBitmap(Bitmap.createScaledBitmap(bitmap,imageWidth,imageHeight,false));
                _tvCardWord12.setText(cardWord);
                break;
        }
    }

    public boolean checkIfAMatch(){
        boolean isAMatch = false;
        //_unMatchCardsFlippedOverList
        //_matchNumbersMap
        int match1 = _unMatchCardsFlippedOverList.get(0);
        int match2 = _unMatchCardsFlippedOverList.get(1);
        if(_matchNumbersMap.get(match1) == match2){
            isAMatch = true;
            _matchedCardsList.add(match1);
            _matchedCardsList.add(match2);
        }
        return isAMatch;
    }

    public void flipCard(int cardNumber){
        Animation flipCardAnimation = AnimationUtils.loadAnimation(this, R.anim.flip_card_left);
        flipCardAnimation.setFillAfter(false);
        //If a person has 0 filled over, okay
        //If a person has 1 flipped over, okay
        //If a person has 2 flipped over:
        //      #1 Correct: Show correct animation
        //      #2 Incorrect: flip other two cards back over and then turn that card over.
        if(_numberOfUnMatchedCardsFlippedOver == 2){
            //flip other two cards back, turn new one up
            flipCardBack(_unMatchCardsFlippedOverList.get(0));
            flipCardBack(_unMatchCardsFlippedOverList.get(0));
            _numberOfUnMatchedCardsFlippedOver = 0;
            _unMatchCardsFlippedOverList = new ArrayList<>();
        }
        _unMatchCardsFlippedOverList.add(cardNumber);
        _numberOfUnMatchedCardsFlippedOver++;
        switch(cardNumber){
            case 1:
                flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        _llMatchingCard1.setVisibility(View.GONE);
                        _llMatchingCardFlip1.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                _llMatchingCard1.startAnimation(flipCardAnimation);
                break;
            case 2:
                flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        _llMatchingCard2.setVisibility(View.GONE);
                        _llMatchingCardFlip2.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                _llMatchingCard2.startAnimation(flipCardAnimation);
                break;
            case 3:
                flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        _llMatchingCard3.setVisibility(View.GONE);
                        _llMatchingCardFlip3.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                _llMatchingCard3.startAnimation(flipCardAnimation);
                break;
            case 4:
                flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        _llMatchingCard4.setVisibility(View.GONE);
                        _llMatchingCardFlip4.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                _llMatchingCard4.startAnimation(flipCardAnimation);
                break;
            case 5:
                flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        _llMatchingCard5.setVisibility(View.GONE);
                        _llMatchingCardFlip5.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                _llMatchingCard5.startAnimation(flipCardAnimation);
                break;
            case 6:
                flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        _llMatchingCard6.setVisibility(View.GONE);
                        _llMatchingCardFlip6.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                _llMatchingCard6.startAnimation(flipCardAnimation);
                break;
            case 7:
                flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        _llMatchingCard7.setVisibility(View.GONE);
                        _llMatchingCardFlip7.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                _llMatchingCard7.startAnimation(flipCardAnimation);
                break;
            case 8:
                flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        _llMatchingCard8.setVisibility(View.GONE);
                        _llMatchingCardFlip8.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                _llMatchingCard8.startAnimation(flipCardAnimation);
                break;
            case 9:
                flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        _llMatchingCard9.setVisibility(View.GONE);
                        _llMatchingCardFlip9.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                _llMatchingCard9.startAnimation(flipCardAnimation);
                break;
            case 10:
                flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        _llMatchingCard10.setVisibility(View.GONE);
                        _llMatchingCardFlip10.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                _llMatchingCard10.startAnimation(flipCardAnimation);
                break;
            case 11:
                flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        _llMatchingCard11.setVisibility(View.GONE);
                        _llMatchingCardFlip11.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                _llMatchingCard11.startAnimation(flipCardAnimation);
                break;
            case 12:
                flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        _llMatchingCard12.setVisibility(View.GONE);
                        _llMatchingCardFlip12.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                _llMatchingCard12.startAnimation(flipCardAnimation);
                break;
        }

        if(_numberOfUnMatchedCardsFlippedOver == 2){
            if(checkIfAMatch()){
                startMatchMadeAnimation();
                _numberOfUnMatchedCardsFlippedOver = 0;
                _unMatchCardsFlippedOverList.clear(); //TODO: Make sure this clears it

            }
        }
    }

    public void flipCardBack(int cardNumber){
        if(!_matchedCardsList.contains(cardNumber)){
            _numberOfUnMatchedCardsFlippedOver--;
            for(int i = 0; i < _unMatchCardsFlippedOverList.size(); i++){
                if(cardNumber == _unMatchCardsFlippedOverList.get(i)){
                    _unMatchCardsFlippedOverList.remove(i);
                }
            }
            Animation flipCardAnimation = AnimationUtils.loadAnimation(this, R.anim.flip_card_left);
            flipCardAnimation.setFillAfter(false);
            switch(cardNumber){
                case 1:
                    flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            _llMatchingCardFlip1.setVisibility(View.GONE);
                            _llMatchingCard1.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    _llMatchingCardFlip1.startAnimation(flipCardAnimation);
                    break;
                case 2:
                    flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            _llMatchingCardFlip2.setVisibility(View.GONE);
                            _llMatchingCard2.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    _llMatchingCardFlip2.startAnimation(flipCardAnimation);
                    break;
                case 3:
                    flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            _llMatchingCardFlip3.setVisibility(View.GONE);
                            _llMatchingCard3.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    _llMatchingCardFlip3.startAnimation(flipCardAnimation);
                    break;
                case 4:
                    flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            _llMatchingCardFlip4.setVisibility(View.GONE);
                            _llMatchingCard4.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    _llMatchingCardFlip4.startAnimation(flipCardAnimation);
                    break;
                case 5:
                    flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            _llMatchingCardFlip5.setVisibility(View.GONE);
                            _llMatchingCard5.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    _llMatchingCardFlip5.startAnimation(flipCardAnimation);
                    break;
                case 6:
                    flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            _llMatchingCardFlip6.setVisibility(View.GONE);
                            _llMatchingCard6.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    _llMatchingCardFlip6.startAnimation(flipCardAnimation);
                    break;
                case 7:
                    flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            _llMatchingCardFlip7.setVisibility(View.GONE);
                            _llMatchingCard7.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    _llMatchingCardFlip7.startAnimation(flipCardAnimation);
                    break;
                case 8:
                    flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            _llMatchingCardFlip8.setVisibility(View.GONE);
                            _llMatchingCard8.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    _llMatchingCardFlip8.startAnimation(flipCardAnimation);
                    break;
                case 9:
                    flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            _llMatchingCardFlip9.setVisibility(View.GONE);
                            _llMatchingCard9.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    _llMatchingCardFlip9.startAnimation(flipCardAnimation);
                    break;
                case 10:
                    flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            _llMatchingCardFlip10.setVisibility(View.GONE);
                            _llMatchingCard10.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    _llMatchingCardFlip10.startAnimation(flipCardAnimation);
                    break;
                case 11:
                    flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            _llMatchingCardFlip11.setVisibility(View.GONE);
                            _llMatchingCard11.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    _llMatchingCardFlip11.startAnimation(flipCardAnimation);
                    break;
                case 12:
                    flipCardAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            _llMatchingCardFlip12.setVisibility(View.GONE);
                            _llMatchingCard12.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    _llMatchingCardFlip12.startAnimation(flipCardAnimation);
                    break;
            }
        }
    }

    public void finishLevel(){
        //TODO: Animation Station!
        _llMatchingGame.setVisibility(View.GONE);
        _rlDialogFlashCardsGoodJob.setVisibility(View.VISIBLE);
        _llGraphButtons.setVisibility(View.INVISIBLE);
        if(_databaseOperationsHelper.getSettingValue(_databaseOperationsHelper, "voice_on") == 1) {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.success);
            mp.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        }
//        _rlDialogFlashCards.setVisibility(View.GONE);
//        _rlDialogFlashCardsGoodJob.setVisibility(View.VISIBLE);
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
        if(_wordType != null){
            _totalScore++;
            String currentImageName = _currentWord;
            //int currentImageId = _databaseOperationsHelper.getSoundImageId(_databaseOperationsHelper, currentImageName, _soundName);
            String wordType = _wordType.split(" - ")[1];
            String soundName = _wordType.split(" - ")[0];
//        int altCurrentImageId = _databaseOperationsHelper.getSoundImageId(_databaseOperationsHelper, currentImageName, soundName);
//        int alt2ImageId = _soundImageIdList.get(_imageListPosition);
            int currentImageId = _databaseOperationsHelper.getSoundImageId(_databaseOperationsHelper, currentImageName, soundName, wordType);
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
            if(!checkIfTablet()) {
                _tvRatioCorrect.setTextSize(getTextSize(18));
            }
        } else {
            Toast.makeText(MatchingActivity.this, "Find a match first!", Toast.LENGTH_SHORT);
        }
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
                            Toast.makeText(MatchingActivity.this, "Score Added!", Toast.LENGTH_SHORT);
                            if(!restart){
                                goToMenu();
                            } else {
                                Intent myIntent = new Intent(MatchingActivity.this, MatchingActivity.class);
                                myIntent.putExtra("LETTER", _soundName);
                                myIntent.putExtra("LETTERSOUND", _soundName);
                                MatchingActivity.this.startActivity(myIntent);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                finish();
                            }
                        } else {
                            Toast.makeText(MatchingActivity.this, "Score was not added :(", Toast.LENGTH_SHORT);
                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MatchingActivity.this);
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
            TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
            tvTitle.setTypeface(_typeFaceAnja);
            if(!checkIfTablet()){
                tvTitle.setTextSize(getTextSize(30));
                tvScoreRatio.setTextSize(getTextSize(35));
                tvScorePercent.setTextSize(getTextSize(35));
                tvDontSave.setTextSize(getTextSize(30));
                tvSave.setTextSize(getTextSize(30));
            }
            dialog.show();
        } else {
            if(!restart){
                goToMenu();
            } else {
                Intent myIntent = new Intent(MatchingActivity.this, MatchingActivity.class);
                myIntent.putExtra("LETTER", _soundName);
                myIntent.putExtra("LETTERSOUND", _soundName);
                MatchingActivity.this.startActivity(myIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();
            }
        }
    }

    public boolean saveScore(String username){
        return _scoreHelper.saveScore(this, username, _correctImageNameIdList, _approximateImageNameIdList, _incorrectImageNameIdList, "matching");
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
                    Toast.makeText(MatchingActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MatchingActivity.this, etUserName.getText().toString() + " added as a new user!", Toast.LENGTH_SHORT).show();
                        addUserNamesLayouts(parentDialog, parentLinearLayout);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(MatchingActivity.this, etUserName.getText().toString() + " already exists, updated user info.", Toast.LENGTH_SHORT).show();
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

    public void playCorrectSound(){
        if(_databaseOperationsHelper.getSettingValue(_databaseOperationsHelper, "scoring_sounds") == 1) {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.correct);
            mp.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        }
    }

    public void playIncorrectSound(){
        if(_databaseOperationsHelper.getSettingValue(_databaseOperationsHelper, "scoring_sounds") == 1) {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.incorrect);
            mp.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
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

    public void startMatchMadeAnimation(){
        //A MATCH!
        String word = _matchWordsMap.get(_unMatchCardsFlippedOverList.get(0));
        _currentWord = word;
        _okayToOpenMirror = true;
        String wordType = _matchWordTypesMap.get(_unMatchCardsFlippedOverList.get(0));
        String imageDrawable = _matchImagesMap.get(_unMatchCardsFlippedOverList.get(0));
        int imageSound = _matchAudioMap.get(_unMatchCardsFlippedOverList.get(0));
        _wordType = wordType;
        showMatchDialog(word, imageDrawable, imageSound, wordType);
    }

    public void showMatchDialog(final String word, final String imageDrawable, final int imageSound, final String wordType) {
        _tvCorrect.setVisibility(View.GONE);
        _tvWrong.setVisibility(View.GONE);
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
        tvWord.setText(word);
        TextView tvWordType = (TextView) dialog.findViewById(R.id.tvWordType);
        tvWordType.setText(wordType);
        ImageView ivSoundImage = (ImageView) dialog.findViewById(R.id.ivSoundImage);
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
        LinearLayout llGraphButtons = (LinearLayout) dialog.findViewById(R.id.llGraphButtons);
        if(_settingsAreScoringButtonsVisible){
            llGraphButtons.setVisibility(View.VISIBLE);
        } else {
            llGraphButtons.setVisibility(View.INVISIBLE);
        }
        Bitmap bitmap = _fileHelper.getBitmapFromInternalStorage(this, imageDrawable);
        ivSoundImage.setImageBitmap(bitmap);
        try{
            ContextWrapper cw = new ContextWrapper(MatchingActivity.this);
            File directory = cw.getDir("audioDir", Context.MODE_PRIVATE);
//                            String path = directory.getPath() + "/" + _imageDrawableList.get(_imageListPosition) + ".mp3";
            File mypath = new File(directory.getPath() + "/",word.toLowerCase().replace(" ","").replace("-","").replace("(","").replace(")","") + ".mp3");
            final MediaPlayer mp = MediaPlayer.create(MatchingActivity.this, Uri.fromFile(mypath));
            ivSoundImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if(_databaseOperationsHelper.getSettingValue(_databaseOperationsHelper, "voice_on") == 1) {
                    if (mp != null) {
                        if(!mp.isPlaying()) {
                            mp.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
                            mp.start();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Sound does not exist for word: " + word, Toast.LENGTH_SHORT).show();
                    }
                }
                }
            });
        } catch(Exception e){
            e.printStackTrace();
        }

        ivClose.setOnTouchListener(_scaleAnimation.onTouchListener);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().getDecorView().setSystemUiVisibility(MatchingActivity.this.getWindow().getDecorView().getSystemUiVisibility());
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        //rlDialogBackground.setVisibility(View.VISIBLE);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(_matchedCardsList.size() == 12){
                    finishLevel();
                } else {
                    _tvCorrect.setVisibility(View.VISIBLE);
                    _tvWrong.setVisibility(View.VISIBLE);
                }
            }
        });
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
                _settingsAreScoringButtonsVisible = _settingsHelper.getSettingsAreScoringButtonsVisible(MatchingActivity.this);
                _settingsIsMirrorFunctionOn = _settingsHelper.getSettingsIsMirrorFunctionOn(MatchingActivity.this);
                if (_settingsAreScoringButtonsVisible) {
                    _llGraphButtons.setVisibility(View.VISIBLE);
                } else {
                    _llGraphButtons.setVisibility(View.INVISIBLE);
                }
                if (_settingsIsMirrorFunctionOn) {
                    _llMirrorButton.setVisibility(View.VISIBLE);
                } else {
                    _llMirrorButton.setVisibility(View.INVISIBLE);
                }
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                _decorView.setSystemUiVisibility(_uiOptions);
                _settingsAreScoringButtonsVisible = _settingsHelper.getSettingsAreScoringButtonsVisible(MatchingActivity.this);
                _settingsIsMirrorFunctionOn = _settingsHelper.getSettingsIsMirrorFunctionOn(MatchingActivity.this);
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
        Intent myIntent = new Intent(MatchingActivity.this, MenuActivity.class);
        MatchingActivity.this.startActivity(myIntent);
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
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }
}
