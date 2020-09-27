package com.example.dogapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dogapp.entites.DogBreed;
import com.example.dogapp.supporter.ImageLoader;

public class DetailFragment extends Fragment {
    // components
    private ImageView ivDogImg;
    private LinearLayout bredForContainer;
    private LinearLayout bredGroupContainer;
    private LinearLayout countryCodeContainer;
    private LinearLayout lifeSpanContainer;
    private LinearLayout temperamentContainer;
    private TextView tvDogName;
    private TextView tvBredFor;
    private TextView tvBredGroup;
    private TextView tvCountryCode;
    private TextView tvLifeSpan;
    private TextView tvTemperament;

    private View view;
    private DogBreed dogBreed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = getView();
        mapComponents();

        if(this.getArguments().getSerializable("dog_breed") != null){
            dogBreed = (DogBreed) this.getArguments().getSerializable("dog_breed");

            if(dogBreed.getBitmap() != null){
                ivDogImg.setImageBitmap(dogBreed.getBitmap());
//            } else {
//                ivDogImg.setTag(dogBreed.getUrl());
//                ImageLoader loader = new ImageLoader(ivDogImg,  , dogBreed);
//                loader.execute();
            }

            tvDogName.setText(dogBreed.getName());

            if (dogBreed.getBredFor() != null){
                tvBredFor.setText(dogBreed.getBredFor());
            } else {
                bredForContainer.setVisibility(View.GONE);
            }

            if (dogBreed.getBreadGroup() != null){
                tvBredGroup.setText(dogBreed.getBreadGroup());
            } else {
                bredGroupContainer.setVisibility(View.GONE);
            }

            if (dogBreed.getCountryCode() != null){
                tvCountryCode.setText(dogBreed.getCountryCode());
            } else {
                countryCodeContainer.setVisibility(View.GONE);
            }

            if (dogBreed.getLifeSpan() != null){
                tvLifeSpan.setText(dogBreed.getLifeSpan());
            } else {
                lifeSpanContainer.setVisibility(View.GONE);
            }

            if (dogBreed.getTemperament() != null){
                tvTemperament.setText(dogBreed.getTemperament());
            } else {
                temperamentContainer.setVisibility(View.GONE);
            }
        }
    }

    private void mapComponents() {
        ivDogImg = view.findViewById(R.id.iv_dog);
        bredForContainer = view.findViewById(R.id.bred_for_container);
        bredGroupContainer = view.findViewById(R.id.bred_group_container);
        countryCodeContainer = view.findViewById(R.id.country_code_container);
        lifeSpanContainer = view.findViewById(R.id.life_span_container);
        temperamentContainer = view.findViewById(R.id.temperament_container);
        tvDogName = view.findViewById(R.id.tv_dog_name);
        tvBredFor = view.findViewById(R.id.tv_bred_for);
        tvBredGroup = view.findViewById(R.id.tv_bred_group);
        tvCountryCode = view.findViewById(R.id.tv_country_code);
        tvLifeSpan = view.findViewById(R.id.tv_life_span);
        tvTemperament = view.findViewById(R.id.tv_temperament);
    }
}