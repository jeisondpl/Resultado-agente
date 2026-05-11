# 05 — Backlog Tecnico

_MAU: ODTT-32586_

## Backlog técnico

**Total:** 11 tareas atómicas, ordenadas topológicamente. Cada tarea es una unidad de trabajo asignable a un desarrollador o a un agente codificador futuro.

| Tarea | Componente | Tipo | Riesgo | Esfuerzo (h) | Bloqueada por | Objetivo |
|-------|------------|------|--------|--------------|----------------|----------|
| `T-001` | `lib-comp-sdp-balancequery-adapt` | library | medium | 6.0 | — | Actualizar lib-comp-sdp-balancequery-adapt para usar jakarta.* y compilar bajo Java 25. |
| `T-002` | `lib-comp-redis-adapt` | library | medium | 6.0 | — | Actualizar lib-comp-redis-adapt para usar jakarta.* y compilar bajo Java 25. |
| `T-003` | `lib-comp-netcracker-cbm-adapt` | library | medium | 6.0 | — | Actualizar lib-comp-netcracker-cbm-adapt para usar jakarta.* y compilar bajo Java 25. |
| `T-004` | `lib-header-validation-adapt` | library | medium | 6.0 | — | Actualizar lib-header-validation-adapt para usar jakarta.* y compilar bajo Java 25. |
| `T-005` | `ms-customer-retrievebalancebuckets` | microservice | medium | 12.0 | `T-001`, `T-002`, `T-003`, `T-004` | Modernizar ms-customer-retrievebalancebuckets a Spring Boot 4 + Jakarta + Java 25. |
| `T-006` | `ms-customer-retrieveaccountbalanceinfo` | microservice | medium | 12.0 | `T-001`, `T-002`, `T-003`, `T-004` | Modernizar ms-customer-retrieveaccountbalanceinfo a Spring Boot 4 + Jakarta + Java 25. |
| `T-007` | `ms-user-customertypequery-soap` | microservice | medium | 12.0 | `T-001`, `T-002`, `T-003`, `T-004` | Modernizar ms-user-customertypequery-soap a Spring Boot 4 + Jakarta + Java 25. |
| `T-008` | `ms-comp-netcrackerrdb` | microservice | medium | 12.0 | `T-001`, `T-002`, `T-003`, `T-004` | Modernizar ms-comp-netcrackerrdb a Spring Boot 4 + Jakarta + Java 25. |
| `T-009` | `ms-comp-sdp-balancequery-rest` | microservice | medium | 12.0 | `T-001`, `T-002`, `T-003`, `T-004` | Modernizar ms-comp-sdp-balancequery-rest a Spring Boot 4 + Jakarta + Java 25. |
| `T-010` | `ms-comp-redis-rest` | microservice | medium | 12.0 | `T-001`, `T-002`, `T-003`, `T-004` | Modernizar ms-comp-redis-rest a Spring Boot 4 + Jakarta + Java 25. |
| `T-011` | `ms-header-validation-rest` | microservice | medium | 12.0 | `T-001`, `T-002`, `T-003`, `T-004` | Modernizar ms-header-validation-rest a Spring Boot 4 + Jakarta + Java 25. |

### Detalle por tarea

#### `T-001` — Migrar librería lib-comp-sdp-balancequery-adapt a Jakarta + Spring Boot 4
- **Componente:** `lib-comp-sdp-balancequery-adapt` (library)
- **Riesgo:** medium · **Esfuerzo:** 6.0 h
- **Objetivo:** Actualizar lib-comp-sdp-balancequery-adapt para usar jakarta.* y compilar bajo Java 25.
- **Alcance:** Reemplazo javax.* → jakarta.*, bump de Spring Boot a 4.x, actualizar pom.xml y verificar compatibilidad backward.
- **Criterios de aceptación:**
  - mvn clean verify exit 0
  - 0 imports javax.* en src/main
  - Tests existentes en verde
- **Tests requeridos:** unit, contract

#### `T-002` — Migrar librería lib-comp-redis-adapt a Jakarta + Spring Boot 4
- **Componente:** `lib-comp-redis-adapt` (library)
- **Riesgo:** medium · **Esfuerzo:** 6.0 h
- **Objetivo:** Actualizar lib-comp-redis-adapt para usar jakarta.* y compilar bajo Java 25.
- **Alcance:** Reemplazo javax.* → jakarta.*, bump de Spring Boot a 4.x, actualizar pom.xml y verificar compatibilidad backward.
- **Criterios de aceptación:**
  - mvn clean verify exit 0
  - 0 imports javax.* en src/main
  - Tests existentes en verde
- **Tests requeridos:** unit, contract

#### `T-003` — Migrar librería lib-comp-netcracker-cbm-adapt a Jakarta + Spring Boot 4
- **Componente:** `lib-comp-netcracker-cbm-adapt` (library)
- **Riesgo:** medium · **Esfuerzo:** 6.0 h
- **Objetivo:** Actualizar lib-comp-netcracker-cbm-adapt para usar jakarta.* y compilar bajo Java 25.
- **Alcance:** Reemplazo javax.* → jakarta.*, bump de Spring Boot a 4.x, actualizar pom.xml y verificar compatibilidad backward.
- **Criterios de aceptación:**
  - mvn clean verify exit 0
  - 0 imports javax.* en src/main
  - Tests existentes en verde
- **Tests requeridos:** unit, contract

#### `T-004` — Migrar librería lib-header-validation-adapt a Jakarta + Spring Boot 4
- **Componente:** `lib-header-validation-adapt` (library)
- **Riesgo:** medium · **Esfuerzo:** 6.0 h
- **Objetivo:** Actualizar lib-header-validation-adapt para usar jakarta.* y compilar bajo Java 25.
- **Alcance:** Reemplazo javax.* → jakarta.*, bump de Spring Boot a 4.x, actualizar pom.xml y verificar compatibilidad backward.
- **Criterios de aceptación:**
  - mvn clean verify exit 0
  - 0 imports javax.* en src/main
  - Tests existentes en verde
- **Tests requeridos:** unit, contract

#### `T-005` — Migrar microservicio ms-customer-retrievebalancebuckets (HYBRID)
- **Componente:** `ms-customer-retrievebalancebuckets` (microservice)
- **Riesgo:** medium · **Esfuerzo:** 12.0 h
- **Objetivo:** Modernizar ms-customer-retrievebalancebuckets a Spring Boot 4 + Jakarta + Java 25.
- **Alcance:** Actualizar pom.xml, application.yml, config SOAP/REST, reemplazar javax.* → jakarta.*, validar contratos.
- **Criterios de aceptación:**
  - mvn clean verify exit 0
  - Endpoints SOAP/REST responden con contrato sin cambios
  - Healthcheck Kubernetes verde
  - Sonar Quality Gate Passed
- **Tests requeridos:** unit, integration, contract, performance

#### `T-006` — Migrar microservicio ms-customer-retrieveaccountbalanceinfo (HYBRID)
- **Componente:** `ms-customer-retrieveaccountbalanceinfo` (microservice)
- **Riesgo:** medium · **Esfuerzo:** 12.0 h
- **Objetivo:** Modernizar ms-customer-retrieveaccountbalanceinfo a Spring Boot 4 + Jakarta + Java 25.
- **Alcance:** Actualizar pom.xml, application.yml, config SOAP/REST, reemplazar javax.* → jakarta.*, validar contratos.
- **Criterios de aceptación:**
  - mvn clean verify exit 0
  - Endpoints SOAP/REST responden con contrato sin cambios
  - Healthcheck Kubernetes verde
  - Sonar Quality Gate Passed
- **Tests requeridos:** unit, integration, contract, performance

#### `T-007` — Migrar microservicio ms-user-customertypequery-soap (HYBRID)
- **Componente:** `ms-user-customertypequery-soap` (microservice)
- **Riesgo:** medium · **Esfuerzo:** 12.0 h
- **Objetivo:** Modernizar ms-user-customertypequery-soap a Spring Boot 4 + Jakarta + Java 25.
- **Alcance:** Actualizar pom.xml, application.yml, config SOAP/REST, reemplazar javax.* → jakarta.*, validar contratos.
- **Criterios de aceptación:**
  - mvn clean verify exit 0
  - Endpoints SOAP/REST responden con contrato sin cambios
  - Healthcheck Kubernetes verde
  - Sonar Quality Gate Passed
- **Tests requeridos:** unit, integration, contract, performance

#### `T-008` — Migrar microservicio ms-comp-netcrackerrdb (REST)
- **Componente:** `ms-comp-netcrackerrdb` (microservice)
- **Riesgo:** medium · **Esfuerzo:** 12.0 h
- **Objetivo:** Modernizar ms-comp-netcrackerrdb a Spring Boot 4 + Jakarta + Java 25.
- **Alcance:** Actualizar pom.xml, application.yml, config SOAP/REST, reemplazar javax.* → jakarta.*, validar contratos.
- **Criterios de aceptación:**
  - mvn clean verify exit 0
  - Endpoints SOAP/REST responden con contrato sin cambios
  - Healthcheck Kubernetes verde
  - Sonar Quality Gate Passed
- **Tests requeridos:** unit, integration, contract, performance

#### `T-009` — Migrar microservicio ms-comp-sdp-balancequery-rest (REST)
- **Componente:** `ms-comp-sdp-balancequery-rest` (microservice)
- **Riesgo:** medium · **Esfuerzo:** 12.0 h
- **Objetivo:** Modernizar ms-comp-sdp-balancequery-rest a Spring Boot 4 + Jakarta + Java 25.
- **Alcance:** Actualizar pom.xml, application.yml, config SOAP/REST, reemplazar javax.* → jakarta.*, validar contratos.
- **Criterios de aceptación:**
  - mvn clean verify exit 0
  - Endpoints SOAP/REST responden con contrato sin cambios
  - Healthcheck Kubernetes verde
  - Sonar Quality Gate Passed
- **Tests requeridos:** unit, integration, contract, performance

#### `T-010` — Migrar microservicio ms-comp-redis-rest (REST)
- **Componente:** `ms-comp-redis-rest` (microservice)
- **Riesgo:** medium · **Esfuerzo:** 12.0 h
- **Objetivo:** Modernizar ms-comp-redis-rest a Spring Boot 4 + Jakarta + Java 25.
- **Alcance:** Actualizar pom.xml, application.yml, config SOAP/REST, reemplazar javax.* → jakarta.*, validar contratos.
- **Criterios de aceptación:**
  - mvn clean verify exit 0
  - Endpoints SOAP/REST responden con contrato sin cambios
  - Healthcheck Kubernetes verde
  - Sonar Quality Gate Passed
- **Tests requeridos:** unit, integration, contract, performance

#### `T-011` — Migrar microservicio ms-header-validation-rest (REST)
- **Componente:** `ms-header-validation-rest` (microservice)
- **Riesgo:** medium · **Esfuerzo:** 12.0 h
- **Objetivo:** Modernizar ms-header-validation-rest a Spring Boot 4 + Jakarta + Java 25.
- **Alcance:** Actualizar pom.xml, application.yml, config SOAP/REST, reemplazar javax.* → jakarta.*, validar contratos.
- **Criterios de aceptación:**
  - mvn clean verify exit 0
  - Endpoints SOAP/REST responden con contrato sin cambios
  - Healthcheck Kubernetes verde
  - Sonar Quality Gate Passed
- **Tests requeridos:** unit, integration, contract, performance
