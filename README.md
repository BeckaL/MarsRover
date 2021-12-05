## Mars Rover

This is an sbt project using scala 2.13, tested with scalatest, and linted with scalafmt.
To run all tests:
`sbt test`

Covered in this project: part 1 (basic movement) and the first part of part 2 (autopilot without obstacles)
As noted below, the biggest thing I'd change given more time would be to add in error handling for cases where coordinates are not within the grid.

## Structure of the project

### `MarsRover`
Contains a public function, `getFinalPosition` which takes a list of instructions, some grid dimensions, and a start position (grid coordinate and direction) and returns a final position after all the instructions have been carried out.
Supported instructions are:
* move forward
* rotate 90 degrees clockwise
* rotate 90 degrees anticlockwise
Tested in `MarsRoverTest`
Future improvements given more time: 
1. add error handling for when the start coordinate is not within the grid 

### `PathFinder` 
Implements path finding assuming no obstacles.
Contains a function `findShortestPath` which takes a start coordinate, a target coordinate and a grid and finds the shortest path between them.
This is achieved with a simple function which derives whether the quickest way to get to the target is to go forwards or 
backwards given the xs and ys of the from coordinate and the target coordinate. It traverses along the y axis until it hits the coordinate, and then along the x axis.
Tested in `PathFinderTest`
Future improvements given more time: 
1. add error handling for when the start or target coordinates are not within the grid
2. implement path finding with obstacles via breadth first search

### `model directory`
Contains all the case classes and traits used to express the domain. 

### `Utils`
Contains a helper function used in both `MarsRover` and `PathFinder` - tested in `UtilsTest`



