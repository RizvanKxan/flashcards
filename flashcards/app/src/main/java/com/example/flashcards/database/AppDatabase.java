package com.example.flashcards.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.flashcards.database.dao.FlashCardDao;
import com.example.flashcards.database.entity.FlashCard;

@Database(entities = {FlashCard.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FlashCardDao flashCardDao();
}
