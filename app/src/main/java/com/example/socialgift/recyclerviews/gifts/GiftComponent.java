package com.example.socialgift.recyclerviews.gifts;

public class GiftComponent {

    private int id;
    private String giftName, descritiption, link, image;
    private double price;

    public GiftComponent(int id, String giftName, String descritiption, String link, String Image, Double price) {
        this.id = id;
        this.giftName = giftName;
        this.descritiption = descritiption;
        this.link = link;
        this.image = image;
        this.price = price;
    }

    public String getGiftName() {
        return giftName;
    }

    public int getId() {
        return id;
    }

    public String getDescritiption() {
        return descritiption;
    }

    public String getLink() {
        return link;
    }

    public String getImage() {
        return image;
    }

    public Double getPrice() {
        return price;
    }
}
