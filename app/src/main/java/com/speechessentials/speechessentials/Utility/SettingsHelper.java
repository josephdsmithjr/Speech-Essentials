package com.speechessentials.speechessentials.Utility;

import android.content.Context;

/**
 * Created by josephdsmithjr on 6/4/2015.
 */
public class SettingsHelper {

    public boolean getSettingsAreScoringButtonsVisible(Context context){
        DatabaseOperationsHelper _databaseOperationsHelper = new DatabaseOperationsHelper(context);
        boolean settingOn = false;
        if(_databaseOperationsHelper.getSettingValue(_databaseOperationsHelper, "scoring_buttons_visible") == 1){
            settingOn = true;
        }
        return settingOn;
    }

    public boolean getSettingsAreScoringSoundsOn(Context context){
        DatabaseOperationsHelper _databaseOperationsHelper = new DatabaseOperationsHelper(context);
        boolean settingOn = false;
        if(_databaseOperationsHelper.getSettingValue(_databaseOperationsHelper, "scoring_sounds") == 1){
            settingOn = true;
        }
        return settingOn;
    }

    public boolean getSettingsIsVoiceOn(Context context){
        DatabaseOperationsHelper _databaseOperationsHelper = new DatabaseOperationsHelper(context);
        boolean settingOn = false;
        if(_databaseOperationsHelper.getSettingValue(_databaseOperationsHelper, "voice_on") == 1){
            settingOn = true;
        }
        return settingOn;
    }

    public boolean getSettingsIsMirrorFunctionOn(Context context){
        DatabaseOperationsHelper _databaseOperationsHelper = new DatabaseOperationsHelper(context);
        boolean settingOn = false;
        if(_databaseOperationsHelper.getSettingValue(_databaseOperationsHelper, "mirror_function_on") == 1){
            settingOn = true;
        }
        return settingOn;
    }
}
