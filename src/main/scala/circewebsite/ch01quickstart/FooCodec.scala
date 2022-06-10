package circewebsite.ch01quickstart

import scala.util.chaining._

import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

object FooCodec extends App {

  sealed trait Foo
  case class Bar(xs: Vector[String])        extends Foo
  case class Qux(i: Int, d: Option[Double]) extends Foo

  val foo: Foo = Qux(13, Some(14.0))
  s"foo:\n$foo\n" pipe println

  val json = foo.asJson
  s"foo encoded to Json:\n$json\n" pipe println

  val jsonString = json.noSpaces
  s"foo encoded to Json (w/o spaces):\n$jsonString\n" pipe println

  val decodedFoo = decode[Foo](jsonString)
  s"foo decoded from Json:\n$decodedFoo" pipe println
}
