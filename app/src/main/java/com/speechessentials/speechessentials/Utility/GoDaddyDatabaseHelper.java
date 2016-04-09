package com.speechessentials.speechessentials.Utility;

/**
 * Created by josephdsmithjr on 4/11/15.
 */
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.TextView;

public class GoDaddyDatabaseHelper extends AsyncTask<String,Void,String>{

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private Context context;
    private int byGetOrPost = 0;
    String soundNameColumn = "";
    public GoDaddyDatabaseHelper(Context context, TextView statusField, TextView roleField, int flag) {
        this.context = context;
        byGetOrPost = flag;
    }

    protected void onPreExecute(){

    }

    @Override
    protected String doInBackground(String... arg0) {
        String dataset = "";
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager.getConnection("jdbc:mysql://p3nlmysql113plsk.secureserver.net:3306/SpeechEssentials?" + "user=speechdbuser&password=uT5n7o9");
            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement.executeQuery("select * from SpeechEssentials.soundnames");
            preparedStatement = connect.prepareStatement("SELECT SOUND_NAME, SOUND_POSITION_NAME, SOUND_IMAGE_NAME, SOUND_IMAGE_FILE, SOUND_AUDIO_FILE from SpeechEssentials.soundnames");
            resultSet = preparedStatement.executeQuery();
            dataset = writeResultSet(resultSet);
            ArrayList<String> soundNamesList = getSoundNames(resultSet);
            HashMap<String, Integer> soundWordTypeMap = getSoundWordType(resultSet);
            HashMap<String, Bitmap> soundImageMap = getSoundImageMap(resultSet);
            HashMap<String, FileInputStream> soundAudioMap = getSoundAudioMap(resultSet);
        } catch (Exception e) {
            String ex = new String("Exception: " + e.getMessage());
        } finally {
            close();
        }
        return dataset;
    }

    @Override
    protected void onPostExecute(String results){
        if(results.equals("false")){
            //openNetworkIssuesDialog();
        } else {

        }
    }

    private String writeResultSet(ResultSet resultSet) throws SQLException {
        String dataset = "";
        while (resultSet.next()) {
            String soundName = resultSet.getString("SOUND_NAME");
            String soundWordType = resultSet.getString("SOUND_POSITION_NAME");
            String soundImageName = resultSet.getString("SOUND_IMAGE_NAME");
            Blob soundImageBlob = resultSet.getBlob("SOUND_IMAGE_FILE");
            try{
                Bitmap soundBitmap = BitmapFactory.decodeStream(soundImageBlob.getBinaryStream());
            } catch (Exception e){
                e.printStackTrace();
            }
            Blob soundAudioBlob = resultSet.getBlob("SOUND_AUDIO_FILE");
            try {
                int blobLength = (int) soundAudioBlob.length();
                byte[] blobAsBytes = soundAudioBlob.getBytes(1, blobLength);
                // create temp file that will hold byte array
                File tempMp3 = File.createTempFile(soundName, "mp3");
                tempMp3.deleteOnExit();
                FileOutputStream fos = new FileOutputStream(tempMp3);
                fos.write(blobAsBytes);
                fos.close();

                // Tried reusing instance of media player
                // but that resulted in system crashes...
                MediaPlayer mediaPlayer = new MediaPlayer();

                // Tried passing path directly, but kept getting
                // "Prepare failed.: status=0x1"
                // so using file descriptor instead
                FileInputStream fis = new FileInputStream(tempMp3);
                mediaPlayer.setDataSource(fis.getFD());
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException ex) {
                String s = ex.toString();
                ex.printStackTrace();
            }
            dataset += soundName;
        }
        return dataset;
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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

    private HashMap<String, Bitmap> getSoundImageMap(ResultSet resultSet) throws SQLException {
        HashMap<String, Bitmap> soundImageMap = new HashMap<>();
        while (resultSet.next()) {
            String soundName = resultSet.getString("SOUND_IMAGE_NAME");
            Blob soundBlob = resultSet.getBlob("SOUND_IMAGE_FILE");
            Bitmap soundBitmap = BitmapFactory.decodeStream(soundBlob.getBinaryStream());
            soundImageMap.put(soundName, soundBitmap);
            //release the blob and free up memory. (since JDBC 4.0)
            soundBlob.free();
        }
        return soundImageMap;
    }

    private HashMap<String, FileInputStream> getSoundAudioMap(ResultSet resultSet) throws SQLException {
        HashMap<String, FileInputStream> soundAudioMap = new HashMap<>();
        while (resultSet.next()) {
            String soundName = resultSet.getString("SOUND_IMAGE_NAME");
            Blob soundBlob = resultSet.getBlob("SOUND_AUDIO_FILE");
            int blobLength = (int) soundBlob.length();
            byte[] blobAsBytes = soundBlob.getBytes(1, blobLength);
            ByteArrayInputStream audioStream = new ByteArrayInputStream(blobAsBytes);
            //release the blob and free up memory. (since JDBC 4.0)
            soundBlob.free();
            try{
                // create temp file that will hold byte array
                File tempMp3 = File.createTempFile(soundName, "mp3", context.getCacheDir());
                tempMp3.deleteOnExit();
                FileOutputStream fos = new FileOutputStream(tempMp3);
                fos.write(blobAsBytes);
                FileInputStream fis = new FileInputStream(tempMp3);
                soundAudioMap.put(soundName, fis);
            } catch (Exception e){

            }
        }
        return soundAudioMap;
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
