# AGENTS.md

## Reglas y convenciones para agentes en el proyecto Azaila Community

### 1. Inyección de dependencias
- Utiliza inyección por constructor para todos los servicios y repositorios.
- Anota los servicios con `@Service` y los repositorios con `@Repository` o extiende de `JpaRepository`.

### 2. Estructura de servicios
- Los servicios implementan interfaces ubicadas en `service.interfaces`.
- Los métodos de servicio deben ser transaccionales (`@Transactional`) cuando modifican datos.
- Lanza excepciones personalizadas (`ResourceNotFoundException`, etc.) para errores de negocio.

### 3. Controladores REST
- Anota los controladores con `@RestController` y mapea rutas con `@RequestMapping`.
- Utiliza DTOs para entrada y salida de datos.
- Valida los datos de entrada y responde con mensajes claros de error.

### 4. Repositorios
- Define métodos personalizados siguiendo la convención de nombres de Spring Data JPA (`findByEmail`, `existsByTelefono`, etc.).
- No incluyas lógica de negocio en los repositorios.

### 5. Modelo de datos
- Una persona solo puede tener un registro en la tabla `persona`.
- Una persona puede tener diferentes roles (organizador, staff, participante) en distintos eventos, pero nunca más de un rol en el mismo evento.
- Usa relaciones `@ManyToMany` y `@ManyToOne` para vincular personas y eventos según el rol.

### 6. Validaciones y lógica de negocio
- Valida unicidad de email y teléfono al crear personas.
- Impide que una persona tenga más de un rol en el mismo evento.
- Utiliza DTOs para exponer solo los datos necesarios.

### 7. Nombres y organización
- Usa nombres descriptivos y en español para clases, métodos y variables.
- Organiza el código en paquetes: `model`, `repository`, `service`, `controller`, `dto`, `exception`, `config`, `enums`.

### 8. Manejo de errores
- Usa excepciones personalizadas para errores de negocio.
- Devuelve respuestas HTTP adecuadas (404, 400, 201, etc.) en los controladores.

### 9. Pruebas
- Implementa pruebas unitarias para servicios y controladores usando JUnit y Mockito.

