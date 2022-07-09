package com.example.flashcards.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.flashcards.database.entity.Deck;

import java.util.List;

@Dao
public interface IDeckDao {

    @Query("SELECT * FROM deck")
    List<Deck> getAllDecks();

    @Query("SELECT * FROM deck WHERE id = :id")
    Deck getDeckById(long id);

    @Insert(onConflict = REPLACE)
    void insertDeck(Deck deck);

}
