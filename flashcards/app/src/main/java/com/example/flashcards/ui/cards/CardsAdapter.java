package com.example.flashcards.ui.cards;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcards.MainActivity;
import com.example.flashcards.R;
import com.example.flashcards.database.entity.FlashCard;

import java.util.List;

public class CardsAdapter  extends RecyclerView.Adapter<CardsAdapter.CardsHolder> {

    private List<FlashCard> mCards;
    private int selectedPos = -1;

    public CardsAdapter(List<FlashCard> cards) {
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

        public CardsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));
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
            //Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            //startActivity(intent);
        }
    }
}

