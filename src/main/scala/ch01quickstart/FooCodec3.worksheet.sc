import io.circe._
import io.circe.generic.JsonCodec
import io.circe.parser._
import io.circe.syntax._

@JsonCodec sealed trait Foo
case class Bar(xs: Vector[String])        extends Foo
case class Qux(i: Int, d: Option[Double]) extends Foo

object Foo // see https://circe.github.io/circe/codecs/known-issues.html

val foo: Foo = Qux(13, Some(14.0))

val json: Json = foo.asJson

val jsonString: String = json.noSpaces

val parsedJson: Either[Error, Json] = parse(jsonString)

val decodedFoo1: Either[Error, Foo] = parsedJson.flatMap(_.as[Foo])

val decodedFoo2: Either[Error, Foo] =
  for {
    json <- parse(jsonString)
    foo  <- json.as[Foo]
  } yield foo

val decodedFoo3: Either[Error, Foo] = decode[Foo](jsonString)
