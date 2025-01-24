#!/bin/bash

# Variables
REPO_URL="https://github.com/wilder512/retotecnicowilder.git"
DOCKER_NETWORK="reto-tecnico-network"
IMAGES=("clientes-service:1.0" "transacciones-service:1.0")

# Validar usuario y contraseña
DOCKER_USERNAME=${1:-}
DOCKER_PASSWORD=${2:-}

if [ -z "$DOCKER_USERNAME" ] || [ -z "$DOCKER_PASSWORD" ]; then
  echo "No se proporcionaron usuario o contraseña de Docker Hub."
  read -p "Usuario de Docker Hub: " DOCKER_USERNAME
  read -s -p "Contraseña de Docker Hub: " DOCKER_PASSWORD
  echo
fi

# Iniciar sesión en Docker
echo "Iniciando sesión en Docker Hub..."
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin || {
  echo "Error al iniciar sesión en Docker Hub. Verifica tus credenciales."
  exit 1
}

# Validar si el repositorio ya existe
if [ -d "retotecnicowilder" ]; then
  echo "El repositorio 'retotecnicowilder' ya existe."
  read -p "¿Deseas eliminarlo y descargarlo nuevamente? (s/n): " REPLY
  if [[ "$REPLY" =~ ^[Ss]$ ]]; then
    rm -rf retotecnicowilder
    echo "Repositorio eliminado."
  else
    echo "Usando el repositorio existente."
  fi
fi

# Clonar el repositorio si no existe
if [ ! -d "retotecnicowilder" ]; then
  echo "Clonando el repositorio..."
  git clone "$REPO_URL" || { echo "Error al clonar el repositorio"; exit 1; }
fi

cd retotecnicowilder || { echo "No se pudo acceder al repositorio clonado"; exit 1; }

# Crear la red de Docker si no existe
if ! docker network ls | grep -q "$DOCKER_NETWORK"; then
  echo "Creando la red Docker: $DOCKER_NETWORK"
  docker network create "$DOCKER_NETWORK" || { echo "Error al crear la red Docker"; exit 1; }
else
  echo "La red Docker '$DOCKER_NETWORK' ya existe."
fi

# Descargar imágenes desde Docker Hub
for IMAGE in "${IMAGES[@]}"; do
  echo "Descargando imagen: $DOCKER_USERNAME/$IMAGE"
  docker pull "$DOCKER_USERNAME/$IMAGE" || { echo "Error al descargar la imagen $DOCKER_USERNAME/$IMAGE"; exit 1; }
done

# Ejecutar los contenedores
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