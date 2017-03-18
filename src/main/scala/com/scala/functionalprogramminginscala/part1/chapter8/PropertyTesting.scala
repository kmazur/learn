package com.scala.functionalprogramminginscala.part1.chapter8


import com.scala.functionalprogramminginscala.part1.chapter6.PureFunctionalState.RNG
import com.scala.functionalprogramminginscala.part1.chapter6.PureFunctionalState.State.State
import com.scala.functionalprogramminginscala.part1.chapter8.PropertyTesting.Prop.{FailedCase, SuccessCount}

import scala.util.Random

object PropertyTesting {

  trait Prop {
    def check: Either[(FailedCase, SuccessCount), SuccessCount]
  }

  object Prop {
    type SuccessCount = Int
    type FailedCase = String
  }

  case class Gen[A](sample: State[RNG, A]) {

    /**
      * Exercise 8.6
      * Implement flatMap, and then use it to implement this more dynamic version of listOfN. Put flatMap and listOfN in the Gen class.
      */
    def flatMap[B](f: A => Gen[B]): Gen[B] = {
      Gen[B]((s) => {
        val (a, s2) = sample(s)
        val (b1, s3) = f(a).sample(s2)
        (b1, s3)
      })
    }

    def listOfN(size: Gen[Int]): Gen[List[A]] = {
      size.flatMap(n => Gen.listOfN(n, this))
    }
  }

  object Gen {
    def choose(start: Int, stopExclusive: Int): Gen[Int] = ???

    /**
      * Exercise 8.5
      * Let’s see what else we can implement using this representation of Gen. Try implementing unit, boolean, and listOfN.
      */
    def unit[A](a: => A): Gen[A] = Gen((s) => (a, s))

    def boolean: Gen[Boolean] = Gen((s) => (Math.random() > 0.5, s))

    def listOfN[A](n: Int, g: Gen[A]): Gen[List[A]] = {
      Gen(RNG.sequence((0 until n).map(i => g.sample).toList))
    }

    /**
      * Exercise 8.7
      * Implement union, for combining two generators of the same type into one, by pulling values from each generator with equal likelihood.
      */
    def union[A](g1: Gen[A], g2: Gen[A]): Gen[A] = {
      boolean.flatMap(which => {
        if (which) {
          g1
        } else {
          g2
        }
      })
    }

    /**
      * Exercise 8.8
      * Implement weighted, a version of union that accepts a weight for each Gen and generates values from each Gen with probability proportional to its weight.
      */
    def weighted[A](g1: (Gen[A],Double), g2: (Gen[A],Double)): Gen[A] = ???

  }

  def main(args: Array[String]): Unit = {
    val types = Array(1, 4, 4, 4, 5, 3)
    val freq = types.foldLeft(Array(0, 0, 0, 0, 0))((acc, t) => {
      acc(t - 1) += 1
      acc
    })
    println(freq.indexOf(freq.max))
  }

}
