# 06 — Matriz Riesgos

_MAU: ODTT-32586_

| ID | Categoría | Severidad | Título | Descripción |
|----|-----------|-----------|--------|-------------|
| R-001 | técnico | high | Compatibilidad SOAP tras Spring Boot 4 + Jakarta | 2 microservicio(s) SOAP requieren reemplazo javax.* → jakarta.* sin romper contratos WSDL. |
| R-004 | dependencias | medium | Orden topológico de librerías | 4 librería(s); deben migrarse libs base antes que consumidores. |
| R-005 | transversal | high | javax → jakarta package rename | Reemplazo de imports javax.* → jakarta.* en Servlet, JAX-WS, JAXB, validation, persistence. |
