package ru.roman.retrofittest.model;

import java.util.ArrayList;

public class DataModel {

    private ArrayList<String> id = new ArrayList<>();
    private ArrayList<String> category = new ArrayList<>();
    private ArrayList<String> text= new ArrayList<>();
    private ArrayList<String> favour = new ArrayList<>();
    private ArrayList<String> img = new ArrayList<>();
    private String fragmentName ="";

    public void clearDataModel(){
        if (id.size()>0) {
            id.clear();
        }
        if (category.size()>0) {
            category.clear();
        }
        if (text.size()>0){
            text.clear();
        }
        if (favour.size()>0){
            favour.clear();
        }
        if (img.size()>0){
            img.clear();
        }
        if (!fragmentName.equals("")){
            fragmentName = "";
        }
    }

    public ArrayList<String> getId() {
        return id;
    }

    public void setId(String id) {
        this.id.add(id);
    }

    public ArrayList<String> getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category.add(category);
    }

    public ArrayList<String> getText() {
        return text;
    }

    public void setText(String text) {
        this.text.add(text);
    }

    public ArrayList<String> getFavour() {
        return favour;
    }

    public void setFavour(String favour) {
        this.favour.add(favour);
    }

    public ArrayList<String> getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img.add(img);
    }

    public String getFragmentName() {
        return fragmentName;
    }

    public void setFragmentName(String fragmentName) {
        this.fragmentName = fragmentName;
    }
}
