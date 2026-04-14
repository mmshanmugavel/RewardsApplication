# Rewards API - Spring boot

## Overview
Retail reward calculation API built using Spring Boot and Java

## Project Folder Structure

rewards-api
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.charter.rewardPoints
│   │   │               │
│   │   │               ├── controller/
│   │   │               │   └── RewardController.java
│   │   │               │
│   │   │               ├── service/
│   │   │               │   └── RewardsService.java
│	│	│				│	└── RewardsServiceImpl.java
│   │   │               │
│   │   │               ├── repository/
│   │   │               │   └── TransactionRepository.java
│   │   │               │
│   │   │               ├── dto/
│   │   │               │   └── RewardsResponse.java
│	│	│				│	└── Transaction.java
│   │   │               │
│   │   │               └── exception/
│   │   │                   └── GlobalExceptionHandler.java
│   │   │                   └── FutureDateException.java
│   │   │                   └── GenericException.java
│   │   │                   └── MissingDateException.java
│   │   │                   └── TransactionNotFoundException.java
│   │   │
│   │   └── resources
│   │       ├── application.properties
│   │       ├── static/
│   │       ├── templates/
│   │
│   └── test
│       └── java
│           └── com.charter.rewardPoints
│                │	└── RewardsApplicationTests.java
│                │
│                ├── controller/
│      	         │   └── RewardsControllerTest.java
│                ├── service/
│				 │		└── RewardsIntegrationTest.java
│				 │		└── RewardsServiceTest.java
│
├── pom.xml
└── README.md


## Rules

- 2 points for every dollar over $100
- 1 point for every dollar between $50 and $100


## API

GET /api/rewards/calculate?customerId=C001&fromDate=2026-01-01&toDate=2026-03-31

SUCCESS RESPONSE - Sample JSON
 {
 	"customerId":"C001",
 	"monthlyRewards":{
 		"2026-03":70,
 		"2026-02":146,
 		"2026-01":305
 	},
 	"totalRewards":521
 }
 
ERROR RESPONSE

status code: 400, From date and To date must be provided
status code: 400, From date or To date can not be a future date
status code: 400, No transactions found for customer 123
 
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