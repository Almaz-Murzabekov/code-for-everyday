package almaz.murzabekov.spark.streaming.example

import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreamingWordNumbers {

  val SERVER_IP = "localhost"
  val SERVER_PORT = 9876

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .appName("spark-streaming-kafka-example")
      .master("local[*]")
      .getOrCreate

    val ssc = new StreamingContext(spark.sparkContext, Seconds(1))
    spark.sparkContext.setLogLevel("ERROR")

    val lines = ssc.socketTextStream(SERVER_IP, SERVER_PORT)

    val words = lines
      .flatMap(_.split(" "))
      .map((_, 1))
      .reduceByKey(_ + _)

    words.print(10)

    ssc.start()
    ssc.awaitTermination()
  }

}
