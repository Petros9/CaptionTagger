import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding._
import akka.http.scaladsl.model.HttpRequest
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Framing, Sink, Source}
import akka.util.ByteString

import scala.util.{Failure, Success}

object Test extends App {
  implicit val system = ActorSystem("CaptionGetter")
  implicit val materializer = ActorMaterializer
  import system.dispatcher

  val delimiter: Flow[ByteString, ByteString, NotUsed] =
    Framing.delimiter(
      ByteString("\r\n"),
      maximumFrameLength = 100000,
      allowTruncation = true)

  Http().singleRequest(Get("http://www.youtube.com/api/timedtext?v=6Af6b_wyiwI&lang=en&fmt=json3")).flatMap { res =>
    val lines = res.entity.dataBytes.via(delimiter).map(_.utf8String)
    lines.runForeach { line =>
      println(line)
    }
  }
}

object CaptionGetter {
  def apply(line: String): Unit = {

    println(line)
  }
}
