import model.{Coordinate, Direction, East, Forward, Grid, Instruction, North, RotateAnticlockwise, RotateClockwise, South, West}

object MarsRover {
  def getFinalPosition(
    instructions: List[Instruction],
    startCoordinate: Coordinate,
    startDirection: Direction,
    grid: Grid
  ): (Direction, Coordinate) =
    instructions.foldLeft((startDirection, startCoordinate)) {
      case ((currentDirection, currentCoordinate), instruction) =>
        instruction match {
          case Forward             => (currentDirection, forwardMove(currentDirection, currentCoordinate, grid))
          case RotateClockwise     => (rotateClockwise(currentDirection), currentCoordinate)
          case RotateAnticlockwise => (rotateAnticlockwise(currentDirection), currentCoordinate)
        }
    }

  private def rotateClockwise(startDirection: Direction): Direction = rotate90Degrees(startDirection, clockwise = true)

  private def rotate90Degrees(from: Direction, clockwise: Boolean): Direction = {
    val directionsInOrder = List(North, East, South, West)
    val increment         = if (clockwise) 1 else -1
    val indexOfNewDirection =
      incrementWithWraparound(start = directionsInOrder.indexOf(from), increment = increment, maxSize = directionsInOrder.size)
    directionsInOrder(indexOfNewDirection)
  }

  private def rotateAnticlockwise(startDirection: Direction): Direction = rotate90Degrees(startDirection, clockwise = false)

  private def forwardMove(direction: Direction, startCoord: Coordinate, grid: Grid) = {
    val (xMove, yMove) = direction match {
      case North => (0, 1)
      case South => (0, -1)
      case East  => (1, 0)
      case West  => (-1, 0)
    }
    val newY = incrementWithWraparound(start = startCoord.y, increment = yMove, maxSize = grid.height)
    val newX = incrementWithWraparound(start = startCoord.x, increment = xMove, maxSize = grid.width)
    Coordinate(newX, newY)
  }

  private def incrementWithWraparound(start: Int, increment: Int, maxSize: Int) =
    // adding max size to start and increment enables handling of negative numbers consistently with modulo -> i.e. -1 % 4 becomes 3, not -1
    (start + increment + maxSize) % maxSize
}
