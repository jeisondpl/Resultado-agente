# allmsisdn.

Pasos para despliegue.

1. Compilaci�n.
2. Creaci�n de imagen.
3. Creaci�n de ConfigMap (Sinconizaci�n con proyecto de despliegue "ms-cluster")
4. Despliegue de servicio.

# Compilaci�n.

mvn clean install

Genera el componente "allmsisdn.jar"

Clase que inicia: ec.otecel.ms

Protocolo: HTTP

Puerto interno: 8080

# Creaci�n de imagen.

sudo docker build -t allmsisdn:1.0.0-qa .

# Eliminaci�n de imagenes.

docker rmi 10.112.230.33:443/telefonica/g-microservicios/allmsisdn:1.0.0-qa

# Crear carpeta de logs.
	
	Realizar en cada uno de los nodos esclavos.

	mkdir -p /datos/logs/ms/allmsisdn

# Adicionar logs a filebeat (ELK).

	Editar el archivo de configuraci�n en cada uno de los nodos esclavos y adicionar al final de paths:
	
	sudo vim /etc/filebeat/filebeat.yml
	
	- /datos/logs/ms/allmsisdn/LogExec.log

	sudo systemctl stop filebeat
	sudo systemctl start filebeat
	sudo systemctl status filebeat
	
# Despliegue de Service y Deployment.

Se realiza en el nodo maestro ubicado en el directorio del microservicio. /home/devops_pre/ms-cluster/ms-cluster/ms-middleware/company-portal/allmsisdn/

Create the configmap

	kubectl create configmap allmsisdn-configmap --namespace=ms-middleware --from-file=application.properties 
	
	kubectl create configmap allmsisdn-log --namespace=ms-middleware --from-file=log4j.xml 
	
	

	kubectl delete configmap allmsisdn-configmap --namespace=ms-middleware

	kubectl delete configmap allmsisdn-log --namespace=ms-middleware


Desplegar


	kubectl apply -f k8s-allmsisdn.yaml


# Eliminaci�n de Service y Deployment.

kubectl delete deployment allmsisdn-deployment --namespace=ms-middleware

kubectl delete service allmsisdn-service --namespace=ms-middleware

kubectl delete configmap allmsisdn-configmap --namespace=ms-middleware

# Verificaci�n de despliegue.

	kubectl get all --all-namespaces
	kubectl get services --namespace=ms-middleware
	kubectl get deployments --namespace=ms-middleware
	kubectl get pods --namespace=ms-middleware -o wide | grep allmsisdn
