import org.scalatest.{FreeSpec, Matchers}

class MarsRoverTest extends FreeSpec with Matchers {
  "A mars rover" - {

    "should move in a single direction" in {
      val numberOfMoves = 3
      val instructions  = List.fill(numberOfMoves)(Forward)

      val startCoordinate = Coordinate(x = 0, y = 0)
      val startDirection  = North

      MarsRover.move(instructions, startCoordinate, startDirection) shouldBe
        Coordinate(x = 0, y = numberOfMoves)
    }
  }

}
