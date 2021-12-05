import model.{Coordinate, East, Forward, Grid, North, Position, RotateAnticlockwise, RotateClockwise, South, West}
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{FreeSpec, Matchers}

class MarsRoverTest extends FreeSpec with Matchers with TableDrivenPropertyChecks {
  "MarsRover.getFinalPosition" - {

    "should calculate a final position given instructions to move in a single direction which do not lead off the grid" in {
      val numberOfMoves   = 3
      val instructions    = List.fill(numberOfMoves)(Forward)
      val startCoordinate = Coordinate(x = 3, y = 3)

      val data = Table(
        ("startDirection", "expectedEndCoordinate"),
        (North, startCoordinate.copy(y = 6)),
        (South, startCoordinate.copy(y = 0)),
        (East, startCoordinate.copy(x = 6)),
        (West, startCoordinate.copy(x = 0))
      )

      forAll(data) { case (startDirection, expectedEndCoordinate) =>
        val startPosition       = Position(startDirection, startCoordinate)
        val expectedEndPosition = Position(startDirection, expectedEndCoordinate)

        MarsRover.getFinalPosition(instructions, startPosition, tenByTenGrid) shouldBe expectedEndPosition
      }
    }

    "should calculate a position when the directions lead off the grid, making the robot appear on the opposite side of the grid" in {
      val startCoordinate = Coordinate(x = 3, y = 3)
      val fiveByFiveGrid  = Grid(height = 5, width = 5)
      val numberOfMoves   = 4
      val instructions    = List.fill(numberOfMoves)(Forward)

      val data = Table(
        ("startDirection", "expectedEndCoordinate"),
        (North, startCoordinate.copy(y = 2)), // y would have been 3 + 4 = 7 (2 off grid)
        (South, startCoordinate.copy(y = 4)), // y would have been 3 - 4 = -1 (1 off grid)
        (East, startCoordinate.copy(x = 2)),  // x would have been 3 + 4 = 7 (2 off grid)
        (West, startCoordinate.copy(x = 4))   // x would have been 3 - 4 = -1 (1 off grid)
      )

      forAll(data) { case (startDirection, expectedEndCoordinate) =>
        val startPosition       = Position(startDirection, startCoordinate)
        val expectedEndPosition = Position(startDirection, expectedEndCoordinate)

        MarsRover.getFinalPosition(instructions, startPosition, fiveByFiveGrid) shouldBe expectedEndPosition
      }
    }

    "should calculate a final position given a single instruction to rotate clockwise" in {
      val instructions = List(RotateClockwise)

      val data = Table(
        ("startDirection", "expectedEndDirection"),
        (North, East),
        (East, South),
        (South, West),
        (West, North)
      )

      forAll(data) { case (startDirection, expectedEndDirection) =>
        val startPosition       = Position(startDirection, originCoordinate)
        val expectedEndPosition = Position(expectedEndDirection, originCoordinate)

        MarsRover.getFinalPosition(instructions, startPosition, tenByTenGrid) shouldBe expectedEndPosition
      }
    }

    "should calculate a final position given a single instruction to rotate anticlockwise" in {
      val instructions = List(RotateAnticlockwise)

      val data = Table(
        ("startDirection", "expectedEndDirection"),
        (North, West),
        (West, South),
        (South, East),
        (East, North)
      )

      forAll(data) { case (startDirection, expectedEndDirection) =>
        val startPosition       = Position(startDirection, originCoordinate)
        val expectedEndPosition = Position(expectedEndDirection, originCoordinate)

        MarsRover.getFinalPosition(instructions, startPosition, tenByTenGrid) shouldBe expectedEndPosition
      }
    }

    "should calculate a final position given an assorted mix of instructions" in {
      val grid = Grid(height = 3, width = 7)

      val startPosition = Position(West, originCoordinate) // x0 on grid drawn out below
      val instructions = List(
        Forward,             // new position = x1 on grid drawn out below
        RotateAnticlockwise, // new direction = South
        Forward,             // new position = x2
        Forward,             // new position = x3
        RotateClockwise,     // newDirection = West
        RotateClockwise,     // newDirection = North
        RotateClockwise,     // new Direction = East
        Forward,             // position = x4
        Forward,             // position = x5
        Forward,             // position = x6
        RotateClockwise,     // newDirection = South
        Forward,             // position = x7
        RotateAnticlockwise, // newDirection = East
        Forward              // position = x8 so final position is x = 0, y = 3
      )

      // visual representation of positions visited (xn represents a coordinate that the robot visits)
      //                       x
      //        0    1    2    3    4    5    6
      //       ---------------------------------
      //     2|                               x2 |
      //  y  1| x4   x5   x6                  x3 |
      //     0| x0        x7   x8             x1 |
      //       ----------------------------------

      val expectedEndPosition = Position(East, Coordinate(x = 3, y = 0))

      MarsRover.getFinalPosition(instructions, startPosition, grid) shouldBe expectedEndPosition
    }
  }

  private val tenByTenGrid     = Grid(height = 10, width = 10)
  private val originCoordinate = Coordinate(0, 0)
}
