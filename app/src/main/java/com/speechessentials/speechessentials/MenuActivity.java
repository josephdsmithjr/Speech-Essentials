package com.speechessentials.speechessentials;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.speechessentials.speechessentials.Utility.DatabaseOperationsHelper;
import com.speechessentials.speechessentials.Utility.FileHelper;
import com.speechessentials.speechessentials.Utility.GooglePlayHelper;
import com.speechessentials.speechessentials.Utility.LayoutHelper;
import com.speechessentials.speechessentials.Utility.SoundHelper;
import com.speechessentials.speechessentials.Utility.StorageHelper;
import com.speechessentials.speechessentials.Utility.SystemUiScaleAnimation;
import com.speechessentials.speechessentials.Utility.TipsHelper;
import com.speechessentials.speechessentials.util.IabHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class MenuActivity extends Activity {

    Animation animation1, animation2;
    Typeface typeFaceAnja, typeFaceBubble;
    View _decorView;
    int _uiOptions, _screenWidth, _screenHeight;
    Dialog _scoresDialog;
    boolean _storeDialogIsShowing, _soundOptionDialogIsShowing;
    Dialog _soundOptionDialog;
    ArrayList<String> _listOfSoundsToDownload;
    Dialog _purchaseDialog, _storeDialog;
    LinearLayout _llSettings, _llScores, _llUpdate, _llUSettings, _llUScores, _llUUpdate, _llNonUpdateLayout, _llUpdateLayout;
    View _vUpdate1, _vUpdate2, _vUUpdate1, _vUUpdate2;
    EditText _etBirthday;
    TextView tvSoundB, tvSoundM, tvSoundK, tvSoundH, tvSoundP, tvSoundN, tvSoundG, tvSoundW, tvSoundT, tvSoundF, tvSoundJ,
            tvSoundY, tvSoundD, tvSoundV, tvSoundCH, tvSoundNG, tvSoundS, tvSoundL, tvSoundSH, tvSoundZH, tvSoundZ, tvSoundR, tvSoundTH;
    TextView tvSoundUnpurchasedM, tvSoundUnpurchasedK, tvSoundUnpurchasedH, tvSoundUnpurchasedP, tvSoundUnpurchasedN, tvSoundUnpurchasedG, tvSoundUnpurchasedW, tvSoundUnpurchasedT, tvSoundUnpurchasedF, tvSoundUnpurchasedJ,
            tvSoundUnpurchasedY, tvSoundUnpurchasedD, tvSoundUnpurchasedV, tvSoundUnpurchasedCH, tvSoundUnpurchasedNG, tvSoundUnpurchasedS, tvSoundUnpurchasedL, tvSoundUnpurchasedSH, tvSoundUnpurchasedZH, tvSoundUnpurchasedZ, tvSoundUnpurchasedR, tvSoundUnpurchasedTH;
    RelativeLayout rlSoundB, rlSoundM, rlSoundK, rlSoundH, rlSoundP, rlSoundN, rlSoundG, rlSoundW, rlSoundT, rlSoundF, rlSoundJ,
            rlSoundY, rlSoundD, rlSoundV, rlSoundCH, rlSoundNG, rlSoundS, rlSoundL, rlSoundSH, rlSoundZH, rlSoundZ, rlSoundR, rlSoundTH;
    RelativeLayout rlSoundUnpurchasedM, rlSoundUnpurchasedK, rlSoundUnpurchasedH, rlSoundUnpurchasedP, rlSoundUnpurchasedN, rlSoundUnpurchasedG, rlSoundUnpurchasedW, rlSoundUnpurchasedT, rlSoundUnpurchasedF, rlSoundUnpurchasedJ,
            rlSoundUnpurchasedY, rlSoundUnpurchasedD, rlSoundUnpurchasedV, rlSoundUnpurchasedCH, rlSoundUnpurchasedNG, rlSoundUnpurchasedS, rlSoundUnpurchasedL, rlSoundUnpurchasedSH, rlSoundUnpurchasedZH, rlSoundUnpurchasedZ, rlSoundUnpurchasedR, rlSoundUnpurchasedTH;
    Button _btPlaceOrder;
    String _googlePlayCode;
    TextView tvCurrentStoreSelected, tvCurrentStoreUnselected;
    TextView tvStoreSoundDescription, tvStoreSoundName;
    MediaPlayer _mp;
    int _numberOfRowsToDownload, _numberOfSoundsToDownload, _currentSoundNumber;
    ImageView _ivThankYou, _ivRobot, _ivSaleLeft, _ivBuyAllSoundsSale, _ivBuySoundSale;
    TextView _tvThankYou, _tvPurchaseIncludes, _tvPleaseWait;
    ProgressBar _soundDownloadSpinnerIndeterminate, _soundDownloadSpinner, _pbApplyProgress;

    LinearLayout _llPromo, _llHeader, _llHeaderSale;
    TextView _tvPromoDescription, _tvPromoPrice, _tvGrandTotalPrice, _tvPurchasePrice;
    View _vPromoSeparator, _vNoPromoFiller;
    String _promoCodeName, _promoCodeDiscount;
    String _currentSoundBeingDownloaded;

    LinearLayout llBuySound;
    LinearLayout llBuyAllSounds;
    TextView tvBuySoundPrice;
    TextView tvBuySoundPriceDescription;
    TextView tvBuyAllSoundsPrice;
    TextView tvBuyAllSoundsPriceDescription, _tvDBVersion;
    String _dbAppVersion, _dbVersion;

    HashMap<TextView, TextView> storeTextViewHashMap;
    ImageView ivStore, ivStoreSale, ivInfo, ivInfoSale, ivLogo, ivLogoSale;
    SystemUiScaleAnimation _scaleAnimation = new SystemUiScaleAnimation();
    RelativeLayout rlDialogBackground;
    ArrayList<Integer> _soundIdList;
    DatabaseOperationsHelper _databaseOperationsHelper;
    String _currentUser;
    IInAppBillingService mService;
    ServiceConnection mServiceConn;
    IabHelper mHelper;
    GooglePlayHelper _googlePlayHelper;
    SoundHelper _soundHelper;
    String _soundToPurchase;
    ProgressBar _pbSpinnerIndeterminate, _pbSpinner;

    ArrayList<String> soundNamesList, _listOfPurchases, _remainingListOfPurchases;
    HashMap<String, String> soundWordTypeMap;
    HashMap<String, Bitmap> soundImageMap;
    HashMap<String, FileInputStream> soundAudioMap;
    FileHelper _fileHelper;
    LayoutHelper _layoutHelper;
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    boolean mStopHandler = false;
    Handler mHandler = new Handler();
    int mHandlerTime;
    long _totalAvailableStorageOnDeviceInBytes, _requiredSpace;
    String _commaSeparatedListOfPurchasedSounds;
    StorageHelper _storageHelper;
    private ResultSet resultSet2;
    boolean _discountOn, _userMadeAPurchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_linear);
        _decorView = getWindow().getDecorView();
        _uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        _decorView.setSystemUiVisibility(_uiOptions);
        _decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        // Note that system bars will only be "visible" if none of the
                        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            // TODO: The system bars are visible. Make any desired
                            // adjustments to your UI, such as showing the action bar or
                            // other navigational controls.
                            _decorView.setSystemUiVisibility(_uiOptions);
                        } else {
                            // TODO: The system bars are NOT visible. Make any desired
                            // adjustments to your UI, such as hiding the action bar or
                            // other navigational controls.
                            _decorView.setSystemUiVisibility(_uiOptions);
                        }
                    }
                });
        initVariables();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        checkForNewDBUpdates();
        setGoogleAnalytics();
        notifyUsers("Speech Essentials","Speech Essentials is great!");
    }

    private void notifyUsers(String notificationTitle, String notificationMessage){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        @SuppressWarnings("deprecation")

        Notification notification = new Notification(R.drawable.robot_head ,"New Message", System.currentTimeMillis());
        Intent notificationIntent = new Intent(this, SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        notification.setLatestEventInfo(MenuActivity.this, notificationTitle, notificationMessage, pendingIntent);
        notificationManager.notify(9999, notification);
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
        tracker.setScreenName("Menu Activity");
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

    public void setGoogleAnalytics(String screenName){
        analytics.setLocalDispatchPeriod(1800);
        tracker = analytics.newTracker("UA-65570011-1");
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
        // All subsequent hits will be send with screen name = "main screen"
        tracker.setScreenName(screenName);
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

    public void initVariables() {
        _fileHelper = new FileHelper();
        _layoutHelper = new LayoutHelper();
        _googlePlayHelper = new GooglePlayHelper();
        _soundHelper = new SoundHelper();
        _storeDialogIsShowing = false;
        _soundOptionDialogIsShowing = false;
        _userMadeAPurchase = false;
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density = getResources().getDisplayMetrics().density;
        _dpHeight = outMetrics.heightPixels / density;//411.42856
        _dpWidth = outMetrics.widthPixels / density;//689.4286
        Point size = new Point();
        display.getSize(size);
        _screenWidth = size.x;
        _screenHeight = size.y;
        _databaseOperationsHelper = new DatabaseOperationsHelper(this);
        animation1 = AnimationUtils.loadAnimation(this, R.anim.flip_up);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.flip_down);
        typeFaceAnja = Typeface.createFromAsset(getAssets(), "font/anja.ttf");
        typeFaceBubble = Typeface.createFromAsset(getAssets(), "font/bubbleboddy.ttf");
        _ivRobot = (ImageView) findViewById(R.id.ivDog);
        ivStore = (ImageView) findViewById(R.id.ivStore);
        ivInfo = (ImageView) findViewById(R.id.ivInfo);
        _llHeader = (LinearLayout) findViewById(R.id.llHeader);
        _llHeader.setVisibility(View.VISIBLE);
        ivLogo = (ImageView) findViewById(R.id.ivLogo);
//        ivLogo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myIntent = new Intent(MenuActivity.this, SoundsActivity.class);
//                MenuActivity.this.startActivity(myIntent);
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
//                finish();
//            }
//        });
        rlDialogBackground = (RelativeLayout) findViewById(R.id.rlDialogBackground);
        ivStore.setOnTouchListener(_scaleAnimation.onTouchListener);
        ivStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("m");
            }
        });
        ivInfo.setOnTouchListener(_scaleAnimation.onTouchListener);
        ivInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfoDialog();
            }
        });
        _llScores = (LinearLayout) findViewById(R.id.llScores);
        _llScores.setOnTouchListener(_scaleAnimation.onTouchListener);
        _llScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScoresDialog();
            }
        });
        _llUScores = (LinearLayout) findViewById(R.id.llUScores);
        _llUScores.setOnTouchListener(_scaleAnimation.onTouchListener);
        _llUScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScoresDialog();
            }
        });
        _llUpdateLayout = (LinearLayout) findViewById(R.id.llUpdateLayout);
        _llNonUpdateLayout = (LinearLayout) findViewById(R.id.llNonUpdateLayout);
        _llUpdateLayout.setVisibility(View.GONE);
        _llNonUpdateLayout.setVisibility(View.VISIBLE);
        _vUpdate1 = findViewById(R.id.vUpdate1);
        _vUpdate2 = findViewById(R.id.vUpdate2);
        _llSettings = (LinearLayout) findViewById(R.id.llSettings);
        _llSettings.setOnTouchListener(_scaleAnimation.onTouchListener);
        _llSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsDialog();
            }
        });
        _llUpdate = (LinearLayout) findViewById(R.id.llUpdate);
        _llUpdate.setOnTouchListener(_scaleAnimation.onTouchListener);
        _llUUpdate = (LinearLayout) findViewById(R.id.llUUpdate);
        _llUUpdate.setOnTouchListener(_scaleAnimation.onTouchListener);
        _vUUpdate1 = findViewById(R.id.vUUpdate1);
        _vUUpdate2 = findViewById(R.id.vUUpdate2);
        _llUSettings = (LinearLayout) findViewById(R.id.llUSettings);
        _llUSettings.setOnTouchListener(_scaleAnimation.onTouchListener);
        _llUSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsDialog();
            }
        });

        tvSoundB = (TextView) findViewById(R.id.tvSoundB);
        tvSoundM = (TextView) findViewById(R.id.tvSoundM);
        tvSoundK = (TextView) findViewById(R.id.tvSoundK);
        tvSoundH = (TextView) findViewById(R.id.tvSoundH);
        tvSoundP = (TextView) findViewById(R.id.tvSoundP);
        tvSoundN = (TextView) findViewById(R.id.tvSoundN);
        tvSoundG = (TextView) findViewById(R.id.tvSoundG);
        tvSoundW = (TextView) findViewById(R.id.tvSoundW);
        tvSoundT = (TextView) findViewById(R.id.tvSoundT);
        tvSoundF = (TextView) findViewById(R.id.tvSoundF);
        tvSoundJ = (TextView) findViewById(R.id.tvSoundJ);
        tvSoundY = (TextView) findViewById(R.id.tvSoundY);
        tvSoundD = (TextView) findViewById(R.id.tvSoundD);
        tvSoundV = (TextView) findViewById(R.id.tvSoundV);
        tvSoundCH = (TextView) findViewById(R.id.tvSoundCH);
        tvSoundNG = (TextView) findViewById(R.id.tvSoundNG);
        tvSoundS = (TextView) findViewById(R.id.tvSoundS);
        tvSoundL = (TextView) findViewById(R.id.tvSoundL);
        tvSoundSH = (TextView) findViewById(R.id.tvSoundSH);
        tvSoundZH = (TextView) findViewById(R.id.tvSoundZH);
        tvSoundZ = (TextView) findViewById(R.id.tvSoundZ);
        tvSoundR = (TextView) findViewById(R.id.tvSoundR);
        tvSoundTH = (TextView) findViewById(R.id.tvSoundTH);
        rlSoundB = (RelativeLayout) findViewById(R.id.rlSoundB);
        rlSoundM = (RelativeLayout) findViewById(R.id.rlSoundM);
        rlSoundK = (RelativeLayout) findViewById(R.id.rlSoundK);
        rlSoundH = (RelativeLayout) findViewById(R.id.rlSoundH);
        rlSoundP = (RelativeLayout) findViewById(R.id.rlSoundP);
        rlSoundN = (RelativeLayout) findViewById(R.id.rlSoundN);
        rlSoundG = (RelativeLayout) findViewById(R.id.rlSoundG);
        rlSoundW = (RelativeLayout) findViewById(R.id.rlSoundW);
        rlSoundT = (RelativeLayout) findViewById(R.id.rlSoundT);
        rlSoundF = (RelativeLayout) findViewById(R.id.rlSoundF);
        rlSoundJ = (RelativeLayout) findViewById(R.id.rlSoundJ);
        rlSoundY = (RelativeLayout) findViewById(R.id.rlSoundY);
        rlSoundD = (RelativeLayout) findViewById(R.id.rlSoundD);
        rlSoundV = (RelativeLayout) findViewById(R.id.rlSoundV);
        rlSoundCH = (RelativeLayout) findViewById(R.id.rlSoundCH);
        rlSoundNG = (RelativeLayout) findViewById(R.id.rlSoundNG);
        rlSoundS = (RelativeLayout) findViewById(R.id.rlSoundS);
        rlSoundL = (RelativeLayout) findViewById(R.id.rlSoundL);
        rlSoundSH = (RelativeLayout) findViewById(R.id.rlSoundSH);
        rlSoundZH = (RelativeLayout) findViewById(R.id.rlSoundZH);
        rlSoundZ = (RelativeLayout) findViewById(R.id.rlSoundZ);
        rlSoundR = (RelativeLayout) findViewById(R.id.rlSoundR);
        rlSoundTH = (RelativeLayout) findViewById(R.id.rlSoundTH);
        tvSoundUnpurchasedM = (TextView) findViewById(R.id.tvSoundUnpurchasedM);
        tvSoundUnpurchasedK = (TextView) findViewById(R.id.tvSoundUnpurchasedK);
        tvSoundUnpurchasedH = (TextView) findViewById(R.id.tvSoundUnpurchasedH);
        tvSoundUnpurchasedP = (TextView) findViewById(R.id.tvSoundUnpurchasedP);
        tvSoundUnpurchasedN = (TextView) findViewById(R.id.tvSoundUnpurchasedN);
        tvSoundUnpurchasedG = (TextView) findViewById(R.id.tvSoundUnpurchasedG);
        tvSoundUnpurchasedW = (TextView) findViewById(R.id.tvSoundUnpurchasedW);
        tvSoundUnpurchasedT = (TextView) findViewById(R.id.tvSoundUnpurchasedT);
        tvSoundUnpurchasedF = (TextView) findViewById(R.id.tvSoundUnpurchasedF);
        tvSoundUnpurchasedJ = (TextView) findViewById(R.id.tvSoundUnpurchasedJ);
        tvSoundUnpurchasedY = (TextView) findViewById(R.id.tvSoundUnpurchasedY);
        tvSoundUnpurchasedD = (TextView) findViewById(R.id.tvSoundUnpurchasedD);
        tvSoundUnpurchasedV = (TextView) findViewById(R.id.tvSoundUnpurchasedV);
        tvSoundUnpurchasedCH = (TextView) findViewById(R.id.tvSoundUnpurchasedCH);
        tvSoundUnpurchasedNG = (TextView) findViewById(R.id.tvSoundUnpurchasedNG);
        tvSoundUnpurchasedS = (TextView) findViewById(R.id.tvSoundUnpurchasedS);
        tvSoundUnpurchasedL = (TextView) findViewById(R.id.tvSoundUnpurchasedL);
        tvSoundUnpurchasedSH = (TextView) findViewById(R.id.tvSoundUnpurchasedSH);
        tvSoundUnpurchasedZH = (TextView) findViewById(R.id.tvSoundUnpurchasedZH);
        tvSoundUnpurchasedZ = (TextView) findViewById(R.id.tvSoundUnpurchasedZ);
        tvSoundUnpurchasedR = (TextView) findViewById(R.id.tvSoundUnpurchasedR);
        tvSoundUnpurchasedTH = (TextView) findViewById(R.id.tvSoundUnpurchasedTH);

        rlSoundH = (RelativeLayout) findViewById(R.id.rlSoundH);
        rlSoundUnpurchasedM = (RelativeLayout) findViewById(R.id.rlSoundUnpurchasedM);
        rlSoundUnpurchasedK = (RelativeLayout) findViewById(R.id.rlSoundUnpurchasedK);
        rlSoundUnpurchasedH = (RelativeLayout) findViewById(R.id.rlSoundUnpurchasedH);
        rlSoundUnpurchasedP = (RelativeLayout) findViewById(R.id.rlSoundUnpurchasedP);
        rlSoundUnpurchasedN = (RelativeLayout) findViewById(R.id.rlSoundUnpurchasedN);
        rlSoundUnpurchasedG = (RelativeLayout) findViewById(R.id.rlSoundUnpurchasedG);
        rlSoundUnpurchasedW = (RelativeLayout) findViewById(R.id.rlSoundUnpurchasedW);
        rlSoundUnpurchasedT = (RelativeLayout) findViewById(R.id.rlSoundUnpurchasedT);
        rlSoundUnpurchasedF = (RelativeLayout) findViewById(R.id.rlSoundUnpurchasedF);
        rlSoundUnpurchasedJ = (RelativeLayout) findViewById(R.id.rlSoundUnpurchasedJ);
        rlSoundUnpurchasedY = (RelativeLayout) findViewById(R.id.rlSoundUnpurchasedY);
        rlSoundUnpurchasedD = (RelativeLayout) findViewById(R.id.rlSoundUnpurchasedD);
        rlSoundUnpurchasedV = (RelativeLayout) findViewById(R.id.rlSoundUnpurchasedV);
        rlSoundUnpurchasedCH = (RelativeLayout) findViewById(R.id.rlSoundUnpurchasedCH);
        rlSoundUnpurchasedNG = (RelativeLayout) findViewById(R.id.rlSoundUnpurchasedNG);
        rlSoundUnpurchasedS = (RelativeLayout) findViewById(R.id.rlSoundUnpurchasedS);
        rlSoundUnpurchasedL = (RelativeLayout) findViewById(R.id.rlSoundUnpurchasedL);
        rlSoundUnpurchasedSH = (RelativeLayout) findViewById(R.id.rlSoundUnpurchasedSH);
        rlSoundUnpurchasedZH = (RelativeLayout) findViewById(R.id.rlSoundUnpurchasedZH);
        rlSoundUnpurchasedZ = (RelativeLayout) findViewById(R.id.rlSoundUnpurchasedZ);
        rlSoundUnpurchasedR = (RelativeLayout) findViewById(R.id.rlSoundUnpurchasedR);
        rlSoundUnpurchasedTH = (RelativeLayout) findViewById(R.id.rlSoundUnpurchasedTH);

        tvSoundB.setTypeface(typeFaceAnja);
        tvSoundM.setTypeface(typeFaceAnja);
        tvSoundK.setTypeface(typeFaceAnja);
        tvSoundH.setTypeface(typeFaceAnja);
        tvSoundP.setTypeface(typeFaceAnja);
        tvSoundN.setTypeface(typeFaceAnja);
        tvSoundG.setTypeface(typeFaceAnja);
        tvSoundW.setTypeface(typeFaceAnja);
        tvSoundT.setTypeface(typeFaceAnja);
        tvSoundF.setTypeface(typeFaceAnja);
        tvSoundJ.setTypeface(typeFaceBubble);
        tvSoundY.setTypeface(typeFaceAnja);
        tvSoundD.setTypeface(typeFaceAnja);
        tvSoundV.setTypeface(typeFaceAnja);
        tvSoundCH.setTypeface(typeFaceAnja);
        tvSoundNG.setTypeface(typeFaceAnja);
        tvSoundS.setTypeface(typeFaceAnja);
        tvSoundL.setTypeface(typeFaceAnja);
        tvSoundSH.setTypeface(typeFaceAnja);
        tvSoundZH.setTypeface(typeFaceAnja);
        tvSoundZ.setTypeface(typeFaceAnja);
        tvSoundR.setTypeface(typeFaceAnja);
        tvSoundTH.setTypeface(typeFaceAnja);
        tvSoundUnpurchasedM.setTypeface(typeFaceAnja);
        tvSoundUnpurchasedK.setTypeface(typeFaceAnja);
        tvSoundUnpurchasedH.setTypeface(typeFaceAnja);
        tvSoundUnpurchasedP.setTypeface(typeFaceAnja);
        tvSoundUnpurchasedN.setTypeface(typeFaceAnja);
        tvSoundUnpurchasedG.setTypeface(typeFaceAnja);
        tvSoundUnpurchasedW.setTypeface(typeFaceAnja);
        tvSoundUnpurchasedT.setTypeface(typeFaceAnja);
        tvSoundUnpurchasedF.setTypeface(typeFaceAnja);
        tvSoundUnpurchasedJ.setTypeface(typeFaceBubble);
        tvSoundUnpurchasedY.setTypeface(typeFaceAnja);
        tvSoundUnpurchasedD.setTypeface(typeFaceAnja);
        tvSoundUnpurchasedV.setTypeface(typeFaceAnja);
        tvSoundUnpurchasedCH.setTypeface(typeFaceAnja);
        tvSoundUnpurchasedNG.setTypeface(typeFaceAnja);
        tvSoundUnpurchasedS.setTypeface(typeFaceAnja);
        tvSoundUnpurchasedL.setTypeface(typeFaceAnja);
        tvSoundUnpurchasedSH.setTypeface(typeFaceAnja);
        tvSoundUnpurchasedZH.setTypeface(typeFaceAnja);
        tvSoundUnpurchasedZ.setTypeface(typeFaceAnja);
        tvSoundUnpurchasedR.setTypeface(typeFaceAnja);
        tvSoundUnpurchasedTH.setTypeface(typeFaceAnja);

        tvSoundB.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundM.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundK.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundH.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundP.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundN.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundG.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundW.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundT.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundF.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundJ.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundY.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundD.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundV.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundCH.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundNG.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundS.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundL.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundSH.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundZH.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundZ.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundR.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundTH.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundUnpurchasedM.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundUnpurchasedK.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundUnpurchasedH.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundUnpurchasedP.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundUnpurchasedN.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundUnpurchasedG.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundUnpurchasedW.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundUnpurchasedT.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundUnpurchasedF.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundUnpurchasedJ.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundUnpurchasedY.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundUnpurchasedD.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundUnpurchasedV.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundUnpurchasedCH.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundUnpurchasedNG.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundUnpurchasedS.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundUnpurchasedL.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundUnpurchasedSH.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundUnpurchasedZH.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundUnpurchasedZ.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundUnpurchasedR.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSoundUnpurchasedTH.setOnTouchListener(_scaleAnimation.onTouchListener);

        tvSoundB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSoundOptionDialog("b", "/b/", 1);
            }
        });
        tvSoundM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToSoundOptionDialog("m", "/m/", 1);

            }
        });
        tvSoundK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToSoundOptionDialog("k", "/k/", 1);

            }
        });
        tvSoundH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToSoundOptionDialog("h", "/h/", 1);

            }
        });
        tvSoundP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToSoundOptionDialog("p", "/p/", 1);

            }
        });
        tvSoundN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToSoundOptionDialog("n", "/n/", 1);
            }
        });
        tvSoundG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToSoundOptionDialog("g", "/g/", 1);
            }
        });
        tvSoundW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToSoundOptionDialog("w", "/w/", 1);
            }
        });
        tvSoundT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToSoundOptionDialog("t", "/t/", 2);
            }
        });
        tvSoundF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToSoundOptionDialog("f", "/f/", 2);
            }
        });
        tvSoundJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToSoundOptionDialog("j", "/ʤ/", 2);
            }
        });
        tvSoundY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToSoundOptionDialog("y", "/y/", 2);
            }
        });
        tvSoundD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToSoundOptionDialog("d", "/d/", 2);
            }
        });
        tvSoundV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToSoundOptionDialog("v", "/v/", 2);
            }
        });
        tvSoundCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToSoundOptionDialog("ch", "/t∫/", 2);
            }
        });
        tvSoundNG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToSoundOptionDialog("ng", "/ŋ/", 2);
            }
        });
        tvSoundS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToSoundOptionDialog("s", "/s/", 3);
            }
        });
        tvSoundL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToSoundOptionDialog("l", "/l/", 3);
            }
        });
        tvSoundSH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToSoundOptionDialog("sh", "/∫/", 3);
            }
        });
        tvSoundZH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToSoundOptionDialog("zh", "/ʒ/", 3);
            }
        });
        tvSoundZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToSoundOptionDialog("z", "/z/", 3);
            }
        });
        tvSoundR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToSoundOptionDialog("r", "/r/", 3);
            }
        });
        tvSoundTH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToSoundOptionDialog("th", "/θ/ /ð/", 3);
            }
        });
        tvSoundUnpurchasedM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("m");
            }
        });
        tvSoundUnpurchasedK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("k");
            }
        });
        tvSoundUnpurchasedH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("h");
            }
        });
        tvSoundUnpurchasedP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("p");
            }
        });
        tvSoundUnpurchasedN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("n");
            }
        });
        tvSoundUnpurchasedG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("g");
            }
        });
        tvSoundUnpurchasedW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("w");
            }
        });
        tvSoundUnpurchasedT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("t");
            }
        });
        tvSoundUnpurchasedF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("f");
            }
        });
        tvSoundUnpurchasedJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("j");
            }
        });
        tvSoundUnpurchasedY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("y");
            }
        });
        tvSoundUnpurchasedD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("d");
            }
        });
        tvSoundUnpurchasedV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("v");
            }
        });
        tvSoundUnpurchasedCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("ch");
            }
        });
        tvSoundUnpurchasedNG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("ng");
            }
        });
        tvSoundUnpurchasedS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("s");
            }
        });
        tvSoundUnpurchasedL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("l");
            }
        });
        tvSoundUnpurchasedSH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("sh");
            }
        });
        tvSoundUnpurchasedZH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("zh");
            }
        });
        tvSoundUnpurchasedZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("z");
            }
        });
        tvSoundUnpurchasedR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("r");
            }
        });
        tvSoundUnpurchasedTH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("th");
            }
        });
        setSoundOptionsSizes();
        //openUpdateImagesDialog();
        setupAvailableSounds();
    }

    public void setupSaleItems(){
        ivStoreSale = (ImageView) findViewById(R.id.ivStoreSale);
        ivInfoSale = (ImageView) findViewById(R.id.ivInfoSale);
        _llHeaderSale = (LinearLayout) findViewById(R.id.llHeaderSale);
        _llHeaderSale.setVisibility(View.VISIBLE);
        _llHeader.setVisibility(View.GONE);
        ivLogoSale = (ImageView) findViewById(R.id.ivLogoSale);
        ivStoreSale.setOnTouchListener(_scaleAnimation.onTouchListener);
        ivStoreSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog("m");
            }
        });
        ivInfoSale.setOnTouchListener(_scaleAnimation.onTouchListener);
        ivInfoSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfoDialog();
            }
        });
    }

    public boolean checkIfTablet() {
        return (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public void setSoundOptionsSizes(){
        if(!checkIfTablet()) {
            double soundOptionSize = _dpHeight * .2673611;
            double robotSize = _dpHeight * .315972231;
            double textSize = _dpHeight * .18229167;
            Double d = new Double(textSize);
            int textSizeInteger = d.intValue();
            final float scale = getResources().getDisplayMetrics().density;
            int soundOptionHeightWidthPixels = (int) (soundOptionSize * scale + 0.5f);
            int robotHeightWidthPixels = (int) (robotSize * scale + 0.5f);
//            int soundOptionPaddingPixels = (int) (padding * scale + 0.5f);
            _ivRobot.getLayoutParams().height = robotHeightWidthPixels;
            _ivRobot.getLayoutParams().width = robotHeightWidthPixels;
            rlSoundB.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundB.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundM.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundK.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundH.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundP.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundN.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundG.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundW.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundT.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundF.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundJ.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundY.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundD.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundV.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundCH.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundNG.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundS.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundL.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundSH.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundZH.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundZ.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundR.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundTH.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedM.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedK.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedH.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedP.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedN.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedG.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedW.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedT.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedF.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedJ.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedY.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedD.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedV.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedCH.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedNG.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedS.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedL.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedSH.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedZH.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedZ.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedR.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedTH.getLayoutParams().height = soundOptionHeightWidthPixels;
            rlSoundM.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundK.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundP.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundN.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundG.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundW.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundT.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundF.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundJ.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundY.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundD.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundV.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundCH.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundNG.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundS.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundL.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundSH.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundZH.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundZ.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundR.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundTH.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundH.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedM.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedK.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedH.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedP.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedN.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedG.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedW.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedT.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedF.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedJ.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedY.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedD.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedV.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedCH.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedNG.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedS.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedL.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedSH.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedZH.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedZ.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedR.getLayoutParams().width = soundOptionHeightWidthPixels;
            rlSoundUnpurchasedTH.getLayoutParams().width = soundOptionHeightWidthPixels;
            tvSoundM.setTextSize(textSizeInteger);
            tvSoundK.setTextSize(textSizeInteger);
            tvSoundH.setTextSize(textSizeInteger);
            tvSoundP.setTextSize(textSizeInteger);
            tvSoundN.setTextSize(textSizeInteger);
            tvSoundG.setTextSize(textSizeInteger);
            tvSoundW.setTextSize(textSizeInteger);
            tvSoundT.setTextSize(textSizeInteger);
            tvSoundF.setTextSize(textSizeInteger);
            tvSoundJ.setTextSize(textSizeInteger);
            tvSoundY.setTextSize(textSizeInteger);
            tvSoundD.setTextSize(textSizeInteger);
            tvSoundV.setTextSize(textSizeInteger);
            tvSoundCH.setTextSize(textSizeInteger);
            tvSoundNG.setTextSize(textSizeInteger);
            tvSoundS.setTextSize(textSizeInteger);
            tvSoundL.setTextSize(textSizeInteger);
            tvSoundSH.setTextSize(textSizeInteger);
            tvSoundZH.setTextSize(textSizeInteger);
            tvSoundZ.setTextSize(textSizeInteger);
            tvSoundR.setTextSize(textSizeInteger);
            tvSoundTH.setTextSize(textSizeInteger);
            tvSoundUnpurchasedM.setTextSize(textSizeInteger);
            tvSoundUnpurchasedK.setTextSize(textSizeInteger);
            tvSoundUnpurchasedH.setTextSize(textSizeInteger);
            tvSoundUnpurchasedP.setTextSize(textSizeInteger);
            tvSoundUnpurchasedN.setTextSize(textSizeInteger);
            tvSoundUnpurchasedG.setTextSize(textSizeInteger);
            tvSoundUnpurchasedW.setTextSize(textSizeInteger);
            tvSoundUnpurchasedT.setTextSize(textSizeInteger);
            tvSoundUnpurchasedF.setTextSize(textSizeInteger);
            tvSoundUnpurchasedJ.setTextSize(textSizeInteger);
            tvSoundUnpurchasedY.setTextSize(textSizeInteger);
            tvSoundUnpurchasedD.setTextSize(textSizeInteger);
            tvSoundUnpurchasedV.setTextSize(textSizeInteger);
            tvSoundUnpurchasedCH.setTextSize(textSizeInteger);
            tvSoundUnpurchasedNG.setTextSize(textSizeInteger);
            tvSoundUnpurchasedS.setTextSize(textSizeInteger);
            tvSoundUnpurchasedL.setTextSize(textSizeInteger);
            tvSoundUnpurchasedSH.setTextSize(textSizeInteger);
            tvSoundUnpurchasedZH.setTextSize(textSizeInteger);
            tvSoundUnpurchasedZ.setTextSize(textSizeInteger);
            tvSoundUnpurchasedR.setTextSize(textSizeInteger);
            tvSoundUnpurchasedTH.setTextSize(textSizeInteger);
        }
    }

    public void goToSoundOptionDialog(final String letter, final String letterSound, final int colorNumber) {
        setGoogleAnalytics("Sound Menu: " + letter);
        if(!_soundOptionDialogIsShowing) {
            _soundIdList = new ArrayList<>();
            _soundOptionDialog = new Dialog(this);
            _soundOptionDialog.getWindow().getAttributes().windowAnimations = R.style.animation;
            _soundOptionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            _soundOptionDialog.setContentView(R.layout.activity_sound_dialog);
            Window window = this.getWindow();
            _decorView.setSystemUiVisibility(_uiOptions);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(_soundOptionDialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            _soundOptionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            _soundOptionDialog.getWindow().setAttributes(lp);
            final ImageView ivQuestion = (ImageView) _soundOptionDialog.findViewById(R.id.ivQuestion);
            ivQuestion.setOnTouchListener(_scaleAnimation.onTouchListener);
            ivQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openTipsDialog(letter);
                }
            });
            View v1 = _soundOptionDialog.findViewById(R.id.v1);
            v1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _soundOptionDialog.dismiss();
                }
            });
            View v2 = _soundOptionDialog.findViewById(R.id.v2);
            v2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _soundOptionDialog.dismiss();
                }
            });
            View v3 = _soundOptionDialog.findViewById(R.id.v3);
            v3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _soundOptionDialog.dismiss();
                }
            });
            View v4 = _soundOptionDialog.findViewById(R.id.v4);
            v4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _soundOptionDialog.dismiss();
                }
            });
            LinearLayout llDialogBox = (LinearLayout) _soundOptionDialog.findViewById(R.id.llDialogBox);
            LinearLayout llFlashcards = (LinearLayout) _soundOptionDialog.findViewById(R.id.llFlashcards);
            LinearLayout llSentencesFlashcards = (LinearLayout) _soundOptionDialog.findViewById(R.id.llRepetitive);
            LinearLayout llSentencesWordSwap = (LinearLayout) _soundOptionDialog.findViewById(R.id.llDifferent);
            LinearLayout llMatching = (LinearLayout) _soundOptionDialog.findViewById(R.id.llMatching);
            LinearLayout llWordFind = (LinearLayout) _soundOptionDialog.findViewById(R.id.llWordFind);
            LinearLayout llPuzzle = (LinearLayout) _soundOptionDialog.findViewById(R.id.llPuzzle);
            LinearLayout llCreateWord = (LinearLayout) _soundOptionDialog.findViewById(R.id.llCreateWord);
            final ImageView ivFlashcardsArrow = (ImageView) _soundOptionDialog.findViewById(R.id.ivFlashcardsArrow);
            final ImageView ivRepetitiveArrow = (ImageView) _soundOptionDialog.findViewById(R.id.ivRepetitiveArrow);
            final ImageView ivDifferentArrow = (ImageView) _soundOptionDialog.findViewById(R.id.ivDifferentArrow);
            final ImageView ivMatchingArrow = (ImageView) _soundOptionDialog.findViewById(R.id.ivMatchingArrow);
            final ImageView ivWordFindArrow = (ImageView) _soundOptionDialog.findViewById(R.id.ivWordFindArrow);
            final ImageView ivPuzzleArrow = (ImageView) _soundOptionDialog.findViewById(R.id.ivPuzzleArrow);
            final ImageView ivCreateWordArrow = (ImageView) _soundOptionDialog.findViewById(R.id.ivCreateWordArrow);

            llFlashcards.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    _scaleAnimation.setBounceAnimation(ivFlashcardsArrow, MenuActivity.this, R.anim.scale_bounce);
                    return false;
                }
            });
            llSentencesFlashcards.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    _scaleAnimation.setBounceAnimation(ivRepetitiveArrow, MenuActivity.this, R.anim.scale_bounce);
                    return false;
                }
            });
            llSentencesWordSwap.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    _scaleAnimation.setBounceAnimation(ivDifferentArrow, MenuActivity.this, R.anim.scale_bounce);
                    return false;
                }
            });
            llMatching.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    _scaleAnimation.setBounceAnimation(ivMatchingArrow, MenuActivity.this, R.anim.scale_bounce);
                    return false;
                }
            });
            llWordFind.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    _scaleAnimation.setBounceAnimation(ivWordFindArrow, MenuActivity.this, R.anim.scale_bounce);
                    return false;
                }
            });
            llPuzzle.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    _scaleAnimation.setBounceAnimation(ivPuzzleArrow, MenuActivity.this, R.anim.scale_bounce);
                    return false;
                }
            });
            llCreateWord.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    _scaleAnimation.setBounceAnimation(ivCreateWordArrow, MenuActivity.this, R.anim.scale_bounce);
                    return false;
                }
            });

            llFlashcards.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToWordsFlashcards(letter, letterSound, colorNumber);
                }
            });
            llSentencesFlashcards.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToSentencesFlashcards(letter, letterSound, colorNumber);
                }
            });
            llSentencesWordSwap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotToSentencesWordSwap(letter, letterSound, colorNumber);
                }
            });
            llMatching.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToGameMatching(letter, letterSound, colorNumber);
                }
            });
            llWordFind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToGameWordFind(letter, letterSound, colorNumber);
                }
            });
            llPuzzle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MenuActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
                }
            });
            llCreateWord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MenuActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
                }
            });
            TextView tvLetter = (TextView) _soundOptionDialog.findViewById(R.id.tvLetter);
            tvLetter.setText(letter);
            tvLetter.setTypeface(typeFaceAnja);
            if (letter.equals("j")) {
                tvLetter.setTypeface(typeFaceBubble);
            }
            TextView tvLetterSound = (TextView) _soundOptionDialog.findViewById(R.id.tvLetterSound);
            tvLetterSound.setText(letterSound);
            //tvLetterSound.setTypeface(typeFaceAnja);
            if (!checkIfTablet()) {
                tvLetter.setTextSize(getTextSize(72));
                tvLetterSound.setTextSize(getTextSize(35));
            }
            final int rawFile = getResources().getIdentifier(letter + "_sound", "raw", getPackageName());
            tvLetter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaPlayer mp = MediaPlayer.create(MenuActivity.this, rawFile);
                    mp.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
                    mp.start();
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                        }
                    });
                }
            });
            tvLetterSound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaPlayer mp = MediaPlayer.create(MenuActivity.this, rawFile);
                    mp.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
                    mp.start();
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                        }
                    });
                }
            });
            RelativeLayout rlDialogBackground = (RelativeLayout) _soundOptionDialog.findViewById(R.id.rlDialogBackground);
            View vLeftBorder = _soundOptionDialog.findViewById(R.id.vLeftBorder);
            View vRightBorder = _soundOptionDialog.findViewById(R.id.vRightBorder);
            switch (colorNumber) {
                case 1:
                    if (checkIfTablet()) {
                        llDialogBox.setBackgroundResource(R.drawable.rounded_dialog_box_tablet_blue);
                    } else {
                        //llDialogBox.setBackgroundResource(R.drawable.rounded_dialog_box_blue);
                        vRightBorder.setBackgroundColor(Color.parseColor("#0171BD"));
                        vLeftBorder.setBackgroundColor(Color.parseColor("#0171BD"));
                    }

                    tvLetter.setTextColor(Color.parseColor("#0171BD"));
                    tvLetterSound.setTextColor(Color.parseColor("#0171BD"));
                    //ivArrow.setImageResource(R.drawable.back_blue);
                    break;
                case 2:
                    if (checkIfTablet()) {
                        llDialogBox.setBackgroundResource(R.drawable.rounded_dialog_box_tablet_yellow);
                    } else {
//                    llDialogBox.setBackgroundResource(R.drawable.rounded_dialog_box_yellow);
                        vRightBorder.setBackgroundColor(Color.parseColor("#FCB913"));
                        vLeftBorder.setBackgroundColor(Color.parseColor("#FCB913"));
                    }
                    tvLetter.setTextColor(Color.parseColor("#FCB913"));
                    tvLetterSound.setTextColor(Color.parseColor("#FCB913"));
                    //ivArrow.setImageResource(R.drawable.back_yellow);
                    break;
                case 3:
                    if (checkIfTablet()) {
                        llDialogBox.setBackgroundResource(R.drawable.rounded_dialog_box_tablet_red);
                    } else {
//                    llDialogBox.setBackgroundResource(R.drawable.rounded_dialog_box_red);
                        vRightBorder.setBackgroundColor(Color.parseColor("#E81F2F"));
                        vLeftBorder.setBackgroundColor(Color.parseColor("#E81F2F"));
                    }
                    tvLetter.setTextColor(Color.parseColor("#E81F2F"));
                    tvLetterSound.setTextColor(Color.parseColor("#E81F2F"));
                    //ivArrow.setImageResource(R.drawable.back_red);
                    break;
            }
            _soundOptionDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    _soundOptionDialogIsShowing = true;
                }
            });
            _soundOptionDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    _soundOptionDialogIsShowing = false;
                }
            });
            _soundOptionDialog.show();
        }
    }

    public void openStoreDialog(String sound) {
        setGoogleAnalytics("Store: " + sound.toUpperCase());
        if(!_storeDialogIsShowing) {
            final Dialog dialog = new Dialog(this);
            dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if(_discountOn){
                dialog.setContentView(R.layout.activity_store_dialog);
            } else {
                dialog.setContentView(R.layout.activity_store_dialog);
            }
            _decorView.setSystemUiVisibility(_uiOptions);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setAttributes(lp);
            setupStoreDialog(dialog, sound);
            dialog.show();
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    _storeDialogIsShowing = true;
                }
            });
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    _storeDialogIsShowing = false;
                }
            });
            _storeDialog = dialog;
        }
    }

    public void openConfirmPurchaseDialog(final String sound){
        setGoogleAnalytics("Confirm Purchase: " + sound.toUpperCase());
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_store_confirm_dialog);
        _decorView.setSystemUiVisibility(_uiOptions);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        tvTitle.setTypeface(typeFaceAnja);
        TextView tvPurchase = (TextView) dialog.findViewById(R.id.tvPurchase);
        _tvPurchasePrice = (TextView) dialog.findViewById(R.id.tvPurchasePrice);
        _tvGrandTotalPrice = (TextView) dialog.findViewById(R.id.tvGrandTotalPrice);
        if(sound.contains("all")){
            if(_discountOn){
                _tvPurchasePrice.setText("$19.99");
                _tvGrandTotalPrice.setText("$19.99");
            } else {
                _tvPurchasePrice.setText("$49.99");
                _tvGrandTotalPrice.setText("$49.99");
            }
            tvPurchase.setText("All Sounds");
        } else {
            if(_discountOn){
                _tvPurchasePrice.setText("$1.99");
                _tvGrandTotalPrice.setText("$1.99");
            }
            tvPurchase.setText(sound.toUpperCase() + " Sound Program");
        }
        _llPromo = (LinearLayout) dialog.findViewById(R.id.llPromo);
        _tvPromoDescription = (TextView) dialog.findViewById(R.id.tvPromoDescription);
        _tvPromoPrice = (TextView) dialog.findViewById(R.id.tvPromoPrice);
        _vPromoSeparator = dialog.findViewById(R.id.vPromoSeparator);
        _vNoPromoFiller = dialog.findViewById(R.id.vNoPromoFiller);
        _llPromo.setVisibility(View.GONE);
        _pbApplyProgress = (ProgressBar) dialog.findViewById(R.id.pbApplyProgress);
        _vNoPromoFiller.setVisibility(View.VISIBLE);
        final EditText etPromoCode = (EditText) dialog.findViewById(R.id.etPromoCode);
        Button btApply = (Button) dialog.findViewById(R.id.btApply);
        btApply.setOnTouchListener(_scaleAnimation.onTouchListener);
        btApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _pbApplyProgress.setVisibility(View.VISIBLE);
                String promo = etPromoCode.getText().toString();
                if (sound.contains("all")) {
                    promo += ",All";
                    _tvPurchasePrice.setText("$49.99");
                    _tvGrandTotalPrice.setText("$49.99");
                } else {
                    promo += ",Individual";
                    _tvPurchasePrice.setText("$4.99");
                    _tvGrandTotalPrice.setText("$4.99");
                }
                checkForPromotion(promo);
            }
        });
        Button btBack = (Button) dialog.findViewById(R.id.btBack);
        btBack.setOnTouchListener(_scaleAnimation.onTouchListener);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        _btPlaceOrder = (Button) dialog.findViewById(R.id.btPlaceOrder);
        btBack.setTypeface(typeFaceAnja);
        _btPlaceOrder.setTypeface(typeFaceAnja);
        _btPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soundProduct = sound;
                if(_discountOn){
                    soundProduct += "_60d";
                }
                purchaseASound(soundProduct);
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    public void checkForPromotion(String promotion){
        setGoogleAnalytics("Promo Check: " + promotion);
        new GoDaddyPromoHelper().execute(promotion);
    }

    private class GoDaddyPromoHelper extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String results){
            if(results.equals("no_network")){
                _googlePlayCode = "";
                openNetworkIssuesDialog();
            } else {
                try {
                    String[] promoCodeList = results.split(",");
                    _promoCodeName = promoCodeList[0];
                    _promoCodeDiscount = "$" + promoCodeList[1];
                    _googlePlayCode = promoCodeList[2];
                    if (!promoCodeList[1].contains(".")) {
                        _promoCodeDiscount += ".00";
                    }
                    Message msg = handler.obtainMessage();
                    msg.what = 5;
                    handler.sendMessage(msg);
                } catch(Exception e){
                    _googlePlayCode = "";
                }
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String results = "";
            String promotionName = "";
            if(isNetworkAvailable()) {
                try {
                    // This will load the MySQL driver, each DB has its own driver
                    Class.forName("com.mysql.jdbc.Driver");
                    // Setup the connection with the DB
                    connect = DriverManager.getConnection("jdbc:mysql://p3nlmysql113plsk.secureserver.net:3306/SpeechEssentials?" + "user=speechdbuser&password=uT5n7o9");//1:05, 1:02
                    // Statements allow to issue SQL queries to the database
                    statement = connect.createStatement();
                    // Result set get the result of the SQL query
                    String[] paramsList = params[0].split(",");
                    promotionName = paramsList[0];
                    String purchaseType = paramsList[1];
                    String query = "SELECT * FROM SpeechEssentials.PromotionDim WHERE PromotionName = '" + promotionName + "' AND PurchaseType = '"+ purchaseType + "'";
                    resultSet = statement.executeQuery(query);
                    results = getPromotionMessageFromResultSet(resultSet, promotionName, purchaseType);
                } catch (Exception e) {
                    _googlePlayCode = "";
                    String ex = new String("Exception: " + e.getMessage());
                    results = "Promo Code - " + promotionName + " Something when wrong, try again";
                } finally {
                    close();
                }
            } else {
                results = "no_network";
            }
            return results;
        }
    }

    public String getPromotionMessageFromResultSet(ResultSet resultSet, String promotionName, String purchaseType) throws SQLException {
        //TODO: Add html
        String promoMessage = "Promo Code - " + promotionName + " Code Not Valid,0";
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String s = df.format(date);
        long currentDateLong = Long.valueOf(s);
        while (resultSet.next()) {
            try{
                int amountOff = resultSet.getInt("PromotionAmountOff");
                long startDate = Long.valueOf(resultSet.getString("PromotionStartDate"));
                long endDate = Long.valueOf(resultSet.getString("PromotionEndDate"));
                if(startDate <= currentDateLong && endDate >= currentDateLong){
                    promoMessage = "Promo Code - " + promotionName;
                } else {
                    promoMessage = "Promo Code - " + promotionName + " Code Expired";
                    amountOff = 0;
                }
                promoMessage += "," + amountOff;
                String googlePlayCode = resultSet.getString("GooglePlayCode");
                promoMessage += "," + googlePlayCode;
            } catch (Exception e) {
                promoMessage = "Promo Code - " + promotionName + " Something when wrong, try again";
            }
        }
        return promoMessage;
    }

    public void setupStoreDialog(final Dialog dialog, final String sound){
        storeTextViewHashMap = new HashMap<>();
        llBuySound = (LinearLayout) dialog.findViewById(R.id.llBuySound);
        llBuyAllSounds = (LinearLayout) dialog.findViewById(R.id.llBuyAllSounds);
        tvBuySoundPrice = (TextView) dialog.findViewById(R.id.tvBuySoundPrice);
        tvBuySoundPriceDescription = (TextView) dialog.findViewById(R.id.tvBuySoundPriceDescription);
        tvBuyAllSoundsPrice = (TextView) dialog.findViewById(R.id.tvBuyAllSoundsPrice);
        tvBuyAllSoundsPriceDescription = (TextView) dialog.findViewById(R.id.tvBuyAllSoundsPriceDescription);
        _ivSaleLeft = (ImageView) dialog.findViewById(R.id.ivSaleLeft);
        _ivBuySoundSale = (ImageView) dialog.findViewById(R.id.ivBuySoundSale);
        _ivBuyAllSoundsSale = (ImageView) dialog.findViewById(R.id.ivBuyAllSoundsSale);
        _ivBuySoundSale.setOnTouchListener(_scaleAnimation.onTouchListener);
        _ivBuyAllSoundsSale.setOnTouchListener(_scaleAnimation.onTouchListener);
        String[] listOfPurchasedSounds = _fileHelper.getStringFromFile("purchasedSoundsFile", "b", this).split(",");
        //TODO: TEST THIS! WE are only going to use one method for this madness
        boolean purchasedAllSounds = false;
        boolean purchasedCurrentSound = false;
        for(int i= 0; i <listOfPurchasedSounds.length; i++){
            if(listOfPurchasedSounds[i].contains("all")){
                purchasedAllSounds = true;
            } else if(listOfPurchasedSounds[i].replace("_60d","").equals(sound)){
                purchasedCurrentSound = true;
            }
        }
        setupStorePurchaseOptions(sound);
        tvStoreSoundDescription = (TextView) dialog.findViewById(R.id.tvSoundDescription);
        tvStoreSoundName = (TextView) dialog.findViewById(R.id.tvSoundName);
        tvStoreSoundName.setTypeface(typeFaceAnja);
        tvStoreSoundName.setText("The " + sound.toUpperCase() + " Sound");
        tvStoreSoundDescription.setText(_soundHelper.getSoundPurchaseDescription(sound));
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        TextView tvSoundName = (TextView) dialog.findViewById(R.id.tvSoundName);
        tvTitle.setTypeface(typeFaceAnja);
        tvSoundName.setTypeface(typeFaceAnja);
        HorizontalScrollView hsvSounds = (HorizontalScrollView) dialog.findViewById(R.id.hsvSounds);
        TextView tvB = (TextView) dialog.findViewById(R.id.tvB);
        TextView tvP = (TextView) dialog.findViewById(R.id.tvP);
        TextView tvM = (TextView) dialog.findViewById(R.id.tvM);
        TextView tvN = (TextView) dialog.findViewById(R.id.tvN);
        TextView tvK = (TextView) dialog.findViewById(R.id.tvK);
        TextView tvG = (TextView) dialog.findViewById(R.id.tvG);
        TextView tvH = (TextView) dialog.findViewById(R.id.tvH);
        TextView tvW = (TextView) dialog.findViewById(R.id.tvW);
        TextView tvT = (TextView) dialog.findViewById(R.id.tvT);
        TextView tvD = (TextView) dialog.findViewById(R.id.tvD);
        TextView tvF = (TextView) dialog.findViewById(R.id.tvF);
        TextView tvV = (TextView) dialog.findViewById(R.id.tvV);
        TextView tvJ = (TextView) dialog.findViewById(R.id.tvJ);
        TextView tvCH = (TextView) dialog.findViewById(R.id.tvCH);
        TextView tvY = (TextView) dialog.findViewById(R.id.tvY);
        TextView tvNG = (TextView) dialog.findViewById(R.id.tvNG);
        TextView tvS = (TextView) dialog.findViewById(R.id.tvS);
        TextView tvZ = (TextView) dialog.findViewById(R.id.tvZ);
        TextView tvL = (TextView) dialog.findViewById(R.id.tvL);
        TextView tvR = (TextView) dialog.findViewById(R.id.tvR);
        TextView tvSH = (TextView) dialog.findViewById(R.id.tvSH);
        TextView tvTH = (TextView) dialog.findViewById(R.id.tvTH);
        TextView tvZH = (TextView) dialog.findViewById(R.id.tvZH);
        TextView tvUnselectedB = (TextView) dialog.findViewById(R.id.tvUnselectedB);
        TextView tvUnselectedP = (TextView) dialog.findViewById(R.id.tvUnselectedP);
        TextView tvUnselectedM = (TextView) dialog.findViewById(R.id.tvUnselectedM);
        TextView tvUnselectedN = (TextView) dialog.findViewById(R.id.tvUnselectedN);
        TextView tvUnselectedK = (TextView) dialog.findViewById(R.id.tvUnselectedK);
        TextView tvUnselectedG = (TextView) dialog.findViewById(R.id.tvUnselectedG);
        TextView tvUnselectedH = (TextView) dialog.findViewById(R.id.tvUnselectedH);
        TextView tvUnselectedW = (TextView) dialog.findViewById(R.id.tvUnselectedW);
        TextView tvUnselectedT = (TextView) dialog.findViewById(R.id.tvUnselectedT);
        TextView tvUnselectedD = (TextView) dialog.findViewById(R.id.tvUnselectedD);
        TextView tvUnselectedF = (TextView) dialog.findViewById(R.id.tvUnselectedF);
        TextView tvUnselectedV = (TextView) dialog.findViewById(R.id.tvUnselectedV);
        TextView tvUnselectedJ = (TextView) dialog.findViewById(R.id.tvUnselectedJ);
        TextView tvUnselectedCH = (TextView) dialog.findViewById(R.id.tvUnselectedCH);
        TextView tvUnselectedY = (TextView) dialog.findViewById(R.id.tvUnselectedY);
        TextView tvUnselectedNG = (TextView) dialog.findViewById(R.id.tvUnselectedNG);
        TextView tvUnselectedS = (TextView) dialog.findViewById(R.id.tvUnselectedS);
        TextView tvUnselectedZ = (TextView) dialog.findViewById(R.id.tvUnselectedZ);
        TextView tvUnselectedL = (TextView) dialog.findViewById(R.id.tvUnselectedL);
        TextView tvUnselectedR = (TextView) dialog.findViewById(R.id.tvUnselectedR);
        TextView tvUnselectedSH = (TextView) dialog.findViewById(R.id.tvUnselectedSH);
        TextView tvUnselectedTH = (TextView) dialog.findViewById(R.id.tvUnselectedTH);
        TextView tvUnselectedZH = (TextView) dialog.findViewById(R.id.tvUnselectedZH);
        storeTextViewHashMap.put(tvUnselectedB, tvB);
        storeTextViewHashMap.put(tvUnselectedP, tvP);
        storeTextViewHashMap.put(tvUnselectedM, tvM);
        storeTextViewHashMap.put(tvUnselectedN, tvN);
        storeTextViewHashMap.put(tvUnselectedK, tvK);
        storeTextViewHashMap.put(tvUnselectedG, tvG);
        storeTextViewHashMap.put(tvUnselectedH, tvH);
        storeTextViewHashMap.put(tvUnselectedW, tvW);
        storeTextViewHashMap.put(tvUnselectedT, tvT);
        storeTextViewHashMap.put(tvUnselectedD, tvD);
        storeTextViewHashMap.put(tvUnselectedF, tvF);
        storeTextViewHashMap.put(tvUnselectedV, tvV);
        storeTextViewHashMap.put(tvUnselectedJ, tvJ);
        storeTextViewHashMap.put(tvUnselectedCH, tvCH);
        storeTextViewHashMap.put(tvUnselectedY, tvY);
        storeTextViewHashMap.put(tvUnselectedNG, tvNG);
        storeTextViewHashMap.put(tvUnselectedS, tvS);
        storeTextViewHashMap.put(tvUnselectedZ, tvZ);
        storeTextViewHashMap.put(tvUnselectedL, tvL);
        storeTextViewHashMap.put(tvUnselectedR, tvR);
        storeTextViewHashMap.put(tvUnselectedSH, tvSH);
        storeTextViewHashMap.put(tvUnselectedTH, tvTH);
        storeTextViewHashMap.put(tvUnselectedZH, tvZH);
        tvB.setVisibility(View.GONE);
        tvP.setVisibility(View.GONE);
        tvM.setVisibility(View.GONE);
        tvN.setVisibility(View.GONE);
        tvK.setVisibility(View.GONE);
        tvG.setVisibility(View.GONE);
        tvH.setVisibility(View.GONE);
        tvW.setVisibility(View.GONE);
        tvT.setVisibility(View.GONE);
        tvD.setVisibility(View.GONE);
        tvF.setVisibility(View.GONE);
        tvV.setVisibility(View.GONE);
        tvJ.setVisibility(View.GONE);
        tvCH.setVisibility(View.GONE);
        tvY.setVisibility(View.GONE);
        tvNG.setVisibility(View.GONE);
        tvS.setVisibility(View.GONE);
        tvZ.setVisibility(View.GONE);
        tvL.setVisibility(View.GONE);
        tvR.setVisibility(View.GONE);
        tvSH.setVisibility(View.GONE);
        tvTH.setVisibility(View.GONE);
        tvZH.setVisibility(View.GONE);
        tvB.setTypeface(typeFaceAnja);
        tvP.setTypeface(typeFaceAnja);
        tvM.setTypeface(typeFaceAnja);
        tvN.setTypeface(typeFaceAnja);
        tvK.setTypeface(typeFaceAnja);
        tvG.setTypeface(typeFaceAnja);
        tvH.setTypeface(typeFaceAnja);
        tvW.setTypeface(typeFaceAnja);
        tvT.setTypeface(typeFaceAnja);
        tvD.setTypeface(typeFaceAnja);
        tvF.setTypeface(typeFaceAnja);
        tvV.setTypeface(typeFaceAnja);
        tvJ.setTypeface(typeFaceBubble);
        tvCH.setTypeface(typeFaceAnja);
        tvY.setTypeface(typeFaceAnja);
        tvNG.setTypeface(typeFaceAnja);
        tvS.setTypeface(typeFaceAnja);
        tvZ.setTypeface(typeFaceAnja);
        tvL.setTypeface(typeFaceAnja);
        tvR.setTypeface(typeFaceAnja);
        tvSH.setTypeface(typeFaceAnja);
        tvTH.setTypeface(typeFaceAnja);
        tvZH.setTypeface(typeFaceAnja);
        tvUnselectedB.setVisibility(View.VISIBLE);
        tvUnselectedP.setVisibility(View.VISIBLE);
        tvUnselectedM.setVisibility(View.VISIBLE);
        tvUnselectedN.setVisibility(View.VISIBLE);
        tvUnselectedK.setVisibility(View.VISIBLE);
        tvUnselectedG.setVisibility(View.VISIBLE);
        tvUnselectedH.setVisibility(View.VISIBLE);
        tvUnselectedW.setVisibility(View.VISIBLE);
        tvUnselectedT.setVisibility(View.VISIBLE);
        tvUnselectedD.setVisibility(View.VISIBLE);
        tvUnselectedF.setVisibility(View.VISIBLE);
        tvUnselectedV.setVisibility(View.VISIBLE);
        tvUnselectedJ.setVisibility(View.VISIBLE);
        tvUnselectedCH.setVisibility(View.VISIBLE);
        tvUnselectedY.setVisibility(View.VISIBLE);
        tvUnselectedNG.setVisibility(View.VISIBLE);
        tvUnselectedS.setVisibility(View.VISIBLE);
        tvUnselectedZ.setVisibility(View.VISIBLE);
        tvUnselectedL.setVisibility(View.VISIBLE);
        tvUnselectedR.setVisibility(View.VISIBLE);
        tvUnselectedSH.setVisibility(View.VISIBLE);
        tvUnselectedTH.setVisibility(View.VISIBLE);
        tvUnselectedZH.setVisibility(View.VISIBLE);
        tvUnselectedB.setTypeface(typeFaceAnja);
        tvUnselectedP.setTypeface(typeFaceAnja);
        tvUnselectedM.setTypeface(typeFaceAnja);
        tvUnselectedN.setTypeface(typeFaceAnja);
        tvUnselectedK.setTypeface(typeFaceAnja);
        tvUnselectedG.setTypeface(typeFaceAnja);
        tvUnselectedH.setTypeface(typeFaceAnja);
        tvUnselectedW.setTypeface(typeFaceAnja);
        tvUnselectedT.setTypeface(typeFaceAnja);
        tvUnselectedD.setTypeface(typeFaceAnja);
        tvUnselectedF.setTypeface(typeFaceAnja);
        tvUnselectedV.setTypeface(typeFaceAnja);
        tvUnselectedJ.setTypeface(typeFaceBubble);
        tvUnselectedCH.setTypeface(typeFaceAnja);
        tvUnselectedY.setTypeface(typeFaceAnja);
        tvUnselectedNG.setTypeface(typeFaceAnja);
        tvUnselectedS.setTypeface(typeFaceAnja);
        tvUnselectedZ.setTypeface(typeFaceAnja);
        tvUnselectedL.setTypeface(typeFaceAnja);
        tvUnselectedR.setTypeface(typeFaceAnja);
        tvUnselectedSH.setTypeface(typeFaceAnja);
        tvUnselectedTH.setTypeface(typeFaceAnja);
        tvUnselectedZH.setTypeface(typeFaceAnja);

        tvUnselectedB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        tvUnselectedP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        tvUnselectedM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        tvUnselectedN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        tvUnselectedK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        tvUnselectedG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        tvUnselectedH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        tvUnselectedW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        tvUnselectedT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        tvUnselectedD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        tvUnselectedF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        tvUnselectedV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        tvUnselectedJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        tvUnselectedCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        tvUnselectedY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        tvUnselectedNG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        tvUnselectedS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        tvUnselectedZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        tvUnselectedL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        tvUnselectedR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        tvUnselectedSH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        tvUnselectedTH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        tvUnselectedZH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStoreTextViewVisibility((TextView) v);
            }
        });
        View v1 = dialog.findViewById(R.id.v1);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v2 = dialog.findViewById(R.id.v2);
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v3 = dialog.findViewById(R.id.v3);
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v4 = dialog.findViewById(R.id.v4);
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView selectedTextView = null;
        TextView unselectedTextView = null;
        switch (sound){
            case "b":
                selectedTextView = tvB;
                unselectedTextView = tvUnselectedB;
                break;
            case "p":
                selectedTextView = tvP;
                unselectedTextView = tvUnselectedP;
                break;
            case "m":
                selectedTextView = tvM;
                unselectedTextView = tvUnselectedM;
                break;
            case "n":
                selectedTextView = tvN;
                unselectedTextView = tvUnselectedN;
                break;
            case "k":
                selectedTextView = tvK;
                unselectedTextView = tvUnselectedK;
                break;
            case "g":
                selectedTextView = tvG;
                unselectedTextView = tvUnselectedG;
                break;
            case "h":
                selectedTextView = tvH;
                unselectedTextView = tvUnselectedH;
                break;
            case "w":
                selectedTextView = tvW;
                unselectedTextView = tvUnselectedW;
                break;
            case "d":
                selectedTextView = tvD;
                unselectedTextView = tvUnselectedD;
                break;
            case "t":
                selectedTextView = tvT;
                unselectedTextView = tvUnselectedT;
                break;
            case "f":
                selectedTextView = tvF;
                unselectedTextView = tvUnselectedF;
                break;
            case "v":
                selectedTextView = tvV;
                unselectedTextView = tvUnselectedV;
                break;
            case "j":
                selectedTextView = tvJ;
                unselectedTextView = tvUnselectedJ;
                break;
            case "ch":
                selectedTextView = tvCH;
                unselectedTextView = tvUnselectedCH;
                break;
            case "y":
                selectedTextView = tvY;
                unselectedTextView = tvUnselectedY;
                break;
            case "ng":
                selectedTextView = tvNG;
                unselectedTextView = tvUnselectedNG;
                break;
            case "s":
                selectedTextView = tvS;
                unselectedTextView = tvUnselectedS;
                break;
            case "z":
                selectedTextView = tvZ;
                unselectedTextView = tvUnselectedZ;
                break;
            case "l":
                selectedTextView = tvL;
                unselectedTextView = tvUnselectedL;
                break;
            case "r":
                selectedTextView = tvR;
                unselectedTextView = tvUnselectedR;
                break;
            case "sh":
                selectedTextView = tvSH;
                unselectedTextView = tvUnselectedSH;
                break;
            case "th":
                selectedTextView = tvTH;
                unselectedTextView = tvUnselectedTH;
                break;
            case "zh":
                selectedTextView = tvZH;
                unselectedTextView = tvUnselectedZH;
                break;
        }
        TextView tvSoundDiscountDescription = (TextView) dialog.findViewById(R.id.tvSoundDiscountDescription);
        if(!checkIfTablet()){
            tvTitle.setTextSize(getTextSize(40));
            tvSoundName.setTextSize(getTextSize(25));
            tvBuySoundPrice.setTextSize(getTextSize(15));
            tvBuySoundPriceDescription.setTextSize(getTextSize(12));
            tvBuyAllSoundsPrice.setTextSize(getTextSize(15));
            tvBuyAllSoundsPriceDescription.setTextSize(getTextSize(12));
            tvSoundDiscountDescription.setTextSize(getTextSize(12));
            tvStoreSoundDescription.setTextSize(getTextSize(12));
            tvB.setTextSize(getTextSize(50));
            tvP.setTextSize(getTextSize(50));
            tvM.setTextSize(getTextSize(50));
            tvN.setTextSize(getTextSize(50));
            tvK.setTextSize(getTextSize(50));
            tvG.setTextSize(getTextSize(50));
            tvH.setTextSize(getTextSize(50));
            tvW.setTextSize(getTextSize(50));
            tvT.setTextSize(getTextSize(50));
            tvD.setTextSize(getTextSize(50));
            tvF.setTextSize(getTextSize(50));
            tvV.setTextSize(getTextSize(50));
            tvJ.setTextSize(getTextSize(50));
            tvCH.setTextSize(getTextSize(50));
            tvY.setTextSize(getTextSize(50));
            tvNG.setTextSize(getTextSize(50));
            tvS.setTextSize(getTextSize(50));
            tvZ.setTextSize(getTextSize(50));
            tvL.setTextSize(getTextSize(50));
            tvR.setTextSize(getTextSize(50));
            tvSH.setTextSize(getTextSize(50));
            tvTH.setTextSize(getTextSize(50));
            tvZH.setTextSize(getTextSize(50));
            tvUnselectedB.setTextSize(getTextSize(50));
            tvUnselectedP.setTextSize(getTextSize(50));
            tvUnselectedM.setTextSize(getTextSize(50));
            tvUnselectedN.setTextSize(getTextSize(50));
            tvUnselectedK.setTextSize(getTextSize(50));
            tvUnselectedG.setTextSize(getTextSize(50));
            tvUnselectedH.setTextSize(getTextSize(50));
            tvUnselectedW.setTextSize(getTextSize(50));
            tvUnselectedT.setTextSize(getTextSize(50));
            tvUnselectedD.setTextSize(getTextSize(50));
            tvUnselectedF.setTextSize(getTextSize(50));
            tvUnselectedV.setTextSize(getTextSize(50));
            tvUnselectedJ.setTextSize(getTextSize(50));
            tvUnselectedCH.setTextSize(getTextSize(50));
            tvUnselectedY.setTextSize(getTextSize(50));
            tvUnselectedNG.setTextSize(getTextSize(50));
            tvUnselectedS.setTextSize(getTextSize(50));
            tvUnselectedZ.setTextSize(getTextSize(50));
            tvUnselectedL.setTextSize(getTextSize(50));
            tvUnselectedR.setTextSize(getTextSize(50));
            tvUnselectedSH.setTextSize(getTextSize(50));
            tvUnselectedTH.setTextSize(getTextSize(50));
            tvUnselectedZH.setTextSize(getTextSize(50));
        }
        selectedTextView.setVisibility(View.VISIBLE);
        unselectedTextView.setVisibility(View.GONE);
        final TextView selectedTV = selectedTextView;
        tvCurrentStoreSelected = selectedTextView;
        tvCurrentStoreUnselected = unselectedTextView;
        final HorizontalScrollView horizontalScrollView = hsvSounds;
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                horizontalScrollTo(selectedTV, horizontalScrollView);
            }
        });
    }

    public void setStoreTextViewVisibility(final TextView textView){
        tvStoreSoundName.setText("The " + textView.getText().toString().toUpperCase() + " Sound");
        tvStoreSoundDescription.setText(_soundHelper.getSoundPurchaseDescription(textView.getText().toString()));
        tvCurrentStoreSelected.setVisibility(View.GONE);
        tvCurrentStoreUnselected.setVisibility(View.VISIBLE);
        textView.setVisibility(View.GONE);
        TextView selected = storeTextViewHashMap.get(textView);
        selected.setVisibility(View.VISIBLE);
        tvCurrentStoreUnselected = textView;
        tvCurrentStoreSelected = selected;
        setupStorePurchaseOptions(textView.getText().toString());
    }

    public void setupStorePurchaseOptions(final String sound){
        String listOfPurchasedSounds = _fileHelper.getStringFromFile("purchasedSoundsFile", "b", this);
        if(_discountOn){
            _ivSaleLeft.setVisibility(View.VISIBLE);
            _ivBuyAllSoundsSale.setVisibility(View.VISIBLE);
            _ivBuySoundSale.setVisibility(View.VISIBLE);
            llBuySound.setVisibility(View.GONE);
            llBuyAllSounds.setVisibility(View.GONE);
            _ivBuyAllSoundsSale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openConfirmPurchaseDialog("sound_all");
                }
            });
            _ivBuySoundSale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openConfirmPurchaseDialog("sound_" + sound);
                }
            });
        }
        if(listOfPurchasedSounds.contains("all")) {
            llBuyAllSounds.setVisibility(View.VISIBLE);
            _ivSaleLeft.setVisibility(View.GONE);
            _ivBuyAllSoundsSale.setVisibility(View.GONE);
            llBuySound.setBackgroundResource(R.drawable.rounded_corners_blue);
            tvBuySoundPrice.setText("Purchased!");
            tvBuySoundPriceDescription.setText("Thank You!");
            llBuyAllSounds.setVisibility(View.GONE);
            llBuySound.setOnClickListener(null);
            llBuyAllSounds.setOnClickListener(null);
        } else if (listOfPurchasedSounds.contains(sound)){
            llBuySound.setVisibility(View.VISIBLE);
            _ivSaleLeft.setVisibility(View.GONE);
            _ivBuySoundSale.setVisibility(View.GONE);
            llBuySound.setBackgroundResource(R.drawable.rounded_corners_blue);
            llBuySound.setOnClickListener(null);
            tvBuySoundPrice.setText("Purchased!");
            tvBuySoundPriceDescription.setText("Thank You!");
            llBuyAllSounds.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openConfirmPurchaseDialog("sound_all");
                }
            });
            llBuyAllSounds.setBackgroundResource(R.drawable.rounded_corners_yellow);
            tvBuyAllSoundsPrice.setText("$49.99");
            tvBuyAllSoundsPriceDescription.setText("buy all now");
        } else {
            llBuySound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openConfirmPurchaseDialog("sound_" + sound);
                }
            });
            llBuySound.setBackgroundResource(R.drawable.rounded_corners_green);
            tvBuySoundPrice.setText("$4.99");
            tvBuySoundPriceDescription.setText("buy " + sound + " now");
            llBuyAllSounds.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openConfirmPurchaseDialog("sound_all");
                }
            });
            llBuyAllSounds.setBackgroundResource(R.drawable.rounded_corners_yellow);
            tvBuyAllSoundsPrice.setText("$49.99");
            tvBuyAllSoundsPriceDescription.setText("buy all now");
        }
    }

    public void horizontalScrollTo(TextView selectedTextView, HorizontalScrollView scrollView){
        int vLeft = selectedTextView.getLeft();
        int vRight = selectedTextView.getRight();
        int sWidth = scrollView.getWidth();
        scrollView.scrollTo((((vLeft + vRight - sWidth) / 2)), 0);
    }

    public void checkForNewDBUpdates(){
        if(isNetworkAvailable()){
            new GoDaddyVersionHelper().execute("");
        }
    }

    public void checkForDiscount(){
        String listOfPurchases = _fileHelper.getStringFromFile("purchasedSoundsFile", "b", this);
        if(!listOfPurchases.contains("all")) {
            if (isNetworkAvailable()) {
                new GoDaddyDiscountHelper().execute("");
            }
        } else {
            String shownRateDialog = _fileHelper.getStringFromFile("shownRateAppDialog", "false", MenuActivity.this);
            if(shownRateDialog.equals("false")){
                openRateDialog();
            }
        }
    }

    public void openInfoDialog() {
        setGoogleAnalytics("Info Dialog");
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_info_dialog);
        _decorView.setSystemUiVisibility(_uiOptions);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        TextView tvParagraph1 = (TextView) dialog.findViewById(R.id.tvParagraph1);
        TextView tvParagraph2 = (TextView) dialog.findViewById(R.id.tvParagraph2);
        TextView tvParagraph3 = (TextView) dialog.findViewById(R.id.tvParagraph3);
        TextView tvParagraph4 = (TextView) dialog.findViewById(R.id.tvParagraph4);
        TextView tvParagraph5 = (TextView) dialog.findViewById(R.id.tvParagraph5);
        TextView tvParagraph6 = (TextView) dialog.findViewById(R.id.tvParagraph6);
        tvParagraph1.setTypeface(typeFaceAnja);
        tvParagraph2.setTypeface(typeFaceAnja);
        tvParagraph3.setTypeface(typeFaceAnja);
        tvParagraph4.setTypeface(typeFaceAnja);
        tvParagraph5.setTypeface(typeFaceAnja);
        tvParagraph6.setTypeface(typeFaceAnja);
        final LinearLayout llUsingThisAppButton = (LinearLayout) dialog.findViewById(R.id.llUsingThisAppButton);
        final LinearLayout llAboutButton = (LinearLayout) dialog.findViewById(R.id.llAboutButton);
        final LinearLayout llVideoTutorialsButton = (LinearLayout) dialog.findViewById(R.id.llVideoTutorialsButton);
        final LinearLayout llUsingThisApp = (LinearLayout) dialog.findViewById(R.id.llUsingThisAppContent);
        final LinearLayout llAbout = (LinearLayout) dialog.findViewById(R.id.llAbout);
        final LinearLayout llVideoTutorials = (LinearLayout) dialog.findViewById(R.id.llVideoTutorials);
        final TextView tvUsingThisApp = (TextView) dialog.findViewById(R.id.tvUsingThisApp);
        final TextView tvAbout = (TextView) dialog.findViewById(R.id.tvAbout);
        final TextView tvVideoTutorials = (TextView) dialog.findViewById(R.id.tvVideoTutorials);
        final LinearLayout llVideo1 = (LinearLayout) dialog.findViewById(R.id.llVideo1);
        final LinearLayout llVideo2 = (LinearLayout) dialog.findViewById(R.id.llVideo3);
        final LinearLayout llVideo3 = (LinearLayout) dialog.findViewById(R.id.llVideo3);
        final LinearLayout llVideo4 = (LinearLayout) dialog.findViewById(R.id.llVideo4);
        _tvDBVersion = (TextView) dialog.findViewById(R.id.tvDBVersion);
        if(_dbVersion != null) {
            _tvDBVersion.setText(_dbVersion);
        }
        final ImageView ivUsingThisApp = (ImageView) dialog.findViewById(R.id.ivUsingThisApp);
        final ImageView ivAbout = (ImageView) dialog.findViewById(R.id.ivAbout);
        final ImageView ivVideoTutorials = (ImageView) dialog.findViewById(R.id.ivVideoTutorials);
        TextView tvVersion = (TextView) dialog.findViewById(R.id.tvVersion);
        LinearLayout llRateApp = (LinearLayout) dialog.findViewById(R.id.llRateApp);
        llRateApp.setOnTouchListener(_scaleAnimation.onTouchListener);
        llRateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("market://details?id=" + MenuActivity.this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + MenuActivity.this.getPackageName())));
                }
            }
        });
        try{
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            tvVersion.setText(version);
        } catch (Exception e){
            e.printStackTrace();
        }
        llUsingThisAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llUsingThisApp.setVisibility(View.VISIBLE);
                llAbout.setVisibility(View.GONE);
                llVideoTutorials.setVisibility(View.GONE);
                llUsingThisAppButton.setBackgroundColor(Color.parseColor("#2B6DB9"));
                llAboutButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                llVideoTutorialsButton.setBackgroundColor(Color.parseColor("#FFFFFF"));

                tvUsingThisApp.setTextColor(Color.parseColor("#FFFFFF"));
                tvAbout.setTextColor(Color.parseColor("#2B6DB9"));
                tvVideoTutorials.setTextColor(Color.parseColor("#2B6DB9"));

                ivUsingThisApp.setImageResource(R.drawable.whitearrow_right);
                ivAbout.setImageResource(R.drawable.bluearrow_right);
                ivVideoTutorials.setImageResource(R.drawable.bluearrow_right);
            }
        });
        llAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llUsingThisApp.setVisibility(View.GONE);
                llAbout.setVisibility(View.VISIBLE);
                llVideoTutorials.setVisibility(View.GONE);
                llUsingThisAppButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                llAboutButton.setBackgroundColor(Color.parseColor("#2B6DB9"));
                llVideoTutorialsButton.setBackgroundColor(Color.parseColor("#FFFFFF"));

                tvUsingThisApp.setTextColor(Color.parseColor("#2B6DB9"));
                tvAbout.setTextColor(Color.parseColor("#FFFFFF"));
                tvVideoTutorials.setTextColor(Color.parseColor("#2B6DB9"));

                ivUsingThisApp.setImageResource(R.drawable.bluearrow_right);
                ivAbout.setImageResource(R.drawable.whitearrow_right);
                ivVideoTutorials.setImageResource(R.drawable.bluearrow_right);
            }
        });
        llVideoTutorialsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llUsingThisApp.setVisibility(View.GONE);
                llAbout.setVisibility(View.GONE);
                llVideoTutorials.setVisibility(View.VISIBLE);
                llUsingThisAppButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                llAboutButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                llVideoTutorialsButton.setBackgroundColor(Color.parseColor("#2B6DB9"));

                tvUsingThisApp.setTextColor(Color.parseColor("#2B6DB9"));
                tvAbout.setTextColor(Color.parseColor("#2B6DB9"));
                tvVideoTutorials.setTextColor(Color.parseColor("#FFFFFF"));

                ivUsingThisApp.setImageResource(R.drawable.bluearrow_right);
                ivAbout.setImageResource(R.drawable.bluearrow_right);
                ivVideoTutorials.setImageResource(R.drawable.whitearrow_right);
            }
        });
        View v1 = dialog.findViewById(R.id.v1);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v2 = dialog.findViewById(R.id.v2);
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v3 = dialog.findViewById(R.id.v3);
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v4 = dialog.findViewById(R.id.v4);
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        if(!checkIfTablet()){
            TextView tvReviewOurApp = (TextView) dialog.findViewById(R.id.tvReviewOurApp);
            TextView tvAppVersion = (TextView) dialog.findViewById(R.id.tvAppVersion);
            TextView tvDatabaseVersion = (TextView) dialog.findViewById(R.id.tvDatabaseVersion);
            TextView tvQuestions = (TextView) dialog.findViewById(R.id.tvQuestions);
            TextView tvSpeechUrl = (TextView) dialog.findViewById(R.id.tvSpeechUrl);
            TextView tvDBVersion = (TextView) dialog.findViewById(R.id.tvDBVersion);
            TextView tvCreatedBy = (TextView) dialog.findViewById(R.id.tvCreatedBy);
            TextView tvCopyright = (TextView) dialog.findViewById(R.id.tvCopyright);
            TextView tvVideo1 = (TextView) dialog.findViewById(R.id.tvVideo1);
            TextView tvVideo2 = (TextView) dialog.findViewById(R.id.tvVideo2);
            TextView tvVideo3 = (TextView) dialog.findViewById(R.id.tvVideo3);
            TextView tvVideo4 = (TextView) dialog.findViewById(R.id.tvVideo4);
            tvUsingThisApp.setTextSize(getTextSize(13));
            tvAbout.setTextSize(getTextSize(13));
            tvVideoTutorials.setTextSize(getTextSize(13));
            tvReviewOurApp.setTextSize(getTextSize(15));
            tvVersion.setTextSize(getTextSize(50));
            tvDBVersion.setTextSize(getTextSize(50));
            tvAppVersion.setTextSize(getTextSize(18));
            tvDatabaseVersion.setTextSize(getTextSize(18));
            tvQuestions.setTextSize(getTextSize(18));
            tvSpeechUrl.setTextSize(getTextSize(13));
            tvCreatedBy.setTextSize(getTextSize(18));
            tvCopyright.setTextSize(getTextSize(15));
            tvVideo1.setTextSize(getTextSize(18));
            tvVideo2.setTextSize(getTextSize(18));
            tvVideo3.setTextSize(getTextSize(18));
            tvVideo4.setTextSize(getTextSize(18));
        }
        llVideo1.setOnTouchListener(_scaleAnimation.onTouchListener);
        llVideo2.setOnTouchListener(_scaleAnimation.onTouchListener);
        llVideo3.setOnTouchListener(_scaleAnimation.onTouchListener);
        llVideo4.setOnTouchListener(_scaleAnimation.onTouchListener);
        llVideo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "C9n3h78kuZc"));
                startActivity(intent);
            }
        });
        llVideo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "ZenJCFbFpuk"));
                startActivity(intent);
            }
        });
        llVideo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "7-ROv3Nv6F8"));
                startActivity(intent);
            }
        });
        llVideo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "rRMXhmELz7U"));
                startActivity(intent);
            }
        });
        dialog.show();
    }

    public void openScoresDialog() {
        setGoogleAnalytics("Scores Dialog");
        _scoresDialog = new Dialog(this);
        _scoresDialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        _scoresDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        _scoresDialog.setContentView(R.layout.activity_scores_dialog);
        _decorView.setSystemUiVisibility(_uiOptions);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(_scoresDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        _scoresDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        _scoresDialog.getWindow().setAttributes(lp);
        TextView tvTitle = (TextView) _scoresDialog.findViewById(R.id.tvTitle);
        tvTitle.setTypeface(typeFaceAnja);
        TextView tvScoreNameTitle = (TextView) _scoresDialog.findViewById(R.id.tvScoreNameTitle);
        tvScoreNameTitle.setTypeface(typeFaceAnja);
        LinearLayout llScoresTable = (LinearLayout) _scoresDialog.findViewById(R.id.llScoresTable);
        LinearLayout llEmailScore = (LinearLayout) _scoresDialog.findViewById(R.id.llEmailScore);
        LinearLayout llNamesHolder = (LinearLayout) _scoresDialog.findViewById(R.id.llNamesHolder);
        llEmailScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
            }
        });
        llNamesHolder.removeAllViews();
        populateUsers(_scoresDialog, llNamesHolder);
        populateScores(llScoresTable, 0, false);
        TextView tvCurrentName = (TextView) _scoresDialog.findViewById(R.id.tvCurrentName);
        tvCurrentName.setText(_currentUser);
        tvCurrentName.setTypeface(typeFaceAnja);
        View v1 = _scoresDialog.findViewById(R.id.v1);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _scoresDialog.dismiss();
            }
        });
        View v2 = _scoresDialog.findViewById(R.id.v2);
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _scoresDialog.dismiss();
            }
        });
        View v3 = _scoresDialog.findViewById(R.id.v3);
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _scoresDialog.dismiss();
            }
        });
        View v4 = _scoresDialog.findViewById(R.id.v4);
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _scoresDialog.dismiss();
            }
        });
        if(!checkIfTablet()) {
            tvTitle.setTextSize(getTextSize(45));
            tvScoreNameTitle.setTextSize(getTextSize(25));
            tvCurrentName.setTextSize(getTextSize(25));
        }
        _scoresDialog.show();
    }

    public void populateUsers(Dialog dialog, LinearLayout parentLayout){
        int textSize = 25;
        if(!checkIfTablet()) {
            Display display = getWindowManager().getDefaultDisplay();
            DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);
            float density = getResources().getDisplayMetrics().density;
            float dpHeight = outMetrics.heightPixels / density;//411.42856
            float dpWidth = outMetrics.widthPixels / density;//689.4286
            double textSizeDouble = (dpHeight + dpWidth) * .018167661279507;
            final float scale = getResources().getDisplayMetrics().density;
            textSize = (int) (textSizeDouble);
        }
        ArrayList<String> listOfUsers = _databaseOperationsHelper.getListOfUsersWithIds(_databaseOperationsHelper);
        boolean isSelected = false;
        for(int i = 0; i < listOfUsers.size(); i++){
            if(i == 0){
                isSelected = true;
            } else {
                isSelected = false;
            }
            _layoutHelper.addUserToScoresLayout(this, dialog, parentLayout, listOfUsers.get(i), isSelected, textSize);
        }
    }

    public boolean saveUser(int userId, String userName, String userBirthday, String userEmail, String userGender){
        boolean userWasSaved = _databaseOperationsHelper.editUser(_databaseOperationsHelper, userId, userName, userEmail, userGender, userBirthday);
        return userWasSaved;
    }

    public ArrayList<String> getUserInfo(int userId){
        ArrayList<String> listOfUserInfo = _databaseOperationsHelper.getUserInfo(_databaseOperationsHelper, userId);
        String userName = listOfUserInfo.get(0);
        String userBirthday = listOfUserInfo.get(1);
        String userEmail = listOfUserInfo.get(2);
        String userGender = listOfUserInfo.get(3);
        return listOfUserInfo;
    }

    public void deleteUser(int userId, String username){
        boolean userDeleted = _databaseOperationsHelper.deleteUser(_databaseOperationsHelper, userId);
        if(userDeleted){
            Toast.makeText(MenuActivity.this, "User " + username + " was succesfully deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MenuActivity.this, "User " + username + " was not succesfully deleted", Toast.LENGTH_SHORT).show();
        }
    }

    public void openUserDialog(final int userId) {
        setGoogleAnalytics("User Dialog");
        ArrayList<String> listOfUserInfo = getUserInfo(userId);
        final String userName = listOfUserInfo.get(0);
        String birthday = listOfUserInfo.get(1);
        String email = listOfUserInfo.get(2);
        boolean isBoy = false;
        if(listOfUserInfo.get(3).equals("male")){
            isBoy = true;
        }
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
        dialog.getWindow().setLayout(_screenWidth, _screenHeight / 2);
        LinearLayout llDelete = (LinearLayout) dialog.findViewById(R.id.llDelete);
        View vDelete = dialog.findViewById(R.id.vDelete);
        vDelete.setVisibility(View.VISIBLE);
        TextView tvDeleteUser = (TextView) dialog.findViewById(R.id.tvDeleteUser);
        llDelete.setVisibility(View.VISIBLE);
        llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser(userId, userName);
                dialog.dismiss();
                try {
                    _scoresDialog.dismiss();
                } catch (Exception e){

                }
                openScoresDialog();
            }
        });
        View v1 = dialog.findViewById(R.id.v1);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v2 = dialog.findViewById(R.id.v2);
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v3 = dialog.findViewById(R.id.v3);
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final EditText etUserName = (EditText) dialog.findViewById(R.id.etUserName);
        etUserName.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        etUserName.setText(userName);
        final EditText etUserEmail = (EditText) dialog.findViewById(R.id.etUserEmail);
        etUserEmail.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        etUserEmail.setText(email);
        _etBirthday = (EditText) dialog.findViewById(R.id.etBirthday);
        _etBirthday.setInputType(InputType.TYPE_NULL);
        _etBirthday.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        _etBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });
        _etBirthday.setText(birthday);
        final RadioGroup radioGrp = (RadioGroup) dialog.findViewById(R.id.radioGrp);
        final LinearLayout llBoyGirlImage = (LinearLayout) dialog.findViewById(R.id.llBoyGirlImage);
        final RadioButton radioM = (RadioButton) dialog.findViewById(R.id.radioM);
        final RadioButton radioF = (RadioButton) dialog.findViewById(R.id.radioF);
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
        if(isBoy){
            radioM.setChecked(true);
            radioF.setChecked(false);
            llBoyGirlImage.setBackgroundResource(R.drawable.boy_avatar);
        } else {
            radioM.setChecked(false);
            radioF.setChecked(true);
            llBoyGirlImage.setBackgroundResource(R.drawable.girl_avatar);
        }
        final TextView tvSave = (TextView) dialog.findViewById(R.id.tvTryAgain);
        if(!checkIfTablet()) {
            tvSave.setTextSize(getTextSize(25));
            tvDeleteUser.setTextSize(getTextSize(25));
        }
        tvSave.setText("save");
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUserName.getText().toString().equals("")) {
                    Toast.makeText(MenuActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
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
                    boolean userWasEditedSuccessfully = saveUser(userId, etUserName.getText().toString(), _etBirthday.getText().toString().trim(), etUserEmail.getText().toString().trim(), gender);
                    if (userWasEditedSuccessfully) {
                        Toast.makeText(MenuActivity.this, etUserName.getText().toString() + " was successfully updated!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(MenuActivity.this, etUserName.getText().toString() + " was not successfully updated.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    try{
                        _scoresDialog.dismiss();
                    } catch (Exception e){

                    }
                    openScoresDialog();
                }
            }
        });
        tvSave.setOnTouchListener(_scaleAnimation.onTouchListener);
        dialog.show();
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        // onDateSet method
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date_selected = String.valueOf(monthOfYear+1)+"/"+String.valueOf(dayOfMonth)+"/"+String.valueOf(year);
            _etBirthday.setText(date_selected);
        }
    };

    public void openDatePicker(){
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        int cday = c.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog dialog = new DatePickerDialog(this, mDateSetListener, cyear, cmonth, cday);
        dialog.getDatePicker().setCalendarViewShown(false);
        dialog.show();
    }

    public void populateScores(LinearLayout linearLayout, int currentUserId, boolean useCurrentUserId){
        if(useCurrentUserId){
            setCurrentUser(currentUserId);
        } else {
            setCurrentUser();
        }
        ArrayList<Integer> listOfScoreIds = _databaseOperationsHelper.getListOfScoreIdsForUser(_databaseOperationsHelper, _currentUser);
        linearLayout.removeAllViews();
        for(int i = 0; i < listOfScoreIds.size(); i++){
            String[] scoreDataList = _databaseOperationsHelper.getScoreDataForUserId(_databaseOperationsHelper, listOfScoreIds.get(i));
            _layoutHelper.addUserScoreLayoutToParentLayout(this, linearLayout, listOfScoreIds.get(i), scoreDataList, _databaseOperationsHelper);
        }
    }

    public void updateScoreUsers(Dialog dialog, LinearLayout parentLayout, int textSize, boolean useUserIdForSelected, int userId){
        ArrayList<String> listOfUsers = _databaseOperationsHelper.getListOfUsersWithIds(_databaseOperationsHelper);
        boolean isSelected = false;
        parentLayout.removeAllViews();
        for(int i = 0; i < listOfUsers.size(); i++){
            String[] userInfo = listOfUsers.get(i).split("USERID");
            int currentUserId = Integer.valueOf(userInfo[1]);
            if(useUserIdForSelected){
                if(currentUserId == userId){
                    isSelected = true;
                } else {
                    isSelected = false;
                }
            } else {
                if(i == 0){
                    isSelected = true;
                } else {
                    isSelected = false;
                }
            }
            _layoutHelper.addUserToScoresLayout(this, dialog, parentLayout, listOfUsers.get(i), isSelected, textSize);
        }
        LinearLayout llScoresTable = (LinearLayout) dialog.findViewById(R.id.llScoresTable);
        populateScores(llScoresTable, userId, true);
        TextView tvCurrentName = (TextView) dialog.findViewById(R.id.tvCurrentName);
        tvCurrentName.setText(_currentUser);
    }

    public void setCurrentUser(int currentUserId){
        String currentUser = "";
        ArrayList<String> listOfUsers = _databaseOperationsHelper.getListOfUsersWithIds(_databaseOperationsHelper);
        for(int i = 0; i < listOfUsers.size(); i++){
            String[] userInfo = listOfUsers.get(i).split("USERID");
            if(Integer.valueOf(userInfo[1]) == currentUserId){
                _currentUser = userInfo[0];
            }
        }
    }

    public void setCurrentUser(){
        ArrayList<String> listOfUsers = _databaseOperationsHelper.getListOfUsersWithIds(_databaseOperationsHelper);
        if(listOfUsers.size() == 0){
            _currentUser = "";
        } else {
            String[] userInfo = listOfUsers.get(0).split("USERID");
            _currentUser = userInfo[0];
        }
    }

    public void deleteScore(final DatabaseOperationsHelper databaseOperationsHelper, final int scoreId, final LinearLayout parentLayout){
        setGoogleAnalytics("Delete Score");
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_deletescore_dialog);
        _decorView.setSystemUiVisibility(_uiOptions);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        TextView tvDelete = (TextView) dialog.findViewById(R.id.tvDelete);
        tvDelete.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseOperationsHelper.deleteScoreForUser(databaseOperationsHelper, scoreId);
                repopulateScoresTable(parentLayout, databaseOperationsHelper);
                dialog.dismiss();
            }
        });
        TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
        tvCancel.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        View v1 = dialog.findViewById(R.id.v1);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v2 = dialog.findViewById(R.id.v2);
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v3 = dialog.findViewById(R.id.v3);
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v4 = dialog.findViewById(R.id.v4);
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void repopulateScoresTable(LinearLayout linearLayout, DatabaseOperationsHelper dop){
        ArrayList<Integer> listOfScoreIds = _databaseOperationsHelper.getListOfScoreIdsForUser(_databaseOperationsHelper, _currentUser);
        linearLayout.removeAllViews();
        for(int i = 0; i < listOfScoreIds.size(); i++){
            String[] scoreDataList = _databaseOperationsHelper.getScoreDataForUserId(_databaseOperationsHelper, listOfScoreIds.get(i));
            _layoutHelper.addUserScoreLayoutToParentLayout(this, linearLayout, listOfScoreIds.get(i), scoreDataList, _databaseOperationsHelper);
        }
    }

    public void openSettingsDialog() {
        setGoogleAnalytics("Settings Dialog");
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
        tvMirrorFunction.setTypeface(typeFaceAnja);
        TextView tvScoringButtons = (TextView) dialog.findViewById(R.id.tvScoringButtons);
        tvScoringButtons.setTypeface(typeFaceAnja);
        TextView tvScoringSounds = (TextView) dialog.findViewById(R.id.tvScoringSounds);
        tvScoringSounds.setTypeface(typeFaceAnja);
        TextView tvVoiceAudio = (TextView) dialog.findViewById(R.id.tvVoiceAudio);
        tvVoiceAudio.setTypeface(typeFaceAnja);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        tvTitle.setTypeface(typeFaceAnja);
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

        View v1 = dialog.findViewById(R.id.v1);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v2 = dialog.findViewById(R.id.v2);
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v3 = dialog.findViewById(R.id.v3);
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v4 = dialog.findViewById(R.id.v4);
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /*
        databaseOperationsHelper.addSetting(databaseOperationsHelper, "voice_on", 1);
        databaseOperationsHelper.addSetting(databaseOperationsHelper, "scoring_buttons_visible", 1);
        databaseOperationsHelper.addSetting(databaseOperationsHelper, "mirror_function_on", 1);
     */
    public void changeSetting(int settingNumber, ImageView imageView){
        setGoogleAnalytics("Change Setting: " + settingNumber);
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

    public void openTipsDialog(String sound) {
        setGoogleAnalytics("Tips Dialog: "+ sound.toUpperCase());
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_tips_dialog);
        _decorView.setSystemUiVisibility(_uiOptions);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        TextView tvTipSound = (TextView) dialog.findViewById(R.id.tvTipSound);
        TextView tvVideoTutorial = (TextView) dialog.findViewById(R.id.tvVideoTutorial);
        tvVideoTutorial.setTypeface(typeFaceAnja);
        TextView tvWordType = (TextView) dialog.findViewById(R.id.tvWordType);
        TextView tvWatchThe = (TextView) dialog.findViewById(R.id.tvWatchThe);
        tvWordType.setTypeface(typeFaceAnja);
        TextView tvTip = (TextView) dialog.findViewById(R.id.tvTip);
        TipsHelper tipsHelper = new TipsHelper();
        tvTip.setText(tipsHelper.getTips(sound));
        LinearLayout llVideoTutorial = (LinearLayout) dialog.findViewById(R.id.llVideoTutorial);
        final String videoTutorialUrl = tipsHelper.getTipsVideo(sound);
        llVideoTutorial.setOnTouchListener(_scaleAnimation.onTouchListener);
        llVideoTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!videoTutorialUrl.equals("")){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoTutorialUrl));
                    startActivity(intent);
                } else {
                    Toast.makeText(MenuActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvTipSound.setTypeface(typeFaceAnja);
        tvTipSound.setText("The " + sound.toUpperCase() + " Sound");
        setupTips(sound);
        if(!checkIfTablet()){
            tvWordType.setTextSize(getTextSize(26));
            tvTipSound.setTextSize(getTextSize(28));
            tvTip.setTextSize(getTextSize(13));
            tvWatchThe.setTextSize(getTextSize(14));
            tvVideoTutorial.setTextSize(getTextSize(22));
        }
        View v1 = dialog.findViewById(R.id.v1);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v2 = dialog.findViewById(R.id.v2);
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v3 = dialog.findViewById(R.id.v3);
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v4 = dialog.findViewById(R.id.v4);
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void setupTips(String sound){

    }

    public void goToParagraphs(String letter, String letterSound, int colorNumber){
        Intent myIntent = new Intent(MenuActivity.this, FlashCardActivity.class);
        myIntent.putExtra("LETTER", letter);
        myIntent.putExtra("LETTERSOUND", letterSound);
        myIntent.putExtra("COLORNUMBER", colorNumber);
        MenuActivity.this.startActivity(myIntent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        finish();
    }

    public void goToWordsFlashcards(String letter, String letterSound, int colorNumber){
        Intent myIntent = new Intent(MenuActivity.this, FlashCardActivity.class);
        myIntent.putExtra("LETTER", letter);
        myIntent.putExtra("LETTERSOUND", letterSound);
        myIntent.putExtra("COLORNUMBER", colorNumber);
        MenuActivity.this.startActivity(myIntent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        finish();
    }

    public void goToGameWordFind(String letter, String letterSound, int colorNumber){
        Intent myIntent = new Intent(MenuActivity.this, WordFindActivity.class);
        myIntent.putExtra("LETTER", letter);
        myIntent.putExtra("LETTERSOUND", letterSound);
        myIntent.putExtra("COLORNUMBER", colorNumber);
        MenuActivity.this.startActivity(myIntent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        finish();
    }

    public void goToGamePuzzle(String letter, String letterSound, int colorNumber){
        Intent myIntent = new Intent(MenuActivity.this, FlashCardActivity.class);
        myIntent.putExtra("LETTER", letter);
        myIntent.putExtra("LETTERSOUND", letterSound);
        myIntent.putExtra("COLORNUMBER", colorNumber);
        MenuActivity.this.startActivity(myIntent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        finish();
    }

    public void goToGameMatching(String letter, String letterSound, int colorNumber){
        Intent myIntent = new Intent(MenuActivity.this, MatchingActivity.class);
        myIntent.putExtra("LETTER", letter);
        myIntent.putExtra("LETTERSOUND", letterSound);
        myIntent.putExtra("COLORNUMBER", colorNumber);
        MenuActivity.this.startActivity(myIntent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        finish();
    }

    public void goToGameCreateWord(String letter, String letterSound, int colorNumber){
        Intent myIntent = new Intent(MenuActivity.this, MatchingActivity.class);
        myIntent.putExtra("LETTER", letter);
        myIntent.putExtra("LETTERSOUND", letterSound);
        myIntent.putExtra("COLORNUMBER", colorNumber);
        MenuActivity.this.startActivity(myIntent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        finish();
    }

    public void goToSentencesFlashcards(String letter, String letterSound, int colorNumber){
        Intent myIntent = new Intent(MenuActivity.this, SentencesFlashCardActivity.class);
        myIntent.putExtra("LETTER", letter);
        myIntent.putExtra("LETTERSOUND", letterSound);
        myIntent.putExtra("COLORNUMBER", colorNumber);
        MenuActivity.this.startActivity(myIntent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        finish();
    }

    public void gotToSentencesWordSwap(String letter, String letterSound, int colorNumber){
        Intent myIntent = new Intent(MenuActivity.this, SentencesWordSwapActivity.class);
        myIntent.putExtra("LETTER", letter);
        myIntent.putExtra("LETTERSOUND", letterSound);
        myIntent.putExtra("COLORNUMBER", colorNumber);
        MenuActivity.this.startActivity(myIntent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exitByBackKey() {
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            unbindService(mServiceConn);
        }
        if(mHelper != null){
            mHelper.dispose();
            mHelper = null;
        }
        if(_soundOptionDialog != null) {
            if (_soundOptionDialog.isShowing()) {
                _soundOptionDialog.dismiss();
            }
        }
    }

    public void onResume() {
        _decorView.setSystemUiVisibility(_uiOptions);
        super.onResume();
    }

    public int setmServiceAndPurchaseSound(IInAppBillingService googlePlaymService){
        int purchaseCode = 0;
        if(googlePlaymService != null){
            mService = googlePlaymService;
        }
        try {
            _listOfSoundsToDownload = _googlePlayHelper.getListOfPurchasedSounds(this);
            if(_listOfSoundsToDownload.contains("all")) {
                //user has purchased all of the sounds!
                userMadeAPurchase("all", true);
                purchaseCode = 1;
            } else if (_listOfSoundsToDownload.contains(_soundToPurchase)) {
                //user has purchased the sound already!
                userMadeAPurchase(_soundToPurchase.replace("sound_", "").replace("_60d",""), true);
                purchaseCode = 2;
            } else {
                _googlePlayHelper.purchaseASound(this, _soundToPurchase, mService);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return purchaseCode;
    }

    public void purchaseASound(String sound){
        setGoogleAnalytics("Purchase Sound: " + sound.toUpperCase());
        _soundToPurchase = sound;
        if(mService == null){
            setupGooglePlay();
        } else {
            setmServiceAndPurchaseSound(null);
        }
    }

    public void setupGooglePlay(){
        mHelper = _googlePlayHelper.getmHelper(this);
        mServiceConn = _googlePlayHelper.createIInAppBillingServiceConnection(this, MenuActivity.this);
    }

    public void userMadeAPurchase(String sound, boolean userPurchasedAlready){
        setGoogleAnalytics("Purchase Made: " + sound.toUpperCase());
        //TODO: Handle all!
        //TODO: Dialog box that says "Thanks for purchasing! Please wait a moment while we download your new content
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_purchase_made);
        _decorView.setSystemUiVisibility(_uiOptions);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        getStorageAvailability();
        _tvThankYou = (TextView) dialog.findViewById(R.id.tvThankYou);
        _ivThankYou = (ImageView) dialog.findViewById(R.id.ivThankYou);
        String ivThankyouCard = sound + "_cards";
        int drawable = getResources().getIdentifier(ivThankyouCard, "drawable", getPackageName());
        try{
            _ivThankYou.setImageResource(drawable);
        } catch(Exception e){
            Toast.makeText(MenuActivity.this, ivThankyouCard + " doesn't exist", Toast.LENGTH_SHORT).show();
        }
        _tvPleaseWait = (TextView) dialog.findViewById(R.id.tvPleaseWait);
        _tvPurchaseIncludes = (TextView) dialog.findViewById(R.id.tvPurchaseIncludes);
        _pbSpinner = (ProgressBar) dialog.findViewById(R.id.pbSpinner);
        _pbSpinner.setVisibility(View.GONE);
        _pbSpinnerIndeterminate = (ProgressBar) dialog.findViewById(R.id.pbSpinnerIndeterminate);
        _pbSpinnerIndeterminate.setVisibility(View.VISIBLE);
        if(userPurchasedAlready){
            _tvThankYou.setText("Looks like you already purchased! Downloading the " + sound.toUpperCase() + " sound");
        } else {
            _tvThankYou.setText("Thank you for purchasing the " + sound.toUpperCase() + " sound");
        }
        dialog.show();
        _purchaseDialog = dialog;
        getPurchasedSoundFromDatabase(null, sound, false, false);
    }

    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                _pbSpinnerIndeterminate.setVisibility(View.GONE);
                _pbSpinner.setVisibility(View.VISIBLE);
            } else if(msg.what == 2){
                _pbSpinner.setVisibility(View.GONE);
                _pbSpinnerIndeterminate.setVisibility(View.GONE);
                //TODO: String ivThankyouCard = _currentSoundBeingDownloaded + "_cards";
                if(_purchaseDialog == null){
                    openUpdateImagesDialog();
                }
                String ivThankyouCard = _currentSoundBeingDownloaded + "_cards";
                int drawable = getResources().getIdentifier(ivThankyouCard, "drawable", getPackageName());
                try{
                    _ivThankYou.setImageResource(drawable);
                } catch(Exception e){
                    Toast.makeText(MenuActivity.this, ivThankyouCard + " doesn't exist", Toast.LENGTH_SHORT).show();
                }
                _tvThankYou.setText("Downloading the " + _currentSoundBeingDownloaded.toUpperCase() + " sound now.");
                _tvPleaseWait.setText("Downloading sound (" + _currentSoundNumber + " / " + _numberOfSoundsToDownload + ")");
                _tvPurchaseIncludes.setText("The " + _currentSoundBeingDownloaded + " sound includes multiple word and sentence flashcards, sentence word swap, a memory game section and a word find game section");
                _pbSpinner.setMax(getNumberOfSoundsToDownloadForSound(_currentSoundBeingDownloaded));
                _pbSpinnerIndeterminate.setVisibility(View.VISIBLE);
                _pbSpinner.setVisibility(View.GONE);
            } else if(msg.what == 3){
                _pbSpinnerIndeterminate.setVisibility(View.GONE);
                _pbSpinner.setVisibility(View.VISIBLE);
                _currentSoundNumber++;
            } else if(msg.what == 4){
                _purchaseDialog.dismiss();
                _pbSpinner.setVisibility(View.GONE);
                _pbSpinnerIndeterminate.setVisibility(View.VISIBLE);
            } else if(msg.what == 5){
                _pbApplyProgress.setVisibility(View.GONE);
                _llPromo.setVisibility(View.VISIBLE);
                _vNoPromoFiller.setVisibility(View.GONE);
                _tvPromoDescription.setText(_promoCodeName);
                _tvPromoPrice.setText(_promoCodeDiscount);
                double currentTotal = Double.valueOf(_tvGrandTotalPrice.getText().toString().replace("$",""));
                double newTotal = currentTotal - Double.valueOf(_promoCodeDiscount.replace("$",""));
                DecimalFormat df = new DecimalFormat("#.00");
                _tvGrandTotalPrice.setText("$" + df.format(newTotal));
                _btPlaceOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        purchaseASound(_googlePlayCode);
                    }
                });
            }
            super.handleMessage(msg);
        }
    };

    public int getNumberOfSoundsToDownloadForSound(String sound){
        int numberOfSoundsToDownload = 0;
        switch (sound){
            case "air": numberOfSoundsToDownload = 98; break;
            case "ar": numberOfSoundsToDownload = 36; break;
            case "b": numberOfSoundsToDownload = 282; break;
            case "bl": numberOfSoundsToDownload = 23; break;
            case "br": numberOfSoundsToDownload = 22; break;
            case "ch": numberOfSoundsToDownload = 171; break;
            case "d": numberOfSoundsToDownload = 247; break;
            case "dr": numberOfSoundsToDownload = 21; break;
            case "ear": numberOfSoundsToDownload = 88; break;
            case "er": numberOfSoundsToDownload = 141; break;
            case "f": numberOfSoundsToDownload = 183; break;
            case "fl": numberOfSoundsToDownload = 24; break;
            case "fr": numberOfSoundsToDownload = 23; break;
            case "g": numberOfSoundsToDownload = 196; break;
            case "gl": numberOfSoundsToDownload = 20; break;
            case "gr": numberOfSoundsToDownload = 23; break;
            case "h": numberOfSoundsToDownload = 93; break;
            case "ire": numberOfSoundsToDownload = 76; break;
            case "j": numberOfSoundsToDownload = 146; break;
            case "k": numberOfSoundsToDownload = 292; break;
            case "kl": numberOfSoundsToDownload = 25; break;
            case "kr": numberOfSoundsToDownload = 22; break;
            case "ks": numberOfSoundsToDownload = 27; break;
            case "l": numberOfSoundsToDownload = 305; break;
            case "m": numberOfSoundsToDownload = 273; break;
            case "n": numberOfSoundsToDownload = 329; break;
            case "ng": numberOfSoundsToDownload = 86; break;
            case "ns": numberOfSoundsToDownload = 20; break;
            case "or": numberOfSoundsToDownload = 111; break;
            case "p": numberOfSoundsToDownload = 299; break;
            case "pl": numberOfSoundsToDownload = 24; break;
            case "pr": numberOfSoundsToDownload = 20; break;
            case "ps": numberOfSoundsToDownload = 21; break;
            case "r": numberOfSoundsToDownload = 77; break;
            case "rl": numberOfSoundsToDownload = 32; break;
            case "s": numberOfSoundsToDownload = 248; break;
            case "sh": numberOfSoundsToDownload = 173; break;
            case "sk": numberOfSoundsToDownload = 23; break;
            case "sl": numberOfSoundsToDownload = 40; break;
            case "sm": numberOfSoundsToDownload = 20; break;
            case "sn": numberOfSoundsToDownload = 21; break;
            case "sp": numberOfSoundsToDownload = 27; break;
            case "st": numberOfSoundsToDownload = 28; break;
            case "sw": numberOfSoundsToDownload = 20; break;
            case "t": numberOfSoundsToDownload = 266; break;
            case "th": numberOfSoundsToDownload = 141; break;
            case "tr": numberOfSoundsToDownload = 21; break;
            case "ts": numberOfSoundsToDownload = 20; break;
            case "v": numberOfSoundsToDownload = 162; break;
            case "w": numberOfSoundsToDownload = 107; break;
            case "y": numberOfSoundsToDownload = 34; break;
            case "z": numberOfSoundsToDownload = 183; break;
            case "zh": numberOfSoundsToDownload = 24; break;
        }
        return numberOfSoundsToDownload;
    }

    public void unlockPurchasedSounds(){
        String[] listOfPurchasedSounds = _fileHelper.getStringFromFile("purchasedSoundsFile", "b", this).split(",");
        for(int i = 0; i < listOfPurchasedSounds.length; i++){
            unlockSound(listOfPurchasedSounds[i]);
        }
    }

    public void openRateDialog(){
        setGoogleAnalytics("Rate Dialog");
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_rate);
        _decorView.setSystemUiVisibility(_uiOptions);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        tvTitle.setTypeface(typeFaceAnja);
        TextView tvEnjoying = (TextView) dialog.findViewById(R.id.tvEnjoying);
        TextView tvPleaseRate = (TextView) dialog.findViewById(R.id.tvPleaseRate);
        TextView tvRateNow = (TextView) dialog.findViewById(R.id.tvRateNow);
        tvRateNow.setTypeface(typeFaceAnja);
        TextView tvRemindMeLater = (TextView) dialog.findViewById(R.id.tvRemindMeLater);
        tvRemindMeLater.setTypeface(typeFaceAnja);
        TextView tvSendFeedback = (TextView) dialog.findViewById(R.id.tvSendFeedback);
        tvSendFeedback.setTypeface(typeFaceAnja);
        TextView tvNoThanks = (TextView) dialog.findViewById(R.id.tvNoThanks);
        tvNoThanks.setTypeface(typeFaceAnja);
        if(!checkIfTablet()) {
            tvTitle.setTextSize(getTextSize(30));
            tvEnjoying.setTextSize(getTextSize(25));
            tvPleaseRate.setTextSize(getTextSize(20));
            tvRateNow.setTextSize(getTextSize(25));
            tvRemindMeLater.setTextSize(getTextSize(25));
            tvSendFeedback.setTextSize(getTextSize(25));
            tvNoThanks.setTextSize(getTextSize(25));
        }
        tvRateNow.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvRemindMeLater.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvSendFeedback.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvNoThanks.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvRateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _fileHelper.writeStringToFile("shownRateAppDialog", "true", MenuActivity.this);
                dialog.dismiss();
                Uri uri = Uri.parse("market://details?id=" + MenuActivity.this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + MenuActivity.this.getPackageName())));
                }
            }
        });
        tvNoThanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _fileHelper.writeStringToFile("shownRateAppDialog", "true", MenuActivity.this);
                dialog.dismiss();
            }
        });
        tvSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _fileHelper.writeStringToFile("shownRateAppDialog", "false", MenuActivity.this);
                dialog.dismiss();
                openFeedback();
            }
        });
        tvRemindMeLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _fileHelper.writeStringToFile("shownRateAppDialog", "false", MenuActivity.this);
                dialog.dismiss();
            }
        });
        dialog.show();
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
        tvTitle.setTypeface(typeFaceAnja);
        TextView tvLoveFeedback = (TextView) dialog.findViewById(R.id.tvLoveFeedback);
        final EditText etFeedback = (EditText) dialog.findViewById(R.id.etFeedback);
        TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
        tvCancel.setTypeface(typeFaceAnja);
        TextView tvSendFeedback = (TextView) dialog.findViewById(R.id.tvSendFeedback);
        tvSendFeedback.setTypeface(typeFaceAnja);
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
                    Toast.makeText(MenuActivity.this, "Please enter feedback", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(MenuActivity.this, "Thank you for your feedback!!", Toast.LENGTH_SHORT).show();
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MenuActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void unlockSound(String sound){
        sound = sound.replace("_60d","");
        switch (sound){
            case "p":
                rlSoundUnpurchasedP.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundP.setVisibility(View.VISIBLE);
                } else {
                    tvSoundP.setVisibility(View.VISIBLE);
                }
                break;
            case "m":
                rlSoundUnpurchasedM.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundM.setVisibility(View.VISIBLE);
                } else {
                    tvSoundM.setVisibility(View.VISIBLE);
                }
                break;
            case "n":
                rlSoundUnpurchasedN.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundN.setVisibility(View.VISIBLE);
                } else {
                    tvSoundN.setVisibility(View.VISIBLE);
                }
                break;
            case "k":
                rlSoundUnpurchasedK.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundK.setVisibility(View.VISIBLE);
                } else {
                    tvSoundK.setVisibility(View.VISIBLE);
                }
                break;
            case "g":
                rlSoundUnpurchasedG.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundG.setVisibility(View.VISIBLE);
                } else {
                    tvSoundG.setVisibility(View.VISIBLE);
                }
                break;
            case "h":
                rlSoundUnpurchasedH.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundH.setVisibility(View.VISIBLE);
                } else {
                    tvSoundH.setVisibility(View.VISIBLE);
                }
                break;
            case "w":
                rlSoundUnpurchasedW.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundW.setVisibility(View.VISIBLE);
                } else {
                    tvSoundW.setVisibility(View.VISIBLE);
                }
                break;
            case "d":
                rlSoundUnpurchasedD.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundD.setVisibility(View.VISIBLE);
                } else {
                    tvSoundD.setVisibility(View.VISIBLE);
                }
                break;
            case "t":
                rlSoundUnpurchasedT.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundT.setVisibility(View.VISIBLE);
                } else {
                    tvSoundT.setVisibility(View.VISIBLE);
                }
                break;
            case "f":
                rlSoundUnpurchasedF.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundF.setVisibility(View.VISIBLE);
                } else {
                    tvSoundF.setVisibility(View.VISIBLE);
                }
                break;
            case "v":
                rlSoundUnpurchasedV.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundV.setVisibility(View.VISIBLE);
                } else {
                    tvSoundV.setVisibility(View.VISIBLE);
                }
                break;
            case "j":
                rlSoundUnpurchasedJ.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundJ.setVisibility(View.VISIBLE);
                } else {
                    tvSoundJ.setVisibility(View.VISIBLE);
                }
                break;
            case "ch":
                rlSoundUnpurchasedCH.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundCH.setVisibility(View.VISIBLE);
                } else {
                    tvSoundCH.setVisibility(View.VISIBLE);
                }
                break;
            case "y":
                rlSoundUnpurchasedY.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundY.setVisibility(View.VISIBLE);
                } else {
                    tvSoundY.setVisibility(View.VISIBLE);
                }
                break;
            case "ng":
                rlSoundUnpurchasedNG.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundNG.setVisibility(View.VISIBLE);
                } else {
                    tvSoundNG.setVisibility(View.VISIBLE);
                }
                break;
            case "s":
                rlSoundUnpurchasedS.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundS.setVisibility(View.VISIBLE);
                } else {
                    tvSoundS.setVisibility(View.VISIBLE);
                }
                break;
            case "z":
                rlSoundUnpurchasedZ.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundZ.setVisibility(View.VISIBLE);
                } else {
                    tvSoundZ.setVisibility(View.VISIBLE);
                }
                break;
            case "l":
                rlSoundUnpurchasedL.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundL.setVisibility(View.VISIBLE);
                } else {
                    tvSoundL.setVisibility(View.VISIBLE);
                }
                break;
            case "r":
                rlSoundUnpurchasedR.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundR.setVisibility(View.VISIBLE);
                } else {
                    tvSoundR.setVisibility(View.VISIBLE);
                }
                break;
            case "sh":
                rlSoundUnpurchasedSH.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundSH.setVisibility(View.VISIBLE);
                } else {
                    tvSoundSH.setVisibility(View.VISIBLE);
                }
                break;
            case "th":
                rlSoundUnpurchasedTH.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundTH.setVisibility(View.VISIBLE);
                } else {
                    tvSoundTH.setVisibility(View.VISIBLE);
                }
                break;
            case "zh":
                rlSoundUnpurchasedZH.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundZH.setVisibility(View.VISIBLE);
                } else {
                    tvSoundZH.setVisibility(View.VISIBLE);
                }
                break;
            case "all":
                rlSoundUnpurchasedP.setVisibility(View.GONE);
                rlSoundUnpurchasedM.setVisibility(View.GONE);
                rlSoundUnpurchasedN.setVisibility(View.GONE);
                rlSoundUnpurchasedK.setVisibility(View.GONE);
                rlSoundUnpurchasedG.setVisibility(View.GONE);
                rlSoundUnpurchasedH.setVisibility(View.GONE);
                rlSoundUnpurchasedW.setVisibility(View.GONE);
                rlSoundUnpurchasedT.setVisibility(View.GONE);
                rlSoundUnpurchasedD.setVisibility(View.GONE);
                rlSoundUnpurchasedF.setVisibility(View.GONE);
                rlSoundUnpurchasedV.setVisibility(View.GONE);
                rlSoundUnpurchasedJ.setVisibility(View.GONE);
                rlSoundUnpurchasedCH.setVisibility(View.GONE);
                rlSoundUnpurchasedY.setVisibility(View.GONE);
                rlSoundUnpurchasedNG.setVisibility(View.GONE);
                rlSoundUnpurchasedS.setVisibility(View.GONE);
                rlSoundUnpurchasedZ.setVisibility(View.GONE);
                rlSoundUnpurchasedL.setVisibility(View.GONE);
                rlSoundUnpurchasedR.setVisibility(View.GONE);
                rlSoundUnpurchasedSH.setVisibility(View.GONE);
                rlSoundUnpurchasedTH.setVisibility(View.GONE);
                rlSoundUnpurchasedZH.setVisibility(View.GONE);
                if(!checkIfTablet()){
                    rlSoundP.setVisibility(View.VISIBLE);
                    rlSoundM.setVisibility(View.VISIBLE);
                    rlSoundN.setVisibility(View.VISIBLE);
                    rlSoundK.setVisibility(View.VISIBLE);
                    rlSoundG.setVisibility(View.VISIBLE);
                    rlSoundH.setVisibility(View.VISIBLE);
                    rlSoundW.setVisibility(View.VISIBLE);
                    rlSoundT.setVisibility(View.VISIBLE);
                    rlSoundD.setVisibility(View.VISIBLE);
                    rlSoundF.setVisibility(View.VISIBLE);
                    rlSoundV.setVisibility(View.VISIBLE);
                    rlSoundJ.setVisibility(View.VISIBLE);
                    rlSoundCH.setVisibility(View.VISIBLE);
                    rlSoundY.setVisibility(View.VISIBLE);
                    rlSoundNG.setVisibility(View.VISIBLE);
                    rlSoundS.setVisibility(View.VISIBLE);
                    rlSoundZ.setVisibility(View.VISIBLE);
                    rlSoundL.setVisibility(View.VISIBLE);
                    rlSoundR.setVisibility(View.VISIBLE);
                    rlSoundSH.setVisibility(View.VISIBLE);
                    rlSoundTH.setVisibility(View.VISIBLE);
                    rlSoundZH.setVisibility(View.VISIBLE);
                } else {
                    tvSoundP.setVisibility(View.VISIBLE);
                    tvSoundM.setVisibility(View.VISIBLE);
                    tvSoundN.setVisibility(View.VISIBLE);
                    tvSoundK.setVisibility(View.VISIBLE);
                    tvSoundG.setVisibility(View.VISIBLE);
                    tvSoundH.setVisibility(View.VISIBLE);
                    tvSoundW.setVisibility(View.VISIBLE);
                    tvSoundT.setVisibility(View.VISIBLE);
                    tvSoundD.setVisibility(View.VISIBLE);
                    tvSoundF.setVisibility(View.VISIBLE);
                    tvSoundV.setVisibility(View.VISIBLE);
                    tvSoundJ.setVisibility(View.VISIBLE);
                    tvSoundCH.setVisibility(View.VISIBLE);
                    tvSoundY.setVisibility(View.VISIBLE);
                    tvSoundNG.setVisibility(View.VISIBLE);
                    tvSoundS.setVisibility(View.VISIBLE);
                    tvSoundZ.setVisibility(View.VISIBLE);
                    tvSoundL.setVisibility(View.VISIBLE);
                    tvSoundR.setVisibility(View.VISIBLE);
                    tvSoundSH.setVisibility(View.VISIBLE);
                    tvSoundTH.setVisibility(View.VISIBLE);
                    tvSoundZH.setVisibility(View.VISIBLE);
                }
                
                break;
        }
    }

    public String getSoundToRecord(String soundToRecord){
        String sound = "";
        switch (soundToRecord){
            case "air": sound = "r"; break;
            case "ar": sound = "r"; break;
            case "bl": sound = "l"; break;
            case "br": sound = "r"; break;
            case "dr": sound = "r"; break;
            case "ear": sound = "r"; break;
            case "er": sound = "r"; break;
            case "fl": sound = "l"; break;
            case "fr": sound = "r"; break;
            case "gl": sound = "l"; break;
            case "gr": sound = "r"; break;
            case "ire": sound = "r"; break;
            case "kl": sound = "l"; break;
            case "kr": sound = "r"; break;
            case "ks": sound = "s"; break;
            case "l": sound = "l"; break;
            case "ns": sound = "s"; break;
            case "or": sound = "r"; break;
            case "pl": sound = "l"; break;
            case "pr": sound = "r"; break;
            case "ps": sound = "s"; break;
            case "r": sound = "r"; break;
            case "rl": sound = "r"; break;
            case "s": sound = "s"; break;
            case "sk": sound = "s"; break;
            case "sl": sound = "l"; break;
            case "sm": sound = "s"; break;
            case "sn": sound = "s"; break;
            case "sp": sound = "s"; break;
            case "st": sound = "s"; break;
            case "sw": sound = "s"; break;
            case "tr": sound = "r"; break;
            case "ts": sound = "s"; break;
            default: sound = soundToRecord;
        }
        return sound;
    }

//    public void recordSoundInFileSystem(String soundName){
//        String listOfStrings = _fileHelper.getStringFromFile("purchasedSoundsFile", "b", this);
//        String[] listOfStringArray = listOfStrings.split(",");
//        String soundToRecord = "";
//        boolean soundAlreadyIsRecorded = false;
//        for(int i = 0; i < listOfStringArray.length; i++){
//            soundToRecord += "," + getSoundToRecord(listOfStringArray[i]);
//            if(soundName.equals(soundToRecord)){
//                soundAlreadyIsRecorded = true;
//            }
//        }
//        if(!soundAlreadyIsRecorded) {
//            _fileHelper.writeStringToFile("purchasedSoundsFile", listOfStrings + "," + soundToRecord, this);
//        }
//    }

    public boolean isSoundAlreadyInCommaSeparatedListOfSounds(String sound, String commaSeparatedListOfSounds){
        boolean soundAlreadyIn = false;
        String[] separatedList = commaSeparatedListOfSounds.split(",");
        for(int i = 0; i < separatedList.length; i++){
            if(separatedList[i].equals(sound)){
                soundAlreadyIn = true;
            }
        }
        return soundAlreadyIn;
    }

    public void setupAvailableSounds(){
        String listOfStrings = _fileHelper.getStringFromFile("purchasedSoundsFile", "b", this);
        if(listOfStrings.contains("all")){
            unlockSound("all");
        } else {
            String[] listOfStringArray = listOfStrings.split(",");
            for(int i = 0; i < listOfStringArray.length; i++) {
                unlockSound(listOfStringArray[i]);
            }
        }
    }

    public void getStorageAvailability(){
        _storageHelper = new StorageHelper();
        String availableInternalMemorySize = _storageHelper.getAvailableInternalMemorySize();
        String totalInternalMemorySize = _storageHelper.getTotalInternalMemorySize();
        _totalAvailableStorageOnDeviceInBytes = 0;
        if(availableInternalMemorySize.contains("KB")){
            _totalAvailableStorageOnDeviceInBytes = Long.valueOf(availableInternalMemorySize.replace("KB","").replace(",","")) * 1000;
        } else {
            _totalAvailableStorageOnDeviceInBytes = Long.valueOf(availableInternalMemorySize.replace("MB","").replace(",","")) * 1000 * 1000;
        }
        String memory = availableInternalMemorySize + " / " + totalInternalMemorySize;
        memory += "";
    }

    private class GoDaddyStorageHelper extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String results){
            if(results.equals("false")) {
                openNetworkIssuesDialog();
            } else if(results.equals("notEnoughStorage")){
                String requiredSpaceFormated = _storageHelper.formatSize(_requiredSpace);
                String totalAvailableFormated = _storageHelper.formatSize(_totalAvailableStorageOnDeviceInBytes);
                openStorageIssuesDialog(requiredSpaceFormated, totalAvailableFormated);
            } else {
                _currentSoundNumber = 1;
                new GoDaddyHelper().execute(_commaSeparatedListOfPurchasedSounds);
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String dataset = "";
            getStorageAvailability();
            if(isNetworkAvailable()) {
                try {
                    // This will load the MySQL driver, each DB has its own driver
                    Class.forName("com.mysql.jdbc.Driver");
                    // Setup the connection with the DB
                    connect = DriverManager.getConnection("jdbc:mysql://p3nlmysql113plsk.secureserver.net:3306/SpeechEssentials?" + "user=speechdbuser&password=uT5n7o9");//1:05, 1:02
                    // Statements allow to issue SQL queries to the database
                    statement = connect.createStatement();
                    String query2 = "";
                    if(params[0].contains("all")){
                        query2 = "SELECT COUNT(*) AS CNT, SUM(LENGTH(SOUND_IMAGE_FILE) / 1.24) + SUM(LENGTH(SOUND_AUDIO_FILE) / 1.24) AS REQUIRED_SPACE FROM SpeechEssentials.soundnames";
                    } else {
                        query2 = "SELECT COUNT(*) AS CNT, SUM(LENGTH(SOUND_IMAGE_FILE) / 1.24) + SUM(LENGTH(SOUND_AUDIO_FILE) / 1.24) AS REQUIRED_SPACE FROM SpeechEssentials.soundnames WHERE SOUND_NAME IN(" + params[0] + ")";
                    }
                    resultSet2 = statement.executeQuery(query2);
                    if(!checkIfEnoughSpaceOnDeviceAndSetNumberOfRows(resultSet2)){
                        dataset = "notEnoughStorage";
                    }
                } catch (Exception e) {
                    String ex = new String("Exception: " + e.getMessage());
                } finally {
                    close();
            }
            } else {
                dataset = "false";
            }
            return dataset;
        }
    }

    public void openStorageIssuesDialog(String required, String total) {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_network_dialog);
        _decorView.setSystemUiVisibility(_uiOptions);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        tvTitle.setText("Storage Error!");
        tvTitle.setTypeface(typeFaceAnja);
        TextView tvIssue = (TextView) dialog.findViewById(R.id.tvIssue);
        tvIssue.setText("not enough storage");
        tvIssue.setTypeface(typeFaceAnja);
        TextView tvIssueInformation = (TextView) dialog.findViewById(R.id.tvIssueInformation);
        tvIssueInformation.setText("Oops, we need a few things from Speech Essentials private cloud.\nTotal required space: " + required + " Available space: " + total);
        TextView tvQuit = (TextView) dialog.findViewById(R.id.tvQuit);
        tvQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tvTryAgain = (TextView) dialog.findViewById(R.id.tvTryAgain);
        tvTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                new GoDaddyStorageHelper().execute(_commaSeparatedListOfPurchasedSounds);
            }
        });
        View v1 = dialog.findViewById(R.id.v1);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v2 = dialog.findViewById(R.id.v2);
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v3 = dialog.findViewById(R.id.v3);
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v4 = dialog.findViewById(R.id.v4);
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void recordSoundsInFileSystem(String commaSeparatedListOfSoundsToRecord){
        _userMadeAPurchase = true;
        String commaSeparatedListOfAlreadyRecordedSounds = _fileHelper.getStringFromFile("purchasedSoundsFile", "b", this);
        String[] alreadyRecordedSoundsList = commaSeparatedListOfAlreadyRecordedSounds.split(",");
        String[] toRecordSoundList = commaSeparatedListOfSoundsToRecord.split(",");
        ArrayList<String> listOfAllStrings = new ArrayList<>();
        for(int i = 0; i < alreadyRecordedSoundsList.length; i++){
            listOfAllStrings.add(alreadyRecordedSoundsList[i]);
        }
        for(int i = 0; i < toRecordSoundList.length; i++){
            listOfAllStrings.add(toRecordSoundList[i]);
        }
        Set<String> hs = new HashSet<>();
        hs.addAll(listOfAllStrings);
        listOfAllStrings.clear();
        listOfAllStrings.addAll(hs);
        String soundToRecord = "";
        String commaSeparatedStringOfSoundsToRecord = "";

        for(int i = 0; i < listOfAllStrings.size(); i++){
            soundToRecord = getSoundToRecord(listOfAllStrings.get(i));
            if(i == 0){
                commaSeparatedStringOfSoundsToRecord += soundToRecord;
            } else {
                commaSeparatedStringOfSoundsToRecord += "," + soundToRecord;
            }
        }
        _fileHelper.writeStringToFile("purchasedSoundsFile", commaSeparatedStringOfSoundsToRecord, this);
    }

    public MediaPlayer playMediaPlayer(boolean looping){
        final MediaPlayer mediaPlayer = MediaPlayer.create(MenuActivity.this, R.raw.ukulele_fun_song);
        mediaPlayer.setLooping(looping);
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
            }
        });
        return mediaPlayer;
    }

    public void stopPlayer(MediaPlayer mediaPlayer){
        try {
            mediaPlayer.stop();
            mediaPlayer.release();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<String> getListOfAllSounds(){
        ArrayList<String> listOfAllSounds = new ArrayList<>();
//        listOfAllSounds.add("air");
//        listOfAllSounds.add("ar");
//        listOfAllSounds.add("b");
//        listOfAllSounds.add("bl");
//        listOfAllSounds.add("br");
        listOfAllSounds.add("ch");
        listOfAllSounds.add("d");
//        listOfAllSounds.add("dr");
//        listOfAllSounds.add("ear");
//        listOfAllSounds.add("er");
        listOfAllSounds.add("f");
//        listOfAllSounds.add("fl");
//        listOfAllSounds.add("fr");
        listOfAllSounds.add("g");
//        listOfAllSounds.add("gl");
//        listOfAllSounds.add("gr");
        listOfAllSounds.add("h");
//        listOfAllSounds.add("ire");
        listOfAllSounds.add("j");
        listOfAllSounds.add("k");
//        listOfAllSounds.add("kl");
//        listOfAllSounds.add("kr");
//        listOfAllSounds.add("ks");
        listOfAllSounds.add("l");
        listOfAllSounds.add("m");
        listOfAllSounds.add("n");
        listOfAllSounds.add("ng");
//        listOfAllSounds.add("ns");
//        listOfAllSounds.add("or");
        listOfAllSounds.add("p");
//        listOfAllSounds.add("pl");
//        listOfAllSounds.add("pr");
//        listOfAllSounds.add("ps");
        listOfAllSounds.add("r");
//        listOfAllSounds.add("rl");
        listOfAllSounds.add("s");
        listOfAllSounds.add("sh");
//        listOfAllSounds.add("sk");
//        listOfAllSounds.add("sl");
//        listOfAllSounds.add("sm");
//        listOfAllSounds.add("sn");
//        listOfAllSounds.add("sp");
//        listOfAllSounds.add("st");
//        listOfAllSounds.add("sw");
        listOfAllSounds.add("t");
        listOfAllSounds.add("th");
//        listOfAllSounds.add("tr");
//        listOfAllSounds.add("ts");
        listOfAllSounds.add("v");
        listOfAllSounds.add("w");
        listOfAllSounds.add("y");
        listOfAllSounds.add("z");
        listOfAllSounds.add("zh");
        return listOfAllSounds;
    }

    public ArrayList<String> getSpecialSoundsLists(String sound){
        ArrayList<String> specialSoundsList = new ArrayList<>();
        switch (sound){
            case "r":
                specialSoundsList.add("rl");
                specialSoundsList.add("air");
                specialSoundsList.add("ar");
                specialSoundsList.add("br");
                specialSoundsList.add("dr");
                specialSoundsList.add("ear");
                specialSoundsList.add("er");
                specialSoundsList.add("fr");
                specialSoundsList.add("gr");
                specialSoundsList.add("ire");
                specialSoundsList.add("kr");
                specialSoundsList.add("or");
                specialSoundsList.add("pr");
                specialSoundsList.add("tr");
                break;
            case "s":
                specialSoundsList.add("sl");
                specialSoundsList.add("ns");
                specialSoundsList.add("ps");
                specialSoundsList.add("sk");
                specialSoundsList.add("ts");
                specialSoundsList.add("sm");
                specialSoundsList.add("ks");
                specialSoundsList.add("sn");
                specialSoundsList.add("sp");
                specialSoundsList.add("st");
                specialSoundsList.add("sw");
                break;
            case "l":
                specialSoundsList.add("bl");
                specialSoundsList.add("fl");
                specialSoundsList.add("gl");
                specialSoundsList.add("kl");
                specialSoundsList.add("pl");
                specialSoundsList.add("rl");
                specialSoundsList.add("sl");
                break;
        }
        return specialSoundsList;
    }

    public boolean checkIfEnoughSpaceOnDeviceAndSetNumberOfRows(ResultSet resultSet) {
        boolean enoughSpace = false;
        _requiredSpace = 0;
        try {
            while (resultSet.next()) {
                _numberOfRowsToDownload = resultSet.getInt("CNT");
                _requiredSpace = resultSet.getLong("REQUIRED_SPACE");
                if (_requiredSpace >= _totalAvailableStorageOnDeviceInBytes) {
                    enoughSpace = false;
                } else {
                    enoughSpace = true;
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return enoughSpace;
    }

    private class GoDaddyHelper extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String results){
            if(results.equals("no_network")){
                openNetworkIssuesDialog();
            } else {
                //TODO: Done!
                unlockPurchasedSounds();
                _purchaseDialog.dismiss();
                if(_storeDialog != null){
                    _storeDialog.dismiss();
                }
                try{
                    updateDBVersionOnApp(_dbVersion);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            stopPlayer(_mp);
        }

        @Override
        protected String doInBackground(String... params) {
            _currentSoundNumber = 1;
            String[] purchasedSounds = params[0].split(",");
            String results = "";
            ArrayList<String> listOfSoundsToDownload = new ArrayList<>();
            for(int i = 0; i < purchasedSounds.length; i++){
                listOfSoundsToDownload.add(purchasedSounds[i].replace("\"", ""));
            }
            Set<String> hs = new HashSet<>();
            hs.addAll(listOfSoundsToDownload);
            listOfSoundsToDownload.clear();
            listOfSoundsToDownload.addAll(hs);
            if(isNetworkAvailable()) {
                try {
                    Collections.sort(listOfSoundsToDownload);
                    _numberOfSoundsToDownload = listOfSoundsToDownload.size();
                    for(int i = 0; i < listOfSoundsToDownload.size(); i++){
                        _currentSoundBeingDownloaded = listOfSoundsToDownload.get(i);
                        Message msg = handler.obtainMessage();
                        msg.what = 2;
                        handler.sendMessage(msg);
                        // This will load the MySQL driver, each DB has its own driver
                        Class.forName("com.mysql.jdbc.Driver");
                        // Setup the connection with the DB
                        connect = DriverManager.getConnection("jdbc:mysql://p3nlmysql113plsk.secureserver.net:3306/SpeechEssentials?" + "user=speechdbuser&password=uT5n7o9");//1:05, 1:02
                        // Statements allow to issue SQL queries to the database
                        statement = connect.createStatement();
                        // Result set get the result of the SQL query
                        String query = "SELECT * FROM SpeechEssentials.soundnames WHERE SOUND_NAME = '" + _currentSoundBeingDownloaded + "'";
                        resultSet = statement.executeQuery(query);
                        Message msg1 = handler.obtainMessage();
                        msg1.what = 3;
                        handler.sendMessage(msg1);
                        writeResultSet(resultSet);
                    }
                    Message msg2 = handler.obtainMessage();
                    msg2.what = 1;
                    handler.sendMessage(msg2);
                    Class.forName("com.mysql.jdbc.Driver");
                    // Setup the connection with the DB
                    connect = DriverManager.getConnection("jdbc:mysql://p3nlmysql113plsk.secureserver.net:3306/SpeechEssentials?" + "user=speechdbuser&password=uT5n7o9");//1:05, 1:02
                    // Statements allow to issue SQL queries to the database
                    statement = connect.createStatement();
                    // Result set get the result of the SQL query
                    String query = "SELECT * FROM SpeechEssentials.SoundNamesVersion";
                    resultSet = statement.executeQuery(query);
                    updateDBVersionOnApp(findVersionFromResultSet(resultSet));
                } catch (Exception e) {
                    String ex = new String("Exception: " + e.getMessage());
                    results = "exception";
                } finally {
                    close();
                }
            } else {
                results = "no_network";
            }
            return results;
        }
    }

    private class GoDaddyVersionHelper extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String results){
            if(results.equals("no_network")){
                //openNetworkIssuesDialog();
            } else if(results.equals("New Data Available")){
                _llUpdateLayout.setVisibility(View.VISIBLE);
                _llNonUpdateLayout.setVisibility(View.GONE);
                _llUUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openUpdateImagesDialog();
                        _llUpdateLayout.setVisibility(View.GONE);
                        _llNonUpdateLayout.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                _llUpdateLayout.setVisibility(View.GONE);
                _llNonUpdateLayout.setVisibility(View.VISIBLE);
            }
            checkForDiscount();
        }

        @Override
        protected String doInBackground(String... params) {
            String results = "";
            if(isNetworkAvailable()) {
                try {
                    // This will load the MySQL driver, each DB has its own driver
                    Class.forName("com.mysql.jdbc.Driver");
                    // Setup the connection with the DB
                    connect = DriverManager.getConnection("jdbc:mysql://p3nlmysql113plsk.secureserver.net:3306/SpeechEssentials?" + "user=speechdbuser&password=uT5n7o9");//1:05, 1:02
                    // Statements allow to issue SQL queries to the database
                    statement = connect.createStatement();
                    // Result set get the result of the SQL query
                    String query = "SELECT * FROM SpeechEssentials.SoundNamesVersion";
                    resultSet = statement.executeQuery(query);
                    String dbVersion = findVersionFromResultSet(resultSet);
                    String appDBVersion = getDBVersionOnApp();
                    _dbVersion = dbVersion;
                    if(!appDBVersion.equals(dbVersion)){
                        //Database is new, download new stuffs
                        results = "New Data Available";
                    }
                } catch (Exception e) {
                    String ex = new String("Exception: " + e.getMessage());
                } finally {
                    close();
                }
            } else {
                results = "no_network";
            }
            return results;
        }
    }

    public void openSaleDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_store_discount_dialog);
        _decorView.setSystemUiVisibility(_uiOptions);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        TextView tvLimited = (TextView) dialog.findViewById(R.id.tvLimited);
        TextView tvPreviousSingleCost = (TextView) dialog.findViewById(R.id.tvPreviousSingleCost);
        TextView tvPreviousAllCost = (TextView) dialog.findViewById(R.id.tvPreviousAllCost);
        TextView tvTakeMeToTheStore = (TextView) dialog.findViewById(R.id.tvTakeMeToTheStore);
        TextView tvStoreLater = (TextView) dialog.findViewById(R.id.tvStoreLater);
        ImageView ivSingleSound = (ImageView) dialog.findViewById(R.id.ivSingleSound);
        ImageView ivAllSounds = (ImageView) dialog.findViewById(R.id.ivAllSounds);
        ivSingleSound.setOnTouchListener(_scaleAnimation.onTouchListener);
        ivAllSounds.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvTakeMeToTheStore.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvStoreLater.setOnTouchListener(_scaleAnimation.onTouchListener);
        tvTitle.setTypeface(typeFaceAnja);
        tvTakeMeToTheStore.setTypeface(typeFaceAnja);
        tvStoreLater.setTypeface(typeFaceAnja);
        if(!checkIfTablet()) {
            tvTitle.setTextSize(getTextSize(40));
            tvLimited.setTextSize(getTextSize(18));
            tvPreviousSingleCost.setTextSize(getTextSize(11));
            tvPreviousAllCost.setTextSize(getTextSize(11));
            tvTakeMeToTheStore.setTextSize(getTextSize(25));
            tvStoreLater.setTextSize(getTextSize(25));
        }
        tvTakeMeToTheStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                openStoreDialog("m");
            }
        });
        ivSingleSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                openStoreDialog("m");
            }
        });
        ivAllSounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                openConfirmPurchaseDialog("sound_all");
            }
        });
        tvStoreLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v1 = dialog.findViewById(R.id.v1);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v2 = dialog.findViewById(R.id.v2);
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v3 = dialog.findViewById(R.id.v3);
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v4 = dialog.findViewById(R.id.v4);
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private class GoDaddyDiscountHelper extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String results){
            if(results.equals("true")){
                int saleDialogShownNumber = Integer.valueOf(_fileHelper.getStringFromFile("shownSaleDialog", "1", MenuActivity.this));
                if(_discountOn){
                    setupSaleItems();
                    if(saleDialogShownNumber == 1) {
                        openSaleDialog();
                        _fileHelper.writeStringToFile("shownSaleDialog", "2", MenuActivity.this);
                    } else {
                       String shownRateDialog = _fileHelper.getStringFromFile("shownRateAppDialog", "false", MenuActivity.this);
                        if(shownRateDialog.equals("false")){
                            if(_fileHelper.getStringFromFile("purchasedSoundsFile", "b", MenuActivity.this).contains("all")){
                                openRateDialog();
                            } else {
                                String[] listOfPurchasedSounds = _fileHelper.getStringFromFile("purchasedSoundsFile", "b", MenuActivity.this).split(",");
                                if(listOfPurchasedSounds.length > 1){
                                    openRateDialog();
                                }
                            }
                        }
                    }
                }
            } else {
                String shownRateDialog = _fileHelper.getStringFromFile("shownRateAppDialog", "false", MenuActivity.this);
                if(shownRateDialog.equals("false")){
                    if(_fileHelper.getStringFromFile("purchasedSoundsFile", "b", MenuActivity.this).contains("all")){
                        openRateDialog();
                    } else {
                        String[] listOfPurchasedSounds = _fileHelper.getStringFromFile("purchasedSoundsFile", "b", MenuActivity.this).split(",");
                        if(listOfPurchasedSounds.length > 1){
                            openRateDialog();
                        }
                    }
                }
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String results = "";
            if(isNetworkAvailable()) {
                try {
                    // This will load the MySQL driver, each DB has its own driver
                    Class.forName("com.mysql.jdbc.Driver");
                    // Setup the connection with the DB
                    connect = DriverManager.getConnection("jdbc:mysql://p3nlmysql113plsk.secureserver.net:3306/SpeechEssentials?" + "user=speechdbuser&password=uT5n7o9");//1:05, 1:02
                    // Statements allow to issue SQL queries to the database
                    statement = connect.createStatement();
                    // Result set get the result of the SQL query
                    String query = "SELECT * FROM SpeechEssentials.DiscountDim";
                    resultSet = statement.executeQuery(query);
                    int dbDiscount = findDiscountFromResultSet(resultSet);
                    if(dbDiscount > 0){
                        _discountOn = true;
                        results = "true";
                    }
                } catch (Exception e) {
                    String ex = new String("Exception: " + e.getMessage());
                } finally {
                    close();
                }
            } else {
                results = "no_network";
            }
            return results;
        }
    }

    private class GoDaddyOnDeviceDBVersionHelper extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String results){
            if(_mp!= null) {
                _mp.stop();
            }
            if(results.equals("refresh")){

            }
        }

        @Override
        protected String doInBackground(String... params) {
            String dataset = "";
            if(isNetworkAvailable()) {
                try {
                    // This will load the MySQL driver, each DB has its own driver
                    Class.forName("com.mysql.jdbc.Driver");
                    // Setup the connection with the DB
                    connect = DriverManager.getConnection("jdbc:mysql://p3nlmysql113plsk.secureserver.net:3306/SpeechEssentials?" + "user=speechdbuser&password=uT5n7o9");//1:05, 1:02
                    // Statements allow to issue SQL queries to the database
                    statement = connect.createStatement();
                    String query = "SELECT * FROM SpeechEssentials.SoundNamesVersion";
                    resultSet = statement.executeQuery(query);
                    updateOnDeviceDBVersionOnApp(findOnDeviceDBVersionFromResultSet(resultSet));
                } catch (Exception e) {
                    String ex = new String("Exception: " + e.getMessage());
                } finally {
                    close();
                }
            } else {
                dataset = "false";
            }
            return dataset;
        }
    }

    public void updateOnDeviceDBVersionOnApp(String dbVersion){
        _fileHelper.writeStringToFile("onDeviceDBVersion", dbVersion, this);
    }

    public String findOnDeviceDBVersionFromResultSet(ResultSet resultSet) throws SQLException {
        String version = "";
        while (resultSet.next()) {
            try{
                version = resultSet.getString("OpnAppDBVersion");
            } catch (Exception e) {

            }
        }
        return version;
    }

    public void openUpdateImagesDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_purchase_made);
        _decorView.setSystemUiVisibility(_uiOptions);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        //TODO: Why are we doing the spinners twice??
        _pbSpinner = (ProgressBar) dialog.findViewById(R.id.pbSpinner);
        _pbSpinner.setVisibility(View.GONE);
        _pbSpinnerIndeterminate = (ProgressBar) dialog.findViewById(R.id.pbSpinnerIndeterminate);
        _pbSpinnerIndeterminate.setVisibility(View.VISIBLE);
        _tvThankYou = (TextView) dialog.findViewById(R.id.tvThankYou);
        _tvPleaseWait = (TextView) dialog.findViewById(R.id.tvPleaseWait);
        _ivThankYou = (ImageView) dialog.findViewById(R.id.ivThankYou);
        _tvThankYou.setTypeface(typeFaceAnja);
        //TODO: Why are we doing the spinners twice??
        _soundDownloadSpinner = (ProgressBar) dialog.findViewById(R.id.pbSpinner);
        _soundDownloadSpinner.setVisibility(View.GONE);
        _soundDownloadSpinnerIndeterminate = (ProgressBar) dialog.findViewById(R.id.pbSpinnerIndeterminate);
        _soundDownloadSpinnerIndeterminate.setVisibility(View.VISIBLE);
        _tvPurchaseIncludes = (TextView) dialog.findViewById(R.id.tvPurchaseIncludes);
        _tvPurchaseIncludes.setText("We are always looking for ways to improve your experience. If you have any feedback for us, please don't hesitate to email us at com.speechessentials.speechessentials@gmail.com.");
        _tvThankYou.setText("We have made a few changes. How exciting!");
        dialog.show();
        _purchaseDialog = dialog;
        String listOfPurchases = _fileHelper.getStringFromFile("purchasedSoundsFile", "b", this);
        String[] purchaseList = listOfPurchases.split(",");
        ArrayList<String> listOfSounds = new ArrayList<>();
        for(int i = 0; i < purchaseList.length; i++){
            listOfSounds.add(purchaseList[i]);
        }
        _mp = playMediaPlayer(true);
        updateImagesAndAudio(listOfSounds);
    }

    public void updateImagesAndAudio(ArrayList<String> listOfPurchases){
        _fileHelper.deleteImageAndAudioContent(this);
        getPurchasedSoundFromDatabase(listOfPurchases, "", true, true);
    }

    public void getPurchasedSoundFromDatabase(ArrayList<String> listOfPurchases, String singleSound, boolean multipleSounds, boolean update){
        //TODO: handle when only one sound
        _listOfPurchases = new ArrayList<>();
        if(multipleSounds){
            try {
                _listOfPurchases = listOfPurchases;
                for(int i = 0; i < _listOfPurchases.size(); i++){
                    if(_listOfPurchases.get(i).contains("all")){
                        _listOfPurchases = getListOfAllSounds();
                        break;
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            if(singleSound.contains("all")){
                _listOfPurchases = getListOfAllSounds();
            } else {
                _listOfPurchases.add(singleSound);
            }
        }
        _commaSeparatedListOfPurchasedSounds = "";
        if(update){
            _commaSeparatedListOfPurchasedSounds = "\"b\"";
        }
        ArrayList<String> specialLetters = new ArrayList<>();
        for (int i = 0; i < _listOfPurchases.size(); i++) {
            String currentSound = _listOfPurchases.get(i);
            currentSound = currentSound.replace("sound_","").replace("_60d", "");
            if(currentSound.equals("r") || currentSound.equals("l") || currentSound.equals("s")){
                specialLetters.addAll(getSpecialSoundsLists(currentSound));
                for(int j = 0; j < specialLetters.size(); j++){
                    if(_commaSeparatedListOfPurchasedSounds.equals("")){
                        _commaSeparatedListOfPurchasedSounds += "\"" + specialLetters.get(j) + "\"";
                    } else {
                        _commaSeparatedListOfPurchasedSounds += ",\"" + specialLetters.get(j) + "\"";
                    }
                }
                specialLetters.clear();
            }
            if(_commaSeparatedListOfPurchasedSounds.equals("")) {
                _commaSeparatedListOfPurchasedSounds += "\"" + currentSound + "\"";
            } else {
                _commaSeparatedListOfPurchasedSounds += ",\"" + currentSound + "\"";
            }
        }
        recordSoundsInFileSystem(_commaSeparatedListOfPurchasedSounds.replace("\"", ""));
        new GoDaddyStorageHelper().execute(_commaSeparatedListOfPurchasedSounds);
    }

    public String getDBVersionOnApp(){
        _dbAppVersion = _fileHelper.getStringFromFile("dbVersion", "0", this);
        return _dbAppVersion;
    }

    public void updateDBVersionOnApp(String dbVersion){
        _fileHelper.writeStringToFile("dbVersion", dbVersion, this);
        mStopHandler = true;
        _llNonUpdateLayout.setVisibility(View.VISIBLE);
        _llUpdateLayout.setVisibility(View.GONE);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void openNetworkIssuesDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_network_dialog);
        _decorView.setSystemUiVisibility(_uiOptions);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        TextView tvQuit = (TextView) dialog.findViewById(R.id.tvQuit);
        tvQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tvTryAgain = (TextView) dialog.findViewById(R.id.tvTryAgain);
        tvTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                new GoDaddyHelper().execute("b");
            }
        });
        View v1 = dialog.findViewById(R.id.v1);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v2 = dialog.findViewById(R.id.v2);
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v3 = dialog.findViewById(R.id.v3);
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View v4 = dialog.findViewById(R.id.v4);
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        soundNamesList = new ArrayList<>();
        soundWordTypeMap = new HashMap<>();
        soundImageMap = new HashMap<>();
        soundAudioMap = new HashMap<>();
        int progress = 0;
        while (resultSet.next()) {
            _pbSpinner.setProgress(progress);
            String soundWordType = resultSet.getString("SOUND_POSITION_NAME");
            try{
                if(soundWordType.contains("sentence")){
                    addSoundAudioToSoundAudioMap(resultSet.getBlob("SOUND_AUDIO_FILE"), "s_" + resultSet.getString("SOUND_IMAGE_NAME"));
                } else if(soundWordType.contains("swap")){
                    addSoundAudioToSoundAudioMap(resultSet.getBlob("SOUND_AUDIO_FILE"), "swap_" + resultSet.getString("SOUND_IMAGE_NAME"));
                } else {
                    addSoundAudioToSoundAudioMap(resultSet.getBlob("SOUND_AUDIO_FILE"), resultSet.getString("SOUND_IMAGE_NAME"));
                    addSoundImageToSoundImageMap(resultSet.getBlob("SOUND_IMAGE_FILE"), resultSet.getString("SOUND_IMAGE_NAME"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            progress++;
        }
        _pbSpinner.setProgress(_pbSpinner.getMax());
    }

    public int findDiscountFromResultSet(ResultSet resultSet) throws SQLException {
        int version = 0;
        Date startDiscountDate = null;
        Date endDiscountDate = null;
        Date today = new Date();
        while (resultSet.next()) {
            try{
                startDiscountDate = resultSet.getDate("DiscountStartDate");
                endDiscountDate = resultSet.getDate("DiscountEndDate");
                if(today.equals(startDiscountDate) || today.equals(endDiscountDate)){
                    version = resultSet.getInt("DiscountPercent");
                }
                if(today.after(startDiscountDate) && today.before(endDiscountDate)){
                    version = resultSet.getInt("DiscountPercent");
                }

            } catch (Exception e) {

            }
        }
        return version;
    }

    public String findVersionFromResultSet(ResultSet resultSet) throws SQLException {
        String version = "";
        while (resultSet.next()) {
            try{
                version = resultSet.getString("VERSION");
            } catch (Exception e) {

            }
        }
        return version;
    }

    private void addSoundImageToSoundImageMap(Blob soundBlob, String soundName) throws SQLException {
        Bitmap soundBitmap = BitmapFactory.decodeStream(soundBlob.getBinaryStream());
        _fileHelper.saveToInternalStorage(this, soundBitmap, soundName);
        soundBlob.free();
    }

    private void addSoundAudioToSoundAudioMap(Blob soundBlob, String soundName) throws SQLException {
        int blobLength = (int) soundBlob.length();
        byte[] blobAsBytes = soundBlob.getBytes(1, blobLength);
        try{
            // create temp file that will hold byte array
            File tempMp3 = File.createTempFile(soundName, "mp3", this.getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(blobAsBytes);
            FileInputStream fis = new FileInputStream(tempMp3);
            _fileHelper.saveAudioInputStreamToInternalStorage(this, fis, soundName);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            soundBlob.free();
        }
    }

    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");
            if (resultCode == RESULT_OK) {
                try {
                    JSONObject jo = new JSONObject(purchaseData);
                    String sku = jo.getString("productId");
                    userMadeAPurchase(sku.replace("sound_","").replace("_60d",""), false);
                }
                catch (JSONException e) {
                    //userMadeAPurchase("Failed to parse purchase data.");
                    e.printStackTrace();
                }
            } else {
                //userMadeAPurchase("m");
            }
        } else {
            int result = resultCode;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        _decorView.setSystemUiVisibility(_uiOptions);
        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            _decorView.setSystemUiVisibility(_uiOptions);
        } else {
            _decorView.setSystemUiVisibility(_uiOptions);
        }
    }
}
