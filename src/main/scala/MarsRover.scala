import model.{Coordinate, Direction, East, Forward, Grid, Instruction, North, Position, RotateAnticlockwise, RotateClockwise, South, West}
import Utils.incrementWithWraparound

object MarsRover {
  def getFinalPosition(instructions: List[Instruction], startPosition: Position, grid: Grid): Position =
    instructions.foldLeft(startPosition) {
      case (currentPosition, instruction) =>
        import currentPosition._
        instruction match {
          case Forward             => currentPosition.withUpdatedCoordinate(forwardMove(direction, coordinate, grid))
          case RotateClockwise     => currentPosition.withUpdatedDirection(rotateClockwise(direction))
          case RotateAnticlockwise => currentPosition.withUpdatedDirection(rotateAnticlockwise(direction))
        }
    }

  private def forwardMove(direction: Direction, startCoord: Coordinate, grid: Grid): Coordinate = {
    val (xMove, yMove) = direction match {
      case North => (0, 1)
      case South => (0, -1)
      case East  => (1, 0)
      case West  => (-1, 0)
    }
    val newY = incrementWithWraparound(start = startCoord.y, increment = yMove, max = grid.height)
    val newX = incrementWithWraparound(start = startCoord.x, increment = xMove, max = grid.width)
    Coordinate(newX, newY)
  }

  private def rotateClockwise(startDirection: Direction): Direction = rotate90Degrees(startDirection, clockwise = true)

  private def rotateAnticlockwise(startDirection: Direction): Direction = rotate90Degrees(startDirection, clockwise = false)

  private def rotate90Degrees(from: Direction, clockwise: Boolean): Direction = {
    val orderedDirections = List(North, East, South, West)
    val increment         = if (clockwise) 1 else -1
    val indexOfNewDirection =
      incrementWithWraparound(start = orderedDirections.indexOf(from), increment = increment, max = orderedDirections.size)
    orderedDirections(indexOfNewDirection)
  }
}
