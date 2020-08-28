package com.aqar55.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.aqar55.R;
import com.aqar55.model.GetPropertyDetail;


public class product_detail_slider_adapter extends PagerAdapter {
    private Context context;
    private List<GetPropertyDetail.Imagesfile> imagesfile;
    public product_detail_slider_adapter(Context context, List<GetPropertyDetail.Imagesfile> imagesfile) {
        this.context = context;
        this.imagesfile = imagesfile;
    }

    @Override
    public int getCount() {
        if(imagesfile!=null)

        return imagesfile.size();
        else
            return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_property_detail_view_pager, null);
        ImageView imageView =view.findViewById(R.id.image_property_detail);

        if(imagesfile.get(position).getImage()!=null && !imagesfile.get(position).getImage().isEmpty())
            Glide.with(context).load(imagesfile.get(position).getImage()).into(imageView);
        else
            Glide.with(context).load(R.color.placeholder_gray).into(imageView);

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}