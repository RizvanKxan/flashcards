package com.example.flashcards.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;


import com.example.flashcards.MainActivity;
import com.example.flashcards.R;
import com.example.flashcards.databinding.FragmentHomeBinding;
import com.example.flashcards.ui.cards.CreateCardDialog;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Button btnAddCard = binding.homeBtnCards;
        Button btnAddDeck = binding.homeBtnAddDeck;

        btnAddCard.setOnClickListener(view -> {
            Navigation
                    .findNavController(view)
                    .navigate(R.id.action_nav_home_to_nav_create_card);
        });

        btnAddDeck.setOnClickListener(view ->
                Navigation
                        .findNavController(view)
                        .navigate(R.id.action_nav_home_to_nav_create_deck));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}