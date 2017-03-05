package com.scala.functionalprogramminginscala.part1.chapter3

/**
  * Write a function flatMap that works like map except that the function given will return a list
  * instead of a single result, and that list should be inserted into the final resulting list. Here is its signature:
  *
  * def flatMap[A,B](as: List[A])(f: A => List[B]): List[B]
  * For instance, flatMap(List(1,2,3))(i => List(i,i)) should result in List(1,1,2,2,3,3).
  */
object Exercise3_20 {

  def main(args: Array[String]): Unit = {
    println(flatMap(List(1, 2, 3))(i => List(i, i)))
  }

  def flatMap[A, B](as: List[A])(f: A => List[B]): List[B] = {
    /*as.foldRight(Nil: List[B])((elem: A, acc: List[B]) => {
      acc.reverse.foldRight(f(elem).reverse)((elem: B, acc: List[B]) => elem :: acc).reverse
    })*/
    as.foldLeft(Nil: List[B])((acc: List[B], elem: A) => {
      acc.foldRight(f(elem))((elem, acc) => elem :: acc)
    })
  }

}
