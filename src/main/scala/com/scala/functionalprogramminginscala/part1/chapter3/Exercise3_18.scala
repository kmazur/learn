package com.scala.functionalprogramminginscala.part1.chapter3

/**
  * Write a function map that generalizes modifying each element in a list while maintaining the structure of the list. Here is its signature:[12]
  *
  * In the standard library, map and flatMap are methods of List.
  *
  * def map[A,B](as: List[A])(f: A => B): List[B]
  */
object Exercise3_18 {

  def main(args: Array[String]): Unit = {
    println(map(List(1, 2, 3, 4, 5, 6))(_ * 2))
  }

  def map[A,B](as: List[A])(f: A => B): List[B] = {
    as.foldRight(Nil:List[B])((elem, acc) => f(elem) :: acc)
  }

}
