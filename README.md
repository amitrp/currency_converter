## Currency Converter
##### Libraries/Plug-ins Involved
- Java 8
- Maven
- Maven Wrapper
- Spring Boot
- Google Guava Table ([docs](https://google.github.io/guava/releases/19.0/api/docs/com/google/common/collect/Table.html))
- JUnit & Mockito

### Rates and References

#### Currency Rates
The given currency rates are pulled out from `src/main/resources/input/currency_rates.txt`

**Example of currency_rates.txt**
```$xslt
AUDUSD=0.8371
CADUSD=0.8711
USDCNY=6.1715
```

#### Currency Cross References
The given cross references are pulled from `src/main/resources/input/currency_cross_references.txt`
The file does not include any inverse references. Those are calculated runtime. 
If a conversion requires multiple cross references the direct rates and cross references are used recursively. 

**Example of currency_cross_references.txt**
```$xslt
AUDCAD=USD
AUDCNY=USD
AUDCZK=USD
AUDDKK=USD
AUDEUR=USD
AUDGBP=USD
AUDJPY=USD
AUDNOK=USD
AUDNZD=USD
```
### Currency Formats
The given currency format (decimal places) are pulled from `src/main/resources/input/currency_formats.txt`
Once the currency conversion is done and before printing the result required decimal places are looked up based on the 
output currency and used for formatting the decimal places. 
Default decimal places are 2.

**Example of currency_formats.txt**
```$xslt
AUD=2
CAD=2
CNY=2
CZK=2
```

### Running Project Locally
It is a maven based Spring-Boot application includes maven wrapper feature. 

##### Running Unit Tests
```aidl
./mvnw clean test
```

##### Starting Application
```$xslt
./mvnw spring-boot:run

```

##### Converting Currency
Once the application is started, currency conversion command can be provided like below
```$xslt
> AUD 100.00 in USD
```
Any invalid input will result in `invalid input` response

##### Exiting the Application
The application will keep on asking for next input. To exit the application simply type `ctl + c` or `exit`

```aidl
> exit
```
