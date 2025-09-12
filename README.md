# 🔐 Maven Multi-Module Project – NetBeans + Jersey + PostgreSQL  

![Java](https://img.shields.io/badge/Java-17-orange?logo=java)  
![Maven](https://img.shields.io/badge/Maven-Build-blue?logo=apachemaven)  
![NetBeans](https://img.shields.io/badge/IDE-NetBeans-blue?logo=apache)  
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-DB-336791?logo=postgresql)  
![Tomcat](https://img.shields.io/badge/Tomcat-10-yellow?logo=apachetomcat)  

🚀 This project is a **multi-module backend** built with **Java (NetBeans + Maven)** that provides:  
- 📦 **Jersey (JAX-RS)** for RESTful APIs  
- 🔑 **Token-based authentication** with auto-refresh  
- 🛡️ **Data encryption** with external configuration  
- 🗄️ **PostgreSQL persistence**  
- 🌍 Simple deployment on **Apache Tomcat 10**  

---

## 📂 Project Structure

backend/ # Parent project (multi-module pom.xml)
│── core/ # Business logic & services
│── web/ # REST API (Jersey)
│── webapp/ # WebApp module (web.xml, WAR packaging)
└── sql/ # Database scripts

yaml
Copia codice

---

## ⚙️ Configuration  

Configuration is **external** and must be placed in:  

📂 `C:/config/`  

### 🔑 Required files:
- **application.properties** → database connection  
  ```properties
  db.url=jdbc:postgresql://localhost:5432/mydb
  db.username=postgres
  db.password=postgres
encryption.properties → encryption keys & algorithm

properties
Copia codice
encryption.key=mySuperSecretKey
encryption.algorithm=AES
👉 Alternatively, you can update AppConfig and EncryptionUtil to change the file paths.

🗄️ Database
The schema and sample tables are provided in:
📄 create_database.sql

Import into PostgreSQL:

bash
Copia codice
psql -U postgres -d mydb -f sql/schema.sql
🛠️ Build with NetBeans
Open the backend (parent) project in NetBeans.

Run Clean and Build → Maven will automatically build core, web, and webapp modules.

The output WAR file will be generated in:

bash
Copia codice
webapp/target/web.war
Copy web.war into Tomcat’s webapps folder:

bash
Copia codice
C:/apache-tomcat-10/webapps/
▶️ Run
Start Tomcat 10 (startup.bat or service).

Access the APIs at:

bash
Copia codice
http://localhost:8080/web/api/
📡 Main APIs (UserResources)
POST /api/auth/login → user login & token generation



🔐 Security
Sensitive data encrypted via AES (configurable in encryption.properties)

Authentication handled via JWT tokens

Tokens are refreshed per operation for extra security

🖼️ Module Architecture Diagram
mermaid
Copia codice
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
