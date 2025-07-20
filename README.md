
# 🏠 WebCrawlerImmobileApp

A fully automated real estate monitoring system for Austrian listings. Users define search filters once, and the system regularly checks for new matches that sends results directly through a notification system (Newsletter design). 

---
## 💡 Why Use It

- Set your filter once and the system does the rest.
- No need to revisit listing sites or manually check.
- Simple web interface with persistent filters and login.
- Matching new listings are processed and sent out without user interaction.
- Smart logic prevents duplicate results.

This app is made for **hands-off, filter-based property discovery**.


---

## 📁 Project Structure (Custom Files Only)

```
📁 src/
├── java/
│   ├── crawler/               # Automated data collection and processing
│   ├── model/                 # Domain models: User, Immobile
│   ├── service/               # Business logic
│   ├── web/controller/        # Web routes: filter, auth, profile
│   └── config/                # MongoDB, web security, interceptors
├── resources/
│   ├── templates/             # Thymeleaf HTML views (web + email)
│   ├── static/                # CSS, JS, images
│   └── application.properties # App configuration
```

---

## 🎯 Filter Capabilities

Each user can create multiple filters using the following options:

- **Price** (minimum and maximum)
- **Price per m²**
- **Property size** (min / max in square meters)
- **Property type**:
  - House (buy or rent)
  - Apartment (buy or rent)
  - Land
- **Region**: One of Austria’s 9 federal states
- **Districts**: Auto-loaded based on selected region (e.g., for Vienna)
---

## 🛡 Security

- Passwords are hashed securely
- Unauthorized users are redirected to login
- Backend uses ORM queries (MongoDB) to prevent injection

---

## 🧰 Tech Stack

| Component      | Technology                     |
|----------------|--------------------------------|
| Backend        | Java 21, Spring Boot 3         |
| Frontend       | Thymeleaf (HTML templates)     |
| Crawler Engine | Selenium + Firefox WebDriver   |
| Database       | MongoDB                        |
| Email Service  | Gmail SMTP (STARTTLS, port 587)|
| DevOps         | Docker Compose                 |
| Build Tool     | Maven                          |
---------------------------------------------------




