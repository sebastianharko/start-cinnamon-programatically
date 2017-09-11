name := "minimal-cluster"

version := "1.0"

scalaVersion := "2.12.3"

libraryDependencies += "com.typesafe.akka" %% "akka-cluster" % "2.5.0"

libraryDependencies += "com.lightbend.cinnamon" %% "cinnamon-chmetrics" % "2.6.0-SNAPSHOT"

libraryDependencies += "com.lightbend.cinnamon" %% "cinnamon-chmetrics-statsd-reporter" % "2.6.0-SNAPSHOT"

libraryDependencies += "com.lightbend.cinnamon" %% "cinnamon-akka" % "2.6.0-SNAPSHOT"

