import sbt._

object Dependencies {

  import Versions._

  val circeCore          = "io.circe" %% "circe-core"           % circeVersion
  val circeParser        = "io.circe" %% "circe-parser"         % circeVersion
  val circeGeneric       = "io.circe" %% "circe-generic"        % circeVersion
  val circeGenericExtras = "io.circe" %% "circe-generic-extras" % circeGenericExtrasVersion
  val circeShapes        = "io.circe" %% "circe-shapes"         % circeVersion
  val circeOptics        = "io.circe" %% "circe-optics"         % circeOpticsVersion
  val circeTesting       = "io.circe" %% "circe-testing"        % circeVersion

  // https://github.com/typelevel/kind-projector
  lazy val kindProjectorPlugin    = compilerPlugin(
    compilerPlugin("org.typelevel" % "kind-projector" % kindProjectorVersion cross CrossVersion.full)
  )
  // https://github.com/oleg-py/better-monadic-for
  lazy val betterMonadicForPlugin = compilerPlugin(
    compilerPlugin("com.olegpy" %% "better-monadic-for" % betterMonadicForVersion)
  )
}
