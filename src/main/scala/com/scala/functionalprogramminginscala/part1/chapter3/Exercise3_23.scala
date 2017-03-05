package com.scala.functionalprogramminginscala.part1.chapter3

/**
  * Generalize the function you just wrote so that it’s not specific to integers or addition.
  * Name your generalized function zipWith.
  */
object Exercise3_23 {

  def main(args: Array[String]): Unit = {
    println(zipWith(List(1, 2, 3), List("a", "b", "c")))
  }

  def zipWith[A, B](a: List[A], b: List[B]): List[(A, B)] = {
    if (a.length != b.length) {
      throw new IllegalArgumentException("Can't zip lists with different lengths")
    }

    def loop(a: List[A], b: List[B]): List[(A, B)] = {
      (a, b) match {
        case (Nil, Nil) => Nil
        case (ah :: at, bh :: bt) => (ah, bh) :: loop(at, bt)
      }
    }
    loop(a, b)
  }

}
