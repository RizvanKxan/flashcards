package com.example.flashcards.database.entity;

import com.example.flashcards.App;
import com.google.firebase.auth.FirebaseAuth;

import java.util.UUID;

public class User {
    public static long counterIdDecks = 0;
    private static User user;
    private String id;

    public static User get() {
        if(user == null) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            user = new User(userId);

        }
        return user;
    }
    private User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
