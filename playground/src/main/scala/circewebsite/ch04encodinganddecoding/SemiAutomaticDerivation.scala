package circewebsite.ch04encodinganddecoding

import scala.util.chaining._
import util.formatting._

import io.circe._
import io.circe.generic.semiauto._
import io.circe.syntax._
import io.circe.generic.JsonCodec

object SemiAutomaticDerivation extends util.App {

  printTextInLine("Implicit Codec")

  case class Foo(a: Int, b: String, c: Boolean)

  implicit val fooDecoder: Decoder[Foo] = deriveDecoder[Foo]
  implicit val fooEncoder: Encoder[Foo] = deriveEncoder[Foo]

  /*
  Or simply:
  implicit val fooDecoder: Decoder[Foo] = deriveDecoder
  implicit val fooEncoder: Encoder[Foo] = deriveEncoder
   */

  Foo(42, "foo", true).asJson pipe println

  printTextInLine("@JsonCodec")

  // For @JsonCodec enable Macro Paradise in Scala <= 2.12.x
  // in Scala 2.13+ use scalacOption: -Ymacro-annotations
  @JsonCodec case class Bar(i: Int, s: String)

  Bar(13, "Qux").asJson pipe println

  printTextInLine("forProductN helper methods")

  case class User(id: Long, firstName: String, lastName: String)

  implicit val decodeUser: Decoder[User] =
    Decoder.forProduct3("id", "first_name", "last_name")(User.apply)
  // decodeUser: io.circe.Decoder[User] = io.circe.ProductDecoders$$anon$3@1a3ea919

  implicit val encodeUser: Encoder[User] =
    Encoder.forProduct3("id", "first_name", "last_name")(u => (u.id, u.firstName, u.lastName))
  // encodeUser: io.circe.Encoder[User] = io.circe.ProductEncoders$$anon$3@1ad27753

  User(42, "John", "Doe").asJson pipe println
}
