package com.hackerrank.challenges.algorithms

import scala.io.StdIn

object WeightedUniformStrings {

  def decompose(s: String): List[String] = s match {
    case x if x.isEmpty => Nil
    case x =>
      val parts = x.span(_ == x.head)
      parts._1 :: decompose(parts._2)
  }

  def main(args: Array[String]) {
    val s = StdIn.readLine
    val keySet = decompose(s)
      .flatMap(group => group.indices.map(i => (group.head, i + 1)))
      .groupBy(group => (1 + (group._1 - 'a')) * group._2)
      .keySet

    for (i <- 0 until StdIn.readInt) {
      val n = StdIn.readInt
      println(if (keySet.contains(n)) "Yes" else "No")
    }
  }
}
