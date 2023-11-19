import io.circe._
import io.circe.generic.JsonCodec
import io.circe.generic.semiauto._
import io.circe.syntax._

// Implicit Codec

case class Foo1(a: Int, b: String, c: Boolean)

implicit val foo1Decoder: Decoder[Foo1] = deriveDecoder[Foo1]
implicit val foo1Encoder: Encoder[Foo1] = deriveEncoder[Foo1]

val json1 = Foo1(42, "foo1", true).asJson
json1.as[Foo1]

/*
  Or simply:
 */
case class Foo2(a: Int, b: String, c: Boolean)

implicit val foo2Decoder: Decoder[Foo2] = deriveDecoder
implicit val foo2Encoder: Encoder[Foo2] = deriveEncoder

val json2 =
  Foo2(42, "foo2", true).asJson
json2.as[Foo2]

// @JsonCodec

// For @JsonCodec enable Macro Paradise in Scala <= 2.12.x
// in Scala 2.13+ use scalacOption: -Ymacro-annotations
@JsonCodec case class Bar(i: Int, s: String)

val bar =
  Bar(13, "Qux").asJson
bar.as[Bar]

// forProductN helper methods

case class User(id: Long, firstName: String, lastName: String)

implicit val decodeUser: Decoder[User] =
  Decoder
    .forProduct3("id", "first_name", "last_name")(User.apply)

implicit val encodeUser: Encoder[User] =
  Encoder
    .forProduct3("id", "first_name", "last_name")(u => (u.id, u.firstName, u.lastName))

val userJson =
  User(42, "John", "Doe").asJson
userJson.as[User]
