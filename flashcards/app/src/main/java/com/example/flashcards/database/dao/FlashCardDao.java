package com.example.flashcards.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface FlashCardDao {
    @Query("SELECT * FROM flash_cards")
    List<FlashCard> getAll();
}
