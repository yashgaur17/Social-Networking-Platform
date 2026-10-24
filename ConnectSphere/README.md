# ConnectSphere — Java Full-Stack Social Networking Platform

A runnable full-stack starter project built with Java 17, Spring Boot, Spring MVC, Spring Data JPA, Thymeleaf, responsive CSS, and an embedded H2 database.

## Features
- Sign up and sign in with BCrypt password hashing
- Session-based login and logout
- Create text posts with optional image URLs
- Like/unlike posts
- Add comments and view comment counts
- Follow/unfollow users
- Profile pages with follower/following/post counts
- Edit display name, bio, and optional avatar URL
- Responsive feed, suggested community members, validation messages
- H2 file-backed database (persists between restarts)

## Requirements
- JDK 17 or newer
- Maven 3.9+ (or use your IDE's Maven integration)
- Internet connection for the first Maven build to download dependencies and Google Fonts

## Run in VS Code / IntelliJ
1. Extract `ConnectSphere.zip`.
2. Open the `ConnectSphere` folder (the one containing `pom.xml`).
3. In a window power shell or cmd terminal, run:
   run mvn clean package
       mvn spring-boot:run
   ```
4. Open `http://localhost:8080`.
5. Create an account and start posting.

To build a runnable jar:
```bash
mvn clean package
java -jar target/connectsphere-1.0.0.jar
```

## Database
The H2 database is stored under `./data/connectsphere`. H2 console is enabled at `http://localhost:8080/h2-console`.
JDBC URL: `jdbc:h2:file:./data/connectsphere` (username `sa`, blank password).

## Project structure
```text
ConnectSphere/
├── pom.xml
├── README.md
└── src/main/
    ├── java/com/connectsphere/
    │   ├── ConnectSphereApplication.java
    │   ├── model/       # JPA entities
    │   ├── repo/        # Spring Data repositories
    │   └── web/         # MVC controller and routes
    └── resources/
        ├── application.properties
        ├── static/css/style.css
        └── templates/   # Thymeleaf pages
```

## Main routes
- `GET /` — authenticated feed
- `GET, POST /login` — sign in
- `GET, POST /register` — create account
- `POST /logout` — end session
- `POST /posts` — create post
- `POST /posts/{id}/like` — toggle like
- `POST /posts/{id}/comments` — add comment
- `POST /users/{id}/follow` — toggle follow
- `GET /profile/{username}` — profile page
- `POST /profile/update` — edit own profile

## Notes before production
This is a college/demo project starter, not a hardened production social network. Before public deployment, add Spring Security authorization/CSRF protection, rate limiting, pagination, email verification/password reset, image upload storage and validation, moderation/reporting, privacy controls, and automated integration tests. Image URLs are optional external URLs; actual file uploads are not implemented.
