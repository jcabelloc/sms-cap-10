echo "Pushing service docker images to docker hub ...."
docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD
docker push jcabelloc/fintech-eurekaserver:$BUILD_NAME
docker push jcabelloc/fintech-configurationserver:$BUILD_NAME
docker push jcabelloc/fintech-zuulserver:$BUILD_NAME
docker push jcabelloc/fintech-servicio-autenticacion:$BUILD_NAME
docker push jcabelloc/fintech-servicio-prestamo:$BUILD_NAME
docker push jcabelloc/fintech-servicio-cliente:$BUILD_NAME
