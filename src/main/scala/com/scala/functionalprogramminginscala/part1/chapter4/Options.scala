package com.scala.functionalprogramminginscala.part1.chapter4

import scala.util.Try

object Options {

  /**
    * Exercise 4.1
    * Implement all of the preceding functions on Option. As you implement each function,
    * try to think about what it means and in what situations you’d use it.
    * We’ll explore when to use each of these functions next
    */
  sealed trait Option[+A] {
    def map[B](f: A => B): Option[B] = this match {
      case None => None
      case Some(x) => Some(f(x))
    }

    def flatMap[B](f: A => Option[B]): Option[B] = map(f).getOrElse(None)

    def getOrElse[B >: A](default: => B): B = this match {
      case None => default
      case Some(x) => x
    }

    def orElse[B >: A](ob: => Option[B]): Option[B] = map(Some(_)).getOrElse(ob)

    def filter(f: A => Boolean): Option[A] = this match {
      case s@Some(x) if f(x) => s
      case _ => None
    }
  }

  case class Some[+A](get: A) extends Option[A]
  case object None extends Option[Nothing]

  /**
    * Exercise 4.2
    */
  def mean(s: Seq[Double]): Option[Double] =
    Some(s).filter(_.nonEmpty).map(seq => seq.sum / seq.size)

  def variance(xs: Seq[Double]): Option[Double] = {
    mean(xs).flatMap(m => mean(xs.map(x => math.pow(x - m, 2))))
  }

  def lift[A, B](f: A => B): Option[A] => Option[B] = (a: Option[A]) => a.map(f)

  /**
    * Exercise 4.3
    * Write a generic function map2 that combines two Option values using a binary function. If either Option value is None, then the return value is too. Here is its signature:
    *
    * def map2[A,B,C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C]
    */
  def map2[A,B,C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] =
    a.flatMap(av => b.map(bv => f(av, bv)))

  /**
    * Exercise 4.4
    * Write a function sequence that combines a list of Options into one Option
    * containing a list of all the Some values in the original list.
    * If the original list contains None even once, the result of the function should be None;
    * otherwise the result should be Some with a list of all the values. Here is its signature
    */
  def sequence[A](a: List[Option[A]]): Option[List[A]] =
    Some(a.foldRight(Nil:List[A])((o, acc) => o.map(v => v :: acc).getOrElse(acc)))

  /**
    * Exercise 4.5
    * Implement this function. It’s straightforward to do using map and sequence,
    * but try for a more efficient implementation that only looks at the list once.
    * In fact, implement sequence in terms of traverse
    */
  def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] =
    //Some(a.foldRight(Nil:List[B])((value, acc) => f(value).map(_ :: acc).getOrElse(acc)))
    a.foldRight[Option[List[B]]](Some(Nil:List[B]))((value, acc) => map2(f(value), acc)(_ :: _))


  def main(args: Array[String]): Unit = {

    val absO: (Option[Double]) => Option[Double] = lift(math.abs)

    val lifted: (Int) => scala.Option[Int] = List(1, 2 ,3).lift
    val value: scala.Option[Int] = lifted(-100)


    val seq = sequence(List(Some(1), Some(2), None, Some(4)))
    println(seq) // prints Some(List(1, 2, 4))


    val list1: Option[List[Int]] = traverse(List(1, 2, 3))((a: Int) => if(a % 2 == 0) Some(a) else None)
    println(list1)

    // map2 can be rewriten as:
    def map2[A,B,C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] =
      for {
        av <- a
        bv <- b
      } yield f(av, bv)
  }

}
