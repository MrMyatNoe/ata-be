package com.tmn.ata.response;

public record SalaryResponse(String id, String timestamp, String employer, String location, String jobTitle,
		String yearsAtEmployer, String yearsOfExperience, String salary, String signingBonus, String annualBonus,
		String annualStockValueBonus, String gender, String additionalComments) {
}
