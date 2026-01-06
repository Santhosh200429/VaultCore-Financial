# 🏦 VaultCore Financial — Backend

VaultCore Financial is a **production-grade Neo-Bank backend simulation** designed to demonstrate **secure financial systems, transaction integrity, concurrency safety, audit compliance, and penetration-testing readiness**.

The project is implemented incrementally over **4 structured weeks**, each addressing **real-world fintech backend engineering challenges**.

---

## 📌 Backend Project Overview

### Use Case (Production Simulation)

VaultCore Financial simulates the **core backend infrastructure of a Neo-Bank**, including:

- Secure user authentication (JWT-based)
- Immutable double-entry accounting ledger
- High-concurrency money transfers
- External REST API integration
- Audit logging & compliance observability
- Monthly PDF account statement generation
- Penetration-testing validation (OWASP ZAP)

### Core Principle

**Security, correctness, and compliance are treated as first-class backend requirements.**

---

## 🛠 Backend Tech Stack

- Java 21
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA (Hibernate)
- AspectJ (AOP)
- Apache PDFBox
- MySQL
- Flyway (DB migrations)
- Logback
- Postman
- OWASP ZAP

---

## 🗂 Backend Project Structure

vaultcore-backend
├── config
├── controller
├── service
├── repository
├── entity
├── dto
├── audit
├── statements
├── security
├── resources
│ ├── db/migration
│ └── logback-spring.xml
├── logs
│ └── audit.log




---

## 📅 Week-wise Backend Implementation

---

### 🟦 Week 1 – Security & Ledger Design

**Goal:** Secure authentication and immutable financial ledger.

**Key Backend Features:**
- JWT-based authentication
- Immutable double-entry ledger system
- Database constraints prevent ledger tampering
- Flyway-managed schema versioning

**Validation:**
- Manual database modification attempts blocked

---

### 🟦 Week 2 – Transaction Engine & Concurrency

**Goal:** Maintain balance correctness under heavy concurrency.

**Key Backend Features:**
- ACID-compliant money transfers
- `@Transactional(isolation = SERIALIZABLE)`
- Double-entry ledger enforcement
- Race condition prevention

**Validation:**
- 100 concurrent transfer threads
- Balances remained correct and non-negative

---

### 🟦 Week 3 – External API Integration

**Goal:** Integrate and measure external service behavior.

**Key Backend Features:**
- Mock Stock Price REST API
- REST client integration
- API latency measurement
- Structured timing metadata

**Validation:**
- API latency consistently below 300 ms

---

### 🟦 Week 4 – Audit, Compliance & Security

---

#### 🔍 Audit Logging (AspectJ)

- Applied to controller, service, and repository layers
- Captures:
  - Method name
  - Parameters (masked)
  - Return values
  - Execution time
- Logs written asynchronously

**Audit Log Location:**
logs/audit.log


---

#### 📄 Monthly PDF Statements

- Generated using Apache PDFBox
- Includes:
  - Opening balance
  - Transaction history
  - Closing balance
- Generated securely via backend API

---

#### 🔐 Penetration Testing

- OWASP ZAP scan executed on backend APIs
- Validated against:
  - SQL Injection
  - XSS
  - Authentication bypass
  - Security misconfigurations

> Penetration testing is performed using external security tools.

---

## 🔬 Backend Testing Summary

| Feature | Test Type | Layer |
|------|---------|------|
| Ledger immutability | Manual DB test | Backend |
| Money transfers | Concurrency stress test | Backend |
| API latency | Runtime measurement | Backend |
| Audit logs | Log verification | Backend |
| PDF statements | API generation test | Backend |
| Security | OWASP ZAP | External |

---

## 🚀 How to Run (Backend)

### Prerequisites
- Java 21
- MySQL
- Maven

### Steps
1. Configure database in `application.properties`
2. Flyway migrations run automatically on startup
3. Run the application:
```bash
run as spring boot app
