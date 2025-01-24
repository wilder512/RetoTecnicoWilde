# RetoTecnicoWilder

![image](https://github.com/user-attachments/assets/b302862c-b631-4f68-ae9b-91021c160269)
descripcion: en esta arqruitectura vemos un modelo que permite realizar la autorizaciones de compra que se utiliza hoy con la plataforma authentic, la cual genera las autorizaciones de compra que se hacen por las diferentes redes y retiros en cajero, lo que permite el mapeo de esta informacion en linea con el producto debito, y su integracion con el cor de cuarta generacion para la aplicacion de estas trasacciones en sus respectivas cuentas (deprositos)

Microservicios: Clientes y Transacciones
Este proyecto consta de dos microservicios:

Servicio de Clientes (consumo con el cor de cuarta generacion): Funciona como una "cartera", almacenando la información de los clientes y sus saldos.
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

Implementación de CI -> .github/main.yml
El pipeline de integración continua está configurado en GitHub Actions para automatizar la construcción, prueba y implementación de los servicios.

Flujo del oleoducto
Eventos Desparadores:

pushy pull_requesta la rama tunkque afecta a los directorios Clientes/**o Transaciones/**.
Pasos principales:

Verificación del código fuente.
Configuración de JDK 17 (Temurin).
Construcción y pruebas unitarias de cada servicio ( Clientes, Transaciones) usando Gradle.
Construcción de imágenes Docker para cada servicio.
Publicación de las imágenes en Docker Hub.
Imágenes de Docker:
<img width="1210" alt="image" src="https://github.com/user-attachments/assets/04c6296f-a8fc-42e1-a008-5847d5264b96" />

Las imágenes se publican con el formato:

dockerhub-wilder512/transacciones:latest.

dockerhub-wilder512/clientes:latest.
<img width="1071" alt="image" src="https://github.com/user-attachments/assets/9afe338c-3204-4744-9cd4-5bc04c045c57" />



Despliegue en aws. 

se crea balanceador de carga:

![image.png](attachment:e028785d-ebb5-4b2c-92a7-4b06aed1a9a4:image.png)

se crea cluster ecs

![image.png](attachment:5a833c84-b272-41d3-a491-b85f0881f24b:image.png)

se crea lista de tareas:

![image.png](attachment:6d35d959-a04e-47e3-b373-ced87a9044a0:image.png)

url de balanceador:  [ALB-retoTecnico-952606943.us-east-1.elb.amazonaws.com](http://alb-retotecnico-952606943.us-east-1.elb.amazonaws.com/)

en la carpeta  deployAws, estan todos los stacks de cloudformation necesarios para el despliegue.

coleccion de postman para realizar las pruebas.
[RetoTecnico.postman_collection.json](https://github.com/user-attachments/files/18542276/RetoTecnico.postman_collection.json)
