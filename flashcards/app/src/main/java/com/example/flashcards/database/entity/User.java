package com.example.flashcards.database.entity;

import com.example.flashcards.App;
import com.google.firebase.auth.FirebaseAuth;

import java.util.UUID;

public class User {
    private static User user;
    private String id;
    private String name;

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

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        User.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
