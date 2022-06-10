import Versions._
import Dependencies._
import ScalacOptions._

val projectName        = "playground-circe"
val projectDescription = "My Circe Playground"

ThisBuild / fork := true
ThisBuild / turbo := true                  // default: false
ThisBuild / includePluginResolvers := true // default: false
Global / onChangedBuildSource := ReloadOnSourceChanges

inThisBuild(
  Seq(
    version := projectVersion,
    scalaVersion := scala2Version,
    publish / skip := true,
    scalacOptions ++= defaultScalacOptions,
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    scalafixDependencies ++= Seq("com.github.liancheng" %% "organize-imports" % scalafixOrganizeImportsVersion),
    Test / parallelExecution := false,
    // run 100 tests for each property // -s = -minSuccessfulTests
    Test / testOptions += Tests.Argument(TestFrameworks.ScalaCheck, "-s", "100"),
    initialCommands :=
      s"""|
          |import scala.util.chaining._
          |import scala.concurrent.duration._
          |import io.circe._
          |import io.circe.parser._
          |import io.circe.syntax._
          |println()
          |""".stripMargin // initialize REPL
  )
)

lazy val root = (project in file("."))
  .settings(
    name := projectName,
    description := projectDescription,
    Compile / console / scalacOptions := consoleScalacOptions,
    libraryDependencies ++= Seq(
      circeCore,
      circeParser,
      circeGeneric,
      circeGenericExtras,
      circeShapes,
      circeOptics,
      jawnParser,
      jawnAst,
      kindProjectorPlugin,
      betterMonadicForPlugin,
      collectionCompat,
      shapeless,
      munit      % Test,
      scalaTest  % Test,
      scalaCheck % Test
    )
  )
