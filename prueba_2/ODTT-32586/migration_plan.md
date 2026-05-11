# Plan de Migración — ODTT-32586

## Problem statement
El objetivo principal de la migración es actualizar los microservicios existentes que operan en un bus G-MSOSB a un entorno más moderno y eficiente utilizando Java OPENJDK 25 LTS, Spring Boot 4 y Redis 8. Esta actualización busca mejorar la eficiencia, modularidad y escalabilidad del sistema, permitiendo una mejor integración con servicios REST y optimizando el flujo de datos. El alcance de la migración incluye la actualización de microservicios y librerías comunes al nuevo estándar, asegurando que funcionen correctamente en escenarios reales. Se utilizarán arquetipos corporativos específicos para la creación de microservicios, como el arquetipoMicroservicios para servicios REST, arquetipoMicroservicios-soap-global para servicios SOAP, y arquetipoMicroservcios-soap-fullstack-micro para servicios híbridos que combinan SOAP y REST. Las restricciones incluyen la disponibilidad de los ambientes de desarrollo y pruebas, así como el acceso a bases de datos Oracle RDB y CBM. Además, se requiere que los microservicios migren sus configuraciones y dependencias para alinearse con las nuevas versiones de software y librerías, asegurando la compatibilidad y el correcto funcionamiento en el nuevo entorno.

## Arquitectura objetivo
Arquitectura objetivo: 10 microservicio(s) (SOAP=2, REST=4) sobre Spring Boot 4 + Java 25 + Jakarta EE 10. 6 librería(s) compartidas migradas a jakarta.*. Persistencia Oracle RDB y caché Redis 8 con clientes actualizados.

## Stack objetivo
- **java**: 25
- **spring_boot**: 4.x
- **jakarta**: True
- **redis**: 8
- **oracle**: RDB

## Restricciones globales
- No inventar dependencias ni endpoints fuera del MAU
- Mantener compatibilidad SOAP backward (contratos WSDL/XSD)
- Migrar javax.* → jakarta.* en todos los módulos
- Conservar comportamiento funcional 1:1 con la versión actual
- Usar los arquetipos corporativos `ec.otecel:arquetipoMicroservicios[(-soap-global)|(-soap-fullstack)] 2.0.0` como base; no reescribir scaffolding propio.
- Componentes `lib-header-validation` / `ms-header-validation` son PROVISTOS por el arquetipo: NO crear, NO migrar.
- Componentes provistos por el arquetipo (descartados de tasks): lib-header-validate, lib-header-validation

## Tareas (14)

| ID | Título | Componente | Tipo | Riesgo | Esfuerzo (h) |
|----|--------|------------|------|--------|--------------|
| T-001 | Migrar librería lib-comp-sdp-balancequery-adapt a Jakarta + Spring Boot 4 | lib-comp-sdp-balancequery-adapt | library | medium | 6.0 |
| T-002 | Migrar librería lib-comp-redis-adapt a Jakarta + Spring Boot 4 | lib-comp-redis-adapt | library | medium | 6.0 |
| T-003 | Migrar librería lib-comp-netcracker-rdb a Jakarta + Spring Boot 4 | lib-comp-netcracker-rdb | library | medium | 6.0 |
| T-004 | Migrar librería APL-MSLibNetcrackerCbm a Jakarta + Spring Boot 4 | APL-MSLibNetcrackerCbm | library | medium | 6.0 |
| T-005 | Migrar microservicio APL-MSCompSdpBalancequeryRest (REST) | APL-MSCompSdpBalancequeryRest | microservice | medium | 12.0 |
| T-006 | Migrar microservicio APL-MSHeaderValidationRest (REST) | APL-MSHeaderValidationRest | microservice | medium | 12.0 |
| T-007 | Migrar microservicio APL-MSCompNetcrackerRDB (UNKNOWN) | APL-MSCompNetcrackerRDB | microservice | medium | 12.0 |
| T-008 | Migrar microservicio APL-MSCustomerRetrievebalancebuckets-OSB (UNKNOWN) | APL-MSCustomerRetrievebalancebuckets-OSB | microservice | medium | 12.0 |
| T-009 | Migrar microservicio APL-MSUserCustomerTypeQuery-Soap (SOAP) | APL-MSUserCustomerTypeQuery-Soap | microservice | high | 12.0 |
| T-010 | Migrar microservicio APL-MSUserCustomerTypeQuery-Rest (REST) | APL-MSUserCustomerTypeQuery-Rest | microservice | medium | 12.0 |
| T-011 | Migrar microservicio APL-MSCompRedisRest (REST) | APL-MSCompRedisRest | microservice | medium | 12.0 |
| T-012 | Migrar microservicio ms-customer-retrievebalancebuckets (HYBRID) | ms-customer-retrievebalancebuckets | microservice | medium | 12.0 |
| T-013 | Migrar microservicio ms-customer-retrieveaccountbalanceinfo (HYBRID) | ms-customer-retrieveaccountbalanceinfo | microservice | medium | 12.0 |
| T-014 | Migrar microservicio ms-user-customertypequery-soap (SOAP) | ms-user-customertypequery-soap | microservice | high | 12.0 |

## Orden de ejecución
T-001 → T-002 → T-003 → T-004 → T-005 → T-006 → T-007 → T-008 → T-009 → T-010 → T-011 → T-012 → T-013 → T-014

## Riesgos (3)
- **[R-001] Compatibilidad SOAP tras Spring Boot 4 + Jakarta** (high): 2 microservicio(s) SOAP requieren reemplazo javax.* → jakarta.* sin romper contratos WSDL.
- **[R-004] Orden topológico de librerías** (medium): 4 librería(s); deben migrarse libs base antes que consumidores.
- **[R-005] javax → jakarta package rename** (high): Reemplazo de imports javax.* → jakarta.* en Servlet, JAX-WS, JAXB, validation, persistence.

## Quality gates
- **Build**: mvn clean verify exit 0
- **Sonar**: Quality Gate Passed
- **Coverage**: >= 80% líneas
- **Contract tests**: 100% verde sobre WSDL/XSD
- **Imports**: 0 imports javax.* en build final

## Estrategia de rollback
- Incremental con feature flags + blue/green por microservicio

## Reserva — agente desarrollador (futuro)
- status: `NOT_IMPLEMENTED`
- contract: `draft-0.1`
- notas: El agente desarrollador no existe todavía. Cuando se implemente, consumirá MigrationPlan.tasks en MigrationPlan.execution_order.
