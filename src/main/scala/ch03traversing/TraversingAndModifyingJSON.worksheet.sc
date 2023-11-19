import scala.util.chaining._

import hutil.stringformat._
import io.circe._
import io.circe.parser._

val rawJson: String =
  """|{
     |  "id": "c730433b-082c-4984-9d66-855c243266f0",
     |  "name": "Foo",
     |  "counts": [1, 2, 3],
     |  "values": {
     |    "bar": true,
     |    "baz": 100.001,
     |    "qux": ["a", "b"]
     |  }
     |} """.stripMargin

val doc: Json =
  parse(rawJson)
    .getOrElse(Json.Null)

val cursor: HCursor = doc.hcursor

val baz: Decoder.Result[Double] =
  cursor
    .downField("values")
    .downField("baz")
    .as[Double]

// You can also use `get[A](key)` as shorthand for `downField(key).as[A]`
val baz2: Decoder.Result[Double] =
  cursor
    .downField("values")
    .get[Double]("baz")

val secondQux: Decoder.Result[String] =
  cursor
    .downField("values")
    .downField("qux")
    .downArray
    .as[String]

s"$dash10 Transforming data $dash10".magenta.println()
"----- Using an ACursor =" pipe println

val reversedNameCursor: ACursor =
  cursor.downField("name").withFocus(_.mapString(_.reverse))

val reversedName: Option[Json] = reversedNameCursor.top
reversedName pipe println

s"$dash10 Cursors $dash10".magenta.println()
"""|
   |circe has three slightly different cursor implementations:
   |
   |- Cursor:  provides functionality for moving around a tree and making modifications
   |- HCursor: tracks the history of operations performed. This can be used to provide useful error messages when something goes wrong.
   |- ACursor: also tracks history, but represents the possibility of failure (e.g. calling downField on a field that doesn’t exist)
   |""".stripMargin pipe println
