package com.scala.functionalprogramminginscala

/**
  * Can you write foldLeft in terms of foldRight? How about the other way around?
  * Implementing foldRight via foldLeft is useful because it lets us implement foldRight tail-recursively,
  * which means it works even for large lists without overflowing the stack.
  */
object Exercise3_13 {

  def main(args: Array[String]): Unit = {
    println(reverse(List(1, 2, 3)))
  }

  def foldLeft[A, B](as: List[A], z: B)(f: (B, A) => B): B = {
    as.reverse.foldRight(z)((a, b) => f(b, a))
  }

  def foldRight[A, B](as: List[A], z: B)(f: (B, A) => B): B = {
    as.reverse.foldLeft(z)((a, b) => f(a, b))
  }

  def reverse[A](as: List[A]): List[A] = {
    foldLeft(as, Nil: List[A])((acc, next) => next :: acc)
  }

}
