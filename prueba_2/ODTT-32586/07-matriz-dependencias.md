# 07 — Matriz Dependencias

_MAU: ODTT-32586_

## Matriz de dependencias

Por cada tarea, qué tareas la **bloquean** (debe completarse antes) y qué tareas **dependen** de ella (se desbloquean al terminar).

| Tarea | Componente | Tipo | Bloqueada por | Desbloquea a |
|-------|------------|------|----------------|---------------|
| `T-001` | `lib-comp-sdp-balancequery-adapt` | library | _(ninguna)_ | `T-005`, `T-006`, `T-007`, `T-008`, `T-009`, `T-010`, `T-011`, `T-012`, `T-013`, `T-014` |
| `T-002` | `lib-comp-redis-adapt` | library | _(ninguna)_ | `T-005`, `T-006`, `T-007`, `T-008`, `T-009`, `T-010`, `T-011`, `T-012`, `T-013`, `T-014` |
| `T-003` | `lib-comp-netcracker-rdb` | library | _(ninguna)_ | `T-005`, `T-006`, `T-007`, `T-008`, `T-009`, `T-010`, `T-011`, `T-012`, `T-013`, `T-014` |
| `T-004` | `APL-MSLibNetcrackerCbm` | library | _(ninguna)_ | `T-005`, `T-006`, `T-007`, `T-008`, `T-009`, `T-010`, `T-011`, `T-012`, `T-013`, `T-014` |
| `T-005` | `APL-MSCompSdpBalancequeryRest` | microservice | `T-001`, `T-002`, `T-003`, `T-004` | _(ninguna)_ |
| `T-006` | `APL-MSHeaderValidationRest` | microservice | `T-001`, `T-002`, `T-003`, `T-004` | _(ninguna)_ |
| `T-007` | `APL-MSCompNetcrackerRDB` | microservice | `T-001`, `T-002`, `T-003`, `T-004` | _(ninguna)_ |
| `T-008` | `APL-MSCustomerRetrievebalancebuckets-OSB` | microservice | `T-001`, `T-002`, `T-003`, `T-004` | _(ninguna)_ |
| `T-009` | `APL-MSUserCustomerTypeQuery-Soap` | microservice | `T-001`, `T-002`, `T-003`, `T-004` | _(ninguna)_ |
| `T-010` | `APL-MSUserCustomerTypeQuery-Rest` | microservice | `T-001`, `T-002`, `T-003`, `T-004` | _(ninguna)_ |
| `T-011` | `APL-MSCompRedisRest` | microservice | `T-001`, `T-002`, `T-003`, `T-004` | _(ninguna)_ |
| `T-012` | `ms-customer-retrievebalancebuckets` | microservice | `T-001`, `T-002`, `T-003`, `T-004` | _(ninguna)_ |
| `T-013` | `ms-customer-retrieveaccountbalanceinfo` | microservice | `T-001`, `T-002`, `T-003`, `T-004` | _(ninguna)_ |
| `T-014` | `ms-user-customertypequery-soap` | microservice | `T-001`, `T-002`, `T-003`, `T-004` | _(ninguna)_ |
