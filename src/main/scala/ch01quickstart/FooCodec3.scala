package ch01quickstart

import scala.util.chaining._

import hutil.stringformat._
import io.circe._
import io.circe.generic.JsonCodec
import io.circe.parser._
import io.circe.syntax._

object FooCodec3 extends App {

  dash80.green.println()

  @JsonCodec sealed trait Foo
  case class Bar(xs: Vector[String])        extends Foo
  case class Qux(i: Int, d: Option[Double]) extends Foo

  val foo: Foo = Qux(13, Some(14.0))
  s"foo:                                     $foo" pipe println

  val json: Json = foo.asJson
  s"foo encoded to Json:                     $json" pipe println

  val jsonString: String = json.noSpaces
  s"foo encoded to Json String (w/o spaces): $jsonString" pipe println

  val parsedJson: Either[Error, Json] = parse(jsonString)
  s"parsedJson parsed from Json String:      $parsedJson" pipe println

  val decodedFoo1: Either[Error, Foo] = parsedJson.flatMap(_.as[Foo])
  s"decodedFoo1 decoded from Json:            $decodedFoo1" pipe println

  val decodedFoo2: Either[Error, Foo] =
    for {
      json <- parse(jsonString)
      foo  <- json.as[Foo]
    } yield foo
  s"decodedFoo2 decoded from Json String:     $decodedFoo2" pipe println

  val decodedFoo3: Either[Error, Foo] = decode[Foo](jsonString)
  s"decodedFoo3 decoded from Json String:     $decodedFoo3" pipe println

  dash80.green.println()
}
