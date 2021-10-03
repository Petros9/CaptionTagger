import com.fasterxml.jackson.databind.exc.MismatchedInputException
import play.api.libs.json._
import scalaj.http.Http

object CaptionManager {

  def isNoun(caption: String): List[String] = {
    caption.split(' ').filter(word => {
      if(word.endsWith("ity") || word.endsWith("ism") || word.endsWith("ion"))  true
      else if(word.startsWith("*") && !word.endsWith("est") && !word.endsWith("ly") && !word.endsWith("ed") && !word.equals("*next") && !word.equals("*same")) true
      else false
    }).toList
  }

  def selectNouns(response: String): List[String] = {
    val captions = Json.parse(response) \\ "utf8"
    val mappedCaptions = captions.map(elem => elem.toString().replace(raw"\n", " ").replace("\"", ""))
    val potentialNoun = mappedCaptions.map(elem => elem
      .replace(",", "").replace(".","").replace(" a ", "*").replace("the ", "*"))

    potentialNoun.map(isNoun).filter(elem => elem.nonEmpty).flatten.distinct.map(elem => elem.replace("*", "")).toList
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
    println(noun.capitalize)
    val wikipediaResponse = Http(s"${Configuration.WIKIPEDIA_URL}${noun.capitalize}").asString.body
    processWikipediaResponse(wikipediaResponse)
  }

  def apply(token: String): Unit = {
    val captionNouns = makeRequestAndManageResponse(token)
    captionNouns.foreach(findWikipediaArticle)
    // robienie wikipedii
  }
}
