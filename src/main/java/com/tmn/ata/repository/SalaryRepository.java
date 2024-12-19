package com.tmn.ata.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tmn.ata.model.Salary;

@Repository
public interface SalaryRepository extends MongoRepository<Salary, String>{

	Optional<Salary> findByJobTitle(String jobTitle);
}
