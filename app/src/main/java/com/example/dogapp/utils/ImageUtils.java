package com.example.dogapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.dogapp.activities.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    public static void storeImage(Bitmap bitmap, String filename) {

        File pictureFile = getOutputMediaFile(filename);
        if (pictureFile == null) {
            System.out.println("Error creating media file, check storage permissions: ");
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(String filename){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + MainActivity.PACKAGE_NAME
                + "/files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        File mediaFile;
        String mImageName = filename +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    public static File getFileFromSdCard(String filename){
        return new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + MainActivity.PACKAGE_NAME
                + "/files"
                + File.separator
                + filename
                + ".jpg");
    }

    public static Bitmap loadBitmapFromSdCard(String filename){
        File file = getFileFromSdCard(filename);
        if(!file.exists()){
            return null;
        } else {
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }
    }

    public static boolean isExistFile(String filename){
        return getFileFromSdCard(filename).exists();
    }
}
