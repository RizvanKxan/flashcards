package com.example.flashcards.database.entity;

public class Card {

    private long id;
    private String word;
    private String value;

    public Card(){};

    public Card(long id, String word, String value) {
        this.id = id;
        this.word = word;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
