package com.example.socialgift.recyclerviews.user_profile;

public class UserProfileListComponent {

    private String listName;
    private String id;

    public UserProfileListComponent(String listName, String id){
        this.listName = listName;
        this.id = id;
    }
    public String getListName(){
        return listName;
    }
    public String getId(){
        return id;
    }
}
