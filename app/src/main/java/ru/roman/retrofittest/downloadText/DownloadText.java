package ru.roman.retrofittest.downloadText;

import android.support.v4.util.ArrayMap;

public class DownloadText {

    private String id;
    private String category;
    private String text;
    private String img_name;
    private String favour;

    private ArrayMap <String,String> idAndCategoryArray;

    public DownloadText() {
    }



    public String getImg_name() {
        return img_name;
    }
    public String getId() {
        return id;
    }
    public String getCategory() {
        return category;
    }
    public String getText() {
        return text;
    }
    public String getFavour() {
        return favour;
    }

}
