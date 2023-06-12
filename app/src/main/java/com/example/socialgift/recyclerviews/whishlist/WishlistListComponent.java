package com.example.socialgift.recyclerviews.whishlist;

public class WishlistListComponent {

    private String giftName;
    private int priority;
    private boolean booked;
    private int id;

    public WishlistListComponent(String giftName, int priority, int booked, int id) {
        this.giftName = giftName;
        this.priority = priority;
        this.booked = booked == 1;
        this.id = id;
    }

    public String getGiftName() {
        return giftName;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isBooked() {
        return booked;
    }

    public int getId() {
        return id;
    }
}