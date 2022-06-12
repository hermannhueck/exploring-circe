package ch04encodinganddecoding

import hutil.stringformat._

import scala.util.chaining._

import io.circe.generic.auto._
import io.circe.syntax._

object Ex02AutomaticDerivation extends App {

  dash80.green.println()

  case class Person(name: String)
  // defined class Person

  case class Greeting(salutation: String, person: Person, exclamationMarks: Int)
  // defined class Greeting

  val greeting = Greeting("Hey", Person("Chris"), 3).asJson tap println
  // res0: io.circe.Json =
  // {
  //   "salutation" : "Hey",
  //   "person" : {
  //     "name" : "Chris"
  //   },
  //   "exclamationMarks" : 3
  // }

  greeting.as[Greeting] tap println
  // res1: io.circe.Decoder.Result[Greeting] = Right(Greeting(Hey,Person(Chris),3))
  // final type Decoder.Result[A] = Either[DecodingFailure, A]

  dash80.green.println()
}
