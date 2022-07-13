package com.example.flashcards.ui.decks;

import static com.example.flashcards.ui.decks.DeckFragment.DECK_ID;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcards.DecksBank;
import com.example.flashcards.R;
import com.example.flashcards.database.entity.Decks;
import com.example.flashcards.database.entity.FlashCard;
import com.example.flashcards.databinding.FragmentDecksBinding;
import com.example.flashcards.ui.cards.CardFragment;

import java.util.ArrayList;
import java.util.List;

public class DecksFragment extends Fragment {

    private FragmentDecksBinding binding;
    public RecyclerView recyclerView;
    private DecksAdapter decksAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DecksViewModel galleryViewModel =
                new ViewModelProvider(this).get(DecksViewModel.class);

        binding = FragmentDecksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.decksRV;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<Decks> decks = new ArrayList<>();
        decks = DecksBank.get().getDecks();
        decksAdapter = new DecksAdapter(decks);
        recyclerView.setAdapter(decksAdapter);

        final TextView textView = binding.textDecks;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public class DecksAdapter extends RecyclerView.Adapter<DecksAdapter.DecksHolder> {
        private List<Decks> decks;
        private int selectedPos = -1;

        public DecksAdapter(List<Decks> decks) {
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
            Decks deck = decks.get(position);
            holder.bind(deck);
        }

        @Override
        public int getItemCount() {
            return decks.size();
        }

        public class DecksHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView mTitleTextView;
            private Decks decks;
            Context context;

            public DecksHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_card, parent, false));
                context = parent.getContext();
                itemView.setOnClickListener(this);
                mTitleTextView = (TextView) itemView.findViewById(R.id.item_card_tv_name);
            }

            // Метод будет вызываться каждый раз, когда RecyclerView потребует связать
            // данный объект CardsHolder с объектом конкретной карточки.
            public void bind(Decks decks) {
                this.decks = decks;
                mTitleTextView.setText(decks.getName());
            }

            @Override
            public void onClick(View view) {
                notifyItemChanged(selectedPos);
                selectedPos = getLayoutPosition();
                notifyItemChanged(selectedPos);
                Bundle arg = new Bundle();
                arg.putLong(DECK_ID, decks.getId());
                DeckFragment fragment = DeckFragment.newInstance(decks.getId());
                Navigation
                        .findNavController(view)
                        .navigate(R.id.action_nav_decks_to_nav_deck, arg);
//                getActivity().getSupportFragmentManager().beginTransaction().add(fragment,"tag").commit();
            }
        }

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}