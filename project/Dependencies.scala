import sbt._

object Dependencies {

  val collectionCompatVersion = "2.1.6"
  val shapelessVersion        = "2.3.3"
  val catsVersion             = "2.0.0"
  val jawnVersion             = "1.0.0"
  val circeVersion            = "0.13.0"
  val scalaTestVersion        = "3.1.2"
  val scalaCheckVersion       = "1.14.3"

  val collectionCompat   = "org.scala-lang.modules" %% "scala-collection-compat" % collectionCompatVersion
  val shapeless          = "com.chuusai"            %% "shapeless"               % shapelessVersion
  val catsCore           = "org.typelevel"          %% "jawn-parser"             % catsVersion
  val catsEffect         = "org.typelevel"          %% "jawn-ast"                % catsVersion
  val jawnParser         = "org.typelevel"          %% "jawn-parser"             % jawnVersion
  val jawnAst            = "org.typelevel"          %% "jawn-ast"                % jawnVersion
  val circeCore          = "io.circe"               %% "circe-core"              % circeVersion
  val circeParser        = "io.circe"               %% "circe-parser"            % circeVersion
  val circeGeneric       = "io.circe"               %% "circe-generic"           % circeVersion
  val circeGenericExtras = "io.circe"               %% "circe-generic-extras"    % circeVersion
  val circeShapes        = "io.circe"               %% "circe-shapes"            % circeVersion
  val circeOptics        = "io.circe"               %% "circe-optics"            % circeVersion
  val scalaTest          = "org.scalatest"          %% "scalatest"               % scalaTestVersion
  val scalaCheck         = "org.scalacheck"         %% "scalacheck"              % scalaCheckVersion
}
