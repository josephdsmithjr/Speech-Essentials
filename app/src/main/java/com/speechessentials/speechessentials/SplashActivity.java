package com.speechessentials.speechessentials;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.speechessentials.speechessentials.Utility.ConnectionHelper;
import com.speechessentials.speechessentials.Utility.DatabaseOperationsHelper;
import com.speechessentials.speechessentials.Utility.FileHelper;
import com.speechessentials.speechessentials.Utility.GooglePlayHelper;
import com.speechessentials.speechessentials.Utility.StorageHelper;
import com.speechessentials.speechessentials.util.IabHelper;

import io.fabric.sdk.android.Fabric;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by josephdsmithjr on 4/14/2014.
 */
public class SplashActivity extends Activity {

    View _decorView;
    ProgressBar _pbSpinner, _pbSpinnerIndeterminate;
    int _uiOptions;
    boolean _firstTime;
    MediaPlayer _mp;
    ArrayList<String> soundNamesList, _listOfPurchases, _remainingListOfPurchases;
    HashMap<String, String> soundWordTypeMap;
    HashMap<String, Bitmap> soundImageMap;
    HashMap<String, FileInputStream> soundAudioMap;
    FileHelper _fileHelper;
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private ResultSet resultSet2 = null;
    IInAppBillingService mService;
    Typeface _typeFaceAnja;
    ServiceConnection mServiceConn;
    IabHelper mHelper;
    String _commaSeparatedListOfPurchasedSounds;
    StorageHelper _storageHelper;
    GooglePlayHelper _googlePlayHelper;
    long _totalAvailableStorageOnDeviceInBytes, _requiredSpace;
    int _numberOfRowsToDownload, _numberOfSoundsToDownload, _currentSoundNumber;
    ImageView _ivThankYou;
    TextView _tvThankYou, _tvPurchaseIncludes, _tvPleaseWait, _tvSpeechUrl;
    ProgressBar _soundDownloadSpinnerIndeterminate, _soundDownloadSpinner;
    Dialog _purchaseDialog;
    String _currentSoundBeingDownloaded;
    TextView _tvDownloadingContent;
    AudioManager amanager;
    ConnectionHelper connectionHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        //connectionHelper = new ConnectionHelper();
        //-100 is worst, 0 is best
        //int wifiStrength = connectionHelper.checkInternetStrength(this);
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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setGoogleAnalytics();
        _typeFaceAnja = Typeface.createFromAsset(getAssets(), "font/anja.ttf");
        _tvSpeechUrl = (TextView) findViewById(R.id.tvSpeechUrl);
        _tvSpeechUrl.setTypeface(_typeFaceAnja);
        _fileHelper = new FileHelper();
        _googlePlayHelper = new GooglePlayHelper();
        _remainingListOfPurchases = new ArrayList<>();
        getStorageAvailability();
        DatabaseOperationsHelper dop = new DatabaseOperationsHelper(this);
        //long numberOfRows = dop.getNumberOfSoundNamesRows();
        int numberOfImages = _fileHelper.getNumberOfFilesInImageDirectory(SplashActivity.this);
        if(numberOfImages > 0){
            _firstTime = false;
        } else {
            _firstTime = true;
        }
        _tvDownloadingContent = (TextView) findViewById(R.id.tvDownloadingContent);
        _pbSpinner = (ProgressBar) findViewById(R.id.pbSpinner);
        _pbSpinner.setVisibility(View.GONE);
        _pbSpinnerIndeterminate = (ProgressBar) findViewById(R.id.pbSpinnerIndeterminate);
        _pbSpinnerIndeterminate.setVisibility(View.GONE);
        if(_firstTime){
            playIntroMusic();
        } else {
 //           DatabaseOperationsHelper databaseOperationsHelper = new DatabaseOperationsHelper(SplashActivity.this);
//            databaseOperationsHelper.updateTheDatabase(SplashActivity.this);
            _pbSpinnerIndeterminate.setVisibility(View.INVISIBLE);
            _tvDownloadingContent.setVisibility(View.INVISIBLE);
        }
        try {
            amanager = (AudioManager) getSystemService(AUDIO_SERVICE);
            //turn off sound, disable notifications
            amanager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
        } catch(Exception e){
            e.printStackTrace();
        }
        new DatabaseWorker().execute();
        //TODO: Don't record the sounds until you have actually downloaded them.
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
        tracker.setScreenName("splash screen");
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

    public void playIntroMusic(){
       _mp = MediaPlayer.create(this, R.raw.ukulele_fun_song);
        _mp.setLooping(true);
        _mp.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        _mp.start();
    }

    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                _pbSpinnerIndeterminate.setVisibility(View.GONE);
                _pbSpinner.setVisibility(View.VISIBLE);
            } else if(msg.what == 2){
                if(!_mp.isPlaying()) {
                    _mp.setLooping(true);
                    _mp.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
                    _mp.start();
                }
                _pbSpinner.setVisibility(View.GONE);
                _pbSpinnerIndeterminate.setVisibility(View.GONE);
                if(_purchaseDialog == null){
                    openUpdateImagesDialog();
                }
                String ivThankyouCard = _currentSoundBeingDownloaded + "_cards";
                int drawable = getResources().getIdentifier(ivThankyouCard, "drawable", getPackageName());
                _ivThankYou.setImageResource(drawable);
                _tvThankYou.setText("Downloading the " + _currentSoundBeingDownloaded.toUpperCase() + " sound now.");
                _tvPleaseWait.setText("Downloading sound (" + _currentSoundNumber + " / " + _numberOfSoundsToDownload + ")");
                _tvPurchaseIncludes.setText("The " + _currentSoundBeingDownloaded + " sound includes multiple word and sentence flashcards, sentence word swap, a memory game section and a word find game section");
                _soundDownloadSpinner.setMax(getNumberOfSoundsToDownloadForSound(_currentSoundBeingDownloaded));
                _soundDownloadSpinnerIndeterminate.setVisibility(View.VISIBLE);
                _soundDownloadSpinner.setVisibility(View.GONE);
            } else if(msg.what == 3){
                _soundDownloadSpinnerIndeterminate.setVisibility(View.GONE);
                _soundDownloadSpinner.setVisibility(View.VISIBLE);
                _currentSoundNumber++;
            } else if(msg.what == 4){
                _purchaseDialog.dismiss();
                _pbSpinner.setVisibility(View.GONE);
                _pbSpinnerIndeterminate.setVisibility(View.VISIBLE);
            } else if(msg.what == 5){
                _pbSpinner.setVisibility(View.VISIBLE);
            } else if(msg.what == 6){
                _pbSpinner.setVisibility(View.GONE);
            }
            super.handleMessage(msg);
        }
    };

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
        _tvThankYou = (TextView) dialog.findViewById(R.id.tvThankYou);
        _tvThankYou.setTypeface(_typeFaceAnja);
        _tvPleaseWait = (TextView) dialog.findViewById(R.id.tvPleaseWait);
        _ivThankYou = (ImageView) dialog.findViewById(R.id.ivThankYou);
        _soundDownloadSpinner = (ProgressBar) dialog.findViewById(R.id.pbSpinner);
        _soundDownloadSpinner.setVisibility(View.GONE);
        _soundDownloadSpinnerIndeterminate = (ProgressBar) dialog.findViewById(R.id.pbSpinnerIndeterminate);
        _soundDownloadSpinnerIndeterminate.setVisibility(View.VISIBLE);
        _tvPurchaseIncludes = (TextView) dialog.findViewById(R.id.tvPurchaseIncludes);
        _tvPurchaseIncludes.setText("The " + _currentSoundBeingDownloaded + " sound includes multiple word and sentence flashcards, sentence word swap, a memory game section and a word find game section");
        _tvPleaseWait.setText("Downloading sound (" + _currentSoundNumber + " / " + _numberOfSoundsToDownload + ")");
        _tvThankYou.setText("Downloading the " + _currentSoundBeingDownloaded + " sound now.");
        dialog.show();
        _purchaseDialog = dialog;
    }

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

    private class DatabaseWorker extends AsyncTask<Integer, Void, Integer> {

        @Override
        protected void onPostExecute(Integer results){
            if(_firstTime) {
                checkForListOfUserPurchasesAndInitGoDaddyHelper();
                _pbSpinner.setVisibility(View.GONE);
                _pbSpinnerIndeterminate.setVisibility(View.VISIBLE);
            } else if (results == 1){
                new GoDaddyOnDeviceDBVersionHelper().execute("");
            } else {
                if(_mp!= null) {
                    _mp.stop();
                }
                // Execute some code after 2 seconds have passed
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent mainIntent = new Intent(SplashActivity.this, MenuActivity.class);
                        SplashActivity.this.startActivity(mainIntent);
                        SplashActivity.this.finish();
                    }
                }, 2000);
            }
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            int result = 0;
            DatabaseOperationsHelper databaseOperationsHelper = new DatabaseOperationsHelper(SplashActivity.this);
            try{
                Message msg = handler.obtainMessage();
                msg.what = 5;
                handler.sendMessage(msg);
            } catch (Exception e){
                e.printStackTrace();
            }
            if(databaseOperationsHelper.ensureDatabaseIsUpToDate(SplashActivity.this, _pbSpinner)){
                result =  1;
            }
            if(isNetworkAvailable()) {
                if(_firstTime){
                    try {
                        // This will load the MySQL driver, each DB has its own driver
                        Class.forName("com.mysql.jdbc.Driver");
                        // Setup the connection with the DB
                        connect = DriverManager.getConnection("jdbc:mysql://p3nlmysql113plsk.secureserver.net:3306/SpeechEssentials?" + "user=speechdbuser&password=uT5n7o9");//1:05, 1:02
                        // Statements allow to issue SQL queries to the database
                        statement = connect.createStatement();
                        String query = "SELECT * FROM SpeechEssentials.SoundNamesVersion";
                        resultSet = statement.executeQuery(query);
                        int dbOnDeviceVersion = findOnDeviceDBVersionFromResultSet(resultSet);
                        databaseOperationsHelper.ensureDatabaseIsUpToDate(SplashActivity.this, _pbSpinner);
                        updateOnDeviceDBVersionOnApp(dbOnDeviceVersion);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        close();
                    }
                }
            } else {
                result = -1;
            }
            return result;
        }
    }

    public void updateTextView(final String text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _tvDownloadingContent.setText(text);
            }
        });
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
            if(_firstTime){
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
            }
            return dataset;
        }
    }

    private class GoDaddyOnDeviceDBVersionHelper extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String results){
            if(_mp!= null) {
                _mp.stop();
            }
            try{
                Message msg = handler.obtainMessage();
                msg.what = 6;
                handler.sendMessage(msg);
            } catch (Exception e){

            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Intent mainIntent = new Intent(SplashActivity.this, MenuActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
            }, 500);
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
                    int dbOnDeviceVersion = findOnDeviceDBVersionFromResultSet(resultSet);
                    int currentOnDeviceVersion = Integer.valueOf(_fileHelper.getStringFromFile("onDeviceDBVersion", "0", SplashActivity.this));
                    if(dbOnDeviceVersion != currentOnDeviceVersion){
                        DatabaseOperationsHelper databaseOperationsHelper = new DatabaseOperationsHelper(SplashActivity.this);
                        databaseOperationsHelper.refreshTables(databaseOperationsHelper, SplashActivity.this, _pbSpinner);
                        updateOnDeviceDBVersionOnApp(dbOnDeviceVersion);
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

    private class GoDaddyHelper extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String results){
            if(results.equals("false")) {
                openNetworkIssuesDialog();
            } else if(results.equals("exception")){
                try {
                    _purchaseDialog.dismiss();
                } catch (Exception e){

                }
                _pbSpinnerIndeterminate.setVisibility(View.GONE);
                _pbSpinner.setVisibility(View.GONE);
                Toast.makeText(SplashActivity.this, "Connection timed out...please use a better network", Toast.LENGTH_SHORT).show();
            } else {
                if(_mp!= null) {
                    _mp.stop();
                }
                Intent mainIntent = new Intent(SplashActivity.this, MenuActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }

        @Override
        protected String doInBackground(String... params) {
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

    public String getCurrentSoundBeingDownloaded(String currentSound){
        String sound = "";
        //+ "_cards";
        switch (currentSound){
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
            default: sound = currentSound;
        }
        return sound + "_cards";
    }

//    public void loadImagesForSmallDevices(){
//        if(_listOfSoundsToDownload.contains("all")){
//            _remainingListOfPurchases = getListOfAllSounds();
//        } else {
//            _remainingListOfPurchases = _listOfSoundsToDownload;
//        }
//        if(_listOfSoundsToDownload.size() > 0) {
//            new GoDaddySmallDeviceHelper().execute(_remainingListOfPurchases.get(0).replace("sound_", ""));
//            _remainingListOfPurchases.remove(0);
//        } else {
//            new GoDaddySmallDeviceHelper().execute("b");
//        }
//    }
//
//    public void loadNextSound(){
//        new GoDaddySmallDeviceHelper().execute(_remainingListOfPurchases.get(0).replace("sound_", ""));
//        _remainingListOfPurchases.remove(0);
//    }

    public ArrayList<String> getListOfAllSounds() {
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

    public boolean checkIfEnoughSpaceOnDeviceAndSetNumberOfRows(ResultSet resultSet) throws  SQLException {
        boolean enoughSpace = false;
        _requiredSpace = 0;
        while (resultSet.next()) {
            _numberOfRowsToDownload = resultSet.getInt("CNT");
            _requiredSpace = resultSet.getLong("REQUIRED_SPACE");
            if(_requiredSpace >= _totalAvailableStorageOnDeviceInBytes){
                enoughSpace = false;
            } else {
                enoughSpace = true;
            }
        }
        return enoughSpace;
    }

    public int findOnDeviceDBVersionFromResultSet(ResultSet resultSet) throws SQLException {
        int version = 0;
        while (resultSet.next()) {
            try {
                String onAppVersion = resultSet.getString("OnAppDBVersion");
                version = Integer.valueOf(onAppVersion);
            } catch (Exception e) {
                e.printStackTrace();
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

    public void updateDBVersionOnApp(String dbVersion){
        _fileHelper.writeStringToFile("dbVersion", dbVersion, this);
    }

    public void updateOnDeviceDBVersionOnApp(int dbVersion){
        _fileHelper.writeStringToFile("onDeviceDBVersion", dbVersion + "", this);
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
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        tvTitle.setTypeface(_typeFaceAnja);
        TextView tvIssue = (TextView) dialog.findViewById(R.id.tvIssue);
        tvIssue.setTypeface(_typeFaceAnja);
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
                checkForListOfUserPurchasesAndInitGoDaddyHelper();
            }
        });
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
        tvTitle.setTypeface(_typeFaceAnja);
        TextView tvIssue = (TextView) dialog.findViewById(R.id.tvIssue);
        tvIssue.setText("not enough storage");
        tvIssue.setTypeface(_typeFaceAnja);
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
    }

    public void checkForListOfUserPurchasesAndInitGoDaddyHelper(){
        if(isNetworkAvailable()) {
            setupGooglePlay();
        } else {
            openNetworkIssuesDialog();
        }
    }

    public void setupGooglePlay(){
        mHelper = _googlePlayHelper.getmHelper(this);
        mServiceConn = _googlePlayHelper.createIInAppBillingServiceConnectionForSplash(this, SplashActivity.this);
    }

    public void getListOfPurchasesAndExecuteGoDaddy(IInAppBillingService googlePlaymService){
       _listOfPurchases = new ArrayList<>();
        if(googlePlaymService != null){
            mService = googlePlaymService;
        }
        try {
            _listOfPurchases = _googlePlayHelper.getListOfPurchasedSounds(this);
            for(int i = 0; i < _listOfPurchases.size(); i++){
                if(_listOfPurchases.get(i).contains("all")){
                    _listOfPurchases = getListOfAllSounds();
                    break;
                }
            }
//            _numberOfSoundsToDownload = _listOfPurchases.size() + 1;
        } catch (Exception e){
            e.printStackTrace();
        }
        _commaSeparatedListOfPurchasedSounds = "\"b\"";
        ArrayList<String> specialLetters = new ArrayList<>();
        for (int i = 0; i < _listOfPurchases.size(); i++) {
            String currentSound = _listOfPurchases.get(i);
            currentSound = currentSound.replace("sound_","").replace("_60d","");
            if(currentSound.equals("r") || currentSound.equals("l") || currentSound.equals("s")){
                specialLetters.addAll(getSpecialSoundsLists(currentSound));
                for(int j = 0; j < specialLetters.size(); j++){
                    _commaSeparatedListOfPurchasedSounds += ",\"" + specialLetters.get(j) + "\"";
                }
                specialLetters.clear();
            }
            _commaSeparatedListOfPurchasedSounds += ",\"" + currentSound + "\"";
        }
        //_listOfPurchases.add("b");
        recordSoundsInFileSystem(_listOfPurchases);
        new GoDaddyStorageHelper().execute(_commaSeparatedListOfPurchasedSounds);
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        soundNamesList = new ArrayList<>();
        soundWordTypeMap = new HashMap<>();
        soundImageMap = new HashMap<>();
        soundAudioMap = new HashMap<>();
        int progress = 0;
        while (resultSet.next()) {
            _soundDownloadSpinner.setProgress(progress);
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

            }
            progress++;
        }
    }

    public void recordSoundsInFileSystem(ArrayList<String> listOfPurchases){
        String commaSeparatedStringOfSoundsToRecord = "b";
        for(int i = 0; i < listOfPurchases.size(); i++){
            commaSeparatedStringOfSoundsToRecord += "," + listOfPurchases.get(i).replace("sound_", "").replace("_60d","");
        }
//        String commaSeparatedListOfAlreadyRecordedSounds = _fileHelper.getStringFromFile("purchasedSoundsFile", "b", this);
//        String[] alreadyRecordedSoundsList = commaSeparatedListOfAlreadyRecordedSounds.split(",");
//        String[] toRecordSoundList = commaSeparatedListOfSoundsToRecord.split(",");
//        ArrayList<String> listOfAllStrings = new ArrayList<>();
//        for(int i = 0; i < alreadyRecordedSoundsList.length; i++){
//            listOfAllStrings.add(alreadyRecordedSoundsList[i]);
//        }
//        for(int i = 0; i < toRecordSoundList.length; i++){
//            listOfAllStrings.add(toRecordSoundList[i]);
//        }
//        Set<String> hs = new HashSet<>();
//        hs.addAll(listOfAllStrings);
//        listOfAllStrings.clear();
//        listOfAllStrings.addAll(hs);
//        String soundToRecord = "";
//        String commaSeparatedStringOfSoundsToRecord = "";
//
//        for(int i = 0; i < listOfAllStrings.size(); i++){
//            soundToRecord = getSoundToRecord(listOfAllStrings.get(i));
//            if(!isSoundAlreadyInCommaSeparatedListOfSounds(soundToRecord, commaSeparatedListOfAlreadyRecordedSounds)){
//                commaSeparatedStringOfSoundsToRecord += "," + soundToRecord;
//            }
//        }
        _fileHelper.writeStringToFile("purchasedSoundsFile", commaSeparatedStringOfSoundsToRecord, this);
    }

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

    private ArrayList<String> getSoundNames(ResultSet resultSet) throws SQLException {
        ArrayList<String> soundNamesList = new ArrayList<>();
        while (resultSet.next()) {
            String soundName = resultSet.getString("SOUND_NAME");
            soundNamesList.add(soundName);
        }
        return soundNamesList;
    }

    private HashMap<String, Integer> getSoundWordType(ResultSet resultSet) throws SQLException {
        HashMap<String, Integer> soundWordTypeMap = new HashMap<>();
        while (resultSet.next()) {
            String soundName = resultSet.getString("SOUND_NAME");
            int soundWordType = resultSet.getInt("SOUND_POSITION_NAME");
            soundWordTypeMap.put(soundName, soundWordType);
        }
        return soundWordTypeMap;
    }

    private void addSoundImageToSoundImageMap(Blob soundBlob, String soundName) throws Exception {
        Bitmap soundBitmap = BitmapFactory.decodeStream(soundBlob.getBinaryStream());
        //soundImageMap.put(soundName, soundBitmap);
        //release the blob and free up memory. (since JDBC 4.0)
        _fileHelper.saveToInternalStorage(this, soundBitmap, soundName);
        soundBlob.free();
    }

    private void addSoundAudioToSoundAudioMap(Blob soundBlob, String soundName) throws Exception {
        int blobLength = (int) soundBlob.length();
        byte[] blobAsBytes = soundBlob.getBytes(1, blobLength);
        ByteArrayInputStream audioStream = new ByteArrayInputStream(blobAsBytes);
        //release the blob and free up memory. (since JDBC 4.0)
        try{
            // create temp file that will hold byte array
            File tempMp3 = File.createTempFile(soundName, "mp3", this.getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(blobAsBytes);
            FileInputStream fis = new FileInputStream(tempMp3);
            _fileHelper.saveAudioInputStreamToInternalStorage(this, fis, soundName);
            //soundAudioMap.put(soundName, fis);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            soundBlob.free();
        }
    }

    public ArrayList<String> getListOfSentences(String soundName){
        DatabaseOperationsHelper doh = new DatabaseOperationsHelper(this);
        ArrayList<String> listOfSentences = doh.getSentenceList(doh, soundName);
        return listOfSentences;
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
}