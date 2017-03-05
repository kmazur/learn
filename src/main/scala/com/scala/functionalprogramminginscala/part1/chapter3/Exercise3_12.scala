package com.scala.functionalprogramminginscala.part1.chapter3

/**
  * Write a function that returns the reverse of a list (given List(1,2,3) it returns List(3,2,1)).
  * See if you can write it using a fold.
  */
object Exercise3_12 {

  def main(args: Array[String]): Unit = {
    println(reverse(List(1, 2, 3)))
  }


  def reverse[A](as: List[A]): List[A] = {
    as.foldLeft(Nil:List[A])((acc, next) => next :: acc)
  }

}
