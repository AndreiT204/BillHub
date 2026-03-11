# 💸 BillHub

BillHub is a full-stack financial management application designed to organize and streamline billing data. It features a robust **Spring Boot** backend, a **PostgreSQL** relational database, and a dynamic **React** frontend.

---

## 📋 Table of Contents
* [About the Project](#about-the-project)
* [Features](#features)
* [Technologies Used](#technologies-used)
* [Setup and Installation](#setup-and-installation)

---

## 🏦 About the Project
BillHub was created to solve the challenge of personal expense organization. While many applications rely on temporary storage, BillHub implements a production-grade relational database (PostgreSQL) to ensure data persistence, security, and integrity.

The project is structured into two main components:
* **Backend:** A Java-based REST API built with Spring Boot that handles business logic and database communication.
* **Frontend:** A responsive User Interface built with React that interacts with the backend using modern asynchronous communication.

---

## ✨ Features

### 📂 Billing Management
Create, view, and organize billing records through an intuitive dashboard. Users can track their financial obligations in real-time.

### 🗄️ Relational Persistence
Leverages **PostgreSQL** and Hibernate/JPA to manage complex data mapping, ensuring that every bill is correctly linked and stored securely.

### 🌐 Native API Integration
Unlike projects that rely on external libraries like Axios, BillHub uses the **Native Fetch API**. This demonstrates a deep understanding of modern browser capabilities and lightweight architectural patterns.

---

## 🛠️ Technologies Used

### Backend
* **Java 17+**
* **Spring Boot** (Web, Data JPA)
* **PostgreSQL** (Production-grade relational database)
* **Maven** (Dependency & Build Management)

### Frontend
* **React.js**
* **Node.js**
* **Native Fetch API** (For all asynchronous network requests)
* **CSS3** (Responsive design and styling)

---

## 🚀 Live Demo

Check out BillHub live on Vercel:
👉 [BillHub](https://your-vercel-link.vercel.app)

---

## 🚀 Setup and Installation

### 1. Database Configuration
Ensure you have **PostgreSQL** installed and running. Create a new database named `billhub`. Update your `src/main/resources/application.properties` with your local credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/billhub
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 2. Run the Backend
From the project root directory:

```bash
mvn clean install
mvn spring-boot:run
```

### 3. Run the Frontend
Navigate to the frontend directory:
```bash
cd frontend
npm install
npm start
```
