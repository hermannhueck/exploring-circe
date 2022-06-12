package ch04encodinganddecoding

import scala.util.chaining._

import hutil.stringformat._
import io.circe._
import io.circe.generic.extras._
import io.circe.syntax._

object Ex03dCustomCodecs4 extends App {

  dash80.green.println()

  s"$dash10 Custom key mappings via annotations $dash10".magenta.println()

  """|
     |It’s often necessary to work with keys in your JSON objects
     |that aren’t idiomatic case class member names in Scala.
     |While the standard generic derivation doesn’t support this use case,
     |the experimental circe-generic-extras module does provide two ways
     |to transform your case class member names during encoding and decoding.
     |""".stripMargin pipe println

  object withSnakeCase {

    s"$dash10 Configuration.default.withSnakeCaseMemberNames $dash10".magenta.println()

    """|In many cases the transformation is as simple as going from camel case to snake case,
       |in which case all you need is a custom implicit configuration:
       |""".stripMargin pipe println

    implicit val config: Configuration =
      Configuration.default.withSnakeCaseMemberNames

    @ConfiguredJsonCodec case class User(firstName: String, lastName: String)
    // defined class User
    // defined object User

    val json = User("Foo", "McBar").asJson tap println
    // res1: io.circe.Json =
    // {
    //   "first_name" : "Foo",
    //   "last_name" : "McBar"
    // }
    json.as[User] tap println
    // res0: io.circe.Decoder.Result[Bar] = Right(User(Foo,McBar))
  }
  withSnakeCase

  object withCustomConfig {

    s"$dash10 explicit member name transformation $dash10".magenta.println()

    """|In other cases you may need more complex mappings. These can be provided as a function:
       |""".stripMargin pipe println

    implicit val config: Configuration =
      Configuration
        .default
        .copy(
          transformMemberNames = {
            case "i"   => "my-int"
            case other => other
          }
        )

    @ConfiguredJsonCodec case class Bar(i: Int, s: String)

    val json = Bar(13, "Qux").asJson tap println
    // res2: io.circe.Json =
    // {
    //   "my-int" : 13,
    //   "s" : "Qux"
    // }
    json.as[Bar] tap println
    // res0: io.circe.Decoder.Result[Bar] = Right(Bar(13,Qux))
  }
  withCustomConfig

  object withConfigByJsonKeyAnnotation {

    s"$dash10 explicit member name transformation (using @JsonKey annotation) $dash10".magenta.println()

    """|Since this is a common use case, we also support for mapping member names via an annotation:
       |""".stripMargin pipe println

    implicit val config: Configuration =
      Configuration.default

    @ConfiguredJsonCodec case class Bar(@JsonKey("my-int") i: Int, s: String)

    val json = Bar(13, "Qux").asJson tap println
    // res3: io.circe.Json =
    // {
    //   "my-int" : 13,
    //   "s" : "Qux"
    // }
    json.as[Bar] tap println
    // res0: io.circe.Decoder.Result[Bar] = Right(Bar(13,Qux))
  }
  withConfigByJsonKeyAnnotation

  object withForProductN {

    s"$dash10 using forProductN $dash10".magenta.println()

    """|It’s worth noting that if you don’t want to use the experimental generic-extras module,
       |the completely unmagical forProductN version isn’t really that much of a burden:
       |""".stripMargin pipe println

    case class User(firstName: String, lastName: String)

    case class Bar(i: Int, s: String)

    implicit val encodeUser: Encoder[User] =
      Encoder
        .forProduct2("first_name", "last_name")(u => (u.firstName, u.lastName))
    // encodeUser: io.circe.Encoder[User] = io.circe.ProductEncoders$$anon$2@5aea35ad

    implicit val encodeBar: Encoder[Bar] =
      Encoder
        .forProduct2("my-int", "s")(b => (b.i, b.s))
    // encodeBar: io.circe.Encoder[Bar] = io.circe.ProductEncoders$$anon$2@455797ec

    User("Foo", "McBar").asJson tap println
    // res4: io.circe.Json =
    // {
    //   "first_name" : "Foo",
    //   "last_name" : "McBar"
    // }

    Bar(13, "Qux").asJson tap println
    // res5: io.circe.Json =
    // {
    //   "my-int" : 13,
    //   "s" : "Qux"
    // }

    """|
       |While this version does involve a bit of boilerplate, it only requires circe-core,
       |and may have slightly better runtime performance in some cases.""".stripMargin pipe println
  }
  withForProductN

  dash80.green.println()
}
