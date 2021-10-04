import org.scalatest.FunSuite

class WikipediaRequestTest extends FunSuite  {
  test("No wikipedia article") {

    os.write.append(os.Path(Configuration.LOGS_PATH) / "logs.txt", "") // makes sure whether logs file exists
    os.remove(os.Path(Configuration.LOGS_PATH) / "logs.txt") // deletes the file


    ResultFileCreator("", "", "", List("ggf")) // word without article/no sense in english
    val logs = os.read(os.Path(Configuration.LOGS_PATH) / "logs.txt")
    assert(logs.equals("no wikipedia article for ggf\n")) // checks whether the exception was reported in logs
  }

  test("Word's ambiguity") {

    os.write.append(os.Path(Configuration.LOGS_PATH) / "logs.txt", "") // makes sure whether logs file exists
    os.remove(os.Path(Configuration.LOGS_PATH) / "logs.txt") // deletes the file


    ResultFileCreator("", "", "", List("great"))
    val logs = os.read(os.Path(Configuration.LOGS_PATH) / "logs.txt")
    assert(logs.equals("the word great is not unequivocally\n")) // checks whether the exception was reported in logs
  }
}
