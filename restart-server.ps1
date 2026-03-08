# Script para reiniciar el servidor Spring Boot localmente
# 1. Detiene cualquier proceso de Java en ejecución (para liberar el puerto 8080)
# 2. Espera un par de segundos para asegurar que el puerto se liberó
# 3. Vuelve a compilar y levantar la aplicación Spring Boot usando Maven Wrapper

Write-Host "Deteniendo procesos Java existentes..." -ForegroundColor Yellow
Stop-Process -Name java -Force -ErrorAction SilentlyContinue

Write-Host "Esperando 2 segundos..." -ForegroundColor DarkGray
Start-Sleep -Seconds 2

Write-Host "Iniciando servidor Spring Boot..." -ForegroundColor Green
# Se puede agregar -RedirectStandardOutput "logs.txt" para guardar los logs en un archivo si se desea
Start-Process -FilePath "mvnw.cmd" -ArgumentList "spring-boot:run" -NoNewWindow
