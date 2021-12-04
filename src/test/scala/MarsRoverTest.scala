import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{FreeSpec, Matchers}

class MarsRoverTest extends FreeSpec with Matchers with TableDrivenPropertyChecks {
  "A mars rover" - {

    "should move in a single direction given the directions do not lead off the grid (negative x or y value)" in {
      val startCoordinate = Coordinate(x = 3, y = 3)

      val numberOfMoves = 3
      val instructions  = List.fill(numberOfMoves)(Forward)

      val data = Table(
        ("startDirection", "expectedEndCoordinate"),
        (North, startCoordinate.copy(y = startCoordinate.y + numberOfMoves)),
        (South, startCoordinate.copy(y = startCoordinate.y - numberOfMoves)),
        (East, startCoordinate.copy(x = startCoordinate.x + numberOfMoves)),
        (West, startCoordinate.copy(x = startCoordinate.x - numberOfMoves))
      )

      forAll(data) { case (startDirection, expectedEndCoordinate) =>
        MarsRover.getFinalPosition(instructions, startCoordinate, startDirection) shouldBe expectedEndCoordinate
      }
    }
  }
}
