package com.scala.functionalprogramminginscala.part1.chapter3

/**
  * Write a function that accepts two lists and constructs a new list by adding corresponding elements.
  * For example, List(1,2,3) and List(4,5,6) become List(5,7,9).
  */
object Exercise3_22 {

  def main(args: Array[String]): Unit = {
    println(add1(List(1, 2, 3), List(4, 5, 6)))
    println(add2(List(1, 2, 3), List(4, 5, 6)))
  }

  def add1(a: List[Int], b: List[Int]): List[Int] = {
    if (a.length != b.length) {
      throw new IllegalArgumentException("Can't add lists with different lengths")
    }

    a.foldRight((Nil: List[Int], b.reverse))((elem: Int, acc: (List[Int], List[Int])) => {
      ((elem + acc._2.head) :: acc._1, acc._2.tail)
    })._1
  }

  def add2(a: List[Int], b: List[Int]): List[Int] = {
    if (a.length != b.length) {
      throw new IllegalArgumentException("Can't add lists with different lengths")
    }

    def loop(a: List[Int], b: List[Int]): List[Int] = {
      (a, b) match {
        case (Nil, Nil) => Nil
        case (ah :: at, bh :: bt) => (ah + bh) :: loop(at, bt)
      }
    }
    loop(a, b)
  }

}
