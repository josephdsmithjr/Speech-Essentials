package com.speechessentials.speechessentials.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.speechessentials.speechessentials.SplashActivity;
import com.speechessentials.speechessentials.Utility.TableDataHelper.*;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by josephdsmithjr on 4/18/15.
 */
public class DatabaseOperationsHelper extends SQLiteOpenHelper {
    public static final int database_version = 1;
    /* Sqlite Data types
        NULL The value is a NULL value.
        INTEGER The value is a signed integer, stored in 1, 2, 3, 4, 6, or 8 bytes depending on the magnitude of the value.
        REAL The value is a floating point value, stored as an 8-byte IEEE floating point number.
        TEXT The value is a text string, stored using the database encoding (UTF-8, UTF-16BE or UTF-16LE).
        BLOB The value is a blob of data, stored exactly as it was input.
     */

    public String CREATE_TABLE_USERS_QUERY = "CREATE TABLE " + TableInfo.TABLE_USERS_NAME +
            "(" + TableInfo.COLUMN_USERS_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TableInfo.COLUMN_USERS_NAME + " TEXT," +
                TableInfo.COLUMN_USERS_EMAIL + " TEXT," +
                TableInfo.COLUMN_USERS_GENDER + " TEXT," +
                TableInfo.COLUMN_USERS_BIRTHDAY + " TEXT"
            + ");";
    
    public String CREATE_TABLE_SCORES_NAME = "CREATE TABLE " + TableInfo.TABLE_SCORES_NAME +
            "(" + TableInfo.COLUMN_SCORES_SCORESID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TableInfo.COLUMN_SCORES_USERNAME + " TEXT," +
                TableInfo.COLUMN_SCORES_SCOREDATE + " TEXT," +
            TableInfo.COLUMN_SCORES_SCORE + " TEXT"
            + ");";

    public String CREATE_TABLE_NOTES_NAME = "CREATE TABLE " + TableInfo.TABLE_NOTES_NAME +
            "(" + TableInfo.COLUMN_NOTES_NOTESID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TableInfo.COLUMN_NOTES_SCOREID + " INTEGER," +
            TableInfo.COLUMN_NOTES_NOTES + " TEXT" +
            ");";

    public String CREATE_TABLE_SOUNDS_NAME = "CREATE TABLE " + TableInfo.TABLE_SOUNDS_NAME +
            "(" + TableInfo.COLUMN_SOUNDS_SOUNDID+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TableInfo.COLUMN_SOUNDS_SOUNDNAME + " TEXT" +
            ");";

    public String CREATE_TABLE_SOUNDNAMES_NAME = "CREATE TABLE " + TableInfo.TABLE_SOUNDNAMES_NAME +
            "(" + TableInfo.COLUMN_SOUNDNAMES_SOUNDNAMEID + " INTEGER PRIMARY KEY," +
            //TableInfo.COLUMN_SOUNDNAMES_SOUNDID + " INTEGER," +
            //TableInfo.COLUMN_SOUNDNAMES_SOUNDPARENTID + " INTEGER," +
            //TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONSID + " INTEGER," +
            TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME + " TEXT," +
            TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONNAME + " TEXT," +
            TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGENAME + " TEXT," +
            TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGEAUDIONAME + " TEXT" +
            ");";

    public String CREATE_TABLE_SOUNDSWAPNAMES_NAME = "CREATE TABLE " + TableInfo.TABLE_SOUNDSWAPNAMES_NAME +
            "(" + TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDSWAPNAMEID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDNAME + " TEXT," +
            TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDPOSITIONNAME + " TEXT," +
            TableInfo.COLUMN_SOUNDSWAPNAMES_BEGINNINGNAME + " TEXT," +
            TableInfo.COLUMN_SOUNDSWAPNAMES_ENDINGNAME + " TEXT," +
            TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDBEGINNINGRESOURCENAME + " TEXT," +
            TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDENDINGRESOURCENAME + " TEXT" +
            ");";

    public String CREATE_TABLE_SOUNDPOSITIONS_NAME = "CREATE TABLE " + TableInfo.TABLE_SOUNDPOSITIONS_NAME +
            "(" + TableInfo.COLUMN_SOUNDPOSITIONS_SOUNDPOSITIONSID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TableInfo.COLUMN_SOUNDPOSITIONS_SOUNDPOSITIONSNAME + " TEXT" +
            ");";

    public String CREATE_TABLE_DIFFSENTENCES_QUERY = "CREATE TABLE " + TableInfo.TABLE_DIFFSENTENCES_NAME +
            "(" + TableInfo.COLUMN_DIFFSENTENCES_DIFFSENTENCESID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TableInfo.COLUMN_DIFFSENTENCES_DIFFSENTENCESNAME+ " TEXT," +
            TableInfo.COLUMN_DIFFSENTENCES_DIFFSENTENCESIMAGEAUDIONAME + " TEXT" +
            ");";

    public String CREATE_TABLE_SETTINGS_QUERY = "CREATE TABLE " + TableInfo.TABLE_SETTINGS_NAME +
            "(" + TableInfo.COLUMN_SETTINGS_SETTINGS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TableInfo.COLUMN_SETTINGS_NAME + " TEXT," +
            TableInfo.COLUMN_SETTINGS_VALUE + " INTEGER" +
            ");";


    public DatabaseOperationsHelper(Context context) {
        //THIS WILL CREATE THE DATABASE
        super(context, TableInfo.DATABASE_NAME, null, database_version);
        Log.d("Database operations", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {
        //CREATE THE TABLES
        sdb.execSQL(CREATE_TABLE_USERS_QUERY);
        Log.d("Database operations", "users table created");
        sdb.execSQL(CREATE_TABLE_SOUNDS_NAME);
        Log.d("Database operations", "sounds table created");
        sdb.execSQL(CREATE_TABLE_SOUNDNAMES_NAME);
        Log.d("Database operations", "soundnames table created");
        sdb.execSQL(CREATE_TABLE_SOUNDSWAPNAMES_NAME);
        Log.d("Database operations", "soundswapnames table created");
        sdb.execSQL(CREATE_TABLE_NOTES_NAME);
        Log.d("Database operations", "notes table created");
        sdb.execSQL(CREATE_TABLE_SOUNDPOSITIONS_NAME);
        Log.d("Database operations", "soundpositions table created");
        sdb.execSQL(CREATE_TABLE_DIFFSENTENCES_QUERY);
        Log.d("Database operations", "diffsentences table created");
        sdb.execSQL(CREATE_TABLE_SETTINGS_QUERY);
        Log.d("Database operations", "settings table created");
        sdb.execSQL(CREATE_TABLE_SCORES_NAME);
        Log.d("Database operations", "scores table created");
    }

    public void refreshTables(DatabaseOperationsHelper dop, Context context, ProgressBar spinner) {
        SQLiteDatabase sdb = dop.getWritableDatabase();
        sdb.execSQL("DROP TABLE IF EXISTS " + TableInfo.TABLE_SOUNDS_NAME);
        sdb.execSQL(CREATE_TABLE_SOUNDS_NAME);
        Log.d("Database operations", "sounds table created");
        sdb.execSQL("DROP TABLE IF EXISTS " + TableInfo.TABLE_SOUNDNAMES_NAME);
        sdb.execSQL(CREATE_TABLE_SOUNDNAMES_NAME);
        Log.d("Database operations", "soundnames table created");
        sdb.execSQL("DROP TABLE IF EXISTS " + TableInfo.TABLE_SOUNDSWAPNAMES_NAME);
        sdb.execSQL(CREATE_TABLE_SOUNDSWAPNAMES_NAME);
        Log.d("Database operations", "soundswapnames table created");
        sdb.execSQL("DROP TABLE IF EXISTS " + TableInfo.TABLE_SOUNDPOSITIONS_NAME);
        sdb.execSQL(CREATE_TABLE_SOUNDPOSITIONS_NAME);
        Log.d("Database operations", "soundpositions table created");
        sdb.execSQL("DROP TABLE IF EXISTS " + TableInfo.TABLE_DIFFSENTENCES_NAME);
        sdb.execSQL(CREATE_TABLE_DIFFSENTENCES_QUERY);
        Log.d("Database operations", "diffsentences table created");
        refreshTheDatabase(context, spinner, (SplashActivity) context);
    }

    public boolean addUser(DatabaseOperationsHelper dop, String username, String email, String gender, String birthday){
        boolean addedNewUser = false;
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableInfo.COLUMN_USERS_NAME, username);
        contentValues.put(TableInfo.COLUMN_USERS_EMAIL, email);
        contentValues.put(TableInfo.COLUMN_USERS_GENDER, gender);
        contentValues.put(TableInfo.COLUMN_USERS_BIRTHDAY, birthday);
        sqLiteDatabase.insert(TableInfo.TABLE_USERS_NAME, null, contentValues);
        Log.d("Database operations", "One row inserted into users table for user: " + username);
        addedNewUser = true;
        return addedNewUser;
    }

    public boolean editUser(DatabaseOperationsHelper dop, int userId, String username, String email, String gender, String birthday){
        //INSERT VALUES INTO USERS TABLE
        boolean addedNewUser = false;
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String checkIfUsersExistQuery = "SELECT * FROM " + TableInfo.TABLE_USERS_NAME + " WHERE " + TableInfo.COLUMN_USERS_USERID + " = " + userId;
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfUsersExistQuery, null);
        if(cursor.getCount() > 0){
            contentValues.put(TableInfo.COLUMN_USERS_NAME, username);
            contentValues.put(TableInfo.COLUMN_USERS_EMAIL, email);
            contentValues.put(TableInfo.COLUMN_USERS_GENDER, gender);
            contentValues.put(TableInfo.COLUMN_USERS_BIRTHDAY, birthday);
            sqLiteDatabase.update(TableInfo.TABLE_USERS_NAME, contentValues, TableInfo.COLUMN_USERS_USERID + " = " + userId, null);
            Log.d("Database operations", "Updated row in users table for user: " + username);
            addedNewUser = true;
        } else {
            addedNewUser = false;
        }
        return addedNewUser;
    }

    public ArrayList<String> getUserInfo(DatabaseOperationsHelper dop, int userId){
        ArrayList<String> userInfoList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String checkIfUsersExistQuery = "SELECT * FROM " + TableInfo.TABLE_USERS_NAME + " WHERE " + TableInfo.COLUMN_USERS_USERID + " = " + userId;
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfUsersExistQuery, null);
        try {
            while (cursor.moveToNext()) {
                userInfoList.add(cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_USERS_NAME)));
                userInfoList.add(cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_USERS_BIRTHDAY)));
                userInfoList.add(cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_USERS_EMAIL)));
                userInfoList.add(cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_USERS_GENDER)));
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return userInfoList;
    }

    public boolean deleteUser(DatabaseOperationsHelper dop, int userId){
        boolean wasDeleted = false;
        try {
            SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
            sqLiteDatabase.execSQL("delete from " + TableInfo.TABLE_USERS_NAME + " where " + TableInfo.COLUMN_USERS_USERID + " = '" + userId + "'");
            //TODO: delete scores (currently we only save the username, not the userId...
            wasDeleted = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return wasDeleted;
    }

    public ArrayList<String> getListOfUsers(DatabaseOperationsHelper dop){
        //INSERT VALUES INTO SOUNDS TABLE
        ArrayList<String> listOfUsers = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_USERS_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfRowExists, null);
        try {
            while (cursor.moveToNext()) {
                listOfUsers.add(cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_USERS_NAME)));
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        Collections.sort(listOfUsers, String.CASE_INSENSITIVE_ORDER);
        return listOfUsers;
    }

    public ArrayList<String> getListOfUsersWithIds(DatabaseOperationsHelper dop){
        //INSERT VALUES INTO SOUNDS TABLE
        ArrayList<String> listOfUsers = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_USERS_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfRowExists, null);
        try {
            while (cursor.moveToNext()) {
                listOfUsers.add(cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_USERS_NAME)) + "USERID" + cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_USERS_USERID)));
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        Collections.sort(listOfUsers, String.CASE_INSENSITIVE_ORDER);
        return listOfUsers;
    }

    public ArrayList<String> getDistinctSoundImageNameList(DatabaseOperationsHelper dop){
        ArrayList<String> distinctSoundImageNameList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //String checkIfUsersExistQuery = "SELECT DISTINCT " + TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGENAME + " FROM " + TableInfo.TABLE_SOUNDS_NAME;
        //Cursor cursor = sqLiteDatabase.rawQuery(checkIfUsersExistQuery, null);
        Cursor cursor = sqLiteDatabase.query(true, TableInfo.TABLE_SOUNDNAMES_NAME, new String[]{TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGEAUDIONAME}, null, null, TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGEAUDIONAME, null, null, null);
        int cursorCount = cursor.getCount();
        while (cursor.moveToNext()) {
            distinctSoundImageNameList.add(cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGEAUDIONAME)));
        }
        return distinctSoundImageNameList;
    }

    public ArrayList<String> getDistinctSoundList(DatabaseOperationsHelper dop, ArrayList<Integer> listOfIds, String gameName){
        ArrayList<String> distinctSoundImageNameList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String inClause = makeInClauseStringFromIntegerList(listOfIds);
        String checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDNAMES_NAME + " WHERE " + TableInfo.COLUMN_SOUNDNAMES_SOUNDNAMEID + " IN (" + inClause + ");";
        if(gameName.equals("swap")){
            checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDSWAPNAMES_NAME + " WHERE " + TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDSWAPNAMEID + " IN (" + inClause + ");";
        }
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfRowExists, null);
        while (cursor.moveToNext()) {
            if(gameName.equals("swap")){
                if(!distinctSoundImageNameList.contains(cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDNAME)))) {
                    distinctSoundImageNameList.add(cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDNAME)));
                }
            } else {
                if(!distinctSoundImageNameList.contains(cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME)))) {
                    distinctSoundImageNameList.add(cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME)));
                }
            }
        }
        return distinctSoundImageNameList;
    }

    public ArrayList<String> getSoundAndSoundNameIdList(DatabaseOperationsHelper dop, ArrayList<Integer> listOfIds, String game){
        ArrayList<String> soundNameIdAndSoundList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String inClause = makeInClauseStringFromIntegerList(listOfIds);
        String checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDNAMES_NAME + " WHERE " + TableInfo.COLUMN_SOUNDNAMES_SOUNDNAMEID + " IN (" + inClause + ");";
        if(game.equals("swap")){
            checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDSWAPNAMES_NAME + " WHERE " + TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDSWAPNAMEID + " IN (" + inClause + ");";
        }
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfRowExists, null);
        while (cursor.moveToNext()) {
            if(game.equals("swap")){
                soundNameIdAndSoundList.add(cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDNAME)) + "," + cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDSWAPNAMEID)));
            } else {
                soundNameIdAndSoundList.add(cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME)) + "," + cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDNAMEID)));
            }
        }
        return soundNameIdAndSoundList;
    }

    public String getSoundPositionNameFromSoundImageId(DatabaseOperationsHelper dop, int soundImageId){
        String soundPositionName = "";
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String checkIfUsersExistQuery = "SELECT * FROM " + TableInfo.TABLE_SOUNDNAMES_NAME + " WHERE " + TableInfo.COLUMN_SOUNDNAMES_SOUNDNAMEID + " = " + soundImageId;
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfUsersExistQuery, null);
        while (cursor.moveToNext()) {
            soundPositionName = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONNAME));
        }
        return soundPositionName;
    }

    public void addSound(DatabaseOperationsHelper dop, String soundName){
        //INSERT VALUES INTO SOUNDS TABLE
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String checkIfUsersExistQuery = "SELECT * FROM " + TableInfo.TABLE_SOUNDS_NAME + " WHERE " + TableInfo.COLUMN_SOUNDS_SOUNDNAME + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfUsersExistQuery, new String[]{soundName});
        int cursorCount = cursor.getCount();
        if(cursorCount <= 0){
            contentValues.put(TableInfo.COLUMN_SOUNDS_SOUNDNAME, soundName);
            sqLiteDatabase.insert(TableInfo.TABLE_SOUNDS_NAME, null, contentValues);
            Log.d("Database operations", "Inserted + " + soundName + " into sounds table");
        }
//        sqLiteDatabase.delete(TableInfo.TABLE_SOUNDS_NAME,null,null);
//        sqLiteDatabase.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TableInfo.TABLE_SOUNDS_NAME + "'");
    }

    public void addSetting(DatabaseOperationsHelper dop, String settingName, int settingValue){
        //INSERT VALUES INTO SOUNDS TABLE
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SETTINGS_NAME +
                " WHERE " +
                TableInfo.COLUMN_SETTINGS_NAME + " = ?"
                ;
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfRowExists, new String[] {settingName});
        int cursorCount = cursor.getCount();
        if(cursorCount <= 0){
            contentValues.put(TableInfo.COLUMN_SETTINGS_NAME, settingName);
            contentValues.put(TableInfo.COLUMN_SETTINGS_VALUE, settingValue);
            sqLiteDatabase.insert(TableInfo.TABLE_SETTINGS_NAME, null, contentValues);
            Log.d("Database operations", "One row inserted into settings table");
            cursor.close();
        } else {
            cursor.close();
        }
    }

    public int getSettingValue(DatabaseOperationsHelper dop, String settingName){
        //INSERT VALUES INTO SOUNDS TABLE
        int settingValue = -1;
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SETTINGS_NAME +
                " WHERE " +
                TableInfo.COLUMN_SETTINGS_NAME + " = ?"
                ;
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfRowExists, new String[]{settingName});
        int cursorCount = cursor.getCount();
        try {
            while (cursor.moveToNext()) {
                settingValue = cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_SETTINGS_VALUE));
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return settingValue;
    }

    public void changeSetting(DatabaseOperationsHelper dop, String settingName, int settingValue){
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SETTINGS_NAME +
                " WHERE " +
                TableInfo.COLUMN_SETTINGS_NAME + " = ?"
                ;
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfRowExists, new String[]{settingName});
        int cursorCount = cursor.getCount();
        if(cursorCount > 0){
            contentValues.put(TableInfo.COLUMN_SETTINGS_VALUE, settingValue);
            sqLiteDatabase.update(TableInfo.TABLE_SETTINGS_NAME, contentValues, TableInfo.COLUMN_SETTINGS_NAME + "='" + settingName + "'", null);
            Log.d("Database operations", "Setting " + settingName + " changed to " + settingValue);
            cursor.close();
        } else {
            cursor.close();
        }
    }

    public void addSoundNameImageAudio(DatabaseOperationsHelper dop, int soundParentId, int soundId, int soundPositionId, String soundName, String soundPositionName, String soundImageName, String soundImageAudioName, String soundAudioPath){
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDNAMES_NAME +
                " WHERE " +
                TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME + " = ? AND " +
                TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONNAME + " = ? AND " +
                TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGENAME + " = ? AND " +
                TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGEAUDIONAME + " = ?"
                ;
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfRowExists, new String[]{soundName, soundPositionName, soundImageName, soundImageAudioName});
        int cursorCount = cursor.getCount();
        if(cursorCount <= 0){
            contentValues.put(TableInfo.COLUMN_SOUNDNAMES_SOUNDID, soundId);
            contentValues.put(TableInfo.COLUMN_SOUNDNAMES_SOUNDPARENTID, soundParentId);
            contentValues.put(TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONSID, soundPositionId);
            contentValues.put(TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME, soundName);
            contentValues.put(TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONNAME, soundPositionName);
            contentValues.put(TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGENAME, soundImageName);
            contentValues.put(TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGEAUDIONAME, soundImageAudioName);
            sqLiteDatabase.insert(TableInfo.TABLE_SOUNDNAMES_NAME, null, contentValues);
            Log.d("Database operations", "One row inserted into soundNames table");
            cursor.close();
        } else {
            cursor.close();
        }
    }

    public void executedInsertQuery(DatabaseOperationsHelper dop, String insertStatement){
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        sqLiteDatabase.execSQL(insertStatement);
    }

    public void addSentenceNameImageAudio(DatabaseOperationsHelper dop, String soundName, String soundPositionName, String soundImageName, String soundImageAudioName){
        //INSERT VALUES INTO SOUNDS TABLE
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDNAMES_NAME +
                " WHERE " +
                TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME + " = ? AND " +
                TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONNAME + " = ? AND " +
                TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGENAME + " = ? AND " +
                TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGEAUDIONAME + " = ?"
                ;
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfRowExists, new String[] {soundName, soundPositionName, soundImageName, soundImageAudioName});
        int cursorCount = cursor.getCount();
        if(cursorCount <= 0){
            int parentSoundId = getParentSoundId(soundName);
            contentValues.put(TableInfo.COLUMN_SOUNDNAMES_SOUNDPARENTID, parentSoundId);
            contentValues.put(TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME, soundName);
            contentValues.put(TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONNAME, soundPositionName);
            contentValues.put(TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGENAME, soundImageName);
            contentValues.put(TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGEAUDIONAME, soundImageAudioName);
            sqLiteDatabase.insert(TableInfo.TABLE_SOUNDNAMES_NAME, null, contentValues);
            Log.d("Database operations", "One row inserted into soundNames table");
            cursor.close();
        } else {
            cursor.close();
        }
    }

    public void addWordSwapResources(DatabaseOperationsHelper dop, String soundName, String soundPositionName, String swapBeginning, String swapEnding, String swapBeginningResourceName, String swapEndingResourceName){
        //INSERT VALUES INTO SOUNDS TABLE
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDSWAPNAMES_NAME +
                " WHERE " +
                TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDNAME + " = ? AND " +
                TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDPOSITIONNAME + " = ? AND " +
                TableInfo.COLUMN_SOUNDSWAPNAMES_BEGINNINGNAME + " = ? AND " +
                TableInfo.COLUMN_SOUNDSWAPNAMES_ENDINGNAME + " = ? AND " +
                TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDBEGINNINGRESOURCENAME + " = ? AND " +
                TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDENDINGRESOURCENAME + " = ?"
                ;
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfRowExists, new String[] {soundName, soundPositionName, swapBeginning, swapEnding, swapBeginningResourceName, swapEndingResourceName});
        int cursorCount = cursor.getCount();
        if(cursorCount <= 0){
            int parentSoundId = getParentSoundId(soundName);
            contentValues.put(TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDNAME, soundName);
            contentValues.put(TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDPOSITIONNAME, soundPositionName);
            contentValues.put(TableInfo.COLUMN_SOUNDSWAPNAMES_BEGINNINGNAME, swapBeginning);
            contentValues.put(TableInfo.COLUMN_SOUNDSWAPNAMES_ENDINGNAME, swapEnding);
            contentValues.put(TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDBEGINNINGRESOURCENAME, swapBeginningResourceName);
            contentValues.put(TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDENDINGRESOURCENAME, swapEndingResourceName);
            sqLiteDatabase.insert(TableInfo.TABLE_SOUNDSWAPNAMES_NAME, null, contentValues);
            Log.d("Database operations", "One row inserted into soundSwapNames table");
            cursor.close();
        } else {
            cursor.close();
        }
    }

    public int getParentSoundId(String soundName){
        int parentSoundId = 0;
        switch (soundName){
            case "ks":
                parentSoundId = 17;
                break;
            case "ts":
                parentSoundId = 17;
                break;
            case "ns":
                parentSoundId = 17;
                break;
            case "ps":
                parentSoundId = 17;
                break;
            case "st":
                parentSoundId = 17;
                break;
            case "sl":
                parentSoundId = 17;
                break;
            case "sc":
                parentSoundId = 17;
                break;
            case "sw":
                parentSoundId = 17;
                break;
            case "sm":
                parentSoundId = 17;
                break;
            case "sn":
                parentSoundId = 17;
                break;
            case "sp":
                parentSoundId = 17;
                break;
            case "tr":
                parentSoundId = 20;
                break;
            case "br":
                parentSoundId = 20;
                break;
            case "dr":
                parentSoundId = 20;
                break;
            case "fr":
                parentSoundId = 20;
                break;
            case "gr":
                parentSoundId = 20;
                break;
            case "cr":
                parentSoundId = 20;
                break;
            case "pr":
                parentSoundId = 20;
                break;
            case "ar":
                parentSoundId = 20;
                break;
            case "or":
                parentSoundId = 20;
                break;
            case "ear":
                parentSoundId = 20;
                break;
            case "air":
                parentSoundId = 20;
                break;
            case "ire":
                parentSoundId = 20;
                break;
            case "er":
                parentSoundId = 20;
                break;
            case "rl":
                parentSoundId = 20;
                break;
            case "pl":
                parentSoundId = 19;
                break;
            case "bl":
                parentSoundId = 19;
                break;
            case "gl":
                parentSoundId = 19;
                break;
            case "fl":
                parentSoundId = 19;
                break;
            case "cl":
                parentSoundId = 19;
                break;
        }
        return parentSoundId;
    }

    public ArrayList<Integer> getListOfImageIds(DatabaseOperationsHelper dop, String soundName, ArrayList<String> soundPositionIdList, int characterLengthMax){
        ArrayList<Integer> listOfImageIds = new ArrayList<>();
        ArrayList<String> listOfImages = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String inClause = makeInClauseStringFromStringList(soundPositionIdList);
        String checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDNAMES_NAME +
                " WHERE " +
                TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME + " = ? AND " +
                TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONNAME + " IN (" + inClause + ");";
        ;
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfRowExists, new String[]{soundName});
        int cursorCount = cursor.getCount();
        try {
            while (cursor.moveToNext()) {
                int imageId = cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDNAMEID));
                String imageWord = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGENAME));
                if(imageWord.length() <= characterLengthMax){
                    listOfImageIds.add(imageId);
                    listOfImages.add(imageWord);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return listOfImageIds;
    }

    public ArrayList<Integer> getListOfImageIds(DatabaseOperationsHelper dop, String soundName, ArrayList<String> soundPositionIdList){
        ArrayList<Integer> listOfImageIds = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String inClause = makeInClauseStringFromStringList(soundPositionIdList);
        String checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDNAMES_NAME +
                " WHERE " +
                TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME + " = ? AND " +
                TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONNAME + " IN (" + inClause + ");";
        ;
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfRowExists, new String[]{soundName});
        int cursorCount = cursor.getCount();
        try {
            while (cursor.moveToNext()) {
                int imageId = cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDNAMEID));
                listOfImageIds.add(imageId);
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return listOfImageIds;
    }

    public ArrayList<Integer> getListOfImageIds(DatabaseOperationsHelper dop, ArrayList<String> soundNameList, ArrayList<String> soundPositionIdList){
        ArrayList<Integer> listOfImageIds = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String checkIfRowExists = "";
        //TODO: BROKEN FOR BLENDS!
        String inClause = makeInClauseStringFromStringList(soundPositionIdList);
        for(int i = 0; i < soundNameList.size(); i++){
            String soundName = soundNameList.get(i);
            String soundPosition = "";
            if(soundName.contains("_")){
                String[] soundNameSplit = soundName.split("_");
                soundName = soundNameSplit[0];
                soundPosition = soundNameSplit[1];
                checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDNAMES_NAME +
                        " WHERE " +
                        TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME + " = '" + soundName + "' AND " +
                        TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONNAME + " = '" + soundPosition + "';";
            } else {
                checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDNAMES_NAME +
                        " WHERE " +
                        TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME + " = '" + soundName + "' AND " +
                        TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONNAME + " IN (" + inClause + ");";
            }
            Cursor cursor = sqLiteDatabase.rawQuery(checkIfRowExists, null);
            try {
                while (cursor.moveToNext()) {
                    int imageId = cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDNAMEID));
                    listOfImageIds.add(imageId);
                }
            } catch(Exception e){
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
        return listOfImageIds;
    }

    public ArrayList<Integer> getListOfWordFindImageIds(DatabaseOperationsHelper dop, ArrayList<String> soundNameList, ArrayList<String> soundPositionIdList){
        ArrayList<Integer> listOfImageIds = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String checkIfRowExists = "";
        String inClause = makeInClauseStringFromStringList(soundPositionIdList);
        for(int i = 0; i < soundNameList.size(); i++){
            String soundName = soundNameList.get(i);
            String soundPosition = "";
            if(soundName.contains("_")){
                String[] soundNameSplit = soundName.split("_");
                soundName = soundNameSplit[0];
                soundPosition = soundNameSplit[1];
                checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDNAMES_NAME +
                        " WHERE " +
                        TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME + " = '" + soundName + "' AND " +
                        TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONNAME + " = '" + soundPosition + "';";
            } else {
                checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDNAMES_NAME +
                        " WHERE " +
                        TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME + " = '" + soundName + "' AND " +
                        TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONNAME + " IN (" + inClause + ");";
            }
            Cursor cursor = sqLiteDatabase.rawQuery(checkIfRowExists, null);
            try {
                while (cursor.moveToNext()) {
                    String imageName = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGENAME));
                    if(imageName.length() <= 6) {
                        int imageId = cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDNAMEID));
                        listOfImageIds.add(imageId);
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
        return listOfImageIds;
    }

    public ArrayList<String> getListOfImages(Context context, DatabaseOperationsHelper dop, ArrayList<Integer> soundNameIdList){
        ArrayList<String> listOfImages = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String inClause = makeInClauseStringFromIntegerList(soundNameIdList);
        String checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDNAMES_NAME +
                " WHERE " + TableInfo.COLUMN_SOUNDNAMES_SOUNDNAMEID + " IN (" + inClause + ");";
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfRowExists, null);
        try {
            while(cursor.moveToNext()){
                String drawablePath = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGEAUDIONAME));
                listOfImages.add(drawablePath);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return listOfImages;
    }

    public ArrayList<String> getListOfImageNames(DatabaseOperationsHelper dop, ArrayList<Integer> soundNameIdList){
        ArrayList<String> listOfImageNames = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String inClause = makeInClauseStringFromIntegerList(soundNameIdList);
        String checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDNAMES_NAME +
                " WHERE " + TableInfo.COLUMN_SOUNDNAMES_SOUNDNAMEID + " IN (" + inClause + ");";
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfRowExists, null);
        int cursorSize = cursor.getCount();
        try {
            while(cursor.moveToNext()){
                String imageName = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGENAME));
                listOfImageNames.add(imageName);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return listOfImageNames;
    }

    public String getImageNameFromId(DatabaseOperationsHelper dop, int soundNameId){
        String imageName = "";
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDNAMES_NAME +
                " WHERE " + TableInfo.COLUMN_SOUNDNAMES_SOUNDNAMEID + " = " + soundNameId;
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfRowExists, null);
        int cursorSize = cursor.getCount();
        try {
            while(cursor.moveToNext()){
                imageName = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGENAME));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return imageName;
    }

    public ArrayList<String> getListOfWordTypes(DatabaseOperationsHelper dop, ArrayList<Integer> soundNameIdList){
        ArrayList<String> listOfWordTypes = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String inClause = makeInClauseStringFromIntegerList(soundNameIdList);
        String checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDNAMES_NAME +
                " WHERE " + TableInfo.COLUMN_SOUNDNAMES_SOUNDNAMEID + " IN (" + inClause + ");";
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfRowExists, null);
        int cursorSize = cursor.getCount();
        try {
            while(cursor.moveToNext()){
                String wordTypeName = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME)) + " - " +
                        cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONNAME));
                listOfWordTypes.add(wordTypeName);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return listOfWordTypes;
    }

    public ArrayList<Integer> getListOfImageAudioPaths(Context context, DatabaseOperationsHelper dop, ArrayList<Integer> soundNameIdList){
        ArrayList<Integer> listOfImageAudioPaths = new ArrayList<>();
        ArrayList<String> listOfMissingImages = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String inClause = makeInClauseStringFromIntegerList(soundNameIdList);
        String checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDNAMES_NAME +
                " WHERE " + TableInfo.COLUMN_SOUNDNAMES_SOUNDNAMEID + " IN (" + inClause + ");";
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfRowExists, null);
        int cursorSize = cursor.getCount();
        try {
            while(cursor.moveToNext()){
                String drawablePath = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGEAUDIONAME));
                int drawable = context.getResources().getIdentifier(drawablePath, "raw", context.getPackageName());
                if(drawable == 0){
                    listOfMissingImages.add(drawablePath);
                    Log.d("Database Operations", "Missing raw file for word: " + drawablePath);
                }
                listOfImageAudioPaths.add(drawable);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        int numberOfMissingImages = listOfMissingImages.size();
        return listOfImageAudioPaths;
    }

    public ArrayList<String> getWordSwapTextList(Context context, DatabaseOperationsHelper dop, String soundName, ArrayList<String> wordTypeList, int positionNumber){
        ArrayList<String> listOfSentenceTexts = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String checkIfRowExists = "";
        Cursor cursor = null;
        for(int i = 0; i < wordTypeList.size(); i++){
            checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDSWAPNAMES_NAME +
                    " WHERE " + TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDNAME + " = ? AND " + TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDPOSITIONNAME + " = ?;";
            String[] wordTypeParts = wordTypeList.get(i).split(" - ");
            String wordType = "swap " + wordTypeParts[1];
            cursor = sqLiteDatabase.rawQuery(checkIfRowExists, new String[]{soundName, wordType});
            int cursorSize = cursor.getCount();
            cursor.moveToFirst();
            try {
                String text = "";
                if(positionNumber == 1){
                    text = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDSWAPNAMES_BEGINNINGNAME));
                } else {
                    text = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDSWAPNAMES_ENDINGNAME));
                }
                listOfSentenceTexts.add(text);
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
        return listOfSentenceTexts;
    }

    public ArrayList<String> getWordSwapResourceList(Context context, DatabaseOperationsHelper dop, String soundName, ArrayList<String> wordTypeList, int positionNumber){
        ArrayList<String> listOfSentenceTexts = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String checkIfRowExists = "";
        Cursor cursor = null;
        for(int i = 0; i < wordTypeList.size(); i++){
            checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDSWAPNAMES_NAME +
                    " WHERE " + TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDNAME + " = ? AND " + TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDPOSITIONNAME + " = ?;";
            String[] wordTypeParts = wordTypeList.get(i).split(" - ");
            String wordType = "swap " + wordTypeParts[1];
            cursor = sqLiteDatabase.rawQuery(checkIfRowExists, new String[]{soundName, wordType});
            cursor.moveToFirst();
            int cursorSize = cursor.getCount();
            try {
                String text = "";
                if(positionNumber == 1){
                    text = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDBEGINNINGRESOURCENAME));
                } else {
                    text = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDENDINGRESOURCENAME));
                }
                listOfSentenceTexts.add(text);
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
        return listOfSentenceTexts;
    }

    public ArrayList<String> getWordSwapAudioPathList(DatabaseOperationsHelper dop, String soundName, ArrayList<String> wordTypeList, int positionNumber){
        ArrayList<String> listOfSentenceTexts = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String checkIfRowExists = "";
        Cursor cursor = null;
        for(int i = 0; i < wordTypeList.size(); i++){
            checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDSWAPNAMES_NAME +
                    " WHERE " + TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDNAME + " = ? AND " + TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDPOSITIONNAME + " = ?;";
            String[] wordTypeParts = wordTypeList.get(i).split(" - ");
            String wordType = "swap " + wordTypeParts[1];
            cursor = sqLiteDatabase.rawQuery(checkIfRowExists, new String[]{soundName, wordType});
            cursor.moveToFirst();
            int cursorSize = cursor.getCount();
            try {
                String soundPosition = "";
                soundPosition = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDPOSITIONNAME));
                //swap_airfr1
                String audioPath = "swap_" + soundName;
                switch (soundPosition){
                    case "swap beginning":
                        audioPath += "ir";
                        break;
                    case "swap middle":
                        audioPath += "mr";
                        break;
                    case "swap ending":
                        audioPath += "fr";
                        break;
                }
                if(positionNumber == 1){
                    audioPath += 1;
                } else {
                    audioPath += 2;
                }
                listOfSentenceTexts.add(audioPath);
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
        return listOfSentenceTexts;
    }

    public ArrayList<String> getSentenceText(Context context, DatabaseOperationsHelper dop, ArrayList<Integer> soundNameIdList, int positionNumber){
        ArrayList<String> listOfSentenceTexts = new ArrayList<>();
        ArrayList<String> listOfSoundNames = new ArrayList<>();
        ArrayList<String> listOfSoundPositions = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String checkIfRowExists = "";
        Cursor cursor = null;
        for(int i = 0; i < soundNameIdList.size(); i++){
            checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDNAMES_NAME +
                    " WHERE " + TableInfo.COLUMN_SOUNDNAMES_SOUNDNAMEID + " = " + soundNameIdList.get(i) + ";";
            cursor = sqLiteDatabase.rawQuery(checkIfRowExists, null);
            int cursorSize = cursor.getCount();
            try {
                while(cursor.moveToNext()){
                    String soundName = "";
                    String soundPosition = "";
                    soundName = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME));
                    soundPosition = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONNAME));
                    listOfSoundNames.add(soundName);
                    listOfSoundPositions.add(soundPosition);
                }
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
        for(int i = 0; i < listOfSoundNames.size(); i++){
            checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDSWAPNAMES_NAME +
                    " WHERE " + TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDNAME + " = ? AND " + TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDPOSITIONNAME + " = ?;";
            cursor = sqLiteDatabase.rawQuery(checkIfRowExists, new String[]{listOfSoundNames.get(i), listOfSoundPositions.get(i)});
            int cursorSize = cursor.getCount();
            try {
                while(cursor.moveToNext()){
                    String text = "";
                    if(positionNumber == 1){
                        text = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDSWAPNAMES_BEGINNINGNAME));
                    } else {
                        text = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDSWAPNAMES_ENDINGNAME));
                    }
                    listOfSentenceTexts.add(text);
                }
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
        return listOfSentenceTexts;
    }

    public ArrayList<String> getSentenceAudioPath(DatabaseOperationsHelper dop, ArrayList<Integer> soundNameIdList, int positionNumber){
        ArrayList<String> listOfSentenceAudioPaths = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String checkIfRowExists = "";
        Cursor cursor = null;
        for(int i = 0; i < soundNameIdList.size(); i++){
            checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDNAMES_NAME +
                    " WHERE " + TableInfo.COLUMN_SOUNDNAMES_SOUNDNAMEID + " = " + soundNameIdList.get(i) + ";";
            cursor = sqLiteDatabase.rawQuery(checkIfRowExists, null);
            int cursorSize = cursor.getCount();
            try {
                while(cursor.moveToNext()){
                    String soundName = "";
                    String soundPosition = "";
                    soundName = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME));
                    soundPosition = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONNAME));
                    //swap_airfr1
                    String audioPath = "swap_" + soundName;
                    switch (soundPosition){
                        case "beginning":
                            audioPath += "ir";
                            break;
                        case "middle":
                            audioPath += "mr";
                            break;
                        case "ending":
                            audioPath += "fr";
                            break;
                    }
                    if(positionNumber == 1){
                        audioPath += 1;
                    } else if(positionNumber == 2){
                        audioPath += 2;
                    }
                    listOfSentenceAudioPaths.add(audioPath);
                }
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
        return listOfSentenceAudioPaths;
    }

    public ArrayList<String> getSentenceDrawablePath(Context context, DatabaseOperationsHelper dop, ArrayList<Integer> soundNameIdList, int positionNumber){
        ArrayList<String> listOfSentenceAudioPaths = new ArrayList<>();
        ArrayList<String> listOfSoundNames = new ArrayList<>();
        ArrayList<String> listOfSoundPositions = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String checkIfRowExists = "";
        Cursor cursor = null;
        for(int i = 0; i < soundNameIdList.size(); i++){
            checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDNAMES_NAME +
                    " WHERE " + TableInfo.COLUMN_SOUNDNAMES_SOUNDNAMEID + " = " + soundNameIdList.get(i) + ";";
            cursor = sqLiteDatabase.rawQuery(checkIfRowExists, null);
            int cursorSize = cursor.getCount();
            try {
                while(cursor.moveToNext()){
                    String soundName = "";
                    String soundPosition = "";
                    soundName = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME));
                    soundPosition = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONNAME));
                    listOfSoundNames.add(soundName);
                    listOfSoundPositions.add(soundPosition);
                }
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
        for(int i = 0; i < listOfSoundNames.size(); i++){
            checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDSWAPNAMES_NAME +
                    " WHERE " + TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDNAME + " = ? AND " + TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDPOSITIONNAME + " = ?;";
            cursor = sqLiteDatabase.rawQuery(checkIfRowExists, new String[]{listOfSoundNames.get(i), listOfSoundPositions.get(i)});
            int cursorSize = cursor.getCount();
            try {
                while(cursor.moveToNext()){
                    String drawablePath = "";
                    if(positionNumber == 1){
                        drawablePath = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDBEGINNINGRESOURCENAME));
                    } else if(positionNumber == 2){
                        drawablePath = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDENDINGRESOURCENAME));
                    }
                    listOfSentenceAudioPaths.add(drawablePath);
                }
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
        return listOfSentenceAudioPaths;
    }

    public void getListOfAllItemsForDatabaseGeneration(Context context, DatabaseOperationsHelper dop){
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDNAMES_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfRowExists, null);
        int cursorSize = cursor.getCount();
        try {
            while(cursor.moveToNext()){
                int rowNumber = cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDNAMEID));
                String databaseSQL = "INSERT INTO SOUNDNAMES(SOUND_NAME, SOUND_POSITION_NAME, SOUND_IMAGE_NAME, SOUND_IMAGE_FILE, SOUND_AUDIO_FILE) " +
                        "VALUES('" +
                        cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME)) + "','" +
                        cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONNAME)) + "','" +
                        cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGEAUDIONAME)) + "',LOAD_FILE('C:/Users/josephdsmithjr/Documents/Personal/SpeechEssentials/pictures/" +
                        cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGEAUDIONAME)) + ".jpg')," +
                        "LOAD_FILE('C:/Users/josephdsmithjr/Documents/Personal/SpeechEssentials/audio/" +
                        cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGEAUDIONAME)) + ".mp3'));";
                System.out.println("SQL #: " + rowNumber + databaseSQL);
                //Log.d("Database OperationsSQL", "#" + rowNumber + ": " + databaseSQL);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }

    public void getListOfMissingResources(Context context, DatabaseOperationsHelper dop){
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String checkIfRowExists = "SELECT * FROM " + TableInfo.TABLE_SOUNDNAMES_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfRowExists, null);
        int cursorSize = cursor.getCount();
        try {
            while(cursor.moveToNext()){
                String drawablePath = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGEAUDIONAME));
                int drawable = context.getResources().getIdentifier(drawablePath, "drawable", context.getPackageName());
                int raw = context.getResources().getIdentifier(drawablePath, "raw", context.getPackageName());
                if(drawable == 0){
                    Log.d("Database Operations", "Missing drawable file for word: " + drawablePath);
                }
                if(raw == 0){
                    Log.d("Database Operations", "Missing raw file for word: " + drawablePath);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }

    public HashMap<Integer, String> getSoundToSoundIdMap(DatabaseOperationsHelper dop){
        HashMap<Integer, String> soundToSoundIdMap = new HashMap<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String soundNameListQuery = "SELECT * FROM " +
                TableInfo.TABLE_SOUNDS_NAME + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(soundNameListQuery, null);
        while (cursor.moveToNext()){
            soundToSoundIdMap.put(cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDS_SOUNDID)), cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDS_SOUNDNAME)));
        }
        return soundToSoundIdMap;
    }

    public HashMap<Integer, String> getPositionToPositionIdMap(DatabaseOperationsHelper dop){
        HashMap<Integer, String> positionToPositionIdMap = new HashMap<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String soundNameListQuery = "SELECT * FROM " +
                TableInfo.TABLE_SOUNDPOSITIONS_NAME + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(soundNameListQuery, null);
        while (cursor.moveToNext()){
            positionToPositionIdMap.put(cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDPOSITIONS_SOUNDPOSITIONSID)), cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDPOSITIONS_SOUNDPOSITIONSNAME)));
        }
        return positionToPositionIdMap;
    }

    public void addScore(DatabaseOperationsHelper dop, String username, String score){
        //INSERT VALUES INTO SCORES TABLE
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableInfo.COLUMN_SCORES_USERNAME, username);
        contentValues.put(TableInfo.COLUMN_SCORES_SCOREDATE, getDate());
        contentValues.put(TableInfo.COLUMN_SCORES_SCORE, score);
        sqLiteDatabase.insert(TableInfo.TABLE_SCORES_NAME, null, contentValues);
        Log.d("Database operations", "One row inserted into scores table for user: " + username);
    }

    public ArrayList<Integer> getListOfScoreIdsForUser(DatabaseOperationsHelper dop, int userId){
        //INSERT VALUES INTO SCORES TABLE
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        ArrayList<Integer> listOfScoreIds = new ArrayList<>();
        String checkIfUsersExistQuery = "SELECT * FROM " + TableInfo.TABLE_SCORES_NAME + " WHERE " + TableInfo.COLUMN_SCORES_USERNAME + " = " + userId;
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfUsersExistQuery, null);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                listOfScoreIds.add(cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_SCORES_SCORESID)));
            }
        }
        return listOfScoreIds;
    }

    public ArrayList<Integer> getListOfScoreIdsForUser(DatabaseOperationsHelper dop, String username){
        //INSERT VALUES INTO SCORES TABLE
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        ArrayList<Integer> listOfScoreIds = new ArrayList<>();
        String checkIfUsersExistQuery = "SELECT * FROM " + TableInfo.TABLE_SCORES_NAME + " WHERE " + TableInfo.COLUMN_SCORES_USERNAME + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfUsersExistQuery, new String[]{username});
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                listOfScoreIds.add(cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_SCORES_SCORESID)));
            }
        }
        return listOfScoreIds;
    }

    public String[] getScoreDataForUserId(DatabaseOperationsHelper dop, int scoreId){
        String[] scoreDataList = new String[2];
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String checkIfUsersExistQuery = "SELECT * FROM " + TableInfo.TABLE_SCORES_NAME + " WHERE " + TableInfo.COLUMN_SCORES_SCORESID + " = " + scoreId + "";
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfUsersExistQuery, null);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()) {
                scoreDataList[0] = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SCORES_SCOREDATE));
                scoreDataList[1] = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SCORES_SCORE));
            }
        }
        return scoreDataList;
    }

    public boolean deleteScoreForUser(DatabaseOperationsHelper dop, int scoreId){
        boolean wasDeleted = false;
        try {
            SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
            sqLiteDatabase.execSQL("delete from " + TableInfo.TABLE_SCORES_NAME + " where " + TableInfo.COLUMN_SCORES_SCORESID + " = '" + scoreId + "'");
            wasDeleted = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return wasDeleted;
    }

    public void addNotes(DatabaseOperationsHelper dop, String notes, int scoreid){
        //INSERT VALUES INTO USERS TABLE
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableInfo.COLUMN_NOTES_NOTES, notes);
        contentValues.put(TableInfo.COLUMN_NOTES_SCOREID, scoreid);
        sqLiteDatabase.insert(TableInfo.TABLE_NOTES_NAME, null, contentValues);
        Log.d("Database operations", "One row inserted into notes table");
    }

    public void addSoundPosition(DatabaseOperationsHelper dop, String soundPositionName){
        //INSERT VALUES INTO SOUNDPOSITIONS TABLE
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String checkIfUsersExistQuery = "SELECT * FROM " + TableInfo.TABLE_SOUNDPOSITIONS_NAME + " WHERE " + TableInfo.COLUMN_SOUNDPOSITIONS_SOUNDPOSITIONSNAME + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(checkIfUsersExistQuery, new String[] {soundPositionName});
        int cursorCount = cursor.getCount();
        if(cursorCount <= 0){
            contentValues.put(TableInfo.COLUMN_SOUNDPOSITIONS_SOUNDPOSITIONSNAME, soundPositionName);
            sqLiteDatabase.insert(TableInfo.TABLE_SOUNDPOSITIONS_NAME, null, contentValues);
            Log.d("Database operations", "Inserted + " + soundPositionName + " into soundpositions table");
        }
//        sqLiteDatabase.delete(TableInfo.TABLE_SOUNDPOSITIONS_NAME,null,null);
//        sqLiteDatabase.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TableInfo.TABLE_SOUNDPOSITIONS_NAME + "'");
    }

    public void addDiffSentence(DatabaseOperationsHelper dop, String diffSentenceName, String diffSentenceAudioPath){
        //INSERT VALUES INTO DIFFSENTENCES TABLE
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableInfo.COLUMN_DIFFSENTENCES_DIFFSENTENCESNAME, diffSentenceName);
        contentValues.put(TableInfo.COLUMN_DIFFSENTENCES_DIFFSENTENCESIMAGEAUDIONAME, diffSentenceAudioPath);
        sqLiteDatabase.insert(TableInfo.TABLE_DIFFSENTENCES_NAME, null, contentValues);
        Log.d("Database operations", "One row inserted into diffsentences table");
    }

    public int getSoundId(DatabaseOperationsHelper dop, String soundName){
        int soundId = 0;
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String soundNameListQuery = "SELECT " +
                TableInfo.COLUMN_SOUNDS_SOUNDID +
                " FROM " +
                TableInfo.TABLE_SOUNDS_NAME +
                " WHERE " + TableInfo.COLUMN_SOUNDS_SOUNDNAME + " = '" + soundName + "';";
        Cursor cursor = sqLiteDatabase.rawQuery(soundNameListQuery, null);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()) {
                soundId = cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDS_SOUNDID));
            }
        }
        return soundId;
    }

    public int getSoundImageId(DatabaseOperationsHelper dop, String soundName, String sound, String wordType) {
        String soundNameListQuery = "SELECT * FROM " + TableInfo.TABLE_SOUNDNAMES_NAME + " WHERE " + TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGENAME + " = '" + soundName + "' AND " + TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME + " = '" + sound + "' AND " + TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONNAME + " = '" + wordType + "';";
        if (wordType.contains("swap")) {
            soundNameListQuery = "SELECT * FROM " + TableInfo.TABLE_SOUNDSWAPNAMES_NAME + " WHERE " + TableInfo.COLUMN_SOUNDSWAPNAMES_BEGINNINGNAME + " = '" + soundName + "' AND " + TableInfo.COLUMN_SOUNDSWAPNAMES_ENDINGNAME + " = '" + sound + "' AND " + TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDPOSITIONNAME + " = '" + wordType + "';";
        }
        int soundId = 0;
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(soundNameListQuery, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if (wordType.contains("swap")) {
                    soundId = cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDSWAPNAMES_SOUNDSWAPNAMEID));
                } else {
                    soundId = cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDNAMEID));
                }
            }
        }
        return soundId;
    }

    public int getSoundImageId(DatabaseOperationsHelper dop, String soundName, String sound){
        int soundId = 0;
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String soundNameListQuery = "SELECT * FROM " +
                TableInfo.TABLE_SOUNDNAMES_NAME +
                " WHERE " + TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGENAME + " = '" + soundName + "' AND " + TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME + " = '" + sound + "';";
        Cursor cursor = sqLiteDatabase.rawQuery(soundNameListQuery, null);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()) {
                soundId = cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDNAMEID));
            }
        }
        return soundId;
    }

    public int getSoundPositionId(DatabaseOperationsHelper dop, String soundPositionName){
        int soundId = 0;
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String soundNameListQuery = "SELECT " +
                TableInfo.COLUMN_SOUNDPOSITIONS_SOUNDPOSITIONSID +
                " FROM " +
                TableInfo.TABLE_SOUNDPOSITIONS_NAME +
                " WHERE " + TableInfo.COLUMN_SOUNDPOSITIONS_SOUNDPOSITIONSNAME + " = " + soundPositionName + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(soundNameListQuery, null);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()) {
                soundId = cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDS_SOUNDID));
            }
        }
        return soundId;
    }

    public ArrayList<Integer> getSoundNameIdList(DatabaseOperationsHelper dop, ArrayList<String> soundNames, ArrayList<String> soundPositions){
        ArrayList<Integer> soundIdList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String soundNameListQuery = "";
        if(soundPositions.size() < 1){
            soundNameListQuery = "SELECT " +
                    TableInfo.COLUMN_SOUNDNAMES_SOUNDNAMEID +
                    " FROM " +
                    TableInfo.TABLE_SOUNDNAMES_NAME +
                    " WHERE " + TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME + " IN (" + makeInClauseStringFromStringList(soundNames) + ");";
        } else {
            soundNameListQuery = "SELECT " +
                    TableInfo.COLUMN_SOUNDNAMES_SOUNDNAMEID +
                    " FROM " +
                    TableInfo.TABLE_SOUNDNAMES_NAME +
                    " WHERE " + TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME + " IN (" + makeInClauseStringFromStringList(soundNames) + ")" +
                    " AND " + TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONSID + " IN (" + makeInClauseStringFromStringList(soundPositions) + ");";
        }
        Cursor cursor = sqLiteDatabase.rawQuery(soundNameListQuery, null);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                soundIdList.add(cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDS_SOUNDID)));
            }
        }
        return soundIdList;
    }

    public ArrayList<String> getSoundList(DatabaseOperationsHelper dop, ArrayList<Integer> soundIds){
        ArrayList<String> soundNameList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String soundNameListQuery = "SELECT " +
                TableInfo.COLUMN_SOUNDS_SOUNDNAME +
                " FROM " +
                TableInfo.TABLE_SOUNDS_NAME +
                " WHERE " + TableInfo.COLUMN_SOUNDS_SOUNDID + " IN (" + makeInClauseStringFromIntegerList(soundIds) + ");";
        Cursor cursor = sqLiteDatabase.rawQuery(soundNameListQuery, null);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                soundNameList.add(cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDS_SOUNDNAME)));
            }
        }
        return soundNameList;
    }

    public ArrayList<String> getSoundInfoList(DatabaseOperationsHelper dop, String soundName){
        ArrayList<String> soundNameList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String soundNameListQuery = "SELECT * FROM " +
                TableInfo.TABLE_SOUNDNAMES_NAME +
                " WHERE " + TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(soundNameListQuery, new String[] {soundName});
        int cursorCount = cursor.getCount();
        if(cursorCount > 0){
            while(cursor.moveToNext()){
                //"b","beginning","back","R.drawable.back","R.raw.back"
                String soundType = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME)) + " - " + cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONNAME));
                String soundWord = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGENAME));
                String imageResource = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGEAUDIONAME));
                String soundInfo = soundType + "|" + soundWord + "|" + imageResource + "|" + imageResource;
                soundNameList.add(soundInfo);
            }
        }
        return soundNameList;
    }

    public ArrayList<String> getSentenceList(DatabaseOperationsHelper dop, String sound){
        ArrayList<String> soundNameList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String soundNameListQuery = "SELECT * FROM " +
                TableInfo.TABLE_SOUNDNAMES_NAME +
                " WHERE " + TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME + " = '" + sound + "' AND " + TableInfo.COLUMN_SOUNDNAMES_SOUNDPOSITIONNAME + " LIKE 'sentence%';";
        Cursor cursor = sqLiteDatabase.rawQuery(soundNameListQuery, null);
        int cursorCount = cursor.getCount();
        if(cursorCount > 0){
            while(cursor.moveToNext()){
                String soundName = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGEAUDIONAME));
                soundNameList.add(soundName);
            }
        }
        return soundNameList;
    }

    public ArrayList<String> getSoundTypeList(DatabaseOperationsHelper dop, String soundName){
        ArrayList<String> soundNameList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String soundNameListQuery = "SELECT " +
                TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME +
                " FROM " +
                TableInfo.TABLE_SOUNDNAMES_NAME +
                " WHERE " + TableInfo.COLUMN_SOUNDS_SOUNDNAME + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(soundNameListQuery, new String[]{soundName});
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                soundNameList.add(cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME)));
            }
        }
        return soundNameList;
    }

    public ArrayList<String> getSoundNameList(DatabaseOperationsHelper dop, String soundName){
        ArrayList<String> soundNameList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String soundNameListQuery = "SELECT " +
                TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME +
                " FROM " +
                TableInfo.TABLE_SOUNDNAMES_NAME +
                " WHERE " + TableInfo.COLUMN_SOUNDS_SOUNDNAME + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(soundNameListQuery, new String[] {soundName});
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                soundNameList.add(cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME)));
            }
        }
        return soundNameList;
    }

    public ArrayList<String> getSoundNameList(DatabaseOperationsHelper dop, ArrayList<Integer> soundIds){
        ArrayList<String> soundNameList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String soundNameListQuery = "SELECT " +
                TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME +
                " FROM " +
                TableInfo.TABLE_SOUNDNAMES_NAME +
                " WHERE " + TableInfo.COLUMN_SOUNDNAMES_SOUNDID + " IN (" + makeInClauseStringFromIntegerList(soundIds) + ");";
        Cursor cursor = sqLiteDatabase.rawQuery(soundNameListQuery, null);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                soundNameList.add(cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDNAME)));
            }
        }
        return soundNameList;
    }

    public HashMap<String,Integer> getSettingsMap(DatabaseOperationsHelper dop){
        HashMap<String,Integer> settingsMap = new HashMap<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String soundNameListQuery = "SELECT *" +
                " FROM " +
                TableInfo.TABLE_SOUNDNAMES_NAME + ");";
        Cursor cursor = sqLiteDatabase.rawQuery(soundNameListQuery, null);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                settingsMap.put(cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SETTINGS_NAME)), cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_SETTINGS_VALUE)));
            }
        }
        return settingsMap;
    }

    public ArrayList<String> getSoundImagePathList(DatabaseOperationsHelper dop, ArrayList<Integer> soundIds){
        ArrayList<String> soundNameList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        String soundNameListQuery = "SELECT " +
                TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGEAUDIONAME +
                " FROM " +
                TableInfo.TABLE_SOUNDS_NAME +
                " WHERE " + TableInfo.COLUMN_SOUNDS_SOUNDID + " IN (" + makeInClauseStringFromIntegerList(soundIds) + ");";
        Cursor cursor = sqLiteDatabase.rawQuery(soundNameListQuery, null);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                soundNameList.add(cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_SOUNDNAMES_SOUNDIMAGEAUDIONAME)));
            }
        }
        return soundNameList;
    }

    public String makeInClauseStringFromStringList(ArrayList<String> inClauseList){
        String inClauseString = "";
        for(int i = 0; i < inClauseList.size(); i++){
            if(i == inClauseList.size()-1){
                inClauseString += "\"" + inClauseList.get(i) + "\"";
            } else {
                inClauseString += "\"" + inClauseList.get(i) + "\", ";
            }
        }
        return inClauseString;
    }

    public String makeInClauseStringFromIntegerList(ArrayList<Integer> inClauseList){
        String inClauseString = "";
        for(int i = 0; i < inClauseList.size(); i++){
            if(i == inClauseList.size()-1){
                inClauseString += inClauseList.get(i) + "";
            } else {
                inClauseString += inClauseList.get(i) + ", ";
            }
        }
        return inClauseString;
    }

    public String getDate(){
        String date = "";
        //SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        SimpleDateFormat s = new SimpleDateFormat("MM/dd/yy");
        date = s.format(new Date());
        return date;
    }

    public boolean ensureDatabaseIsUpToDate(Context context, ProgressBar progressBar){
        boolean everythingIsUpToDate = false;
        FileHelper fileHelper = new FileHelper();
        try {
            context.openFileInput("databaseUpToDateFile");
            everythingIsUpToDate = true;
        } catch (FileNotFoundException e){
            updateTheDatabase(context, progressBar, (SplashActivity) context);
        }
        return everythingIsUpToDate;
    }

    public long getNumberOfSoundNamesRows(){
        long numberOfSoundNameRows = 0;
        try {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            numberOfSoundNameRows = DatabaseUtils.longForQuery(sqLiteDatabase, "SELECT COUNT(*) FROM " + TableInfo.TABLE_SOUNDNAMES_NAME, null);
        } catch (Exception e){
            e.printStackTrace();
        }
        return numberOfSoundNameRows;
    }

    public void updateTheDatabase(Context context, ProgressBar progressBar, SplashActivity splashActivity){
        FileHelper fileHelper = new FileHelper();
        SoundHelper soundHelper = new SoundHelper();
        ArrayList<String> soundNames = soundHelper.getSoundList();
        ArrayList<String> soundPositions = soundHelper.getSoundPositionNameList();
        addSetting(this, "voice_on", 1);
        addSetting(this, "scoring_buttons_visible", 1);
        addSetting(this, "mirror_function_on", 1);
        addSetting(this, "scoring_sounds", 1);
        progressBar.setMax(5312);
        splashActivity.updateTextView("Updating database with over 3600 words.");
        soundHelper.addMultipleSoundImageAudioResourcesToDB(context, progressBar);
        splashActivity.updateTextView("Updating database with over 1500 sentences.");
        soundHelper.addMultipleSentencesImageAudioResourcesToDB(context, progressBar);
        splashActivity.updateTextView("Updating database with over 100 word swapping sentences.");
        soundHelper.addMultipleWordSwapImageAudioResourcesToDB(context, progressBar);
        splashActivity.updateTextView("Downloading sound resources now.");
        for(int i = 0; i < soundNames.size(); i++){
            addSound(this, soundNames.get(i));
        }
        for(int i = 0; i < soundPositions.size(); i++){
            addSoundPosition(this, soundPositions.get(i));
        }
        fileHelper.getStringFromFile("databaseUpToDateFile","1",context);
    }

    public void refreshTheDatabase(Context context, ProgressBar progressBar, SplashActivity splashActivity){
        FileHelper fileHelper = new FileHelper();
        SoundHelper soundHelper = new SoundHelper();
        ArrayList<String> soundNames = soundHelper.getSoundList();
        ArrayList<String> soundPositions = soundHelper.getSoundPositionNameList();
        progressBar.setMax(5312);
        splashActivity.updateTextView("Updating database with over 3600 words.");
        soundHelper.addMultipleSoundImageAudioResourcesToDB(context, progressBar);
        splashActivity.updateTextView("Updating database with over 1500 sentences.");
        soundHelper.addMultipleSentencesImageAudioResourcesToDB(context, progressBar);
        splashActivity.updateTextView("Updating database with over 100 word swapping sentences.");
        soundHelper.addMultipleWordSwapImageAudioResourcesToDB(context, progressBar);
        splashActivity.updateTextView("Downloading sound resources now.");
        for(int i = 0; i < soundNames.size(); i++){
            addSound(this, soundNames.get(i));
        }
        for(int i = 0; i < soundPositions.size(); i++){
            addSoundPosition(this, soundPositions.get(i));
        }
        fileHelper.getStringFromFile("databaseUpToDateFile","1",context);
    }

    public void updateTheDatabase(Context context){
        FileHelper fileHelper = new FileHelper();
        SoundHelper soundHelper = new SoundHelper();
        ArrayList<String> soundNames = soundHelper.getSoundList();
        ArrayList<String> soundPositions = soundHelper.getSoundPositionNameList();
        addSetting(this, "voice_on", 1);
        addSetting(this, "scoring_buttons_visible", 1);
        addSetting(this, "mirror_function_on", 1);
        addSetting(this, "scoring_sounds", 1);
        soundHelper.addMultipleSoundImageAudioResourcesToDB(context, null);
        soundHelper.addMultipleSentencesImageAudioResourcesToDB(context, null);
        soundHelper.addMultipleWordSwapImageAudioResourcesToDB(context, null);
        for(int i = 0; i < soundNames.size(); i++){
            addSound(this, soundNames.get(i));
        }
        for(int i = 0; i < soundPositions.size(); i++){
            addSoundPosition(this, soundPositions.get(i));
        }
        fileHelper.getStringFromFile("databaseUpToDateFile","1",context);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
