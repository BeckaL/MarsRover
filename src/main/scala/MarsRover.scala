object MarsRover {

  def getFinalPosition(
    instructions: List[Instruction],
    startCoordinate: Coordinate,
    startDirection: Direction
  ): (Direction, Coordinate) =
    instructions.foldLeft((startDirection, startCoordinate)) {
      case ((currentDirection, currentCoordinate), instruction) =>
        instruction match {
          case Forward         => (currentDirection, forwardMove(currentDirection, currentCoordinate))
          case RotateClockwise => (rotateClockwise(currentDirection), currentCoordinate)
          case _               => ???
        }
    }

  def rotateClockwise(startDirection: Direction): Direction = {
    val directionsInOrder   = List(North, East, South, West)
    val indexOfNewDirection = (directionsInOrder.indexOf(startDirection) + 1) % 4 // wrap around if new index > 3
    directionsInOrder(indexOfNewDirection)
  }

  def forwardMove(direction: Direction, startCoord: Coordinate) = {
    val (xMove, yMove) = direction match {
      case North => (0, 1)
      case South => (0, -1)
      case East  => (1, 0)
      case West  => (-1, 0)
    }
    Coordinate(startCoord.x + xMove, startCoord.y + yMove)
  }
}
