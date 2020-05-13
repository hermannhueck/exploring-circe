package circewebsite.ch06circeoptics

import scala.util.chaining._
import util.formatting._

import io.circe._
import io.circe.parser._

object Optics extends util.App {

  println
  printTextInLine("Traversing JSON")

  val jsonDocument = """
{
  "order": {
    "customer": {
      "name": "Custy McCustomer",
      "contactDetails": {
        "address": "1 Fake Street, London, England",
        "phone": "0123-456-789"
      }
    },
    "items": [{
      "id": 123,
      "description": "banana",
      "quantity": 1
    }, {
      "id": 456,
      "description": "apple",
      "quantity": 2
    }],
    "total": 123.45
  }
}
  """

  jsonDocument pipe println

  val json = parse(jsonDocument).getOrElse(Json.Null)

  "\n--- retrieving phone number using a cursor:" pipe println

  val phoneNum: Option[String] =
    json
      .hcursor
      .downField("order")
      .downField("customer")
      .downField("contactDetails")
      .get[String]("phone")
      .toOption
  // phoneNum: Option[String] = Some(0123-456-789)
  phoneNum pipe println

  "\n--- retrieving phone number with optics:" pipe println

  import io.circe.optics.JsonPath
  // import io.circe.optics.JsonPath._

  val _phoneNum =
    JsonPath.root.order.customer.contactDetails.phone.string
  // _phoneNum: monocle.Optional[io.circe.Json,String] = monocle.POptional$$anon$1@dc15f21

  val phoneNum2: Option[String] = _phoneNum.getOption(json)
  // phoneNum: Option[String] = Some(0123-456-789)

  phoneNum2 pipe println

  "\n--- retrieving items and quantities using a cursor:" pipe println

  val items: Vector[Json] = json
    .hcursor
    .downField("order")
    .downField("items")
    .focus
    .flatMap(_.asArray)
    .getOrElse(Vector.empty)
    .tap(println)
  // items: Vector[io.circe.Json] =
  // Vector({
  //   "id" : 123,
  //   "description" : "banana",
  //   "quantity" : 1
  // }, {
  //   "id" : 456,
  //   "description" : "apple",
  //   "quantity" : 2
  // })

  println

  val quantities: Vector[Int] =
    items
      .flatMap(_.hcursor.get[Int]("quantity").toOption)
      .tap(println)
  // quantities: Vector[Int] = Vector(1, 2)

  "\n--- retrieving items and quantities with optics:" pipe println

  val items2 =
    JsonPath
      .root
      .order
      .items
      .arr
      .getOption(json)
      .getOrElse(Vector.empty) tap println

  println

  val quantities2: List[Int] =
    JsonPath
      .root
      .order
      .items
      .each
      .quantity
      .int
      .getAll(json) tap println
  // quantities2: List[Int] = List(1, 2)

  printTextInLine("Modifying JSON")

  val doubleQuantities: Json => Json =
    JsonPath
      .root
      .order
      .items
      .each
      .quantity
      .int
      .modify(_ * 2)
  // doubleQuantities: io.circe.Json => io.circe.Json = monocle.PTraversal$$Lambda$9793/0x0000000802610840@6b6cd655

  val modifiedJson: Json =
    doubleQuantities(json) tap println

  printTextInLine("Recursively modifying JSON")

  import io.circe.optics.JsonOptics._
  import monocle.function.Plated

  Plated.transform[Json] { j =>
    json.asNumber match {
      case Some(n) => Json.fromString(n.toString)
      case None    => j
    }
  }(json) pipe println

  println
  printTextInLine("Dynamic")

  """|
     |Some of the code above may look quite magical at first glance.
     |How are we calling methods like order, items and customer on circe’s JsonPath class?
     |
     |The answer is that JsonPath relies on a slightly obscure feature of Scala called Dynamic.
     |This means you can call methods that don’t actually exist. When you do so,
     |the selectDynamic method is called, and the name of the method you wanted to call is passed as an argument.
     |""".stripMargin pipe println

  printTextInLine("Warning")

  """|
     |The use of Dynamic means that your code is not “typo-safe”. For example, if you fat-finger the previous example:
     |
     |This code will compile just fine, but not do what you expect.
     |Because the JSON document doesn’t have an itemss field, the same document will be returned unmodified.
     |""".stripMargin pipe println

  val doubleQuantitiesTypo: Json => Json = // leaves the numbers unchanged
    JsonPath.root.order.itemss.each.quantity.int.modify(_ * 2) // Note the "itemss" typo

  val modifiedJsonTypo = doubleQuantitiesTypo(json) tap println
}
