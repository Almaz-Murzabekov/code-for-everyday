package almaz.murzabekov.spark.streaming.example

trait SparkStreamingKafkaExample {

  lazy val STREAMING_MICRO_BATCH__SIZE_IN_SECONDS = 1

  lazy val INPUT_SERVER_URL = "localhost:9092"

  lazy val KAFKA_INPUT_TOPICS = Set("test_input")

  lazy val KAFKA_GROUP_ID = "console-consumer"

}
