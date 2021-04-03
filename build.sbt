name := "imdb-stream"

version := "1.0"

scalaVersion := "2.13.1"

lazy val akkaVersion = "2.5.31"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.1.0" % Test,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
  "com.lightbend.akka" %% "akka-stream-alpakka-csv" % "2.0.2",
  "com.lightbend.akka" %% "akka-stream-alpakka-file" % "2.0.2",
  "com.chuusai" %% "shapeless" % "2.3.3"
)