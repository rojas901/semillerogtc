# REST API semillero GTC

Esta aplicación es el proyecto final para el semillero realizado por GTC.

Para usar la aplicación puede clonar el repositorio usando el siguiente comando en su consola`git clone https://github.com/rojas901/semillerogtc.git`.

Luego abrirla en su editor de codigo y ejecutar en consola `mvn install`.

Por ultimo ejecutar `mvn spring-boot:run` en la consola, para iniciar la aplicación.

# REST API

Luego que tenga la aplicación arriba, con la ayuda de postman podra probar las siguiente rutas.

## Registrar usuario

### Request

Metodo: POST

URL: http://localhost:8080/usuarios/v1/registrarse

Body JSON:
{
    "nombre": String,
	"email": String,
	"password": String,
	"telefonos": [
		{
			"number": String,
			"citycode": String,
			"countrycode": String
		}
	]
}

Nota: Registre varios usuarios para una mejor experiencia.

## Login con usuario creado

### Request

Metodo: POST

URL: http://localhost:8080/usuarios/v1/login

Body JSON:
{
    "email": String,
	"password": String
}

Nota: Cuando se loguea correctamente, en la respuesta de la petición obtendra un token, este token debe agregarse a todas las peticiones que se van a mostrar en adelante, creando un header llamado authorization y en su valor agregando el token obtenido.

## Obtener todos los usuarios

### Request

Metodo: GET

URL: http://localhost:8080/usuarios/v1

headers: Authorization: token de login

## Obtener usuario por id

### Request

Metodo: GET

URL: http://localhost:8080/usuarios/v1/id/{id}

headers: Authorization: token de login

Nota: {id} es una notación para indicar que en esta parte de la ruta usted debe pegar el id que desea buscar.

## Obtener usuario por email

### Request

Metodo: GET

URL: http://localhost:8080/usuarios/v1/email/{email}

headers: Authorization: token de login

Nota: {email} es una notación para indicar que en esta parte de la ruta usted debe pegar el email que desea buscar.

## Obtener usuario por nombre

### Request

Metodo: GET

URL: http://localhost:8080/usuarios/v1/nombre/{nombre}

headers: Authorization: token de login

Nota: {nombre} es una notación para indicar que en esta parte de la ruta usted debe pegar el nombre que desea buscar.

## Actualizar un usuario

### Request

Metodo: PATCH

URL: http://localhost:8080/usuarios/v1/{id}

headers: Authorization: token de login

Body JSON {
    "email": String,
	"password": String
}

Nota: {id} es una notación para indicar que en esta parte de la ruta usted debe pegar el id que desea actualizar. Tenga en cuenta que si actualiza el correo del usuario del cual esta usando el token, debera hacer loguin nuevamente para obtener el nuevo token y actualizar el token en todas las rutas que desee usar.

## Borrar un usuario

Metodo: PATCH

URL: http://localhost:8080/usuarios/v1/{id}

headers: Authorization: token de login

Nota: {id} es una notación para indicar que en esta parte de la ruta usted debe pegar el id que desea borrar. Tenga en cuenta que si elimina el usuario del cual esta usando el token, debera hacer loguin nuevamente con usuario registrado para obtener el nuevo token y actualizar el token en todas las rutas que desee usar.