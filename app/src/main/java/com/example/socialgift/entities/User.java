package com.example.socialgift.entities;

public class User {

    private int id;
    private String name;
    private String email;
    private String photo;

    public User(int id, String name, String email, String photo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.photo = photo;
    }

    public User(String name, String photo) {
        this(-1, name, null, photo);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() { return email; }

    public String getPhoto() {
        return photo;
    }
}
