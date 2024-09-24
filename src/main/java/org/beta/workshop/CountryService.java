package org.beta.workshop;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CountryService {
    private final List<Country> countries = new ArrayList<>();


    public CountryService(CountryReader countryReader) {
        System.out.println("Creating service");
        this.countries.addAll(countryReader.read());
    }

    public List<Country> listAllCountries() {
        return countries;
    }

    public List<String> listAllCountriesNames() {
        return countries.stream()
                .map(Country::name)
                .toList();
    }

    public String getCapital(String countryName) {
        Optional<String> capitalOptional = countries.stream()
                .filter(country -> country.name().equalsIgnoreCase(countryName))
                .map(country -> country.capital())
                .findFirst();
        if (capitalOptional.isPresent()) {
            return capitalOptional.get();
        } else {
            throw new RuntimeException();
        }

    }

    public Long getPopulationOfACountry(String countryName) {
        if(countryName == null){
            throw new IllegalArgumentException("Country name cannot be null");
        }
        Optional<Long> population = countries.stream()
                .filter(country -> country.name().equalsIgnoreCase(countryName))
                .map(country -> Long.valueOf(country.population()))
                .findFirst();
        if (population.isPresent()) {
            return population.get();
        } else {
            throw new RuntimeException();
        }
    }

    public List<Country> getCountriesInAContinent(String continentName){
        return countries.stream()
                .filter(country -> country.continent().equalsIgnoreCase(continentName))
                .toList();
    }

    public List<String> getCountryNeighbours(String countryName){
        if(countryName == null){
            throw new IllegalArgumentException("Country name cannot be null");
        }
        return countries.stream()
                .filter(country -> country.name().equalsIgnoreCase(countryName))
                .flatMap(country -> country.neighbours().stream())
                .toList();
    }

    public List<Country> getCountriesInContinentWithPopulationLargerThan(String continent, Long population){
        if(continent == null || population == null){
            throw new IllegalArgumentException("Continent or populaltion cannot be null");
        }
        return countries.stream()
                .filter(country -> country.continent().equalsIgnoreCase(continent))
                .filter(country -> country.population() > population)
                .toList();
    }

    public List<Country> filterCountryByNeighbours(String neighbour1, String neighbour2){
        return countries.stream()
                .filter(country -> country.neighbours().contains(neighbour1))
                .toList();
    }
}
