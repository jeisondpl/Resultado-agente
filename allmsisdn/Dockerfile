FROM 10.112.230.33/telefonica/g-microservicios/base/jre-25.0.2_12:latest

# Instalar libgcc para soporte de Netty QUIC (RHEL/CentOS minimal)
RUN dnf install -y libgcc || echo 'dnf no disponible, contacte al equipo de infraestructura para incluir libgcc en la imagen base.'

RUN mkdir -p /opt/ms/allmsisdn && mkdir -p /opt/log/allmsisdn

WORKDIR /opt/ms/allmsisdn

# --------------------------------
# Copy scripts, packages & plugins
# --------------------------------
COPY --from=builder:1.0.0 /app/target/*.jar /opt/ms/allmsisdn/

# Set permissions
USER root
RUN chown -R :1000 /opt/ms/allmsisdn && chmod -R g+rwx /opt/ms/allmsisdn
#-----------------------
# Expose container ports
# ----------------------
EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java -jar $JAVA_OPTS /opt/ms/allmsisdn/allmsisdn-1.0.0.jar" ]
