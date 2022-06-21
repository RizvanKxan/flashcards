package com.example.flashcards.database.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "flash_cards")
public class FlashCard {
    @Ignore
    public boolean isActive;
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String word;
    private String value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
