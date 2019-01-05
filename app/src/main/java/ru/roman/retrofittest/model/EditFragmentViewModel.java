package ru.roman.retrofittest.model;

import android.arch.lifecycle.ViewModel;

public class EditFragmentViewModel extends ViewModel {

    private String imagePath;
    private int requestCodeChoiceImg = 200;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getRequestCodeChoiceImg() {
        return requestCodeChoiceImg;
    }

    public void setRequestCodeChoiceImg(int requestCodeChoiceImg) {
        this.requestCodeChoiceImg = requestCodeChoiceImg;
    }
}
