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

  private def rotate90Degrees(from: Direction, clockwise: Boolean) = {
    val allDirectionsInOrder = List(North, East, South, West)
    val nextIndex =
      if (clockwise)
        allDirectionsInOrder.indexOf(from) + 1
      else
        allDirectionsInOrder.indexOf(from) - 1 + allDirectionsInOrder.size // adding number of directions avoids negative result in modulo
    allDirectionsInOrder(nextIndex % allDirectionsInOrder.size) // modulo enables wraparound -> i.e. index 5 becomes index 2
  }

  private def rotateAnticlockwise(startDirection: Direction): Direction = rotate90Degrees(startDirection, clockwise = false)

  private def forwardMove(direction: Direction, startCoord: Coordinate, grid: Grid) = {
    val (xMove, yMove) = direction match {
      case North => (0, 1)
      case South => (0, -1)
      case East  => (1, 0)
      case West  => (-1, 0)
    }
    val newY = ((startCoord.y + yMove) + grid.height) % grid.height
    val newX = ((startCoord.x + xMove) + grid.width)  % grid.width
    Coordinate(newX, newY)
  }
}
