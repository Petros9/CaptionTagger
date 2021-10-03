import play.api.libs.json._
import scalaj.http.Http

object CaptionManager {

  def isNoun(caption: String): List[String] = {
    caption.split(' ').filter(word => {
      if(word.endsWith("ity") || word.endsWith("ism") || word.endsWith("ion"))  true
      else if(word.startsWith("*") && !word.endsWith("est") && !word.endsWith("ly") && !word.endsWith("ed") && !word.equals("*next")) true
      else false
    }).toList
  }

  def selectNouns(response: String): Unit = {
    val captions = Json.parse(response) \\ "utf8"
    val mappedCaptions = captions.map(elem => elem.toString().replace(raw"\n", " ").replace("\"", ""))
    val potentialNoun = mappedCaptions.map(elem => elem
      .replace(",", "").replace(".","").replace(" a ", "*").replace("the ", "*"))

    potentialNoun.map(isNoun).filter(elem => !elem.isEmpty).flatten.distinct.map(elem => elem.replace("*", ""))
  }

  def getResponse(token: String): Unit = {
    val captionResponse = Http(s"${Configuration.REQUEST_URL}&v=$token").asString.body
    selectNouns(captionResponse)
  }

  def apply(token: String): Unit = {
    getResponse(token)
  }
}
