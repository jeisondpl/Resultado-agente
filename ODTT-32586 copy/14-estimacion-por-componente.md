# 14 — Estimacion Por Componente

_MAU: ODTT-32586_

## Estimación por componente

**Total agregado:** 124 h (≈ 15.5 días-persona).

Horas desglosadas en **Migración** (cambios de código, deps, configuración) y **Tests** (unit + contract + integration). La complejidad refleja el riesgo y la cantidad de superficie afectada.

| Componente | Tipo | Migración (h) | Tests (h) | Total (h) | Complejidad | Justificación |
|------------|------|---------------|-----------|-----------|-------------|---------------|
| `lib-comp-sdp-balancequery-adapt` | library (adapter) | 6 | 3 | **9** | Media | Adaptador con cliente externo; revisar API del cliente actualizado. |
| `lib-comp-redis-adapt` | library (adapter) | 6 | 3 | **9** | Media | Adaptador con cliente externo; revisar API del cliente actualizado. |
| `lib-comp-netcracker-cbm-adapt` | library (adapter) | 6 | 3 | **9** | Media | Adaptador con cliente externo; revisar API del cliente actualizado. |
| `lib-header-validation-adapt` | library (adapter) | 6 | 3 | **9** | Media | Adaptador con cliente externo; revisar API del cliente actualizado. |
| `ms-customer-retrievebalancebuckets` | microservice (HYBRID) | 10 | 6 | **16** | Alta | Doble exposición SOAP+REST en el mismo artefacto; tests de contrato duplicados. |
| `ms-customer-retrieveaccountbalanceinfo` | microservice (HYBRID) | 10 | 6 | **16** | Alta | Doble exposición SOAP+REST en el mismo artefacto; tests de contrato duplicados. |
| `ms-user-customertypequery-soap` | microservice (HYBRID) | 10 | 6 | **16** | Alta | Doble exposición SOAP+REST en el mismo artefacto; tests de contrato duplicados. |
| `ms-comp-netcrackerrdb` | microservice (REST) | 6 | 4 | **10** | Media | Spring MVC + Jackson; cambios más mecánicos que en SOAP. |
| `ms-comp-sdp-balancequery-rest` | microservice (REST) | 6 | 4 | **10** | Media | Spring MVC + Jackson; cambios más mecánicos que en SOAP. |
| `ms-comp-redis-rest` | microservice (REST) | 6 | 4 | **10** | Media | Spring MVC + Jackson; cambios más mecánicos que en SOAP. |
| `ms-header-validation-rest` | microservice (REST) | 6 | 4 | **10** | Media | Spring MVC + Jackson; cambios más mecánicos que en SOAP. |
