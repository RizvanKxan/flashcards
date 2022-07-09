package com.example.flashcards.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.flashcards.database.dao.FlashCardDao;
import com.example.flashcards.database.dao.IDeckDao;
import com.example.flashcards.database.entity.Deck;
import com.example.flashcards.database.entity.Decks;
import com.example.flashcards.database.entity.FlashCard;

@Database(entities = {FlashCard.class, Decks.class, Deck.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FlashCardDao flashCardDao();
    public abstract IDeckDao deckDao();
}