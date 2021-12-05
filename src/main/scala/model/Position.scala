package model

final case class Position(direction: Direction, coordinate: Coordinate) {
  def withUpdatedDirection(newDirection: Direction)    = this.copy(direction = newDirection)
  def withUpdatedCoordinate(newCoordinate: Coordinate) = this.copy(coordinate = newCoordinate)
}
