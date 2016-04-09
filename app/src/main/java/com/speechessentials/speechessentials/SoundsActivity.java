package com.speechessentials.speechessentials;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.speechessentials.speechessentials.Utility.LayoutHelper;


public class SoundsActivity extends Activity {

    View _decorView;
    TextView tvB, tvP, tvM, tvN, tvK, tvG, tvH, tvW, tvT, tvD, tvF, tvV, tvJ, tvCH, tvY, tvNG, tvS, tvZ, tvL, tvR, tvSH, tvTH, tvZH;
    Typeface _typeFaceAnjaBold, _typeFaceAnja;
    LinearLayout _parentLinearLayout;
    ScrollView _scrollView;
    ProgressBar _progressBar;
    int _uiOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setContentView(R.layout.activity_testing);
        initializeVariables();
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
        tracker.setScreenName("Sounds Acit");
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

    public void initializeVariables(){
        _typeFaceAnjaBold = Typeface.createFromAsset(getAssets(), "font/anja.ttf");
        _typeFaceAnja = Typeface.createFromAsset(getAssets(), "font/anja.ttf");
        _parentLinearLayout = (LinearLayout) findViewById(R.id.llParentLayout);
        _scrollView = (ScrollView) findViewById(R.id.svScrollView);
        _progressBar = (ProgressBar) findViewById(R.id.pbSpinner);
        tvB = (TextView) findViewById(R.id.tvB);
        tvP = (TextView) findViewById(R.id.tvP);
        tvM = (TextView) findViewById(R.id.tvM);
        tvN = (TextView) findViewById(R.id.tvN);
        tvK = (TextView) findViewById(R.id.tvK);
        tvG = (TextView) findViewById(R.id.tvG);
        tvH = (TextView) findViewById(R.id.tvH);
        tvW = (TextView) findViewById(R.id.tvW);
        tvT = (TextView) findViewById(R.id.tvT);
        tvD = (TextView) findViewById(R.id.tvD);
        tvF = (TextView) findViewById(R.id.tvF);
        tvV = (TextView) findViewById(R.id.tvV);
        tvJ = (TextView) findViewById(R.id.tvJ);
        tvCH = (TextView) findViewById(R.id.tvCH);
        tvY = (TextView) findViewById(R.id.tvY);
        tvNG = (TextView) findViewById(R.id.tvNG);
        tvS = (TextView) findViewById(R.id.tvS);
        tvZ = (TextView) findViewById(R.id.tvZ);
        tvL = (TextView) findViewById(R.id.tvL);
        tvR = (TextView) findViewById(R.id.tvR);
        tvSH = (TextView) findViewById(R.id.tvSH);
        tvTH = (TextView) findViewById(R.id.tvTH);
        tvZH = (TextView) findViewById(R.id.tvZH);
        tvB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("b");
            }
        });
        tvP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("p");
            }
        });
        tvM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("m");
            }
        });
        tvN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("n");
            }
        });
        tvK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("k");
            }
        });
        tvG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("g");
            }
        });
        tvH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("h");
            }
        });
        tvW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("w");
            }
        });
        tvT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("t");
            }
        });
        tvD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("d");
            }
        });
        tvF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("f");
            }
        });
        tvV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("v");
            }
        });
        tvJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("j");
            }
        });
        tvCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("ch");
            }
        });
        tvY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("y");
            }
        });
        tvNG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("ng");
            }
        });
        tvS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("s");
            }
        });
        tvZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("z");
            }
        });
        tvL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("l");
            }
        });
        tvR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("r");
            }
        });
        tvSH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("sh");
            }
        });
        tvTH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("th");
            }
        });
        tvZH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSoundList("zh");
            }
        });
        _progressBar.setVisibility(View.VISIBLE);
        _scrollView.setVisibility(View.GONE);
        LayoutHelper layoutHelper = new LayoutHelper();
        layoutHelper.populateSoundImageLayout(this, "b", _parentLinearLayout, _typeFaceAnjaBold, 6, 6, 6);
        _progressBar.setVisibility(View.GONE);
        _scrollView.setVisibility(View.VISIBLE);
    }

    public void populateSoundList(String sound){
        _progressBar.setVisibility(View.VISIBLE);
        _scrollView.setVisibility(View.GONE);
        LayoutHelper layoutHelper = new LayoutHelper();
        layoutHelper.populateSoundImageLayout(this, sound, _parentLinearLayout, _typeFaceAnjaBold, 6, 6, 6);
        _progressBar.setVisibility(View.GONE);
        _scrollView.setVisibility(View.VISIBLE);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exitByBackKey() {
        Intent myIntent = new Intent(SoundsActivity.this, MenuActivity.class);
        SoundsActivity.this.startActivity(myIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
//        if(mHelper != null){
//            mHelper.dispose();
//        }
//        mHelper = null;
//        if(mService != null){
//            unbindService(mServiceConn);
//        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
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
