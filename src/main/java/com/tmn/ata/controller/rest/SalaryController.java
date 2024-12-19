package com.tmn.ata.controller.rest;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tmn.ata.dto.SalaryDto;
import com.tmn.ata.model.Salary;
import com.tmn.ata.service.SalaryService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/api/v1/salary")
@RequiredArgsConstructor
@Slf4j
public class SalaryController {

	private final SalaryService service;

	@GetMapping
	public String getTesting() {
		log.info("controller testing");
		return service.getHello();
	}

	@GetMapping("/salaries")
	public List<Salary> getSalaries() {
		log.info("controller: salaries");
		return service.getSalaries();
	}

	@GetMapping("/import")
	public ResponseEntity<ApiResponse<String>> importSalaries(HttpServletRequest request) {
		service.readAndSaveSalaries();
        ApiResponse<String> response = ApiResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Salaries imported successfully")
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.ok(response);
	}

	@GetMapping("/sorted")
	public ResponseEntity<ApiResponse<List<SalaryDto>>> getSalariesSorted(@RequestParam List<String> fields, @RequestParam List<String> directions,
			@RequestParam(defaultValue = "0") int skip, @RequestParam(defaultValue = "10") int limit, HttpServletRequest request) {
		if (fields.size() != directions.size()) {
            throw new IllegalArgumentException("The number of fields and directions must match.");
        }
        List<SalaryDto> salaries = service.getSalariesSorted(fields, directions, skip, limit);
        ApiResponse<List<SalaryDto>> response = ApiResponse.<List<SalaryDto>>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Salaries fetched successfully")
                .path(request.getRequestURI())
                .data(salaries)
                .build();
        return ResponseEntity.ok(response);
	}

}
