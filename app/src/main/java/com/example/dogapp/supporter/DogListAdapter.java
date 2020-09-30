package com.example.dogapp.supporter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogapp.ListFragmentDirections;
import com.example.dogapp.R;
import com.example.dogapp.activities.MainActivity;
import com.example.dogapp.dao.DogBreedDao;
import com.example.dogapp.dao.DogBreedDatabase;
import com.example.dogapp.entites.DogBreed;
import com.example.dogapp.utils.ImageUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class DogListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DogBreed> dogBreedList;
    private Context context;

    // final variable
    private final int SHOW_DRAFT = 135;
    private final int HIDE_DRAFT = 246;


    public DogListAdapter(Context context, List<DogBreed> dogBreedList) {
        this.context = context;
        this.dogBreedList = dogBreedList;
    }

    @Override
    public int getItemViewType(int position) {
        return dogBreedList.get(position).isShowingDraft() ? SHOW_DRAFT : HIDE_DRAFT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == HIDE_DRAFT){
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.dog_item, parent, false));
        } else {
            return new DraftViewHolder(LayoutInflater.from(context).inflate(R.layout.draft_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){
            MyViewHolder myViewHolder = (MyViewHolder) holder;

            if (ImageUtils.isExistFile(dogBreedList.get(position).getId() + "")){
                myViewHolder.gifLoading.setVisibility(View.GONE);
                Uri uri = Uri.fromFile(ImageUtils.getFileFromSdCard(dogBreedList.get(position).getId() + ""));
                Picasso.with(context).load(uri).into(myViewHolder.ivDog);
            } else {
                if(MainActivity.isNetworkConnected){
                    Target target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            myViewHolder.gifLoading.setVisibility(View.GONE);
                            myViewHolder.ivDog.setVisibility(View.VISIBLE);
                            myViewHolder.ivDog.setImageBitmap(bitmap);
                            Thread thread = new Thread(){
                                @Override
                                public void run() {
                                    ImageUtils.storeImage(bitmap, dogBreedList.get(position).getId() + "");
                                }
                            };
                            thread.start();
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                            myViewHolder.ivDog.setVisibility(View.GONE);
                            myViewHolder.gifLoading.setVisibility(View.VISIBLE);
                        }
                    };
                    myViewHolder.ivDog.setTag(target);
                    Picasso.with(context)
                            .load(dogBreedList.get(position).getUrl())
                            .into(target);
                }
            }

            myViewHolder.tvDogName.setText(dogBreedList.get(position).getName());
            myViewHolder.tvDogBredFor.setText(dogBreedList.get(position).getBredFor());

            myViewHolder.cardView.setOnLongClickListener(view -> {
                showDraftAtPosition(position);
                return true;
            });
        }

        if(holder instanceof DraftViewHolder){
            DraftViewHolder draftViewHolder = (DraftViewHolder) holder;
            DogBreed dogBreed = dogBreedList.get(position);
            draftViewHolder.tvDogName.setText(dogBreed.getName());
            draftViewHolder.tvLifeSpan.setText(dogBreed.getLifeSpan());
            draftViewHolder.tvDogBredFor.setText(dogBreed.getBredFor());
        }
    }

    @Override
    public int getItemCount() {
        return dogBreedList.size();
    }

    public void showDraftAtPosition(int pos){
        for(DogBreed dogBreed : dogBreedList){
            dogBreed.setShowingDraft(false);
        }
        dogBreedList.get(pos).setShowingDraft(true);
        notifyDataSetChanged();
    }

    public boolean isDraftShown(){
        for(DogBreed dogBreed : dogBreedList){
            if(dogBreed.isShowingDraft()){
                return true;
            }
        }

        return false;
    }

    public void closeDraft(){
        for(DogBreed dogBreed : dogBreedList){
            dogBreed.setShowingDraft(false);
        }
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public int id;
        public CardView cardView;
        public ImageView ivDog;
        public TextView tvDogName;
        public TextView tvDogBredFor;
        public GifImageView gifLoading;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cardView = (CardView) itemView;
            ivDog = itemView.findViewById(R.id.iv_dog_img);
            tvDogName = itemView.findViewById(R.id.tv_dog_name);
            tvDogBredFor = itemView.findViewById(R.id.tv_dog_bred_for);
            gifLoading = itemView.findViewById(R.id.gif_loading);

            itemView.setOnClickListener(view -> {
                NavDirections action = ListFragmentDirections.actionListFragmentToDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("dog_breed", dogBreedList.get(getLayoutPosition()));
                Navigation.findNavController(view).navigate(action.getActionId(), bundle);
            });
        }
    }

    public class DraftViewHolder extends RecyclerView.ViewHolder{
        public int id;
        public CardView cardView;
        public TextView tvDogName;
        public TextView tvLifeSpan;
        public TextView tvDogBredFor;

        public DraftViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cardView = (CardView) itemView;
            tvDogName = itemView.findViewById(R.id.tv_dog_name);
            tvDogBredFor = itemView.findViewById(R.id.tv_bred_for);
            tvLifeSpan = itemView.findViewById(R.id.tv_life_span);

            itemView.setOnClickListener(view -> closeDraft());
        }
    }
}
