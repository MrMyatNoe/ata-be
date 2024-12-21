# ATA Backend Assignment 

## Tech Stacks

- Java 17
- Spring Boot 3
- Mongo DB


### What this assignment done
- click the import data file in db and query according to assignment

### Steps to test 
1. Firstly, data import from **http://localhost:8080/api/v1/salary/import** and check in db
2. for the first query try example **http://localhost:8080/api/v1/salary/filter?salary_gte=53330**, you can replace salary_lte
3. for the second query try **http://localhost:8080/api/v1/salary/sparse?fields=salary&sort=asc**
4. for the third query try** http://localhost:8080/api/v1/salary/sorted?fields=salary&directions=asc**
