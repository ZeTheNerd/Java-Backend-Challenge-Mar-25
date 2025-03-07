# Java-Backend-Challenge-Mar-25
Repository for a Java Backend Challenge built on Spring Boot and Apache Kafka. Provides a REST API for a calculator.

## How to build and run the project
To run this project execute the following commands in the root project folder.

Start by building the project:

```shell
./gradlew clean build
```

After that, start the containers:

```shell
docker-compose up --build -d
```

The project will be available at `localhost:8080`. The following endpoints are accessible:
- GET /sum?a={number}&b={number}
- GET /subtraction?a={number}&b={number}
- GET /multiplication?a={number}&b={number}
- GET /division?a={number}&b={number}

To stop the containers use:

```shell
docker-compose down
```