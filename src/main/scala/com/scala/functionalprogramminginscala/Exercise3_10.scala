package com.scala.functionalprogramminginscala

/**
  * Our implementation of foldRight is not tail-recursive and will result in a StackOverflowError
  * for large lists (we say it’s not stack-safe). Convince yourself that this is the case, and then write another
  * general list-recursion function, foldLeft, that is tail-recursive,
  * using the techniques we discussed in the previous chapter. Here is its signature:
  *
  * Again, foldLeft is defined as a method of List in the Scala standard library,
  * and it is curried similarly for better type inference, so you can write mylist.foldLeft(0.0)(_ + _).
  *
  * def foldLeft[A,B](as: List[A], z: B)(f: (B, A) => B): B
  */
object Exercise3_10 {

  def main(args: Array[String]): Unit = {
    println(foldLeft(List(1, 2, 3), 0)(_ + _))
  }

  def foldLeft[A,B](as: List[A], z: B)(f: (B, A) => B): B = {
    def loop(acc: B, list: List[A]): B = {
      list match {
        case Nil => acc
        case h :: t => loop(f(acc, h), t)
      }
    }
    loop(z, as)
  }

}
