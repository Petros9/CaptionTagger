object NounChecker {

  def prepareWordList(wordList: List[String]): List[String] = {
    wordList.map(elem => elem
      .replace(",", "")
      .replace(".","")
      .replace(" a ", " *")
      .replace(" the ", " *")
      .replace(" my ", " *")
      .replace(" your ", " *")
      .replace(" his ", " *")
      .replace(" her ", " *")
      .replace(" our ", " *")
      .replace(" their ", " *"))
  }

  def isNoun(words: String): List[String] = {
    words.split(' ').filter(word => {
      if(word.endsWith("ity") || word.endsWith("ism") || word.endsWith("ion"))  true
      else if(word.startsWith("*") && !word.endsWith("est") && !word.endsWith("ly") && !word.endsWith("ed") && !word.equals("*next") && !word.equals("*same")) true
      else false
    }).toList
  }
}
