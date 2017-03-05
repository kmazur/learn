package com.scala.functionalprogramminginscala.part1.chapter2

/**
  * Implement uncurry, which reverses the transformation of curry.
  * Note that since => associates to the right, A => (B => C) can be written as A => B => C.
  *
  * def uncurry[A,B,C](f: A => B => C): (A, B) => C
  */
object Exercise2_4 {

  def main(args: Array[String]): Unit = {
    def sum(a: Int)(b: Int): Int = a + b

    val sumFunc: (Int) => (Int) => Int = sum
    val uncurried: (Int, Int) => Int = uncurry(sumFunc)
  }

  def uncurry[A,B,C](f: A => B => C): (A, B) => C = {
    (a: A, b: B) => f(a)(b)
  }

}
