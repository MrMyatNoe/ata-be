package com.tmn.ata.mapper;

import com.tmn.ata.dto.SalaryDto;
import com.tmn.ata.model.Salary;
import com.tmn.ata.response.SalaryCustomResponse;
import com.tmn.ata.response.SalaryResponse;
import com.tmn.ata.util.DateHelper;

public class SalaryMapper {

	public static Salary convertToEntity(SalaryDto dto) {
        return Salary.builder()
                .timestamp(DateHelper.parseDate(dto.getTimestamp()))
                .employer(dto.getEmployer())
                .location(dto.getLocation())
                .jobTitle(dto.getJobTitle())
                .yearsAtEmployer(dto.getYearsAtEmployer())
                .yearsOfExperience(dto.getYearsOfExperience())
                .salary(dto.getSalary())
                .signingBonus(dto.getSigningBonus())
                .annualBonus(dto.getAnnualBonus())
                .annualStockValueBonus(dto.getAnnualStockValueBonus())
                .gender(dto.getGender())
                .additionalComments(dto.getAdditionalComments())
                .build();
    }
	
	public static SalaryResponse convertToResponse(Salary salary) {
		return new SalaryResponse(salary.getId(), DateHelper.DateToFormat((salary.getTimestamp())), salary.getEmployer(),
				salary.getLocation(), salary.getJobTitle(), salary.getYearsAtEmployer(), salary.getYearsOfExperience(),
				salary.getSalary(), salary.getSigningBonus(), salary.getAnnualBonus(),
				salary.getAnnualStockValueBonus(), salary.getGender(), salary.getAdditionalComments());
	}
	
	public static SalaryCustomResponse convertToRecord(Salary salary) {
        return new SalaryCustomResponse(salary.getJobTitle(), salary.getSalary(), salary.getGender());
    }
}
