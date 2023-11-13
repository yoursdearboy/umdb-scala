name := """umdb"""
organization := "com.yoursdearboy"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "3.3.1"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.0" % Test

libraryDependencies += "org.postgresql" % "postgresql" % "42.6.0"

libraryDependencies ++= Seq(
  "org.playframework" %% "play-slick"            % "6.0.0-M2",
  "org.playframework" %% "play-slick-evolutions" % "6.0.0-M2"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.yoursdearboy.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.yoursdearboy.binders._"
