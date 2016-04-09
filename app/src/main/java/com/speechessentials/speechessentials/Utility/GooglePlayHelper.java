package com.speechessentials.speechessentials.Utility;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.android.vending.billing.IInAppBillingService;
import com.speechessentials.speechessentials.MenuActivity;
import com.speechessentials.speechessentials.SplashActivity;
import com.speechessentials.speechessentials.util.IabHelper;
import com.speechessentials.speechessentials.util.IabResult;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by josephdsmithjr on 5/30/2015.
 */
public class GooglePlayHelper {
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
    MenuActivity _menuActivity;
    SplashActivity _splashActivity;

    static final int RC_REQUEST = 10001;
    String base64EncodedPublicKey;
    IInAppBillingService mService;

    ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
            if(_menuActivity != null){
                _menuActivity.setmServiceAndPurchaseSound(mService);
            }
            if(_splashActivity != null){
                _splashActivity.getListOfPurchasesAndExecuteGoDaddy(mService);
            }
        }
    };

    public String getBeginningBit(){
        return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAooHqfTuIkv8VuVrSGMfilQRJA6ShumDNmz0Ac0eQEQ8tNQ4FUBNWG3BRFOV0X2XNahygmhej";
    }

    public String getMiddleBaseBit(){
        return "SmE6zmgM7iQLWakOooEsJd699Kywwf35RIzNj/XjIT7pDY3I7w08RBd8XKPSc27pYa4KKqyUifb1zu7P/m80GOXbnrbQGMv7kmy48n7YxGBMKBc5Zw2U";
    }

    public String getEndingBaseBit() {
        return "h381YTetAuI9LInbS1lIenH1aoGACKXPt2PuCNsNsH30Uci76qAdWg/DWIa3vsWMBB3MGE02PX5zm35htJr/mLp1Uj6tokgVLEkuOBB4hz1Wm6rvyWF5nugaKwWB1AoqJL8+GE0LJjpx2OiFDX1ZdC4f+wIDAQAB";
    }

    public IabHelper getmHelper(Context context){
        base64EncodedPublicKey = getBeginningBit() + getMiddleBaseBit() + getEndingBaseBit();
        IabHelper mHelper = new IabHelper(context, base64EncodedPublicKey);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.d(TAG, "Problem setting up In-app Billing: " + result);
                }
                // Hooray, IAB is fully set up!
            }
        });
        return mHelper;
    }

    public ServiceConnection createIInAppBillingServiceConnectionForSplash(Context context, SplashActivity splashActivity){
        _splashActivity = splashActivity;
        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        context.bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
        return mServiceConn;
    }

    public ServiceConnection createIInAppBillingServiceConnection(Context context, MenuActivity menuActivity){
        _menuActivity = menuActivity;
        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        context.bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
        return mServiceConn;
    }
    
    public HashMap<String, String> getMapOfProductAndPrices(Context context) throws Exception{
        HashMap<String, String> productAndPricesMap = new HashMap<>();
        ArrayList<String> skuList = new ArrayList<String> ();
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
        Bundle querySkus = new Bundle();
        querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
        Bundle skuDetails = mService.getSkuDetails(3,context.getPackageName(), "inapp", querySkus);
        int response = skuDetails.getInt("RESPONSE_CODE");
        if (response == 0) {
            ArrayList<String> responseList = skuDetails.getStringArrayList("DETAILS_LIST");

            for (String thisResponse : responseList) {
                JSONObject object = new JSONObject(thisResponse);
                String sku = object.getString("productId");
                String price = object.getString("price");
                productAndPricesMap.put(sku, price);
            }
        }
        return  productAndPricesMap;
    }

    public void purchaseASound(Context context, String productSKU, IInAppBillingService mService) throws Exception {
        //The response will go to the onActivityResult method in the Activity that called this method
        Bundle buyIntentBundle = mService.getBuyIntent(3, context.getPackageName(), productSKU, "inapp", ""); //, "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ"
        PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
        Activity activity = (Activity) context;
        activity.startIntentSenderForResult(pendingIntent.getIntentSender(), 1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
    }

    public ArrayList<String> getListOfPurchasedSounds(Context context) throws Exception {
        ArrayList<String> listOfPurchasedSounds = new ArrayList<>();
        Bundle ownedItems = mService.getPurchases(3, context.getPackageName(), "inapp", null);
        int response = ownedItems.getInt("RESPONSE_CODE");
        if (response == 0) {
            ArrayList<String> ownedSkus = ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
            ArrayList<String>  purchaseDataList = ownedItems.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
            ArrayList<String>  signatureList = ownedItems.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
            String continuationToken = ownedItems.getString("INAPP_CONTINUATION_TOKEN");
            for (int i = 0; i < purchaseDataList.size(); ++i) {
                String purchaseData = purchaseDataList.get(i);
                String signature = signatureList.get(i);
                String sku = ownedSkus.get(i);
                //String sound = sku.replace("sound_","");
                listOfPurchasedSounds.add(sku);
                // do something with this purchase information
                // e.g. display the updated list of products owned by user
            }
            // if continuationToken != null, call getPurchases again
            // and pass in the token to retrieve more items
        }
        return listOfPurchasedSounds;
    }

//    public ArrayList<String> getListOfPurchasedSounds(Context context, IInAppBillingService mService) throws Exception{
//        ArrayList<String> skuList = new ArrayList<String> ();
//        skuList.add(SKU_SOUND_B);
//        skuList.add(SKU_SOUND_P);
//        skuList.add(SKU_SOUND_M);
//        skuList.add(SKU_SOUND_N);
//        skuList.add(SKU_SOUND_K);
//        skuList.add(SKU_SOUND_G);
//        skuList.add(SKU_SOUND_H);
//        skuList.add(SKU_SOUND_W);
//        skuList.add(SKU_SOUND_T);
//        skuList.add(SKU_SOUND_D);
//        skuList.add(SKU_SOUND_F);
//        skuList.add(SKU_SOUND_V);
//        skuList.add(SKU_SOUND_J);
//        skuList.add(SKU_SOUND_CH);
//        skuList.add(SKU_SOUND_Y);
//        skuList.add(SKU_SOUND_NG);
//        skuList.add(SKU_SOUND_S);
//        skuList.add(SKU_SOUND_Z);
//        skuList.add(SKU_SOUND_L);
//        skuList.add(SKU_SOUND_R);
//        skuList.add(SKU_SOUND_SH);
//        skuList.add(SKU_SOUND_TH);
//        skuList.add(SKU_SOUND_ZH);
//        skuList.add(SKU_SOUND_ALL);
//        Bundle querySkus = new Bundle();
//        querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
//        Bundle skuDetails = mService.getSkuDetails(3,context.getPackageName(), "inapp", querySkus);
//
//        int response = skuDetails.getInt("RESPONSE_CODE");
//        if (response == 0) {
//            ArrayList<String> responseList = skuDetails.getStringArrayList("DETAILS_LIST");
//
//            for (String thisResponse : responseList) {
//                JSONObject object = new JSONObject(thisResponse);
//                String sku = object.getString("productId");
//                String price = object.getString("price");
//                if (sku.equals("premiumUpgrade")) mPremiumUpgradePrice = price;
//                else if (sku.equals("gas")) mGasPrice = price;
//            }
//        }
//        return skuList;
//    }
}
