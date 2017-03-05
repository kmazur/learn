package com.scala.functionalprogramminginscala.part1.chapter3

/**
  * Compute the length of a list using foldRight.
  *
  * def length[A](as: List[A]): Int
  */
object Exercise3_9 {

  def main(args: Array[String]): Unit = {
    println(length(List(1, 2, 3)))
    println(length(Nil))
    println(length(List(1)))
  }

  def length[A](as: List[A]): Int = {
    as.foldRight(0)((x, y) => 1 + y)
  }

}
