package com.scala.functionalprogramminginscala

/**
  * Write a function that turns each value in a List[Double] into a String.
  * You can use the expression d.toString to convert some d: Double to a String.
  */
object Exercise3_17 {

  def main(args: Array[String]): Unit = {
    val strings = toString(List(1, 2, 3))
    println(strings)
    println(strings.map(_.getClass))
  }

  def toString(as: List[Double]): List[String] = {
    as.foldRight(Nil:List[String])((elem, acc) => elem.toString :: acc)
  }

}
