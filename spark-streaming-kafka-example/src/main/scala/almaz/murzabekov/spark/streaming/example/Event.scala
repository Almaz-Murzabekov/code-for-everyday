package almaz.murzabekov.spark.streaming.example

case class Event(timestamp: Long,
                 event_name: String,
                 event_body: Option[String])
