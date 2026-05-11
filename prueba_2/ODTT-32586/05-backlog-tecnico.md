# 05 — Backlog Tecnico

_MAU: ODTT-32586_

## Backlog técnico

**Total:** 14 tareas atómicas, ordenadas topológicamente. Cada tarea es una unidad de trabajo asignable a un desarrollador o a un agente codificador futuro.

| Tarea | Componente | Tipo | Riesgo | Esfuerzo (h) | Bloqueada por | Objetivo |
|-------|------------|------|--------|--------------|----------------|----------|
| `T-001` | `lib-comp-sdp-balancequery-adapt` | library | medium | 6.0 | — | Actualizar lib-comp-sdp-balancequery-adapt para usar jakarta.* y compilar bajo Java 25. |
| `T-002` | `lib-comp-redis-adapt` | library | medium | 6.0 | — | Actualizar lib-comp-redis-adapt para usar jakarta.* y compilar bajo Java 25. |
| `T-003` | `lib-comp-netcracker-rdb` | library | medium | 6.0 | — | Actualizar lib-comp-netcracker-rdb para usar jakarta.* y compilar bajo Java 25. |
| `T-004` | `APL-MSLibNetcrackerCbm` | library | medium | 6.0 | — | Actualizar APL-MSLibNetcrackerCbm para usar jakarta.* y compilar bajo Java 25. |
| `T-005` | `APL-MSCompSdpBalancequeryRest` | microservice | medium | 12.0 | `T-001`, `T-002`, `T-003`, `T-004` | Modernizar APL-MSCompSdpBalancequeryRest a Spring Boot 4 + Jakarta + Java 25. |
| `T-006` | `APL-MSHeaderValidationRest` | microservice | medium | 12.0 | `T-001`, `T-002`, `T-003`, `T-004` | Modernizar APL-MSHeaderValidationRest a Spring Boot 4 + Jakarta + Java 25. |
| `T-007` | `APL-MSCompNetcrackerRDB` | microservice | medium | 12.0 | `T-001`, `T-002`, `T-003`, `T-004` | Modernizar APL-MSCompNetcrackerRDB a Spring Boot 4 + Jakarta + Java 25. |
| `T-008` | `APL-MSCustomerRetrievebalancebuckets-OSB` | microservice | medium | 12.0 | `T-001`, `T-002`, `T-003`, `T-004` | Modernizar APL-MSCustomerRetrievebalancebuckets-OSB a Spring Boot 4 + Jakarta + Java 25. |
| `T-009` | `APL-MSUserCustomerTypeQuery-Soap` | microservice | high | 12.0 | `T-001`, `T-002`, `T-003`, `T-004` | Modernizar APL-MSUserCustomerTypeQuery-Soap a Spring Boot 4 + Jakarta + Java 25. |
| `T-010` | `APL-MSUserCustomerTypeQuery-Rest` | microservice | medium | 12.0 | `T-001`, `T-002`, `T-003`, `T-004` | Modernizar APL-MSUserCustomerTypeQuery-Rest a Spring Boot 4 + Jakarta + Java 25. |
| `T-011` | `APL-MSCompRedisRest` | microservice | medium | 12.0 | `T-001`, `T-002`, `T-003`, `T-004` | Modernizar APL-MSCompRedisRest a Spring Boot 4 + Jakarta + Java 25. |
| `T-012` | `ms-customer-retrievebalancebuckets` | microservice | medium | 12.0 | `T-001`, `T-002`, `T-003`, `T-004` | Modernizar ms-customer-retrievebalancebuckets a Spring Boot 4 + Jakarta + Java 25. |
| `T-013` | `ms-customer-retrieveaccountbalanceinfo` | microservice | medium | 12.0 | `T-001`, `T-002`, `T-003`, `T-004` | Modernizar ms-customer-retrieveaccountbalanceinfo a Spring Boot 4 + Jakarta + Java 25. |
| `T-014` | `ms-user-customertypequery-soap` | microservice | high | 12.0 | `T-001`, `T-002`, `T-003`, `T-004` | Modernizar ms-user-customertypequery-soap a Spring Boot 4 + Jakarta + Java 25. |

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

#### `T-003` — Migrar librería lib-comp-netcracker-rdb a Jakarta + Spring Boot 4
- **Componente:** `lib-comp-netcracker-rdb` (library)
- **Riesgo:** medium · **Esfuerzo:** 6.0 h
- **Objetivo:** Actualizar lib-comp-netcracker-rdb para usar jakarta.* y compilar bajo Java 25.
- **Alcance:** Reemplazo javax.* → jakarta.*, bump de Spring Boot a 4.x, actualizar pom.xml y verificar compatibilidad backward.
- **Criterios de aceptación:**
  - mvn clean verify exit 0
  - 0 imports javax.* en src/main
  - Tests existentes en verde
- **Tests requeridos:** unit, contract

#### `T-004` — Migrar librería APL-MSLibNetcrackerCbm a Jakarta + Spring Boot 4
- **Componente:** `APL-MSLibNetcrackerCbm` (library)
- **Riesgo:** medium · **Esfuerzo:** 6.0 h
- **Objetivo:** Actualizar APL-MSLibNetcrackerCbm para usar jakarta.* y compilar bajo Java 25.
- **Alcance:** Reemplazo javax.* → jakarta.*, bump de Spring Boot a 4.x, actualizar pom.xml y verificar compatibilidad backward.
- **Criterios de aceptación:**
  - mvn clean verify exit 0
  - 0 imports javax.* en src/main
  - Tests existentes en verde
- **Tests requeridos:** unit, contract

#### `T-005` — Migrar microservicio APL-MSCompSdpBalancequeryRest (REST)
- **Componente:** `APL-MSCompSdpBalancequeryRest` (microservice)
- **Riesgo:** medium · **Esfuerzo:** 12.0 h
- **Objetivo:** Modernizar APL-MSCompSdpBalancequeryRest a Spring Boot 4 + Jakarta + Java 25.
- **Alcance:** Actualizar pom.xml, application.yml, config SOAP/REST, reemplazar javax.* → jakarta.*, validar contratos.
- **Criterios de aceptación:**
  - mvn clean verify exit 0
  - Endpoints SOAP/REST responden con contrato sin cambios
  - Healthcheck Kubernetes verde
  - Sonar Quality Gate Passed
- **Tests requeridos:** unit, integration, contract, performance

#### `T-006` — Migrar microservicio APL-MSHeaderValidationRest (REST)
- **Componente:** `APL-MSHeaderValidationRest` (microservice)
- **Riesgo:** medium · **Esfuerzo:** 12.0 h
- **Objetivo:** Modernizar APL-MSHeaderValidationRest a Spring Boot 4 + Jakarta + Java 25.
- **Alcance:** Actualizar pom.xml, application.yml, config SOAP/REST, reemplazar javax.* → jakarta.*, validar contratos.
- **Criterios de aceptación:**
  - mvn clean verify exit 0
  - Endpoints SOAP/REST responden con contrato sin cambios
  - Healthcheck Kubernetes verde
  - Sonar Quality Gate Passed
- **Tests requeridos:** unit, integration, contract, performance

#### `T-007` — Migrar microservicio APL-MSCompNetcrackerRDB (UNKNOWN)
- **Componente:** `APL-MSCompNetcrackerRDB` (microservice)
- **Riesgo:** medium · **Esfuerzo:** 12.0 h
- **Objetivo:** Modernizar APL-MSCompNetcrackerRDB a Spring Boot 4 + Jakarta + Java 25.
- **Alcance:** Actualizar pom.xml, application.yml, config SOAP/REST, reemplazar javax.* → jakarta.*, validar contratos.
- **Criterios de aceptación:**
  - mvn clean verify exit 0
  - Endpoints SOAP/REST responden con contrato sin cambios
  - Healthcheck Kubernetes verde
  - Sonar Quality Gate Passed
- **Tests requeridos:** unit, integration, contract, performance

#### `T-008` — Migrar microservicio APL-MSCustomerRetrievebalancebuckets-OSB (UNKNOWN)
- **Componente:** `APL-MSCustomerRetrievebalancebuckets-OSB` (microservice)
- **Riesgo:** medium · **Esfuerzo:** 12.0 h
- **Objetivo:** Modernizar APL-MSCustomerRetrievebalancebuckets-OSB a Spring Boot 4 + Jakarta + Java 25.
- **Alcance:** Actualizar pom.xml, application.yml, config SOAP/REST, reemplazar javax.* → jakarta.*, validar contratos.
- **Criterios de aceptación:**
  - mvn clean verify exit 0
  - Endpoints SOAP/REST responden con contrato sin cambios
  - Healthcheck Kubernetes verde
  - Sonar Quality Gate Passed
- **Tests requeridos:** unit, integration, contract, performance

#### `T-009` — Migrar microservicio APL-MSUserCustomerTypeQuery-Soap (SOAP)
- **Componente:** `APL-MSUserCustomerTypeQuery-Soap` (microservice)
- **Riesgo:** high · **Esfuerzo:** 12.0 h
- **Objetivo:** Modernizar APL-MSUserCustomerTypeQuery-Soap a Spring Boot 4 + Jakarta + Java 25.
- **Alcance:** Actualizar pom.xml, application.yml, config SOAP/REST, reemplazar javax.* → jakarta.*, validar contratos.
- **Criterios de aceptación:**
  - mvn clean verify exit 0
  - Endpoints SOAP/REST responden con contrato sin cambios
  - Healthcheck Kubernetes verde
  - Sonar Quality Gate Passed
- **Tests requeridos:** unit, integration, contract, performance

#### `T-010` — Migrar microservicio APL-MSUserCustomerTypeQuery-Rest (REST)
- **Componente:** `APL-MSUserCustomerTypeQuery-Rest` (microservice)
- **Riesgo:** medium · **Esfuerzo:** 12.0 h
- **Objetivo:** Modernizar APL-MSUserCustomerTypeQuery-Rest a Spring Boot 4 + Jakarta + Java 25.
- **Alcance:** Actualizar pom.xml, application.yml, config SOAP/REST, reemplazar javax.* → jakarta.*, validar contratos.
- **Criterios de aceptación:**
  - mvn clean verify exit 0
  - Endpoints SOAP/REST responden con contrato sin cambios
  - Healthcheck Kubernetes verde
  - Sonar Quality Gate Passed
- **Tests requeridos:** unit, integration, contract, performance

#### `T-011` — Migrar microservicio APL-MSCompRedisRest (REST)
- **Componente:** `APL-MSCompRedisRest` (microservice)
- **Riesgo:** medium · **Esfuerzo:** 12.0 h
- **Objetivo:** Modernizar APL-MSCompRedisRest a Spring Boot 4 + Jakarta + Java 25.
- **Alcance:** Actualizar pom.xml, application.yml, config SOAP/REST, reemplazar javax.* → jakarta.*, validar contratos.
- **Criterios de aceptación:**
  - mvn clean verify exit 0
  - Endpoints SOAP/REST responden con contrato sin cambios
  - Healthcheck Kubernetes verde
  - Sonar Quality Gate Passed
- **Tests requeridos:** unit, integration, contract, performance

#### `T-012` — Migrar microservicio ms-customer-retrievebalancebuckets (HYBRID)
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

#### `T-013` — Migrar microservicio ms-customer-retrieveaccountbalanceinfo (HYBRID)
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

#### `T-014` — Migrar microservicio ms-user-customertypequery-soap (SOAP)
- **Componente:** `ms-user-customertypequery-soap` (microservice)
- **Riesgo:** high · **Esfuerzo:** 12.0 h
- **Objetivo:** Modernizar ms-user-customertypequery-soap a Spring Boot 4 + Jakarta + Java 25.
- **Alcance:** Actualizar pom.xml, application.yml, config SOAP/REST, reemplazar javax.* → jakarta.*, validar contratos.
- **Criterios de aceptación:**
  - mvn clean verify exit 0
  - Endpoints SOAP/REST responden con contrato sin cambios
  - Healthcheck Kubernetes verde
  - Sonar Quality Gate Passed
- **Tests requeridos:** unit, integration, contract, performance
