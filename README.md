# Mercado libre ejercicio country details
API rest que a partir de una dirección IP obtiene detales del país, como datos de la moneda, codigo iso y conversión a otras monedas
* [Ejercicio](#ejercicio)
  * [Implementación](#implementación)
  * [Experiencia](#experiencia)
* [Configuración](#configuración)
  * [Instrucciones](#instrucciones)
* [Explorar](#explorar)
  * [Servicios](#servicios)
    * [Country-detail](#country-detail)
    * [Banned-ip](#banned-ip )
* [Pruebas](#pruebas)
* [Sobre el código fuente](#sobre-el-código-fuente)

# Ejercicio

## Implementación

- [Java](https://www.java.com/en/)
- [Spring boot](https://spring.io/projects/spring-boot)
- [MySql](https://www.mysql.com/)
- [Junit](https://spring.io/projects/spring-boot)
- [Swagger](https://swagger.io/)
- [Docker](https://www.docker.com/)
- [Apache Tomcat](http://tomcat.apache.org/)
 
### Experiencia

Cuando terminé de leer el ejercicio el principal objetivo era una API rest. Primero pensé en utilizar jersey, lo habia utilizado en ocasiones anteriores sin embargo, aproveché la oportunidad para introducirme a spring-boot. No fue sencillo pero paso a paso fui atandado cada uno de los cabos para que sea funcional. Para entregar el ejecicio indicaron que deseable era por medio de un docker file así que también tuve la oportunidad de aprender un poco sobre eso.

## Configuración

### Instrucciones

- Descargar Apache Tomcat versión 8.5.64, descomprimirlo y ubicarlo en la unidad de preferencia
- Descargar MySql Utilizar el puerto 3306, otra opión es descargar e instalar xampp finalmente desde una terminal de comandos ir al home de Myql luego ejecutar bin/mysql.exe o bin/mysql.sh. Los script a ejecutar se encuentran en la carpeta "mysql" en este repositorio
- Utilizar el archivo docker que se encuentra en la carpeta "docker" en este repositorio y realizar lo siguiente:
- Sino se utilizó el archivo docker clone este proyecto el importelo al IDE de su elección (En mi caso utilice eclipse)
- Añada el servidor al IDE en caso que desee gestionarlo desde allí
- Desde eclipse para ejecutar el proyecto solo hay que hacer click derecho en la raiz de proyecto -> Run as -> Run on Server
- Cuando la inicialización terminó y todo esta correcto se mostrará el sigueinte mensaje: INFO  [http-nio-8080-exec-2] o.s.w.s.FrameworkServlet: Completed initialization in 1 ms
- En la consola se comienza a ver el inicio de igual forma en la raiz del proyecto hay una carpeta "logs"

## Explorar

- Una vez esta iniciado la base de datos y el servidor se puede ingresar a la siguiente URL: http://localhost:8080/validaIp/swagger-ui.html allí podrá la documentacion en swagger del proyecto
- Puede utilizar postman (https://www.postman.com/) para importar el swagger.json del proyecto el cual se puede descargar desde la URL anterior. Igualmente se encuentra en la carpeta "postman". Allí tambien encontará collecciones para las pruebas junto con datos para cada una de ellas.
- En los siguientes endpoints: http://localhost:8080/validaIp/country-detail/status y http://localhost:8080/validaIp/ban-ip/status ayudan a determinar si está listo para recibir peticiones la respuesta será:
  {
      "statusCode": 200,
      "transactionTimestamp": "17-03-2021 06:37:54",
      "data": "Ready"
  }
  
### Servicios
  
#### Country detail
- El endpoint principal es http://localhost:8080/validaIp/country-detail/:ip path paramtero es la IP que se desea consultar y query parametros (currencyExchange) es a la moneda que se quiere hacer la conversión.
Por ejemplo para la ip 4.4.4.4 la respuesta es:
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
Si enviamos la ip "my_ip_local" parte de la respuesta será:

    "statusCode": 500,
    "transactionTimestamp": "17-03-2021 07:04:11",
    "error": {
        "code": "1002",
        "message": "Not valid IP: my_ip_local",
        "exception": "com.mercadoLibre.validaIp.exception.RestException: Not valid IP: my_ip_local",       
Si enviamos la ip 1.1.1.1 y esta bloqueada parte de la respuesta será:

    "statusCode": 500,
    "transactionTimestamp": "17-03-2021 07:04:50",
    "error": {
        "code": "1004",
        "message": "The IP: 1.1.1.1 is banned",
        "exception": "com.mercadoLibre.validaIp.exception.RestException: The IP: 1.1.1.1 is banned",

#### Banned IP

Podemos prohibir IPs lo hacemos con el sigueinte endpoint: POST http://localhost:8080/validaIp/ban-ip/:ip solo retorna el exito de la operación
{
    "statusCode": 200,
    "transactionTimestamp": "17-03-2021 07:15:04"
}

Podemos consultar una IP puntual con el sigueinte endpoint: GET http://localhost:8080/validaIp/ban-ip/:ip su respuesta será:

{
    "statusCode": 200,
    "transactionTimestamp": "17-03-2021 07:15:49",
    "data": {
        "ip": "1.1.1.1",
        "dmlTmst": "2021-03-18T00:15:04.960+00:00"
    }
}
Tambien otras dos operaciones complentarias se pueden consultar en la documentación swagger.

## Pruebas

Para las pruebas unitarias utilice junit y tambien utilicé postman allí cree llamados a los diferentes endpoints y confirme la respuesta para cada caso. En la carpeta postman podrán encontrar las colecciones datos. Para ejecutarlo desde postman solo será necesario importar cada colecció, seleccionar el conjuento de datos ejecutarla con runner.

## Sobre el código fuente

Cómo intervienen 3 servicios externos, consultarlos en línea siempre no es muy agradable además, la información de cada país no cambiará con mucha frecuencia. Por esa razón implemente un cache aunque está predeterminado a 60 segundos; se puede actualizar el valor con solo cambiar el archivo de propiedades de la aplicación.
