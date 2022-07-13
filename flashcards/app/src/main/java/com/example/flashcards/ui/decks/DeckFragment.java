package com.example.flashcards.ui.decks;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.flashcards.DecksBank;
import com.example.flashcards.R;
import com.example.flashcards.database.entity.Deck;
import com.example.flashcards.database.entity.Decks;
import com.example.flashcards.databinding.FragmentDeckBinding;
import com.example.flashcards.databinding.FragmentDecksBinding;

import java.util.ArrayList;
import java.util.List;

public class DeckFragment extends Fragment {
    private FragmentDeckBinding binding;
    RecyclerView recyclerView;
    private DeckFragment.DeckAdapter deckAdapter;
    TextView textView;

    public static final String DECK_ID = "deckId";

    private long deckId;

    public DeckFragment() {
    }

    public static DeckFragment newInstance(long id) {
        DeckFragment fragment = new DeckFragment();
        Bundle args = new Bundle();
        args.putLong(DECK_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            deckId = getArguments().getLong(DECK_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDeckBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        deckId = getArguments().getLong(DECK_ID);
        textView = binding.tvText;
        textView.setText(deckId + "");
        recyclerView = binding.fragmentDeckRv;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<Deck> deck = new ArrayList<>();
        deck = DecksBank.get().getAllDeck();
        deckAdapter = new DeckAdapter(deck);
        recyclerView.setAdapter(deckAdapter);



        return view;
    }

    public class DeckAdapter extends RecyclerView.Adapter<DeckFragment.DeckAdapter.DeckHolder> {
        private List<Deck> deck;
        private int selectedPos = -1;

        public DeckAdapter(List<Deck> deck) {
            this.deck = deck;
        }

        @NonNull
        @Override
        public DeckFragment.DeckAdapter.DeckHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new DeckFragment.DeckAdapter.DeckHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DeckFragment.DeckAdapter.DeckHolder holder, int position) {
            Deck deckk = deck.get(position);
            holder.bind(deckk);
        }

        @Override
        public int getItemCount() {
            return deck.size();
        }

        public class DeckHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView mTitleTextView;
            private Deck deck;
            Context context;

            public DeckHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_card, parent, false));
                context = parent.getContext();
                itemView.setOnClickListener(this);
                mTitleTextView = (TextView) itemView.findViewById(R.id.item_card_tv_name);
            }

            // Метод будет вызываться каждый раз, когда RecyclerView потребует связать
            // данный объект CardsHolder с объектом конкретной карточки.
            public void bind(Deck deck) {
                this.deck = deck;
                mTitleTextView.setText(deck.getDeckID() + "");
            }

            @Override
            public void onClick(View view) {

            }
        }

    }
}