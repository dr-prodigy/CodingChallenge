# CodingChallenge
### Coding Assignment for Credit Suisse - Developer Tools & Services

The application initializes a HSQL database table, converts and persists JSON log data (input/sample.json)
to DB, marking log items with alert = true if duration is greater than 4ms.
After processing, all the processed events are logged.
Along with the code and gradle build configuration, Spock-based unit tests are provided.

## Commands to download, build and run sw

```
git clone https://github.com/dr-prodigy/CodingChallenge.git
cd CodingChallenge
gradlew build
gradlew fatJar
java -jar build\libs\CodingChallenge-fatJar-1.0-SNAPSHOT.jar
```

Upon running, results will be available on CodingChallenge_log.log
