import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.6",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "lookout",
    libraryDependencies += "com.twitter" %% "finagle-http" % "18.5.0",
    libraryDependencies += "io.spray" %%  "spray-json" % "1.3.3",
    libraryDependencies += scalaTest % Test
  )
