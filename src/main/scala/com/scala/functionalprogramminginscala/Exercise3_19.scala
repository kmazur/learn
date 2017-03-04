package com.scala.functionalprogramminginscala

/**
  * Write a function filter that removes elements from a list unless they satisfy a given predicate. Use it to remove all odd numbers from a List[Int].
  *
  * def filter[A](as: List[A])(f: A => Boolean): List[A]
  */
object Exercise3_19 {

  def main(args: Array[String]): Unit = {
    println(filter(List(1, 2, 3, 4, 5, 6))(_ % 2 == 0))
  }

  def filter[A](as: List[A])(f: A => Boolean): List[A] = {
    as.foldRight(Nil: List[A])((elem, acc) => if (f(elem)) elem :: acc else acc)
  }

}
