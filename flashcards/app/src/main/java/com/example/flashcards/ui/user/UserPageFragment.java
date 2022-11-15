package com.example.flashcards.ui.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.flashcards.NewCardsBank;
import com.example.flashcards.R;
import com.example.flashcards.database.entity.User;
import com.example.flashcards.databinding.FragmentDeckBinding;
import com.example.flashcards.databinding.FragmentUserPageBinding;

import org.w3c.dom.Text;

public class UserPageFragment extends Fragment {

    public UserPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentUserPageBinding binding = FragmentUserPageBinding.inflate(inflater, container, false);
        TextView userName = binding.userNameValue;
        userName.setText(User.getUser().getName());
        TextView countCard = binding.userCardCountValue;
        countCard.setText(NewCardsBank.get().getCountCard() + "");
        TextView allSwipe = binding.userAllSwipeValue;
        allSwipe.setText(User.get().getAllSwipe() + "");
        TextView swipeKnow = binding.userSwipeKnowValue;
        swipeKnow.setText(User.get().getKnowSwipe() + "");
        TextView swipeDontKnow = binding.userSwipeDontKnowValue;
        swipeDontKnow.setText(User.get().getDontKnowSwipe() + "");

        Button clearSwipeBtn = binding.userClearSwipe;
        clearSwipeBtn.setOnClickListener(view -> {
            User.get().setAllSwipe(0);
            User.get().setKnowSwipe(0);
            User.get().setDontKnowSwipe(0);
            allSwipe.setText("0");
            swipeKnow.setText("0");
            swipeDontKnow.setText("0");
        });
        return binding.getRoot();
    }
}