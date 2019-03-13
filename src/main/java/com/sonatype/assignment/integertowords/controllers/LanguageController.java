package com.sonatype.assignment.integertowords.controllers;

import com.sonatype.assignment.integertowords.datacontracts.LanguageResponseObject;
import com.sonatype.assignment.integertowords.services.language.LanguageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LanguageController {

    private static final Logger logger = LoggerFactory.getLogger(LanguageController.class);

    @Autowired
    LanguageService languageService;

    @GetMapping(value = "/integertoword/{input}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LanguageResponseObject> integerToWord(@PathVariable String input) {
        logger.debug("Request received for integerToWord with parameter: " + input);
        LanguageResponseObject responseObject = new LanguageResponseObject();
        try {
            logger.debug("Try parsing: " + input);
            Integer number = Integer.parseInt(input);
            logger.debug("Parsed successfully. Call languageService.getWordForInteger with parameter: " + number);
            responseObject.setWordInEnglish(languageService.getWordForInteger(number));
            return new ResponseEntity<>(responseObject, HttpStatus.OK);

        } catch (NumberFormatException e) {
            logger.error("Error parsing number. Send 400 error");
            responseObject.setErrorMessage("Invalid number or out of range");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }
}
