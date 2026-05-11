# 03 — Arquitectura Objetivo

_MAU: ODTT-32586_

## ¿Qué son las entidades?

Las **entidades** son los elementos técnicos que el LLM-visión identifica en los diagramas del MAU:
microservicios, librerías compartidas, bases de datos, procedimientos almacenados, endpoints y otros componentes. 
Las **relaciones** describen cómo se invocan entre sí (consulta, llama, valida, expone).

## Glosario consolidado (21 entidades únicas)

| Entidad | Tipo inferido | Aparece en |
|---------|---------------|------------|
| `/header/validate` | endpoint | img-009 |
| `APL-MSUserCustomerTypeQuery-Soap` | microservicio | img-009 |
| `OTC_K_CUSTOMER_INFORMATION` | procedimiento Oracle | img-008 |
| `OTC_K_GET_DATA_MOBILE_LINE` | procedimiento Oracle | img-007 |
| `OTC_P_QUERY_CUSTOMER` | procedimiento Oracle | img-008 |
| `RDB` | base de datos | img-009 |
| `RDB_OSB` | base de datos | img-007, img-008 |
| `Rest` | otro | img-009 |
| `Soap` | otro | img-009 |
| `api/cbm/bam/accounts/{id}` | endpoint | img-008 |
| `exposición` | otro | img-009 |
| `lib-comp-netcracker-cbm` | librería | img-008 |
| `lib-header-validate` | librería | img-009 |
| `lib-header-validation` | librería | img-007, img-008 |
| `ms-comp-netcracker-cbm-rest` | microservicio | img-008 |
| `ms-comp-netcrackerdb` | microservicio | img-009 |
| `ms-comp-sdp-balancequery-rest` | microservicio | img-007 |
| `ms-customer-retrieveaccountbalanceinfo` | microservicio | img-008 |
| `ms-customer-retrievebalancebuckets` | microservicio | img-007 |
| `ms-header-validation-rest` | microservicio | img-007, img-008, img-009 |
| `ms-user-customertypequery-test` | microservicio | img-009 |

## Vista por diagrama

### img-007 — architecture  _(confianza 90%)_

El diagrama muestra dos arquitecturas de software, una actual y una propuesta, para un sistema de consulta de balance. En la sección 'Actual', se observa un flujo que inicia en 'ms-customer-retrievebalancebuckets' y se conecta a servicios como 'OTC_K_GET_DATA_MOBILE_LINE' y 'RDB_OSB'. La sección 'Propuesta' introduce nuevos componentes como 'lib-header-validation' y 'ms-header-validation-rest', y modifica el flujo de datos hacia servicios como 'ms-comp-sdp-balancequery-rest'. La propuesta parece optimizar el flujo de datos y mejorar la modularidad del sistema, integrando servicios REST adicionales y validaciones de encabezado.

**Relaciones:**
- `ms-customer-retrievebalancebuckets` → `OTC_K_GET_DATA_MOBILE_LINE` _(flow)_
- `OTC_K_GET_DATA_MOBILE_LINE` → `RDB_OSB` _(flow)_
- `lib-header-validation` → `ms-header-validation-rest` _(flow)_
- `ms-header-validation-rest` → `ms-comp-sdp-balancequery-rest` _(flow)_

### img-008 — architecture  _(confianza 90%)_

El diagrama presenta dos secciones: 'Actual' y 'Propuesta', comparando la arquitectura actual y una propuesta para un sistema de consulta de balance de cuentas. En la sección 'Actual', el flujo comienza con 'ms-customer-retrieveaccountbalanceinfo', que se conecta a 'api/cbm/bam/accounts/{id}' y luego a 'ms-comp-netcracker-cbm-rest'. También interactúa con una base de datos 'RDB_OSB' a través de 'OTC_K_CUSTOMER_INFORMATION' y 'OTC_P_QUERY_CUSTOMER'. En la sección 'Propuesta', se introduce un componente de validación 'lib-header-validation' que se conecta a 'ms-header-validation-rest'. El flujo sigue similar, pero se añade 'lib-comp-netcracker-cbm' antes de 'ms-comp-netcracker-cbm-rest'.

**Relaciones:**
- `ms-customer-retrieveaccountbalanceinfo` → `api/cbm/bam/accounts/{id}` _()_
- `api/cbm/bam/accounts/{id}` → `ms-comp-netcracker-cbm-rest` _()_
- `ms-customer-retrieveaccountbalanceinfo` → `RDB_OSB` _(OTC_K_CUSTOMER_INFORMATION)_
- `ms-customer-retrieveaccountbalanceinfo` → `RDB_OSB` _(OTC_P_QUERY_CUSTOMER)_
- `lib-header-validation` → `ms-header-validation-rest` _()_
- `ms-customer-retrieveaccountbalanceinfo` → `lib-comp-netcracker-cbm` _()_
- `lib-comp-netcracker-cbm` → `ms-comp-netcracker-cbm-rest` _()_

### img-009 — architecture  _(confianza 90%)_

El diagrama presenta dos secciones: 'Actual' y 'Propuesta', comparando la arquitectura actual de un sistema con una propuesta de mejora. En la sección 'Actual', el flujo comienza con 'APL-MSUserCustomerTypeQuery-Soap' que se conecta a '/header/validate' y luego a 'ms-header-validation-rest'. Otro flujo paralelo conecta 'ms-user-customertypequery-test' con 'ms-comp-netcrackerdb' y finalmente con 'RDB'. En la sección 'Propuesta', se introduce un componente de 'exposición' que incluye 'Soap' y 'Rest', y un nuevo componente 'lib-header-validate' antes de '/header/validate'. La propuesta busca optimizar el flujo de validación y acceso a la base de datos.

