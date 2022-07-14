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

import com.example.flashcards.CardsBank;
import com.example.flashcards.R;
import com.example.flashcards.database.entity.FlashCard;
import com.example.flashcards.databinding.FragmentCardBinding;

import java.util.UUID;

public class CardFragment extends DialogFragment {

    EditText etW;
    EditText etV;
    private FlashCard mCard;
    private Button mBtnAdd;
    private FragmentCardBinding binding;

    //ToDo Cтоит отказаться от параметров передаваемых в конструктор фрагмента
    public CardFragment(UUID id) {
        getCard(id);
    }

    public static CardFragment newInstance(UUID id) {
        CardFragment fragment = new CardFragment(id);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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
                mCard.setWord(word);
                mCard.setValue(value);
                CardsBank.get().updateCard(mCard);
                Toast msg = Toast.makeText(getActivity(), R.string.editCardSuccess, Toast.LENGTH_SHORT);
                msg.show();
                dismiss();
            }
        });
        Button btnCancel = binding.fragmentCardBtnCancel;
        btnCancel.setOnClickListener(view1 -> dismiss());
    }

    public void getCard(UUID id) {
        CardsBank.get().getCard(new CardsBank.Result<FlashCard>() {
            @Override
            public void onSuccess(FlashCard card) {
                mCard = card;
            }

            @Override
            public void onError(Exception exception) {

            }
        }, id);
    }
}