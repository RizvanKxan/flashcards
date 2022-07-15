package com.example.flashcards.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.flashcards.database.entity.Deck;
import com.example.flashcards.database.entity.Decks;
import com.example.flashcards.database.entity.FlashCard;

import java.util.List;
import java.util.UUID;

@Dao
public interface IDeckDao {

    @Query("SELECT * FROM decks")
    List<Decks> getAllDecks();

    @Query("SELECT * FROM deck")
    List<Deck> getAllDeck();

    @Query("SELECT * FROM deck WHERE id = :id")
    Decks getDeckById(UUID id);

    @Insert(onConflict = REPLACE)
    void insertDeck(Deck deck);

    @Insert(onConflict = REPLACE)
    void insertListDecks(List<Decks> decks);

    @Insert(onConflict = REPLACE)
    void insertListDeck(List<Deck> deck);

    @Delete
    void deleteDecks(Decks decks);
}
