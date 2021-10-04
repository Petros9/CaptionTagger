object EntryFileReader {
  def apply(path: String): Unit = {
    os.read.lines.stream(os.Path(path)).foreach(line => CaptionManager(line))
  }
}
