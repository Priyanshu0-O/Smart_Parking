# Smart Parking Space Rental System

A robust, full-stack web application designed for renting parking spots, developed as a **Semester 5 Mini Project**.

---

## 🚀 Overview

The **Smart Parking Space Rental System** allows users to find, book, and manage parking spaces. It supports two types of users:

### Customers
- Search for available parking spots
- Check pricing
- Book preferred date and time slots

### Owners
- List their own parking spaces
- Manage availability
- View customer bookings

---

## 🛠 Tech Stack

| Component | Technology |
|-----------|------------|
| **Backend** | Spring Boot (Java 21), Spring Data JPA |
| **Database** | MySQL |
| **Security** | Spring Security Crypto (BCrypt) |
| **Frontend** | Vanilla JavaScript, HTML5, Tailwind CSS |
| **Build Tool** | Maven |

---

## ✨ Key Features

- 🔐 **User Authentication**
  - Secure registration and login for both Owners and Customers.

- 🚫 **Conflict Detection**
  - Prevents double-booking of the same parking space for overlapping time slots.

- 📅 **Dynamic Booking**
  - Users can choose dates and time slots with automatic total price calculation.

- 📝 **CRUD Operations**
  - Owners can create, update, and delete parking space listings.

- 📱 **Responsive UI**
  - Modern and user-friendly interface built with Tailwind CSS.

---

## ⚙️ Prerequisites

Before running the project, ensure you have:

- JDK 21 installed
- MySQL Server installed and running
- IntelliJ IDEA (or any preferred Java IDE)

---

# 🚀 Setup & Running Instructions

## 1. Database Setup

Ensure your MySQL server is running.

The application is configured to create the `smart_parking_db` database automatically if it does not already exist.

---

## 2. Configure Database Credentials

Update your credentials in:

```properties
src/main/resources/application.properties
```

```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

---

## 3. Build & Run

1. Open the project in IntelliJ IDEA.
2. Wait for Maven to download all dependencies.
3. Locate:

```text
src/main/java/com/college/smartparking/SmartParkingApplication.java
```

4. Click the **Run (▶)** button.

---

## 4. Access the Application

After the application starts successfully, open your browser and visit:

```text
http://localhost:8080/
```

---

## 📌 Project

Developed as a **Semester 5 Mini Project**.
