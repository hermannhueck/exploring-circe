package ch04encodinganddecoding

import java.time.Instant

import scala.util.chaining._

import cats.syntax.either._
import io.circe.parser.decode
import io.circe.syntax._
import io.circe.{Decoder, Encoder}

object CustomCodecs2 extends App {

  implicit val encodeInstant: Encoder[Instant] =
    Encoder
      .encodeString
      .contramap[Instant](_.toString)
  // encodeInstant: io.circe.Encoder[java.time.Instant] = io.circe.Encoder$$anon$1@661d3b8e

  implicit val decodeInstant: Decoder[Instant] = Decoder.decodeString.emap { str =>
    Either
      .catchNonFatal(Instant.parse(str))
      .leftMap(_ => s"Instant cannot be parsed from String: $str")
  }
  // decodeInstant: io.circe.Decoder[java.time.Instant] = io.circe.Decoder$$anon$13@50ec334

  val instant = Instant.now()

  instant
    .asJson
    .tap(println)
    .noSpaces
    .pipe(decode(_))
    .pipe(println)
}
