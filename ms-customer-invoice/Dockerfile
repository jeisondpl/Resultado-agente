FROM 10.112.230.33/telefonica/g-microservicios/base/jre-25.0.2_12:latest

# Instalar libgcc para soporte de Netty QUIC (RHEL/CentOS minimal)
RUN dnf install -y libgcc || echo 'dnf no disponible, contacte al equipo de infraestructura para incluir libgcc en la imagen base.'

RUN mkdir -p /opt/ms/ms-customer-invoice && mkdir -p /opt/log/ms-customer-invoice

WORKDIR /opt/ms/ms-customer-invoice

# --------------------------------
# Copy scripts, packages & plugins
# --------------------------------
COPY --from=builder:1.0.0 /app/target/*.jar /opt/ms/ms-customer-invoice/

# Set permissions
USER root
RUN chown -R :1000 /opt/ms/ms-customer-invoice && chmod -R g+rwx /opt/ms/ms-customer-invoice
#-----------------------
# Expose container ports
# ----------------------
EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java -jar $JAVA_OPTS /opt/ms/ms-customer-invoice/ms-customer-invoice-1.0.0.jar" ]
