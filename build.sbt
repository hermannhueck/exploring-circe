import Dependencies._
import ScalacOptions._

val projectName        = "playground-circe"
val projectDescription = "My Circe Playground"
val projectVersion     = "0.1.0"

val scala212               = "2.12.11"
val scala213               = "2.13.2"
val supportedScalaVersions = List(scala212, scala213)

inThisBuild(
  Seq(
    version := projectVersion,
    scalaVersion := scala213,
    crossScalaVersions := supportedScalaVersions,
    publish / skip := true,
    libraryDependencies ++= Seq(
      collectionCompat,
      shapeless,
      scalaTest  % Test,
      scalaCheck % Test
    ),
    initialCommands :=
      s"""|
          |import scala.util.chaining._
          |import util.syntax.pipe._
          |import io.circe._
          |import io.circe.parser._
          |import io.circe.syntax._
          |println
          |""".stripMargin // initialize REPL
  )
)

lazy val root = (project in file("."))
  .aggregate(playground)
  .settings(
    name := projectName,
    description := projectDescription,
    crossScalaVersions := Seq.empty
  )

lazy val playground = (project in file("playground"))
  .dependsOn(compat213, util)
  .settings(
    name := "playground",
    description := projectDescription,
    libraryDependencies ++= Seq(
      circeCore,
      circeParser,
      circeGeneric,
      circeGenericExtras,
      circeShapes,
      circeOptics,
      jawnParser,
      jawnAst
    ) ++ {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, minor)) if minor >= 13 => Seq.empty
        // Macro paradise not needed in 2.13. Just use scalacOption -Ymacro-annotations. See project/ScalacOptions.scala
        case _ =>
          Seq(compilerPlugin("org.scalamacros" %% "paradise" % "2.1.1" cross CrossVersion.full))
      }
    },
    scalacOptions ++= scalacOptionsFor(scalaVersion.value),
    // suppress unused import warnings in the scala repl
    console / scalacOptions := removeScalacOptionXlintUnusedForConsoleFrom(scalacOptions.value)
  )

lazy val compat213 = (project in file("compat213"))
  .settings(
    name := "compat213",
    description := "Compat library providing features of Scala 2.13 backported to 2.12",
    scalacOptions ++= scalacOptionsFor(scalaVersion.value)
  )

lazy val util = (project in file("util"))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "util",
    description := "Utilities",
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "build",
    scalacOptions ++= scalacOptionsFor(scalaVersion.value)
  )

// https://github.com/typelevel/kind-projector
addCompilerPlugin("org.typelevel" % "kind-projector" % "0.11.0" cross CrossVersion.full)
// https://github.com/oleg-py/better-monadic-for
addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
