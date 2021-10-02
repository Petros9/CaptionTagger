object File {
  def apply(path: String): Unit = {
    os.read.lines.stream(os.Path(path)).foreach(line => CaptionGetter(line))
  }
}
