# 17 — Riesgos Criticos

_MAU: ODTT-32586_

### [R-001] Compatibilidad SOAP tras Spring Boot 4 + Jakarta
2 microservicio(s) SOAP requieren reemplazo javax.* → jakarta.* sin romper contratos WSDL.

### [R-005] javax → jakarta package rename
Reemplazo de imports javax.* → jakarta.* en Servlet, JAX-WS, JAXB, validation, persistence.

