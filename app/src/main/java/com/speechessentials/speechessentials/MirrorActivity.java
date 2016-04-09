package com.speechessentials.speechessentials;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.speechessentials.speechessentials.Utility.SettingsHelper;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by josephdsmithjr on 6/11/2015.
 */
public class MirrorActivity extends Activity implements Camera.PictureCallback {
    private final static String DEBUG_TAG = "MirrorActivity";
    private Camera mCam;
    private MirrorView mCamPreview;
    private int mCameraId = 0;
    private FrameLayout mPreviewLayout;
    String _soundName;
    RelativeLayout _rlCamera;
    Typeface typeFaceAnja;
    boolean _settingsIsVoiceOn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        // do we have a camera?
        _soundName = getIntent().getStringExtra("IMAGENAME");
        _rlCamera = (RelativeLayout) findViewById(R.id.rlCamera);
        typeFaceAnja = Typeface.createFromAsset(getAssets(), "font/anja.ttf");
        SettingsHelper settingsHelper = new SettingsHelper();
        _settingsIsVoiceOn = settingsHelper.getSettingsIsVoiceOn(this);
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(this, "No camera feature on this device", Toast.LENGTH_LONG).show();
        } else {
            mCameraId = findFirstFrontFacingCamera();
            if (mCameraId >= 0) {
                mPreviewLayout = (FrameLayout) findViewById(R.id.camPreview);
                mPreviewLayout.removeAllViews();
                startCameraInLayout(mPreviewLayout, mCameraId);
                TextView tvSoundImageName = (TextView) findViewById(R.id.tvSoundImageName);
                tvSoundImageName.setText(_soundName);
                tvSoundImageName.setTypeface(typeFaceAnja);
                setAudio(tvSoundImageName);
                Button exit = (Button) findViewById(R.id.exit);
                exit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        finish();
                    }
                });
            } else {
                Toast.makeText(this, "No front facing camera found.", Toast.LENGTH_LONG).show();
            }
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
        tracker.setScreenName("Mirror Activity");
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

    public void setAudio(TextView textView){
        if (_soundName != "") {
            try {
                ContextWrapper cw = new ContextWrapper(MirrorActivity.this);
                File directory = cw.getDir("audioDir", Context.MODE_PRIVATE);
//                            String path = directory.getPath() + "/" + _imageDrawableList.get(_imageListPosition) + ".mp3";
                File mypath = new File(directory.getPath() + "/",_soundName.toLowerCase().replace(" ","").replace("-","").replace("(", "").replace(")","") + ".mp3");
                final MediaPlayer mp = MediaPlayer.create(MirrorActivity.this, Uri.fromFile(mypath));
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(_settingsIsVoiceOn) {
                            if (mp != null) {
                                mp.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
                                mp.start();
                            } else {
                                Toast.makeText(getApplicationContext(), "Sound does not exist for word: " + _soundName, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int findFirstFrontFacingCamera() {
        int foundId = -1;
        // find the first front facing camera
        int numCams = Camera.getNumberOfCameras();
        for (int camId = 0; camId < numCams; camId++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(camId, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                Log.d(DEBUG_TAG, "Found front facing camera");
                foundId = camId;
                break;
            }
        }
        return foundId;
    }

    private void startCameraInLayout(FrameLayout layout, int cameraId) {
        // TODO pull this out of the UI thread.
        mCam = Camera.open(cameraId);
        if (mCam != null) {
            mCamPreview = new MirrorView(this, mCam);
            layout.addView(mCamPreview);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCam == null && mPreviewLayout != null) {
            mPreviewLayout.removeAllViews();
            startCameraInLayout(mPreviewLayout, mCameraId);
        }
    }

    @Override
    protected void onPause() {
        if (mCam != null) {
            mCam.release();
            mCam = null;
        }
        super.onPause();
    }

    public void onPictureTaken(byte[] data, Camera camera) {
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "SimpleSelfCam");
        if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
            Log.d(DEBUG_TAG, "Can't create directory to save image");
            Toast.makeText(this, "Can't make path to save pic.", Toast.LENGTH_LONG).show();
            return;
        }
        String filename = pictureFileDir.getPath() + File.separator + "latest_mug.jpg";
        File pictureFile = new File(filename);
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();
            Toast.makeText(this, "Image saved as latest_mug.jpg", Toast.LENGTH_LONG).show();
        } catch (Exception error) {
            Log.d(DEBUG_TAG, "File not saved: " + error.getMessage());
            Toast.makeText(this, "Can't save image.", Toast.LENGTH_LONG).show();
        }
    }

    public class MirrorView extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder mHolder;
        private Camera mCamera;

        public MirrorView(Context context, Camera camera) {
            super(context);
            mCamera = camera;
            mHolder = getHolder();
            mHolder.addCallback(this);
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        public void surfaceCreated(SurfaceHolder holder) {
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (Exception error) {
                Log.d(DEBUG_TAG, "Error starting mPreviewLayout: " + error.getMessage());
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            if (mHolder.getSurface() == null) {
                return;
            }

            // can't make changes while mPreviewLayout is active
            try {
                mCamera.stopPreview();
            } catch (Exception e) {

            }
            try {
                // set rotation to match device orientation
                setCameraDisplayOrientationAndSize();
                // start up the mPreviewLayout
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();
            } catch (Exception error) {
                Log.d(DEBUG_TAG, "Error starting mPreviewLayout: " + error.getMessage());
            }
        }

        public void setCameraDisplayOrientationAndSize() {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(mCameraId, info);
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            int degrees = rotation * 90;
            /*
             * // the above is just a shorter way of doing this, but could break
             * if the values change switch (rotation) { case Surface.ROTATION_0:
             * degrees = 0; break; case Surface.ROTATION_90: degrees = 90;
             * break; case Surface.ROTATION_180: degrees = 180; break; case
             * Surface.ROTATION_270: degrees = 270; break; }
             */
            int result;
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                result = (info.orientation + degrees) % 360;
                result = (360 - result) % 360;
            } else {
                result = (info.orientation - degrees + 360) % 360;
            }
            mCamera.setDisplayOrientation(result);
            Camera.Size previewSize = mCam.getParameters().getPreviewSize();
            if (result == 90 || result == 270) {
                // swap - the physical camera itself doesn't rotate in relation
                // to the screen ;)
                mHolder.setFixedSize(_rlCamera.getHeight(), _rlCamera.getWidth());
            } else {
                mHolder.setFixedSize(_rlCamera.getWidth(), _rlCamera.getHeight());
            }
        }

    }
}