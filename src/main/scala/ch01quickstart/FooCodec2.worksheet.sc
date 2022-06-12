import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

sealed trait Foo
case class Bar(xs: Vector[String])        extends Foo
case class Qux(i: Int, d: Option[Double]) extends Foo

val foo: Foo = Qux(13, Some(14.0))

val json = foo.asJson

val jsonString = json.noSpaces

val parsedJson = parse(jsonString)

val decodedFoo1 = parsedJson.flatMap(_.as[Foo])

val decodedFoo2 =
  for {
    json <- parse(jsonString)
    foo  <- json.as[Foo]
  } yield foo

val decodedFoo3 = decode[Foo](jsonString)
