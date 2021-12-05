object Utils {

  def incrementWithWraparound(start: Int, increment: Int, max: Int): Int =
    // adding max size to start and increment enables handling of negative numbers consistently with modulo -> i.e. -1 % 4 becomes 3, not -1
    (start + increment + max) % max

}
