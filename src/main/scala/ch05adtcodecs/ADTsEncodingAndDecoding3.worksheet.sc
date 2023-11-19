import io.circe.parser._
import io.circe.syntax._

// A more generic solution (with circe-shapes)

// The generic-extras module provides a little more configurability in this respect. We can write the following, for example:

import ch05adtcodecs.event._

import io.circe.generic.extras.auto._
import io.circe.generic.extras.Configuration

implicit val genDevConfig: Configuration =
  Configuration.default.withDiscriminator("what_am_i")

decode[Event]("""{ "i": 1000, "what_am_i": "Foo" }""")

parse("""{ "i": 1000, "what_am_i": "Foo" }""").flatMap(_.as[Event])

(Foo(100): Event).asJson.noSpaces

// Instead of a wrapper object in the JSON we have an extra field that indicates the constructor.
// This isn’t the default behavior since it has some weird corner cases (e.g. if one of our case classes
// had a member named what_am_i), but in many cases it’s reasonable
// and it’s been supported in generic-extras since that module was introduced.
