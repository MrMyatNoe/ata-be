package com.tmn.ata.controller.rest;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {

	private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private T data;
}