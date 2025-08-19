# 📌 Desafío Foro

Este proyecto es una API REST desarrollada en **Java Spring Boot** que implementa un sistema de foros con autenticación de usuarios, manejo de tópicos y seguridad con **JWT**.  

---

## 🚀 Características principales
- Registro y autenticación de usuarios.
- Gestión de tópicos (crear, listar, actualizar).
- Seguridad con Spring Security y JWT.
- Persistencia con JPA/Hibernate.
- Migraciones de base de datos con Flyway.
- Pruebas unitarias con JUnit.

---

## 📂 Estructura del proyecto

src/
├── main/
│ ├── java/desafio_foro/beta/
│ │ ├── BetaApplication.java # Clase principal
│ │ ├── config/ # Configuraciones (seguridad, data seeder)
│ │ ├── controller/ # Controladores REST
│ │ ├── dto/ # Data Transfer Objects
│ │ ├── exception/ # Manejo de excepciones
│ │ ├── model/ # Entidades JPA
│ │ ├── repository/ # Repositorios Spring Data
│ │ └── security/ # Filtros y servicios JWT
│ └── resources/
│ ├── application.properties # Configuración de la aplicación
│ └── db/migration/ # Migraciones Flyway
└── test/
└── java/desafio_foro/beta/ # Pruebas unitarias

## ⚙️ Requisitos previos
- [Java JDK 17+](https://adoptium.net/)  
- [Maven 3.8+](https://maven.apache.org/)  
- [PostgreSQL](https://www.postgresql.org/) (u otra base de datos compatible)  

---

## 🔧 Configuración y ejecución

1. Clona este repositorio:  
   ```bash
   git clone <url-del-repo>
   cd desafio_Foro

   spring.datasource.url=jdbc:postgresql://localhost:5432/foro
spring.datasource.username=usuario
spring.datasource.password=contraseña
spring.jpa.hibernate.ddl-auto=validate
spring.flyway.enabled=true


Compila y ejecuta el proyecto:
./mvnw spring-boot:run 

La aplicación estará disponible en:
👉 http://localhost:8082

Endpoints principales
Autenticación
POST /auth/login → Iniciar sesión y obtener JWT.
POST /auth/register → Registrar nuevo usuario.

Tópicos
GET /topicos → Listar tópicos.
POST /topicos → Crear nuevo tópico.
PUT /topicos/{id} → Actualizar un tópico existente.
DELETE /topicos/{id} → Eliminar un tópico.

Tests
Ejecutar pruebas unitarias con:
./mvnw test

