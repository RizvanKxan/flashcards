package com.example.flashcards;

import static com.example.flashcards.MainActivity.TABLE_USERS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcards.database.entity.Card;
import com.example.flashcards.database.entity.NewDeck;
import com.example.flashcards.database.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class NewDecksBank {
    private static NewDecksBank sBank;
    private FirebaseFirestore db;
    private final String TABLE_USERS = "users";
    private final String USER_ID;
    private final String TABLE_DECKS = "decks";
    private List<NewDeck> deckList;

    public NewDecksBank(){
        db = FirebaseFirestore.getInstance();
        USER_ID = User.get().getId();
        openBank();
    }

    public static NewDecksBank get() {
        if(sBank == null) {
            sBank = new NewDecksBank();
        }
        return sBank;
    }

    public void getAllDeck() {
        db.collection(TABLE_USERS).document(USER_ID).collection(TABLE_DECKS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    deckList.clear();
                    for (QueryDocumentSnapshot dc: task.getResult()
                         ) {
                        deckList.add(dc.toObject(NewDeck.class));
                    }
                }
            }
        });
    }

    public List<NewDeck> getDeck() {
        return deckList;
    }

    public void addDeck(NewDeck deck) {
        deckList.add(deck);
        db.collection(TABLE_USERS)
                .document(USER_ID)
                .collection(TABLE_DECKS)
                .document(deck.getName()).set(deck);
    }

    public void deleteDecks(NewDeck deck) {
        db.collection(TABLE_USERS).document(USER_ID).collection(TABLE_DECKS).document(deck.getName()).delete();
    }

    public void openBank() {
        deckList = new ArrayList<NewDeck>();
        getAllDeck();
    }
}
