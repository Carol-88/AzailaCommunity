### **1. Descripción del Proyecto**

Este proyecto es el backend de una plataforma de gestión de eventos comunitarios para el pueblo de Azaila, Teruel. Su objetivo es facilitar a los habitantes la creación, organización y participación en eventos locales, fomentando la interacción y la vida social en la comunidad.

La aplicación es una **API RESTful** desarrollada con **Java 17** y el framework **Spring Boot**. Utiliza **JPA** para la persistencia de datos en una base de datos **MySQL**, implementando todas las operaciones CRUD (Create, Read, Update, Delete) para sus principales entidades.

La arquitectura sigue el patrón **MVC (Modelo-Vista-Controlador)**, donde el **Controller** gestiona las peticiones HTTP, el **Service** encapsula la lógica de negocio y el **Repository** maneja la comunicación con la base de datos.

### **2. Diagrama de Clases del Proyecto**

El diseño de la aplicación se basa en cinco entidades principales que se relacionan entre sí. Para gestionar los diferentes tipos de usuarios (habitantes, organizadores y personal), se ha implementado una estrategia de **herencia en JPA**, que demuestra el conocimiento de un diseño de bases de datos más complejo.

* **`Persona`**: Clase abstracta de la cual heredan las demás entidades de usuario.

* **`Habitante`**: Representa a un usuario que puede unirse a eventos como participante.

* **`Organizador`**: Representa a un usuario que puede crear eventos y unirse a ellos como participante.

* **`Staff`**: Representa a un usuario que colabora en la organización de eventos y también puede unirse a otros eventos como participante.

* **`Evento`**: Representa un evento comunitario, creado por un `Organizador`.

A continuación, se presenta un diagrama de clases que ilustra la estructura de datos y sus relaciones:

* **Relación de Herencia**: Las clases `Habitante`, `Organizador` y `Staff` heredan de la clase abstracta `Persona`. Se utilizará la estrategia de herencia **Joined** para mantener la normalización de la base de datos.

* **Relación `Organizador` a `Evento`**: Un `Organizador` puede crear muchos `Eventos` (relación 1 a N).

* **Relación `Staff` a `Evento`**: Un miembro del `Staff` puede colaborar en muchos `Eventos` (relación 1 a N).

* **Relación `Persona` a `Evento` (Participación)**: Una `Persona` (ya sea `Habitante`, `Organizador` o `Staff`) puede participar en muchos `Eventos`. Esta relación de muchos a muchos se gestiona a través de la entidad de unión `Participación`.

### **3. Configuración y Setup**

Para configurar y ejecutar el proyecto de forma local, sigue estos pasos:

1. **Clona el repositorio de GitHub:**
   `git clone <URL_DEL_REPOSITORIO>`

2. **Configura la base de datos:**

    * Crea una base de datos en MySQL llamada `azaila_db`.

    * Ajusta las credenciales de conexión en el archivo `src/main/resources/application.properties` o `application.yml`.

3. **Ejecuta la aplicación:**

    * Desde tu IDE (IntelliJ, Eclipse), ejecuta la clase principal de la aplicación.

    * Desde la línea de comandos, utiliza Maven: `mvn spring-boot:run`

La API estará disponible en `http://localhost:8080`.

### **4. Tecnologías Utilizadas**

* **Lenguaje de Programación:** Java 17

* **Framework:** Spring Boot

* **ORM:** Spring Data JPA

* **Base de Datos:** MySQL

* **Gestor de Dependencias:** Maven

* **Control de Versiones:** Git & GitHub

### **5. Estructura de Controladores y Rutas**

La API se estructura en múltiples controladores, cada uno con un conjunto de rutas **RESTful** para gestionar sus respectivas entidades y roles de usuario. Se ha priorizado el uso de `PATCH` para actualizaciones parciales, siguiendo las mejores prácticas REST.

* **`HabitanteController`** (`/api/v1/habitantes`)

    * **GET `/`**: Obtiene todos los habitantes.

    * **GET `/{id}`**: Obtiene un habitante por ID.

    * **POST `/`**: Crea un nuevo habitante.

    * **PATCH `/{id}`**: Actualiza un habitante.

    * **DELETE `/{id}`**: Elimina un habitante.

* **`OrganizadorController`** (`/api/v1/organizadores`)

    * **GET `/`**: Obtiene todos los organizadores.

    * **GET `/{id}`**: Obtiene un organizador por ID.

    * **POST `/`**: Crea un nuevo organizador.

    * **PATCH `/{id}`**: Actualiza un organizador.

    * **DELETE `/{id}`**: Elimina un organizador.

* **`StaffController`** (`/api/v1/staff`)

    * **GET `/`**: Obtiene todos los miembros del staff.

    * **GET `/{id}`**: Obtiene un miembro del staff por ID.

    * **POST `/`**: Crea un nuevo miembro del staff.

    * **PATCH `/{id}`**: Actualiza un miembro del staff.

    * **DELETE `/{id}`**: Elimina un miembro del staff.

* **`EventoController`** (`/api/v1/eventos`)

    * **GET `/`**: Obtiene todos los eventos.

    * **GET `/{id}`**: Obtiene un evento por ID.

    * **POST `/`**: Crea un nuevo evento.

    * **PATCH `/{id}`**: Actualiza un evento.

    * **DELETE `/{id}`**: Elimina un evento.

* **`ParticipacionController`** (`/api/v1/participaciones`)

    * **POST `/`**: Crea una nueva participación (un usuario se apunta a un evento).

    * **DELETE `/{id}`**: Elimina una participación.

### **6. Enlaces Adicionales**

* **URL del repositorio de GitHub:** `<URL_DEL_REPOSITORIO>`

* **URL de las diapositivas de la presentación:** (Próximamente)

### **7. Trabajo Futuro (Módulo 3)**

* **Frontend Sencillo:** Creación de una interfaz de usuario minimalista y amigable para personas mayores, con una lista de eventos y los botones "Crear evento" y "Apuntarme".

* **Seguridad:** Implementación de autenticación con **Spring Security** (Bearer Authentication).

* **Integraciones:** Posible conexión con servicios externos para notificaciones (ej. vía WhatsApp) y otros sistemas comunitarios.

### **8. Miembros del Equipo**

* **Carolina Romero** - Desarrolladora y Arquitecta del proyecto.