import org.scalatest.FunSuite

class NounCheckerTest extends FunSuite{
  test("Filter noun-like words") {
    val words = "intuition assertion assert first identity edit start *man *best *cat"
    val nounList = NounChecker.isNoun(words)
    assert(nounList.equals(List("intuition", "assertion", "identity", "*man", "*cat"))) // checks whether the exception was reported in logs
  }

  test("Mark potential nouns"){
    val wordList = List("jealous my cat the man a problem first last")
    val wordListWithPotentialNounsMarked = NounChecker.prepareWordList(wordList)
    assert(wordListWithPotentialNounsMarked.equals(List("jealous *cat *man *problem first last")))
  }
}
