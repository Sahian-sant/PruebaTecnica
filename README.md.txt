API REST - Prueba Técnica


Esta solución permite administrar usuarios utilizando Spring Boot y Java 17.


Requisitos Necesarios:

Java JDK 17

Maven 3.6 o superior

Oracle Database (Local o Remoto)

Docker (Opcional): Este nos ayuda a que nuestro programa funcione de manera aislada, sin necesidad de instalar dependencias adicionales en el sistema operativo.


Tecnologias y Dependencias:

Spring Web: Para la creación de los endpoints REST.

Oracle JDBC Driver: Conector oficial para la base de datos.

Springdoc OpenAPI (Swagger): Para la documentación interactiva de la API.

Maven: Gestión de dependencias y construcción del ciclo de vida del proyecto.


*Este proyecto utiliza lassiguientes anotaciones para el funcionamiento de Spring

@RestController:Manejo de peticiones REST que devolvera objetos en formato JSON.
@PostMapping:Para mapeo de rutas
@GetMapping:Para mapero derutas
@RequestBody:Vincula el cuerpo de la petición HTTP
@Service:Marcar la logica para el funcionamiento de los procesos


*Seguridad
Utiliza cifrado de seguridad AES-256 para proteger las contraseñas del usuario

El uso de Zona horaria configurada deacuerdo a los requerimientos,al igual se le dio un formato de fecha


Configuración y Ejecución
Obtener el proyecto: Clone el repositorio o extraiga el archivo ZIP.
para realizar laclonacion del proyecto:
git clone https://github.com/Sahian-sant/PruebaTecnica.git

Configurar Base de Datos: Editar sus credenciales en el archivo src/main/resources/application.properties que se encuentra dentro de la carpeta del proyecto.

Compilar y Ejecutar:
Desde la terminal (Git Bash o CMD) en la raíz del proyecto, ejecute los siguiente comandos:

mvn clean package -DskipTests :este es para limpiar versiones   

java -jar target/*.jar:Toma el archivo creado y prende la aplicaion


Pruebas
Se realizaron las pruebas  primero en postman para posteiormente en swagger
se adjunta el archivo de postman que lleva por nombre Consulta de prueba.postman_collection.json para ser importado automaticamente los abrira y mostrara la consulta en la interfaz y la ruta de swagger que se obtine por defecto cuando se emplea en caso en el pom.al igual de las consultas que se realizaron

Swagger:http://localhost:9000/swagger-ui/index.html


postman:
Listar Usuarios
GET:http://localhost:9000/users?filter=name+co+user&sortedBy=name

Crear Usuario
POST:http://localhost:9000/users

Actualizar(se necesita obtener el id  que  quiere actualizar)
PATH:http://localhost:9000/users/{id}

Eliminar  (se necesita el id a eliminar)
DELETE:http://localhost:9000/users/{id}

Login
POST:http://127.0.0.1:9000/users/login?taxId=   &password=



SE ADJUNTA LA LIGA DE GIT: https://github.com/Sahian-sant/PruebaTecnica.git

