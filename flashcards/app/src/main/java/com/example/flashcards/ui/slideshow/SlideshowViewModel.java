package com.example.flashcards.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.flashcards.CardsBank;
import com.example.flashcards.database.entity.FlashCard;

import java.util.ArrayList;
import java.util.List;

public class SlideshowViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<List<FlashCard>> mCards;
    private int i = -1;

    public boolean isValueShown() {
        return isValueShown;
    }

    private boolean isValueShown = false;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Фрагмент с слайдшоу.");
        mCards = new MutableLiveData<>();
        List<FlashCard> cards = CardsBank.get().getCards();
        mCards.setValue(cards);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void swipe() {
        i = i + 1;
        if (i < mCards.getValue().size()) {
            mText.setValue(mCards.getValue().get(i).getWord());
            isValueShown = false;
        } else {
            mText.setValue("Пока больше нечего показать..");
        }

    }

    public void getValue() {
        isValueShown = true;
        if (i == -1) return;
        if (i < mCards.getValue().size()) {
            mText.setValue(mCards.getValue().get(i).getValue());
        } else {
            mText.setValue("Пока больше нечего показать..");
        }
    }

    public void getWord() {
        isValueShown = false;
        if (i == -1) return;
        if (i < mCards.getValue().size()) {
            mText.setValue(mCards.getValue().get(i).getWord());
        } else {
            mText.setValue("Пока больше нечего показать..");
        }
    }
}