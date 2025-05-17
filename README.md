# ChallengeForo

## Descripción del Proyecto

ChallengeForo es una aplicación de API REST desarrollada en Java con Spring Boot, diseñada para gestionar un foro de discusión. Permite a los usuarios registrarse, iniciar sesión, crear tópicos de discusión, publicar respuestas y administrar cursos relacionados con los tópicos.

## Funcionalidades Principales

*   **Gestión de Usuarios:**
    *   Registro de nuevos usuarios.
    *   Autenticación de usuarios.
    *   Actualización de perfiles de usuario.
*   **Gestión de Tópicos:**
    *   Creación de nuevos tópicos de discusión.
    *   Listado de tópicos existentes (con paginación y filtros).
    *   Obtención de detalles de un tópico específico.
    *   Actualización de tópicos.
    *   Eliminación de tópicos.
*   **Gestión de Respuestas:**
    *   Publicación de respuestas a tópicos existentes.
    *   Listado de respuestas para un tópico.
    *   Actualización de respuestas.
    *   Eliminación de respuestas.
*   **Gestión de Cursos:**
    *   Creación de cursos a los que pueden pertenecer los tópicos.
    *   Listado de cursos.
    *   Actualización de información de cursos.
    *   Eliminación de cursos.

## Cómo se Usa

La aplicación expone una API REST que puede ser consumida por cualquier cliente HTTP (como un frontend web, una aplicación móvil, o herramientas como Postman o curl).

### Prerrequisitos

*   Java JDK 17 o superior.
*   Maven 3.8 o superior.
*   Una base de datos (por ejemplo, H2, MySQL, PostgreSQL). La configuración por defecto podría usar una base de datos en memoria como H2 para desarrollo.

### Ejecución Local

1.  **Clonar el repositorio:**
    ```bash
    git clone <URL_DEL_REPOSITORIO>
    cd ChallengeForo
    ```
2.  **Configurar la base de datos:**
    Modifica el archivo `src/main/resources/application.properties` para apuntar a tu instancia de base de datos, no se utilizo contraseña para desarrollarlo ya que se hizo por una sola persona, pero es una buena practica añadir contraseña en la configuración aunque no sea para producción. Importante se utilizo MongoDB como base de datos, si se busca usar otra base de datos es necesario adapatar el codigo del proyecto:
    ```properties
    
    spring.application.name=Foro
    spring.data.mongodb.host=localhost
    spring.data.mongodb.port=27017
    spring.data.mongodb.database=foro
    #Configuración recomendable para añadir
    #spring.data.mongodb.username=usuario_prod_mongo
    #spring.data.mongodb.password=contraseña_prod_mongo
    ```
3.  **Compilar y ejecutar la aplicación:**
    Puedes usar el wrapper de Maven incluido:
    *   En Windows:
        ```bash
        mvnw spring-boot:run
        ```
    *   En macOS/Linux:
        ```bash
        ./mvnw spring-boot:run
        ```
    La aplicación estará disponible por defecto en `http://localhost:8080`.

### Endpoints Principales (Ejemplos)

*   **Usuarios:**
    *   `POST /usuarios` - Registrar un nuevo usuario.
    *   `POST /login` - Iniciar sesión (puede requerir configuración de Spring Security).
*   **Tópicos:**
    *   `POST /topicos` - Crear un nuevo tópico.
    *   `GET /topicos` - Listar todos los tópicos.
    *   `GET /topicos/{id}` - Obtener un tópico por ID.
*   **Respuestas:**
    *   `POST /respuestas` - Crear una nueva respuesta (asociada a un tópico).
*   **Cursos:**
    *   `POST /cursos` - Crear un nuevo curso.
    *   `GET /cursos` - Listar todos los cursos.

(Nota: Los endpoints exactos y los cuerpos de las solicitudes/respuestas deben consultarse en la documentación de la API o en el código de los controladores).

## Configuración para otros Entornos

Para configurar la aplicación para diferentes entornos (desarrollo, pruebas, producción), Spring Boot utiliza perfiles.

1.  **Crear archivos de propiedades específicos del perfil:**
    Puedes crear archivos como `application-dev.properties`, `application-prod.properties`, etc., en el directorio `src/main/resources/`.
    Por ejemplo, `application-prod.properties` para un entorno de producción con MongoDB podría tener:
    ```properties
    spring.application.name=Foro
    spring.data.mongodb.host=servidor_produccion_mongo
    spring.data.mongodb.port=27017
    spring.data.mongodb.database=foro_prod
    spring.data.mongodb.username=usuario_prod_mongo
    spring.data.mongodb.password=contraseña_prod_mongo
    # Otras configuraciones específicas de producción para MongoDB
    ```

2.  **Activar un perfil:**
    Puedes activar un perfil al ejecutar la aplicación mediante una propiedad del sistema, una variable de entorno o en el propio `application.properties`.
    *   **Argumento de línea de comandos:**
        ```bash
        java -jar target/ChallengeForo-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
        ```
        o con Maven:
        ```bash
        mvnw spring-boot:run -Dspring-boot.run.profiles=prod
        ```
    *   **Variable de entorno:**
        Establece la variable `SPRING_PROFILES_ACTIVE` a `prod`.
    *   **En `application.properties` (menos común para cambiar entre entornos):**
        ```properties
        spring.profiles.active=dev
        ```

De esta manera, puedes mantener diferentes configuraciones para la base de datos, niveles de logging, y otros parámetros específicos del entorno sin modificar el código fuente principal.

