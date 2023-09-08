package ua.kiev.prog.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SingleRate {
    @JsonProperty("UAH")
    private double uah;
//Завдання 1 вивести окрім курсу евро до гривні - курс євро до доллара
    @JsonProperty("USD")
    private double usd;
}
