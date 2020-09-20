package com.example.dogapp.supporter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dogapp.ListFragmentDirections;
import com.example.dogapp.R;
import com.example.dogapp.entites.DogBreed;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class DogListAdapter extends RecyclerView.Adapter<DogListAdapter.MyViewHolder> {
    private List<DogBreed> dogBreedList;
    private Context context;

    public DogListAdapter(Context context, List<DogBreed> dogBreedList) {
        this.context = context;
        this.dogBreedList = dogBreedList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.dog_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (dogBreedList.get(position).getBitmap() != null){
            holder.ivDog.setImageBitmap(dogBreedList.get(position).getBitmap());
        } else {
            try {
                Glide.with(context)
                        .load(R.raw.loading)
                        .into(holder.ivDog);
            } catch (Exception e){
                e.printStackTrace();
            }

            String url = dogBreedList.get(position).getUrl();
            holder.ivDog.setTag(url);
            ImageLoader loader = new ImageLoader(holder.ivDog, url, dogBreedList.get(position));
            loader.execute();
        }

        holder.tvDogName.setText(dogBreedList.get(position).getName());
        holder.tvDogBredFor.setText(dogBreedList.get(position).getBredFor());
    }

    @Override
    public int getItemCount() {
        return dogBreedList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public GifImageView ivDog;
        public TextView tvDogName;
        public TextView tvDogBredFor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cardView = (CardView) itemView;
            ivDog = itemView.findViewById(R.id.iv_dog_img);
            tvDogName = itemView.findViewById(R.id.tv_dog_name);
            tvDogBredFor = itemView.findViewById(R.id.tv_dog_bred_for);

            itemView.setOnClickListener(view -> {
                NavDirections action = ListFragmentDirections.actionListFragmentToDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("dog_breed", dogBreedList.get(getLayoutPosition()));
                Navigation.findNavController(view).navigate(action.getActionId(), bundle);
            });
        }
    }
}
