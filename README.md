# Prueba Técnica Devsu
Este repositorio alberga el proyecto solicitado como parte de la prueba técnica (Java) de Devsu.
Cuenta con dos Microservicios cada uno con su propio Dockerfile
* persona-ms
* cuenta-ms
Además, en la carpeta [database](database/) se encuentra el script de creación de la BD y el respectivo Dockerfile.
## Levantamiento del proyecto
El archivo docker-compose.yml sirve para poder levantar los contenedores de ambos microservicios y de la BD.
Los puertos en los que escucha cada componente son los siguientes:
* persona-ms (9080)
* cuenta-ms (9081)
* postgres (54320)

Para ejecutar el archivo docker-compose.yml deberá tener **docker** y **docker-compose** instalado, posicionarse dentro de la carpeta database y ejecutar el siguiente comando:
```bash
docker-compose up -d
```
## Base de datos
Los datos para conectarse a la BD son los siguientes:
* database: devsujava
* port: 54320
* user: devsu
* password: devsu
## Postman
El archivo [Devsu-prueba-tecnica.postman_collection.json](Devsu-prueba-tecnica.postman_collection.json) contiene la colección postman que podrá usar para probar los endpoints del proyecto.
