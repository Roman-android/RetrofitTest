package ru.roman.retrofittest.libs;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ImgGlide {

    public Context context;

    public ImgGlide(Context context) {
        this.context = context;
    }

    public void showImg(String pathImg, int width, int height, ImageView image) {
        Glide
                .with(context)
                .load(pathImg)
                .apply(new RequestOptions()
                        .override(width, height)
                        .centerCrop()
                        .circleCrop())
                .into(image);
    }

}
