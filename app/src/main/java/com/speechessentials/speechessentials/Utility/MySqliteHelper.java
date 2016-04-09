package com.speechessentials.speechessentials.Utility;

/**
 * Created by josephdsmithjr on 4/16/15.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqliteHelper extends SQLiteOpenHelper {

    public static final String TABLE_USERS = "USERS";
    public static final String COLUMN_USERID = "userid";
    public static final String COLUMN_FIRSTNAME = "";
    public static final String COLUMN_LASTNAME = "";
    public static final String COLUMN_EMAIL = "";
    public static final String COLUMN_GENDER = "";
    public static final String COLUMN_BIRTHDAY = "";
    public static final String COLUMN_IMAGEURL = "";

    public static final String TABLE_SCORES = "SCORES";
    public static final String COLUMN_SCORESID = "";
//    public static final String COLUMN_USERID = "";
    public static final String COLUMN_SCOREDATE = "";
    public static final String COLUMN_SCORESOUND = "";
    public static final String COLUMN_SCOREPOSITION = "";
    public static final String COLUMN_SCORECORRECT = "";
    public static final String COLUMN_SCOREAPPROXIMATE = "";
    public static final String COLUMN_SCOREINCORRECT = "";

    private static final String DATABASE_NAME = "commments.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_USERS + "(" + COLUMN_USERID
            + " integer primary key autoincrement, " + COLUMN_USERID
            + " text not null);";

    public MySqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySqliteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

}
/*
USERS
userid
firstname
lastname
email
gender
birthday
imageurl


NOTES
noteid
userid


SCORES
scoreid
userid
scoredate
scoresound
scoreposition
scorecorrect
scoreapproximate
scoreincorrect
scoreword



 */