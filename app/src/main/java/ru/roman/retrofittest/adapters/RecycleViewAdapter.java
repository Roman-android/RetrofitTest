package ru.roman.retrofittest.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import ru.roman.retrofittest.R;
import ru.roman.retrofittest.interfaces.OnItemClickListener;
import ru.roman.retrofittest.model.DataModel;


public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    private OnItemClickListener listener;

    private ArrayList <String> mId;
    private ArrayList <String> mCategory;
    private ArrayList<String> mText;
    private ArrayList<String> mFavour;
    private ArrayList<String> mImgPath;

    private Context context;

    public RecycleViewAdapter(DataModel dataModel, Context context, OnItemClickListener listener) {
        this.mId = dataModel.getId();
        this.mCategory = dataModel.getCategory();
        this.mText = dataModel.getText();
        this.mFavour = dataModel.getFavour();
        this.mImgPath = dataModel.getImg();
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);

        final ViewHolder mViewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view, mViewHolder.getAdapterPosition());
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(view,mViewHolder.getAdapterPosition());
                return true;
            }
        });

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String category = mCategory.get(position);
        String text = mText.get(position);
        String img = mImgPath.get(position);

        holder.categoryText.setText(category);
        holder.descText.setText(text);
        holder.imgName.setImageURI(Uri.parse(img));
        Glide
                .with(context)
                .load(img)
                .apply(new RequestOptions()
                        .override(100, 100)
                        .centerCrop()
                        .circleCrop())
                .into(holder.imgName);

    }

    @Override
    public int getItemCount() {
        return mCategory.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryText;
        private TextView descText;
        private ImageView imgName;

        ViewHolder(View itemView) {
            super(itemView);

            categoryText = itemView.findViewById(R.id.categoryText);
            descText = itemView.findViewById(R.id.descText);
            imgName = itemView.findViewById(R.id.imageDownload);

        }
    }

}