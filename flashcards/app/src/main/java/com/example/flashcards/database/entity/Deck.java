package com.example.flashcards.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "deck")
public class Deck {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "deck_ID")
    private long deckID;

    @ColumnInfo(name = "card_ID")
    private long cardID;

    public Deck() {

    }

    public Deck(long deckID, long cardID) {
        this.deckID = deckID;
        this.cardID = cardID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDeckID() {
        return deckID;
    }

    public void setDeckID(long deckID) {
        this.deckID = deckID;
    }

    public long getCardID() {
        return cardID;
    }

    public void setCardID(long cardID) {
        this.cardID = cardID;
    }
}
