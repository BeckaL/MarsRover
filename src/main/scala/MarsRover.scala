object MarsRover {

  def getFinalPosition(
    instructions: List[Instruction],
    startCoordinate: Coordinate,
    startDirection: Direction
  ): Coordinate = {
    val (_, finalCoordinate) =
      instructions.foldLeft((startDirection, startCoordinate)) { case ((currentDirection, currentCoordinate), instruction) =>
        instruction match {
          case Forward => (currentDirection, forwardMove(currentDirection, currentCoordinate))
          case _       => ???
        }
      }
    finalCoordinate
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
