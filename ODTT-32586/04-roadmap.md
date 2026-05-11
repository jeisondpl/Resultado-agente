# 04 — Roadmap

_MAU: ODTT-32586_

## Roadmap incremental

> **Nota clave:** cada microservicio del MAU expone **SOAP y REST simultáneamente** en el mismo artefacto Maven. No se separan por protocolo; se migra cada microservicio como una unidad y la exposición dual se preserva.

### Sprint 1 — Librerías compartidas base
_Migrar primero: son dependencia transitiva de adapters y microservicios._

- _sin librerías base detectadas_

### Sprint 2 — Adaptadores e integraciones
_Capa de integración con Oracle RDB, Redis 8 y sistemas externos._

- `lib-comp-sdp-balancequery-adapt` _(adapter)_
- `lib-comp-redis-adapt` _(adapter)_
- `lib-comp-netcracker-cbm-adapt` _(adapter)_
- `lib-header-validation-adapt` _(adapter)_

### Sprint 3..N — Microservicios consumidores
_Cada microservicio se migra como un único artefacto que mantiene ambas exposiciones (SOAP + REST)._

- `ms-customer-retrievebalancebuckets` — expone **SOAP + REST** en el mismo artefacto
- `ms-customer-retrieveaccountbalanceinfo` — expone **SOAP + REST** en el mismo artefacto
- `ms-user-customertypequery-soap` — expone **SOAP + REST** en el mismo artefacto
- `ms-comp-netcrackerrdb` — expone **SOAP + REST** en el mismo artefacto
- `ms-comp-sdp-balancequery-rest` — expone **SOAP + REST** en el mismo artefacto
- `ms-comp-redis-rest` — expone **SOAP + REST** en el mismo artefacto
- `ms-header-validation-rest` — expone **SOAP + REST** en el mismo artefacto
