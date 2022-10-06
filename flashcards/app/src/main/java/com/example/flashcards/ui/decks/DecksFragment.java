package com.example.flashcards.ui.decks;

import static com.example.flashcards.ui.decks.DeckFragment.DECK_NAME;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcards.DecksBank;
import com.example.flashcards.MainActivity;
import com.example.flashcards.NewDecksBank;
import com.example.flashcards.R;
import com.example.flashcards.database.entity.Decks;
import com.example.flashcards.database.entity.NewDeck;
import com.example.flashcards.databinding.FragmentDecksBinding;

import java.util.ArrayList;
import java.util.List;

public class DecksFragment extends Fragment {

    public RecyclerView recyclerView;
    private FragmentDecksBinding binding;
    public DecksAdapter decksAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        DecksViewModel galleryViewModel =
                new ViewModelProvider(this).get(DecksViewModel.class);

        binding = FragmentDecksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.decksRV;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<NewDeck> decks1 = NewDecksBank.get().getDeck();
        decksAdapter = new DecksAdapter(decks1);
        recyclerView.setAdapter(decksAdapter);
        final TextView textView = binding.textDecks;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public class DecksAdapter extends RecyclerView.Adapter<DecksAdapter.DecksHolder> {
        public List<NewDeck> decks;
        private int selectedPos = -1;

        public DecksAdapter(List<NewDeck> decks) {
            this.decks = decks;
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
            }

            public void bind(NewDeck decks) {
                this.deck = decks;
                mTitleTextView.setText(decks.getName());
            }

            @Override
            public void onClick(View view) {
                notifyItemChanged(selectedPos);
                selectedPos = getLayoutPosition();
                notifyItemChanged(selectedPos);
                Bundle arg = new Bundle();
                arg.putSerializable(DECK_NAME, deck.getName());
                DeckFragment fragment = DeckFragment.newInstance(deck.getName());
                Navigation
                        .findNavController(view)
                        .navigate(R.id.action_nav_decks_to_nav_deck, arg);
            }
        }
    }
}