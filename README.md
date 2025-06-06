wget https://archive.apache.org/dist/kafka/3.7.0/kafka_2.13-3.7.0.tgz

 
tar -xzf kafka_2.13-3.7.0.tgz
cd kafka_2.13-3.7.0

 
bin/zookeeper-server-start.sh config/zookeeper.properties

bin/kafka-server-start.sh config/server.properties

 
bin/kafka-topics.sh --create --topic test --bootstrap-server 172.20.1.217:9092 --partitions 1 --replication-factor 1

bin/kafka-console-producer.sh --topic test --bootstrap-server 172.20.1.217:9092

 
bin/kafka-console-consumer.sh --topic test --from-beginning --bootstrap-server 172.20.1.217:9092

 
 
nano kafka_2.13-3.7.0/config/server.properties

listeners=PLAINTEXT://0.0.0.0:9092
advertised.listeners=PLAINTEXT://172.20.1.217:9092


