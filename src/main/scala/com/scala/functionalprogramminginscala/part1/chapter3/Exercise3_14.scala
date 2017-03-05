package com.scala.functionalprogramminginscala.part1.chapter3

/**
  * Implement append in terms of either foldLeft or foldRight
  */
object Exercise3_14 {

  def main(args: Array[String]): Unit = {
    println(appendL(List(1, 2, 3), 1))
    println(appendR(List(1, 2, 3), 1))
  }

  def appendL[A](as: List[A], elem: A): List[A] = {
    val reversed = as.foldLeft(Nil: List[A])((acc, e) => e :: acc)
    val appended = elem :: reversed
    appended.foldLeft(Nil: List[A])((acc, e) => e :: acc)
  }

  def appendR[A](as: List[A], elem: A): List[A] = {
    as.foldRight(List[A](elem))((e, acc) => e :: acc)
  }

}
