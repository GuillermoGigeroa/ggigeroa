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
- MySQL 5.7+ (opcional)

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

## 🔐 Autenticación

Credenciales por defecto (desarrollo):
```
Usuario: ggigeroa
Contraseña: admin
```

> ⚠️ **Para producción:** Cambiar a OAuth2/JWT y usar variables de entorno.

## 📖 Documentación

- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON:** `http://localhost:8080/v3/api-docs`
- **Health Check:** `curl -u leandro:impresora123 http://localhost:8080/api/`

## 📂 Estructura

- `src/main/java/` - Código fuente
- `src/main/resources/` - Configuración
- `docs/` - Documentación (GitHub Pages)
- `agent.md` - Contexto técnico

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