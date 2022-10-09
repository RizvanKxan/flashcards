package com.example.flashcards.database.entity;

public class Card {

    private String word;
    private String value;
    private String deckName;
    private String userID;

    public Card(){};

    public Card(String word, String value, String deckName) {
        this.word = word;
        this.value = value;
        this.deckName = deckName;
        userID = User.get().getId();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
