package com.example.flashcards.ui.slideshow;

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

public class SlideshowFragment extends Fragment {
    private FragmentSlideshowBinding binding;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        FrameLayout fr = binding.topCard;
        //fr.setBackgroundColor(Color.CYAN);
        FrameLayout fr2 = binding.bottomCard;
        //fr2.setBackgroundColor(Color.CYAN);
        final TextView tv = binding.tvText;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), tv::setText);
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