# Rewards API - Spring boot

## Overview
Retail reward calculation API built using Spring Boot and Java


## Rules

- 2 points for every dollar over $100
- 1 point for every dollar between $50 and $100


## API

GET http://localhost:8080/RewardsApplication/api/rewards/C001?fromDate=2026-01-01&toDate=2026-03-31

OUTPUT: e.g {"customerId":"C001","monthlyRewards":{"2026-03":70,"2026-02":146,"2026-01":305},"totalRewards":521}

## Features

- RESTful API
- Dynamic customer Id & date range
- Clean architecture
- Unit tests
- Exception handling

## Tech Stack

- Java
- Spring boot
- JUnit