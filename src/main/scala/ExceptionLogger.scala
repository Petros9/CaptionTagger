object ExceptionLogger {

  def apply(message: String): Unit = {
    os.write.append(os.Path(Configuration.LOGS_PATH) / "logs.txt", s"$message\n")
  }
}
