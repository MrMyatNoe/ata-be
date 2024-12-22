# ATA Backend Assignment 

## Tech Stacks

- Java 17
- Spring Boot 3
- Mongo DB


### What this assignment done

- click the import data file in db and query according to assignment

### Steps to test 

1. Firstly, data import from  ```http://localhost:8080/api/v1/salary/import``` and check in db
2. for the first query try example ```http://localhost:8080/api/v1/salary/filter?salary_gte=53330```, you can replace salary_lte
3. for the second query try ```http://localhost:8080/api/v1/salary/sparse?fields=salary&sort=asc```
4. for the third query try ```http://localhost:8080/api/v1/salary/sorted?fields=salary&directions=asc```

### Required Field to Test

1. the fields in the bracket are the request for **field**
```
TIMESTAMP("timestamp"), EMPLOYER("employer"), LOCATION("location"), JOB_TITLE("jobTitle"),
	YEARS_AT_EMPLOYER("yearsAtEmployer"), YEARS_OF_EXPERIENCE("yearsOfExperience"), SALARY("salary"),
	SIGNING_BONUS("signingBonus"), ANNUAL_BONUS("annualBonus"), ANNUAL_STOCK_VALUE_BONUS("annualStockValueBonus"),
	GENDER("gender"), ADDITIONAL_COMMENTS("additionalComments")
```

2. the fields in the bracket are the request for **directions**
```
ASC("asc"), DESC("desc")
```
