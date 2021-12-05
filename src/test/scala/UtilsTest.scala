import org.scalatest.{FreeSpec, Matchers}
import org.scalatest.prop.TableDrivenPropertyChecks

class UtilsTest extends FreeSpec with Matchers with TableDrivenPropertyChecks {
  "increment with wraparound correctly increments" - {
    "when no wraparound is required for a positive increment" in {
      Utils.incrementWithWraparound(start = 1, increment = 1, max = 3) shouldBe 2
    }

    "when no wraparound is required for a negative increment" in {
      Utils.incrementWithWraparound(start = 1, increment = -1, max = 3) shouldBe 0
    }

    "when wraparound is required for a positive increment" in {
      // start is zero indexed so 1 is the biggest index when maxSize is 2
      Utils.incrementWithWraparound(start = 1, increment = 1, max = 2) shouldBe 0
    }

    "when wraparound is required for a negative increment" in {
      Utils.incrementWithWraparound(start = 0, increment = -1, max = 2) shouldBe 1
    }
  }

}
