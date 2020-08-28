package com.aqar55.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import com.aqar55.R;

public class Custom_Professional_InfoWindowAdpter implements GoogleMap.InfoWindowAdapter {

    private Context context;
    private String professionalId;
    private String category;
    private String professionalName;
    private String image;
//    private ProgressBar pbProfWindow;
    private ImageView ivPropertyImage;

    public Custom_Professional_InfoWindowAdpter(Context context,String professionalId,String category,String professionalName,String image) {
        this.context = context;
        this.professionalId = professionalId;
        this.category = category;
        this.professionalName = professionalName;
        this.image = image;
    }

    @Override
    public View getInfoWindow(Marker marker) {


        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity) context).getLayoutInflater()
                .inflate(R.layout.layout_professional_map, null);

        TextView tvProfessionalName =  view.findViewById(R.id.tvProfessionalName);
        TextView tvProfCategory = view.findViewById(R.id.tvProfCategory);
        TextView tvProfID = view.findViewById(R.id.tvProfID);
        TextView tvListingProf = view.findViewById(R.id.tvListingProf);
        ivPropertyImage = view.findViewById(R.id.ivPropertyImage);
//        pbProfWindow = view.findViewById(R.id.pbProfWindow);


        tvProfID.setText(professionalId);
        tvProfCategory.setText(category);
        tvProfessionalName.setText(professionalName);
        if(!image.isEmpty() && image!=null){
            Picasso.get().load(image).into(ivPropertyImage);

        }else {
            Picasso.get().load(R.drawable.addimagesize).into(ivPropertyImage);
        }

        return view;
    }

//    private void setProfilePic(final String profilePic) {
//        if (profilePic != null && !profilePic.isEmpty()) {
//            pbProfWindow.setVisibility(View.VISIBLE);
//            Glide.with(context)
//                    .load(profilePic)
//                    .into(new SimpleTarget<Drawable>(200,200) {
//                        @Override
//                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                            ivPropertyImage.setImageDrawable(resource);
//                            pbProfWindow.setVisibility(View.GONE);
//                        }
//
//                        @Override
//                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
//                            super.onLoadFailed(errorDrawable);
//                            pbProfWindow.setVisibility(View.GONE);
//                            setProfilePic(profilePic);
//                        }
//                    });
//        }else {
//            pbProfWindow.setVisibility(View.GONE);
//            Glide.with(context)
//                    .load(R.drawable.addimagesize)
//                    .centerCrop()
//                    .into(ivPropertyImage);
//        }
//
//    }



}
