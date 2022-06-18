package com.example.flashcards;

import android.content.Context;

import com.example.flashcards.database.AppDatabase;
import com.example.flashcards.database.dao.FlashCardDao;
import com.example.flashcards.database.entity.FlashCard;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CardsBank {
    private static CardsBank sBank;
    private final FlashCardDao cardsDao;
    private final AppDatabase db;
    private final ExecutorService executorService;

    private CardsBank(ExecutorService executorService){
        this.executorService = executorService;
        db = App.getInstance().getDatabase();
        cardsDao = db.flashCardDao();
    }

    public static CardsBank get() {
        if (sBank == null) {
            sBank = new CardsBank(Executors.newSingleThreadExecutor());
        }
        return sBank;
    }

    public void getCards(Result<List<FlashCard>> listener) {
        executorService.execute(() -> {
            try {
                List<FlashCard> cardsList = cardsDao.getAll();
                listener.onSuccess(cardsList);
            } catch (Exception exception) {
                listener.onError(exception);
            }
        });
    }

    public void addCard(FlashCard card) {
        executorService.execute(() -> cardsDao.insertFlashCard(card));
    }

    public interface Result<T> {
        void onSuccess(T t);

        void onError(Exception exception);
    }
}
