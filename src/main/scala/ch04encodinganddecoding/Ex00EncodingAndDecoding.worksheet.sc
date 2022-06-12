import scala.util.chaining._

import io.circe.syntax._

val intsJson = List(1, 2, 3).asJson
intsJson pipe println

intsJson.as[List[Int]] tap println
// res0: io.circe.Decoder.Result[List[Int]] = Right(List(1, 2, 3))

import io.circe.parser.decode

decode[List[Int]]("[1, 2, 3]") tap println
// res1: Either[io.circe.Error,List[Int]] = Right(List(1, 2, 3))
