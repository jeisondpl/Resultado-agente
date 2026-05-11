# 04 — Roadmap

_MAU: ODTT-32586_

## Roadmap incremental

> **Nota clave:** cada microservicio del MAU expone **SOAP y REST simultáneamente** en el mismo artefacto Maven. No se separan por protocolo; se migra cada microservicio como una unidad y la exposición dual se preserva.

### Sprint 1 — Librerías compartidas base
_Migrar primero: son dependencia transitiva de adapters y microservicios._

- `lib-header-validation`
- `lib-header-validate`

### Sprint 2 — Adaptadores e integraciones
_Capa de integración con Oracle RDB, Redis 8 y sistemas externos._

- `lib-comp-sdp-balancequery-adapt` _(adapter)_
- `lib-comp-redis-adapt` _(adapter)_
- `lib-comp-netcracker-rdb` _(adapter)_

### Sprint 2.5 — Resto de librerías

- `APL-MSLibNetcrackerCbm`

### Sprint 3..N — Microservicios consumidores
_Cada microservicio se migra como un único artefacto que mantiene ambas exposiciones (SOAP + REST)._

- `APL-MSCompSdpBalancequeryRest` — expone **SOAP + REST** en el mismo artefacto
- `APL-MSHeaderValidationRest` — expone **SOAP + REST** en el mismo artefacto
- `APL-MSCompNetcrackerRDB` — expone **SOAP + REST** en el mismo artefacto
- `APL-MSCustomerRetrievebalancebuckets-OSB` — expone **SOAP + REST** en el mismo artefacto
- `APL-MSUserCustomerTypeQuery-Soap` — expone **SOAP + REST** en el mismo artefacto
- `APL-MSUserCustomerTypeQuery-Rest` — expone **SOAP + REST** en el mismo artefacto
- `APL-MSCompRedisRest` — expone **SOAP + REST** en el mismo artefacto
- `ms-customer-retrievebalancebuckets` — expone **SOAP + REST** en el mismo artefacto
- `ms-customer-retrieveaccountbalanceinfo` — expone **SOAP + REST** en el mismo artefacto
- `ms-user-customertypequery-soap` — expone **SOAP + REST** en el mismo artefacto
