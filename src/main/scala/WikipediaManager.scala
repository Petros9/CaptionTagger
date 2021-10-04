import com.fasterxml.jackson.databind.exc.MismatchedInputException
import play.api.libs.json.{JsValue, Json}
import scalaj.http.Http

object WikipediaManager {
  def returnLinkWithArticle(parsedResponse: JsValue): (String, String, String, String) = {
    val link = parsedResponse \\ "page"
    val article = parsedResponse \\ "extract"
    (Configuration.WIKIPEDIA_OK, link.toList.mkString, article.mkString, parsedResponse.toString())
  }

  def processWikipediaResponse(noun: String, response: String): (String, String, String, String) = {
    try {
      val parsedResponse = Json.parse(response)
      val articleType = parsedResponse \\ "type"

      if(articleType.head.toString().equals(Configuration.DISAMBIGUATION)) (Configuration.WIKIPEDIA_ERROR, s"the word $noun is not unequivocally", s"the word $noun is not unequivocally", s"the word $noun is not unequivocally")
      else returnLinkWithArticle(parsedResponse)

    } catch {
      case _: MismatchedInputException => (Configuration.WIKIPEDIA_ERROR, s"the word $noun is not unequivocally", s"no wikipedia article for $noun", s"no wikipedia article for $noun")
    }
  }

  def apply(noun: String): (String, String, String, String) = {
    val wikipediaResponse = Http(s"${Configuration.WIKIPEDIA_URL}${noun.capitalize}").asString.body
    processWikipediaResponse(noun, wikipediaResponse)
  }

}
