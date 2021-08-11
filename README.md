# Clip-Prueba
Prueba

Para levantar esta API de Spring - Boot tienes que seguir los siguientes pasos:

""Iniciar RabbitMQ en Docker
1.- Tener Docker instalado (en mi caso tengo la versión 3.1.0(51484)
2.- Abrir Docker, para verificar la versión en la terminal se tiene que ejecutar la siguiente línea: docker --version
3.- Una vez validada la versión de docker, podremos ejecutar la siguiente línea: docker pull rabbitmq:3-management
4.- Posterior, ejecutamos la siguiente línea para iniciar RabbitMQ en Docker images
5.- Si los pasos anteriores fueron correctos podras ingresar la url del localhost de RabbitMQ (http://localhost:15672/#/) y de deberá observar un apartado 
    donde se puede realizar un login con usuario y contraseña "guest" para ambos campos.
6.- Ingresando se tendrá una consola donde se podrán administrar los queues que se vayan creando, los mensajes, etc

""Clonar proyecto de github
7.- En el cmd o terminal del equipo que estés usando, deberás moverte a la ruta donde esté tu workspace (comando cd <carpeta>)
8.- Una vez en la ubicación del workspace ejecutaremos el comando -->> git clone https://github.com/FranciscoCE/Clip-Prueba.git
9.- Se descargará el proyecto en la ruta seleccionada

""Levantar proyecto en IDE
10.- En mi caso utilicé STS 4, para importar el proyecto damos click derecho en explorador de proyectos, seleccionamos importar y elegimos que será un tipo
     de proyecto Maven y al final proyectos Maven existentes
11.- Después damos click en el botón de seleccionar y elegimos el proyecto que se ha descargado.
12.- Una vez importado el proyecto, damos click derecho en el nombre del proyecto y seleccionamos ejecutar como "apliación de spring boot"
13.- El proyecto inciará
  
 ""Base de datos M2
14.- Una vez iniciado el proyecto, podremos entrar a la siguiente url ->http://localhost:9000/h2-console/
15.- Aqui validaremos la BD embebida en donde se almacenarán nuestros datos.
16.- Elegimos las siguientes opciones para inciar sesión:
     -Saved Settings: Generic H2 (Embedded)
     -Setting Name: Generic H2 (Embedded)
     -Driver Class: org.h2.Driver
     -JDBC URL: jdbc:h2:mem:testdb
     -User NAme: sa
     -Password: "" -> vacío (Campo en blanco)
 17.- Damos click en connect y entraremos a la consola administrativa de H2
 18.- En la parte superior izquierda se creo la tabla "REGISTRO", damos un click y en la ventana del lado derecho se colocará el query: SELECT * FROM REGISTRO 
 19.- Damos click en "run" y veremos la tabla creada pero sin registros aún
  
 ""Consumir endpoints
 20.- Se puede hacer directamente en un navegador o en alguna aplicación, en mi caso utilicé "Postman"
 21.- Para el publisher utilizamos la url (por ejemplo): http://localhost:9000/hit/Francisco, la cual envía el mensaje por rabbitMQ y guarda en BD
 22.- Para el consumer utilizamos la url (por ejemplo): http://localhost:9000/hit/, la cual recibe los mensajes por rabbitMQ y muestra los registros guardados.
  
 ""Consola del proyecto
 23.- Ahí se podrán observar los mensajes de envío y recibido de las colas.
  

