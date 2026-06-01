FROM 10.112.230.33/telefonica/g-microservicios/base/jre-25.0.2_12:latest

# Instalar libgcc para soporte de Netty QUIC (RHEL/CentOS minimal)
RUN dnf install -y libgcc || echo 'dnf no disponible, contacte al equipo de infraestructura para incluir libgcc en la imagen base.'

RUN mkdir -p /opt/ms/ms-libs-digercic && mkdir -p /opt/log/ms-libs-digercic

WORKDIR /opt/ms/ms-libs-digercic

# --------------------------------
# Copy scripts, packages & plugins
# --------------------------------
COPY --from=builder:1.0.0 /app/target/*.jar /opt/ms/ms-libs-digercic/

# Set permissions
USER root
RUN chown -R :1000 /opt/ms/ms-libs-digercic && chmod -R g+rwx /opt/ms/ms-libs-digercic
#-----------------------
# Expose container ports
# ----------------------
EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java -jar $JAVA_OPTS /opt/ms/ms-libs-digercic/ms-libs-digercic-1.0.0.jar" ]
