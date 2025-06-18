# 📦 Payment Service – Study Project

> **Purpose:** A learning‐oriented Kotlin backend that calculates delivery–driver payouts.  
> **Pattern:** Hexagonal Architecture (Ports & Adapters).

---

## 🛠 Tech Stack
| Layer                | Library / Tool |
|----------------------|----------------|
| Language             | Kotlin         |
| Framework            | Spring Boot    |
| Tests + Mocks        | JUnit 5, Mockito |
| External-API Mocks   | WireMock (Docker) |
| Build                | Gradle         |

---

## 🗂 Folder Layout (hexagonal)

```text
com.example
├── adapter
│   ├── inbound         ← REST controllers, DTOs
│   └── outbound        ← External‐API clients / DTOs
├── application
│   ├── domain
│   │   └── entity      ← Pure domain objects
│   ├── service         ← Use-case implementations
│   └── valueobject     ← Money, Constants, etc.
├── port                ← Inbound & outbound port definitions
└── PaymentServiceApp.kt
```


## 🚀 Running Locally

1. **Start WireMock** (exposes test stubs on **`localhost:8081`**):

```bash
   docker compose up         # requires Docker Desktop or engine
```

2. **Run the service**:

```bash
   ./gradlew bootRun
```

The application will start on **`localhost:8080`** and call WireMock for delivery data.

---

## ✅ Running Tests

```bash
./gradlew test
```
---

## 🏗 Ideas for Production Hardening

| Area                       | Suggestion                                                                                                                                                                |
| -------------------------- |---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Config / Feature Flags** | Move constants (e.g. `BASE_RATE_PER_MINUTE`, `SEQUENCE_GAP_TOLERANCE_IN_MINUTES`) to a feature-flag platform such as **FlagSmith** for runtime changes without redeploys. |
| **HTTP Client**            | Replace manual requests code with **FeignClient** (or Retrofit) for clearer contracts, retries and fallback logic.                                                        |
| **Observability**          | Add structured logging, metrics and tracing (OpenTelemetry).                                                                                                              |
| **Security**               | Introduce authentication/authorization middleware.                                                                                                                        |

---

## 📚 Learning Goals

* Practice **Hexagonal Architecture** & dependency inversion.
* Write **pure domain** logic free of framework concerns.
