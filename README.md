# Cryptocurrency_trading_platform
Interview Traiding 212

A simple full-stack cryptocurrency trading simulation platform built with **Java (Spring Boot)** on the backend and **HTML/JavaScript** on the frontend.

---

## 🧩 Features

- 📈 View live crypto prices (mocked/real).
- 👤 User account balance handling.
- 👜 View and manage portfolio holdings.
- 🔁 Buy and sell cryptocurrency (with balance/holding validation).
- 📜 Transaction history display in a professional table format.
- 🔄 Reset button to restore account balance and clear portfolio.
- ❌ Input validation and clear error handling.

---

## 📁 Project Structure
```
+---.vscode
|       settings.json
|
+---backend
|   \---crypto-traiding
|       |   .gitattributes
|       |   .gitignore
|       |   HELP.md
|       |   mvnw
|       |   mvnw.cmd
|       |   pom.xml
|       |
|       +---.mvn
|       |   \---wrapper
|       |           maven-wrapper.properties
|       |
|       +---.vscode
|       |       settings.json
|       |
|       |
|       +---src
|       |   +---main
|       |   |   +---java
|       |   |   |   \---com
|       |   |   |       \---crypto
|       |   |   |           \---traiding
|       |   |   |               +---api
|       |   |   |               |   |   KrakenWebSocketClient.java
|       |   |   |               |   |
|       |   |   |               |   +---configuration
|       |   |   |               |   |       KrakenWebSocketConfig.java
|       |   |   |               |   |
|       |   |   |               |   +---dto
|       |   |   |               |   |       KrakenTickerResponse.java
|       |   |   |               |   |
|       |   |   |               |   \---store
|       |   |   |               |           KrakenPriceStore.java
|       |   |   |               |
|       |   |   |               +---config
|       |   |   |               |       CorsConfig.java
|       |   |   |               |       KrakenApiConfig.java
|       |   |   |               |       SecurityConfig.java
|       |   |   |               |
|       |   |   |               +---controller
|       |   |   |               |       CryptoController.java
|       |   |   |               |       PingController.java
|       |   |   |               |       PortfolioController.java
|       |   |   |               |       TradeController.java
|       |   |   |               |       TransactionController.java
|       |   |   |               |       UserController.java
|       |   |   |               |
|       |   |   |               +---crypto_traiding
|       |   |   |               |       CryptoTraidingApplication.java
|       |   |   |               |
|       |   |   |               +---exception
|       |   |   |               |       DatabaseException.java
|       |   |   |               |       InvalidTransactionException.java
|       |   |   |               |
|       |   |   |               +---model
|       |   |   |               |       Transaction.java
|       |   |   |               |       User.java
|       |   |   |               |
|       |   |   |               \---service
|       |   |   |                       AccountService.java
|       |   |   |                       CryptoPriceService.java
|       |   |   |                       PortfolioService.java
|       |   |   |                       TradingService.java
|       |   |   |                       TransactionService.java
|       |   |   |
|       |   |   \---resources
|       |   |       |   application-dev.properties
|       |   |       |   application-prod.properties
|       |   |       |   application.properties
|       |   |       |   schema.sql
|       |   |       |
|       |   |       +---static
|       |   |       |       index.html
|       |   |       |       script.js
|       |   |       |       style.css
|       |   |       |
|       |   |       \---templates
|       |   \---test
|       |       \---java
|       |           \---com
|       |               \---crypto
|       |                   \---traiding
|       |                       +---controller
|       |                       |       PortfolioControllerTest.java
|       |                       |       TransactionControllerTest.java
|       |                       |
|       |                       +---crypto_traiding
|       |                       |       CryptoTraidingApplicationTests.java
|       |                       |
|       |                       \---service
|       |                               TradingServiceTest.java
|       |                               TransactionService.java
```
# How to Run the Application

This guide outlines how to set up and launch the Crypto Trading Platform locally for testing or evaluation purposes.

## 📦 Requirements
Before running the application, ensure the following are installed on your system:

- Java 17 or higher

- Apache Maven (latest version recommended)

- (Optional) PostgreSQL or MySQL, if using a persistent database

- A web browser (e.g., Chrome, Firefox)

## ▶️ Running the Application Locally

### Clone the Repository
First, clone the project using Git:
git clone https://github.com/ValentinaPetrova/Cryptocurrency_trading_platform

```
cd crypto-trading-platform
```

### Use Maven to start the Spring Boot application:

```
mvn spring-boot:run
```

### Access the Web Interface

Once the backend starts successfully, navigate to the following URL in your browser:

http://localhost:8080

You should see the main interface of the crypto trading platform.
