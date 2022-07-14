package com.example.flashcards.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "decks")
public class Decks {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id")
    private UUID id = UUID.randomUUID();

    @ColumnInfo(name = "name")
    private String name;

    public Decks(String name) {
        this.name = name;
    }

    @NonNull
    public UUID getId() {
        return id;
    }

    public void setId(@NonNull UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
