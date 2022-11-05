package com.example.flashcards;

import static com.example.flashcards.MainActivity.GLOBAL_DECKS;
import static com.example.flashcards.MainActivity.TABLE_USERS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcards.database.entity.Card;
import com.example.flashcards.database.entity.FlashCard;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class NewCardsBank {
    private static NewCardsBank sBank;
    private FirebaseFirestore db;
    private final String TABLE_USERS = "users";
    private final String USER_ID;
    private final String TABLE_DECKS = "decks";
    private final String TABLE_CARDS = "cards";
    private final String GLOBAL_CARDS = "global_cards";

    private List<Card> cardsList;
    private List<Card> cardsGlobalList;

    public NewCardsBank(){
        db = FirebaseFirestore.getInstance();
        USER_ID = User.get().getId();
        openBank();
    }

    public static NewCardsBank get() {
        if(sBank == null) {
            sBank = new NewCardsBank();
        }
        return sBank;
    }

    public void regUserName() {
        Map<String, String> userName = new HashMap<>();
        userName.put("user_name", User.get().getName());
        db.collection(TABLE_USERS).document(USER_ID).set(userName);
    }

    public void getAllCard() {
        db.collection(TABLE_USERS).document(USER_ID).collection(TABLE_CARDS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    cardsList.clear();
                    for (QueryDocumentSnapshot dc: task.getResult()
                    ) {
                        cardsList.add(dc.toObject(Card.class));
                    }
                }
            }
        });
    }

    public List<Card> getCardsList() {
        return cardsList;
    }

    public List<Card> getCardsDeck(String deckName) {
        List<Card> result;
        result = cardsList.stream().filter(card -> card.getDeckName().equals(deckName)).collect(Collectors.toList());
        return result;
    }


    public void addCard(Card card) {
        cardsList.add(card);
        db.collection(TABLE_USERS)
                .document(USER_ID)
                .collection(TABLE_CARDS)
                .document(card.getWord())
                .set(card);

    }

    public void deleteCard(Card card) {
        cardsList.remove(card);
        db.collection(TABLE_USERS)
                .document(USER_ID)
                .collection(TABLE_CARDS)
                .document(card.getWord())
                .delete();
    }

    public void openBank() {
        cardsList = new ArrayList<>();
        cardsGlobalList = new ArrayList<>();
        getAllCard();
        regUserName();
    }

    public Card getCard(String cardName) {
        Card card = new Card();
        List<Card> newList = cardsList.stream().filter(value -> value.getWord().equals(cardName)).collect(Collectors.toList());
        if(newList.size() > 0) {
            card = newList.get(0);
        }
        return card;
    }

    public void sendCardsDeckToGlobal(String deckName) {
        List<Card> cardsDeck;
        cardsDeck = getCardsDeck(deckName);
        cardsDeck.forEach(card -> {
            db.collection(GLOBAL_CARDS)
                    .document()
                    .set(card);
        });

    }

    public List<Card> getGlobalCardsDeck() {
        return cardsGlobalList;
    }

    public void loadGlobalCards(String deckUserId) {
        db.collection(GLOBAL_CARDS)
                .whereEqualTo("userID", deckUserId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    cardsGlobalList.clear();
                    for (QueryDocumentSnapshot dc: task.getResult()
                    ) {
                        cardsGlobalList.add(dc.toObject(Card.class));
                    }
                }
            }
        });

    }
}
