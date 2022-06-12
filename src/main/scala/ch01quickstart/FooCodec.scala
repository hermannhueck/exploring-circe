package ch01quickstart

import hutil.stringformat._

import scala.util.chaining._

import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

object FooCodec extends App {

  dash80.green.println()

  sealed trait Foo
  case class Bar(xs: Vector[String])        extends Foo
  case class Qux(i: Int, d: Option[Double]) extends Foo

  val foo: Foo = Qux(13, Some(14.0))
  s"foo:                              $foo" pipe println

  val json = foo.asJson
  s"foo encoded to Json:              $json" pipe println

  val foo2 = json.as[Foo]
  s"foo2 decoded from Json:           $foo2" pipe println

  val jsonString = json.noSpaces
  s"foo encoded to Json (w/o spaces): $jsonString" pipe println

  val decodedFoo = decode[Foo](jsonString)
  s"foo decoded from Json String:     $decodedFoo" pipe println

  dash80.green.println()
}
