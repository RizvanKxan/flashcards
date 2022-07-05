package com.example.flashcards.ui.decks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DecksViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DecksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Это фрагмент с колодами.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}