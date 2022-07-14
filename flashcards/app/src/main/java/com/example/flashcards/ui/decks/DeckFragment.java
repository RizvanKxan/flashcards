package com.example.flashcards.ui.decks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcards.CardsBank;
import com.example.flashcards.DecksBank;
import com.example.flashcards.R;
import com.example.flashcards.database.entity.Deck;
import com.example.flashcards.database.entity.FlashCard;
import com.example.flashcards.databinding.FragmentDeckBinding;

import java.util.List;
import java.util.UUID;

public class DeckFragment extends Fragment {

    public static final String DECK_ID = "deckId";
    private UUID deckId;

    public static DeckFragment newInstance(UUID id) {
        DeckFragment fragment = new DeckFragment();
        Bundle args = new Bundle();
        args.putSerializable(DECK_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            deckId = (UUID) getArguments().getSerializable(DECK_ID);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentDeckBinding binding = FragmentDeckBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        deckId = (UUID) getArguments().getSerializable(DECK_ID);
        RecyclerView recyclerView = binding.fragmentDeckRv;

        List<Deck> deck;
        deck = DecksBank.get().getAllDeck();
        DeckAdapter deckAdapter = new DeckAdapter(deck);
        recyclerView.setAdapter(deckAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }


    private class DeckHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mTitleTextView;
        private final TextView mTitleTextView2;
        FlashCard cardD;
        private Deck deck;

        public DeckHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_deck, parent, false));

            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.item_deck_tv_deck_id);
            mTitleTextView2 = itemView.findViewById(R.id.item_deck_tv_card_id);
        }

        // Метод будет вызываться каждый раз, когда RecyclerView потребует связать
        // данный объект CardsHolder с объектом конкретной карточки.
        public void bind(Deck deck) {
            this.deck = deck;

            CardsBank.get().getCard(new CardsBank.Result<FlashCard>() {
                @Override
                public void onSuccess(FlashCard card) {
                    cardD = card;
                }

                @Override
                public void onError(Exception exception) {

                }
            }, deck.getCardID());
            if (cardD != null) {
                update();
            }
        }

        private void update() {
            mTitleTextView.setText("deck_id: " + deck.getDeckID());
            mTitleTextView2.setText("card_id: " + deck.getCardID() + " " + cardD.getWord());
        }

        @Override
        public void onClick(View view) {

        }
    }

    private class DeckAdapter extends RecyclerView.Adapter<DeckHolder> {
        private final List<Deck> decks;

        public DeckAdapter(List<Deck> deck) {
            decks = deck;
        }

        @NonNull
        @Override
        public DeckHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DeckHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DeckHolder holder, int position) {
            Deck deckk = decks.get(position);
            holder.bind(deckk);
        }

        @Override
        public int getItemCount() {
            return decks.size();
        }

    }
}