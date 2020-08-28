package com.aqar55.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LocationResponse {
    @SerializedName("coordinates")
    private ArrayList<Double> coordinates;

    public ArrayList<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Double> coordinates) {
        this.coordinates = coordinates;
    }
}
