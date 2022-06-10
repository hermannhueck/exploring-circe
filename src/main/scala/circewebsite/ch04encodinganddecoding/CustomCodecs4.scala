package circewebsite.ch04encodinganddecoding

import scala.util.chaining._

import hutil.stringformat._
import io.circe._
import io.circe.generic.extras._
import io.circe.syntax._

object CustomCodecs4 extends App {

  println()
  s"$dash10 Custom key mappings via annotations $dash10".magenta.println()

  """|
     |It’s often necessary to work with keys in your JSON objects
     |that aren’t idiomatic case class member names in Scala.
     |While the standard generic derivation doesn’t support this use case,
     |the experimental circe-generic-extras module does provide two ways
     |to transform your case class member names during encoding and decoding.
     |""".stripMargin pipe println

  println()

  """|In many cases the transformation is as simple as going from camel case to snake case,
     |in which case all you need is a custom implicit configuration:
     |""".stripMargin pipe println

  object withConfig {

    implicit val config: Configuration =
      Configuration.default.withSnakeCaseMemberNames

    @ConfiguredJsonCodec case class User(firstName: String, lastName: String)
    // defined class User
    // defined object User

    User("Foo", "McBar").asJson pipe println
    // res1: io.circe.Json =
    // {
    //   "first_name" : "Foo",
    //   "last_name" : "McBar"
    // }
  }
  withConfig

  println()
  """|In other cases you may need more complex mappings. These can be provided as a function:
     |""".stripMargin pipe println

  object withCustomConfig {

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

    Bar(13, "Qux").asJson pipe println
    // res2: io.circe.Json =
    // {
    //   "my-int" : 13,
    //   "s" : "Qux"
    // }
  }
  withCustomConfig

  println()
  """|Since this is a common use case, we also support for mapping member names via an annotation:
     |""".stripMargin pipe println

  object withConfigByAnnotation {

    implicit val config: Configuration =
      Configuration.default

    @ConfiguredJsonCodec case class Bar(@JsonKey("my-int") i: Int, s: String)

    Bar(13, "Qux").asJson pipe println
    // res3: io.circe.Json =
    // {
    //   "my-int" : 13,
    //   "s" : "Qux"
    // }
  }
  withConfigByAnnotation

  println()
  """|It’s worth noting that if you don’t want to use the experimental generic-extras module,
     |the completely unmagical forProductN version isn’t really that much of a burden:
     |""".stripMargin pipe println

  object withForProductN {

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

    User("Foo", "McBar").asJson pipe println
    // res4: io.circe.Json =
    // {
    //   "first_name" : "Foo",
    //   "last_name" : "McBar"
    // }

    Bar(13, "Qux").asJson pipe println
    // res5: io.circe.Json =
    // {
    //   "my-int" : 13,
    //   "s" : "Qux"
    // }

  }
  withForProductN

  """|
     |While this version does involve a bit of boilerplate, it only requires circe-core,
     |and may have slightly better runtime performance in some cases.""".stripMargin pipe println
}
