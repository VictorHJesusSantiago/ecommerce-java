#!/bin/bash
set -e

echo "Starting E-Commerce Platform..."

if [ "$1" = "dev" ]; then
    echo "Starting in development mode..."
    mvn spring-boot:run -Dspring-boot.run.profiles=dev
elif [ "$1" = "prod" ]; then
    echo "Starting in production mode..."
    mvn spring-boot:run -Dspring-boot.run.profiles=prod
elif [ "$1" = "test" ]; then
    echo "Running tests..."
    mvn test
elif [ "$1" = "build" ]; then
    echo "Building application..."
    mvn clean package -DskipTests
elif [ "$1" = "docker" ]; then
    echo "Starting with Docker Compose..."
    docker-compose up -d
elif [ "$1" = "docker-build" ]; then
    echo "Building Docker image..."
    mvn clean package -DskipTests
    docker-compose build
else
    echo "Usage: ./start.sh [dev|prod|test|build|docker|docker-build]"
fi
