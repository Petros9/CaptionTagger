object ExceptionLogger {

  def apply(message: String): Unit = {
    os.write(os.Path(Configuration.LOGS_PATH) / "logs.tx", message)
  }
}
