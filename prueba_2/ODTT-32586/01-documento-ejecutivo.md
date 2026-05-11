# 01 — Documento Ejecutivo

_MAU: ODTT-32586_

## Resumen ejecutivo

El objetivo principal de la migración es actualizar los microservicios existentes que operan en un bus G-MSOSB a un entorno más moderno y eficiente utilizando Java OPENJDK 25 LTS, Spring Boot 4 y Redis 8. Esta actualización busca mejorar la eficiencia, modularidad y escalabilidad del sistema, permitiendo una mejor integración con servicios REST y optimizando el flujo de datos. El alcance de la migración incluye la actualización de microservicios y librerías comunes al nuevo estándar, asegurando que funcionen correctamente en escenarios reales. Se utilizarán arquetipos corporativos específicos para la creación de microservicios, como el arquetipoMicroservicios para servicios REST, arquetipoMicroservicios-soap-global para servicios SOAP, y arquetipoMicroservcios-soap-fullstack-micro para servicios híbridos que combinan SOAP y REST. Las restricciones incluyen la disponibilidad de los ambientes de desarrollo y pruebas, así como el acceso a bases de datos Oracle RDB y CBM. Además, se requiere que los microservicios migren sus configuraciones y dependencias para alinearse con las nuevas versiones de software y librerías, asegurando la compatibilidad y el correcto funcionamiento en el nuevo entorno.

- Microservicios identificados: **10**
- Librerías a migrar: **6**
- Diagramas analizados: **3/11**
- Riesgos identificados: **3**

