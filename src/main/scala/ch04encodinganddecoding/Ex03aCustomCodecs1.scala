package ch04encodinganddecoding

import hutil.stringformat._

import scala.util.chaining._

import io.circe.parser.decode
import io.circe.syntax._
import io.circe.{Decoder, Encoder, HCursor, Json}

object Ex03aCustomCodecs1 extends App {

  dash80.green.println()

  class Thing(val foo: String, val bar: Int) {

    override def toString: String =
      s"Thing(foo=$foo, bar=$bar)"
  }

  implicit val encodeFoo: Encoder[Thing] = new Encoder[Thing] {

    final def apply(a: Thing): Json = Json.obj(
      ("foo", Json.fromString(a.foo)),
      ("bar", Json.fromInt(a.bar))
    )
  }
  // encodeFoo: io.circe.Encoder[Thing] = $anon$1@210da895

  implicit val decodeFoo: Decoder[Thing] = new Decoder[Thing] {

    final def apply(c: HCursor): Decoder.Result[Thing] =
      for {
        foo <- c.downField("foo").as[String]
        bar <- c.downField("bar").as[Int]
      } yield {
        new Thing(foo, bar)
      }
  }
  // decodeFoo: io.circe.Decoder[Thing] = $anon$1@74145742

  val json = new Thing("foo", 42).asJson tap println
  json.as[Thing] tap println
  decode(json.noSpaces) tap println

  dash80.green.println()
}
