package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTOs.AmountOfCountries;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTOs.CountryDTO;

import java.util.List;

public interface ICountryService {
    List<CountryDTO> GetAllCountries(String name, String code);

    List<CountryDTO> GetAllCountriesByContinent(String continent);

    List<CountryDTO> GetAllCountriesByLanguage(String language);

    CountryDTO GetCountryWithMostBorders();

    List<CountryDTO> PostAmountOfCountriesToSave(AmountOfCountries amountOfCountries);
}
