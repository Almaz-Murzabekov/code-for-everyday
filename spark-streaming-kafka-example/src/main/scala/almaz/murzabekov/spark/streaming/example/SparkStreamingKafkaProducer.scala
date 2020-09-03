package almaz.murzabekov.spark.streaming.example

import java.util.Properties

import io.circe.syntax.EncoderOps
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object SparkStreamingKafkaProducer extends SparkStreamingKafkaExample {

  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", INPUT_SERVER_URL)
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)

    import java.util.UUID

    def randomEvent(): String = {
      val eventName = UUID.randomUUID.toString
      val eventBody = if(System.nanoTime() % 2 == 0) Option(UUID.randomUUID.toString) else None
      val event = Event(System.nanoTime(), eventName, eventBody)

      import io.circe.generic.auto._
      val json = event.asJson.noSpaces

      println(json)

      json
    }

    while (true){
      val key = UUID.randomUUID.toString
      val value = randomEvent()

      val record = new ProducerRecord[String, String](KAFKA_INPUT_TOPICS.head, key, value)
      producer.send(record)
      println(s"Event fired: $key")
      Thread.sleep((Math.random * 100).toLong)
    }
  }
}
