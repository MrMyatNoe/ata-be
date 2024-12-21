package com.tmn.ata.controller.rest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tmn.ata.response.ApiResponse;
import com.tmn.ata.response.SalaryCustomResponse;
import com.tmn.ata.response.SalaryResponse;
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
	public ApiResponse<List<SalaryResponse>> getSalariesSorted(@RequestParam List<String> fields, @RequestParam List<String> directions,
			@RequestParam(defaultValue = "0") int skip, @RequestParam(defaultValue = "10") int limit, HttpServletRequest request) {
		if (fields.size() != directions.size()) {
            throw new IllegalArgumentException("The number of fields and directions must match.");
        }
        List<SalaryResponse> salaries = service.getSalariesSorted(fields, directions, skip, limit);
		return new ApiResponse<>(LocalDateTime.now(), HttpStatus.OK.value(), null, "Salaries fetched successfully",
				request.getRequestURI(), salaries);
	}
	
	@GetMapping("/sparse")
	public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getFilteredSalaries(@RequestParam List<String> fields, @RequestParam List<String> sort,
			HttpServletRequest request) {
        log.info("controller : Fetching filtered salaries by fields: {}", fields, sort);
        List<Map<String, Object>> filteredSalaries = service.getFilteredSalaries(fields, sort);
        ApiResponse<List<Map<String, Object>>> response = ApiResponse.<List<Map<String, Object>>>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Filtered salaries fetched successfully")
                .path(request.getRequestURI())
                .data(filteredSalaries)
                .build();
        return ResponseEntity.ok(response);
    }
	
	@GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<SalaryCustomResponse>>> filterSalaries(
            @RequestParam Map<String, String> filters, HttpServletRequest request) {
        log.info("Filtering salaries with filters: {}", filters);
        List<SalaryCustomResponse> filteredSalaries = service.filterSalaries(filters);
        ApiResponse<List<SalaryCustomResponse>> response = ApiResponse.<List<SalaryCustomResponse>>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Filtered salaries fetched successfully")
                .path(request.getRequestURI())
                .data(filteredSalaries)
                .build();
        return ResponseEntity.ok(response);
    }

}
