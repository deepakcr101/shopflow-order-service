# ShopFlow Order Service

## Service Description

The ShopFlow Order Service is responsible for managing customer orders. It provides RESTful endpoints for creating, retrieving, updating, and deleting orders. The service also integrates with Apache Kafka to publish an event whenever a new order is created.

## Tech Stack

- **Java 17**
- **Spring Boot 2.7.5**
- **Spring Data JPA** for database interaction.
- **Spring for Apache Kafka** for event-driven communication.
- **PostgreSQL** as the primary database.
- **Maven** for dependency management.

## How to Run in Dev Mode

To run the service in development mode, you can use the following Maven command:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

This will start the service on port `8083` and connect to the development database and Kafka broker as configured in `src/main/resources/application-dev.properties`.

## API Endpoints

### Orders

- **GET /api/orders**

  Retrieves a list of all orders.

- **GET /api/orders/{id}**

  Retrieves a specific order by its ID.

- **POST /api/orders**

  Creates a new order.

  **Example Request:**

  ```json
  {
    "orderItems": [
      {
        "productId": 1,
        "quantity": 2,
        "price": 20.00
      }
    ],
    "totalAmount": 40.00
  }
  ```

- **PUT /api/orders/{id}**

  Updates an existing order.

- **DELETE /api/orders/{id}**

  Deletes an order by its ID.

## Communication with Other Services

The Order Service communicates with other services asynchronously via Apache Kafka.

### Published Events

- **`order_created_events`**

  When a new order is created, the service publishes the order ID to the `order_created_events` Kafka topic. Other services, such as the Payment Service or Notification Service, can subscribe to this topic to process the order further.
