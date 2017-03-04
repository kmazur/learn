package com.scala.functionalprogramminginscala

/**
  * Implement isSorted, which checks whether an Array[A] is sorted according to a given comparison function:
  *
  * def isSorted[A](as: Array[A], ordered: (A,A) => Boolean): Boolean
  *
  */
object Exercise2_2 {

  def main(args: Array[String]): Unit = {
    println(isSorted(Array(1, 2, 3, 4), (a:Int, b:Int) => a <= b))
    println(isSorted(Array(1, 2, 3, 4), (a:Int, b:Int) => a >= b))
    println(isSorted(Array(), (a:Int, b:Int) => a < b))
    println(isSorted(Array(1), (a:Int, b:Int) => a < b))
  }

  def isSorted[A](as: Array[A], ordered: (A, A) => Boolean): Boolean = {
    if (as.length <= 1) {
      true
    } else {
      def check(n: Int): Boolean = {
        if(n >= as.length)                 true
        else if(ordered(as(n-1), as(n)))   check(n+1)
        else                               false
      }
      check(1)
    }
  }

}
