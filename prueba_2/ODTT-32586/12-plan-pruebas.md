# 12 — Plan Pruebas

_MAU: ODTT-32586_

## Plan de pruebas

- **Unit:** ≥ 80 % coverage por componente.
- **Integration:** Redis 8, Oracle RDB, SOAP/REST end-to-end.
- **Contract:** Pact / WSDL frozen, validación 1:1 con producción.
- **Performance:** p95 ≤ baseline + 10 %.
- **Regression:** suite full antes de cada release branch.

