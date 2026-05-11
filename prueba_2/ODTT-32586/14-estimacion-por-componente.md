# 14 — Estimacion Por Componente

_MAU: ODTT-32586_

## Estimación por componente

**Total agregado:** 170 h (≈ 21.2 días-persona).

Horas desglosadas en **Migración** (cambios de código, deps, configuración) y **Tests** (unit + contract + integration). La complejidad refleja el riesgo y la cantidad de superficie afectada.

| Componente | Tipo | Migración (h) | Tests (h) | Total (h) | Complejidad | Justificación |
|------------|------|---------------|-----------|-----------|-------------|---------------|
| `lib-header-validation` | library (shared) | 4 | 2 | **6** | Baja | Librería base; cambios típicos javax→jakarta + bump POM. |
| `lib-comp-sdp-balancequery-adapt` | library (adapter) | 6 | 3 | **9** | Media | Adaptador con cliente externo; revisar API del cliente actualizado. |
| `lib-comp-redis-adapt` | library (adapter) | 6 | 3 | **9** | Media | Adaptador con cliente externo; revisar API del cliente actualizado. |
| `lib-comp-netcracker-rdb` | library (adapter) | 6 | 3 | **9** | Media | Adaptador con cliente externo; revisar API del cliente actualizado. |
| `lib-header-validate` | library (shared) | 4 | 2 | **6** | Baja | Librería base; cambios típicos javax→jakarta + bump POM. |
| `APL-MSLibNetcrackerCbm` | library (other) | 5 | 2 | **7** | Media | Librería sin clasificación firme; estimación conservadora. |
| `APL-MSCompSdpBalancequeryRest` | microservice (REST) | 6 | 4 | **10** | Media | Spring MVC + Jackson; cambios más mecánicos que en SOAP. |
| `APL-MSHeaderValidationRest` | microservice (REST) | 6 | 4 | **10** | Media | Spring MVC + Jackson; cambios más mecánicos que en SOAP. |
| `APL-MSCompNetcrackerRDB` | microservice (UNKNOWN) | 8 | 5 | **13** | Media | Tipo no determinado; estimación por defecto. |
| `APL-MSCustomerRetrievebalancebuckets-OSB` | microservice (UNKNOWN) | 8 | 5 | **13** | Media | Tipo no determinado; estimación por defecto. |
| `APL-MSUserCustomerTypeQuery-Soap` | microservice (SOAP) | 8 | 5 | **13** | Alta | Spring-WS + JAX-WS + JAXB; contrato WSDL frozen, riesgo de regresión. |
| `APL-MSUserCustomerTypeQuery-Rest` | microservice (REST) | 6 | 4 | **10** | Media | Spring MVC + Jackson; cambios más mecánicos que en SOAP. |
| `APL-MSCompRedisRest` | microservice (REST) | 6 | 4 | **10** | Media | Spring MVC + Jackson; cambios más mecánicos que en SOAP. |
| `ms-customer-retrievebalancebuckets` | microservice (HYBRID) | 10 | 6 | **16** | Alta | Doble exposición SOAP+REST en el mismo artefacto; tests de contrato duplicados. |
| `ms-customer-retrieveaccountbalanceinfo` | microservice (HYBRID) | 10 | 6 | **16** | Alta | Doble exposición SOAP+REST en el mismo artefacto; tests de contrato duplicados. |
| `ms-user-customertypequery-soap` | microservice (SOAP) | 8 | 5 | **13** | Alta | Spring-WS + JAX-WS + JAXB; contrato WSDL frozen, riesgo de regresión. |
