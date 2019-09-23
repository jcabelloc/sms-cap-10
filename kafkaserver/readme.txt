### Desde el CMD teniendo docker instalado

docker run -p 2181:2181 -p 9092:9092 -e ADVERTISED_HOST=localhost  -e NUM_PARTITIONS=10 johnnypark/kafka-zookeeper
