package com.example.flashcards.database.entity;

import com.example.flashcards.App;
import com.google.firebase.auth.FirebaseAuth;

import java.util.UUID;

public class User {
    private static User user;
    private String id;

    public static User get() {
        if(user == null) {
            user = new User();
        }
        return user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
