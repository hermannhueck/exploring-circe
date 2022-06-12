package ch05adtcodecs

object event {
  sealed trait Event                         extends Product with Serializable
  final case class Foo(i: Int)               extends Event
  final case class Bar(s: String)            extends Event
  final case class Baz(c: Char)              extends Event
  final case class Qux(values: List[String]) extends Event
}
