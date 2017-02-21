package com.scala

object UsefulMethods {

  def main(args: Array[String]): Unit = {


    // Lifting

    val arr = Array.fill[Int](5)(0)
    // lift -> A => Option[B] on PartialFunction
    val elementAt5 = arr.lift(5).getOrElse(-1) // -1
    val elementAt0 = arr.lift(0).getOrElse(-1) // 0
    println(elementAt5)
    println(elementAt0)

  }

}
