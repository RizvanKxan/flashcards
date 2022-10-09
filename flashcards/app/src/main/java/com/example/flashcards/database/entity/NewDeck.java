package com.example.flashcards.database.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

public class NewDeck {

    public boolean isGlobal;
    private String name;

    private String userID;

    public NewDeck() {
    }

    public NewDeck(String name) {
        this.name = name;
        isGlobal = false;
        userID = User.get().getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
