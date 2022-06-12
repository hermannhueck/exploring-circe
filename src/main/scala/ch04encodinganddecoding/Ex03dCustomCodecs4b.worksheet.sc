import scala.util.chaining._

import hutil.stringformat._
import io.circe._
import io.circe.generic.extras._
import io.circe.syntax._

s"$dash10 Custom key mappings via annotations $dash10".magenta.println()

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
