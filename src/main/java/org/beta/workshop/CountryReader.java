package org.beta.workshop;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.util.Collections.singletonList;
import static java.util.regex.Pattern.quote;

@Component
public class CountryReader {
    public CountryReader() {
        System.out.println("Creating CountryReader");
    }

    @SneakyThrows
    public List<Country> read() {
        return Files.lines(Path.of("/Users/patricktocut/Documents/projects/curs18-workshop/workshop/src/main/resources/countries.txt"))
                .map(this::mapToCountry)
                .toList();
    }

    private Country mapToCountry(String line) {
        String[] splitArray = line.split(quote("|"));
        Country country = Country.builder()
                .name(splitArray[0])
                .capital(splitArray[1])
                .population(parseInt(splitArray[2]))
                .area(parseInt(splitArray[3]))
                .continent(splitArray[4])
                .build();

        if (splitArray.length == 6) {
            List<String> neighbours = List.of(splitArray[5].split("~"));
            return country.withNeighbours(neighbours);
        }
        return country;
    }
}
