package com.example.dogapp.supporter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.dogapp.entites.DogBreed;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/*
* This class created to load image from url into viewholder in DogListAdapter class
* @properties topId, endId is set value when scrolling recyclerview. It determines viewholders is showing, need to load image first
* */
public class ImageLoader extends AsyncTask<Void, Void, Bitmap> {

    private int id;
    private DogBreed dogBreed;
    private DogListAdapter.MyViewHolder holder;

    // support variables to determined what viewholders are visible
    public static int topId = -1;
    public static int endId = Integer.MAX_VALUE;

    /*
    * @param holder: is contain image view and id
    *        id: is use for check whether it equals with id of viewholder when doInBackGround method execute
    *        dogBreed will setBitmap() to reuse it for next loading image, dont need to get bitmap from url again*/
    public ImageLoader(DogListAdapter.MyViewHolder holder, int id, DogBreed dogBreed) {
        this.holder = holder;
        this.id = id;
        this.dogBreed = dogBreed;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        try{
            if (id >= topId && id <= endId){
                InputStream is = new URL(dogBreed.getUrl()).openStream();
                Bitmap bm = BitmapFactory.decodeStream(is);
                return bm;
            } else {
                return null;
            }
        }catch(IOException e){
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null){
            if(id == holder.id){
                holder.ivDog.setImageBitmap(bitmap);
                dogBreed.setBitmap(bitmap);
            }
        }
    }
}
