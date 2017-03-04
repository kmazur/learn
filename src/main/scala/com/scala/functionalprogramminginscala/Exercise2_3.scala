package com.scala.functionalprogramminginscala

/**
  * Let’s look at another example, currying,[9] which converts a function f of two arguments into a function of one argument
  * that partially applies f.
  * Here again there’s only one implementation that compiles. Write this implementation.
  *
  * This is named after the mathematician Haskell Curry, who discovered the principle.
  * It was independently discovered earlier by Moses Schoenfinkel, but Schoenfinkelization didn’t catch on.
  *
  * def curry[A,B,C](f: (A, B) => C): A => (B => C)
  */
object Exercise2_3 {

  def main(args: Array[String]): Unit = {
    def sum(a: Int, b: Int): Int = a + b
    val curried: (Int) => (Int) => Int = curry(sum)
    val partial: (Int) => Int = curried(1)

    println(curried(1)(2))
   }

  def curry[A, B, C](f: (A, B) => C): A => (B => C) = (a: A) => (b: B) => f(a, b)

}
