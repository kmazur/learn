package com.scala.scalafortheimpatient

import scala.util.Try

object UsefulMethods {

  def main(args: Array[String]): Unit = {


    // Lifting

    val arr = Array.fill[Int](5)(0)
    // lift -> A => Option[B] on PartialFunction
    val elementAt5 = arr.lift(5).getOrElse(-1) // -1
    val elementAt0 = arr.lift(0).getOrElse(-1) // 0
    println(elementAt5)
    println(elementAt0)



    val (q, r) = BigInt(10) /% 3 // (3, 1)



    // Option
    val dontKnowIfItsNull = null
    val noneVal = Option(dontKnowIfItsNull) // None
    val someVal = Option(1)                 // Some(1)

    val partial1: PartialFunction[Int, Int] = { case 1 => 1 case 2 => 4}
    val fullFunction1: (Int) => Int = { case 1 => 1 case 2 => 4 case _ => 0}
    partial1.isDefinedAt(1) // true
    partial1.apply(1)
    val partialLifted: (Int) => Option[Int] = partial1.lift




    val tryInt1 = Try("abc".toInt) // Failure(NumberFormatException)
    val tryInt2 = Try("123".toInt) // Success(123)


    // variable binding with @
    // a@Type assign to a the whole type
    for(x@Some(y) <- List(None, Some(1))) println(x, y) // prints (Some(1), 1)
    val elem@Some(x): Some[Int] = Some(1)
    println(s"$elem, $x")
    val Array(h, i, rest @ _*) = Array(1, 2, 3, 4, 5)

  }

}
