package com.example.smartbackpack.List;

public class ListItem {
    private int imageResource;
    private String name;
    private int amount;

    public ListItem(int imageResource, String name, int amount){
        this.imageResource = imageResource;
        this.name = name;
        this.amount = amount;
    }

    public int getImageResource() { return imageResource; }
    public String getName() { return name; }
    public int getAmount() { return amount; }
}
