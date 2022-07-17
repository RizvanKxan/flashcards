package com.example.flashcards.database.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "deck", foreignKeys = {
        @ForeignKey(
                entity = Decks.class,
                parentColumns = "id",
                childColumns = "deck_ID",
                onDelete = CASCADE,
                onUpdate = CASCADE
        ),
        @ForeignKey(
                entity = FlashCard.class,
                parentColumns = "id",
                childColumns = "card_ID",
                onDelete = CASCADE,
                onUpdate = CASCADE
        )
})
public class Deck {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id")
    private UUID id = UUID.randomUUID();

    @ColumnInfo(name = "deck_ID")
    private UUID deckID;

    @ColumnInfo(name = "card_ID")
    private UUID cardID;

    @Ignore
    private final UUID defaultUUID = UUID.randomUUID();

    public Deck() {

    }

    public Deck(UUID deckID, UUID cardID) {
        this.deckID = deckID;
        this.cardID = cardID;
    }

    @NonNull
    public UUID getId() {
        return id;
    }

    public void setId(@NonNull UUID id) {
        this.id = id;
    }

    public UUID getDeckID() {
        if (deckID == null) {
            return defaultUUID;
        }
        return deckID;
    }

    public void setDeckID(UUID deckID) {
        this.deckID = deckID;
    }

    public UUID getCardID() {
        return cardID;
    }

    public void setCardID(UUID cardID) {
        this.cardID = cardID;
    }
}
