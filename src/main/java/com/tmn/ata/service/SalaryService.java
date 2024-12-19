package com.tmn.ata.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmn.ata.controller.rest.SalaryField;
import com.tmn.ata.controller.rest.SortDirection;
import com.tmn.ata.dto.SalaryDto;
import com.tmn.ata.model.Salary;
import com.tmn.ata.repository.SalaryRepository;
import com.tmn.ata.util.DateHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalaryService {

	private final SalaryRepository repository;

	public String getHello() {
		log.info("service: testing");
		return "hello";
	}

	public List<Salary> saveSalaries(List<Salary> salaries) {
		log.info("service: saving salaries ");
		return repository.saveAll(salaries);
	}

	public List<Salary> getSalaries() {
		log.info("service: get salaries ");
		return repository.findAll();
	}

	public void readAndSaveSalaries() {
		try {
			// Read JSON file from resources
			ClassPathResource resource = new ClassPathResource("salary.json");
			InputStream inputStream = resource.getInputStream();

			// Parse JSON data
			ObjectMapper objectMapper = new ObjectMapper();
			List<SalaryDto> salaryDtos = objectMapper.readValue(inputStream, new TypeReference<List<SalaryDto>>() {
			});

			List<Salary> salaries = salaryDtos.stream()
                    .map(this::convertToEntity)
                    .collect(Collectors.toList());

			// Save to MongoDB
			repository.saveAll(salaries);

			log.info("Salaries saved successfully");
		} catch (IOException e) {
			log.error("Error reading or parsing the JSON file", e);
		}
	}

	public List<SalaryDto> getSalariesSorted(List<String> fields, List<String> directions,int skip, int limit) {
		List<SalaryField> salaryFields = fields.stream().map(SalaryField::fromValue).collect(Collectors.toList());

		List<SortDirection> sortDirections = directions.stream().map(SortDirection::fromValue)
				.collect(Collectors.toList());

		List<Sort.Order> orders = IntStream.range(0, salaryFields.size())
				.mapToObj(i -> new Sort.Order(
						sortDirections.get(i) == SortDirection.ASC ? Sort.Direction.ASC : Sort.Direction.DESC,
						salaryFields.get(i).getValue()))
				.collect(Collectors.toList());

		Sort sort = Sort.by(orders);
        Pageable pageable = PageRequest.of(skip / limit, limit, sort);
		Page<Salary> list = repository.findAll(pageable);

		return list.stream().map(this::convertToDto).collect(Collectors.toList());
	}
	
	private Salary convertToEntity(SalaryDto dto) {
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
	
	private SalaryDto convertToDto(Salary salary) {
        return SalaryDto.builder()
                .id(salary.getId())
                .timestamp(salary.getTimestamp().toString())
                .employer(salary.getEmployer())
                .location(salary.getLocation())
                .jobTitle(salary.getJobTitle())
                .yearsAtEmployer(salary.getYearsAtEmployer())
                .yearsOfExperience(salary.getYearsOfExperience())
                .salary(salary.getSalary())
                .signingBonus(salary.getSigningBonus())
                .annualBonus(salary.getAnnualBonus())
                .annualStockValueBonus(salary.getAnnualStockValueBonus())
                .gender(salary.getGender())
                .additionalComments(salary.getAdditionalComments())
                .build();
    }
}
