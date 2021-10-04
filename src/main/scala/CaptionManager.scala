import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import play.api.libs.json._
import scalaj.http.Http


object CaptionManager {

  def filterNouns(response: String, token: String): Unit = {
    try {
      val captions = Json.parse(response) \\ "utf8"
      val mappedCaptions = captions.map(elem => elem.toString().replace(raw"\n", " ").replace("\"", ""))
      val potentialNoun = NounChecker.markPotentialNouns(mappedCaptions.toList)

      val nouns = potentialNoun.map(NounChecker.filterNouns).filter(elem => elem.nonEmpty).flatten.distinct.map(elem => elem.replace(Configuration.NOUN_POINTER, ""))
      ResultFileCreator(token, response, mappedCaptions.mkString, nouns)
    } catch {
      case _: MismatchedInputException => ExceptionLogger(s"No Youtube video for token: $token")
      case _: JsonParseException => ExceptionLogger(s"No Youtube video for token: $token")
    }
  }

  def makeRequestAndManageResponse(token: String): Unit = {
    val captionResponse = Http(s"${Configuration.YOUTUBE_URL}&v=$token").asString.body
    filterNouns(captionResponse, token)
  }

  def apply(token: String): Unit = {
    makeRequestAndManageResponse(token)
  }
}
