# Integer to Word
## Converting a given integer to english words

### Swagger:
1) Click on language-controller
2) Click on GET endpoint - /api/integertoword/{input} integerToWord
3) Click 'Try it out' button
4) Enter a valid number in the 'input' 
5) Click 'Execute' button
6) Scroll down to see result in 'Response body'

### API:

1) Make following GET call to the REST API
```
http://localhost:8080/api/integertoword/12345
```
2) Expect response in 'wordInEnglish' JSON property. Example: Input 12345. Response:
```
Twelve thousand three hundred and forty five
```


### Limitations:
1) Maximum Number Processable: 2147483647
2) Minimum Number Processable: -2147483648
3) Input does not accept any special characters like decimals or commas
4) Only supports English language
5) Only supports Western Numbering System (Millions/Billions)