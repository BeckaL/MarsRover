import model.{Coordinate, Grid}

object PathFinder {
  def findShortestPath(from: Coordinate, to: Coordinate, grid: Grid): List[Coordinate] = {
    val _ = grid
    if (from.x == to.x) {
      pointsOnLine(from.y, to.y).map(Coordinate(from.x, _))
    } else if (from.y == to.y) {
      pointsOnLine(from.x, to.x).map(Coordinate(_, from.y))
    } else {
      ???
    }
  }

  private def pointsOnLine(from: Int, to: Int): List[Int] =
    if (from < to) (from to to).toList else (to to from).toList.reverse
}
