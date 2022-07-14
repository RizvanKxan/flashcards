package com.example.flashcards.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "flash_cards")
public class FlashCard {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id")
    private UUID id = UUID.randomUUID();

    @ColumnInfo(name = "word")
    private String word;

    @ColumnInfo(name = "value")
    private String value;

    @NonNull
    public UUID getId() {
        return id;
    }

    public void setId(@NonNull UUID id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FlashCard(String word, String value) {
        this.word = word;
        this.value = value;
    }
}
