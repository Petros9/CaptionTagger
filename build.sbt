name := "CaptionTagger"

version := "0.1"

scalaVersion := "2.13.6"

val AkkaVersion = "2.5.20"
val AkkaHttpVersion = "10.1.7"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion
)
libraryDependencies += "com.lihaoyi" %% "os-lib" % "0.7.8"