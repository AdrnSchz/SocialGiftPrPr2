package com.example.socialgift.entities;

public class User {

    private static int id;
    private static String name;
    private static String surname;
    private static String email;
    private static String imageLink;

    public static int id(){
        return id;
    }

    public static String getName(){
        return name;
    }

    public static String getSurname(){
        return surname;
    }

    public static String getEmail(){
        return email;
    }

    public static String getImageLink(){
        return imageLink;
    }

    public static void updateLoggedInUser(int newId, String newName, String newSurname, String newEmail){
        id = newId;
        name = newName;
        surname = newSurname;
        email = newEmail;
    }
}
