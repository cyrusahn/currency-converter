package com.cyrusahn.Currency_converter;

import com.cyrusahn.Currency_converter.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class CurrencyController {

    // 1️⃣ Define the list of currencies you want to show on the page
    private final List<String> currencies = Arrays.asList("USD", "EUR", "GBP", "CAD", "JPY", "AUD");

    // 2️⃣ Inject your CurrencyService
    @Autowired
    private CurrencyService currencyService;

    // 3️⃣ Serve the main page — this loads your index.html and passes the currency list
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("currencies", currencies);
        return "index";
    }

    // 4️⃣ API endpoint that your JavaScript calls with AJAX
    //    Example: /api/convert?from=USD&amount=100
    @GetMapping("/api/convert")
    @ResponseBody
    public Map<String, Double> convert(
            @RequestParam String from,
            @RequestParam double amount
    ) {
        return currencyService.getConvertedRates(from, amount);
    }
}




