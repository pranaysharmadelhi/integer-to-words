package com.sonatype.assignment.integertowords.services.language;

import com.sonatype.assignment.integertowords.services.language.db.scale.Scales;
import com.sonatype.assignment.integertowords.services.language.db.scale.ScalesRepository;
import com.sonatype.assignment.integertowords.services.language.db.small_numbers.SmallNumbers;
import com.sonatype.assignment.integertowords.services.language.db.small_numbers.SmallNumbersRepository;
import com.sonatype.assignment.integertowords.services.language.db.tens.Tens;
import com.sonatype.assignment.integertowords.services.language.db.tens.TensRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LanguageService {

    @Autowired
    ScalesRepository scalesRepository;

    @Autowired
    TensRepository tensRepository;

    @Autowired
    SmallNumbersRepository smallNumbersRepository;

    //Set Default Numbering System to 1
    private static final int numberSystemId = 1;

    private static final String andWord = "and";

    private static final Logger logger = LoggerFactory.getLogger(LanguageService.class);

    public String getWordForInteger(Integer number) {
        logger.debug("Request received for getWordForInteger with parameter: " + number);
        String word = "";
        List<Tens> tensList = tensRepository.findAll();
        List<SmallNumbers> smallNumbersList = smallNumbersRepository.findAll();
        Boolean isNegative = false;
        if (number == 0) {
            logger.debug("Input Number is zero, return word Zero from db");
            word = smallNumbersList.stream().filter(x -> x.getNumber() == 0).findFirst().get().getWordInEnglish();
        } else {
            logger.debug("Input Number is not zero");
            if (number < 0) {
                logger.debug("Number is negative");
                isNegative = true;
                number = Math.abs(number);
            }
            int significantDigits = number.toString().length();
            logger.debug("Total Significant digits: " + significantDigits);

            logger.debug("Get all scales / denominations ");
            List<Scales> scales = scalesRepository.findAllByNumberingSystemIdAndNoOfZerosStartBeforeOrderByNoOfZerosStartDesc(numberSystemId, significantDigits);

            logger.debug("Loop over all scales to generate full word in english");
            for (Scales scale : scales) {
                logger.debug("Get word for scale that starts at digit: " + scale.getNoOfZerosStart() + " with maxLength: " + scale.getMaxLength() + " called " + scale.getWordInEnglish());
                String nextWords = getWordForScale(scale, getNumberForScale(scale, number), tensList, smallNumbersList);
                if (!nextWords.trim().isEmpty()) {
//                    if (!word.trim().isEmpty()) {
//                        logger.debug("Adding comma");
//                        word = word.trim();
//                        word += ", ";
//                    }
                    logger.debug("Add word: " + nextWords);
                    word += nextWords;
                }
            }
        }
        word = word.trim();
        if (!word.isEmpty()) {
            if (isNegative) {
                logger.debug("Negative number. Add word 'negative'");
                word = "Negative " + word;
            }
            logger.debug("Capitalize final sentence");
            word = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
        }
        logger.debug("Return final word: " + word);
        return word;
    }

    public String getWordForScale(Scales scales, Integer number, List<Tens> tensList, List<SmallNumbers> smallNumbersList) {
        String word = "";
        logger.debug("Split number according to scales in tens/hundreds depending on number system");

        Integer[] splittedNumber = getSplittedNumber(number, scales.getMaxLength());
        int small = splittedNumber[0];
        int tens = splittedNumber[1];
        if (scales.getMaxLength() >= 3) {
            int hundreds = splittedNumber[2];
            if (hundreds > 0) {
                logger.debug("hundredth number is present, add word 'hundred'");

                word += smallNumbersList.stream()
                        .filter(x -> x.getNumber() == hundreds)
                        .findFirst()
                        .get()
                        .getWordInEnglish() +
                        " hundred ";
            }
        }
        if (tens > 0) {
            if (!word.trim().isEmpty()) {
                logger.debug("Tens word is present, add word 'and'");
                word += andWord + " ";
            }
            word += tensList.stream()
                    .filter(x -> x.getNumber() == tens)
                    .findFirst()
                    .get()
                    .getWordInEnglish()
                    + " ";
        }
        if (small > 0) {
            if (!word.trim().isEmpty() && tens == 0) {
                logger.debug("Tens word was not present, add word 'and'");
                word += andWord + " ";
            }
            word += smallNumbersList.stream()
                    .filter(x -> x.getNumber() == small)
                    .findFirst()
                    .get()
                    .getWordInEnglish() +
                    " ";
        }
        if (!word.trim().isEmpty() && !scales.getWordInEnglish().trim().isEmpty()) {
            logger.debug("Add the scale word: " + scales.getWordInEnglish());
            word += scales.getWordInEnglish() + " ";
        }

        return word;
    }

    public Integer getNumberForScale(Scales scale, Integer number) {
        logger.debug("getNumberForScale sub number for scale: " + scale);

        int startIndex = number.toString().length() - (scale.getNoOfZerosStart() + scale.getMaxLength());
        if (startIndex < 0) {
            logger.debug("Reached the beginning of a number");
            startIndex = 0;
        }
        int endIndex = number.toString().length() - scale.getNoOfZerosStart();

        logger.debug("Get substring of a number for this scale.");
        String newNumber = number.toString().substring(startIndex, endIndex);

        logger.debug("Substring: " + newNumber + " generated for scale " + scale.getWordInEnglish());

        return Integer.parseInt(newNumber);
    }



    public Integer[] getSplittedNumber(Integer number, int maxDigits) {
        logger.debug("Split number: " + number + " into hundreds/tens/small digits upto: " + maxDigits + " digits.");

        logger.debug("Create an array with length of maxDigits");
        Integer[] splittedNumber = new Integer[maxDigits];

        int scale = (int) Math.round(Math.pow(10, maxDigits));
        logger.debug("Maximum Scale for this denomination is: " + scale);

        int subNumber = number % scale;
        logger.debug("Sub Number: " + subNumber);

        //ToDo - Below code can be wrapped in a loop to extend wording of large numbers whose words dont' exists, for eg: novenonagintanongentillion. This is out of scope

        if (maxDigits >= 3) {
            logger.debug("This scale needs a hundreds digit.");
            splittedNumber[2] = subNumber / 100;
            logger.debug("Hundreds digit: " + splittedNumber[2]);
        }

        splittedNumber[1] = (subNumber % 100);

        if (splittedNumber[1] >= 10 && splittedNumber[1] < 20) {
            logger.debug("Tens digit is a 'teen'. Set it as a small number.");
            splittedNumber[0] = splittedNumber[1];
            splittedNumber[1] = 0;
        } else {
            splittedNumber[0] = subNumber % 10;
            splittedNumber[1] = (splittedNumber[1] / 10) * 10;
        }

        logger.debug("Tens digit: " + splittedNumber[1]);
        logger.debug("Small digit: " + splittedNumber[0]);

        return splittedNumber;
    }
}
