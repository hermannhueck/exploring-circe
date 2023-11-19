import io.circe._
import io.circe.generic.extras._
import io.circe.syntax._

// Custom key mappings via annotations

// explicit member name transformation (using @JsonKey annotation)

// Since this is a common use case, we also support for mapping member names via an annotation:

implicit val config: Configuration =
  Configuration.default

@ConfiguredJsonCodec case class Bar(@JsonKey("my-int") i: Int, s: String)

val json = Bar(13, "Qux").asJson

json.as[Bar]
