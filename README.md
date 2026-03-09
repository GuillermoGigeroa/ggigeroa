# Sistema de Impresora

Aplicación Spring Boot 3.5.10 que demuestra patrones de desarrollo profesional en Java. Incluye REST API, autenticación segura, Swagger UI y soporte para base de datos MySQL opcional.

## 🛠️ Tecnologías

- **Java 17** - Lenguaje
- **Spring Boot 3.5.10** - Framework
- **Spring Web** - REST API
- **Spring Security** - Autenticación HTTP Basic
- **Spring Data JPA** - Persistencia (opcional)
- **MySQL 5.7+** - Base de datos (opcional)
- **Springdoc OpenAPI 2.x** - Documentación Swagger UI
- **SLF4J + Logback** - Logging
- **Maven** - Build tool

## 🚀 Inicio Rápido

### Requisitos

- Java 17+
- Maven 3.8+ (incluido: `mvnw`)

### Compilar

```bash
mvnw.cmd clean package -DskipTests
```

### Ejecutar

```bash
# Con Maven
mvnw.cmd spring-boot:run

# O con JAR
java -jar target/impresora.ggigeroa-0.0.1-SNAPSHOT.jar
```

La aplicación se levanta en `http://localhost:8080`

## 🔐 Autenticación y Base de Datos

La aplicación utiliza seguridad web y base de datos incrustada H2:
- **Usuario Web:** `ggigeroa`
- **Contraseña:** `admin`

### Consola de Base de Datos H2
- **URL:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:impresora_db`
- **Nombre de Usuario:** `sa`
- **Contraseña:** *(vacía)*

> ⚠️ **Para producción:** Cambiar a OAuth2/JWT y reemplazar H2 por MySQL en `application.properties`.

## 📖 Documentación Endpoints

- **Registros:** `/api/registros`
- **Imágenes:** `/api/imagenes`
- **Frontend Dashboard:** `http://localhost:8080/`

Ejemplo de uso de API (cURL):
```bash
curl -u ggigeroa:admin -X GET http://localhost:8080/api/registros
```

## 📂 Estructura

- `src/main/java/` - Código fuente
- `src/main/resources/` - Configuración
- `agent.md` - Contexto técnico (IA)

## ✨ Características

- ✅ REST API con CRUD completo
- ✅ MVC con templates HTML
- ✅ HTTP Basic Authentication
- ✅ Swagger/OpenAPI integrado
- ✅ Logging centralizado (SLF4J)
- ✅ Funciona sin BD disponible
- ✅ Procesamiento asíncrono en background
- ✅ Manejo global de excepciones
- ✅ Respuestas JSON estandarizadas

## 📞 Soporte

Más información disponible en `agent.md`.