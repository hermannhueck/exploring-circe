import scala.util.chaining._

import hutil.stringformat._
import io.circe._
import io.circe.generic.extras._
import io.circe.syntax._

s"$dash10 Custom key mappings via annotations $dash10".magenta.println()

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
