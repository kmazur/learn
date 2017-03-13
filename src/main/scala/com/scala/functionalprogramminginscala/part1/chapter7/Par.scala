package com.scala.functionalprogramminginscala.part1.chapter7

import java.util.concurrent.{Callable, ExecutorService, Future, TimeUnit}

import scala.collection.immutable.Seq

object Par {

  type Par[A] = ExecutorService => Future[A]

  def unit[A](a: A): Par[A] = (es: ExecutorService) => UnitFuture(a)

  private case class UnitFuture[A](get: A) extends Future[A] {
    def isDone = true

    def get(timeout: Long, units: TimeUnit) = get

    def isCancelled = false

    def cancel(evenIfRunning: Boolean): Boolean = false
  }

  /**
    * Exercise 7.1
    * Par.map2 is a new higher-order function for combining the result of two parallel computations.
    * What is its signature? Give the most general signature possible (don’t assume it works only for Int).
    */
  def map2[A, B, C](a: Par[A], b: Par[B])(f: (A, B) => C): Par[C] =
    (es: ExecutorService) => {
      val af = a(es)
      val bf = b(es)
      UnitFuture(f(af.get, bf.get))
    }

  def fork[A](a: => Par[A]): Par[A] = es => es.submit(new Callable[A] {
    def call = a(es).get
  })

  def lazyUnit[A](a: => A): Par[A] = fork(unit(a))

  /**
    * Exercise 7.4
    * This API already enables a rich set of operations.
    * Here’s a simple example: using lazyUnit, write a function to convert any function A => B to one that evaluates its result asynchronously.
    */
  def asyncF[A, B](f: A => B): A => Par[B] = {
    a => lazyUnit(f(a))
  }

  def map[A, B](pa: Par[A])(f: A => B): Par[B] =
    map2(pa, unit(()))((a, _) => f(a))

  def sortPar(parList: Par[List[Int]]) = map(parList)(_.sorted)


  def parMap[A, B](ps: List[A])(f: A => B): Par[List[B]] = fork {
    val fbs: List[Par[B]] = ps.map(asyncF(f))
    sequence(fbs)
  }

  /**
    * Exercise 7.5
    * Hard: Write this function, called sequence. No additional primitives are required. Do not call run.
    */
  def sequence[A](ps: List[Par[A]]): Par[List[A]] = {
    ps.foldRight(unit[List[A]](Nil))((p, acc) => {
      map2(acc, p)((list, elem) => elem :: list)
    })
  }

  /**
    * Exercise 7.6
    * Implement parFilter, which filters elements of a list in parallel.
    */
  def parFilter[A](as: List[A])(f: A => Boolean): Par[List[A]] = {
    val mapped: List[Par[List[A]]] = as.map(asyncF((a) => {
      if (f(a)) List(a)
      else Nil
    }))
    map(sequence(mapped))(f => f.flatten)
  }

  def delay[A](fa: => Par[A]): Par[A] =
    es => fa(es)

  def sum(ints: IndexedSeq[Int]): Par[Int] =
    if (ints.size <= 1)
      Par.unit(ints.headOption getOrElse 0)
    else {
      val (l, r) = ints.splitAt(ints.length / 2)
      Par.map2(sum(l), sum(r))(_ + _)
    }


  def main(args: Array[String]): Unit = {

  }

}
