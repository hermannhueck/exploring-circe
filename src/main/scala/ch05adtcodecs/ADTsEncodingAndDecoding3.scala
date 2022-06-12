package ch05adtcodecs

import scala.util.chaining._

import hutil.stringformat._
import io.circe.parser._
import io.circe.syntax._

object ADTsEncodingAndDecoding3 extends App {

  println()
  s"$dash10 A more generic solution (with circe-shapes) $dash10".magenta.println()

  """|
     |The generic-extras module provides a little more configurability in this respect. We can write the following, for example:
     |""".stripMargin pipe println

  sealed trait Event                         extends Product with Serializable
  final case class Foo(i: Int)               extends Event
  final case class Bar(s: String)            extends Event
  final case class Baz(c: Char)              extends Event
  final case class Qux(values: List[String]) extends Event

  import io.circe.generic.extras.auto._
  import io.circe.generic.extras.Configuration

  implicit val genDevConfig: Configuration =
    Configuration.default.withDiscriminator("what_am_i")

  dash80.println()
  decode[Event]("""{ "i": 1000, "what_am_i": "Foo" }""") pipe println
  // res0: Either[io.circe.Error,Event] = Right(Foo(1000))

  parse("""{ "i": 1000, "what_am_i": "Foo" }""").flatMap(_.as[Event]) pipe println
  // res1: Either[io.circe.Error,Event] = Right(Foo(1000))

  (Foo(100): Event).asJson.noSpaces pipe println
  // res2: String = {"i":100}
  dash80.println()

  """|
     |Instead of a wrapper object in the JSON we have an extra field that indicates the constructor.
     |This isn’t the default behavior since it has some weird corner cases (e.g. if one of our case classes
     |had a member named what_am_i), but in many cases it’s reasonable
     |and it’s been supported in generic-extras since that module was introduced.
     |""".stripMargin pipe println
}
