
object ResultFileCreator {

  def showResults(token: String, rawCaption: List[String], mappedCaption: List[String], link: String, article: String, rawArticle: String): Unit = {
    println("###################")

    println(token)
    println(rawCaption)
    println(mappedCaption)
    println(article)
    println(rawArticle)
    println(link)

    println("###################")
  }

  def apply(token: String, rawCaption: List[String], mappedCaption: List[String], nounList: List[String]): Unit = {
    nounList.foreach(noun => {
      val result = WikipediaManager(noun)

      result match {
        case ("error", message, _) => println(message)
        case (_, _, _) => showResults(token, rawCaption, mappedCaption, result._1, result._2, result._3)
      }})
  }

}
