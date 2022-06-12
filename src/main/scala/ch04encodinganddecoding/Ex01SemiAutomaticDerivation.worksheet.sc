import scala.util.chaining._

import hutil.stringformat._
import io.circe._
import io.circe.generic.JsonCodec
import io.circe.generic.semiauto._
import io.circe.syntax._

s"$dash10 Implicit Codec $dash10".magenta.println()

case class Foo1(a: Int, b: String, c: Boolean)

implicit val foo1Decoder: Decoder[Foo1] = deriveDecoder[Foo1]
implicit val foo1Encoder: Encoder[Foo1] = deriveEncoder[Foo1]

val json1 = Foo1(42, "foo1", true).asJson tap println
json1.as[Foo1] tap println

/*
  Or simply:
 */
case class Foo2(a: Int, b: String, c: Boolean)

implicit val foo2Decoder: Decoder[Foo2] = deriveDecoder
implicit val foo2Encoder: Encoder[Foo2] = deriveEncoder

val json2 = Foo2(42, "foo2", true).asJson tap println
json2.as[Foo2] tap println

s"$dash10 @JsonCodec $dash10".magenta.println()

// For @JsonCodec enable Macro Paradise in Scala <= 2.12.x
// in Scala 2.13+ use scalacOption: -Ymacro-annotations
@JsonCodec case class Bar(i: Int, s: String)

val bar = Bar(13, "Qux").asJson tap println
bar.as[Bar] tap println

s"$dash10 forProductN helper methods $dash10".magenta.println()

case class User(id: Long, firstName: String, lastName: String)

implicit val decodeUser: Decoder[User] =
  Decoder.forProduct3("id", "first_name", "last_name")(User.apply)
// decodeUser: io.circe.Decoder[User] = io.circe.ProductDecoders$$anon$3@1a3ea919

implicit val encodeUser: Encoder[User] =
  Encoder.forProduct3("id", "first_name", "last_name")(u => (u.id, u.firstName, u.lastName))
// encodeUser: io.circe.Encoder[User] = io.circe.ProductEncoders$$anon$3@1ad27753

val user = User(42, "John", "Doe").asJson tap println
user.as[User] tap println
