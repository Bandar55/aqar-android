package com.aqar55.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import com.aqar55.R;
import com.aqar55.databinding.AddImageLayoutNewBinding;
import com.aqar55.model.AddImageModel;


public class Add_Image_Adapter extends RecyclerView.Adapter<Add_Image_Adapter.MyViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<AddImageModel> imageList;
    private Context context;
    private AddImageLayoutNewBinding addImageLayoutNewBinding;

    public Add_Image_Adapter(Context context, ArrayList<AddImageModel> bitmaps) {
        imageList = bitmaps;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        addImageLayoutNewBinding = DataBindingUtil.inflate(inflater, R.layout.add_image_layout_new, viewGroup, false);
        return new MyViewHolder(addImageLayoutNewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        holder.BindData(imageList);

        holder.addImageLayoutNewBinding.ivAddPlusSign.setImageURI(Uri.fromFile(imageList.get(i).getFile().getAbsoluteFile()));







        // Glide.with(context).load(imageList.get(i).getFile()).into(holder.addImageLayoutNewBinding.ivAddPlusSign);
        holder.addImageLayoutNewBinding.tvDescriptionImage.setText(imageList.get(i).getTitle());

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public void updateList(ArrayList<AddImageModel> stringList) {
        imageList = stringList;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AddImageLayoutNewBinding addImageLayoutNewBinding;
        private ArrayList<AddImageModel> addImageModel;

        MyViewHolder(@NonNull AddImageLayoutNewBinding addImageLayoutNewBinding) {
            super(addImageLayoutNewBinding.getRoot());
            this.addImageLayoutNewBinding = addImageLayoutNewBinding;
            this.addImageLayoutNewBinding.ivClose.setOnClickListener(this);
        }

        public void BindData(ArrayList<AddImageModel> addImageModel) {
            this.addImageModel=addImageModel;

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_close:

                    if(addImageModel!=null&&addImageModel.size()>0)
                    {
                        addImageModel.remove(getAdapterPosition());
                        notifyDataSetChanged();
                    }
                    break;
            }

        }
    }
}

