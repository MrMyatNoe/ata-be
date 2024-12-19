package com.tmn.ata.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.tmn.ata.dto.SalaryDto;
import com.tmn.ata.model.Salary;

@Mapper(componentModel = "spring")
public interface SalaryMapper {

	List<Salary> toModels(List<SalaryDto> dtos);
}
