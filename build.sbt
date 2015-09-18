name := """bitblog"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaCore,
  filters,
  "mysql" % "mysql-connector-java" % "5.1.27",
  "com.typesafe.play" %% "play-mailer" % "2.4.1",
  "org.apache.lucene" % "lucene-core" % "5.3.0"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
