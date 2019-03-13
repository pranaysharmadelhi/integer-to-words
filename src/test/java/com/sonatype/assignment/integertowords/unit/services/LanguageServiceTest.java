package com.sonatype.assignment.integertowords.unit.services;

import com.sonatype.assignment.integertowords.IntegerToWordsApplication;
import com.sonatype.assignment.integertowords.services.language.LanguageService;
import com.sonatype.assignment.integertowords.services.language.db.scale.Scales;
import com.sonatype.assignment.integertowords.services.language.db.scale.ScalesRepository;
import com.sonatype.assignment.integertowords.services.language.db.small_numbers.SmallNumbers;
import com.sonatype.assignment.integertowords.services.language.db.small_numbers.SmallNumbersRepository;
import com.sonatype.assignment.integertowords.services.language.db.tens.Tens;
import com.sonatype.assignment.integertowords.services.language.db.tens.TensRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegerToWordsApplication.class })
@WebAppConfiguration
public class LanguageServiceTest {

    private List<Tens> tensList = new ArrayList<>();
    private List<SmallNumbers> smallNumbersList = new ArrayList<>();

    @InjectMocks
    private LanguageService service;

    @Autowired
    TensRepository tensRepository;

    @Autowired
    SmallNumbersRepository smallNumbersRepository;

    @Autowired
    ScalesRepository scalesRepository;

    @Before
    public void setup() {

        initMocks(this);

        ReflectionTestUtils.setField(service, "tensRepository", tensRepository);
        ReflectionTestUtils.setField(service, "smallNumbersRepository", smallNumbersRepository);
        ReflectionTestUtils.setField(service, "scalesRepository", scalesRepository);

        int cn = 1;
        for (int i = 20; i <= 90; i = i + 10) {
            Tens tens = new Tens();
            tens.setId(cn++);
            tens.setNumber(i);
            String tenWord = "";
            switch (i) {
                case 20:
                    tenWord = "twenty";
                    break;
                case 30:
                    tenWord = "thirty";
                    break;
                case 40:
                    tenWord = "forty";
                    break;
                case 50:
                    tenWord = "fifty";
                    break;
                case 60:
                    tenWord = "sixty";
                    break;
                case 70:
                    tenWord = "seventy";
                    break;
                case 80:
                    tenWord = "eighty";
                    break;
                case 90:
                    tenWord = "ninety";
                    break;
            }
            tens.setWordInEnglish(tenWord);
            tensList.add(tens);
        }

        for (int i = 0; i <= 19; i++) {
            SmallNumbers smallNumbers = new SmallNumbers();
            smallNumbers.setId(cn++);
            smallNumbers.setNumber(i);
            String smallNumber = "";
            switch (i) {
                case 0:
                    smallNumber = "zero";
                    break;
                case 1:
                    smallNumber = "one";
                    break;
                case 2:
                    smallNumber = "two";
                    break;
                case 3:
                    smallNumber = "three";
                    break;
                case 4:
                    smallNumber = "four";
                    break;
                case 5:
                    smallNumber = "five";
                    break;
                case 6:
                    smallNumber = "six";
                    break;
                case 7:
                    smallNumber = "seven";
                    break;
                case 8:
                    smallNumber = "eight";
                    break;
                case 9:
                    smallNumber = "nine";
                    break;
                case 10:
                    smallNumber = "ten";
                    break;
                case 11:
                    smallNumber = "eleven";
                    break;
                case 12:
                    smallNumber = "twelve";
                    break;
                case 13:
                    smallNumber = "thirteen";
                    break;
                case 14:
                    smallNumber = "fourteen";
                    break;
                case 15:
                    smallNumber = "fifteen";
                    break;
                case 16:
                    smallNumber = "sixteen";
                    break;
                case 17:
                    smallNumber = "seventeen";
                    break;
                case 18:
                    smallNumber = "eighteen";
                    break;
                case 19:
                    smallNumber = "nineteen";
                    break;
            }
            smallNumbers.setWordInEnglish(smallNumber);
            smallNumbersList.add(smallNumbers);
        }
    }

    @After
    public void cleanUp() {
    }


    @Test
    public void testGetWordForInteger() throws Exception {
        assertThat(service.getWordForInteger(2147483647).trim()).isEqualTo("Two billion one hundred and forty seven million four hundred and eighty three thousand six hundred and forty seven");
        assertThat(service.getWordForInteger(-2147483647).trim()).isEqualTo("Negative two billion one hundred and forty seven million four hundred and eighty three thousand six hundred and forty seven");
    }

    @Test
    public void testGetNumberForScaleForHundreds() throws Exception {
        Scales scale = new Scales();
        scale.setId(1);
        scale.setNoOfZerosStart(0);
        scale.setMaxLength(3);
        scale.setNumberingSystemId(1);
        scale.setWordInEnglish("");

        assertThat(service.getNumberForScale(scale, 1234567890)).isEqualTo(890);
    }

    @Test
    public void testGetNumberForScaleForThousands() throws Exception {
        Scales scale = new Scales();
        scale.setId(1);
        scale.setNoOfZerosStart(3);
        scale.setMaxLength(3);
        scale.setNumberingSystemId(1);
        scale.setWordInEnglish("Thousand");

        assertThat(service.getNumberForScale(scale, 1234567890)).isEqualTo(567);
    }

    @Test
    public void testGetNumberForScaleForMillions() throws Exception {
        Scales scale = new Scales();
        scale.setId(1);
        scale.setNoOfZerosStart(6);
        scale.setMaxLength(3);
        scale.setNumberingSystemId(1);
        scale.setWordInEnglish("Million");

        assertThat(service.getNumberForScale(scale, 1234567890)).isEqualTo(234);
    }

    @Test
    public void testGetNumberForScaleForBillions() throws Exception {
        Scales scale = new Scales();
        scale.setId(1);
        scale.setNoOfZerosStart(9);
        scale.setMaxLength(3);
        scale.setNumberingSystemId(1);
        scale.setWordInEnglish("Million");

        assertThat(service.getNumberForScale(scale, 1234567890)).isEqualTo(1);
    }

    @Test
    public void testGetSplittedNumber() throws Exception {

        assertThat(service.getSplittedNumber(123, 3)).containsExactly(3, 20, 1);

        assertThat(service.getSplittedNumber(1, 3)).containsExactly(1, 0, 0);

        assertThat(service.getSplittedNumber(10, 3)).containsExactly(10, 0, 0);

        assertThat(service.getSplittedNumber(9, 3)).containsExactly(9, 0, 0);

        assertThat(service.getSplittedNumber(11, 3)).containsExactly(11, 0, 0);

        assertThat(service.getSplittedNumber(100, 3)).containsExactly(0, 0, 1);


        assertThat(service.getSplittedNumber(1, 2)).containsExactly(1, 0);

        assertThat(service.getSplittedNumber(10, 2)).containsExactly(10, 0);

        assertThat(service.getSplittedNumber(9, 2)).containsExactly(9, 0);

        assertThat(service.getSplittedNumber(11, 2)).containsExactly(11, 0);

    }


    @Test
    public void testGetWordForScaleThousands() throws Exception {

        Scales scale = new Scales();
        scale.setId(1);
        scale.setNoOfZerosStart(3);
        scale.setMaxLength(3);
        scale.setNumberingSystemId(1);
        scale.setWordInEnglish("thousand");

        assertThat(service.getWordForScale(scale, 0, tensList, smallNumbersList).trim()).isEqualTo("");

        assertThat(service.getWordForScale(scale, 1, tensList, smallNumbersList).trim()).isEqualTo("one thousand");

        assertThat(service.getWordForScale(scale, 10, tensList, smallNumbersList).trim()).isEqualTo("ten thousand");

        assertThat(service.getWordForScale(scale, 12, tensList, smallNumbersList).trim()).isEqualTo("twelve thousand");

        assertThat(service.getWordForScale(scale, 120, tensList, smallNumbersList).trim()).isEqualTo("one hundred and twenty thousand");

        assertThat(service.getWordForScale(scale, 123, tensList, smallNumbersList).trim()).isEqualTo("one hundred and twenty three thousand");

    }


    @Test
    public void testGetWordForScaleMillions() throws Exception {

        Scales scale = new Scales();
        scale.setId(1);
        scale.setNoOfZerosStart(9);
        scale.setMaxLength(3);
        scale.setNumberingSystemId(1);
        scale.setWordInEnglish("million");

        assertThat(service.getWordForScale(scale, 0, tensList, smallNumbersList).trim()).isEqualTo("");

        assertThat(service.getWordForScale(scale, 1, tensList, smallNumbersList).trim()).isEqualTo("one million");

        assertThat(service.getWordForScale(scale, 10, tensList, smallNumbersList).trim()).isEqualTo("ten million");

        assertThat(service.getWordForScale(scale, 12, tensList, smallNumbersList).trim()).isEqualTo("twelve million");

        assertThat(service.getWordForScale(scale, 120, tensList, smallNumbersList).trim()).isEqualTo("one hundred and twenty million");

        assertThat(service.getWordForScale(scale, 123, tensList, smallNumbersList).trim()).isEqualTo("one hundred and twenty three million");

    }




    @Test
    public void testGetWordForScaleBillions() throws Exception {

        Scales scale = new Scales();
        scale.setId(1);
        scale.setNoOfZerosStart(12);
        scale.setMaxLength(3);
        scale.setNumberingSystemId(1);
        scale.setWordInEnglish("billion");

        assertThat(service.getWordForScale(scale, 0, tensList, smallNumbersList).trim()).isEqualTo("");

        assertThat(service.getWordForScale(scale, 1, tensList, smallNumbersList).trim()).isEqualTo("one billion");

        assertThat(service.getWordForScale(scale, 10, tensList, smallNumbersList).trim()).isEqualTo("ten billion");

        assertThat(service.getWordForScale(scale, 12, tensList, smallNumbersList).trim()).isEqualTo("twelve billion");

        assertThat(service.getWordForScale(scale, 120, tensList, smallNumbersList).trim()).isEqualTo("one hundred and twenty billion");

        assertThat(service.getWordForScale(scale, 123, tensList, smallNumbersList).trim()).isEqualTo("one hundred and twenty three billion");

    }


}
