# Plan de Migración — ODTT-32586

## Problem statement
El objetivo de la migración es actualizar los microservicios existentes de un sistema basado en un bus a una arquitectura más moderna utilizando Java OPENJDK 25 LTS, Spring Boot 4 y Redis 8. Este proceso implica la migración de librerías comunes y microservicios a nuevos arquetipos, asegurando que todos los componentes funcionen correctamente en un entorno de pre-producción. Las restricciones incluyen la disponibilidad de los ambientes de microservicios y bases de datos, así como la necesidad de datos de prueba adecuados. El stack tecnológico se centra en Java, Spring Boot y Redis, con un enfoque en la modularidad y la eficiencia en la interacción entre servicios y bases de datos.

## Arquitectura objetivo
Arquitectura objetivo: 7 microservicio(s) (SOAP=0, REST=4) sobre Spring Boot 4 + Java 25 + Jakarta EE 10. 4 librería(s) compartidas migradas a jakarta.*. Persistencia Oracle RDB y caché Redis 8 con clientes actualizados.

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

## Tareas (11)

| ID | Título | Componente | Tipo | Riesgo | Esfuerzo (h) |
|----|--------|------------|------|--------|--------------|
| T-001 | Migrar librería lib-comp-sdp-balancequery-adapt a Jakarta + Spring Boot 4 | lib-comp-sdp-balancequery-adapt | library | medium | 6.0 |
| T-002 | Migrar librería lib-comp-redis-adapt a Jakarta + Spring Boot 4 | lib-comp-redis-adapt | library | medium | 6.0 |
| T-003 | Migrar librería lib-comp-netcracker-cbm-adapt a Jakarta + Spring Boot 4 | lib-comp-netcracker-cbm-adapt | library | medium | 6.0 |
| T-004 | Migrar librería lib-header-validation-adapt a Jakarta + Spring Boot 4 | lib-header-validation-adapt | library | medium | 6.0 |
| T-005 | Migrar microservicio ms-customer-retrievebalancebuckets (HYBRID) | ms-customer-retrievebalancebuckets | microservice | medium | 12.0 |
| T-006 | Migrar microservicio ms-customer-retrieveaccountbalanceinfo (HYBRID) | ms-customer-retrieveaccountbalanceinfo | microservice | medium | 12.0 |
| T-007 | Migrar microservicio ms-user-customertypequery-soap (HYBRID) | ms-user-customertypequery-soap | microservice | medium | 12.0 |
| T-008 | Migrar microservicio ms-comp-netcrackerrdb (REST) | ms-comp-netcrackerrdb | microservice | medium | 12.0 |
| T-009 | Migrar microservicio ms-comp-sdp-balancequery-rest (REST) | ms-comp-sdp-balancequery-rest | microservice | medium | 12.0 |
| T-010 | Migrar microservicio ms-comp-redis-rest (REST) | ms-comp-redis-rest | microservice | medium | 12.0 |
| T-011 | Migrar microservicio ms-header-validation-rest (REST) | ms-header-validation-rest | microservice | medium | 12.0 |

## Orden de ejecución
T-001 → T-002 → T-003 → T-004 → T-005 → T-006 → T-007 → T-008 → T-009 → T-010 → T-011

## Riesgos (2)
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
