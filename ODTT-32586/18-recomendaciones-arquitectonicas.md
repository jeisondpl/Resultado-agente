# 18 — Recomendaciones Arquitectonicas

_MAU: ODTT-32586_

## Recomendaciones arquitectónicas

- Adoptar `Clean Architecture` por microservicio para aislar dominios.
- Estandarizar DTOs en módulo `lib-shared-dto`.
- Centralizar observabilidad: OpenTelemetry → Prometheus + Loki + Tempo.
- Hardening: sealed classes, records inmutables, Virtual Threads (Java 21+).
- CI/CD: pipelines paramétricos por componente con caché de Maven.

