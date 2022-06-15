package com.example.flashcards.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.flashcards.database.entity.FlashCard;

import java.util.List;

@Dao
public interface FlashCardDao {
    @Query("SELECT * FROM flash_cards")
    List<FlashCard> getAll();

    @Query("SELECT * FROM flash_cards WHERE id = :id")
    FlashCard getById(long id);

    @Insert(onConflict = REPLACE)
    void insertFlashCard(FlashCard card);

    @Delete
    void deleteFlashCard(FlashCard card);

    @Update
    void updateFlashCard(FlashCard card);
}
