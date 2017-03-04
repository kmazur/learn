package com.scala.scalafortheimpatient

object Tuples {
  def main(args: Array[String]): Unit = {

    val t2 = (1, 2)
    val t3 = (1, 2, 3)

    println(t2._1)
    println(t3._3)

    val (a, b, _) = t3
    val Array(c, d) = Array(1, 2)

    "New York".partition(_.isUpper) // ("NY", "ew ork")


  }
}
