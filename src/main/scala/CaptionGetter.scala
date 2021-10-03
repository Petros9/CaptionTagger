import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Framing}
import akka.util.ByteString
import play.api.libs.json._

object CaptionGetter {

  def manageResponse(response: String): Unit = {
    val captions = Json.parse(response) \\ "utf8"
    println(captions)
    //val result = keys.flatMap(_.asOpt[Int]) // List(1, 3, 5)
  }

  def apply(token: String): Unit = {
    implicit val system = ActorSystem("CaptionGetter")
    implicit val materializer = ActorMaterializer
    import system.dispatcher

    val delimiter: Flow[ByteString, ByteString, NotUsed] =
      Framing.delimiter(
        ByteString("\r\n"),
        maximumFrameLength = 100000,
        allowTruncation = true)

    Http().singleRequest(Get(s"${Configuration.REQUEST_URL}&v=$token")).flatMap{ res =>
      val httpResponse = res.entity.dataBytes.via(delimiter).map(_.utf8String)
      httpResponse.runForeach(manageResponse)
    }
  }
}
