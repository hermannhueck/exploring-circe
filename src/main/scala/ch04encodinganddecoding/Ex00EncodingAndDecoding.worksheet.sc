import io.circe.syntax._

val intsJson =
  List(1, 2, 3).asJson

intsJson
  .as[List[Int]]

import io.circe.parser.decode

decode[List[Int]]("[1, 2, 3]")
