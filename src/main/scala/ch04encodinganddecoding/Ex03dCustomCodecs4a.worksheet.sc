import scala.util.chaining._

import hutil.stringformat._
import io.circe._
import io.circe.generic.extras._
import io.circe.syntax._

s"$dash10 Custom key mappings via annotations $dash10".magenta.println()

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
