package com.scala.functionalprogramminginscala

/**
  * Write a function that transforms a list of integers by adding 1 to each element.
  * (Reminder: this should be a pure function that returns a new List!)
  */
object Exercise3_16 {

  def main(args: Array[String]): Unit = {
    println(addOne(List(1, 2, 3)))
  }

  def addOne(as: List[Int]): List[Int] = {
    as.foldRight(Nil:List[Int])((elem, acc) => (elem + 1) :: acc)
  }

}
