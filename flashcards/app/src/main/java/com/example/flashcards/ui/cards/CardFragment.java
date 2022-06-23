package com.example.flashcards.ui.cards;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.flashcards.CardsBank;
import com.example.flashcards.R;
import com.example.flashcards.database.entity.FlashCard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardFragment extends Fragment {
    private FlashCard mCard;
    private TextView mTextView;
    public static CardFragment newInstance(long cardID) {

        Bundle args = new Bundle();
        args.putSerializable("ARG_CRIME_ID", cardID);

        CardFragment fragment = new CardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long cardID = (long) getArguments().getSerializable("ARG_CRIME_ID");
        CardsBank.get().getCard(new CardsBank.Result<FlashCard>() {
            @Override
            public void onSuccess(FlashCard card) {
                mCard = card;
            }

            @Override
            public void onError(Exception exception) {

            }
        }, cardID);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        mTextView = (TextView) view.findViewById(R.id.fragment_crime_tv);
        mTextView.setText("1111111111");
        return view;
    }


}
