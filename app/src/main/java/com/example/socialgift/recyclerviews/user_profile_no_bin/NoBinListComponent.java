package com.example.socialgift.recyclerviews.user_profile_no_bin;

public class NoBinListComponent {
    private int id;
    private int ownerId;
    private String listName;

    public NoBinListComponent(String listName, int id, int ownerId) {
        this.listName = listName;
        this.id = id;
        this.ownerId = ownerId;
    }

    public String getListName(){
        return listName;
    }

    public int getId() {
        return id;
    }

    public int getOwnerId() {
        return ownerId;
    }
}
