package com.example.flashcards.ui.cards;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
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
import android.widget.TextView;

import com.example.flashcards.CardsBank;
import com.example.flashcards.R;
import com.example.flashcards.database.entity.FlashCard;
import com.example.flashcards.databinding.FragmentCardListBinding;

import java.util.ArrayList;
import java.util.List;

public class CardsListFragment extends Fragment {
    private FragmentCardListBinding binding;
    private CardsViewModel mViewModel;
    private RecyclerView mCardsRecyclerView;
    private CardsAdapter cardsAdapter;
    //private int mSelectedPosition = -1;

    public static CardsListFragment newInstance() {
        return new CardsListFragment();
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
        cardsAdapter = new CardsAdapter(getActivity(),cards);
        mCardsRecyclerView.setAdapter(cardsAdapter);
        cardsAdapter.notifyDataSetChanged();

        // TODO: Use the ViewModel
    }

    public class CardsAdapter  extends RecyclerView.Adapter<CardsAdapter.CardsHolder> {

        private List<FlashCard> mCards;
        private int selectedPos = -1;
        private Context context;

        public CardsAdapter(Context context, List<FlashCard> cards) {
            this.context = context;
            mCards = cards;
        }

        @NonNull
        @Override
        public CardsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new CardsHolder(layoutInflater, parent);
        }

        // Чем проще реализация - тем плавнее пракрутка RecyclerView. Если связывание
        // сделать менее эфективным и громоздким, тем выше вероятность запинок и рывков
        // при прокрутке.
        @Override
        public void onBindViewHolder(@NonNull CardsHolder holder, int position) {
            FlashCard card = mCards.get(position);
            if(selectedPos == position) {
                holder.itemView.setBackgroundColor(Color.GREEN);
            } else {
                holder.itemView.setBackgroundColor(Color.WHITE);
            }
            holder.bind(card);
        }

        @Override
        public int getItemCount() {
            return mCards.size();
        }

        public void setFlashCards(List<FlashCard> cards) {
            mCards = cards;
        }

        public class CardsHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView mTitleTextView;
            private FlashCard mCard;
            Context context;

            public CardsHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_card, parent, false));
                context = parent.getContext();
                itemView.setOnClickListener(this);
                mTitleTextView = (TextView) itemView.findViewById(R.id.item_card_tv_name);
            }

            // Метод будет вызываться каждый раз, когда RecyclerView потребует связать
            // данный объект CardsHolder с объектом конкретной карточки.
            public void bind(FlashCard card) {
                mCard = card;
                mTitleTextView.setText(card.getWord());
            }

            @Override
            public void onClick(View view) {
                // mCard.isActive = true;
                notifyItemChanged(selectedPos);
                selectedPos= getLayoutPosition();
                notifyItemChanged(selectedPos);
                CardFragment fragment = CardFragment.newInstance(mCard.getId());
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.nav_host_fragment_content_main, fragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();


            }
        }
    }
}