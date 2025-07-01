package com.cyrusahn.Currency_converter.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class CurrencyService {

    private final List<String> supportedCurrencies = Arrays.asList("USD", "EUR", "GBP", "CAD", "JPY", "AUD");

    public Map<String, Double> getConvertedRates(String fromCurrency, double amount) {
        Map<String, Double> result = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            
            String apiKey = "e017f82e50c7e4b3c2d1f87c50a4b4ec";
            String url = "https://api.exchangerate.host/live?access_key=" + apiKey + "&source=" + fromCurrency;



            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode root = mapper.readTree(response.body());
            JsonNode rates = root.get("quotes");

            System.out.println("Rates from API: " + rates.toString());

            for (String currency : supportedCurrencies) {
                if (!currency.equals(fromCurrency)) {
                    double rate = rates.get(fromCurrency + currency).asDouble();
                    result.put(currency, amount * rate);
                } else {
                    result.put(currency, amount); // Original amount
                }
            }
            
            System.out.println("Conversion result map: " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
