import scala.util.chaining._

import hutil.stringformat._
import io.circe._
import io.circe.generic.extras._
import io.circe.syntax._

s"$dash10 Custom key mappings via annotations $dash10".magenta.println()

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
