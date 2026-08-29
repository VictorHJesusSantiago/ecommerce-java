@echo off
setlocal

if "%1"=="dev" (
    echo Starting in development mode...
    mvn spring-boot:run -Dspring-boot.run.profiles=dev
) else if "%1"=="prod" (
    echo Starting in production mode...
    mvn spring-boot:run -Dspring-boot.run.profiles=prod
) else if "%1"=="test" (
    echo Running tests...
    mvn test
) else if "%1"=="build" (
    echo Building application...
    mvn clean package -DskipTests
) else if "%1"=="docker" (
    echo Starting with Docker Compose...
    docker-compose up -d
) else (
    echo Usage: start.bat [dev^|prod^|test^|build^|docker]
)

endlocal
