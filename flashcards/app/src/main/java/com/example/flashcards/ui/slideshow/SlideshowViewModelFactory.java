package com.example.flashcards.ui.slideshow;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.UUID;

public class SlideshowViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private UUID id;

    public SlideshowViewModelFactory(UUID id) {
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SlideshowViewModel(id);
    }
}
