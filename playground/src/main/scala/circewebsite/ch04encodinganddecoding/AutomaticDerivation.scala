package circewebsite.ch04encodinganddecoding

import scala.util.chaining._

import io.circe.generic.auto._
import io.circe.syntax._

object AutomaticDerivation extends util.App {

  case class Person(name: String)
  // defined class Person

  case class Greeting(salutation: String, person: Person, exclamationMarks: Int)
  // defined class Greeting

  Greeting("Hey", Person("Chris"), 3).asJson pipe println
  // res0: io.circe.Json =
  // {
  //   "salutation" : "Hey",
  //   "person" : {
  //     "name" : "Chris"
  //   },
  //   "exclamationMarks" : 3
  // }
}
