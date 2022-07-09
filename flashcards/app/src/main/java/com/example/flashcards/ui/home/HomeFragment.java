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
    CreateCardDialog mDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Button btnCards = binding.homeBtnCards;
        Button btnAddDeck = binding.homeBtnAddDeck;
        btnCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog = CreateCardDialog.newInstance();
                mDialog.show(getActivity().getSupportFragmentManager(),"tag");
            }
        });
//Todo Не совсем правильно работает. ломается навигация обратно.
        btnAddDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_decks);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}