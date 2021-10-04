import com.fasterxml.jackson.databind.exc.MismatchedInputException
import play.api.libs.json.Json
import scalaj.http.Http

object WikipediaManager {
  def processWikipediaResponse(noun: String, response: String): (String, String, String) = {
    try {
      val parsedResponse = Json.parse(response)
      val link = parsedResponse \\ "page"
      val article = parsedResponse \\ "extract"
      val articleType = parsedResponse \\ "type"

      if(articleType.head.toString().equals("\"disambiguation\"")) ("error", s"the word $noun is not unequivocally", s"the word $noun is not unequivocally")
      else (link.toString(), article.toString(), parsedResponse.toString())

    } catch {
      case _: MismatchedInputException => ("error", "no wikipedia article", "now wikipedia article")
    }
  }

  def apply(noun: String): (String, String, String) = {
    val wikipediaResponse = Http(s"${Configuration.WIKIPEDIA_URL}${noun.capitalize}").asString.body
    processWikipediaResponse(noun, wikipediaResponse)
  }

}
