package com.example.flashcards.ui.cards;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flashcards.CardsBank;
import com.example.flashcards.R;
import com.example.flashcards.database.entity.FlashCard;
import com.example.flashcards.databinding.FragmentCardListBinding;
import com.example.flashcards.databinding.FragmentGalleryBinding;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.flashcards.databinding.FragmentCardListBinding;

public class CardsFragment extends Fragment {
    private FragmentCardListBinding binding;
    private CardsViewModel mViewModel;
    private RecyclerView mCardsRecyclerView;
    private CardsAdapter cardsAdapter;

    public static CardsFragment newInstance() {
        return new CardsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
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
//        cards.add(new FlashCard("text", "value"));
//        cards.add(new FlashCard("text2", "value"));
//        cards.add(new FlashCard("text3", "value"));
        cardsAdapter = new CardsAdapter(cards);
        mCardsRecyclerView.setAdapter(cardsAdapter);
        cardsAdapter.notifyDataSetChanged();

        // TODO: Use the ViewModel
    }

    // ViewHolder который будет заполнять наш макет. Весь код, выполняющий работу по
    // связыванию, располагается здесь.
    private class CardsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private FlashCard mCard;

        public CardsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));

            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.item_card_tv_name);
        }

        // Метод будет вызываться каждый раз, когда RecyclerView потребует связать
        // данный объект CrimeHolder с объектом конкретного преступления.
        public void bind(FlashCard card) {
            mCard = card;
        }

        @Override
        public void onClick(View view) {
            //Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            //startActivity(intent);
        }
    }

    // Adapter с которым будет взаимодействовать наш RecyclerView, когда ему
    // потребуется создать ViewHolder или связать его с данными(Crime).
    // Сам RecyclerView ничего не знает о Crime.
    private class CardsAdapter extends RecyclerView.Adapter<CardsHolder> {

        private List<FlashCard> mCards;

        public CardsAdapter(List<FlashCard> cards) {
            mCards = cards;
        }

        @NonNull
        @Override
        public CardsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CardsHolder(layoutInflater, parent);
        }

        // Чем проще реализация - тем плавнее пракрутка RecyclerView. Если связывание
        // сделать менее эфективным и громоздким, тем выше вероятность запинок и рывков
        // при прокрутке.
        @Override
        public void onBindViewHolder(@NonNull CardsHolder holder, int position) {
            FlashCard card = mCards.get(position);
            holder.bind(card);
        }

        @Override
        public int getItemCount() {
            return mCards.size();
        }

        public void setCrimes(List<FlashCard> cards) {
            mCards = cards;
        }
    }
}