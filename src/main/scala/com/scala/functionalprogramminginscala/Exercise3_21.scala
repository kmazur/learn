package com.scala.functionalprogramminginscala

/**
  * Use flatMap to implement filter.
  */
object Exercise3_21 {

  def main(args: Array[String]): Unit = {
    println(filter(List(1, 2, 3, 4, 5, 6))(_ % 2 == 0))
  }

  def filter[A](as: List[A])(f: A => Boolean): List[A] = {
    as.flatMap({
      case x if f(x) => List(x)
      case _ => Nil
    })
  }

}
