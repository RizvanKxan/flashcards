package com.example.flashcards.ui.decks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.flashcards.NewDecksBank;
import com.example.flashcards.R;
import com.example.flashcards.database.entity.NewDeck;
import com.example.flashcards.databinding.FragmentDecksBinding;
import com.example.flashcards.databinding.FragmentGlobalDecksBinding;

import java.util.List;

public class GlobalDecksFragment extends Fragment {

    private FragmentGlobalDecksBinding binding;
    public RecyclerView recyclerView;
    public DecksAdapter decksAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        DecksViewModel galleryViewModel =
                new ViewModelProvider(this).get(DecksViewModel.class);

        binding = FragmentGlobalDecksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.globalCardsRecycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<NewDeck> decks1 = NewDecksBank.get().getGlobalDeck();
        decksAdapter = new DecksAdapter(decks1, true);
        recyclerView.setAdapter(decksAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}