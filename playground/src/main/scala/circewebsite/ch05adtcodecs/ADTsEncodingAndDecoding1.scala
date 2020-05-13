package circewebsite.ch05adtcodecs

import scala.util.chaining._
import util.formatting._

import cats.syntax.functor._
import io.circe.{Decoder, Encoder}
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.parser._

object ADTsEncodingAndDecoding1 extends util.App {

  println
  printTextInLine("ADTs encoding and decoding")

  """|
     |The most straightforward way to encode / decode ADTs is by using generic derivation for the case classes
     |but explicitly defined instances for the ADT type.
     |""".stripMargin pipe println

  sealed trait Event                         extends Product with Serializable
  final case class Foo(i: Int)               extends Event
  final case class Bar(s: String)            extends Event
  final case class Baz(c: Char)              extends Event
  final case class Qux(values: List[String]) extends Event

  object GenericDerivation {

    implicit val encodeEvent: Encoder[Event] = Encoder.instance {
      case foo @ Foo(_) => foo.asJson
      case bar @ Bar(_) => bar.asJson
      case baz @ Baz(_) => baz.asJson
      case qux @ Qux(_) => qux.asJson
    }

    implicit val decodeEvent: Decoder[Event] =
      List[Decoder[Event]](
        Decoder[Foo].widen,
        Decoder[Bar].widen,
        Decoder[Baz].widen,
        Decoder[Qux].widen
      ).reduceLeft(_ or _)
  }

  import GenericDerivation._

  decode[Event]("""{ "i": 1000 }""") pipe println
  // res0: Either[io.circe.Error,Event] = Right(Foo(1000))

  parse("""{ "i": 1000 }""").flatMap(_.as[Event]) pipe println
  // res1: Either[io.circe.Error,Event] = Right(Foo(1000))

  (Foo(100): Event).asJson.noSpaces pipe println
  // res2: String = {"i":100}
}
