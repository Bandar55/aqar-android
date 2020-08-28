package com.aqar55.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import com.aqar55.R;
import com.aqar55.model.ProfessionalDataResponse;

public class SpinnerCategoryList  extends ArrayAdapter<ProfessionalDataResponse>

    {

        private Context context;
        private List<ProfessionalDataResponse> values;

    public SpinnerCategoryList(Activity context, int textViewResourceId, List<ProfessionalDataResponse> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values =  values;
    }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

        // And here is when the "chooser" is popped up
        // Normally is the same view, but you can customize it if you want
        @Override
        public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        convertView = mInflater.inflate(R.layout.property_spinner_item, parent ,false);

        TextView label = convertView.findViewById(R.id.spinner_text);
        label.setText(values.get(position).getName());

        return convertView;
    }

    }

