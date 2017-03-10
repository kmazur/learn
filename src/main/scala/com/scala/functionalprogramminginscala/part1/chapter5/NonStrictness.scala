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

    def foldRight[B](init: => B)(f: (A, => B) => B): B = this match {
      case Cons(h, t) => f(h(), t().foldRight(init)(f))
      case _ => init
    }

    /**
      * Exercise 5.4
      * Implement forAll, which checks that all elements in the Stream match a given predicate.
      * Your implementation should terminate the traversal as soon as it encounters a nonmatching value.
      */
    def forAll(p: A => Boolean): Boolean = this match {
      case Cons(h, t) if p(h()) => t().forAll(p)
      case Empty => true
      case _ => false
    }


    /**
      * Exercise 5.13
      * Use unfold to implement map, take, takeWhile, zipWith (as in chapter 3), and zipAll.
      * The zipAll function should continue the traversal as long as
      * either stream has more elements—it uses Option to indicate whether each stream has been exhausted.
      */
    def unfoldMap[B](f: A => B): Stream[B] = Stream.unfold(this)({
      case Cons(h, t) => Some((f(h()), t()))
      case _ => None
    })

    def unfoldTake(n: Int): Stream[A] = Stream.unfold((this, n))({
      case (Cons(h, t), n) if n > 1 => Some((h(), (t(), n - 1)))
      case (Cons(h, t), n) if n == 1 => Some((h(), (Empty, 0)))
      case _ => None
    })

    def unfoldTakeWhile(p: A => Boolean): Stream[A] = Stream.unfold(this)({
      case Cons(h, t) if p(h()) => Some(h(), t())
      case _ => None
    })

    def zipAll[B](other: Stream[B]): Stream[(A, B)] = Stream.unfold((this, other))({
      case (Cons(h1, t1), Cons(h2, t2)) => Some((h1(), h2()), (t1(), t2()))
      case _ => None
    })

    def zipAll2[B](other: Stream[B]): Stream[(Option[A], Option[B])] = Stream.unfold((this, other))({
      case (Cons(h1, t1), Cons(h2, t2)) => Some((Some(h1()), Some(h2())), (t1(), t2()))
      case (Cons(h1, t1), Empty) => Some((Some(h1()), None), (t1(), Empty))
      case (Empty, Cons(h2, t2)) => Some((None, Some(h2())), (Empty, t2()))
      case _ => None
    })


    /**
      * Exercise 5.5
      * Use foldRight to implement takeWhile.
      */
    def takeWhileWithFold(p: A => Boolean): Stream[A] = {
      foldRight(Stream.empty[A])((a, b) => if (p(a)) Stream.cons(a, b) else Stream.empty)
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

    /**
      * Exercise 5.8
      * Generalize ones slightly to the function constant, which returns an infinite Stream of a given value.
      */
    def constant[A](a: A): Stream[A] = Stream.cons[A](a, constant(a))

    /**
      * Exercise 5.9
      * Write a function that generates an infinite stream of integers, starting from n, then n + 1, n + 2, and so on.[7]
      */
    def from(n: Int): Stream[Int] = Stream.cons(n, from(n + 1))

    /**
      * Exercise 5.10
      * Write a function fibs that generates the infinite stream of Fibonacci numbers: 0, 1, 1, 2, 3, 5, 8, and so on.
      */
    def fibs(): Stream[Int] = {
      def fibs(n1: Int, n2: Int): Stream[Int] = Stream.cons(n1 + n2, fibs(n2, n1 + n2))

      Stream.cons(0, Stream.cons(1, fibs(0, 1)))
    }


    /**
      * Exercise 5.11
      * Write a more general stream-building function called unfold.
      * It takes an initial state, and a function for producing both the next state and the next value in the generated stream.
      */
    def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] = {
      f(z).map(e =>
        Stream.cons(e._1, unfold(e._2)(f))
      ).getOrElse(Empty)
    }

    // Recursive   -> consumes data
    // Corecursive -> produces data

    /**
      * Exercise 5.12
      * Write fibs, from, constant, and ones in terms of unfold.
      */
    def unfoldFibs(): Stream[Int] = unfold((0, 1))(n => Some((n._1, (n._2, n._1 + n._2))))

    def unfoldFrom(n: Int): Stream[Int] = unfold(n)(state => Some(state + 1, state + 1))

    def unfoldConstant(n: Int): Stream[Int] = unfold(n)(state => Some(state, state))

    def unfoldOnes(): Stream[Int] = unfoldConstant(1)

  }


  def main(args: Array[String]): Unit = {

    val stream: Stream[Int] = Stream(1, 2, 3, 4)
    println(stream.toList) // List(1, 2, 3, 4)
    println(stream.take(2).toList) // List(1, 2)
    println(stream.drop(2).toList) // List(3, 4)
    println(stream.drop(4).toList) // List()

    println(stream.forAll(_ > 0)) // true
    println(stream.forAll(_ == 0)) // false

    println(Stream.constant(3).take(5).toList.sum) // 15
    println(Stream.fibs().take(10).toList)
    println(Stream.unfoldFibs().take(10).toList)
    println(Stream.unfoldFrom(10).take(10).toList)
    println(Stream.unfoldConstant(10).take(10).toList)


  }

}
