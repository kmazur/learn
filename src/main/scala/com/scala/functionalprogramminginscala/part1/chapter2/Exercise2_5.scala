package com.scala.functionalprogramminginscala.part1.chapter2

/**
  * Implement the higher-order function that composes two functions.
  *
  * def compose[A,B,C](f: B => C, g: A => B): A => C
  */
object Exercise2_5 {

  def main(args: Array[String]): Unit = {
    def toString(n: Int): String = n.toString
    def toInt(str: String): Int = str.toInt

    val composed: (Int) => Int = compose(toInt, toString)
    println(composed(1))
  }

  def compose[A,B,C](f: B => C, g: A => B): A => C = (a: A) => f(g(a))

}
