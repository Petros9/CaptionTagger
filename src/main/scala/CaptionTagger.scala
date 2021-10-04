
import java.nio.file.NoSuchFileException
import java.util.Scanner

object CaptionTagger extends App {

  os.write.append(os.Path(Configuration.LOGS_PATH) / "logs.txt", "")
  os.remove(os.Path(Configuration.LOGS_PATH) / "logs.txt")

  val scanner = new Scanner(System.in)
  try{
    EntryFileReader(Configuration.ENTRY_FILES_PATH+scanner.nextLine())
  } catch {
    case _:NoSuchFileException => println("No file with that name was provided")
  }
}
