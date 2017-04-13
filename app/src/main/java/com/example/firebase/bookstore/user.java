package com.example.firebase.bookstore;

/**
 * Created by Mekhal on 4/13/2017.
 */

public class user {

    String name;
    String email;

    public user() {
    }

    public user(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}
