package com.sonatype.assignment.integertowords.services.language.db.scale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Scales {

    @Id
    @GeneratedValue
    private int id;
    private int noOfZerosStart;
    private int numberingSystemId;
    private int maxLength;
    private String wordInEnglish;


    public String getWordInEnglish() {
        return wordInEnglish;
    }

    public void setWordInEnglish(String wordInEnglish) {
        this.wordInEnglish = wordInEnglish;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getNoOfZerosStart() {
        return noOfZerosStart;
    }

    public void setNoOfZerosStart(int noOfZerosStart) {
        this.noOfZerosStart = noOfZerosStart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberingSystemId() {
        return numberingSystemId;
    }

    public void setNumberingSystemId(int numberingSystemId) {
        this.numberingSystemId = numberingSystemId;
    }
}