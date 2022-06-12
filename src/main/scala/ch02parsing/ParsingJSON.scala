package ch02parsing

import scala.util.chaining._

import hutil.stringformat._
import io.circe._
import io.circe.parser._

object ParsingJSON extends App {

  dash80.green.println()

  val rawJson: String =
    """|{
       |  "foo": "bar",
       |  "baz": 123,
       |  "list of stuff": [ 4, 5, 6 ]
       |}""".stripMargin
  "----- rawJson =" pipe println
  rawJson pipe println

  val parseResult: Either[Error, Json] = parse(rawJson)
  "\n----- parseResult =" pipe println
  parseResult pipe println

  val badJson: String = "yolo"
  "\n----- badJson =" pipe println
  badJson pipe println

  val parseResult2: Either[Error, Json] = parse(badJson)
  "\n----- parseResult2 =" pipe println
  parseResult2 pipe println

  "\n----- pattern match:" pipe println
  parse(rawJson) match {
    case Left(failure) => println(s"Invalid JSON :(  $failure")
    case Right(json)   => println(s"Yay, got some JSON: ${json.noSpaces}")
  }

  "\n----- Either#getOrElse:" pipe println
  val json: Json = parse(rawJson).getOrElse(Json.Null)
  json.noSpaces pipe println

  "\n----- Either#fold:" pipe println
  val json2: Json = parse(rawJson).fold(_ => Json.Null, identity)
  json2.noSpaces pipe println

  dash80.green.println()
}
