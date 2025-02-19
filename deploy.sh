#!/bin/bash

docker-compose down

./mvnw clean install -DskipTests

docker-compose up --build -d
docker-compose ps
