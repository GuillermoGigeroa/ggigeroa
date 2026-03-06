# Endpoints de la Aplicación Impresora

## Controlador Principal: AppRestController
URL Base: `/api`

### GET /api/
Health check básico.
```
Respuesta:
{
  "status": "success",
  "message": "El sistema ha sido iniciado correctamente",
  "timestamp": 1234567890
}
```

### GET /api/{path}/{id}
Obtener datos genéricos.
- Parámetros: path (String), id (String)
```
GET /api/usuario/123
Respuesta:
{
  "status": "success",
  "path": "usuario",
  "idUsuario": "123",
  "message": "Datos obtenidos correctamente",
  "requestId": "uuid",
  "timestamp": "06/03/2026 15:00:00"
}
```

### POST /api/{path}/{id}
Procesar/crear datos.
- Parámetros: path (String), id (String)
- Body: JSON arbitrario
```
POST /api/usuario/123
Body: {"nombre": "Juan", "edad": 30}
Respuesta:
{
  "status": "success",
  "path": "usuario",
  "idUsuario": "123",
  "message": "Datos procesados correctamente",
  "receivedJson": {"nombre": "Juan", "edad": 30},
  "timestamp": "06/03/2026 15:00:00",
  "procesado": true
}
```

### PUT /api/{path}/{id}
Actualizar datos.
- Body: JSON con nuevos datos
```
PUT /api/usuario/123
Respuesta:
{
  "status": "success",
  "datosAnteriores": {...},
  "datosNuevos": {...}
}
```

### DELETE /api/{path}/{id}
Eliminar datos.
```
DELETE /api/usuario/123
Respuesta:
{
  "status": "success",
  "eliminado": true
}
```

### GET /api/validar
Validar estado de la aplicación.
```
Respuesta:
{
  "status": "success",
  "data": {
    "jpaEnabled": "true",
    "datasourceUrl": "jdbc:mysql://localhost:3306/impresora_db",
    "puerto": "8080",
    "aplicacion": "Impresora"
  }
}
```

### POST /api/procesar-async
Procesar datos asincronamente.
- Body: JSON
```
Respuesta:
{
  "status": "success",
  "data": {
    "taskId": "uuid",
    "estado": "En procesamiento",
    "datosRecibidos": {...}
  }
}
```

---

## Controlador Avanzado: AppAdvancedController
URL Base: `/api/v1`

### POST /api/v1/procesar
Procesar datos con validación.
- Body: JSON
```
POST /api/v1/procesar
Body: {"id": 1, "nombre": "Test"}
Respuesta:
{
  "status": "success",
  "data": {
    "original": {...},
    "procesado": true,
    "elementosProcesados": 2
  }
}
```

### GET /api/v1/estadisticas/{tipo}
Obtener estadísticas.
```
GET /api/v1/estadisticas/mensual
Respuesta:
{
  "status": "success",
  "data": {
    "tipo": "mensual",
    "totalProcesos": 456.78,
    "tiempoPromedio": "2345.67ms",
    "tasaExito": "98.5%"
  }
}
```

### POST /api/v1/tarea-larga
Iniciar tarea de larga duración.
- Parámetros:
  - nombre: nombre de la tarea
  - duracionSegundos: duración (default: 5)
```
POST /api/v1/tarea-larga?nombre=DescargaArchivos&duracionSegundos=10
Respuesta (202 Accepted):
{
  "status": "success",
  "data": {
    "tareaId": "uuid",
    "nombre": "DescargaArchivos",
    "duracion": "10s",
    "estado": "En ejecución"
  }
}
```

### GET /api/v1/echo
Endpoint de echo con logging.
- Parámetros:
  - mensaje: mensaje para echar
  - nivel: nivel de log (INFO, ERROR, WARN, DEBUG) default: INFO
```
GET /api/v1/echo?mensaje=Hola&nivel=INFO
Respuesta:
{
  "status": "success",
  "data": {
    "mensaje": "Hola",
    "nivel": "INFO",
    "timestamp": 1234567890
  }
}
```

---

## Controlador Web: AppController
URL Base: `/admin`

### GET /admin/info
Información de la aplicación.
```
Respuesta:
{
  "status": "success",
  "data": {
    "aplicacion": "Impresora",
    "puerto": "8080",
    "javaVersion": "17.0.12",
    "osName": "Windows 10",
    "idSesion": "uuid"
  }
}
```

### GET /admin/procesar
Procesar en background.
```
Respuesta:
{
  "status": "success",
  "data": {
    "taskId": "uuid",
    "estado": "En procesamiento"
  }
}
```

### GET /admin/health
Health check del sistema.
```
Respuesta:
{
  "status": "UP",
  "timestamp": "06/03/2026 15:00:00",
  "uptime": "512 MB"
}
```

### GET /admin/configuracion
Obtener configuración actual.
```
Respuesta:
{
  "status": "success",
  "data": {
    "datasource.url": "jdbc:mysql://...",
    "jpa.enabled": "true",
    "jpa.ddl-auto": "none",
    "server.port": "8080"
  }
}
```

---

## Códigos de Error

| Código | Descripción |
|--------|-------------|
| ERR_EMPTY_BODY | Body vacío o inválido |
| ERR_VALIDATION | Datos inválidos |
| ERR_PROCESSING | Error al procesar |
| ERR_STATS | Error al obtener estadísticas |
| ERR_TASK | Error al iniciar tarea |
| ERR_INTERNAL | Error interno del servidor |

---

## Autenticación

Todos los endpoints están protegidos con HTTP Basic Auth.
Credenciales:
- Usuario: `leandro`
- Contraseña: `impresora123`

Ejemplo con curl:
```bash
curl -u leandro:impresora123 http://localhost:8080/api/
```
