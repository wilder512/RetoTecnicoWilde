#!/bin/bash

# Variables
REPO_URL="https://github.com/wilder512/retotecnicowilder.git"
DOCKER_NETWORK="reto-tecnico-network"
DOCKER_USERNAME="wilder512"
IMAGES=("clientes-service:1.0" "transacciones-service:1.0")

# Paso 1: Clonar el repositorio
echo "Clonando el repositorio..."
git clone "$REPO_URL" || { echo "Error al clonar el repositorio"; exit 1; }
cd retotecnicowilder || { echo "No se pudo acceder al repositorio clonado"; exit 1; }

# Paso 2: Crear la red de Docker si no existe
if ! docker network ls | grep -q "$DOCKER_NETWORK"; then
  echo "Creando la red Docker: $DOCKER_NETWORK"
  docker network create "$DOCKER_NETWORK" || { echo "Error al crear la red Docker"; exit 1; }
else
  echo "La red Docker '$DOCKER_NETWORK' ya existe."
fi

# Paso 3: Descargar imágenes desde Docker Hub
for IMAGE in "${IMAGES[@]}"; do
  echo "Descargando imagen: $DOCKER_USERNAME/$IMAGE"
  docker pull "$DOCKER_USERNAME/$IMAGE" || { echo "Error al descargar la imagen $DOCKER_USERNAME/$IMAGE"; exit 1; }
done

# Paso 4: Ejecutar los contenedores
echo "Iniciando contenedor: clientes-service"
docker run -d --name clientes-service --network "$DOCKER_NETWORK" -p 8080:8080 "$DOCKER_USERNAME/clientes-service:1.0" || {
  echo "Error al iniciar el contenedor clientes-service"
  exit 1
}

echo "Iniciando contenedor: transacciones-service"
docker run -d --name transacciones-service --network "$DOCKER_NETWORK" -p 8081:8081 "$DOCKER_USERNAME/transacciones-service:1.0" || {
  echo "Error al iniciar el contenedor transacciones-service"
  exit 1
}

echo "El entorno se ha configurado correctamente. Servicios en ejecución:"
echo "- Clientes: http://localhost:8080"
echo "- Transacciones: http://localhost:8081"
