package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTOs.AmountOfCountries;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTOs.CountryDTO;
import ar.edu.utn.frc.tup.lciii.service.ICountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CountryController {

    @Autowired
    private final ICountryService countryService;

    @GetMapping("/countries")
    public ResponseEntity<List<CountryDTO>> GetAllCountries(@RequestParam(name = "name", required = false) String name,
                                                            @RequestParam(name = "code", required = false) String code){
        List<CountryDTO> response = countryService.GetAllCountries(name, code);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/countries/{continent}/continent")
    public ResponseEntity<List<CountryDTO>> GetAllCountriesByContinent(@PathVariable String continent){
        List<CountryDTO> response = countryService.GetAllCountriesByContinent(continent);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/countries/{language}/language")
    public ResponseEntity<List<CountryDTO>> GetAllCountriesByLanguage(@PathVariable String language){
        List<CountryDTO> response = countryService.GetAllCountriesByLanguage(language);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/countries/most-borders")
    public ResponseEntity<CountryDTO> GetCountryWithMostBorders(){
        CountryDTO response = countryService.GetCountryWithMostBorders();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/countries")
    public ResponseEntity<List<CountryDTO>> PostAmountOfCountriesToSave(@RequestBody AmountOfCountries amountOfCountries){
        List<CountryDTO> response = countryService.PostAmountOfCountriesToSave(amountOfCountries);
        return ResponseEntity.ok(response);
    }
}