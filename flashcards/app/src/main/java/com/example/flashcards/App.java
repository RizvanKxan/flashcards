package com.example.flashcards;

public class App extends Application {
    public static App INSTANCE;
    private AppDatabase database;

    public static App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        database = Room.databaseBuilder(getApplicationContext(, AppDatabase.class, "database"))
        .fallbackToDestructiveMigration()
        .build();
    }

    public AppDatabase getDatabase() {
        return database;
    }
}