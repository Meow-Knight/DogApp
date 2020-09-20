package com.example.dogapp.supporter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.dogapp.entites.DogBreed;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageLoader extends AsyncTask<Void, Void, Bitmap> {

    private String url;
    private DogBreed dogBreed;
    private ImageView imageView;

    public ImageLoader(ImageView imageView, String url, DogBreed dogBreed) {
        this.imageView = imageView;
        this.url = url;
        this.dogBreed = dogBreed;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        try{
            InputStream is = new URL(dogBreed.getUrl()).openStream();
            Bitmap bm = BitmapFactory.decodeStream(is);
            dogBreed.setBitmap(bm);
            return bm;
        }catch(IOException e){
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(url.equals(imageView.getTag())){
            imageView.setImageBitmap(bitmap);
            imageView.setTag(null);
        }
    }
}
