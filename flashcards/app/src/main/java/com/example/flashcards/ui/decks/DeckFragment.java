package com.example.flashcards.ui.decks;

import static com.example.flashcards.ui.cards.CardFragment.CARD_NAME;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcards.CardsBank;
import com.example.flashcards.DecksBank;
import com.example.flashcards.NewCardsBank;
import com.example.flashcards.R;
import com.example.flashcards.database.entity.Card;
import com.example.flashcards.database.entity.Deck;
import com.example.flashcards.database.entity.FlashCard;
import com.example.flashcards.database.entity.NewDeck;
import com.example.flashcards.databinding.FragmentDeckBinding;

import java.util.List;
import java.util.UUID;

public class DeckFragment extends Fragment {

    public static final String DECK_NAME = "deckName";
    private String deckName;

    public static DeckFragment newInstance(String deckName) {
        DeckFragment fragment = new DeckFragment();
        Bundle args = new Bundle();
        args.putSerializable(DECK_NAME, deckName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            deckName = getArguments().getString(DECK_NAME);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentDeckBinding binding = FragmentDeckBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        RecyclerView recyclerView = binding.fragmentDeckRv;
        Button btnTrainDeck = binding.btnTrainDeck;

        btnTrainDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle arg = new Bundle();
                //ToDo cardID может быть null, выяснить почему
                arg.putSerializable(DECK_NAME, deckName);
                Navigation.findNavController(view).navigate(R.id.action_nav_deck_to_nav_slideshow, arg);
            }
        });

        List<Card> cardList;
        cardList = NewCardsBank.get().getCardsDeck(deckName);
        DeckAdapter deckAdapter = new DeckAdapter(cardList);
        recyclerView.setAdapter(deckAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }


    private class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.DeckHolder> {
        private final List<Card> cards;

        public DeckAdapter(List<Card> cardsList) {
            cards = cardsList;
        }

        @NonNull
        @Override
        public DeckHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DeckHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DeckHolder holder, int position) {
            Card card = cards.get(position);
            holder.bind(card);
        }

        @Override
        public int getItemCount() {
            return cards.size();
        }

        private class DeckHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private final TextView mTitleTextView2;
            private Card card;

            public DeckHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_deck, parent, false));

                itemView.setOnClickListener(this);
                mTitleTextView2 = itemView.findViewById(R.id.item_deck_tv_card_id);
            }

            public void bind(Card card) {
                this.card = card;

                if (card != null) {
                    mTitleTextView2.setText(card.getWord());
                } else {
                    mTitleTextView2.setText("Карточка удалена..(");
                }
            }


            @Override
            public void onClick(View view) {
//                if (cardD != null) {
//                    notifyItemChanged(getAdapterPosition());
//                    Bundle arg = new Bundle();
//                    //ToDo cardID может быть null, выяснить почему
//                    arg.putSerializable(CARD_NAME, cardD.getWord());
//                    Navigation.findNavController(view).navigate(R.id.action_nav_deck_to_nav_card_fragment, arg);
                //}
            }
        }
    }
}