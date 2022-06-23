package com.example.flashcards.ui.slideshow;

import android.app.Application;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.flashcards.App;
import com.example.flashcards.CardsBank;
import com.example.flashcards.R;
import com.example.flashcards.database.entity.FlashCard;

import java.util.ArrayList;
import java.util.List;

public class SlideshowViewModel extends ViewModel {

    private int i = -1;
    private final MutableLiveData<String> mText;
    private final MutableLiveData<List<FlashCard>> mCards;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
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

    public LiveData<String> getText() {
        return mText;
    }
    public boolean getInt() {
        return i%2==0;
    }
    public void swipe() {
        i = i + 1;
        if(i<mCards.getValue().size()) {
            mText.setValue(mCards.getValue().get(i).getWord());
        } else
        {
            mText.setValue("Пока больше нечего показать..");
        }

    }

    public void getValue() {
        if(i == -1) return;
        if(i<mCards.getValue().size()) {
            mText.setValue(mCards.getValue().get(i).getValue());
           // i++;
        } else
        {
            mText.setValue("Пока больше нечего показать..");
        }
    }
}