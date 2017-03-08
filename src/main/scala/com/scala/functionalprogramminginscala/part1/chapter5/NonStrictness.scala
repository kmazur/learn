package com.scala.functionalprogramminginscala.part1.chapter5

import scala.annotation.tailrec

object NonStrictness {


  sealed trait Stream[+A] {
    def headOption: Option[A] = this match {
      case Empty => None
      case Cons(head, _) => Some(head())
    }

    def tailOption: Option[Stream[A]] = this match {
      case Empty => None
      case Cons(_, tail) => Some(tail())
    }

    /**
      * Exercise 5.1
      * Write a function to convert a Stream to a List, which will force its evaluation and let you look at it in the REPL.
      * You can convert to the regular List type in the standard library.
      * You can place this and other functions that operate on a Stream inside the Stream trait.
      */
    def toListRecursive: List[A] = {
      headOption.flatMap(h => tailOption.map(tail => h :: tail.toList)).getOrElse(Nil)
    }

    def toList: List[A] = {
      @tailrec
      def loop(s: Stream[A], acc: List[A]): List[A] = s match {
        case Cons(h, t) => loop(t(), h() :: acc)
        case _ => acc
      }

      loop(this, Nil).reverse
    }

    /**
      * Exercise 5.2
      * Write the function take(n) for returning the first n elements of a Stream, and drop(n) for skipping the first n elements of a Stream.
      */
    def take(n: Int): Stream[A] = {
      this match {
        case Cons(h, t) if n > 1 => Stream.cons(h(), t().take(n - 1))
        case Cons(h, _) if n == 1 => Stream.cons(h(), Empty)
        case Empty => Empty
      }
      /*
      @tailrec
      def loop(s: Stream[A], acc: List[A], counter: Int): List[A] = s match {
        case Cons(h, t) if counter > 0 => loop(t(), h() :: acc, counter - 1)
        case _ => acc
      }
      loop(this, Nil, n).reverse*/
    }

    def drop(n: Int): Stream[A] = this match {
      /*case Cons(_, t) if n > 0  => t().drop(n - 1)
      case s@Cons(_, _) if n == 0 => s
      case Empty => Empty*/
      case Cons(_, t) if n > 0 => t().drop(n - 1)
      case _ => this
    }


    /**
      * Exercise 5.3
      * Write the function takeWhile for returning all starting elements of a Stream that match the given predicate.
      */
    def takeWhile(p: A => Boolean): Stream[A] = this match {
      case Cons(h, t) if p(h()) => Stream.cons(h(), t().takeWhile(p))
      case _ => Empty
    }
  }

  case object Empty extends Stream[Nothing]

  case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

  object Stream {
    def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
      lazy val head = hd
      lazy val tail = tl
      Cons(() => head, () => tail)
    }

    def empty[A]: Stream[A] = Empty

    def apply[A](as: A*): Stream[A] = {
      if (as.isEmpty) empty else cons(as.head, apply(as.tail: _*))
    }
  }


  def main(args: Array[String]): Unit = {

    val stream: Stream[Int] = Stream(1, 2, 3, 4)
    println(stream.toList) // List(1, 2, 3, 4)
    println(stream.take(2).toList) // List(1, 2)
    println(stream.drop(2).toList) // List(3, 4)
    println(stream.drop(4).toList) // List()
  }

}
