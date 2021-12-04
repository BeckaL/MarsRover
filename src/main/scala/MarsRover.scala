object MarsRover {

  def move(
    instructions: List[Instruction],
    startCoordinate: Coordinate,
    startDirection: Direction
  ): Coordinate = {
    val (_, finalCoordinate) =
      instructions.foldLeft((startDirection, startCoordinate)) { case ((currentDirection, currentCoordinate), instruction) =>
        instruction match {
          case Forward => (currentDirection, currentCoordinate.copy(y = currentCoordinate.y + 1))
          case _       => ???
        }
      }
    finalCoordinate
  }

}
