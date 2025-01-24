#!/bin/bash

# Variables
REPO_URL="https://github.com/wilder512/retotecnicowilder.git"
DOCKER_NETWORK="microservices-net"

# Crear network si no existe
if ! docker network ls | grep -q "$DOCKER_NETWORK"; then
  echo "Creando red Docker: $DOCKER_NETWORK"
  docker network create "$DOCKER_NETWORK"
fi

# Clonar o actualizar el repositorio
if [[ -d "retotecnicowilder" ]]; then
  echo "El repositorio ya existe. Actualizando..."
  rm -rf retotecnicowilder
fi
echo "Clonando el repositorio..."
git clone "$REPO_URL"

# Navegar al directorio
cd retotecnicowilder || { echo "Error: no se pudo acceder al repositorio clonado"; exit 1; }

# Descargar imágenes
echo "Descargando imágenes de Docker..."
docker pull "wilder512/clientes-service:1.0" || { echo "Error al descargar la imagen clientes-service"; exit 1; }
docker pull "wilder512/transacciones-service:1.0" || { echo "Error al descargar la imagen transacciones-service"; exit 1; }

# Levantar contenedores
echo "Iniciando contenedor: clientes-service"
docker run -d --name clientes-service --network "$DOCKER_NETWORK" -p 8080:8080 "wilder512/clientes-service:1.0" || {
  echo "Error al iniciar el contenedor clientes-service"
  exit 1
}

echo "Iniciando contenedor: transacciones-service"
docker run -d --name transacciones-service --network "$DOCKER_NETWORK" -p 8081:8081 "wilder512/transacciones-service:1.0" || {
  echo "Error al iniciar el contenedor transacciones-service"
  exit 1
}

# Mensaje final
echo "¡Todo listo! Los servicios están corriendo."
echo "Clientes: http://localhost:8080"
echo "Transacciones: http://localhost:8081"
