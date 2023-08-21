package bank;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "rates")
public class Rate {
    @Id
    @GeneratedValue
    private Long id;
    private String baseCurrency;
    private String targetCurrency;
    private Double exchangeRate;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "transactionRate")
    private List<Transaction> transactions = new ArrayList<>();
    private static HashMap<String, HashMap<String, Double>> exchangeRates = new HashMap<>();

    // Заполнение мапы начальными значениями курсов обмена
    static {
        HashMap<String, Double> usdRates = new HashMap<>();
        usdRates.put("UAH", 36.93);
        usdRates.put("EUR", 0.91);
        usdRates.put("USD", 1.0);
        exchangeRates.put("USD", usdRates);

        HashMap<String, Double> uahRates = new HashMap<>();
        uahRates.put("USD", 0.027);
        uahRates.put("EUR", 0.025);
        uahRates.put("UAH", 1.0);
        exchangeRates.put("UAH", uahRates);


        HashMap<String, Double> eurRates = new HashMap<>();
        eurRates.put("USD", 1.09);
        eurRates.put("UAH", 40.38);
        eurRates.put("EUR", 1.0);
        exchangeRates.put("EUR", eurRates);
    }

    public Rate() {
    }

    public Rate(String baseCurrency, String targetCurrency) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.exchangeRate = setExchangeRate();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public Double setExchangeRate() {
        Double currentRate = 0.0;
        if (exchangeRates.containsKey(baseCurrency) && exchangeRates.get(baseCurrency).containsKey(targetCurrency)) {
           currentRate = exchangeRates.get(baseCurrency).get(targetCurrency);
        } else {
            System.out.println("Exchange rate not available for this currencies.");
        }
        return currentRate;
    }



    @Override
    public String toString() {
        return "Rate{" +
                "id=" + id +
                ", baseCurrency='" + baseCurrency + '\'' +
                ", targetCurrency='" + targetCurrency + '\'' +
                ", exchangeRate=" + exchangeRate +
                '}';
    }
}
