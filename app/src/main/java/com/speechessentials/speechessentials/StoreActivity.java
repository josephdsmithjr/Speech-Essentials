package com.speechessentials.speechessentials;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.speechessentials.speechessentials.Utility.SoundHelper;
import com.speechessentials.speechessentials.util.IabHelper;
import com.speechessentials.speechessentials.util.IabResult;
import com.speechessentials.speechessentials.util.Inventory;
import com.speechessentials.speechessentials.util.Purchase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by josephdsmithjr on 6/8/2015.
 */
public class StoreActivity  extends Activity {

    View _decorView;
    int _uiOptions;
    TextView tvStoreSoundName, tvStoreSoundDescription, tvCurrentStoreSelected, tvCurrentStoreUnselected;
    SoundHelper _soundHelper;
    HashMap<TextView, TextView> storeTextViewHashMap;
    Typeface typeFaceAnja;
    String _soundName;

    //Google Play Stuffs
    // Debug tag, for logging
    static final String TAG = "SpeechEssentials";
    static final String SKU_SOUND_B = "sound_b";
    static final String SKU_SOUND_P = "sound_p";
    static final String SKU_SOUND_M = "sound_m";
    static final String SKU_SOUND_N = "sound_n";
    static final String SKU_SOUND_K = "sound_k";
    static final String SKU_SOUND_G = "sound_g";
    static final String SKU_SOUND_H = "sound_h";
    static final String SKU_SOUND_W = "sound_w";
    static final String SKU_SOUND_T = "sound_t";
    static final String SKU_SOUND_D = "sound_d";
    static final String SKU_SOUND_F = "sound_f";
    static final String SKU_SOUND_V = "sound_v";
    static final String SKU_SOUND_J = "sound_j";
    static final String SKU_SOUND_CH = "sound_ch";
    static final String SKU_SOUND_Y = "sound_y";
    static final String SKU_SOUND_NG = "sound_ng";
    static final String SKU_SOUND_S = "sound_s";
    static final String SKU_SOUND_Z = "sound_z";
    static final String SKU_SOUND_L = "sound_l";
    static final String SKU_SOUND_R = "sound_r";
    static final String SKU_SOUND_SH = "sound_sh";
    static final String SKU_SOUND_TH = "sound_th";
    static final String SKU_SOUND_ZH = "sound_zh";
    static final String SKU_SOUND_ALL = "sound_all";

    // Does the user have the all sounds purchase?
    boolean mPurchasedAllSounds = false;

    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 10001;

    // The helper object
    IabHelper mHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_dialog);
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
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArHFPNVo4oLuaYT80qID82AT8LrJ3DRFPDDsE//xkYeKGMkg/Rpd9cMRQ2D0KtmAdg6AjmZfyYTxjkwlXJEN0QGFyLnbEixCLHOs/n2qYSU60Xjjs66nvOcrMkMquud0B7dBNFMDb83E3g70YtgkWIfdU5fliE1kbwL/C/ZNA9Oo8TVhv77ZJm9RjHDjMwkaIB0jnJGpFN1VkSXbGi2aMmLOxiuujoeLivF3NKPR+B+oW7zsMxsnH7bncoREfhTXpTlhkIxOnpVk/+/M17WVzXOaDsfhR0ZGNDIMZjznhHkhZECqJkbKmUmvKkYfIT7Y41XCdL4vC4jZCDNPU6TgLAwIDAQAB";
        // Create the helper, passing it our context and the public key to verify signatures with
        Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(this, base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(true);

        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    complain("Problem setting up in-app billing: " + result);
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d(TAG, "Setup successful. Querying inventory.");
                ArrayList<String> skuList = new ArrayList();
                skuList.add(SKU_SOUND_B);
                skuList.add(SKU_SOUND_P);
                skuList.add(SKU_SOUND_M);
                skuList.add(SKU_SOUND_N);
                skuList.add(SKU_SOUND_K);
                skuList.add(SKU_SOUND_G);
                skuList.add(SKU_SOUND_H);
                skuList.add(SKU_SOUND_W);
                skuList.add(SKU_SOUND_T);
                skuList.add(SKU_SOUND_D);
                skuList.add(SKU_SOUND_F);
                skuList.add(SKU_SOUND_V);
                skuList.add(SKU_SOUND_J);
                skuList.add(SKU_SOUND_CH);
                skuList.add(SKU_SOUND_Y);
                skuList.add(SKU_SOUND_NG);
                skuList.add(SKU_SOUND_S);
                skuList.add(SKU_SOUND_Z);
                skuList.add(SKU_SOUND_L);
                skuList.add(SKU_SOUND_R);
                skuList.add(SKU_SOUND_SH);
                skuList.add(SKU_SOUND_TH);
                skuList.add(SKU_SOUND_ZH);
                skuList.add(SKU_SOUND_ALL);
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
        setGoogleAnalytics();
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
        tracker.setScreenName("Store Activity");
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

    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                complain("Failed to query inventory: " + result);
                return;
            }

            Log.d(TAG, "Query inventory was successful.");

            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */

            // Do we have the premium upgrade?
            Purchase premiumPurchase = inventory.getPurchase(SKU_SOUND_ALL);
            mPurchasedAllSounds = (premiumPurchase != null && verifyDeveloperPayload(premiumPurchase));
            Log.d(TAG, "User is " + (mPurchasedAllSounds ? "PREMIUM" : "NOT PREMIUM"));

            //updateUi();
            setWaitScreen(false);
            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };

    // User clicked the "Buy Gas" button
    public void onBuyGasButtonClicked(View arg0) {

    }

    public void purchaseASound(String soundSKU){
        Log.d(TAG, "Purchase sound button clicked.");

        if (mPurchasedAllSounds) {
            complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
            return;
        }

        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        Log.d(TAG, "Launching purchase flow for gas.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "speechessentialspayload";

        mHelper.launchPurchaseFlow(this, soundSKU, RC_REQUEST, mPurchaseFinishedListener, payload);
    }

    void complain(String message) {
        Log.e(TAG, "**** SpeechEssentials Error: " + message);
        alert("Error: " + message);
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goToMenu();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void goToMenu(){
        Intent myIntent = new Intent(StoreActivity.this, MenuActivity.class);
        StoreActivity.this.startActivity(myIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null) return;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    /** Verifies the developer payload of a purchase. */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */

        return true;
    }

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
                complain("Error purchasing: " + result);
                setWaitScreen(false);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("Error purchasing. Authenticity verification failed.");
                setWaitScreen(false);
                return;
            }

            Log.d(TAG, "Purchase successful.");

            if (purchase.getSku().equals(SKU_SOUND_M)) {
                // bought 1/4 tank of gas. So consume it.
                Log.d(TAG, "Purchase is gas. Starting gas consumption.");
                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            }
            else if (purchase.getSku().equals(SKU_SOUND_ALL)) {
                // bought the premium upgrade!
                Log.d(TAG, "Purchase is premium upgrade. Congratulating user.");
                alert("Thank you for upgrading to premium!");
                mPurchasedAllSounds = true;
                updateUi();
                setWaitScreen(false);
            }
        }
    };

    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            // We know this is the "gas" sku because it's the only one we consume,
            // so we don't check which sku was consumed. If you have more than one
            // sku, you probably should check...
            if (result.isSuccess()) {
                // successfully consumed, so we apply the effects of the item in our
                // game world's logic, which in our case means filling the gas tank a bit
                Log.d(TAG, "Consumption successful. Provisioning.");
                //mTank = mTank == TANK_MAX ? TANK_MAX : mTank + 1;
                //saveData();
                alert("You bought a sound!");
            }
            else {
                complain("Error while consuming: " + result);
            }
            updateUi();
            setWaitScreen(false);
            Log.d(TAG, "End consumption flow.");
        }
    };

    // Drive button clicked. Burn gas!
    public void onDriveButtonClicked(View arg0) {
//        Log.d(TAG, "Drive button clicked.");
//        if (!mSubscribedToInfiniteGas && mTank <= 0) alert("Oh, no! You are out of gas! Try buying some!");
//        else {
//            if (!mSubscribedToInfiniteGas) --mTank;
//            saveData();
//            alert("Vroooom, you drove a few miles.");
//            updateUi();
//            Log.d(TAG, "Vrooom. Tank is now " + mTank);
//        }
    }

    // updates UI to reflect model
    public void updateUi() {
//        // update the car color to reflect premium status or lack thereof
//        ((ImageView)findViewById(R.id.free_or_premium)).setImageResource(mIsPremium ? R.drawable.premium : R.drawable.free);
//
//        // "Upgrade" button is only visible if the user is not premium
//        findViewById(R.id.upgrade_button).setVisibility(mIsPremium ? View.GONE : View.VISIBLE);
//
//        // "Get infinite gas" button is only visible if the user is not subscribed yet
//        findViewById(R.id.infinite_gas_button).setVisibility(mSubscribedToInfiniteGas ?
//                View.GONE : View.VISIBLE);
//
//        // update gas gauge to reflect tank status
//        if (mSubscribedToInfiniteGas) {
//            ((ImageView)findViewById(R.id.gas_gauge)).setImageResource(R.drawable.gas_inf);
//        }
//        else {
//            int index = mTank >= TANK_RES_IDS.length ? TANK_RES_IDS.length - 1 : mTank;
//            ((ImageView)findViewById(R.id.gas_gauge)).setImageResource(TANK_RES_IDS[index]);
//        }
    }

    // Enables or disables the "please wait" screen.
    void setWaitScreen(boolean set) {
        //findViewById(R.id.screen_main).setVisibility(set ? View.GONE : View.VISIBLE);
        //findViewById(R.id.screen_wait).setVisibility(set ? View.VISIBLE : View.GONE);
    }

    // We're being destroyed. It's important to dispose of the helper here!
    @Override
    public void onDestroy() {
        super.onDestroy();

        // very important:
        Log.d(TAG, "Destroying helper.");
        if (mHelper != null) {
            mHelper.dispose();
            mHelper = null;
        }
    }



    public void initiateVariables(){
        _soundHelper = new SoundHelper();
        typeFaceAnja = Typeface.createFromAsset(getAssets(), "font/anja.ttf");
        View v1 = (View) findViewById(R.id.v1);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMenu();
            }
        });
        View v2 = (View) findViewById(R.id.v2);
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMenu();
            }
        });
        View v3 = (View) findViewById(R.id.v3);
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMenu();
            }
        });
        View v4 = (View) findViewById(R.id.v4);
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMenu();
            }
        });
        storeTextViewHashMap = new HashMap<>();
        LinearLayout llBuySound = (LinearLayout) findViewById(R.id.llBuySound);
        LinearLayout llBuyAllSounds = (LinearLayout) findViewById(R.id.llBuyAllSounds);
        llBuySound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purchaseASound("sound_ " + _soundName);
            }
        });
        llBuyAllSounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purchaseASound("sound_all");
            }
        });
        tvStoreSoundDescription = (TextView) findViewById(R.id.tvSoundDescription);
        tvStoreSoundName = (TextView) findViewById(R.id.tvSoundName);
        tvStoreSoundName.setTypeface(typeFaceAnja);
        tvStoreSoundName.setText("The " + _soundName.toUpperCase() + " Sound");
        tvStoreSoundDescription.setText(_soundHelper.getSoundPurchaseDescription(_soundName));
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        TextView tvSoundName = (TextView) findViewById(R.id.tvSoundName);
        tvTitle.setTypeface(typeFaceAnja);
        tvSoundName.setTypeface(typeFaceAnja);
        HorizontalScrollView hsvSounds = (HorizontalScrollView) findViewById(R.id.hsvSounds);
        TextView tvB = (TextView) findViewById(R.id.tvB);
        TextView tvP = (TextView) findViewById(R.id.tvP);
        TextView tvM = (TextView) findViewById(R.id.tvM);
        TextView tvN = (TextView) findViewById(R.id.tvN);
        TextView tvK = (TextView) findViewById(R.id.tvK);
        TextView tvG = (TextView) findViewById(R.id.tvG);
        TextView tvH = (TextView) findViewById(R.id.tvH);
        TextView tvW = (TextView) findViewById(R.id.tvW);
        TextView tvT = (TextView) findViewById(R.id.tvT);
        TextView tvD = (TextView) findViewById(R.id.tvD);
        TextView tvF = (TextView) findViewById(R.id.tvF);
        TextView tvV = (TextView) findViewById(R.id.tvV);
        TextView tvJ = (TextView) findViewById(R.id.tvJ);
        TextView tvCH = (TextView) findViewById(R.id.tvCH);
        TextView tvY = (TextView) findViewById(R.id.tvY);
        TextView tvNG = (TextView) findViewById(R.id.tvNG);
        TextView tvS = (TextView) findViewById(R.id.tvS);
        TextView tvZ = (TextView) findViewById(R.id.tvZ);
        TextView tvL = (TextView) findViewById(R.id.tvL);
        TextView tvR = (TextView) findViewById(R.id.tvR);
        TextView tvSH = (TextView) findViewById(R.id.tvSH);
        TextView tvTH = (TextView) findViewById(R.id.tvTH);
        TextView tvZH = (TextView) findViewById(R.id.tvZH);
        TextView tvUnselectedB = (TextView) findViewById(R.id.tvUnselectedB);
        TextView tvUnselectedP = (TextView) findViewById(R.id.tvUnselectedP);
        TextView tvUnselectedM = (TextView) findViewById(R.id.tvUnselectedM);
        TextView tvUnselectedN = (TextView) findViewById(R.id.tvUnselectedN);
        TextView tvUnselectedK = (TextView) findViewById(R.id.tvUnselectedK);
        TextView tvUnselectedG = (TextView) findViewById(R.id.tvUnselectedG);
        TextView tvUnselectedH = (TextView) findViewById(R.id.tvUnselectedH);
        TextView tvUnselectedW = (TextView) findViewById(R.id.tvUnselectedW);
        TextView tvUnselectedT = (TextView) findViewById(R.id.tvUnselectedT);
        TextView tvUnselectedD = (TextView) findViewById(R.id.tvUnselectedD);
        TextView tvUnselectedF = (TextView) findViewById(R.id.tvUnselectedF);
        TextView tvUnselectedV = (TextView) findViewById(R.id.tvUnselectedV);
        TextView tvUnselectedJ = (TextView) findViewById(R.id.tvUnselectedJ);
        TextView tvUnselectedCH = (TextView) findViewById(R.id.tvUnselectedCH);
        TextView tvUnselectedY = (TextView) findViewById(R.id.tvUnselectedY);
        TextView tvUnselectedNG = (TextView) findViewById(R.id.tvUnselectedNG);
        TextView tvUnselectedS = (TextView) findViewById(R.id.tvUnselectedS);
        TextView tvUnselectedZ = (TextView) findViewById(R.id.tvUnselectedZ);
        TextView tvUnselectedL = (TextView) findViewById(R.id.tvUnselectedL);
        TextView tvUnselectedR = (TextView) findViewById(R.id.tvUnselectedR);
        TextView tvUnselectedSH = (TextView) findViewById(R.id.tvUnselectedSH);
        TextView tvUnselectedTH = (TextView) findViewById(R.id.tvUnselectedTH);
        TextView tvUnselectedZH = (TextView) findViewById(R.id.tvUnselectedZH);
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
        tvJ.setTypeface(typeFaceAnja);
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
        tvUnselectedJ.setTypeface(typeFaceAnja);
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
        TextView selectedTextView = null;
        TextView unselectedTextView = null;
        switch (_soundName){
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
        selectedTextView.setVisibility(View.VISIBLE);
        unselectedTextView.setVisibility(View.GONE);
        final TextView selectedTV = selectedTextView;
        tvCurrentStoreSelected = selectedTextView;
        tvCurrentStoreUnselected = unselectedTextView;
        final HorizontalScrollView horizontalScrollView = hsvSounds;
        horizontalScrollTo(selectedTV, horizontalScrollView);
    }

    public void setStoreTextViewVisibility(TextView textView){
        _soundName = textView.getText().toString();
        tvStoreSoundName.setText("The " + textView.getText().toString().toUpperCase() + " Sound");
        tvStoreSoundDescription.setText(_soundHelper.getSoundPurchaseDescription(textView.getText().toString()));
        tvCurrentStoreSelected.setVisibility(View.GONE);
        tvCurrentStoreUnselected.setVisibility(View.VISIBLE);
        textView.setVisibility(View.GONE);
        TextView selected = storeTextViewHashMap.get(textView);
        selected.setVisibility(View.VISIBLE);
        tvCurrentStoreUnselected = textView;
        tvCurrentStoreSelected = selected;
    }

    public void horizontalScrollTo(TextView selectedTextView, HorizontalScrollView scrollView){
        int vLeft = selectedTextView.getLeft();
        int vRight = selectedTextView.getRight();
        int sWidth = scrollView.getWidth();
        scrollView.scrollTo((((vLeft + vRight - sWidth) / 2)), 0);
    }

}
