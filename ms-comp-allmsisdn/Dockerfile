# ------------------------------------------------------------------------
#
# Imagen de microservicio - Telefonica
# 
# Directorio de ubicacion de todos los microservicio /opt/ms/ms-comp-allmsisdn
#
# docker build -f Dockerfile -t ms-comp-allmsisdn:1.0.0-qa .
#
# ------------------------------------------------------------------------

FROM 10.112.230.33/telefonica/g-microservicios/base/openjdk-8-runtime-java:latest
MAINTAINER devops@telefonica.com.ec

RUN mkdir -p /opt/ms/ms-comp-allmsisdn

WORKDIR /opt/ms/ms-comp-allmsisdn

# --------------------------------
# Copy scripts, packages & plugins
# --------------------------------
COPY target/ms-comp-allmsisdn-1.0.0.jar /opt/ms/ms-comp-allmsisdn/


#-----------------------
# Expose container ports
# ----------------------
EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java -jar $JAVA_OPTS /opt/ms/ms-comp-allmsisdn/ms-comp-allmsisdn-1.0.0.jar" ]
