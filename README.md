# 🏘️ NextHood - Neighborhood Watch & Reporting System

NextHood is a powerful, community-driven platform designed to improve local governance through digital collaboration. It enables **residents**, **volunteers**, and **local authorities** to **report, track, and resolve neighborhood issues** like potholes, garbage dumps, streetlight outages, or suspicious activities in real-time.

---

## 🚀 Features

- 🔐 **User Management** (Resident / Volunteer / Admin roles with JWT auth)
- 🆘 **Issue Reporting** (Geo-tagged, image upload, auto-addressing)
- 🛠️ **Resolution Workflow** (Status tracking, comments, notifications)
- 📢 **Notification System** (Email updates via JavaMailSender)
- 👍👎 **Community Feedback** (Voting and moderated comments)
- 📊 **Admin Dashboard** (Heatmaps, analytics, response times)

---
## 🔧 Tech Stack

| Layer         | Technology                                     |
|---------------|------------------------------------------------|
| Backend       | Java 21, Spring Boot, Spring MVC              |
| Auth & Security | Spring Security, JWT                        |
| Database      | MySQL (AWS RDS), Spring Data JPA              |
| File Storage  | AWS S3 (for images)                           |
| Hosting       | AWS EC2                                       |
| Notifications | JavaMailSender (Email)          |
| Maps & Geo    | Google Maps API, OpenCage Geocoder            |
| Testing       | JUnit, Mockito                                |
| DevOps        | Docker, Maven                                 |

---

## 🧪 Testing

| Tool         | Purpose                        |
|--------------|--------------------------------|
| JUnit        | Unit testing                   |
| Mockito      | Mocking service/repository     |
| Testcontainers | Optional MySQL-in-Docker     |

---

## 📁 Microservices

### 1. 🔐 Auth Service
- JWT-based login/register
- Role-based access control

### 2. 👤 User Service
- Manage profiles
- Store geolocation data

### 3. 🆘 Issue Service
- Report issues with images & location
- Vote, comment, update status

### 4. 📢 Notification Service
- Email/SMS alerts
- Optional scheduled jobs (cron, RabbitMQ)

---

## 🗂️ Entity Relationship Diagram (Simplified)

```text
User
 └── id, name, email, role, location

Issue
 └── id, title, description, status, lat, lng, address, created_by, image_url

Comment
 └── id, issue_id, user_id, content, timestamp

Vote
 └── id, issue_id, user_id, vote_type (up/down)

Notification
 └── id, user_id, message, type, sent_at
