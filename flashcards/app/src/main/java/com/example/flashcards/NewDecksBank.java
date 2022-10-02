package com.example.flashcards;

import static com.example.flashcards.MainActivity.TABLE_USERS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.flashcards.database.entity.Card;
import com.example.flashcards.database.entity.NewDeck;
import com.example.flashcards.database.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class NewDecksBank {
    private static NewDecksBank sBank;
    private FirebaseFirestore db;

    public NewDecksBank(){
        db = FirebaseFirestore.getInstance();
    }

    public static NewDecksBank get() {
        if(sBank == null) {
            sBank = new NewDecksBank();
        }
        return sBank;
    }

    List<Card> list = new ArrayList<>();
    Card card1 = new Card();
    public void getDeck() {
        DocumentReference docRef = db.collection(TABLE_USERS).document(User.get().getId()).collection("deck1").document("card1");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Card tem1 = new Card();
                if(task.isComplete()){
                    tem1 = task.getResult().toObject(Card.class);
                    list.add(tem1);
                }
            }
        });
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Card tem1 = new Card();
                tem1 = documentSnapshot.toObject(Card.class);
                list.add(tem1);
            }
        });
    }

    public void addDeck(NewDeck deck) {

    }
    public List<Card> getDeck2() {
        return list;
    }
}
