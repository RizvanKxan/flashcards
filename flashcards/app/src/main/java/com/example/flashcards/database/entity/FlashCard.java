package com.example.flashcards.database.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "flash_cards")
public class FlashCard {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String word;
    private String value;

    public FlashCard(String word, String value) {
        this.word = word;
        this.value = value;
    }
    
    
}
