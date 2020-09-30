package com.example.dogapp.entites;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class DogBreed implements Serializable {

    @PrimaryKey
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String name;

    @ColumnInfo(name = "bredFor")
    @SerializedName("bred_for")
    private String bredFor;

    @ColumnInfo
    @SerializedName("breed_group")
    private String breadGroup;

    @ColumnInfo
    @SerializedName("country_code")
    private String countryCode;

    @ColumnInfo
    @SerializedName("life_span")
    private String lifeSpan;

    @ColumnInfo
    @SerializedName("url")
    private String url;

    @ColumnInfo
    @SerializedName("temperament")
    private String temperament;

    @ColumnInfo
    private boolean isShowingDraft;

    public DogBreed(int id, String name, String bredFor, String breadGroup, String countryCode, String lifeSpan, String url, String temperament) {
        this.id = id;
        this.name = name;
        this.bredFor = bredFor;
        this.breadGroup = breadGroup;
        this.countryCode = countryCode;
        this.lifeSpan = lifeSpan;
        this.url = url;
        this.temperament = temperament;
        this.isShowingDraft = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getBredFor() {
        return bredFor;
    }

    public String getBreadGroup() {
        return breadGroup;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public String getUrl() {
        return url;
    }

    public String getTemperament() {
        return temperament;
    }

    public boolean isShowingDraft() {
        return isShowingDraft;
    }

    public void setShowingDraft(boolean showingDetail) {
        isShowingDraft = showingDetail;
    }

    @Override
    public String toString() {
        return "DogBreed{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", breadFor='" + bredFor + '\'' +
                ", breadGroup='" + breadGroup + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", lifeSpan='" + lifeSpan + '\'' +
                ", url='" + url + '\'' +
                ", temperament='" + temperament + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DogBreed dogBreed = (DogBreed) o;
        return id == dogBreed.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
