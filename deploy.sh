#!/bin/bash

build_application() {
  ./mvnw clean install
  if [ $? -ne 0 ]; then
    echo "falha no build"
    exit 1
  fi
}

start_containers() {
  docker-compose up --build -d
  if [ $? -ne 0 ]; then
    echo "falha ao iniciar contêineres"
    exit 1
  fi
}

check_health() {
  echo "aguardando disponibilidade da API"
  until $(curl --output /dev/null --silent --head --fail -u username:password http://localhost:8080/actuator/health); do
    printf '.'
    sleep 5
  done
  echo "API pronta para receber requisições"
}

if [ "$1" == "--off" ]; then
  docker-compose down
  exit 0
fi

build_application

start_containers

check_health

docker-compose ps
