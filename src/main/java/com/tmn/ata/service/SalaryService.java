package com.tmn.ata.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
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
import com.tmn.ata.mapper.SalaryMapper;
import com.tmn.ata.model.Salary;
import com.tmn.ata.repository.SalaryRepository;
import com.tmn.ata.response.SalaryCustomResponse;
import com.tmn.ata.response.SalaryResponse;
import com.tmn.ata.util.CurrencyHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalaryService {

	private final SalaryRepository repository;

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
                    .map(dto -> SalaryMapper.convertToEntity(dto))
                    .collect(Collectors.toList());

			// Save to MongoDB
			repository.saveAll(salaries);

			log.info("Salaries saved successfully");
		} catch (IOException e) {
			log.error("Error reading or parsing the JSON file", e);
		}
	}

	public List<SalaryResponse> getSalariesSorted(List<String> fields, List<String> directions, int skip, int limit) {
		log.info("service: sorted ", fields, directions, skip, limit);
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

		return list.stream().map(salary -> SalaryMapper.convertToResponse(salary)).collect(Collectors.toList());
	}
	
	public List<Map<String, Object>> getFilteredSalaries(List<String> fields, List<String> directions) {
		log.info("service: filtered salaries ", fields, directions);
		List<SalaryField> salaryFields = fields.stream().map(SalaryField::fromValue).collect(Collectors.toList());
		List<SortDirection> sortDirections = directions.stream().map(SortDirection::fromValue)
				.collect(Collectors.toList());

		List<Sort.Order> orders = IntStream.range(0, salaryFields.size())
				.mapToObj(i -> new Sort.Order(
						sortDirections.get(i) == SortDirection.ASC ? Sort.Direction.ASC : Sort.Direction.DESC,
						salaryFields.get(i).getValue()))
				.collect(Collectors.toList());

        List<Salary> salaries = repository.findAll(Sort.by(orders));
        return salaries.stream()
                .map(salary -> filterFields(salary, fields))
                .collect(Collectors.toList());
    }
	
	public List<SalaryCustomResponse> filterSalaries(Map<String, String> filters) {
		return repository.findAll().stream()
				.filter(salary -> applyFilters(salary, filters))
				.map(salary -> SalaryMapper.convertToRecord(salary)).collect(Collectors.toList());
	}
	
	private boolean applyFilters(Salary salary, Map<String, String> filters) {
		for (Map.Entry<String, String> filter : filters.entrySet()) {
			String key = filter.getKey();
			String value = filter.getValue();
			switch (key) {
			case "jobTitle":
				if (!salary.getJobTitle().equalsIgnoreCase(value)) {
					return false;
				}
				break;
			case "salary[gte]":
				double salaryValue = CurrencyHelper.parseSalary(salary.getSalary());
				if (salaryValue < Double.parseDouble(value)) {
					return false;
				}
				break;
			case "salary[lte]":
				double salaryValueLte = CurrencyHelper.parseSalary(salary.getSalary());
				if (salaryValueLte > Double.parseDouble(value)) {
					return false;
				}
				break;
			case "gender":
				if (!salary.getGender().equalsIgnoreCase(value)) {
					return false;
				}
				break;
			default:
				break;
			}
		}
		return true;
	}
	
    private Map<String, Object> filterFields(Salary salary, List<String> fields) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> salaryMap = objectMapper.convertValue(salary, Map.class);
        return salaryMap.entrySet().stream()
                .filter(entry -> fields.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
	
}
