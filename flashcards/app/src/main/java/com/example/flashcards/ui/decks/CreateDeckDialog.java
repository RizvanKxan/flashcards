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
import com.example.flashcards.R;
import com.example.flashcards.database.entity.Deck;
import com.example.flashcards.database.entity.Decks;
import com.example.flashcards.databinding.FragmentCreateDeckBinding;

public class CreateDeckDialog extends DialogFragment {

    private FragmentCreateDeckBinding binding;

    private EditText nameDeckEditText;
    private Button btnAdd;
    private Button btnCancel;

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateDeckBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        nameDeckEditText = binding.createDeckEtName;
        btnAdd = binding.btnAdd;
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameDeck = nameDeckEditText.getText().toString();
                if(nameDeck.trim().length() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Внимание!")
                            .setMessage("Поля должны быть заполнены.")
                            .setPositiveButton("Принято", null)
                            .create()
                            .show();
                } else {
                    Decks decks = new Decks(nameDeck);
                    DecksBank.get().addDeck(decks);
                    Toast msg = Toast.makeText(getActivity(), "Колода успешно добавлена!", Toast.LENGTH_SHORT);
                    msg.show();
                    dismiss();
                }
            }
        });
        btnCancel = binding.btnCancel;
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;


    }
}
