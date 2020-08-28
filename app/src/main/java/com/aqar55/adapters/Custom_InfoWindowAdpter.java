package com.aqar55.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import com.aqar55.R;

public class Custom_InfoWindowAdpter implements GoogleMap.InfoWindowAdapter  {

    private Context context;
    private String businessId;
    private String category;
    private String businessName;
    private String image;

    public Custom_InfoWindowAdpter(Context context, String businessId, String category, String businessName, String image) {
        this.context = context;
        this.businessId = businessId;
        this.category = category;
        this.businessName = businessName;
        this.image = image;
    }

    @Override
    public View getInfoWindow(Marker marker) {


        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity) context).getLayoutInflater()
                .inflate(R.layout.layout_include_map, null);
        ImageView ivPropertyImageBusiness = view.findViewById(R.id.ivPropertyImageBusiness);
        TextView tvBusinessName = view.findViewById(R.id.tvBusinessName);
        TextView tvBusinesssCategory = view.findViewById(R.id.tvBusinesssCategory);
        TextView tvBusinessDetails = view.findViewById(R.id.tvBusinessDetails);
        TextView tvBusinessId = view.findViewById(R.id.tvBusinessId);

        Glide.with(context).load(image).into(ivPropertyImageBusiness);
        tvBusinessName.setText(businessName);
        tvBusinesssCategory.setText(category);
        tvBusinessId.setText(businessId);

        return view;
    }
}
