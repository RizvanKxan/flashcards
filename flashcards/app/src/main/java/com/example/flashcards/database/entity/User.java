package com.example.flashcards.database.entity;

import com.example.flashcards.App;
import com.google.firebase.auth.FirebaseAuth;

import java.util.UUID;

public class User {
    private static User user;
    private String id;
    private String name;
    private int allSwipe;
    private int knowSwipe;
    private int dontKnowSwipe;

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

    public int getAllSwipe() {
        return allSwipe;
    }

    public void setAllSwipe(int allSwipe) {
        this.allSwipe = allSwipe;
    }

    public int getKnowSwipe() {
        return knowSwipe;
    }

    public void setKnowSwipe(int knowSwipe) {
        this.knowSwipe = knowSwipe;
    }

    public int getDontKnowSwipe() {
        return dontKnowSwipe;
    }

    public void setDontKnowSwipe(int dontKnowSwipe) {
        this.dontKnowSwipe = dontKnowSwipe;
    }
}
