package com.aqar55.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.aqar55.R;
import com.aqar55.model.StataticContent;

public class SliderAdapter extends PagerAdapter {

    private static final String TAG = "SliderAdapter";
    private Context context;
    private TextView title, desc;
    private  List<StataticContent.Data> data;

    public SliderAdapter(Context context, List<StataticContent.Data> data) {
        this.context = context;
        this.data=data;

    }



    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_slider_adapter_intro, null);
        title = view.findViewById(R.id.introdetailtexttitle);
        desc = view.findViewById(R.id.intro1detailtext);


        title.setText(data.get(position).getTitle());
        desc.setText(data.get(position).getDescription());

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view.getRootView(), 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}