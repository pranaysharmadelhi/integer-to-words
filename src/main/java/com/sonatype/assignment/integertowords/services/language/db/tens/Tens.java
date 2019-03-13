package com.sonatype.assignment.integertowords.services.language.db.tens;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Tens {

    @Id
    @GeneratedValue
    private int id;

    private int number;

    private String wordInEnglish;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getWordInEnglish() {
        return wordInEnglish;
    }

    public void setWordInEnglish(String wordInEnglish) {
        this.wordInEnglish = wordInEnglish;
    }
}