package almaz.murzabekov.spark.streaming.example

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreamingKafkaConsumer extends SparkStreamingKafkaExample {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("SparkKafkaStreamingConsumer")
      .setMaster("local[*]")

    val ssc = new StreamingContext(conf, Seconds(STREAMING_MICRO_BATCH__SIZE_IN_SECONDS))
    val sc = ssc.sparkContext
    sc.setLogLevel("ERROR")

    val kafkaParams = Map[String, Object](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> INPUT_SERVER_URL,
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer],
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer],
      ConsumerConfig.GROUP_ID_CONFIG -> KAFKA_GROUP_ID
    )

    val stream = KafkaUtils.createDirectStream[String, Event](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, Event](KAFKA_INPUT_TOPICS, kafkaParams))

    stream.foreachRDD(rdd => rdd.foreach(p => {
      println(s"[Event] Key: ${p.key()} Body: ${p.value()}")
    }))

    ssc.start()
    ssc.awaitTermination()
  }
}
