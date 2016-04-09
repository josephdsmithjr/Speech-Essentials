package com.speechessentials.speechessentials.Utility;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by josephdsmithjr on 8/26/2014.
 */
public class FileHelper {

    //File Names:
    public String getStringFromFile(String nameOfFile, String defaultFileString, Context context){
        String collected = "";
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = context.openFileInput(nameOfFile);
            byte[] dataArray = new byte[fis.available()];
            while (fis.read(dataArray) != -1){
                collected = new String(dataArray);
            }
        } catch (FileNotFoundException e) {
            //If the file doesn't exist, create it!
            try {
                fos = context.openFileOutput(nameOfFile, Context.MODE_PRIVATE);
                fos.close();
            } catch (FileNotFoundException fe) {
                fe.printStackTrace();
            } catch (IOException ie) {
                ie.printStackTrace();
            } finally{
                try {
                    fos = context.openFileOutput(nameOfFile, Context.MODE_PRIVATE);
                    fos.write(defaultFileString.getBytes());
                } catch (FileNotFoundException fe) {
                    fe.printStackTrace();
                } catch (IOException ie) {
                    ie.printStackTrace();
                } finally {
                    try {
                        fis = context.openFileInput(nameOfFile);
                        byte[] dataArray = new byte[fis.available()];
                        while (fis.read(dataArray) != -1){
                            collected = new String(dataArray);
                        }
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return collected;
    }

    public int getNumberOfFilesInImageDirectory(Context context){
        int numberOfImages = 0;
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory.getPath());
        File[] list = mypath.listFiles();
        numberOfImages = list.length;
        return numberOfImages;
    }

    public boolean writeStringToFile(String fileName, String fileString, Context context){
        boolean updatedFile = false;
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(fileString.getBytes());
            fos.close();
            updatedFile = true;
        } catch (FileNotFoundException e) {
            getStringFromFile(fileName, fileString, context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return updatedFile;
    }

    public void saveToInternalStorage(Context context, Bitmap bitmapImage, String imageName){
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory.getPath() + "/",imageName.toLowerCase().replace(" ","").replace("-","").replace("(","").replace(")","") + ".jpg");
        if(!mypath.exists()){
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(mypath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Missing Image:", "Missing imageName: " + imageName);
            }
        } else {
            //Image already exists!
            try {
                FileOutputStream fos = null;
                fos = new FileOutputStream(mypath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Missing Image:", "Missing imageName: " + imageName);
            }
        }
    }

    public void saveAudioToInternalStorage(Context context, String audioName){
        InputStream myInput = null;
        OutputStream myOutput = null;
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("audioDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory.getPath() + "/",audioName.toLowerCase().replace(" ","").replace("-", "").replace("(","").replace(")","") + ".mp3");
        File[] listOfFiles = context.getFilesDir().listFiles();
        if(mypath.exists()){
            //File exists!
            int i = 0;
        } else {
            try {
                // Open your mp3 file as the input stream
                myOutput = new FileOutputStream(mypath);
                //transfer bytes from the inputfile to the outputfile
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //Close the streams => Better to have it in *final* block
                try {
                    if (myOutput != null) {
                        myOutput.flush();
                        myOutput.close();
                    }
                    if (myInput != null) {
                        myInput.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void deleteImageAndAudioContent(Context context){
        OutputStream myOutput = null;
        ContextWrapper cw = new ContextWrapper(context);
        File dir = cw.getDir("audioDir", Context.MODE_PRIVATE);
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }
        }
        File imageDir = cw.getDir("imageDir", Context.MODE_PRIVATE);
        if (imageDir.isDirectory()) {
            String[] children = imageDir.list();
            for (int i = 0; i < children.length; i++) {
                new File(imageDir, children[i]).delete();
            }
        }
    }

    public void saveAudioInputStreamToInternalStorage(Context context, InputStream myInput, String audioName){
        OutputStream myOutput = null;
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("audioDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory.getPath() + "/",audioName.toLowerCase().replace(" ","").replace("-","").replace("(","").replace(")","") + ".mp3");
        File[] listOfFiles = context.getFilesDir().listFiles();
        if(mypath.exists()){
            //File exists!
            int i = 0;
        } else {
            try {
                // Open your mp3 file as the input stream
                myOutput = new FileOutputStream(mypath);
                //transfer bytes from the inputfile to the outputfile
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Missing Image:", "Missing imageName: " + audioName);
            } finally {
                //Close the streams => Better to have it in *final* block
                try {
                    if (myOutput != null) {
                        myOutput.flush();
                        myOutput.close();
                    }
                    if (myInput != null) {
                        myInput.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<String> getListOfAllImageNamesOnDevice(Context context){
        ArrayList<String> listOfAllImages = new ArrayList<>();
        File mydir = context.getDir("imageDir", Context.MODE_PRIVATE);
        File lister = mydir.getAbsoluteFile();
        for (String list : lister.list())
        {
            listOfAllImages.add(list);
        }
        return listOfAllImages;
    }

    public ArrayList<String> getListOfAllAudioFileNamesOnDevice(Context context){
        ArrayList<String> listOfAllImages = new ArrayList<>();
        File mydir = context.getDir("audioDir", Context.MODE_PRIVATE);
        File lister = mydir.getAbsoluteFile();
        for (String list : lister.list())
        {
            listOfAllImages.add(list);
        }
        return listOfAllImages;
    }

    public Bitmap getBitmapFromInternalStorage(Context context, String bitmapName){
        ImageHelper imageHelper = new ImageHelper();
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        String imagePath = directory.getPath() + "/" + bitmapName + ".jpg";
        Bitmap bitmap = imageHelper.decodeBitmap(imagePath, 100, 100);
        return bitmap;
    }

    public void transferAllResourcesToFile(Context context, DatabaseOperationsHelper dbo){
        ArrayList<String> allImageList = dbo.getDistinctSoundImageNameList(dbo);
        ArrayList<String> missingImageList = new ArrayList<>();
        for(int i = 0; i < allImageList.size(); i++){
            int drawable = context.getResources().getIdentifier(allImageList.get(i), "raw", context.getPackageName());
            if(drawable != 0){
                Bitmap bm = BitmapFactory.decodeResource(context.getResources(), drawable);
                saveToInternalStorage(context, bm, allImageList.get(i));
                saveAudioToInternalStorage(context, allImageList.get(i));
            } else {
                missingImageList.add(allImageList.get(i));
            }
        }
        ArrayList<String> listOfAudios = getListOfAllAudioFileNamesOnDevice(context);
        ArrayList<String> listOfImages = getListOfAllImageNamesOnDevice(context);
        int i = 0;
    }
}