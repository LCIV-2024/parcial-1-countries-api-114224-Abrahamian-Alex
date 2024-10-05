package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.clients.CountryClient;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTOs.AmountOfCountries;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTOs.CountryDTO;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.exception.CountryNotFoundException;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService implements ICountryService {

        @Autowired
        private CountryRepository countryRepository;

        @Autowired
        private CountryClient countryClient;

        @Override
        public List<CountryDTO> GetAllCountries(String name, String code) {

                var countries = countryClient.getAllCountries();

                if (countries == null || countries.isEmpty()) {
                        throw new CountryNotFoundException("Countries Not found");
                }

                var countriesDto = countries.stream()
                        .filter(country -> (name == null || country.getName().equalsIgnoreCase(name)) &&
                                (code == null || country.getCode().equalsIgnoreCase(code)))
                        .map(this::mapToDTO)
                        .collect(Collectors.toList());

                return countriesDto;
        }

        @Override
        public List<CountryDTO> GetAllCountriesByContinent(String continent) {

                var countries = countryClient.getAllCountries();

                if (countries == null || countries.isEmpty()) {
                        throw new CountryNotFoundException("Countries Not found");
                }

                var countriesDto = countries.stream()
                        .filter(country -> (country.getRegion().equalsIgnoreCase(continent)))
                        .map(this::mapToDTO)
                        .collect(Collectors.toList());

                return countriesDto;
        }

        @Override
        public List<CountryDTO> GetAllCountriesByLanguage(String language) {

                var countries = countryClient.getAllCountries();

                if (countries == null || countries.isEmpty()) {
                        throw new CountryNotFoundException("Countries Not found");
                }

                var countriesDto = countries.stream()
                        .filter(country -> country.getLanguages() != null &&
                                country.getLanguages().values().stream()
                                .anyMatch(lang -> lang.equalsIgnoreCase(language)))
                        .map(this::mapToDTO)
                        .collect(Collectors.toList());

                return countriesDto;
        }

        @Override
        public CountryDTO GetCountryWithMostBorders() {
                var countries = countryClient.getAllCountries();

                if (countries == null || countries.isEmpty()) {
                        throw new CountryNotFoundException("Countries Not found");
                }

                var countryWithMostBorders = countries.stream()
                        .filter(country -> country.getBorders() != null)
                        .max(Comparator.comparingInt(country ->  country.getBorders().size()))
                        .orElseThrow(() -> new CountryNotFoundException("Countries Not found"));

                return mapToDTO(countryWithMostBorders);
        }

        @Override
        public List<CountryDTO> PostAmountOfCountriesToSave(AmountOfCountries amountOfCountries) {
                if (amountOfCountries.getAmount() <= 0 ) {
                        throw new IllegalArgumentException("Amount must be greater than 0");
                }

                if (amountOfCountries.getAmount() > 10 ) {
                        throw new IllegalArgumentException("Amount must be less than 10");
                }

                var countries = countryClient.getAllCountries();

                if (countries == null || countries.isEmpty()) {
                        throw new CountryNotFoundException("Countries Not found");
                }

                var countriesShuffle = countries.stream()
                        .limit(amountOfCountries.getAmount())
                        .collect(Collectors.toList());

                Collections.shuffle(countriesShuffle);

                var countriesToSave = countriesShuffle.stream()
                        .map(x -> {
                                CountryEntity countryEntity = new CountryEntity();
                                countryEntity.setCode(x.getCode());
                                countryEntity.setArea(x.getArea());
                                countryEntity.setName(x.getName());
                                countryEntity.setPopulation(x.getPopulation());
                                return countryEntity;
                        })
                        .collect(Collectors.toList());

                countryRepository.saveAll(countriesToSave);

                List<CountryDTO> countriesDto = countriesToSave.stream()
                        .map(x -> new CountryDTO(x.getCode(), x.getName()))
                        .collect(Collectors.toList());

                return countriesDto;
        }

        private CountryDTO mapToDTO(Country country) {
                return new CountryDTO(country.getCode(), country.getName());
        }
}