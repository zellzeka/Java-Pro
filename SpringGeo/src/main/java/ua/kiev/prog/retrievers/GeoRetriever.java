package ua.kiev.prog.retrievers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.kiev.prog.dto.LocationDTO;
import ua.kiev.prog.json.JsonLocation;

@Component
public class GeoRetriever {
    private static final String URL = "http://ipinfo.io/";

    public LocationDTO getLocation(String ip, double currentRate) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<JsonLocation> response = restTemplate.getForEntity(URL + ip, JsonLocation.class);
        JsonLocation location = response.getBody();
        LocationDTO finalLocation = LocationDTO.of(location.getIp(), location.getCity(), location.getRegion(), location.getCountry(), currentRate);

        return finalLocation;
    }
}
