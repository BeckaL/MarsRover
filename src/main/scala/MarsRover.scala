object MarsRover {

  def getFinalPosition(
    instructions: List[Instruction],
    startCoordinate: Coordinate,
    startDirection: Direction
  ): (Direction, Coordinate) =
    instructions.foldLeft((startDirection, startCoordinate)) {
      case ((currentDirection, currentCoordinate), instruction) =>
        instruction match {
          case Forward             => (currentDirection, forwardMove(currentDirection, currentCoordinate))
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
        allDirectionsInOrder.indexOf(from) - 1 + allDirectionsInOrder.size // adding four avoids negative result in modulo
    allDirectionsInOrder(nextIndex % allDirectionsInOrder.size) // modulo enables wraparound -> i.e. index 5 becomes index 2
  }

  private def rotateAnticlockwise(startDirection: Direction): Direction = rotate90Degrees(startDirection, clockwise = false)

  private def forwardMove(direction: Direction, startCoord: Coordinate) = {
    val (xMove, yMove) = direction match {
      case North => (0, 1)
      case South => (0, -1)
      case East  => (1, 0)
      case West  => (-1, 0)
    }
    Coordinate(startCoord.x + xMove, startCoord.y + yMove)
  }
}
