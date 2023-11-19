import io.circe._
import io.circe.generic.extras._
import io.circe.syntax._

// Custom key mappings via annotations

// Configuration.default.withSnakeCaseMemberNames

// In many cases the transformation is as simple as going from camel case to snake case,
// in which case all you need is a custom implicit configuration:

implicit val config: Configuration =
  Configuration.default.withSnakeCaseMemberNames

@ConfiguredJsonCodec case class User(firstName: String, lastName: String)

val json = User("Foo", "McBar").asJson

json.as[User]
