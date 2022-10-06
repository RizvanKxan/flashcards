package com.example.flashcards.ui.cards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.flashcards.CardsBank;
import com.example.flashcards.DecksBank;
import com.example.flashcards.NewCardsBank;
import com.example.flashcards.NewDecksBank;
import com.example.flashcards.R;
import com.example.flashcards.database.entity.Card;
import com.example.flashcards.database.entity.Deck;
import com.example.flashcards.database.entity.Decks;
import com.example.flashcards.database.entity.FlashCard;
import com.example.flashcards.database.entity.NewDeck;
import com.example.flashcards.databinding.FragmentCreateCardBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CreateCardDialog extends DialogFragment {

    private FragmentCreateCardBinding binding;
    private EditText mWordEditText;
    private EditText mValueEditText;
    private Button mBtnAdd;
    private String deckName;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCreateCardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        mBtnAdd = binding.btnAdd;
        mBtnAdd.setOnClickListener(view1 -> {
            String word = mWordEditText.getText().toString();
            String value = mValueEditText.getText().toString();

            //--- если слово или значение пусты, то выводим сообщение об этом и не создаём новый объект
            if (word.trim().length() == 0 || value.trim().length() == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.attention)
                        .setMessage(R.string.msgErrorCreateCard)
                        .setPositiveButton(R.string.accept, null)
                        .create()
                        .show();
            } else {
                //FlashCard card = new FlashCard(word, value);

                //CardsBank.get().addCard(card);
                if(deckName != null) {
                    Card card1 = new Card(word, value, deckName);
                    NewCardsBank.get().addCard(card1,deckName);
//                    Deck deck = new Deck(deckId, card.getId());
//                    DecksBank.get().addDeck(deck);
                }
                Toast msg = Toast.makeText(getActivity(), R.string.addCardSuccess, Toast.LENGTH_SHORT);
                msg.show();
                dismiss();
            }

        });

        Button btnCancel = binding.btnCancel;
        btnCancel.setOnClickListener(view12 -> dismiss());
        mWordEditText = view.findViewById(R.id.create_card_et_word);
        mValueEditText = view.findViewById(R.id.create_card_et_value);
        Spinner spinnerDecks = binding.spinnerDecks;

        List<NewDeck> decks = NewDecksBank.get().getDeck();
        List<String> decksName = new ArrayList<>();
        decks.forEach(item -> decksName.add(item.getName()));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, decksName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDecks.setAdapter(adapter);


        spinnerDecks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                deckName = (String) adapterView.getItemAtPosition(i);
                //List<NewDeck> deck = decks.stream().filter(s -> s.getName().equals(item)).collect(Collectors.toList());
                //deckId = deck.get(0).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }
}
