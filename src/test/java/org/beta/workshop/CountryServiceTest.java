package org.beta.workshop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CountryServiceTest {
    @Autowired
    CountryService countryService;

    @Test
    void testGetCapital() {
        String capital = countryService.getCapital("Romania");
        assertThat(capital).isEqualTo("Bucharest");
    }

    @Test
    void testGetPopulationOfACountry() {
        Long population = countryService.getPopulationOfACountry("Romania");
        assertThat(population).isEqualTo(19861408);
    }

    @Test
    void testIfCountryInputIsNull() {
        var exception = assertThrows(RuntimeException.class, () -> countryService.getPopulationOfACountry(null));
        assertThat(exception).hasMessage("Country name cannot be null");
    }

    @Test
    void testGetCountriesInAContinent() {
        List<Country> countries = countryService.getCountriesInAContinent("Europe");
        assertThat(countries).isNotEmpty();
        assertThat(countries.size()).isEqualTo(52);
    }

    @Test
    void testGetCountryNeighbours() {
        List<String> neighbours = countryService.getCountryNeighbours("Romania");
        assertThat(neighbours).isNotEmpty();
        assertThat(neighbours.size()).isEqualTo(5);
    }

    @Test
    void testGetCountryNeughboursIfInputIsNull() {
        var exception = assertThrows(RuntimeException.class, () -> countryService.getCountryNeighbours(null));
        assertThat(exception).hasMessage("Country name cannot be null");
    }

    @Test
    void testGetCountriesInContinentWithPopulationLargerThan() {
        List<Country> countries = countryService.getCountriesInContinentWithPopulationLargerThan("Oceania", 0L);
        assertThat(countries).isNotEmpty();
        assertThat(countries.size()).isEqualTo(27);
    }

    @Test
    void testIfContinentOrPopulationIsNull() {
        var exception = assertThrows(RuntimeException.class, () -> countryService.getCountriesInContinentWithPopulationLargerThan(null, null));
        assertThat(exception).hasMessage("Continent or populaltion cannot be null");
    }

    @Test
    void testFilterCountryByNeighbours(){
        List<Country> countries = countryService.filterCountryByNeighbours("Hungary","Belgium");
        assertThat(countries).isNotEmpty();
        assertThat(countries.size()).isEqualTo(6);
    }
}

