import io.circe._
import io.circe.syntax._

// Encoding/Decoding Map[K, V]
// Custom Key Types

// If you need to encode/decode Map[K, V] where K is not String (or Symbol, Int, Long, etc.),
// you need to provide a KeyEncoder and/or KeyDecoder for your custom key type.

case class Foo(value: String)

implicit val fooKeyEncoder: KeyEncoder[Foo] = new KeyEncoder[Foo] {
  override def apply(foo: Foo): String = foo.value
}

val map = Map[Foo, Int](
  Foo("hello") -> 123,
  Foo("world") -> 456
)

implicit val fooKeyDecoder: KeyDecoder[Foo] = new KeyDecoder[Foo] {
  override def apply(key: String): Option[Foo] = Some(Foo(key))
}

val json = map.asJson

json.as[Map[Foo, Int]]
