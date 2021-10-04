object NounChecker {

  def markPotentialNouns(wordList: List[String]): List[String] = {
    wordList.map(elem => elem
      .replace(",", "")
      .replace(".","")
      .replace(" a ", s" ${Configuration.NOUN_POINTER}")
      .replace(" the ", s" ${Configuration.NOUN_POINTER}")
      .replace(" my ", s" ${Configuration.NOUN_POINTER}")
      .replace(" your ", s" ${Configuration.NOUN_POINTER}")
      .replace(" his ", s" ${Configuration.NOUN_POINTER}")
      .replace(" her ", s" ${Configuration.NOUN_POINTER}")
      .replace(" our ", s" ${Configuration.NOUN_POINTER}")
      .replace(" their ", s" ${Configuration.NOUN_POINTER}"))
  }

  def filterNouns(words: String): List[String] = {
    words.split(' ').filter(word => {
      if(word.endsWith("ity") || word.endsWith("ism") || word.endsWith("ion"))  true
      else if(word.startsWith(Configuration.NOUN_POINTER) && !word.endsWith("est") && !word.endsWith("ly") && !word.endsWith("ed") && !word.equals(s"${Configuration.NOUN_POINTER}next") && !word.equals(s"${Configuration.NOUN_POINTER}same")) true
      else false
    }).toList
  }
}
