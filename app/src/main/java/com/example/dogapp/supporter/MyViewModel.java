package com.example.dogapp.supporter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dogapp.entites.DogBreed;
import com.example.dogapp.service.DogsApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MyViewModel extends AndroidViewModel {
    private Application application;
    private MutableLiveData<List<DogBreed>> dogBreedData;
    private List<DogBreed> allDogBreedList;
    private List<DogBreed> searchingDogBreedList;
    private DogsApiService apiService;


    public MyViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        dogBreedData = new MutableLiveData<>();
        dogBreedData.setValue(new ArrayList<>());
    }

    public List<DogBreed> getAllDogBreed(){
        if(dogBreedData == null){
            initialDogBreedData();
        }
        return dogBreedData.getValue();
    }

    public LiveData<List<DogBreed>> getAllDogBreedLiveData(){
        return dogBreedData;
    }

    public void initialDogBreedData() {
        if (dogBreedData.getValue().size() == 0){
            apiService = new DogsApiService();
            apiService.getAllDogs()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                        @Override
                        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<DogBreed> dogBreeds) {
                            allDogBreedList = dogBreeds;
                            dogBreedData.setValue(dogBreeds);
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        }
                    });
        }
    }

    public void setDataWithQuery(String input) {
        searchingDogBreedList = new ArrayList<>();
        for(DogBreed dogBreed : allDogBreedList){
            if(dogBreed.getName().toLowerCase().contains(input)){
                searchingDogBreedList.add(dogBreed);
            }
        }

        dogBreedData.setValue(searchingDogBreedList);
    }

    public void setDataWithAllDogBreed(){
        dogBreedData.setValue(allDogBreedList);
    }
}
