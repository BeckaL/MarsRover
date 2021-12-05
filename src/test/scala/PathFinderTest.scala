import model.{Coordinate, Grid}
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{FreeSpec, Matchers}

class PathFinderTest extends FreeSpec with Matchers with TableDrivenPropertyChecks {
  "PathFinder.findShortestPath" - {
    "should find shortest path when start coord and end coord form a line and quickest route is to stay within grid" in {
      val grid       = Grid(height = 6, width = 6)
      val startCoord = (3, 3)

      val data = Table(
        ("targetCoord", "expectedPath"),
        ((5, 3), List(startCoord, (4, 3), (5, 3))),         // target is on the same row, reached by incrementing x by 1 each step
        ((1, 3), List(startCoord, (2, 3), (1, 3))),         // target is on the same row,  reached by incrementing x by -1 each step
        ((0, 3), List(startCoord, (4, 3), (5, 3), (0, 3))), // same row, equal distance either way, defaults to incrementing x by +1
        ((3, 5), List(startCoord, (3, 4), (3, 5))),         // target is on the same column, reached by incrementing y by 1 each step
        ((3, 1), List(startCoord, (3, 2), (3, 1))),         // target is on the same column,  reached by incrementing y by -1 each step
        ((3, 0), List(startCoord, (3, 4), (3, 5), (3, 0))), // same column, equal distance either way, defaults to incrementing x by +1
        (startCoord, List(startCoord))                      // from and to same coord gives path of just that coord
      )

      forAll(data) { case (targetTuple, expectedPath) =>
        val result = PathFinder.findShortestPath(from = coordFrom(startCoord), to = coordFrom(targetTuple), grid = grid)

        result shouldBe expectedPath.map(coordFrom)
      }
    }

    "should find shortest path when start coord and end coord form a line and quickest route is to go off grid" in {
      val grid = Grid(height = 6, width = 6)
      val data = Table(
        ("start", "target", "expectedPath"),
        // quickest route is to move off the grid and appear on the other side, incrementing x by + 1
        ((0, 0), (0, 4), List((0, 0), (0, 5), (0, 4))),
        // quickest route is to move off the grid and appear on the other side, incrementing x by -1
        ((0, 4), (0, 0), List((0, 4), (0, 5), (0, 0))),
        // quickest route is to move off the grid and appear on the other side, incrementing y by + 1
        ((0, 0), (4, 0), List((0, 0), (5, 0), (4, 0))),
        // quickest route is to move off the grid and appear on the other side, incrementing y by - 1
        ((4, 0), (0, 0), List((4, 0), (5, 0), (0, 0)))
      )

      forAll(data) { case (startTuple, targetTuple, expectedPath) =>
        val result = PathFinder.findShortestPath(from = coordFrom(startTuple), to = coordFrom(targetTuple), grid = grid)

        result shouldBe expectedPath.map(coordFrom)
      }
    }

    "should find a shortest path when start coord and end coord do not fall on a vertical or horizontal line" in {
      val grid = Grid(height = 6, width = 6)
      val data = Table(
        ("start", "target", "expectedPath"),
        // target is reached by incrementing moving along the y axis first until target is reached, and then along x axis
        ((0, 0), (2, 2), List((0, 0), (0, 1), (0, 2), (1, 2), (2, 2))),
        // quickest way is to increment y by -1, and x by + 1
        ((0, 0), (2, 5), List((0, 0), (0, 5), (1, 5), (2, 5))),
        // quickest way is to increment y by 1, and x by -1
        ((0, 0), (5, 2), List((0, 0), (0, 1), (0, 2), (5, 2))),
        // quickest way is to increment y by -1, and x by -1
        ((0, 0), (5, 5), List((0, 0), (0, 5), (5, 5)))
      )

      forAll(data) { case (startTuple, targetTuple, expectedPath) =>
        val result = PathFinder.findShortestPath(from = coordFrom(startTuple), to = coordFrom(targetTuple), grid = grid)

        result shouldBe expectedPath.map(coordFrom)
      }
    }
  }

  private def coordFrom(tuple: (Int, Int)) = Coordinate(tuple._1, tuple._2)
}
