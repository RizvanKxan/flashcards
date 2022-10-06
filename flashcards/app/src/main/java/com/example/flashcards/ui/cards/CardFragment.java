package com.example.flashcards.ui.cards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcards.CardsBank;
import com.example.flashcards.NewCardsBank;
import com.example.flashcards.R;
import com.example.flashcards.database.entity.Card;
import com.example.flashcards.databinding.FragmentCardBinding;

import java.util.UUID;

public class CardFragment extends DialogFragment {

    public static final String CARD_NAME = "cardName";
    private String cardName;
    private EditText etW;
    private EditText etV;
    private Card mCard;
    private Button mBtnAdd;
    private FragmentCardBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            cardName = getArguments().getString(CARD_NAME);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mCard = NewCardsBank.get().getCard(cardName);
        if(mCard.getWord() == null) {
            try {
                finalize();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        etW = binding.fragmentCardEtWord;
        etW.setText(mCard.getWord());
        etV = binding.fragmentCardEtValue;
        etV.setText(mCard.getValue());
        mBtnAdd = binding.fragmentCardBtnOk;
        mBtnAdd.setOnClickListener(view12 -> {
            String word = etW.getText().toString();
            String value = etV.getText().toString();

            if (word.trim().length() == 0 || value.trim().length() == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.attention)
                        .setMessage(R.string.msgErrorCreateCard)
                        .setPositiveButton(R.string.accept, null)
                        .create()
                        .show();
            } else {
                String deckName = mCard.getDeckName();
                cardName = word;
                NewCardsBank.get().deleteCard(mCard);
                Card card = new Card(word, value, deckName);
                NewCardsBank.get().addCard(card, card.getDeckName());
                Toast msg = Toast.makeText(getActivity(), R.string.editCardSuccess, Toast.LENGTH_SHORT);
                msg.show();
                dismiss();
            }
        });
        Button btnCancel = binding.fragmentCardBtnCancel;
        btnCancel.setOnClickListener(view1 -> dismiss());
    }
}