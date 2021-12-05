import model.{Coordinate, Grid}

object PathFinder {
  def findShortestPath(from: Coordinate, to: Coordinate, grid: Grid): List[Coordinate] = {
    val _ = grid
    if (from.x == to.x) {
      pointsOnLine(from.y, to.y, grid.height).map(Coordinate(from.x, _))
    } else if (from.y == to.y) {
      pointsOnLine(from.x, to.x, grid.width).map(Coordinate(_, from.y))
    } else {
      ???
    }
  }

  private def pointsOnLine(from: Int, to: Int, gridDimensionMax: Int): List[Int] = {
    val toWithWraparound       = if (to > from) to else to + gridDimensionMax
    val distanceForwards       = toWithWraparound - from
    val isQuickerToGoBackwards = distanceForwards > gridDimensionMax / 2
    val increment              = if (isQuickerToGoBackwards) -1 else 1
    val numberOfSteps          = if (isQuickerToGoBackwards) gridDimensionMax - distanceForwards else distanceForwards
    (0 until numberOfSteps + 1).toList.map(i => incrementWithWraparound(from, i * increment, gridDimensionMax))
  }

  // TODO move somewhere common
  private def incrementWithWraparound(start: Int, increment: Int, max: Int): Int =
    // adding max size to start and increment enables handling of negative numbers consistently with modulo -> i.e. -1 % 4 becomes 3, not -1
    (start + increment + max) % max
}
