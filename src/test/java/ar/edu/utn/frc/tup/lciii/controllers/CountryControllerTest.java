package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTOs.AmountOfCountries;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTOs.CountryDTO;
import ar.edu.utn.frc.tup.lciii.exception.CountryNotFoundException;
import ar.edu.utn.frc.tup.lciii.service.ICountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICountryService countryService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGetAllCountriesNotFoundException() throws Exception {
        given(countryService.GetAllCountries(isNull(), isNull())).willThrow(new CountryNotFoundException("Countries Not found"));

        mockMvc.perform(get("/api/countries"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Countries Not found"))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void testGetAllCountriesByContinentNotFoundException() throws Exception {
        given(countryService.GetAllCountriesByContinent(anyString())).willThrow(new CountryNotFoundException("Countries Not found"));

        mockMvc.perform(get("/api/countries/{continent}/continent", "Atlantis"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Countries Not found"))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void testGetAllCountriesByContinentGenericError() throws Exception {
        given(countryService.GetAllCountriesByContinent(anyString())).willThrow(new RuntimeException("Error getting countries"));

        mockMvc.perform(get("/api/countries/{continent}/continent", "Atlantis"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"))
                .andExpect(jsonPath("$.error").value("Internal Server Error"));
    }


    @Test
    void testGetAllCountriesByLanguageNotFoundException() throws Exception {
        given(countryService.GetAllCountriesByLanguage(anyString())).willThrow(new CountryNotFoundException("Countries Not found"));

        mockMvc.perform(get("/api/countries/{language}/language", "Klingon"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Countries Not found"))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void testGetAllCountries() throws Exception {
        List<CountryDTO> expectedCountry = List.of(new CountryDTO("ARG", "Argentina"));

        when(countryService.GetAllCountries(null, null)).thenReturn(expectedCountry);

        mockMvc.perform(get("/api/countries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("ARG"))
                .andExpect(jsonPath("$[0].name").value("Argentina"));
    }

    @Test
    void testGetAllCountriesByContinent() throws Exception {
        List<CountryDTO> expectedCountry = List.of(new CountryDTO("ARG", "Argentina"));

        when(countryService.GetAllCountriesByContinent(anyString())).thenReturn(expectedCountry);

        mockMvc.perform(get("/api/countries/{continent}/continent", "Americas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("ARG"))
                .andExpect(jsonPath("$[0].name").value("Argentina"));
    }

    @Test
    void testGetAllCountriesByLanguage() throws Exception {
        List<CountryDTO> expectedCountry = List.of(new CountryDTO("ARG", "Argentina"));

        when(countryService.GetAllCountriesByLanguage(anyString())).thenReturn(expectedCountry);

        mockMvc.perform(get("/api/countries/{language}/language", "Spanish"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("ARG"))
                .andExpect(jsonPath("$[0].name").value("Argentina"));
    }

    @Test
    void testGetCountryWithMostBorders() throws Exception {
        CountryDTO expectedCountry = new CountryDTO("CHI", "China");

        when(countryService.GetCountryWithMostBorders()).thenReturn(expectedCountry);

        mockMvc.perform(get("/api/countries/most-borders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("CHI"))
                .andExpect(jsonPath("$.name").value("China"));
    }

    @Test
    void testPostAmountOfCountriesToSave() throws Exception {
        List<CountryDTO> expectedCountry = List.of(new CountryDTO("ARM", "Armenia"));
        AmountOfCountries amountOfCountries = new AmountOfCountries(1);

        when(countryService.PostAmountOfCountriesToSave(amountOfCountries)).thenReturn(expectedCountry);

        mockMvc.perform(post("/api/countries")
                .contentType("application/json")
                .content("{\"amountOfCountryToSave\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("ARM"))
                .andExpect(jsonPath("$[0].name").value("Armenia"));
    }
}
