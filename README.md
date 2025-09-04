# Plataforma de Gestión de Eventos Comunitarios - Azaila

## 1. Descripción del Proyecto

Este proyecto es el **backend** de una plataforma de gestión de eventos comunitarios para el pueblo de Azaila, Teruel.  
Su objetivo es facilitar a los habitantes la **creación, organización y participación** en eventos locales, fomentando la interacción y la vida social en la comunidad.

La aplicación es una **API RESTful** desarrollada con **Java 17** y el framework **Spring Boot**.  
Utiliza **Spring Data JPA** para la persistencia de datos en una base de datos **MySQL**, implementando todas las operaciones CRUD (Create, Read, Update, Delete) para sus principales entidades.

La arquitectura sigue el patrón **MVC (Modelo-Vista-Controlador) + otras capas adicionales para manejar aspectos específicos como la configuración o las excepciones**:

- **Model**: define las entidades y sus relaciones.
- **Controller**: gestiona las peticiones HTTP.
- **Service**: encapsula la lógica de negocio.
- **Repository**: maneja la comunicación con la base de datos.
- **DTO**: garantizan una separación clara entre las entidades y las respuestas de la API.
- **Config**: contiene un inicializador de datos para pruebas.
- **Exception**: maneja las excepciones personalizadas.

---

## 2. Diagrama de Clases del Proyecto

El diseño se basa en cinco entidades principales, relacionadas mediante **herencia en JPA** y asociaciones entre ellas:

* **`Persona`**: Clase abstracta de la cual heredan `Habitante`, `Organizador` y `Staff`.
* **`Habitante`**: Usuario que puede unirse a eventos como participante.
* **`Organizador`**: Usuario que puede crear eventos y unirse a ellos.
* **`Staff`**: Usuario que colabora en la organización de eventos y también puede participar en otros.
* **`Evento`**: Representa un evento comunitario, creado por un `Organizador`.

### Relaciones
- **Herencia**: `Habitante`, `Organizador` y `Staff` heredan de `Persona`. Se usará la estrategia de herencia **JOINED**.
- **Organizador → Evento**: Un `Organizador` puede crear muchos `Eventos` (1:N).
- **Staff → Evento**: Un miembro del `Staff` puede colaborar en muchos `Eventos` (1:N).
- **Persona ↔ Evento**: Una `Persona` puede participar en muchos `Eventos` mediante la relación **muchos a muchos**.
  - Esta relación se implementa con JPA y genera automáticamente la **tabla intermedia `participacion`** (sin necesidad de crear un modelo aparte).

📌 *El diagrama UML se incluye en la carpeta `/docs` del repositorio.*

### Estructura del Proyecto
```
├───.idea
├───.mvn
│   └───wrapper
└───src
    ├───main
    │   ├───java
    │   │   └───azaila
    │   │       └───community
    │   │           ├───config
    │   │           ├───controller
    │   │           ├───dto
    │   │           │   ├───evento
    │   │           │   ├───habitante
    │   │           │   ├───organizador
    │   │           │   └───staff
    │   │           ├───enums
    │   │           ├───exception
    │   │           ├───model
    │   │           ├───repository
    │   │           └───service
    │   │               ├───impl
    │   │               └───interfaces
    │   └───resources
    │       ├───static
    │       └───templates
    └───test
        └───java
            └───azaila
                └───community
```
---

## 3. Configuración y Setup

1. **Clona el repositorio:**
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   ```
2. **Configura la base de datos MySQL:**
   - Crea una base de datos llamada `azaila_events`.
   - Actualiza las credenciales en `src/main/resources/application.properties`:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:TU_PUERTO/azaila_events
     spring.datasource.username=TU_USUARIO
     spring.datasource.password=TU_CONTRASEÑA
     ```
3. **Compila y ejecuta la aplicación:**
   Ejecuta la aplicación:
   - Desde el IDE (IntelliJ/Eclipse): ejecuta la clase principal AzailaCommunityApplication.
   - Desde consola con Maven:
     ```bash
     mvn spring-boot:run
     ```
La API estará disponible en: http://localhost:8080

## 4. Tecnologías utilizadas:
   - Java 17
   - Spring Boot
   - Spring Data JPA
   - MySQL
   - Maven
   - Lombok
   - Swagger (para documentación de la API)
   - JUnit y Mockito (para pruebas unitarias)
   - Git y GitHub (control de versiones)

## 5. Controladores y Rutas REST
La API se estructura en controladores por entidad, siguiendo buenas prácticas REST. Se contemplan endpoints para listar, crear, actualizar y eliminar entidades, así como para gestionar la relación muchos a muchos entre personas y eventos.
- HabitanteController (/api/v1/habitantes)
  - GET / → Listar todos los habitantes
  - GET /{id} → Obtener habitante por ID
  - POST / → Crear habitante
  - PATCH /{id} → Actualizar habitante
  - DELETE /{id} → Eliminar habitante
- StaffController (/api/v1/staff)
  - GET / → Listar todo el staff
  - GET /{id} → Obtener staff por ID
  - POST / → Crear staff
  - PATCH /{id} → Actualizar staff
  - DELETE /{id} → Eliminar staff
- OrganizadorController (/api/v1/organizadores)
  - GET / → Listar todos los organizadores
  - GET /{id} → Obtener organizador por ID
  - POST / → Crear organizador
  - PATCH /{id} → Actualizar organizador
  - DELETE /{id} → Eliminar organizador
- EventoController (/api/v1/eventos)
  - GET / → Listar todos los eventos
  - GET /{id} → Obtener evento por ID
  - POST / → Crear evento
  - PATCH /{id} → Actualizar evento
  - DELETE /{id} → Eliminar evento
  - POST /{eventoId}/apuntarse/{personaId} → Añadir participante a un evento
  - DELETE /{eventoId}/desapuntarse/{personaId} → Quitar participante de un evento
## 6. Enlaces
- [Repositorio GitHub](<URL_DEL_REPOSITORIO>)
- [Documentación Swagger](http://localhost:8080/swagger-ui.html) (una vez la aplicación esté en ejecución)
- [Diagrama UML](./docs/diagrama_uml.png)
- [Enlace a la presentación](<URL_DE_LA_PRESENTACION>)
## 7. Autor
  - Nombre: [Carolina Romero]
