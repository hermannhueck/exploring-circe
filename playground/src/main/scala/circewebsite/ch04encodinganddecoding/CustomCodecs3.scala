package circewebsite.ch04encodinganddecoding

import scala.util.chaining._
import util.formatting._

import io.circe._
import io.circe.syntax._

object CustomCodecs3 extends util.App {

  println
  printTextInLine("Custom key types")

  """|
     |If you need to encode/decode Map[K, V] where K is not String (or Symbol, Int, Long, etc.),
     |you need to provide a KeyEncoder and/or KeyDecoder for your custom key type.
     |""".stripMargin pipe println

  case class Foo(value: String)

  implicit val fooKeyEncoder: KeyEncoder[Foo] = new KeyEncoder[Foo] {
    override def apply(foo: Foo): String = foo.value
  }
  // fooKeyEncoder: io.circe.KeyEncoder[Foo] = $anon$1@374349c7

  val map = Map[Foo, Int](
    Foo("hello") -> 123,
    Foo("world") -> 456
  )
  // map: scala.collection.immutable.Map[Foo,Int] = Map(Foo(hello) -> 123, Foo(world) -> 456)

  val json = map.asJson tap println
  // json: io.circe.Json =
  // {
  //   "hello" : 123,
  //   "world" : 456
  // }

  implicit val fooKeyDecoder: KeyDecoder[Foo] = new KeyDecoder[Foo] {
    override def apply(key: String): Option[Foo] = Some(Foo(key))
  }
  // fooKeyDecoder: io.circe.KeyDecoder[Foo] = $anon$1@22682c48

  println
  json.as[Map[Foo, Int]] pipe println
  // res0: io.circe.Decoder.Result[Map[Foo,Int]] = Right(Map(Foo(hello) -> 123, Foo(world) -> 456))
}
