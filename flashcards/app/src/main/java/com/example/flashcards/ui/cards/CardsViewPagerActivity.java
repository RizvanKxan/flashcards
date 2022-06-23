package com.example.flashcards.ui.cards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.flashcards.App;
import com.example.flashcards.CardsBank;
import com.example.flashcards.R;
import com.example.flashcards.database.entity.FlashCard;
import com.example.flashcards.databinding.ActivityCardsViewPagerBinding;
import com.example.flashcards.databinding.FragmentSlideshowBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardsViewPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<FlashCard> mCards;

public static Intent newIntent(Context context, long id) {
    Intent intent = new Intent(context, CardsViewPagerActivity.class);
    intent.putExtra("string1", id);
    return intent;
}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_view_pager);

        long id = (long) getIntent()
                .getSerializableExtra("string1");;

        mViewPager = (ViewPager) findViewById(R.id.cards_view_pager);
        mCards = new ArrayList<>();
        CardsBank.get().getCards(new CardsBank.Result<List<FlashCard>>() {
            @Override
            public void onSuccess(List<FlashCard> flashCards) {
                mCards.addAll(flashCards);
            }

            @Override
            public void onError(Exception exception) {

            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                FlashCard card = mCards.get(position);
                return CardFragment.newInstance(card.getId());
            }

            @Override
            public int getCount() {
                return mCards.size();
            }
        });

//        for (int i = 1; i < mCards.size(); i++) {
//            if((mCards.get(i).getId()) == id) {
//                FlashCard card = new FlashCard("sd", "sd");
//
//                mViewPager.setCurrentItem(i);
//                break;
//            }
//        }
    }
}
