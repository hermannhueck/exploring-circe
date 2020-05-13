package circewebsite.ch05adtcodecs

import scala.util.chaining._
import util.formatting._

import io.circe.{Decoder, Encoder}
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.parser._

object ADTsEncodingAndDecoding2 extends util.App {

  println
  printTextInLine("A more generic solution (with circe-shapes)")

  """|
     |As discussed on Gitter, we can avoid the fuss of writing out all the cases by using the circe-shapes module:
     |""".stripMargin pipe println

  sealed trait Event                         extends Product with Serializable
  final case class Foo(i: Int)               extends Event
  final case class Bar(s: String)            extends Event
  final case class Baz(c: Char)              extends Event
  final case class Qux(values: List[String]) extends Event

  // To suppress previously imported inplicit codecs.
  // import ADTsEncodingAndDecoding1.GenericDerivation.{decodeEvent => _, encodeEvent => _}

  object ShapesDerivation {

    import io.circe.shapes
    import shapeless.{Coproduct, Generic}

    implicit def encodeAdtNoDiscr[A, Repr <: Coproduct](
        implicit
        gen: Generic.Aux[A, Repr],
        encodeRepr: Encoder[Repr]
    ): Encoder[A] = encodeRepr.contramap(gen.to)

    implicit def decodeAdtNoDiscr[A, Repr <: Coproduct](
        implicit
        gen: Generic.Aux[A, Repr],
        decodeRepr: Decoder[Repr]
    ): Decoder[A] = decodeRepr.map(gen.from)

  }

  import ShapesDerivation._

  printLine(80)
  decode[Event]("""{ "i": 1000 }""") pipe println
  // res0: Either[io.circe.Error,Event] = Right(Foo(1000))

  parse("""{ "i": 1000 }""").flatMap(_.as[Event]) pipe println
  // res1: Either[io.circe.Error,Event] = Right(Foo(1000))

  (Foo(100): Event).asJson.noSpaces pipe println
  // res2: String = {"i":100}
  printLine(80)

  """|
     |This will work for any ADT anywhere that encodeAdtNoDiscr and decodeAdtNoDiscr are in scope.
     |If we wanted it to be more limited, we could replace the generic A with our ADT types in those definitions,
     |or we could make the definitions non-implicit and define implicit instances explicitly for the ADTs
     |we want encoded this way.
     |
     |The main drawback of this approach (apart from the extra circe-shapes dependency) is
     |that the constructors will be tried in alphabetical order, which may not be what we want
     |if we have ambiguous case classes (where the member names and types are the same).
     |""".stripMargin pipe println
}
