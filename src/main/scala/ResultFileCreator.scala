import play.api.libs.json.Json

object ResultFileCreator {

  def saveResults(token: String, rawCaption: String, mappedCaption: String, link: String, article: String, rawArticle: String): Unit = {
    val result = Json.obj(
      "token" -> token,
      "raw_captions" -> Json.stringify(Json.parse(rawCaption)),
      "plain_captions" -> mappedCaption,
      "raw_article" -> rawArticle,
      "article" -> article,
      "link" -> link
    )
    os.write.append(os.Path(Configuration.RESULT_PATH) / "result.json", result.toString()+",")
  }

  def apply(token: String, rawCaption: String, mappedCaption: String, nounList: List[String]): Unit = {
    os.remove(os.Path(Configuration.RESULT_PATH) / "result.json")
    nounList.foreach(noun => {
      val result = WikipediaManager(noun)
      result match {
        case (Configuration.WIKIPEDIA_ERROR, message, _, _) => ExceptionLogger(message)
        case (Configuration.WIKIPEDIA_OK, _, _, _) => saveResults(token, rawCaption, mappedCaption, result._2, result._3, result._4)
        case _ => ExceptionLogger("Unknown result")
      }
    })
  }

}
