name := "CaptionTagger"

version := "0.1"

scalaVersion := "2.13.6"

val AkkaVersion = "2.6.16"
val AkkaHttpVersion = "10.2.6"
val PlayVersion = "2.9.2"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion
)
libraryDependencies += "com.typesafe.play" %% "play-json" % PlayVersion
libraryDependencies += "com.lihaoyi" %% "os-lib" % "0.7.8"