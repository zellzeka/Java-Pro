package ua.kiev.prog.retrievers;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.kiev.prog.json.Rate;

@Component
public class RateRetriever {

    private static final String URL =
            "http://data.fixer.io/api/latest?access_key=ea3e4e7516b94034246cf7170cb519a1";

    @Cacheable("rates") // Redis
    public Rate getRate() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Rate> response = restTemplate.getForEntity(URL, Rate.class);
            return response.getBody();
        } catch (Exception ex) {
            return new Rate();
        }
    }
}
