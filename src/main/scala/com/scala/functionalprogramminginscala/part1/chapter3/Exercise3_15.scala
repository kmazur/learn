package com.scala.functionalprogramminginscala.part1.chapter3

/**
  * Write a function that concatenates a list of lists into a single list.
  * Its runtime should be linear in the total length of all lists. Try to use functions we have already defined.
  */
object Exercise3_15 {

  def main(args: Array[String]): Unit = {
    println(concat(List(List(1, 2, 3), List(4, 5, 6))))
  }

  def concat[A](as: List[List[A]]): List[A] = {
    as.foldRight(Nil: List[A])((list: List[A], racc: List[A]) => {
      val reversed = list.foldLeft(Nil: List[A])((acc, elem) => elem :: acc)
      racc.foldRight(reversed)((elem: A, all: List[A]) => elem :: all)
    }).reverse

    /*as.foldLeft(Nil:List[A])((lacc:List[A], list:List[A]) => {
      lacc.foldRight(list)((elem, racc) => elem :: racc)
    })*/
  }

}
