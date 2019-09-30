# Me :) Bank Retail Banking Application

The financial transaction analyser

## Installation

This is IntelliJ exported Project - 

```bash
 mvn -Dmaven.wagon.http.ssl.insecure=true clean install
 ```
 NOTE : -Dmaven.wagon.http.ssl.insecure=true only to use if you get SSL verification exception if you are building project behind proxy.
 This is to enable/disable use of relaxed ssl check for user generated certificates.
    

## Usage

```
Float and Double is not recommended for banking transactions.
Even BigDecimal has its own cavities so I prefer to use JSR 354: Money and Currency API, 
which provides an API for representing, transporting, and performing comprehensive calculations with Money and Currency.

[JSR-354](https://jcp.org/en/jsr/detail?id=354)


There is InternetBankingApplication.java main class to try some inputs manually.

TransactionServiceTest class for Junit tests.
```

## Assumptions to show my understanding of best practices

```Assumptions
1. Just added one Business Validation exception, Reversal transaction related txn Id should exist in Database && fromAccount && toAccount should be same of both txnIDs otherwise thorws BusinessException
2. Data validation like number of data columns available, Optional Check for related transaction columns
3. I added a attribute in TransactionRequest to consider this analysis with or without reversal transactions : transactionRequest.setWithReversal(true);
4. We could have added balance service as well if the use cases would have more complicated
5. Still there is scope to add Controller layer before service if the we get a requirement for REST Apis but for that we will need to use Frameworks/libraries like SpringBoot and MVC.

```


## Contributing
Pull requests/review/ comments are welcome. 
Please make sure to update tests as appropriate.

## License
[ME](https://mebank.com.au)