import com.fasterxml.jackson.databind.exc.MismatchedInputException
import play.api.libs.json._
import scalaj.http.Http

object CaptionManager {

  def selectNouns(response: String): List[String] = {
    val captions = Json.parse(response) \\ "utf8"
    val mappedCaptions = captions.map(elem => elem.toString().replace(raw"\n", " ").replace("\"", ""))
    val potentialNoun = NounChecker.prepareWordList(mappedCaptions.toList)

    potentialNoun.map(NounChecker.isNoun).filter(elem => elem.nonEmpty).flatten.distinct.map(elem => elem.replace("*", ""))
    // tu najlepiej byloby robic zapytania do wikipedii, potrzebny jest tylko token
  }

  def makeRequestAndManageResponse(token: String): List[String] = {
    val captionResponse = Http(s"${Configuration.YOUTUBE_URL}&v=$token").asString.body
    selectNouns(captionResponse)
  }


  def processWikipediaResponse(response: String): Unit = {
    try {
      val parsedResponse = Json.parse(response)
      val link = parsedResponse \\ "page"
      val article = parsedResponse \\ "extract"
      println(link.distinct)
      println(article)
    } catch {
      case _: MismatchedInputException => println("No wikipedia article")
    }
  }

  def findWikipediaArticle(noun: String): Unit = {
    val wikipediaResponse = Http(s"${Configuration.WIKIPEDIA_URL}${noun.capitalize}").asString.body
    processWikipediaResponse(wikipediaResponse)
  }

  def apply(token: String): Unit = {
    val captionNouns = makeRequestAndManageResponse(token)
    captionNouns.foreach(findWikipediaArticle)

  }
}
