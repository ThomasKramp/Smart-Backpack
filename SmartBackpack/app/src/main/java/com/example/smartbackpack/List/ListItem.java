package com.example.smartbackpack.List;

import android.graphics.Bitmap;

public class ListItem {
    private Bitmap image;
    private String name;
    private int amount;

    public ListItem(Bitmap image, String name, int amount){
        this.image = image;
        this.name = name;
        this.amount = amount;
    }

    public Bitmap getImage() { return image; }
    public String getName() { return name; }
    public int getAmount() { return amount; }

    public void setImage(Bitmap image) { this.image = image; }
    public void setName(String name) {
        this.name = name;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
