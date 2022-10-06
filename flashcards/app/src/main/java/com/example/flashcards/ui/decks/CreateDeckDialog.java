package com.example.flashcards.ui.decks;

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

public class CreateDeckDialog extends DialogFragment {

    private FragmentCreateDeckBinding binding;

    private EditText nameDeckEditText;

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentCreateDeckBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameDeckEditText = binding.createDeckEtName;
        Button btnAdd = binding.btnAdd;
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameDeck = nameDeckEditText.getText().toString();
                if(nameDeck.trim().length() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.attention)
                            .setMessage(R.string.msgErrorCreateCard)
                            .setPositiveButton(R.string.accept, null)
                            .create()
                            .show();
                } else {
                    NewDeck deck = new NewDeck(nameDeck);
                    NewDecksBank.get().addDeck(deck);
                    Toast msg = Toast.makeText(getActivity(), R.string.addDeckSuccess, Toast.LENGTH_SHORT);
                    msg.show();
                    dismiss();
                }
            }
        });

        Button btnCancel = binding.btnCancel;
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
