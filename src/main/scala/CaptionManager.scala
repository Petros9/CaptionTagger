import com.fasterxml.jackson.databind.exc.MismatchedInputException
import play.api.libs.json._
import scalaj.http.Http


object CaptionManager {

  def selectNouns(response: String, token: String): Unit = {
    try {
      val captions = Json.parse(response) \\ "utf8"
      val mappedCaptions = captions.map(elem => elem.toString().replace(raw"\n", " ").replace("\"", ""))
      val potentialNoun = NounChecker.prepareWordList(mappedCaptions.toList)

      val nouns = potentialNoun.map(NounChecker.isNoun).filter(elem => elem.nonEmpty).flatten.distinct.map(elem => elem.replace(Configuration.NOUN_POINTER, ""))
      ResultFileCreator(token, captions.map(elem => elem.toString()).toList, mappedCaptions.toList, nouns)
    } catch {
      case _: MismatchedInputException => println(s"No Youtube video for token: $token")
    }
  }

  def makeRequestAndManageResponse(token: String): Unit = {
    val captionResponse = Http(s"${Configuration.YOUTUBE_URL}&v=$token").asString.body
    selectNouns(captionResponse, token)
  }

  def apply(token: String): Unit = {
    makeRequestAndManageResponse(token)
  }
}
