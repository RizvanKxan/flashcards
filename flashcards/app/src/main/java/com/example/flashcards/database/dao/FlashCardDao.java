package com.example.flashcards.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.flashcards.database.entity.FlashCard;

import java.util.List;
import java.util.UUID;

@Dao
public interface FlashCardDao {

    @Query("SELECT * FROM flash_cards")
    List<FlashCard> getAll();

    //ToDo переписать в будущем на LiveData
    @Query("SELECT * FROM flash_cards")
    LiveData<List<FlashCard>> getAllLiveData();

    @Query("SELECT * FROM flash_cards WHERE id = :id")
    FlashCard getById(UUID id);

    @Insert(onConflict = REPLACE)
    void insertFlashCard(FlashCard card);

    @Delete
    void deleteFlashCard(FlashCard card);

    @Update
    void updateFlashCard(FlashCard card);
}
