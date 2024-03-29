import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

sealed trait Foo
case class Bar(xs: Vector[String])        extends Foo
case class Qux(i: Int, d: Option[Double]) extends Foo

val foo: Foo = Qux(13, Some(14.0))

val json = foo.asJson

val foo2 = json.as[Foo]

val jsonString = json.noSpaces

val decodedFoo = decode[Foo](jsonString)
