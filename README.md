# RetoTecnicoWilder


Microservicios: Clientes y Transacciones
Este proyecto consta de dos microservicios:

Servicio de Clientes: Funciona como una "cartera", almacenando la información de los clientes y sus saldos.
Servicio de Transacciones: Permite registrar transacciones y actualizar los saldos de los clientes consumiendo el servicio de Clientes.

Ambos servicios están contenidos con Docker para facilitar su implementación y ejecución.

Tecnologías utilizadas
Java 
Base de datos 
Docker 
Spring RestTemplate:
Estructura del proyecto:

/clientes
    ├── src/main/java/com/.../clientes/
    ├── src/main/resources/
    └── Dockerfile

/transacciones
    ├── src/main/java/com/.../transacciones/
    ├── src/main/resources/
    └── Dockerfile

    

Cómo Levantar los Servicios en Local:

Requisitos:
  Docker
  git

configuracion:
  Clona este repositorio:
  --> git clone https://github.com/wilder512/RetoTecnicoWilde.git: rama:feaure/monorepo
  --> cd RetoTecnicoWilde
  
clona las imágenes Docker para ambos servicios:
   --> docker pull wilder512/clientes-service:1.0
   --> docker pull wilder512/transacciones-service:1.0

Crea un Docker network para conectar los servicios:
  comando: --> docker network create microservices-net

docker network create microservices-net

Iniciar los contenedores:
  --> docker run -d --name clientes-service --network microservices-net -p 8080:8080 wilder512/clientes-service:1.0
  --> docker run -d --name transacciones-service --network microservices-net -p 8081:8081 wilder512//transacciones-  service:1.0

Verificación:
    Servicio de Clientes: Disponible en http://localhost:8080/api/clientes
    Servicio de Transacciones: Disponible en http://localhost:8081/api/transactions

Pruebas con Postman:
Consultar saldo de cliente:
  http
    GET http://localhost:8080/api/clientes/{id}
  
Crear transacción:
  http
    POST http://localhost:8081/api/transactions
        Content-Type: application/json
        Body: 
        {
          "clientId" : 1,
          "accountId": "123456789",
          "amount": 100000.00,
          "type": "purchase"
        }

ambiente aws. 
