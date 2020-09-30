package com.example.dogapp.supporter;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dogapp.dao.DogBreedDao;
import com.example.dogapp.dao.DogBreedDatabase;
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

    private DogBreedDao dogBreedDao;

    private boolean isLoaded = false;


    public MyViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        dogBreedData = new MutableLiveData<>();

        allDogBreedList = new ArrayList<>();
        dogBreedData.setValue(allDogBreedList);
        dogBreedDao = DogBreedDatabase.getInstance(application.getApplicationContext()).dogBreedDao();
        loadAllDogBreedDataFromDatabase();
    }

    public void loadAllDogBreedDataFromDatabase(){
        AsyncTask.execute(() -> {
            List<DogBreed> tmp = dogBreedDao.getAll();
            if (tmp.size() == 0){
                loadDogBreedDataFromUrl();
            } else {
                allDogBreedList = tmp;
            }
        });
        new Handler().postDelayed(() -> dogBreedData.setValue(allDogBreedList), 2000);
    }

    public LiveData<List<DogBreed>> getAllDogBreedLiveData(){
        return dogBreedData;
    }

    public void loadDogBreedDataFromUrl() {
        isLoaded = false;
        apiService = new DogsApiService();
        apiService.getAllDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<DogBreed> dogBreeds) {
                        allDogBreedList = dogBreeds;
                        dogBreedData.setValue(dogBreeds);

                        AsyncTask.execute(() -> {
                            dogBreedDao.clearDatabase();
                            for(DogBreed dogBreed : allDogBreedList){
                                dogBreedDao.insertAll(dogBreed);
                            }
                        });

                        isLoaded = true;
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        isLoaded = true;
                    }
                });
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

    public void refreshData() {
        loadDogBreedDataFromUrl();
    }

    public boolean isLoaded(){
        return isLoaded;
    }
}
