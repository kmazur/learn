package com.hackerrank.challenges.algorithms

import scala.io.StdIn

object FindDigits {

  def destructure(n: Int): List[Int] = n match {
    case x if x <= 0 => Nil
    case x => (x % 10) :: destructure(x / 10)
  }

  def solve(n: Int): Int = {
    destructure(n).view.filterNot(_ == 0).count(n % _ == 0)
  }

  def main(args: Array[String]) {
    val cases = StdIn.readInt
    for (i <- 0 until cases) {
      val n = StdIn.readInt
      println(solve(n))
    }
  }
}
