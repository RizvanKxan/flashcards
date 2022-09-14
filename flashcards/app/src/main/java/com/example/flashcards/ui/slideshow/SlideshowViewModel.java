package com.example.flashcards.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.flashcards.CardsBank;
import com.example.flashcards.DecksBank;
import com.example.flashcards.database.entity.Deck;
import com.example.flashcards.database.entity.FlashCard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SlideshowViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<List<FlashCard>> mCards;
    private MutableLiveData<List<FlashCard>> mCardsDeck;
    //позиция текущей карточки
    private int currentCardPosition = -1;
    private UUID currentDeckId;
    private boolean isValueShown = false;

    public SlideshowViewModel() {
        mCards = new MutableLiveData<>();
        List<FlashCard> cards = CardsBank.get().getCards();
        mCards.setValue(cards);

        mText = new MutableLiveData<>();
        mText.setValue("Смахните карту в одну из сторон.");
    }

    public boolean isValueShown() {
        return isValueShown;
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void swipe() {
        currentCardPosition = currentCardPosition + 1;
        if (currentCardPosition < mCards.getValue().size()) {
            mText.setValue(mCards.getValue().get(currentCardPosition).getWord());
            isValueShown = false;
        } else {
            mText.setValue("Карточек больше нет.");
        }

    }

    public void getValue() {
        isValueShown = true;
        if (currentCardPosition == -1) return;
        if (currentCardPosition < mCards.getValue().size()) {
            mText.setValue(mCards.getValue().get(currentCardPosition).getValue());
        } else {
            mText.setValue("Пока больше нечего показать..");
        }
    }

    public void getWord() {
        isValueShown = false;
        if (currentCardPosition == -1) return;
        if (currentCardPosition < mCards.getValue().size()) {
            mText.setValue(mCards.getValue().get(currentCardPosition).getWord());
        } else {
            mText.setValue("Пока больше нечего показать..");
        }
    }

    public void setDeckId(UUID deckId) {
        if(deckId != null) {
            currentDeckId = deckId;
            List<Deck> deck;
            deck = DecksBank.get().getAllDeckId(deckId);
            List<UUID> carddd = new ArrayList<>();
            deck.forEach((x) -> carddd.add(x.getCardID()));
            List<FlashCard> cards = new ArrayList<>();
            carddd.forEach((x) -> cards.add(CardsBank.get().getCard(x)));
            mCards.setValue(cards);
        } else {
            List<FlashCard> cards = CardsBank.get().getCards();
            mCards.setValue(cards);
        }
    }
}