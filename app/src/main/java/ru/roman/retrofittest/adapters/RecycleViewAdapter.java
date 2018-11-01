package ru.roman.retrofittest.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import ru.roman.retrofittest.MainActivity;
import ru.roman.retrofittest.R;


public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.mViewHolder> {

    private ArrayMap<String,String> mCategoryText;
    private ArrayList<String> mImgName;

    Context context;

    public RecycleViewAdapter(ArrayMap<String,String> categoryText, ArrayList<String>imgName, Context context) {
        this.mCategoryText = categoryText;
        this.mImgName = imgName;
        this.context=context;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        String desc = mCategoryText.valueAt(position);
        String img = mImgName.get(position);

        holder.categoryText.setText(desc);
        holder.imgName.setImageURI(Uri.parse(img));

        Glide
                .with(context)
                .load(img)
                .apply(new RequestOptions()
                        .override(100, 100)
                        .centerCrop())
                .into(holder.imgName);

    }

    @Override
    public int getItemCount() {
        return mCategoryText.size();
    }

    public static class mViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryText;
        private ImageView imgName;

        public mViewHolder(View itemView) {
            super(itemView);

            categoryText = itemView.findViewById(R.id.categoryText);
            imgName = itemView.findViewById(R.id.imageDownload);
        }
    }

}
