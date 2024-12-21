package com.tmn.ata.util;

public class CurrencyHelper {

	public static double parseSalary(String salary) {
		if (salary == null || salary.isEmpty() || salary.equals("-")) {
			return 0;
		}
		try {
			// Handle different currency symbols and formats
			if (salary.contains("£")) {
				return convertToUSD(salary.replaceAll("[^\\d.]", ""), "GBP");
			} else if (salary.contains("€")) {
				return convertToUSD(salary.replaceAll("[^\\d.]", ""), "EUR");
			} else if (salary.contains("¥")) {
				return convertToUSD(salary.replaceAll("[^\\d.]", ""), "JPY");
			} else if (salary.contains("₹")) {
				return convertToUSD(salary.replaceAll("[^\\d.]", ""), "INR");
			} else if (salary.contains("HKD")) {
				return convertToUSD(salary.replaceAll("[^\\d.]", ""), "HKD");
			} else if (salary.contains("AUD")) {
				return convertToUSD(salary.replaceAll("[^\\d.]", ""), "AUD");
			} else if (salary.contains("CAD")) {
				return convertToUSD(salary.replaceAll("[^\\d.]", ""), "CAD");
			} else if (salary.contains("NZD")) {
				return convertToUSD(salary.replaceAll("[^\\d.]", ""), "NZD");
			} else if (salary.contains("USD") || salary.contains("$")) {
				return Double.parseDouble(salary.replaceAll("[^\\d.]", ""));
			} else if (salary.contains("K")) {
				return Double.parseDouble(salary.replaceAll("[^\\d.]", "")) * 1000;
			} else if (salary.contains("per hour") || salary.contains("/hr") || salary.contains("hour")) {
				return Double.parseDouble(salary.replaceAll("[^\\d.]", "")) * 2080; // Assuming 2080 working hours in a
																					// year
			} else if (salary.contains("per day") || salary.contains("/day") || salary.contains("day")) {
				return Double.parseDouble(salary.replaceAll("[^\\d.]", "")) * 260; // Assuming 260 working days in a
																					// year
			} else {
				return Double.parseDouble(salary.replaceAll("[^\\d.]", ""));
			}
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	private static double convertToUSD(String amount, String currency) {
		double value = Double.parseDouble(amount);
		switch (currency) {
		case "GBP":
			return value * 1.38; // Example conversion rate
		case "EUR":
			return value * 1.18; // Example conversion rate
		case "JPY":
			return value * 0.0091; // Example conversion rate
		case "INR":
			return value * 0.013; // Example conversion rate
		case "HKD":
			return value * 0.13; // Example conversion rate
		case "AUD":
			return value * 0.74; // Example conversion rate
		case "CAD":
			return value * 0.79; // Example conversion rate
		case "NZD":
			return value * 0.70; // Example conversion rate
		default:
			return value;
		}
	}
}
