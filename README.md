# Mercado libre ejercicio country details

API rest que a partir de una dirección IP obtiene detales del país, como datos de la moneda, código ISO y conversión a otras monedas.

* [Ejercicio](#ejercicio)
  * [Implementación](#implementación)
  * [Experiencia](#experiencia)
* [Configuración](#configuración)
  * [Instrucciones](#instrucciones)
    * [Apache Tomcat](#apache-tomcat)
    * [MySql](#mysql)
      * [Crear base de datos y usuario](#crear-base-de-datos-y-usuario)
      * [Crear tablas en la base de datos](#crear-tablas-en-la-base-de-datos)
      * [Ejecución del proyecto](#ejecución-del-proyecto)
    * [Docker](#docker)
* [Explorar](#explorar)
  * [Servicios](#servicios)
    * [Country-detail](#country-detail)
    * [Banned-ip](#banned-ip )
* [Pruebas](#pruebas)
* [Sobre el código fuente](#sobre-el-código-fuente)

# Ejercicio

Para seguir avanzando en el proceso de selección han propuesto el siguiente escenario: Para coordinar acciones de respuesta ante fraudes, es útil tener disponible información
contextual del lugar de origen detectado en el momento de comprar, buscar y pagar. Para
ello se decide crear una herramienta que dada una IP obtenga información asociada.
El ejercicio consiste en construir una API Rest que permita:
  1. Dada una dirección IP, encontrar el país al que pertenece y mostrar:
    a. El nombre y código ISO del país.
    b. Moneda local y su cotización actual en dólares o euros.
  2. Ban/Blacklist de una IP: marcar la ip en una lista negra no permitiéndole consultar el la información del punto 1.

# Implementación

- [Java](https://www.java.com/en/)
- [Spring boot](https://spring.io/projects/spring-boot)
- [MySql](https://www.mysql.com/)
- [JUnit](https://spring.io/projects/spring-boot)
- [Swagger](https://swagger.io/)
- [Docker](https://www.docker.com/)
- [Apache Tomcat](http://tomcat.apache.org/)
 
## Experiencia

Cuando terminé de leer el ejercicio el principal objetivo era una API rest. Primero pensé en utilizar jersey, lo habia utilizado en ocasiones anteriores sin embargo, aproveché la oportunidad para introducirme a spring-boot. No fue sencillo pero paso a paso fui atandado cada uno de los cabos para que sea funcional. Para entregar el ejecicio indicaron que deseable era por medio de un docker file así que también tuve la oportunidad de aprender un poco sobre eso.

# Configuración

## Instrucciones

### Apache tomcat

- Descargar Apache Tomcat versión 8.5.64, descomprimirlo y ubicarlo en la unidad de preferencia.
- Añadirlo a eclipse.
  - En la pestaña servers
  
  ![image](https://user-images.githubusercontent.com/80859407/111659134-f8b27f80-87da-11eb-8643-2c4d5a4dff72.png)
  
  - Seleccionar la versión de tomcat
  
  ![image](https://user-images.githubusercontent.com/80859407/111659370-2b5c7800-87db-11eb-89ea-6f2f1f75028a.png)
  
  - Ubicar el home de tomcat
  
  ![image](https://user-images.githubusercontent.com/80859407/111659672-6bbbf600-87db-11eb-9e67-7231e7009cc5.png)
  
  - Iniciar el servidor y validar que no genere ningun error que bloquee la inicialización.

### MySql

- Descargar e instalar [XAMPP](https://www.apachefriends.org/index.html) Ahí llega una version de MySql que no necesita mucha configuración aparte de la creación de la base de datos.
- Iniciar MySql desde XAMPP 

![image](https://user-images.githubusercontent.com/80859407/111661421-023ce700-87dd-11eb-935e-dcb5d50ccbf9.png)

- En el home de xappm ir a la carpeta /bin

![image](https://user-images.githubusercontent.com/80859407/111661103-bab65b00-87dc-11eb-800e-0ae685a42d16.png)

#### Crear base de datos y usuario

- En la carpeta /bin ejecutar lo siguiente: mysql.exe -u root -p dar enter, cuando pida contraseña enter nuevamente.
- Ejecutar: ```CREATE DATABASE melidb;```
- Ejecutar: ```use melidb;```
- Ejecutar:  ```CREATE USER 'meli_user'@'localhost' IDENTIFIED  BY 'meli_pass';```

#### Crear tablas en la base de datos

- Estando dónde quedamos en el punto anterior en este repositorio esta la carpeta mysql, allí encontrarán las 4 tablas necesarias. Hay que ejecutarlas en el siguiente orden: banned_ip, country_detail, country_currency y currency_exchange.

#### Ejecución del proyecto

- Usa vez los pasos anteriores han ido completados, solo faltará clonar este proyecto que contiene el código fuente e importarlo a eclipse como "Maven project"

![image](https://user-images.githubusercontent.com/80859407/111665987-47fbae80-87e1-11eb-985a-cf2fe992e107.png)

- Finalmente ejecutarlo

![image](https://user-images.githubusercontent.com/80859407/111666568-d53f0300-87e1-11eb-8be3-f196c2883b4f.png)

## Docker

- Para realizar la instalación con usando docker se debe hacer lo sigueinte:
  1. Clonar el proyecto a una ruta local.
  2. Ubicarse en la carpeta docker dentro del proyecto.
  3. Ejecutar desde una terminal de comandos (Dentro de la carpeta docker): ```docker-compose up --build```

# Explorar

- Una vez esta iniciado la base de datos y el servidor se puede ingresar a la siguiente URL: http://localhost:8080/validaIp/swagger-ui.html allí podrá la documentacion en swagger del proyecto
- Puede utilizar [Postman](https://www.postman.com/) para importar el swagger.json del proyecto el cual se puede descargar desde la URL anterior. Igualmente se encuentra en la carpeta "postman", también encontará collecciones para las pruebas junto con datos para cada una de ellas.
- En los siguientes endpoints: http://localhost:8080/validaIp/country-detail/status y http://localhost:8080/validaIp/ban-ip/status ayudan a determinar si está listo para recibir peticiones la respuesta será:
  {
      "statusCode": 200,
      "transactionTimestamp": "17-03-2021 06:37:54",
      "data": "Ready"
  }
  
## Servicios
  
### Country detail
- El endpoint principal es http://localhost:8080/validaIp/country-detail/:ip path paramtero es la IP que se desea consultar y query parametros (currencyExchange) es a la moneda que se quiere hacer la conversión.
Por ejemplo para la ip 4.4.4.4 la respuesta es:
```
{
    "statusCode": 200,
    "transactionTimestamp": "17-03-2021 06:48:24",
    "data": {
        "name": "United States",
        "isoCode": "USA",
        "currency": {
            "name": "United States Dollar",
            "code": "USD",
            "symbol": "$"
        },
        "currencyExchanges": [
            {
                "value": 0.834695,
                "type": "EUR"
            },
            {
                "value": 1.0,
                "type": "USD"
            }
        ],
        "dmlTmst": "17-03-2021 11:48:39"
    }
}
```
Si enviamos la ip "my_ip_local" parte de la respuesta será:
```
"statusCode": 500,
"transactionTimestamp": "17-03-2021 07:04:11",
"error": {
    "code": "1002",
    "message": "Not valid IP: my_ip_local",
    "exception": "com.mercadoLibre.validaIp.exception.RestException: Not valid IP: my_ip_local",
```

Si enviamos la ip 1.1.1.1 y esta bloqueada parte de la respuesta será:
```
"statusCode": 500,
"transactionTimestamp": "17-03-2021 07:04:50",
"error": {
    "code": "1004",
    "message": "The IP: 1.1.1.1 is banned",
    "exception": "com.mercadoLibre.validaIp.exception.RestException: The IP: 1.1.1.1 is banned",
```

### Banned IP

Podemos prohibir IPs lo hacemos con el siguiente endpoint: POST http://localhost:8080/validaIp/ban-ip/:ip solo retorna el exito de la operación
```
{
    "statusCode": 200,
    "transactionTimestamp": "17-03-2021 07:15:04"
}
```

Podemos consultar una IP puntual con el siguiente endpoint: GET http://localhost:8080/validaIp/ban-ip/:ip su respuesta será:
```
{
    "statusCode": 200,
    "transactionTimestamp": "17-03-2021 07:15:49",
    "data": {
        "ip": "1.1.1.1",
        "dmlTmst": "2021-03-18T00:15:04.960+00:00"
    }
}
```
También otras dos operaciones complementarias se pueden consultar en la documentación swagger.

# Pruebas

Para las pruebas unitarias utilice junit y tambien utilicé postman allí cree llamados a los diferentes endpoints y confirme la respuesta para cada caso. En la carpeta postman podrán encontrar las colecciones datos. Para ejecutarlo desde postman solo será necesario importar cada colecció, seleccionar el conjuento de datos ejecutarla con runner.

# Sobre el código fuente

Cómo intervienen 3 servicios externos, consultarlos en línea siempre no es muy agradable además, la información de cada país no cambiará con mucha frecuencia. Por esa razón implemente un cache aunque está predeterminado a 60 segundos; se puede actualizar el valor con solo cambiar el archivo de propiedades de la aplicación.
