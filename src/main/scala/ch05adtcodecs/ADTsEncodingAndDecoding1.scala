package ch05adtcodecs

import scala.util.chaining._

import cats.syntax.functor._
import hutil.stringformat._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import io.circe.{Decoder, Encoder}

object ADTsEncodingAndDecoding1 extends App {

  dash80.green.println()

  s"$dash10 ADTs encoding and decoding $dash10".magenta.println()

  """|
     |The most straightforward way to encode / decode ADTs is by using generic derivation for the case classes
     |but explicitly defined instances for the ADT type.
     |""".stripMargin pipe println

  import ch05adtcodecs.event._
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

  decode[Event]("""{ "i": 1000 }""") tap println
  // res0: Either[io.circe.Error,Event] = Right(Foo(1000))

  parse("""{ "i": 1000 }""").flatMap(_.as[Event]) tap println
  // res1: Either[io.circe.Error,Event] = Right(Foo(1000))

  (Foo(100): Event).asJson.noSpaces tap println
  // res2: String = {"i":100}

  dash80.green.println()
}
