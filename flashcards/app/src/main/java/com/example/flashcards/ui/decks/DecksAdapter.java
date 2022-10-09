package com.example.flashcards.ui.decks;

import static com.example.flashcards.ui.decks.DeckFragment.DECK_GLOBAL;
import static com.example.flashcards.ui.decks.DeckFragment.DECK_NAME;
import static com.example.flashcards.ui.decks.DeckFragment.USER_ID;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcards.NewDecksBank;
import com.example.flashcards.R;
import com.example.flashcards.database.entity.NewDeck;

import java.util.List;

public class DecksAdapter extends RecyclerView.Adapter<DecksAdapter.DecksHolder> {
    public List<NewDeck> decks;
    private final Boolean isGlobalMode;
    private int selectedPos = -1;

    public DecksAdapter(List<NewDeck> decks) {
        this.decks = decks;
        this.isGlobalMode = false;
    }

    public DecksAdapter(List<NewDeck> decks, Boolean isModeGlobal) {
        this.decks = decks;
        this.isGlobalMode = isModeGlobal;
    }

    @NonNull
    @Override
    public DecksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new DecksHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull DecksHolder holder, int position) {
        NewDeck deck = decks.get(position);
        holder.bind(deck);
    }

    @Override
    public int getItemCount() {
        return decks.size();
    }

    public class DecksHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        private TextView mTitleTextView;
        private ImageButton deleteDeck;
        private NewDeck deck;
        private String deckUserId;

        public DecksHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));
            context = parent.getContext();
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.item_card_tv_name);
            deleteDeck = itemView.findViewById(R.id.itemDelete);
            deleteDeck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NewDecksBank.get().deleteDecks(deck);
                    decks.remove(deck);
                    notifyDataSetChanged();
                }
            });
            if(isGlobalMode) {
                deleteDeck.setVisibility(View.INVISIBLE);
            } else {
                deleteDeck.setVisibility(View.VISIBLE);
            }
        }

        public void bind(NewDeck decks) {
            this.deck = decks;
            if(isGlobalMode) {
                deckUserId = decks.getUserID();
            }
            mTitleTextView.setText(decks.getName());
        }

        @Override
        public void onClick(View view) {
            notifyItemChanged(selectedPos);
            selectedPos = getLayoutPosition();
            notifyItemChanged(selectedPos);
            Bundle arg = new Bundle();
            arg.putSerializable(DECK_NAME, deck.getName());
            arg.putBoolean(DECK_GLOBAL, isGlobalMode);
            if(deckUserId != null) {
                arg.putString(USER_ID, deckUserId);
            }
            DeckFragment fragment = DeckFragment.newInstance(deck.getName());
            if(isGlobalMode) {
                Navigation
                        .findNavController(view)
                        .navigate(R.id.action_globalDecksFragment_to_nav_deck, arg);
            } else {
                Navigation
                        .findNavController(view)
                        .navigate(R.id.action_nav_decks_to_nav_deck, arg);
            }
        }
    }
}
