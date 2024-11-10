# USER RECOMMENDATION SYSTEM

This project is a microservices-based application architecture featuring API Gateway, Okta authentication, and various resilience patterns like circuit breaker, rate limiter, and retry mechanisms. Each microservice is independently deployed with its own database, and the application is supported by a Config Server and Service Registry (Eureka) for centralized configuration and discovery.

## Architecture Overview

### Components
1. Client: The user or application making requests to the system.

2. API Gateway:
Serves as the entry point for all client requests.
Manages routing to backend services and integrates with Okta for token-based authentication.
Implements resilience patterns like rate limiting.

3. Okta Authentication:
Provides secure authentication via Okta token management.
Ensures that only authenticated requests are processed by the API Gateway.

4. Config Server:
Centralized configuration for all microservices.
Simplifies configuration management across different environments.

5. Service Registry (Eureka):
Handles service discovery for microservices.
Allows services to locate each other dynamically.

6. Microservices:
    - Hotel Service:
      Manages hotel-related information.
      Communicates with DB1 (PostgreSQL).
  
    - User Service:
      Manages user-related data.
      Communicates with DB2 (MySQL).
  
    - Rating Service:
      Manages ratings and reviews for hotels.
      Communicates with DB3 (PostgreSQL).

7. Communication:

  - FeignClient: Used for inter-service communication with Hotel Service, User Service, and Rating Service.
  RestTemplate: Alternate HTTP client for microservice communication, used where FeignClient is not applied.
  Resilience Features
  
  - Circuit Breaker: Protects services from cascading failures by breaking connections when a service is overloaded or unresponsive.
  - Rate Limiter: Limits the number of requests to the API Gateway, ensuring fair usage and protection against abuse.
  - Retry Mechanism: Automatically retries failed requests, improving reliability.

8. Databases
Each microservice is backed by its own dedicated database:

  DB1: PostgreSQL for Hotel Service.
  DB2: MySQL for User Service.
  DB3: PostgreSQL for Rating Service.

9. Technologies Used
  - Spring Boot: Framework for building the microservices.
  - Spring Cloud Gateway: API Gateway for request routing and security.
  - Spring Cloud Config: Manages centralized configuration.
  - Eureka: Service discovery and registry.
  - Okta: Token-based authentication.
  - FeignClient: Declarative REST client for inter-service communication.
  - RestTemplate: HTTP client for making REST calls.
  - Resilience4j: Implements circuit breaker, rate limiter, and retry mechanisms.
  - PostgreSQL: Database for HotelService and RatingService.
  - MySQL: Database for UserService.
  -Git & GitHub: Version control and source code management.
