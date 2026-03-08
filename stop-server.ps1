# Script para detener el servidor Spring Boot localmente

Write-Host "Deteniendo procesos Java existentes..." -ForegroundColor Yellow
Stop-Process -Name java -Force -ErrorAction SilentlyContinue

Write-Host "El servidor ha sido detenido exitosamente." -ForegroundColor Green
