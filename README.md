# ZooLogic · Java y MySQL

**Prototipo de aplicación de escritorio para practicar autenticación, persistencia relacional e interfaces con Java Swing.**

![Compilación Java](https://github.com/rrrarrra9/Zoologic-App-Java/actions/workflows/java-build.yml/badge.svg)

Proyecto académico orientado a un sistema de gestión de zoológico. La versión actual implementa la base de usuarios y acceso; los módulos de animales, instalaciones y gestión operativa siguen pendientes.

## Funcionalidades actuales

- Ventana de acceso con validación de campos obligatorios.
- Registro de usuarios con nombre, apellido, correo y rol.
- Inicio de sesión contra una base de datos MySQL.
- Pantalla de bienvenida con el nombre y rol del usuario.
- Componentes visuales reutilizables en `Theme.java`.
- Consultas JDBC parametrizadas y cierre de recursos con `try-with-resources`.

## Tecnologías y organización

| Área | Implementación |
| --- | --- |
| Lenguaje | Java, compilación configurada para JDK 17 |
| Interfaz | Swing y AWT |
| Persistencia | MySQL y JDBC |
| Configuración | dotenv-java y archivo `.env` |
| Dependencias y ejecución | Maven |
| Validación automática | GitHub Actions: compilación y empaquetado |

El código separa modelo, controladores de acceso a datos y vistas. Mantiene los nombres originales de los paquetes: `Model`, `Controller` y `Vist`.

## Puesta en marcha

Requisitos: JDK 17 o superior, Maven 3.8 o superior, MySQL y un entorno gráfico.

```bash
git clone https://github.com/rrrarrra9/Zoologic-App-Java.git
cd Zoologic-App-Java
cp .env.example .env
```

En PowerShell, utiliza `Copy-Item .env.example .env`.

1. Ejecuta [database/schema.sql](database/schema.sql) en una instalación local de MySQL. Crea la base `zoologic` y la tabla `usuario` que esperan los controladores.
2. Ajusta `DB_URL`, `DB_USER` y `DB_PASSWORD` en `.env` con tus credenciales locales.
3. Compila y ejecuta desde la raíz del repositorio:

```bash
mvn --batch-mode --no-transfer-progress verify
mvn exec:java
```

Maven descarga MySQL Connector/J y dotenv-java. No necesitas enlazar JAR desde una carpeta de descargas personal. En IntelliJ IDEA, abre el proyecto como proyecto Maven e importa `pom.xml`.

## Recorrido de demostración

1. Pulsa **Registrarse**.
2. Introduce datos ficticios y selecciona el rol **CLIENTE**.
3. Inicia sesión con el correo y contraseña que acabas de registrar.
4. Comprueba la pantalla de bienvenida.

Utiliza una base de datos de desarrollo y contraseñas de prueba.

## Archivos principales

| Ruta | Responsabilidad |
| --- | --- |
| `src/Main.java` | Arranque de la interfaz en el hilo de Swing |
| `src/Model/Usuario.java` | Modelo de usuario |
| `src/Controller/DBConnection.java` | Conexión JDBC desde variables de entorno |
| `src/Controller/UserController.java` | Consultas, registro y autenticación |
| `src/Vist/LoginFrame.java` | Formularios de acceso y registro |
| `src/Vist/DashboardFrame.java` | Pantalla inicial tras autenticación |
| `src/Vist/Theme.java` | Colores y componentes reutilizables |
| `database/schema.sql` | Esquema mínimo para una instalación local |

## Estado y siguientes mejoras

Este proyecto es un prototipo de aprendizaje. El registro permite seleccionar roles, incluida la opción ADMIN, y las contraseñas usan SHA-256 sin salt. Para un uso real habría que restringir la asignación de roles y migrar a un algoritmo de contraseñas como bcrypt o Argon2.

También quedan pendientes:

- Mostrar errores de base de datos correctamente antes de confirmar un registro.
- Evitar llamadas JDBC en el hilo de la interfaz.
- Incorporar los módulos de gestión del zoológico.
- Añadir pruebas de integración de registro y autenticación.

La automatización actual comprueba la compilación y el empaquetado; no ejecuta la interfaz ni valida una conexión real a MySQL.

## Autor

[Raúl Ortiz Sánchez](https://github.com/rrrarrra9) · Estudiante de Desarrollo de Aplicaciones Multiplataforma.
