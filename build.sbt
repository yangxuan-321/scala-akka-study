name := "scala-akka-study"
organization := "moda.sbt"
version := "0.1.0-SNAPSHOT"
scalaVersion := "2.13.1"


val akkaV         = "2.6.4"
val akkaHttpV     = "10.1.11"
val akkaHttpJsonV = "1.29.1"
val slickV        = "3.3.2"
val catsV         = "2.1.0"
val circeV        = "0.13.0"
val prometheusV   = "0.8.0"
val nettyV        = "4.1.38.Final"
val silencerV     = "1.6.0"

libraryDependencies += "com.typesafe.akka" %% "akka-actor-typed"    % akkaV
libraryDependencies += "com.typesafe.akka" %% "akka-stream"         % akkaV
libraryDependencies += "commons-io"        % "commons-io"           % "2.6"
libraryDependencies += "com.typesafe.akka" %% "akka-http"           % akkaHttpV