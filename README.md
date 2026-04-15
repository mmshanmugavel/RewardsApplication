# Rewards API - Spring boot

## Overview
Retail reward calculation API built using Spring Boot and Java

## Project Folder Structure

## Project Folder Structure

- rewards-api
  - src
    - main
      - java
        - com.charter.rewardpoints
          - controller
            - RewardsController.java
          - service
            - RewardsService.java
            - RewardsServiceImpl.java
          - repository
            - TransactionRepository.java
          - dto
            - RewardsResponse.java
            - Transaction.java
          - exception
            - GlobalExceptionHandler.java
            - FutureDateException.java
            - GenericException.java
            - MissingDateException.java
            - TransactionNotFoundException.java
      - resources
        - application.properties
        - static/
        - templates/
    - test
      - java
        - com.charter.rewardpoints
          - RewardsApplicationTests.java
          - controller
            - RewardsControllerTest.java
          - service
            - RewardsServiceTest.java
  - Documents
  - pom.xml
  - README.md

## Rules

- 2 points for every dollar over $100
- 1 point for every dollar between $50 and $100


## API

GET /api/rewards/calculate?fromDate=2026-01-01&toDate=2026-03-31

SUCCESS RESPONSE - Sample JSON

[
    {
        "customerId": "C002",
        "monthlyRewards": {
            "2026-01": 310
        },
        "totalRewards": 310
    },
    {
        "customerId": "C001",
        "monthlyRewards": {
            "2026-03": 70,
            "2026-02": 146,
            "2026-01": 307
        },
        "totalRewards": 523
    },
    {
        "customerId": "C003",
        "monthlyRewards": {
            "2026-03": 24,
            "2026-02": 47,
            "2026-01": 126
        },
        "totalRewards": 197
    }
]
 
ERROR RESPONSE

# From date and To date must be provided

{
    "status": 400,
    "error": "Bad Request",
    "message": "StartDate and EndDate must be provided",
    "timestamp": "2026-04-15T18:25:10.5003157",
    "path": "/RewardsApplication/api/rewards/calculate"
}

# From date or To date can not be a future date

{
    "status": 400,
    "error": "Bad Request",
    "message": "StartDate or EndDate can not be a future date",
    "timestamp": "2026-04-15T18:25:10.5003157",
    "path": "/RewardsApplication/api/rewards/calculate"
}

# No transactions found

{
    "status": 400,
    "error": "Bad Request",
    "message": "No transactions found for startdate: 2026-03-01, enddate: 2026-03-02",
    "timestamp": "2026-04-15T18:27:24.4441633",
    "path": "/RewardsApplication/api/rewards/calculate"
}

# From date can not be after To date
{
    "status": 400,
    "error": "Bad Request",
    "message": "StartDate can not be after EndDate",
    "timestamp": "2026-04-15T18:27:52.8991303",
    "path": "/RewardsApplication/api/rewards/calculate"
}

## Steps to Run

# Clone repository
git clone https://github.com/mmshanmugavel/RewardsApplication.git

# Navigate to project
cd RewardsApplication

# Build project
mvn  clean install

# Run application
mvn spring-boot:run

## Features

- RESTful API
- Dynamic customer Id & date range
- Validations
- Clean architecture
- Unit tests
- Exception handling

## Tech Stack

- Java
- Spring boot
- JUnit