package com.aqar55.activitys;
import com.squareup.picasso.Callback;


import com.google.android.gms.maps.model.Marker;

public class InfoWindowRefresher implements Callback {
    private Marker markerToRefresh;

    public InfoWindowRefresher(Marker markerToRefresh) {
        this.markerToRefresh = markerToRefresh;
    }

    @Override
    public void onSuccess() {
        markerToRefresh.showInfoWindow();
    }

    @Override
    public void onError(Exception e) {

    }

}