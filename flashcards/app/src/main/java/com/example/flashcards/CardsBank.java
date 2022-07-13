package com.example.flashcards;

import android.content.Context;

import com.example.flashcards.database.AppDatabase;
import com.example.flashcards.database.dao.FlashCardDao;
import com.example.flashcards.database.entity.FlashCard;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CardsBank {
    private static CardsBank sBank;
    private final FlashCardDao cardsDao;
    private final ExecutorService executorService;

    private CardsBank(ExecutorService executorService){
        this.executorService = executorService;
        AppDatabase db = App.getInstance().getDatabase();
        cardsDao = db.flashCardDao();
    }

    public static CardsBank get() {
        if (sBank == null) {
            sBank = new CardsBank(Executors.newSingleThreadExecutor());
        }
        return sBank;
    }

    public void getCard(Result<FlashCard> listener, long id) {
        executorService.execute(() -> {
            try {
                FlashCard card = cardsDao.getById(id);
                listener.onSuccess(card);
            } catch (Exception exception) {
                listener.onError(exception);
            }
        });
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

    public void updateCard(FlashCard card) {
        executorService.execute(()-> cardsDao.updateFlashCard(card));
    }

    public interface Result<T> {
        void onSuccess(T t);

        void onError(Exception exception);
    }
}
