# Project Title

Property adding, updating and searching service for Zapu.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing
purposes.

### Prerequisites

What things you need to install the software and how to install them

- Docker
- Docker Compose

You can download Docker [here](https://docs.docker.com/get-docker/) and it includes Docker Compose.

### Running the project

A step by step series of examples that tell you how to get a development environment running

Navigate to the project folder in your terminal and run the following commands:

1. Package Application
```bash
mvn clean package
```

2. Up Docker Service
```bash
docker-compose up
```

## Searching Examples

- http://localhost:8080/arama?category=1
- http://localhost:8080/arama?category=1&city=2
- http://localhost:8080/arama?category=1&city=2&city=3
- http://localhost:8080/arama?category=1&city=2&city=3&page=1
- http://localhost:8080/arama?category=1&city=2&city=3&page=2&pageSize=2

## Postman Request Examples

A zapu_postman_collection.json file in project folder contains all request examples.
You can import this file to Postman Desktop application.

# Request Examples

```
1. GET http://localhost:8080/api/categories Get Categories
2. GET http://localhost:8080/api/cities Get Cities
3. GET http://localhost:8080/detay/1 // Getting property details by Id
4. POST http://localhost:8080/api/property Add property

   Body: {
   "category": 1,
   "title": "Ankara Yeni mahalle 2+1 daire",
   "city" : 3,
   "price": 4253534,
   "currency": "TL"
   }
   
5. PUT http://localhost:8080/api/property/1 Update property by Id

   Body: {
   "category": 2,
   "title": "Ankara Yeni mahalle 2+1 daire",
   "city" : 1,
   "price": 50000,
   "currency": "TL"
   }
   
```
