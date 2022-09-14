package com.example.flashcards;

import com.example.flashcards.database.AppDatabase;
import com.example.flashcards.database.dao.FlashCardDao;
import com.example.flashcards.database.entity.FlashCard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class CardsBank {
    private static CardsBank sBank;
    private final FlashCardDao cardsDao;
    private final ExecutorService executorService;
    private List<FlashCard> cardList;
    private final List<FlashCard> newCardList;

    private CardsBank(ExecutorService executorService){
        this.executorService = executorService;
        AppDatabase db = App.getInstance().getDatabase();
        cardsDao = db.flashCardDao();

        cardList = new ArrayList<>();
        newCardList = new ArrayList<>();
    }

    public static CardsBank get() {
        if (sBank == null) {
            sBank = new CardsBank(Executors.newSingleThreadExecutor());
        }
        return sBank;
    }

    public void openBank() {
        executorService.execute(() -> {
            cardList = cardsDao.getAll();
        });
    }

    public void saveBank() {
        executorService.execute(() -> {
            cardsDao.insertListFlashCard(newCardList);
        });
    }

    public FlashCard getCard(UUID id) {
        List<FlashCard> cards = cardList.stream().filter(s -> s.getId().equals(id)).collect(Collectors.toList());
        FlashCard card;
        if(cards.size()>0) {
            card = cards.get(0);
            return card;
        }
        return null;
    }

    public List<FlashCard> getCards() {
        return cardList;
    }

    public void addCard(FlashCard card) {
        cardList.add(card);
        newCardList.add(card);
    }

    public void updateCard(FlashCard card) {
        UUID cardId = card.getId();
        List<FlashCard> cards = cardList.stream().filter(s -> s.getId().equals(cardId)).collect(Collectors.toList());
        FlashCard card1;
        if(cards.size() > 0) {
            card1 = cards.get(0);
            card1.setWord(card.getWord());
            card1.setValue(card.getValue());
        }
        cards = newCardList.stream().filter(s -> s.getId().equals(cardId)).collect(Collectors.toList());
        FlashCard card2;
        if(cards.size() > 0) {
            card2 = cards.get(0);
            card2.setWord(card.getWord());
            card2.setValue(card.getValue());
        }
        executorService.execute(()-> {
            cardsDao.updateFlashCard(card);
        });
    }

    public void deleteCard(FlashCard mCard) {
        cardList.remove(mCard);
        newCardList.remove(mCard);
        executorService.execute(()-> {
            cardsDao.deleteFlashCard(mCard);
        });
    }
}
