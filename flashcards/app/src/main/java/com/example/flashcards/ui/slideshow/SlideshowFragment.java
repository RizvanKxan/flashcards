package com.example.flashcards.ui.slideshow;

import static com.example.flashcards.ui.decks.DeckFragment.DECK_ID;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.TransitionAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.flashcards.R;
import com.example.flashcards.databinding.FragmentSlideshowBinding;
import com.example.flashcards.ui.home.HomeViewModel;

import java.util.UUID;

import java.util.UUID;

public class SlideshowFragment extends Fragment {
    private FragmentSlideshowBinding binding;
    private UUID deckId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            deckId = (UUID) getArguments().getSerializable(DECK_ID);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);
        slideshowViewModel.setDeckId(deckId);
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        final TextView tv = binding.tvText;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), text -> tv.setText(text));
        MotionLayout motion = binding.motionLayout;
        motion.setTransitionListener(new TransitionAdapter() {
            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
                super.onTransitionCompleted(motionLayout, currentId);

                if(currentId == R.id.offScreenPass || currentId == R.id.offScreenLike) {
//                    motionLayout.setTransition(R.id.start, R.id.like);
                    slideshowViewModel.swipe();
                }
            }
        });

        Button btn = binding.btnText;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(slideshowViewModel.isValueShown()) {
                    slideshowViewModel.getWord();
                } else {
                    slideshowViewModel.getValue();
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}