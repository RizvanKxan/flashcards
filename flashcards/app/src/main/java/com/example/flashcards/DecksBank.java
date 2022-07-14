package com.example.flashcards;
import com.example.flashcards.database.AppDatabase;
import com.example.flashcards.database.dao.IDeckDao;
import com.example.flashcards.database.entity.Deck;
import com.example.flashcards.database.entity.Decks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DecksBank {
    private static DecksBank sBank;
    private final IDeckDao deckDao;
    private final ExecutorService executorService;
    private List<Decks> decksList;
    private List<Deck> deckList;
    private List<Decks> newDecksList;
    private List<Deck> newDeckList;

    private DecksBank(ExecutorService executorService) {
        this.executorService = executorService;
        AppDatabase db = App.getInstance().getDatabase();
        deckDao = db.deckDao();

        decksList = new ArrayList<>();
        newDecksList = new ArrayList<>();
        newDeckList = new ArrayList<>();

        executorService.execute(() -> {
            decksList = deckDao.getAllDecks();
            deckList = deckDao.getAllDeck();
        });

    }

    public static DecksBank get() {
        if(sBank == null) {
            sBank = new DecksBank(Executors.newSingleThreadExecutor());
        }
        return sBank;
    }

    public void addDecks(Decks decks) {
        decksList.add(decks);
        newDecksList.add(decks);
    }

    public void addDeck(Deck deck) {
        deckList.add(deck);
        newDeckList.add(deck);
    }
    public void saveBank() {
        executorService.execute(() -> {
            deckDao.insertListDecks(newDecksList);
            deckDao.insertListDeck(newDeckList);
        });
    }

    public List<Decks> getDecks() {
        return decksList;
    }

    public List<Deck> getAllDeck() {
        return deckList;
    }
}
