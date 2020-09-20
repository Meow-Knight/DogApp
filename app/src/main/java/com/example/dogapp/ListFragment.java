package com.example.dogapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.dogapp.service.DogsApiService;
import com.example.dogapp.supporter.DogListAdapter;
import com.example.dogapp.supporter.MyViewModel;

public class ListFragment extends Fragment {
    // supporters
    private DogListAdapter adapter;
    private MyViewModel viewModel;

    // components
    private RecyclerView rvDogBreeds;

    // values
    private final int SPAN_NUMBER = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapComponents();
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        if(viewModel.getAllDogBreed() == null){
            new Handler().postDelayed(() ->{
                adapter = new DogListAdapter(view.getContext(), viewModel.getAllDogBreed());
                rvDogBreeds.setAdapter(adapter);
            }, 1000);
        } else {
            adapter = new DogListAdapter(view.getContext(), viewModel.getAllDogBreed());
            rvDogBreeds.setAdapter(adapter);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), SPAN_NUMBER, GridLayoutManager.VERTICAL, false);
        rvDogBreeds.setLayoutManager(gridLayoutManager);
    }

    private void mapComponents() {
        rvDogBreeds = getView().findViewById(R.id.rv_dog_list);
    }
}