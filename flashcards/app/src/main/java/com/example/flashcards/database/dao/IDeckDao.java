package com.example.flashcards.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.flashcards.database.entity.Deck;
import com.example.flashcards.database.entity.Decks;

import java.util.List;

@Dao
public interface IDeckDao {

    @Query("SELECT * FROM decks")
    List<Decks> getAllDecks();

    @Query("SELECT * FROM deck")
    List<Deck> getAllDeck();

    @Query("SELECT * FROM deck WHERE id = :id")
    Decks getDeckById(long id);

    @Insert(onConflict = REPLACE)
    void insertDeck(Decks decks);

    @Insert(onConflict = REPLACE)
    void insertListDeck(List<Decks> decks);

}
