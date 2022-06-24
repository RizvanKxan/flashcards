package com.example.flashcards.ui.cards;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flashcards.CardsBank;
import com.example.flashcards.R;
import com.example.flashcards.database.entity.FlashCard;

import java.util.ArrayList;
import java.util.List;

public class CardFragment extends DialogFragment {
    private FlashCard mCard;
    private Button mBtnAdd;
    EditText etW;
    EditText etV;

    public CardFragment(long id) {
        getCard(id);
    }

    public static CardFragment newInstance(long id) {
        CardFragment fragment = new CardFragment(id);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");

        View dialogView = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_card, null);
        etW = dialogView.findViewById(R.id.fragment_card_et_word);
        etW.setText(mCard.getWord());
        etV = dialogView.findViewById(R.id.fragment_card_et_value);
        etV.setText(mCard.getValue());
        mBtnAdd = dialogView.findViewById(R.id.fragment_card_btn_ok);
        mBtnAdd.setOnClickListener(this::onClick);
        Button btnCancel = dialogView.findViewById(R.id.fragment_card_btn_cancel);
        btnCancel.setOnClickListener(this::onClick);
        return dialogView;
    }

    public void onClick(View v) {
        Button btn = (Button) v;
        CharSequence text = btn.getText();
        if (mBtnAdd.getText().equals(text)) {
            String word = etW.getText().toString();
            String value = etV.getText().toString();

            //--- если слово или значение пусты, то выводим сообщение об этом и не создаём новый объект
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
            }
        }

        dismiss();
    }

    public void getCard(long id) {
        CardsBank.get().getCard(new CardsBank.Result<FlashCard>(){
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