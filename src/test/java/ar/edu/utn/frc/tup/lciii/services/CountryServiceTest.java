package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.clients.CountryClient;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTOs.AmountOfCountries;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTOs.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CountryServiceTest {
    @Mock
    private CountryClient countryClient;

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryService countryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetAllDistricts() {
        List<Country> country = List.of(new Country("Argentina", 45000000L, 10000.0, "ARG",
                "Americas", List.of("BRA", "CHL", "URY", "BOL", "PRY"),
                Map.of("spa", "Spanish")));

        when(countryClient.getAllCountries()).thenReturn(country);

        List<CountryDTO> result = countryService.GetAllCountries("Argentina", "ARG");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ARG", result.get(0).getCode());
        assertEquals("Argentina", result.get(0).getName());
    }

    @Test
    public void testGetAllCountriesByContinent() {
        List<Country> country = List.of(new Country("Argentina", 45000000L, 10000.0, "ARG",
                "Americas", List.of("BRA", "CHL", "URY", "BOL", "PRY"),
                Map.of("spa", "Spanish")));

        when(countryClient.getAllCountries()).thenReturn(country);

        List<CountryDTO> result = countryService.GetAllCountriesByContinent("Americas");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ARG", result.get(0).getCode());
        assertEquals("Argentina", result.get(0).getName());
    }

    @Test
    public void testGetAllCountriesByLanguage() {
        List<Country> country = List.of(new Country("Argentina", 45000000L, 10000.0, "ARG",
                "Americas", List.of("BRA", "CHL", "URY", "BOL", "PRY"),
                Map.of("spa", "Spanish")));

        when(countryClient.getAllCountries()).thenReturn(country);

        List<CountryDTO> result = countryService.GetAllCountriesByLanguage("Spanish");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ARG", result.get(0).getCode());
        assertEquals("Argentina", result.get(0).getName());
    }

    @Test
    public void testGetCountryWithMostBorders() {
        List<Country> country = List.of(new Country("Argentina", 45000000L, 10000.0, "ARG",
                "Americas", List.of("BRA", "CHL", "URY", "BOL", "PRY"),
                Map.of("spa", "Spanish")));

        when(countryClient.getAllCountries()).thenReturn(country);

        CountryDTO result = countryService.GetCountryWithMostBorders();

        assertNotNull(result);
        assertEquals("ARG", result.getCode());
        assertEquals("Argentina", result.getName());
    }

    @Test
    public void testPostAmountOfCountriesToSave() {
        List<Country> country = List.of(new Country("Argentina", 45000000L, 10000.0, "ARG",
                "Americas", List.of("BRA", "CHL", "URY", "BOL", "PRY"),
                Map.of("spa", "Spanish")));

        when(countryClient.getAllCountries()).thenReturn(country);
        when(countryRepository.saveAll(anyList())).thenReturn(null);  // Simulación de guardar los países

        List<CountryDTO> result = countryService.PostAmountOfCountriesToSave(new AmountOfCountries(1));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ARG", result.get(0).getCode());
        assertEquals("Argentina", result.get(0).getName());
    }
}
