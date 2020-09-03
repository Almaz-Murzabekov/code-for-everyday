From official instruction https://kafka.apache.org/documentation/#gettingStarted

### Installation process

> mkdir frameworks && cd frameworks

> wget https://downloads.apache.org/kafka/2.6.0/kafka_2.12-2.6.0.tgz -O kafka_2.12-2.6.0.tgz

> mkdir kafka && cd kafka

> tar -xvzf ../kafka_2.12-2.6.0.tgz --strip 1

> bin/zookeeper-server-start.sh config/zookeeper.properties

In another terminal do following instruction

> cd frameworks/kafka

> bin/kafka-server-start.sh config/server.properties