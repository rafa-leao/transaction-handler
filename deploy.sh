#!/bin/bash

# Parar e remover contêineres existentes
docker-compose down

# Construir a aplicação usando Maven
./mvnw clean install -DskipTests
if [ $? -ne 0 ]; then
  echo "Falha no build"
  exit 1
fi

# Construir e iniciar os contêineres do Docker Compose
docker-compose up --build -d
if [ $? -ne 0 ]; then
  echo "Falha ao iniciar os contêineres"
  exit 1
fi

# Aguardar o SonarQube estar acessível
echo "Aguardando o SonarQube"
until $(curl --output /dev/null --silent --head --fail http://localhost:9000/sessions/new); do
    printf '.'
    sleep 5
done
echo "SonarQube pronto"

# Executar a análise do Sonar
SONAR_TOKEN="sqa_af5751049c63cc446c89fea8557e7c8eff6b8821"
./mvnw clean verify sonar:sonar -Dsonar.projectKey=autorizador -Dsonar.projectName=autorizador -Dsonar.host.url=http://localhost:9000 -Dsonar.login=$SONAR_TOKEN
if [ $? -ne 0 ]; then
  echo "Falha na análise do Sonar"
  exit 1
fi

# Exibir o status dos contêineres
docker-compose ps
