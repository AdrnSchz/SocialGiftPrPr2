package com.example.socialgift;

public class ListComponent {

    private String userName;
    private String userEmail;
    private String userImage; // TODO: string?
    private String giftName;
    private String listName;

    public ListComponent(String userName, String userImage, String listName) {
        this.userName = userName;
        this.userImage = userImage;
        this.listName = listName;
        this.giftName = null;
        this.userEmail = null;
    }

    public ListComponent(String userName, String userImage, String userEmail, String giftName, String listName) {
        this(userName, userImage, listName);
        this.userEmail = userEmail;
        this.giftName = giftName;
    }

    public ListComponent(String name, int type) {
        this(null, null, null);
        if (type == 1) {
            giftName = name;
        }
        else {
            listName = name;
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getGiftName() {
        return giftName;
    }

    public String getListName() {
        return listName;
    }
}