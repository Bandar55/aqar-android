package com.aqar55.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aqar55.R;

public class TinyArrayAdapter<T> extends ArrayAdapter<T> {

    public TinyArrayAdapter(@NonNull Context context, int resource, @NonNull T[] objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = super.getView(position,convertView,parent);
        v.setPadding(0,0,0,0);
        Typeface face = Typeface.DEFAULT;
        ((TextView)v).setTypeface(face);
        ((TextView)v).setGravity(Gravity.CENTER);
        v.setPadding(0,0,0,0);

        //((TextView)v).setTextSize(getContext().getResources().getDimension(R.dimen.text_size_regular));

        ((TextView)v).setTextColor(getContext().getResources().getColor(R.color.dark_text_color));
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position,convertView,parent);
        //we know that simple_spinne_item has android.R.id.text1 Textview
        TextView textView = view.findViewById(android.R.id.text1);
        Typeface face = Typeface.DEFAULT;
        textView.setTypeface(face);
        textView.setGravity(Gravity.CENTER);
        // textView.setTextSize(getContext().getResources().getDimension(R.dimen.text_size_regular));
        textView.setTextColor(getContext().getResources().getColor(R.color.dark_text_color));

        return view;
    }
}
