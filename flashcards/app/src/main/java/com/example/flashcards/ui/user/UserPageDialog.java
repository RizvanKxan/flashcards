package com.example.flashcards.ui.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.flashcards.DecksBank;
import com.example.flashcards.NewDecksBank;
import com.example.flashcards.R;
import com.example.flashcards.database.entity.Deck;
import com.example.flashcards.database.entity.Decks;
import com.example.flashcards.database.entity.NewDeck;
import com.example.flashcards.databinding.FragmentCreateDeckBinding;
import com.example.flashcards.databinding.FragmentUserPageBinding;

public class UserPageDialog extends DialogFragment {

    private FragmentUserPageBinding binding;

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentUserPageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
