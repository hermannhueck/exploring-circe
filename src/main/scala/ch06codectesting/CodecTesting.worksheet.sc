import io.circe._
import io.circe.syntax._

case class Person(name: String)
object Person {
  implicit val encPerson: Encoder[Person] = Encoder.forProduct1("name")(_.name)
  implicit val decPerson: Decoder[Person] = Decoder.forProduct1("mame")(Person.apply _)
}

Person("James")
  .asJson
  .as[Person]

import cats.Eq
import io.circe.testing.ArbitraryInstances
import org.scalacheck.{Arbitrary, Gen}

object Implicits extends ArbitraryInstances {
  implicit val eqPerson: Eq[Person]         = Eq.fromUniversalEquals
  implicit val arbPerson: Arbitrary[Person] = Arbitrary {
    Gen.listOf(Gen.alphaChar) map { chars => Person(chars.mkString("")) }
  }
}

import io.circe.testing.CodecTests

val personCodecTests = CodecTests[Person]

import Implicits._
// CodecTests[T] expose two “rule sets” that can be used with Discipline.

// The less restrictive set is unserializableCodec.
personCodecTests.unserializableCodec

// The more restrictive set is codec:
personCodecTests.codec

// Now use discipline to check the rule sets.

// codec checks the laws from unserializableCodec and ensures that your encoder and decoder
// can be serialized to and from Java byte array streams. It is generally a good idea
// to use the stronger laws from .codec, and you definitely should use them
// if you’re in a setting where the JVM has to ship a lot of data around,
// for example in a Spark application. However, if you’re not in a distributed setting
// and the serializability laws are getting in your way,
// it’s fine to skip them with the unserializableCodec.
