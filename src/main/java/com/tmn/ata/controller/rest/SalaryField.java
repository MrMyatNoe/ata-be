package com.tmn.ata.controller.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum SalaryField {

	TIMESTAMP("timestamp"), EMPLOYER("employer"), LOCATION("location"), JOB_TITLE("jobTitle"),
	YEARS_AT_EMPLOYER("yearsAtEmployer"), YEARS_OF_EXPERIENCE("yearsOfExperience"), SALARY("salary"),
	SIGNING_BONUS("signingBonus"), ANNUAL_BONUS("annualBonus"), ANNUAL_STOCK_VALUE_BONUS("annualStockValueBonus"),
	GENDER("gender"), ADDITIONAL_COMMENTS("additionalComments");

	private String value;

	public static SalaryField fromValue(String value) {
		for (SalaryField dir : SalaryField.values()) {
			if (dir.value.equalsIgnoreCase(value)) {
				return dir;
			}
		}

		throw new IllegalArgumentException("Unknown Salary Field" + value);
	}
}
