package com.example.flashcards.ui.decks;

import static com.example.flashcards.ui.cards.CardFragment.CARD_ID;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentDeckBinding binding = FragmentDeckBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        RecyclerView recyclerView = binding.fragmentDeckRv;

        List<Deck> deck;
        deck = DecksBank.get().getAllDeckId(deckId);
        DeckAdapter deckAdapter = new DeckAdapter(deck);
        recyclerView.setAdapter(deckAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }


    private class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.DeckHolder> {
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

        private class DeckHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private final TextView mTitleTextView2;
            FlashCard cardD;
            private Deck deck;

            public DeckHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_deck, parent, false));

                itemView.setOnClickListener(this);
                mTitleTextView2 = itemView.findViewById(R.id.item_deck_tv_card_id);
            }

            // Метод будет вызываться каждый раз, когда RecyclerView потребует связать
            // данный объект CardsHolder с объектом конкретной карточки.
            public void bind(Deck deck) {
                this.deck = deck;

                cardD = CardsBank.get().getCard(deck.getCardID());
                if (cardD != null) {
                    mTitleTextView2.setText(cardD.getWord());
                } else {
                    mTitleTextView2.setText("Карточка удалена..(");
                }
            }


            @Override
            public void onClick(View view) {
                if (cardD != null) {
                    notifyItemChanged(getAdapterPosition());
                    Bundle arg = new Bundle();
                    //ToDo cardID может быть null, выяснить почему
                    arg.putSerializable(CARD_ID, cardD.getId());
                    Navigation.findNavController(view).navigate(R.id.action_nav_deck_to_nav_card_fragment, arg);
                }
            }
        }
    }
}