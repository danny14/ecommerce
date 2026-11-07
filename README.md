# E-Commerce Backend Service

Servicio backend desarrollado con Spring Boot y Java 25 bajo arquitectura limpia en capas, disenado para ser escalable, desacoplado y seguro.

---

## 1. Requisitos Previos

* Java Development Kit (JDK): Version 25 LTS (o superior).
* Maven Wrapper: Incluido en el repositorio (`mvnw` / `mvnw.cmd`). No requiere instalacion global previa de Maven.
* Puerto de red: `8081` (configurado por defecto en `application.properties` para prevenir colisiones con servicios en el puerto 8080).

---

## 2. Configuracion del Entorno (JAVA_HOME)

Dado que el proyecto compila bajo el estandar de Java 25, la variable de entorno `JAVA_HOME` debe apuntar a un JDK 25 valido antes de compilar o ejecutar en consola.

### Windows (PowerShell)
```powershell
# Configuracion temporal en la sesion actual:
$env:JAVA_HOME = "$HOME\.jdks\ms-25.0.4.1"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"

# Verificacion de version:
java -version
```

### Windows (CMD - Simbolo del sistema)
```cmd
:: Configuracion temporal en la sesion actual:
set "JAVA_HOME=%USERPROFILE%\.jdks\ms-25.0.4.1"
set "PATH=%JAVA_HOME%\bin;%PATH%"

:: Verificacion de version:
java -version
```

### Linux / macOS (Bash / Zsh)
```bash
export JAVA_HOME="$HOME/.jdks/ms-25.0.4.1"
export PATH="$JAVA_HOME/bin:$PATH"

java -version
```

---

## 3. Comandos de Maven Wrapper (mvnw)

El proyecto incluye Maven Wrapper configurado con Apache Maven 3.9.9 para garantizar compatibilidad con IDEs y el contenedor de inyeccion de dependencias (Plexus / Guice).

### En Windows (PowerShell)
```powershell
# 1. Limpiar artefactos anteriores
.\mvnw.cmd clean

# 2. Compilar el codigo fuente
.\mvnw.cmd compile

# 3. Ejecutar pruebas unitarias
.\mvnw.cmd test

# 4. Generar el archivo empaquetado (.jar)
.\mvnw.cmd clean package -DskipTests
```

### En Windows (CMD)
```cmd
# 1. Limpiar artefactos anteriores
mvnw.cmd clean

# 2. Compilar el codigo fuente
mvnw.cmd compile

# 3. Generar el archivo empaquetado (.jar)
mvnw.cmd clean package -DskipTests
```

### En Linux / macOS
```bash
chmod +x ./mvnw
./mvnw clean compile
./mvnw clean package -DskipTests
```

---

## 4. Ejecucion del Proyecto por Consola

Existen dos metodos estandar para iniciar el servicio desde la consola:

### Metodo A: Mediante el plugin de Spring Boot (Recomendado para desarrollo)

Este metodo compila las clases modificadas y levanta el servidor embebido Tomcat inmediatamente:

#### Windows PowerShell:
```powershell
.\mvnw.cmd spring-boot:run
```

#### Windows CMD:
```cmd
mvnw.cmd spring-boot:run
```

#### Sobrescribir puerto desde consola:
```powershell
.\mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--server.port=9090"
```

---

### Metodo B: Mediante el ejecutable JAR (Recomendado para produccion)

Primero empaquete el proyecto y luego ejecute el binario con la JVM:

```powershell
# Paso 1: Compilar y empaquetar
.\mvnw.cmd clean package -DskipTests

# Paso 2: Ejecutar el archivo JAR generado
java -jar target/ecommerce-0.0.1-SNAPSHOT.jar
```

---

## 5. Endpoints de la API y Pruebas

Una vez iniciado el servicio en `http://localhost:8081`, puede validar su funcionamiento mediante el navegador, cURL o PowerShell:

| Metodo | Endpoint | Descripcion |
| :--- | :--- | :--- |
| GET | `/api/products/status` | Verifica el estado del servicio y el contenedor Spring. |
| GET | `/api/products/discount?price=100&percentage=15` | Calcula el precio final aplicando un porcentaje de descuento. |
| GET | `/v3/api-docs` | Especificacion formal OpenAPI en formato JSON. |
| GET | `/swagger-ui/index.html` | Interfaz interactiva de documentacion Swagger UI. |

### Ejemplos de consumo en consola:

#### Consulta de estado (PowerShell):
```powershell
Invoke-RestMethod -Uri "http://localhost:8081/api/products/status"
```

#### Calculo de descuento (PowerShell):
```powershell
Invoke-RestMethod -Uri "http://localhost:8081/api/products/discount?price=100&percentage=15"
```

#### Consulta mediante cURL:
```bash
curl -X GET "http://localhost:8081/api/products/discount?price=100&percentage=15"
```

---

## 6. Resolucion de Problemas Comunes (Troubleshooting)

### Error 1: Google Guice MISSING_IMPLEMENTATION (ArtifactHandlerManager)
* **Causa:** Ocurre cuando el wrapper o el IDE intenta sincronizar Maven 3.10+ en un entorno cuyo contenedor de Plexus/Guice no reconoce los nuevos enlaces de inyeccion JSR-330 de Maven 3.10.
* **Solucion aplicada:** Se fija la distribucion en `.mvn/wrapper/maven-wrapper.properties` a la version estable `3.9.9`:
  ```properties
  distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.9/apache-maven-3.9.9-bin.zip
  ```

### Error 2: "Fatal error compiling: error: release version 25 not supported"
* **Causa:** La consola esta ejecutando una version de Java inferior a la 25 (por ejemplo, Java 8, 11 o 20) mientras `pom.xml` exige `<java.version>25</java.version>`.
* **Solucion:** Asegurese de configurar `JAVA_HOME` apuntando al directorio de instalacion de JDK 25 como se describe en la seccion 2.

### Error 3: "Web server failed to start. Port 8080 was already in use"
* **Causa:** El puerto 8080 esta ocupado por otro servicio del sistema operativo.
* **Solucion aplicada:** El puerto por defecto se asigno a `8081` en `src/main/resources/application.properties` con soporte de variable de entorno dinamica:
  ```properties
  server.port=${PORT:8081}
  ```
