import model.{Coordinate, Grid}
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{FreeSpec, Matchers}

class PathFinderTest extends FreeSpec with Matchers with TableDrivenPropertyChecks {
  "PathFinder.findShortestPath" - {
    "should find shortest path when start coord and end coord form a line and quickest route is to stay within grid" in {
      val grid       = Grid(height = 6, width = 6)
      val startCoord = Coordinate(x = 3, y = 3)

      val data = Table(
        ("targetCoord", "expectedCoordinatesBetweenStartAndTarget"),
        (Coordinate(3, 5), List(Coordinate(3, 4))), // target is on the same row, reached by incrementing x by 1 each step
        (Coordinate(5, 3), List(Coordinate(4, 3))), // target is on the same column, reached by incrementing y by 1 each step
        (Coordinate(1, 3), List(Coordinate(2, 3))), // target is on the same row,  reached by incrementing x by -1 each step
        (Coordinate(3, 1), List(Coordinate(3, 2)))  // target is on the same column,  reached by incrementing y by -1 each step
      )

      forAll(data) { case (targetCoord, expectedCoordinatesBetweenStartAndTarget) =>
        val expectedPath = startCoord +: expectedCoordinatesBetweenStartAndTarget :+ targetCoord
        PathFinder.findShortestPath(from = startCoord, to = targetCoord, grid = grid) shouldBe expectedPath
      }
    }
  }
}
