
import java.nio.file.NoSuchFileException
import java.util.Scanner

object CaptionTagger extends App {

  val scanner = new Scanner(System.in)
  try{
    EntryFileOpener(Configuration.ENTRY_FILES_PATH+scanner.nextLine())
  } catch {
    case _:NoSuchFileException => println("No file with that name was provided")
  }
}
