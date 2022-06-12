package ch04encodinganddecoding

import hutil.stringformat._

import scala.util.chaining._

import io.circe.syntax._

object Ex00EncodingAndDecoding extends App {

  dash80.green.println()

  val intsJson = List(1, 2, 3).asJson
  intsJson pipe println

  intsJson.as[List[Int]] pipe println
  // res0: io.circe.Decoder.Result[List[Int]] = Right(List(1, 2, 3))

  import io.circe.parser.decode

  decode[List[Int]]("[1, 2, 3]") pipe println
  // res1: Either[io.circe.Error,List[Int]] = Right(List(1, 2, 3))

  dash80.green.println()
}