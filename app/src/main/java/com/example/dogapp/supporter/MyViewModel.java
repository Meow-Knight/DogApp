package com.example.dogapp.supporter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.dogapp.entites.DogBreed;
import com.example.dogapp.service.DogsApiService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MyViewModel extends AndroidViewModel {
    private Application application;
    private MutableLiveData<List<DogBreed>> allDogBreedData;
    private DogsApiService apiService;


    public MyViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public List<DogBreed> getAllDogBreed(){
        if(allDogBreedData == null){
            initialDogBreedData();
        }
        return allDogBreedData.getValue();
    }

    private void initialDogBreedData() {
        allDogBreedData = new MutableLiveData<>();
        apiService = new DogsApiService();
        apiService.getAllDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<DogBreed> dogBreeds) {
                        allDogBreedData.setValue(dogBreeds);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                    }
                });
    }
}