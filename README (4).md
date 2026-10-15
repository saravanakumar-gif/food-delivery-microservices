# Food Delivery Microservices

A food delivery backend migrated from a single Spring Boot monolith into independent microservices, with service discovery, an API gateway, service-to-service calls, and event-driven notifications. The whole system runs with one `docker compose up`.

## Architecture

```
Client (Postman / frontend)
          |
          v
   API Gateway :8090 -----> Eureka Server :8571 (service discovery)
          |
   +------+---------------+------------------+
   v                      v                  v
User Service :8087   Restaurant Service   Order Service :8085
                          :8082               |   \
                                              |    +--(OpenFeign)--> Restaurant, User
                                              | Kafka topic: order-events
                                              v
                                     Notification Service :8086

Payment Service :8084 --(OpenFeign)--> Order Service
```

## Services

| Service | Port | Database | Responsibility |
|---|---|---|---|
| eureka-server | 8571 | - | Service registry |
| api-gateway | 8090 | - | Single entry point, routes to services by name |
| user-service | 8087 | user_service_db | Register, login (JWT), profile, addresses |
| restaurant-service | 8082 | restaurant_service_db | Restaurants and menu items |
| order-service | 8085 | order_service_db | Place and track orders, publishes order events |
| payment-service | 8084 | payment_service_db | Payment records, updates order payment status |
| notification-service | 8086 | - | Consumes order events from Kafka (simulated email log) |

## Tech stack

Java 21, Spring Boot 4.1, Spring Cloud (Gateway, Eureka, OpenFeign), Spring Security with JWT, Spring Data JPA, PostgreSQL, Apache Kafka 4.0 (KRaft mode), Docker and Docker Compose.

## Design decisions

- **Database per service.** No shared tables. Where the monolith used entity relations (for example `Order -> User`), the microservices store only the other service's ID, plus snapshot fields such as customer name and restaurant name on the order.
- **JWT across services.** User Service issues the token (it carries `userId` and `role`). Every other service validates it with the same secret and builds the security context from the token, with no user database lookup.
- **Token propagation.** A Feign `RequestInterceptor` forwards the incoming `Authorization` header on service-to-service calls.
- **Event-driven notifications.** Order Service publishes `OrderPlacedEvent` to the `order-events` Kafka topic. Notification Service consumes it, so ordering does not depend on notification being up.
- **Payment is simulated.** No real gateway keys are needed. Real Razorpay integration is planned for when a frontend exists.

## Run it

Prerequisite: Docker Desktop (running).

```bash
docker compose up --build
```

The first build takes 10 to 15 minutes. After that:

- Eureka dashboard: http://localhost:8571
- API entry point: http://localhost:8090

Databases start empty. Register a `RESTAURANT_OWNER` and a `CUSTOMER` through the gateway, create a restaurant and menu item as the owner, add an address as the customer, then place an order. The notification service log shows the consumed event:

```bash
docker compose logs -f notification-service
```

Useful commands:

```bash
docker compose ps                         # container status
docker compose up --build order-service   # rebuild one service
docker compose down                       # stop (data kept)
docker compose down -v                    # stop and wipe data
```

## Configuration

Each service reads its settings from `application.yml`, and Docker Compose overrides the host-specific values (database URL, Eureka URL, Kafka address) with environment variables. Use your own Base64 `jwt.secret`, the same value in every service, and do not commit real secrets.

## Possible next steps

- Real Razorpay integration and a wallet service
- JWT validation centralised at the gateway
- Frontend (React) consuming the gateway
