# TravelNestPro Backend Startup Script
# Uses workspace-safe defaults and supports overriding through environment variables.
$env:DB_USERNAME="root"
$env:DB_PASSWORD=if ($env:DB_PASSWORD) { $env:DB_PASSWORD } else { "root" }
$env:JAVA_HOME=if ($env:JAVA_HOME) { $env:JAVA_HOME } else { "C:\Program Files\Java\jdk-25.0.2" }
$env:Path="$env:JAVA_HOME\bin;$env:Path"

Write-Host "Starting TravelNestPro Backend..." -ForegroundColor Green
Write-Host "Database: travelnextpro" -ForegroundColor Cyan
Write-Host "User: root" -ForegroundColor Cyan
Write-Host "Port: 8080" -ForegroundColor Cyan
Write-Host ""

.\mvnw.cmd clean spring-boot:run
