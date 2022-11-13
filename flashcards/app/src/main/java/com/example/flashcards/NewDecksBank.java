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
import java.util.stream.Collectors;

public class NewDecksBank {
    private static NewDecksBank sBank;
    private FirebaseFirestore db;
    private final String TABLE_USERS = "users";
    private final String USER_ID;
    private final String TABLE_DECKS = "decks";
    private final String TABLE_GLOBAL_DECKS = "global_decks";
    private String userName;
    private List<NewDeck> deckList;
    private List<NewDeck> deckGlobalList;

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

    public void loadGlobalDeck() {
        db.collection(TABLE_GLOBAL_DECKS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    deckGlobalList.clear();
                    for (QueryDocumentSnapshot dc: task.getResult()
                    ) {
                        deckGlobalList.add(dc.toObject(NewDeck.class));
                    }
                }
            }
        });
    }

    public List<NewDeck> getDeck() {
        return deckList;
    }

    public List<NewDeck> getGlobalDeck() {
        return deckGlobalList;
    }

    public void addDeck(NewDeck deck) {
        deckList.add(deck);

        db.collection(TABLE_USERS)
                .document(USER_ID)
                .collection(TABLE_DECKS)
                .document(deck.getName()).set(deck);
    }

    public void deleteDecks(NewDeck deck) {
        deckList.remove(deck);
        db.collection(TABLE_USERS).document(USER_ID).collection(TABLE_DECKS).document(deck.getName()).delete();
    }

    public void sendDeckToGlobal(String nameDeck) {
        NewDeck deck = getDeckByName(nameDeck);
        db.collection(TABLE_GLOBAL_DECKS).document().set(deck);
    }

    public NewDeck getDeckByName(String nameDeck) {
        return deckList.stream().filter(d -> d.getName().equals(nameDeck)).collect(Collectors.toList()).get(0);
    }
    public void openBank() {
        deckList = new ArrayList<NewDeck>();
        deckGlobalList = new ArrayList<>();
        getAllDeck();
        loadGlobalDeck();
    }

    public void getUserNameById(String userId) {
        db.collection(TABLE_USERS).document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    userName = task.getResult().get("user_name").toString();
                }
            }
        });
    }

    public String getName() {
        return userName;
    }
}
