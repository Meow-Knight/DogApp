package com.example.dogapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dogapp.entites.DogBreed;

import java.util.List;

@Dao
public interface DogBreedDao {
    @Query("SELECT * FROM dogbreed")
    List<DogBreed> getAll();

    @Insert
    void insertAll(DogBreed... dogBreeds);

    @Delete
    void delete(DogBreed dogBreed);

    @Query("DELETE FROM dogbreed")
    void clearDatabase();
}
