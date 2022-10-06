package com.example.flashcards.ui.cards;

import static com.example.flashcards.ui.cards.CardFragment.CARD_NAME;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcards.CardsBank;
import com.example.flashcards.NewCardsBank;
import com.example.flashcards.NewDecksBank;
import com.example.flashcards.R;
import com.example.flashcards.database.entity.Card;
import com.example.flashcards.database.entity.FlashCard;
import com.example.flashcards.database.entity.NewDeck;
import com.example.flashcards.databinding.FragmentCardListBinding;

import java.util.ArrayList;
import java.util.List;

public class CardsListFragment extends Fragment{

    public RecyclerView mCardsRecyclerView;
    private FragmentCardListBinding binding;
    private CardsViewModel cardsViewModel;
    private CardsAdapter cardsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        cardsViewModel =
                new ViewModelProvider(this).get(CardsViewModel.class);

        binding = FragmentCardListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        mCardsRecyclerView = binding.cardsRecyclerView;
        mCardsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<Card> cards = NewCardsBank.get().getCardsList();
        cardsAdapter = new CardsAdapter(cards);
        mCardsRecyclerView.setAdapter(cardsAdapter);
        cardsAdapter.notifyDataSetChanged();
        return view;
    }

    public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardsHolder> {

        private List<Card> mCards;

        public CardsAdapter(List<Card> cards) {
            mCards = cards;
        }

        @NonNull
        @Override
        public CardsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new CardsHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CardsHolder holder, int position) {
            Card card = mCards.get(position);
            holder.bind(card);
        }

        @Override
        public int getItemCount() {
            return mCards.size();
        }

        public class CardsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private final TextView mTitleTextView;
            private Card mCard;
            private ImageButton deleteCard;

            public CardsHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_card, parent, false));
                itemView.setOnClickListener(this);
                mTitleTextView = itemView.findViewById(R.id.item_card_tv_name);
                deleteCard = itemView.findViewById(R.id.itemDelete);
                deleteCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NewCardsBank.get().deleteCard(mCard);
                        notifyDataSetChanged();
                    }
                });
            }

            public void bind(Card card) {
                mCard = card;
                mTitleTextView.setText(card.getWord());

            }

            @Override
            public void onClick(View view) {
                try {
                    notifyDataSetChanged();
                    Bundle arg = new Bundle();
                    arg.putSerializable(CARD_NAME, mCard.getWord());
                    Navigation.findNavController(view).navigate(R.id.action_nav_cards_to_nav_card_fragment, arg);
                } catch (Exception exception) {
                    Log.e("error", exception.getMessage());
                };
            }
        }
    }
}