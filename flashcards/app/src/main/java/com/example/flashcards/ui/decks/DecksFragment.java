package com.example.flashcards.ui.decks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcards.NewDecksBank;
import com.example.flashcards.database.entity.NewDeck;
import com.example.flashcards.databinding.FragmentDecksBinding;

import java.util.List;

public class DecksFragment extends Fragment {

    public RecyclerView recyclerView;
    private FragmentDecksBinding binding;
    public DecksAdapter decksAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        DecksViewModel galleryViewModel =
                new ViewModelProvider(this).get(DecksViewModel.class);

        binding = FragmentDecksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.decksRV;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<NewDeck> decks1 = NewDecksBank.get().getDeck();
        decksAdapter = new DecksAdapter(decks1);
        recyclerView.setAdapter(decksAdapter);
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