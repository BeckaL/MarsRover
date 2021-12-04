import model.{Coordinate, East, Forward, Grid, North, RotateAnticlockwise, RotateClockwise, South, West}
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{FreeSpec, Matchers}

class MarsRoverTest extends FreeSpec with Matchers with TableDrivenPropertyChecks {
  "A mars rover" - {
    val tenByTenGrid = Grid(height = 10, width = 10)
    "should move in a single direction given the directions do not lead off the grid (negative x or y value)" in {
      val startCoordinate = Coordinate(x = 3, y = 3)
      val numberOfMoves   = 3
      val instructions    = List.fill(numberOfMoves)(Forward)

      val data = Table(
        ("startDirection", "expectedEndCoordinate"),
        (North, Coordinate(x = 3, y = 6)),
        (South, Coordinate(x = 3, y = 0)),
        (East, Coordinate(x = 6, y = 3)),
        (West, Coordinate(x = 0, y = 3))
      )

      forAll(data) { case (startDirection, expectedEndCoordinate) =>
        val expectedEndPosition = (startDirection, expectedEndCoordinate)
        MarsRover.getFinalPosition(instructions, startCoordinate, startDirection, tenByTenGrid) shouldBe expectedEndPosition
      }
    }

    "should appear on the other side of the grid when the directions lead off the grid" in {
      val startCoordinate = Coordinate(x = 3, y = 3)
      val fiveByFiveGrid  = Grid(height = 5, width = 5)
      val numberOfMoves   = 4
      val instructions    = List.fill(numberOfMoves)(Forward)

      val data = Table(
        ("startDirection", "expectedEndCoordinate"),
        (North, Coordinate(x = 3, y = 2)), // y would have been 3 + 4 = 7 (2 off grid)
        (South, Coordinate(x = 3, y = 4)), // y would have been 3 - 4 = -1 (1 off grid)
        (East, Coordinate(x = 2, y = 3)),  // x would have been 3 + 4 = 7 (2 off grid)
        (West, Coordinate(x = 4, y = 3))   // x would have been 3 - 4 = -1 (1 off grid)
      )

      forAll(data) { case (startDirection, expectedEndCoordinate) =>
        val expectedEndPosition = (startDirection, expectedEndCoordinate)
        MarsRover.getFinalPosition(instructions, startCoordinate, startDirection, fiveByFiveGrid) shouldBe expectedEndPosition
      }
    }

    "should be able to rotate clockwise" in {
      val startCoordinate = Coordinate(x = 3, y = 3)
      val instructions    = List(RotateClockwise)

      val data = Table(
        ("startDirection", "expectedEndDirection"),
        (North, East),
        (East, South),
        (South, West),
        (West, North)
      )

      forAll(data) { case (startDirection, expectedEndDirection) =>
        val expectedEndPosition = (expectedEndDirection, startCoordinate)
        MarsRover.getFinalPosition(instructions, startCoordinate, startDirection, tenByTenGrid) shouldBe expectedEndPosition
      }
    }

    "should be able to rotate anticlockwise" in {
      val startCoordinate = Coordinate(x = 3, y = 3)
      val instructions    = List(RotateAnticlockwise)

      val data = Table(
        ("startDirection", "expectedEndDirection"),
        (North, West),
        (West, South),
        (South, East),
        (East, North)
      )

      forAll(data) { case (startDirection, expectedEndDirection) =>
        val expectedEndPosition = (expectedEndDirection, startCoordinate)
        MarsRover.getFinalPosition(instructions, startCoordinate, startDirection, tenByTenGrid) shouldBe expectedEndPosition
      }
    }

    "should be able to follow a set of various instructions correctly" in {
      val grid = Grid(height = 3, width = 7)

      val startCoordinate = Coordinate(x = 0, y = 0) // x0
      val startDirection  = West
      val instructions = List(
        Forward,             // new position = x1
        RotateAnticlockwise, // new direction = South
        Forward,             // new position = x2
        Forward,             // new position = x3
        RotateClockwise,     // newDirection = West, position = x3
        RotateClockwise,     // newDirection = North, position = x3
        RotateClockwise,     // new Direction = East, position = x3
        Forward,             // position = x4
        Forward,             // position = x5
        Forward,             // position = x6
        RotateClockwise,     // newDirection = South
        Forward,             // position = x7
        RotateAnticlockwise, // newDirection = East
        Forward              // position = x8 so final position is x = 0, y = 3
      )

      // visual representation of positions visited
      //                       x
      //        0    1    2    3    4    5    6
      //       ---------------------------------
      //     2|                               x2 |
      //  y  1| x4   x5   x6                  x3 |
      //     0| x0        x7   x8             x1 |
      //       ----------------------------------

      val expectedEndPosition = (East, Coordinate(x = 3, y = 0))

      MarsRover.getFinalPosition(instructions, startCoordinate, startDirection, grid) shouldBe expectedEndPosition
    }
  }
}
