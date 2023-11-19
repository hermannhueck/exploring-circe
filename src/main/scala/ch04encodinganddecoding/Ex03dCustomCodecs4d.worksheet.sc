import io.circe._
import io.circe.syntax._

// Custom key mappings via annotations

// using forProductN

// It’s worth noting that if you don’t want to use the experimental generic-extras module,
// the completely unmagical forProductN version isn’t really that much of a burden:

case class User(firstName: String, lastName: String)

case class Bar(i: Int, s: String)

implicit val encodeUser: Encoder[User] =
  Encoder
    .forProduct2("first_name", "last_name")(u => (u.firstName, u.lastName))

implicit val encodeBar: Encoder[Bar] =
  Encoder
    .forProduct2("my-int", "s")(b => (b.i, b.s))

User("Foo", "McBar").asJson

Bar(13, "Qux").asJson

// While this version does involve a bit of boilerplate, it only requires circe-core,
// and may have slightly better runtime performance in some cases.""".stripMargin pipe println
