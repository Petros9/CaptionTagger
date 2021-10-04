import org.scalatest.FunSuite

class YoutubeRequestTest extends FunSuite {

  test("Wrong youtube token") {

    os.write.append(os.Path(Configuration.LOGS_PATH) / "logs.txt", "") // makes sure whether logs file exists
    os.remove(os.Path(Configuration.LOGS_PATH) / "logs.txt") // deletes the file

    EntryFileOpener(Configuration.ENTRY_FILES_PATH+"test_file.txt") // contains only qwerty string
    val logs = os.read(os.Path(Configuration.LOGS_PATH) / "logs.txt")
    assert(logs.equals("No Youtube video for token: qwerty\n")) // checks whether the exception was reported in logs
  }
}
