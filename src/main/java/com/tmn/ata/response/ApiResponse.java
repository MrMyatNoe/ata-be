package com.tmn.ata.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiResponse<T> {

	private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private T data;
}
