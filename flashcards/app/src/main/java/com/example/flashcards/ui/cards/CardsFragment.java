package com.example.flashcards.ui.cards;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flashcards.CardsBank;
import com.example.flashcards.R;
import com.example.flashcards.database.entity.FlashCard;
import com.example.flashcards.databinding.FragmentCardListBinding;
import com.example.flashcards.databinding.FragmentGalleryBinding;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.flashcards.databinding.FragmentCardListBinding;
import com.example.flashcards.ui.gallery.GalleryViewModel;

public class CardsFragment extends Fragment {
    private FragmentCardListBinding binding;
    private CardsViewModel mViewModel;
    private RecyclerView mCardsRecyclerView;
    private CardsAdapter cardsAdapter;
    //private int mSelectedPosition = -1;

    public static CardsFragment newInstance() {
        return new CardsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        CardsViewModel galleryViewModel =
                new ViewModelProvider(this).get(CardsViewModel.class);

        View view = inflater.inflate(R.layout.fragment_card_list, container, false);
        mCardsRecyclerView = (RecyclerView) view.findViewById(R.id.cards_recycler_view);
        mCardsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CardsViewModel.class);
        List<FlashCard> cards = new ArrayList<>();
        CardsBank.get().getCards(new CardsBank.Result<List<FlashCard>>() {
            @Override
            public void onSuccess(List<FlashCard> flashCards) {
                cards.addAll(flashCards);
            }

            @Override
            public void onError(Exception exception) {

            }
        });
//        cards.add(new FlashCard("text", "value"));
//        cards.add(new FlashCard("text2", "value"));
//        cards.add(new FlashCard("text3", "value"));
        cardsAdapter = new CardsAdapter(cards);
        mCardsRecyclerView.setAdapter(cardsAdapter);
        cardsAdapter.notifyDataSetChanged();

        // TODO: Use the ViewModel
    }
}