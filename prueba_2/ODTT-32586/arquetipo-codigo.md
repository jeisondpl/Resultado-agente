# Arquetipo de código objetivo

_MAU: ODTT-32586_

Este documento es la **guía de generación de código** para la migración. Describe los arquetipos corporativos a usar, cómo instalarlos, cómo generar cada microservicio y qué componentes NO deben implementarse porque el arquetipo ya los inyecta. Se basa en el diagrama `Migración Jdk25 4.png` y en las plantillas reales del repo `APL-MSGeneral-Arquetipos/`.

## Principios de migración (legacy jdk 8 → jdk 25)

Por cada microservicio existente se **reutiliza la lógica de negocio** y se reemplaza el scaffolding:

| Componente legacy | Componente nuevo (arquetipo) | Cambio |
|-------------------|------------------------------|--------|
| `Service` | `service/I<Name>Service.java` + `service/<Name>Service[Imp].java` | **Reutilizar** lógica adecuadamente |
| `Controller` | `controller/<Name>Controller.java` (+ SoapEndpoint si aplica) | **Reutilizar**; reemplazar `javax → jakarta` |
| `DTO → MCI` | `dto/exposition/` + `dto/integration/` | **División en dos capas** (ver §DTOs) |
| `Config` | `conf/DBConfig`, `RedisConf`, `SoapConfig` | Versiones nuevas; BD puede cambiar |
| `Mapper` | `mapper/` | Reutilizar; mapea `exposition ↔ integration` |
| `Adaptadores` | `adapter/` | Reutilizar; clientes actualizados (Oracle, Redis 8, etc.) |

## Arquetipos corporativos disponibles

| # | artifactId (groupId=`ec.otecel`) | version | Cuándo usarlo |
|---|-----------------------------------|---------|----------------|
| **1** | `arquetipoMicroservicios` | 2.0.0 | Microservicios que exponen ÚNICAMENTE REST. |
| **2** | `arquetipoMicroservicios-soap-global` | 2.0.0 | Microservicios que exponen ÚNICAMENTE SOAP. |
| **3** | `arquetipoMicroservcios-soap-fullstack-micro` | 2.0.0 | Microservicios que exponen SOAP y REST en el MISMO artefacto (caso HYBRID, más común del MAU). |

**Regla de selección automática:** `HYBRID → 3`, `SOAP → 2`, `REST → 1`, `UNKNOWN → 3` (por defecto, no rompe nada).

## Paso 0 — Descargar e instalar los arquetipos en local

Antes de generar ningún microservicio, instalar los 3 arquetipos en el repo Maven local.

```powershell
# 1. Clonar (o pull si ya existe)
cd C:\INDRA\APL-Agente-ms
# git clone <repo-url> APL-MSGeneral-Arquetipos   # si aún no existe

# 2. Instalar los 3 arquetipos en ~/.m2/repository
cd C:\INDRA\APL-Agente-ms\APL-MSGeneral-Arquetipos\ArquetipoBasicoRest\arquetipoMicroservicios
mvn clean install
cd C:\INDRA\APL-Agente-ms\APL-MSGeneral-Arquetipos\ArquetipoBasicoSoapGlobal\arquetipoMicroservicios-soap-global
mvn clean install
cd C:\INDRA\APL-Agente-ms\APL-MSGeneral-Arquetipos\ArquetipoBasicoSoapFullStack\arquetipoMicroservicios-soap-fullstack
mvn clean install
```

Resultado esperado en `~/.m2/repository/ec/otecel/`:

- `arquetipoMicroservicios/2.0.0/` ✅
- `arquetipoMicroservicios-soap-global/2.0.0/` ✅
- `arquetipoMicroservcios-soap-fullstack-micro/2.0.0/` ✅
## Paso 1 — Generar el proyecto Maven a partir del arquetipo

Comando por microservicio (sustituir los `<...>`):

```powershell
mvn archetype:generate `
  -DarchetypeGroupId=ec.otecel `
  -DarchetypeArtifactId=<arquetipoMicroservicios | arquetipoMicroservicios-soap-global | arquetipoMicroservcios-soap-fullstack-micro> `
  -DarchetypeVersion=2.0.0 `
  -DgroupId=ec.otecel `
  -DartifactId=<ms-nombre-del-microservicio> `
  -Dversion=1.0.0-SNAPSHOT `
  -Dpackage=ec.otecel `
  -DserviceName=<NombreCamelCase> `
  -DserviceNameFolder=<paquete-en-minusculas> `
  -Dmethod1Name=<getX|retrieveY> `
  -DclassName=<Default | NombreClase> `
  -Dtype=NODELETE `
  -DinteractiveMode=false
```

Parámetros obligatorios (de `archetype-metadata.xml`): `serviceName`, `serviceNameFolder`, `method1Name`, `className`, `groupId`, `version`, `package`, `type`. El resto Maven los pide o usa defaults.

## Microservicios a generar

| Microservicio | Tipo | Arquetipo asignado | artifactId del arquetipo | Comando |
|---------------|------|---------------------|---------------------------|---------|
| `APL-MSCompSdpBalancequeryRest` | REST | **1** — `arquetipoMicroservicios` | `arquetipoMicroservicios` | `-DarchetypeArtifactId=arquetipoMicroservicios` |
| `APL-MSHeaderValidationRest` | REST | **1** — `arquetipoMicroservicios` | `arquetipoMicroservicios` | `-DarchetypeArtifactId=arquetipoMicroservicios` |
| `APL-MSCompNetcrackerRDB` | UNKNOWN | **3** — `arquetipoMicroservicios-soap-fullstack` | `arquetipoMicroservcios-soap-fullstack-micro` | `-DarchetypeArtifactId=arquetipoMicroservcios-soap-fullstack-micro` |
| `APL-MSCustomerRetrievebalancebuckets-OSB` | UNKNOWN | **3** — `arquetipoMicroservicios-soap-fullstack` | `arquetipoMicroservcios-soap-fullstack-micro` | `-DarchetypeArtifactId=arquetipoMicroservcios-soap-fullstack-micro` |
| `APL-MSUserCustomerTypeQuery-Soap` | SOAP | **2** — `arquetipoMicroservicios-soap-global` | `arquetipoMicroservicios-soap-global` | `-DarchetypeArtifactId=arquetipoMicroservicios-soap-global` |
| `APL-MSUserCustomerTypeQuery-Rest` | REST | **1** — `arquetipoMicroservicios` | `arquetipoMicroservicios` | `-DarchetypeArtifactId=arquetipoMicroservicios` |
| `APL-MSCompRedisRest` | REST | **1** — `arquetipoMicroservicios` | `arquetipoMicroservicios` | `-DarchetypeArtifactId=arquetipoMicroservicios` |
| `ms-customer-retrievebalancebuckets` | HYBRID | **3** — `arquetipoMicroservicios-soap-fullstack` | `arquetipoMicroservcios-soap-fullstack-micro` | `-DarchetypeArtifactId=arquetipoMicroservcios-soap-fullstack-micro` |
| `ms-customer-retrieveaccountbalanceinfo` | HYBRID | **3** — `arquetipoMicroservicios-soap-fullstack` | `arquetipoMicroservcios-soap-fullstack-micro` | `-DarchetypeArtifactId=arquetipoMicroservcios-soap-fullstack-micro` |
| `ms-user-customertypequery-soap` | SOAP | **2** — `arquetipoMicroservicios-soap-global` | `arquetipoMicroservicios-soap-global` | `-DarchetypeArtifactId=arquetipoMicroservicios-soap-global` |
## Librerías a migrar

| Librería | Clasificación | Patrón |
|----------|----------------|--------|
| `lib-comp-sdp-balancequery-adapt` | adapter | adaptador de integración (Redis, Oracle, Netcracker, SDP) |
| `lib-comp-redis-adapt` | adapter | adaptador de integración (Redis, Oracle, Netcracker, SDP) |
| `lib-comp-netcracker-rdb` | adapter | adaptador de integración (Redis, Oracle, Netcracker, SDP) |
| `APL-MSLibNetcrackerCbm` | other | librería general |

Estructura objetivo de cada librería:

```text
lib-<nombre>/
├── pom.xml                       # bump Spring Boot 4 + Java 25 + jakarta.*
└── src/main/java/ec/otecel/.../
    ├── api/                      # interfaces públicas
    ├── impl/                     # implementación
    └── config/                   # @AutoConfiguration / spring.factories
```

## ⛔ Componentes provistos por el arquetipo (NO implementar)

Los siguientes componentes aparecen en el MAU pero **NO se generan ni migran**: el arquetipo ya inyecta esa responsabilidad mediante `conf/ServiceInterceptor`, `dto/exposition/.../HeaderInfo`, `conf/DetailSoapFaultDefinitionExceptionResolver` y el paquete `util/` (logging, error mapping, validación XML).

| Componente del MAU | Reemplazado por (arquetipo) |
|--------------------|-----------------------------|
| `lib-header-validation` _(library)_ | `conf/ServiceInterceptor` + `HeaderInfo` (SOAP) / interceptores REST |
| `lib-header-validate` _(library)_ | `conf/ServiceInterceptor` + `HeaderInfo` (SOAP) / interceptores REST |

> Regla dura: la **planificación descarta automáticamente estas tareas**. Si en una iteración futura el equipo confirma que necesitan implementación propia, añadirlas manualmente al `MigrationPlan.tasks` y eliminar del filtro `PROVIDED_BY_ARCHETYPE`.
## Estructura real del proyecto generado por el arquetipo

Tomado directamente de `archetype-resources/` (sustituye `__serviceNameFolder__` por tu paquete y `__artifactId__` por el nombre del microservicio):

```text
ms-<nombre>/
├── pom.xml
├── Dockerfile
├── .dockerignore
├── build-docker.ps1
├── k8s-<artifactId>.yaml
├── traefik-ingress-route-service-<artifactId>.yaml
├── sonar-project.properties
├── <artifactId>soapui-project.xml
└── src/main/
    ├── java/ec/otecel/<serviceNameFolder>/
    │   ├── Application.java                              # @SpringBootApplication
    │   ├── adapter/PostgressDBAdapter.java                # renombrar/sustituir por Oracle
    │   ├── conf/
    │   │   ├── DBConfig.java
    │   │   ├── RedisConf.java
    │   │   ├── SoapConfig.java                           # arquetipos 2 y 3
    │   │   ├── DetailSoapFaultDefinitionExceptionResolver.java   # arquetipos 2 y 3
    │   │   └── ServiceInterceptor.java                   # arquetipos 2 y 3 — VALIDA HEADERS
    │   ├── constants/MsConstants.java
    │   ├── controller/<ServiceName>Controller.java
    │   ├── dto/
    │   │   ├── exposition/                               # contrato hacia el cliente
    │   │   │   ├── <Method1>RequestDTO.java               # arquetipo 1
    │   │   │   ├── <Method1>ResponseDTO.java              # arquetipo 1
    │   │   │   ├── commonbusinessentities/.../HeaderInfo.java  # arquetipos 2 y 3 (TMF)
    │   │   │   └── ... (jerarquía TMF en SOAP/fullstack)
    │   │   └── integration/                              # contrato hacia adapters
    │   │       ├── ParameterRequestDTO.java
    │   │       ├── ParameterResponseDTO.java
    │   │       └── ParameterDBDTO.java
    │   ├── exception/GlobalExceptionHandler.java         # SOLO arquetipo 1 (REST)
    │   ├── mapper/
    │   │   ├── GetParameterDBRequestDTOMapper.java
    │   │   ├── GetParameterResponseExtractorDTOMapper.java
    │   │   └── GetParametersResponseDTOMapper.java
    │   ├── service/
    │   │   ├── I<ServiceName>Service.java
    │   │   └── <ServiceName>Service.java                  # o ServiceImp en arquetipo 3
    │   └── util/                                          # ⛔ NO TOCAR — viene del arquetipo
    │       ├── BasicOperationAdapter.java
    │       ├── BasicOperationController.java              # arquetipo 1
    │       ├── BasicOperationSoapEndpoint.java            # arquetipos 2 y 3
    │       ├── BasicOperationSoapService.java             # arquetipos 2 y 3
    │       ├── BasicOperationService.java                 # arquetipo 1
    │       ├── CommonUtil.java
    │       ├── ErrorMappingProperties[Impl].java
    │       ├── ErrorTOpenApiProperties[Impl].java
    │       ├── LoggingUtilities.java
    │       ├── ServiceErrorMapping.java
    │       └── XmlValidationUtil.java                     # arquetipos 2 y 3
    └── resources/
        ├── application.properties
        ├── ErrorMapping.properties
        ├── TOpenApiErrors.properties
        ├── log4j2.xml
        └── wsdl/                                         # arquetipos 2 y 3
            ├── <name>.wsdl
            └── *.xsd
```

### Diferencias entre los tres arquetipos

| Archivo / paquete | REST (1) | SOAP-global (2) | Fullstack (3) |
|--------------------|:--------:|:----------------:|:--------------:|
| `conf/SoapConfig` | — | ✅ | ✅ |
| `conf/DetailSoapFaultDefinitionExceptionResolver` | — | ✅ | ✅ |
| `conf/ServiceInterceptor` (header validation) | — | ✅ | ✅ |
| `exception/GlobalExceptionHandler` | ✅ | — | — |
| `dto/exposition/` jerárquico TMF | — | parcial | ✅ |
| `dto/integration/` separado | ✅ | — _(plano en `dto/`)_ | ✅ |
| `util/XmlValidationUtil` | — | ✅ | ✅ |
| `util/BasicOperationController` / `*Service` | ✅ | — | — |
| `util/BasicOperationSoapEndpoint` / `*SoapService` | — | ✅ | ✅ |
| `wsdl/` + `*.xsd` | — | ✅ | ✅ |
| Service Impl naming | `<Name>Service` | `<Name>Service` | `<Name>ServiceImp` |

## Regla de los DTOs (división obligatoria)

- **`dto/exposition/`** — contratos hacia el cliente que invoca el MS (REST request/response, SOAP request/response generados desde WSDL/XSD).
- **`dto/integration/`** — contratos hacia los sistemas externos consumidos por los adapters (Oracle, Redis, SDP, Netcracker, otros MS).
- El `mapper/` traduce **siempre** entre ambos; jamás se expone un DTO de integration al cliente.
- En arquetipo 2 (SOAP-global) los DTOs de integration están planos en `dto/` (no en subcarpeta); respetar esa convención al generar.
- Validación con `jakarta.validation.constraints.*`.

## Propiedades obligatorias en `application.properties`

El arquetipo trae un `application.properties` base; **estas claves son obligatorias** y deben rellenarse al generar cada microservicio:

```properties
spring.application.name=ms-<nombre>
server.port=${SERVER_PORT:8080}

# Oracle RDB (NO Postgres del default del arquetipo)
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASS}
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

# Redis 8
spring.data.redis.host=${REDIS_HOST}
spring.data.redis.port=${REDIS_PORT:6379}
spring.data.redis.timeout=2s

# Observabilidad
management.endpoints.web.exposure.include=health,info,prometheus,metrics
management.endpoint.health.probes.enabled=true

# Spring Boot 4 — Virtual Threads
spring.threads.virtual.enabled=true

# SOAP (arquetipos 2 y 3)
soap.wsdl-location=classpath:wsdl/<Name>.wsdl
soap.endpoint-path=/ws/<name>
```

## Buenas prácticas Java 25

- **Records** para DTOs de integration y value objects.
- **Sealed classes** para jerarquías cerradas (respuestas, eventos).
- **Pattern matching** en `switch`.
- **Virtual threads** para I/O bound (`spring.threads.virtual.enabled=true`).
- **Text blocks** para JSON/SQL embebido.
- **`var`** sólo para variables locales obvias.
- Cero `javax.*` en `src/main`.
- No tocar el paquete `util/` del arquetipo (logging, error mapping, validación XML).

## Checklist de aceptación del código generado

- [ ] Los 3 arquetipos están instalados en `~/.m2/repository/ec/otecel/`.
- [ ] Cada microservicio se generó con `mvn archetype:generate` usando el arquetipo asignado.
- [ ] `mvn clean verify` en verde por microservicio.
- [ ] 0 imports `javax.*` en `src/main`.
- [ ] Paquetes presentes: `controller/`, `service/`, `adapter/`, `mapper/`, `dto/exposition/`, `dto/integration/` (o `dto/` plano si arquetipo 2), `conf/`, `constants/`, `util/`.
- [ ] DTOs de integration son `record`.
- [ ] `application.properties` contiene todas las claves obligatorias y apunta a Oracle (no Postgres).
- [ ] Arquetipos 2 y 3: `wsdl/<Name>.wsdl` presente y endpoint responde SOAP.
- [ ] Arquetipo 3: el MISMO artefacto responde SOAP **y** REST (no se duplica el MS).
- [ ] NO existe paquete propio de `header-validation` (lo cubre `conf/ServiceInterceptor`).
- [ ] `util/` del arquetipo intacto (no editado).
- [ ] Tests unitarios cubren `service/` y `mapper/`; tests de contrato (WSDL/XSD) en verde.
- [ ] `Dockerfile`, `k8s-<artifactId>.yaml` y `traefik-ingress-route-service-<artifactId>.yaml` actualizados con valores reales del MS.
