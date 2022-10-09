package com.example.flashcards.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.flashcards.CardsBank;
import com.example.flashcards.DecksBank;
import com.example.flashcards.NewCardsBank;
import com.example.flashcards.database.entity.Card;
import com.example.flashcards.database.entity.Deck;
import com.example.flashcards.database.entity.FlashCard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SlideshowViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<List<Card>> mCards;
    private MutableLiveData<List<Card>> mCardsDeck;
    //позиция текущей карточки
    private int currentCardPosition = -1;
    private String currentDeckId;
    private boolean isValueShown = false;

    public SlideshowViewModel() {
        mCards = new MutableLiveData<>();
        List<Card> cards = NewCardsBank.get().getCardsList();
        mCards.setValue(cards);

        mText = new MutableLiveData<>();
        mText.setValue("Смахните влево, если не помните значение. Или вправо, если помните.");
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

    public void setDeckId(String deckId) {
        if(deckId != null) {
            currentDeckId = deckId;
            List<Card> cards = new ArrayList<>();
            cards = NewCardsBank.get().getCardsDeck(deckId);
            mCards.setValue(cards);
        } else {
            List<Card> cards = NewCardsBank.get().getCardsList();
            mCards.setValue(cards);
        }
    }
}