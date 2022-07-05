package com.example.flashcards.ui.decks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.flashcards.databinding.FragmentDecksBinding;

public class DecksFragment extends Fragment {

    private FragmentDecksBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DecksViewModel galleryViewModel =
                new ViewModelProvider(this).get(DecksViewModel.class);

        binding = FragmentDecksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDecks;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}