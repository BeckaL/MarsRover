import model.{Coordinate, Grid}

import scala.annotation.tailrec
import Utils.incrementWithWraparound

object PathFinder {
  def findShortestPath(from: Coordinate, to: Coordinate, grid: Grid): List[Coordinate] = {
    val xIncrement = getIncrement(from = from.x, to = to.x, gridDimensionMax = grid.width)
    val yIncrement = getIncrement(from = from.y, to = to.y, gridDimensionMax = grid.height)

    @tailrec
    def traverseUntilTargetReached(current: Coordinate, visited: List[Coordinate]): List[Coordinate] = {
      val newVisited = visited :+ current
      if (current == to) {
        newVisited
      } else {
        val next =
          if (current.y == to.y)
            current.copy(x = incrementWithWraparound(current.x, xIncrement, grid.width))
          else
            current.copy(y = incrementWithWraparound(current.y, yIncrement, grid.height))
        traverseUntilTargetReached(next, newVisited)
      }
    }

    traverseUntilTargetReached(from, List.empty)
  }

  private def getIncrement(from: Int, to: Int, gridDimensionMax: Int) = {
    val toAssumingAfterFrom    = if (to > from) to else to + gridDimensionMax
    val isQuickerToGoBackwards = toAssumingAfterFrom - from > gridDimensionMax / 2
    if (isQuickerToGoBackwards) -1 else 1
  }
}
