package ua.kiev.prog.json;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JsonLocation {
    private String ip;
    private String city;
    private String region;
    private String country;

    public JsonLocation(String ip, String city, String region, String country) {
        this.ip = ip;
        this.city = city;
        this.region = region;
        this.country = country;
    }


}
