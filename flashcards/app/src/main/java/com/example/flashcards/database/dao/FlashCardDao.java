package com.example.flashcards.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface FlashCardDao {
    @Query("SELECT * FROM flash_cards")
    List<FlashCard> getAll();

    @Query("SELECT * FROM flash_cards WHERE id = :id")
    FlashCard getById(id);

    @Insert(onConflict = REPLACE)
    void insertFlashCard(FlashCard card);

    @Delete
    void deleteFlashCard(long id);

    @Update
    void updateFlashCard(FlashCard card);
}
