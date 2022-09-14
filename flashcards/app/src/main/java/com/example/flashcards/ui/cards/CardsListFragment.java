package com.example.flashcards.ui.cards;

import static com.example.flashcards.ui.cards.CardFragment.CARD_ID;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.flashcards.R;
import com.example.flashcards.database.entity.FlashCard;
import com.example.flashcards.databinding.FragmentCardListBinding;

import java.util.ArrayList;
import java.util.List;

public class CardsListFragment extends Fragment {

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
        List<FlashCard> cards = CardsBank.get().getCards();
        cardsAdapter = new CardsAdapter(cards);
        mCardsRecyclerView.setAdapter(cardsAdapter);
        return view;
    }

    public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardsHolder> {

        private List<FlashCard> mCards;

        public CardsAdapter(List<FlashCard> cards) {
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
            FlashCard card = mCards.get(position);
            holder.bind(card);
        }

        @Override
        public int getItemCount() {
            return mCards.size();
        }

        public void setFlashCards(List<FlashCard> cards) {
            mCards = cards;
        }

        public class CardsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private final TextView mTitleTextView;
            private FlashCard mCard;
            private ImageButton deleteCard;

            public CardsHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_card, parent, false));
                itemView.setOnClickListener(this);
                mTitleTextView = itemView.findViewById(R.id.item_card_tv_name);
                deleteCard = itemView.findViewById(R.id.itemDelete);
                deleteCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CardsBank.get().deleteCard(mCard);
                        notifyDataSetChanged();
                    }
                });
            }

            public void bind(FlashCard card) {
                mCard = card;
                mTitleTextView.setText(card.getWord());

            }

            @Override
            public void onClick(View view) {
                Bundle arg = new Bundle();
            arg.putSerializable(CARD_ID, mCard.getId());
            Navigation.findNavController(view).navigate(R.id.action_nav_cards_to_nav_card_fragment, arg);
            }
        }
    }
}