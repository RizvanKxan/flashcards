package com.example.flashcards.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


import com.example.flashcards.MainActivity;
import com.example.flashcards.R;
import com.example.flashcards.databinding.FragmentHomeBinding;
import com.example.flashcards.ui.cards.CardsFragment;
import com.example.flashcards.ui.cards.DialogCreateCard;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    DialogFragment addCard;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Button btnCards = binding.homeBtnCards;
        btnCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCard = new DialogCreateCard();
                addCard.show(getFragmentManager(),"tag");
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}