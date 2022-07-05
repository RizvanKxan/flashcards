package com.example.flashcards.ui.cards;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.flashcards.CardsBank;
import com.example.flashcards.database.entity.FlashCard;

import java.util.ArrayList;
import java.util.List;

public class CardsViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    private final MutableLiveData<List<FlashCard>> mCards;

    public CardsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Это фрагмент с карточками.");
        mCards = new MutableLiveData<>();
        List<FlashCard> cards = new ArrayList<>();
        CardsBank.get().getCards(new CardsBank.Result<List<FlashCard>>() {
            @Override
            public void onSuccess(List<FlashCard> flashCards) {
                cards.addAll(flashCards);
            }

            @Override
            public void onError(Exception exception) {

            }
        });
        mCards.setValue(cards);
    }

}