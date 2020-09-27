package com.example.dogapp.entites;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class DogBreed implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("bred_for")
    private String bredFor;

    @SerializedName("breed_group")
    private String breadGroup;

    @SerializedName("country_code")
    private String countryCode;

    @SerializedName("life_span")
    private String lifeSpan;

    @SerializedName("url")
    private String url;

    @SerializedName("temperament")
    private String temperament;

    private Bitmap bitmap;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getBredFor() {
        return bredFor;
    }

    public void setBreadFor(String bredFor) {
        this.bredFor = bredFor;
    }

    public String getBreadGroup() {
        return breadGroup;
    }

    public void setBreadGroup(String breadGroup) {
        this.breadGroup = breadGroup;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(String lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public void setBredFor(String bredFor) {
        this.bredFor = bredFor;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
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
