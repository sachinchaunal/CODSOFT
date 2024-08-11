package com.currency.convertor;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Scanner;

public class CurrencyConverter {
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter base currency (e.g., USD): ");
        String baseCurrency = scanner.nextLine().toUpperCase();

        System.out.print("Enter target currency (e.g., EUR): ");
        String targetCurrency = scanner.nextLine().toUpperCase();

        System.out.print("Enter amount to convert: ");
        double amount = scanner.nextDouble();

        try {
            double exchangeRate = fetchExchangeRate(baseCurrency, targetCurrency);
            double convertedAmount = amount * exchangeRate;
            System.out.printf("%.2f %s is equivalent to %.2f %s%n", amount, baseCurrency, convertedAmount, targetCurrency);
        } catch (IOException e) {
            System.out.println("Failed to fetch exchange rate. Please try again.");
        }

        scanner.close();
    }

    private static double fetchExchangeRate(String baseCurrency, String targetCurrency) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL + baseCurrency)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseData = response.body().string();
            JSONObject json = new JSONObject(responseData);
            return json.getJSONObject("rates").getDouble(targetCurrency);
        }
    }
}
