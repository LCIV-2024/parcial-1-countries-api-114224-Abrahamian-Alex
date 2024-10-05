package ar.edu.utn.frc.tup.lciii.clients;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTOs.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Service
public class CountryClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${URL_REST_TEMPLATE}")
    private String url;

    public CountryClient () {
        if (url == null || url.isEmpty()) {
            url = "https://my-json-server.typicode.com/LCIV-2023/fake-api-rwc2023";
        }
    }

    public List<Country> getAllCountries() {
        List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
        return response.stream().map(this::mapToCountry).collect(Collectors.toList());
    }

    private Country mapToCountry(Map<String, Object> countryData) {
        Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
        return Country.builder()
                .name((String) nameData.get("common"))
                .population(((Number) countryData.get("population")).longValue())
                .area(((Number) countryData.get("area")).doubleValue())
                .region((String) countryData.get("region"))
                .code((String) countryData.get("cca3"))
                .borders((List<String>) countryData.get("borders"))
                .languages((Map<String, String>) countryData.get("languages"))
                .build();
    }
}
