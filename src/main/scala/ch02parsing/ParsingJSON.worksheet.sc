import io.circe._
import io.circe.parser._

val rawJson: String =
  """|{
     |  "foo": "bar",
     |  "baz": 123,
     |  "list of stuff": [ 4, 5, 6 ]
     |}""".stripMargin

val parseResult =
  parse(rawJson)

val badJson: String = "yolo"

val parseResult2 =
  parse(badJson)

parse(rawJson) match {
  case Left(failure) => println(s"Invalid JSON :(  $failure")
  case Right(json)   => println(s"Yay, got some JSON: ${json.noSpaces}")
}

val json: Json =
  parse(rawJson).getOrElse(Json.Null)

val json2: Json =
  parse(rawJson).fold(_ => Json.Null, identity)
