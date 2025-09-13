# 🔐 Maven Multi-Module Project – NetBeans + Jersey + PostgreSQL  

![Java](https://img.shields.io/badge/Java-17-orange?logo=java)  
![Maven](https://img.shields.io/badge/Maven-Build-blue?logo=apachemaven)  
![NetBeans](https://img.shields.io/badge/IDE-NetBeans-blue?logo=apache)  
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-DB-336791?logo=postgresql)  
![Tomcat](https://img.shields.io/badge/Tomcat-10-yellow?logo=apachetomcat)  

🚀 Overview

This project is a multi-module backend built with Java (NetBeans + Maven) that provides:

📦 Jersey (JAX-RS) for RESTful APIs

🔑 Token-based authentication with auto-refresh

🛡️ Data encryption with external configuration

🗄️ PostgreSQL persistence

🌍 Simple deployment on Apache Tomcat 10

📂 Project Structure
backend/      # Parent project (multi-module pom.xml)
│
├── core/     # Business logic & services
├── web/      # REST API (Jersey)
├── webapp/   # WebApp module (web.xml, WAR packaging)
└── sql/      # Database scripts

⚙️ Configuration

Configuration is external and must be placed in:

C:/config/

🔑 Required files:

application.properties → database connection

db.url=jdbc:postgresql://localhost:5432/mydb
db.username=postgres
db.password=postgres
registration.enabled=true  # enable/disable user registration (used to create admin)


encryption.properties → encryption keys & algorithm

encryption.key=mySuperSecretKey
encryption.algorithm=AES


👉 Alternatively, you can update AppConfig and EncryptionUtil to change the file paths.

🧑‍💻 Admin & User Setup

Register an admin user:
Use POST /api/register/user
exampe of POST Enter username: "admin", password: "password123"

Log in:
Use POST /api/auth/login
The token will be automatically saved in the variable {{token}}

Test protected APIs:
Use the categories, products, and orders APIs
Each request will automatically extend the token’s lifetime by 15 minutes

Register additional users (if needed):
Use POST /api/register/user to create operators

Delete test users:
Use DELETE /api/register/user/{username} to clean the database

🗄️ Database

The schema and sample tables are provided in:

sql/create_database.sql


Import into PostgreSQL:

psql -U postgres -d mydb -f sql/schema.sql

🛠️ Build with NetBeans

Open the backend (parent) project in NetBeans.

Run Clean and Build → Maven will automatically build core, web, and webapp modules.

The output WAR file will be generated in:

webapp/target/web.war


Copy web.war into Tomcat’s webapps folder:

C:/apache-tomcat-10/webapps/

▶️ Run

Start Tomcat 10 (startup.bat or service).

Access the APIs at:

http://localhost:8080/web/api/

📡 Main APIs (UserResources)

POST /api/auth/login → user login & token generation

🔐 Security

Sensitive data encrypted via AES (configurable in encryption.properties)

Authentication handled via JWT tokens

Tokens are refreshed per operation for extra security

🖼️ Module Architecture Diagram
flowchart TB
    A[backend (parent)] --> B[core]
    A --> C[web]
    A --> D[webapp]
    B -->|logic/services| C
    C -->|REST API| D
    D -->|WAR deploy| Tomcat[(Tomcat 10)]
    Tomcat -->|JDBC connection| DB[(PostgreSQL)]

🤝 Contributing

Fork 🍴 this repo

Create a feature branch ✨

Commit & Push ✅

Open a Pull Request 🚀

📜 License

MIT License © 2025

Commit & Push ✅

Open a Pull Request 🚀

📜 License
MIT License © 2025
