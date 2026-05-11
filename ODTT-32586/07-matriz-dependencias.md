# 07 — Matriz Dependencias

_MAU: ODTT-32586_

## Matriz de dependencias

Por cada tarea, qué tareas la **bloquean** (debe completarse antes) y qué tareas **dependen** de ella (se desbloquean al terminar).

| Tarea | Componente | Tipo | Bloqueada por | Desbloquea a |
|-------|------------|------|----------------|---------------|
| `T-001` | `lib-comp-sdp-balancequery-adapt` | library | _(ninguna)_ | `T-005`, `T-006`, `T-007`, `T-008`, `T-009`, `T-010`, `T-011` |
| `T-002` | `lib-comp-redis-adapt` | library | _(ninguna)_ | `T-005`, `T-006`, `T-007`, `T-008`, `T-009`, `T-010`, `T-011` |
| `T-003` | `lib-comp-netcracker-cbm-adapt` | library | _(ninguna)_ | `T-005`, `T-006`, `T-007`, `T-008`, `T-009`, `T-010`, `T-011` |
| `T-004` | `lib-header-validation-adapt` | library | _(ninguna)_ | `T-005`, `T-006`, `T-007`, `T-008`, `T-009`, `T-010`, `T-011` |
| `T-005` | `ms-customer-retrievebalancebuckets` | microservice | `T-001`, `T-002`, `T-003`, `T-004` | _(ninguna)_ |
| `T-006` | `ms-customer-retrieveaccountbalanceinfo` | microservice | `T-001`, `T-002`, `T-003`, `T-004` | _(ninguna)_ |
| `T-007` | `ms-user-customertypequery-soap` | microservice | `T-001`, `T-002`, `T-003`, `T-004` | _(ninguna)_ |
| `T-008` | `ms-comp-netcrackerrdb` | microservice | `T-001`, `T-002`, `T-003`, `T-004` | _(ninguna)_ |
| `T-009` | `ms-comp-sdp-balancequery-rest` | microservice | `T-001`, `T-002`, `T-003`, `T-004` | _(ninguna)_ |
| `T-010` | `ms-comp-redis-rest` | microservice | `T-001`, `T-002`, `T-003`, `T-004` | _(ninguna)_ |
| `T-011` | `ms-header-validation-rest` | microservice | `T-001`, `T-002`, `T-003`, `T-004` | _(ninguna)_ |
