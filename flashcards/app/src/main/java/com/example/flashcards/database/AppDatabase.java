package com.example.flashcards.database;

@Database(entities = {FlashCard.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public abstract FlashCardDao flashCardDao();
}
