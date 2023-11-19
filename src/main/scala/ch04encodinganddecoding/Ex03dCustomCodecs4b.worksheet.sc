import io.circe._
import io.circe.generic.extras._
import io.circe.syntax._

// Custom key mappings via annotations

// explicit member name transformation

// In other cases you may need more complex mappings. These can be provided as a function:

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

val json = Bar(13, "Qux").asJson

json.as[Bar]
