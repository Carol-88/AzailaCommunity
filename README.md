### **1. Descripción del Proyecto**

Este proyecto es el backend de una plataforma de gestión de eventos comunitarios para el pueblo de Azaila, Teruel. Su objetivo es facilitar a los habitantes la creación, organización y participación en eventos locales, fomentando la interacción y la vida social en la comunidad.

La aplicación es una **API RESTful** desarrollada con **Java 17** y el framework **Spring Boot**. Utiliza **JPA** para la persistencia de datos en una base de datos **MySQL**, implementando todas las operaciones CRUD (Create, Read, Update, Delete) para sus principales entidades.

La arquitectura sigue el patrón **MVC (Modelo-Vista-Controlador)**, donde el **Controller** gestiona las peticiones HTTP, el **Service** encapsula la lógica de negocio y el **Repository** maneja la comunicación con la base de datos.

### **2. Diagrama de Clases del Proyecto**

El diseño de la aplicación se basa en cinco entidades principales que se relacionan entre sí:

* **`Persona`**: Representa a un habitante del pueblo, que puede ser organizador y/o participante de eventos. Clase abstracta de la cual heredaran Habitante, Organizador, y Staff.
*  **`Habitante`** : Puede unirse a eventos como participante.
*  **`Organizador`** : Puede organizar eventos y unirse a eventos como participante. Relacion monodireccional con el evento que crea.
*  **`Staff`**: Puede colaborar en la organizacion de eventos y unirse a otros eventos como participante. Relacion monodireccional con el evento en el que participa.
* **`Evento`**: Representa un evento comunitario, creado por un `Organizador`.
  
A continuación, se presenta un diagrama de clases que ilustra la estructura de datos y sus relaciones:

* **Relación `Usuario` a `Evento`**: Un `Usuario` puede ser el organizador de muchos `Eventos` (relación 1 a N).

* **Relación `Usuario` a `Participacion`**: Un `Usuario` puede tener muchas `Participaciones` (relación 1 a N).

* **Relación `Evento` a `Participacion`**: Un `Evento` puede tener muchas `Participaciones` (relación 1 a N).

### **3. Configuración y Setup**

Para configurar y ejecutar el proyecto de forma local, sigue estos pasos:

1.  **Clona el repositorio de GitHub:**
    `git clone <URL_DEL_REPOSITORIO>`

2.  **Configura la base de datos:**

    * Crea una base de datos en MySQL llamada `azaila_db`.

    * Ajusta las credenciales de conexión en el archivo `src/main/resources/application.properties` o `application.yml`.

3.  **Ejecuta la aplicación:**

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

La API se estructura en tres controladores, cada uno con un conjunto de rutas **RESTful** para gestionar sus respectivas entidades. Se ha priorizado el uso de `PATCH` para actualizaciones parciales, siguiendo las mejores prácticas REST.

* **`UsuarioController`** (`/api/v1/usuarios`)

    * **GET `/`**: Obtiene todos los usuarios.

    * **GET `/{id}`**: Obtiene un usuario por ID.

    * **POST `/`**: Crea un nuevo usuario.

    * **PATCH `/{id}`**: Actualiza un usuario.

    * **DELETE `/{id}`**: Elimina un usuario.

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
