import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{FreeSpec, Matchers}

class MarsRoverTest extends FreeSpec with Matchers with TableDrivenPropertyChecks {
  "A mars rover" - {
    val startCoordinate = Coordinate(x = 3, y = 3)
    "should move in a single direction given the directions do not lead off the grid (negative x or y value)" in {
      val numberOfMoves = 3
      val instructions  = List.fill(numberOfMoves)(Forward)

      val data = Table(
        ("startDirection", "expectedEndCoordinate"),
        (North, Coordinate(x = 3, y = 6)),
        (South, Coordinate(x = 3, y = 0)),
        (East, Coordinate(x = 6, y = 3)),
        (West, Coordinate(x = 0, y = 3))
      )

      forAll(data) { case (startDirection, expectedEndCoordinate) =>
        val expectedEndPosition = (startDirection, expectedEndCoordinate)
        MarsRover.getFinalPosition(instructions, startCoordinate, startDirection) shouldBe expectedEndPosition
      }
    }

    "should be able to rotate clockwise" in {
      val instructions = List(RotateClockwise)

      val data = Table(
        ("startDirection", "expectedEndDirection"),
        (North, East),
        (East, South),
        (South, West),
        (West, North)
      )

      forAll(data) { case (startDirection, expectedEndDirection) =>
        val expectedEndPosition = (expectedEndDirection, startCoordinate)
        MarsRover.getFinalPosition(instructions, startCoordinate, startDirection) shouldBe expectedEndPosition
      }
    }

    "should be able to rotate anticlockwise" in {
      val instructions = List(RotateAnticlockwise)

      val data = Table(
        ("startDirection", "expectedEndDirection"),
        (North, West),
        (West, South),
        (South, East),
        (East, North)
      )

      forAll(data) { case (startDirection, expectedEndDirection) =>
        val expectedEndPosition = (expectedEndDirection, startCoordinate)
        MarsRover.getFinalPosition(instructions, startCoordinate, startDirection) shouldBe expectedEndPosition
      }
    }

  }
}
