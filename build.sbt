scalaVersion := "2.11.7"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4-SNAPSHOT"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.1.0"

libraryDependencies += "org.scalaz.stream" %% "scalaz-stream" % "0.7.2a"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

libraryDependencies += "com.chuusai" %% "shapeless" % "2.1.0"

libraryDependencies += "org.typelevel" %% "scodec-core" % "1.3.0"

libraryDependencies += "joda-time" % "joda-time" % "2.7"

libraryDependencies += "com.softwaremill.quicklens" %% "quicklens" % "1.4.2"

libraryDependencies += "com.typesafe.akka" % "akka-stream-experimental_2.11" % "2.0-M1"

autoCompilerPlugins := true

addCompilerPlugin(
  "org.scala-lang.plugins" % "scala-continuations-plugin_2.11.6" % "1.0.2")

libraryDependencies +=
  "org.scala-lang.plugins" %% "scala-continuations-library" % "1.0.2"

scalacOptions += "-P:continuations:enable"

