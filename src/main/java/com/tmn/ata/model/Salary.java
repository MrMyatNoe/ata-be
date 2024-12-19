package com.tmn.ata.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(collection = "salaries")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Salary {

	@Id
	private String id;
	private Date timestamp;
	private String employer;
	private String location;

	@Indexed
	private String jobTitle;
	private String yearsAtEmployer;
	private String yearsOfExperience;
	private String salary;
	private String signingBonus;
	private String annualBonus;
	private String annualStockValueBonus;

	@Indexed
	private String gender;
	private String additionalComments;
}
