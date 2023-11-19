import java.time.Instant

import io.circe.parser.decode
import io.circe.syntax._
import io.circe.{Decoder, Encoder}

implicit val encodeInstant: Encoder[Instant] =
  Encoder
    .encodeString
    .contramap[Instant](_.toString)

import cats.syntax.either._
val decodeInstant0: Decoder[Instant] = Decoder.decodeString.emap { str =>
  Either
    .catchNonFatal(Instant.parse(str))
    .leftMap(_ => s"Instant cannot be parsed from String: $str")
}

import scala.util.Try
implicit val decodeInstant: Decoder[Instant] = Decoder.decodeString.emapTry { str =>
  Try(Instant.parse(str))
}

val instant = Instant.now()

val encoded =
  instant
    .asJson
    .noSpaces

decode[Instant](encoded)
decode(encoded)
