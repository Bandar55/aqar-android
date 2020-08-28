package com.aqar55.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.aqar55.R;
import com.aqar55.databinding.LayoutVideoViewBinding;

public class Add_Media_Adapter extends RecyclerView.Adapter<Add_Media_Adapter.MyViewHolder> {
    private LayoutVideoViewBinding layoutVideoViewBinding;
    private LayoutInflater inflater;
    private Context context;
    private List<File> imagefiles;
    private String string;

    public Add_Media_Adapter(Context context, List<File> imagefiles, String string) {
        this.context = context;
        this.imagefiles = imagefiles;
        this.string = string;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        layoutVideoViewBinding = DataBindingUtil.inflate(inflater, R.layout.layout_video_view, viewGroup, false);
        return new MyViewHolder(layoutVideoViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.layoutVideoViewBinding.videoView.setVideoURI(Uri.fromFile(imagefiles.get(i)));
    }

    public void updateList(ArrayList<File> stringList, String data) {
        imagefiles = stringList;
        string = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return imagefiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private LayoutVideoViewBinding layoutVideoViewBinding;

        public MyViewHolder(@NonNull LayoutVideoViewBinding layoutVideoViewBinding) {
            super(layoutVideoViewBinding.getRoot());

            this.layoutVideoViewBinding = layoutVideoViewBinding;
        }
    }

}


