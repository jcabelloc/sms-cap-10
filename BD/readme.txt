### Desde el CMD y en ubicado en este directorio ejecutar
docker image build -t jcabelloc/postgres .
docker container run -it -p 5432:5432 jcabelloc/postgres