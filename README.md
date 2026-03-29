# gRPC + Spring Boot Demo

Взаимодействие двух микросервисов через **gRPC** внутри Spring Boot приложения.

---

## 📌 Описание

В проекте реализована простая схема:

* **pricing-service** — gRPC сервер, который рассчитывает цену
* **order-service** — REST сервис, который обращается к pricing-service через gRPC
* **grpc-contract** — общий контракт (protobuf), который используют оба сервиса

---

## 🏗️ Структура проекта

```text
grpc-spring-demo-parent/
├── grpc-contract/     # .proto контракт + сгенерированные классы
├── pricing-service/   # gRPC сервер
└── order-service/     # REST клиент + gRPC клиент (stub)
```

---

## 🧠 Архитектура взаимодействия

```text
Postman / Frontend
        ↓ HTTP (JSON)
order-service (REST)
        ↓ gRPC (protobuf / HTTP2)
pricing-service (gRPC server)
```

---

## ⚙️ Как это работает

### 1. Контракт (grpc-contract)

В модуле `grpc-contract` находится файл:

```proto
service PriceService {
  rpc CalculatePrice (PriceRequest) returns (PriceResponse);
}
```

Из него генерируются:

* `PriceRequest`
* `PriceResponse`
* `PriceServiceGrpc`

Этот контракт используется **и сервером, и клиентом**.

---

### 2. gRPC сервер (pricing-service)

Реализует метод:

```java
@GrpcService
public class PriceGrpcService extends PriceServiceGrpc.PriceServiceImplBase
```

Сервис поднимается на порту:

```
9090
```

---

### 3. gRPC клиент (order-service)

Создаётся `stub`:

```java
PriceServiceGrpc.PriceServiceBlockingStub
```

И вызывается:

```java
stub.calculatePrice(request);
```

---

### 4. REST слой (order-service)

REST endpoint принимает запрос и внутри вызывает gRPC:

```text
POST /orders/calculate-price
```

---

## 🚀 Как запустить

### 1. Собрать проект

В корне:

```bash
mvn clean install
```

---

### 2. Запустить сервисы

Сначала:

```bash
pricing-service
```

Затем:

```bash
order-service
```

---

### 3. Вызвать REST endpoint

```http
POST http://localhost:8082/orders/calculate-price
```

### Пример запроса:

```json
{
  "productName": "Laptop",
  "unitPrice": 150000,
  "quantity": 2,
  "discountPercent": 10
}
```

---

### Ответ:

```json
{
  "totalWithoutDiscount": 300000.0,
  "discountAmount": 30000.0,
  "finalTotal": 270000.0
}
```