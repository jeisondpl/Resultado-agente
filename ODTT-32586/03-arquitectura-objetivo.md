# 03 — Arquitectura Objetivo

_MAU: ODTT-32586_

## ¿Qué son las entidades?

Las **entidades** son los elementos técnicos que el LLM-visión identifica en los diagramas del MAU:
microservicios, librerías compartidas, bases de datos, procedimientos almacenados, endpoints y otros componentes. 
Las **relaciones** describen cómo se invocan entre sí (consulta, llama, valida, expone).

## Glosario consolidado (24 entidades únicas)

| Entidad | Tipo inferido | Aparece en |
|---------|---------------|------------|
| `APL-MSCustomerRetrieveaccountbalanceinfo-OSB` | microservicio | img-008 |
| `APL-MSUserCustomerTypeQuery-Soap` | microservicio | img-009 |
| `OTC_K_CUSTOMER_INFORMATION` | procedimiento Oracle | img-008 |
| `OTC_K_GET_DATA_MOBILE_LINE` | procedimiento Oracle | img-007 |
| `OTC_P_G_DATA_FROM_MOBILE` | procedimiento Oracle | img-007 |
| `OTC_P_QUERY_CUSTOMER` | procedimiento Oracle | img-008 |
| `RDB` | base de datos | img-009 |
| `RDB_OSB` | base de datos | img-007, img-008 |
| `api/cbm/bam/accounts/{id}` | endpoint | img-008 |
| `lib-comp-netcracker-cbm` | librería | img-008 |
| `lib-comp-redis-adapt` | librería | img-007 |
| `lib-comp-sdp-balancequery` | librería | img-007 |
| `lib-header-validate` | librería | img-009 |
| `lib-header-validation` | librería | img-007 |
| `lib-header-validation-rest` | microservicio | img-008 |
| `ms-comp-netcracker-cbm-rest` | microservicio | img-008 |
| `ms-comp-netcrackerdb` | microservicio | img-007 |
| `ms-comp-nettrackerb` | microservicio | img-009 |
| `ms-comp-redis-rest` | microservicio | img-007 |
| `ms-comp-sdp-balancequery-rest` | microservicio | img-007 |
| `ms-customer-retrieveaccountbalanceinfo` | microservicio | img-008 |
| `ms-customer-retrievebalancebuckets` | microservicio | img-007 |
| `ms-header-validator-rest` | microservicio | img-009 |
| `ms-user-customertypequery` | microservicio | img-009 |

## Vista por diagrama

### img-007 — flow  _(confianza 85%)_

El diagrama presenta una comparación entre la arquitectura actual y la propuesta para el servicio de consulta de balances. En la parte actual, se observa un flujo que incluye el servicio 'ms-customer-retrievebalancebuckets' que interactúa con un sistema de base de datos (RDB_OSB) y realiza llamadas a funciones específicas para obtener datos móviles. En la propuesta, se introduce un nuevo enfoque que incluye validaciones de encabezado y un servicio REST mejorado, además de la integración con componentes adicionales como 'lib-comp-sdp-balancequery' y 'lib-comp-redis-adapt'. Esta evolución sugiere un enfoque más modular y eficiente para la gestión de consultas de balance, optimizando la interacción entre los servicios y la base de datos.

**Relaciones:**
- `ms-customer-retrievebalancebuckets` → `RDB_OSB` _(interactúa con)_
- `OTC_K_GET_DATA_MOBILE_LINE` → `ms-customer-retrievebalancebuckets` _(llama a)_
- `ms-comp-sdp-balancequery-rest` → `lib-comp-sdp-balancequery` _(utiliza)_
- `lib-comp-redis-adapt` → `ms-comp-redis-rest` _(conecta con)_

### img-008 — component  _(confianza 85%)_

El diagrama presenta una comparación entre la arquitectura actual y la propuesta para el servicio de consulta de saldo de cuentas. En la arquitectura actual, se observa un flujo de datos desde el servicio 'ms-customer-retrieveaccountbalanceinfo' hacia la base de datos 'RDB_OSB', utilizando dos operaciones: 'OTC_K_CUSTOM_INFORMATION' y 'OTC_P_QUERY_CUSTOMER'. En la propuesta, se introduce una nueva capa de validación de encabezados y se diversifican las exposiciones a SOAP y REST. Además, se incluye un nuevo servicio 'lib-comp-netcracker-cbm' que se conecta al servicio 'ms-comp-netcracker-cbm-rest', mejorando la modularidad y la flexibilidad del sistema.

**Relaciones:**
- `ms-customer-retrieveaccountbalanceinfo` → `RDB_OSB` _(consulta)_
- `ms-customer-retrieveaccountbalanceinfo` → `api/cbm/bam/accounts/{id}` _(llamada)_
- `lib-header-validation-rest` → `ms-header-validation-rest` _(validación)_
- `lib-comp-netcracker-cbm` → `ms-comp-netcracker-cbm-rest` _(conexión)_

### img-009 — architecture  _(confianza 85%)_

El diagrama presenta una comparación entre la arquitectura actual y la propuesta para un sistema de consulta de tipo de usuario. En la arquitectura actual, se observa un flujo de datos que incluye un servicio SOAP y un servicio REST, con validaciones de encabezado y acceso a una base de datos relacional (RDB). La propuesta introduce una nueva biblioteca de validación y un enfoque más modular, separando las validaciones y mejorando la interacción entre los servicios. Se destacan los componentes como 'ms-user-customertypequery' y 'ms-comp-nettrackerb', que son esenciales para el funcionamiento del sistema. La transición de SOAP a REST se sugiere para optimizar el rendimiento y la escalabilidad del sistema.

**Relaciones:**
- `APL-MSUserCustomerTypeQuery-Soap` → `ms-header-validator-rest` _(validates)_
- `ms-header-validator-rest` → `RDB` _(accesses)_
- `APL-MSUserCustomerTypeQuery-Soap` → `lib-header-validate` _(uses)_

