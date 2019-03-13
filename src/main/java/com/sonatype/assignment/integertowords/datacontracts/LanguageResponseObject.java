package com.sonatype.assignment.integertowords.datacontracts;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LanguageResponseObject {
    private String wordInEnglish;
    private String errorMessage;

    public String getWordInEnglish() {
        return wordInEnglish;
    }

    public void setWordInEnglish(String wordInEnglish) {
        this.wordInEnglish = wordInEnglish;
    }


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
