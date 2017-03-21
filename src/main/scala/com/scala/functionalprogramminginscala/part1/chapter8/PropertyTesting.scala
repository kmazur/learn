package com.scala.functionalprogramminginscala.part1.chapter8


import com.scala.functionalprogramminginscala.part1.chapter5.NonStrictness.Stream
import com.scala.functionalprogramminginscala.part1.chapter6.PureFunctionalState.RNG
import com.scala.functionalprogramminginscala.part1.chapter6.PureFunctionalState.RNG.Rand
import com.scala.functionalprogramminginscala.part1.chapter6.PureFunctionalState.State.State
import com.scala.functionalprogramminginscala.part1.chapter8.PropertyTesting.Prop.{Falsified, Passed, Result}


object PropertyTesting {

  type TestCases = Int

  case class Prop(run: (TestCases, RNG) => Result) {
    /**
      * Exercise 8.9
      * Now that we have a representation of Prop, implement && and || for composing Prop values.
      * Notice that in the case of failure we don’t know which property was responsible, the left or the right.
      * Can you devise a way of handling this, perhaps by allowing Prop values to be assigned a tag or label
      * which gets displayed in the event of a failure?
      */
    def &&(p: Prop): Prop = {
      Prop((tc, rng) => {
        val run1 = run(tc, rng)
        val run2 = p.run(tc, rng)
        (run1, run2) match {
          case (Falsified(x, y), Passed) => Falsified(x, y)
          case (Passed, Falsified(x, y)) => Falsified(x, y)
          case (Falsified(a, b), Falsified(x, y)) => Falsified(a, b + y)
          case _ => Passed
        }
      })
    }

    def ||(p: Prop): Prop = {
      Prop((tc, rng) => {
        val run1 = run(tc, rng)
        val run2 = p.run(tc, rng)
        (run1, run2) match {
          case (Falsified(x, y), Passed) => Passed
          case (Passed, Falsified(x, y)) => Passed
          case (Falsified(a, b), Falsified(x, y)) => Falsified(a, b + y)
          case _ => Passed
        }
      })
    }
  }

  object Prop {

    sealed trait Result {
      def isFalsified: Boolean
    }

    case object Passed extends Result {
      def isFalsified = false
    }

    case class Falsified(failure: FailedCase,
                         successes: SuccessCount) extends Result {
      def isFalsified = true
    }

    case object Proved extends Result {
      def isFalsified = false
    }

    type SuccessCount = Int
    type FailedCase = String


    def randomStream[A](g: Gen[A])(rng: RNG): Stream[A] =
      Stream.unfold(rng)(rng => Some(g.sample.run(rng)))

    def forAll[A](as: Gen[A])(f: A => Boolean): Prop = Prop {
      (n, rng) =>
        randomStream(as)(rng).zip(Stream.from(0)).take(n).map {
          case (a, i) => try {
            if (f(a)) Passed else Falsified(a.toString, i)
          } catch {
            case e: Exception => Falsified(buildMsg(a, e), i)
          }
        }.find(_.isFalsified).getOrElse(Passed)
    }

    def buildMsg[A](s: A, e: Exception): String =
      s"test case: $s\n" +
        s"generated an exception: ${e.getMessage}\n" +
        s"stack trace:\n ${e.getStackTrace.mkString("\n")}"
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
    def weighted[A](g1: (Gen[A], Double), g2: (Gen[A], Double)): Gen[A] = {
      val prob = g1._2 / (g1._2 + g2._2)

      val double: Rand[Double] = RNG.double
      Gen(RNG.flatMap(double)((randValue) => {
        val generator: Gen[A] = if (randValue < prob) {
          g1._1
        } else {
          g2._1
        }
        generator.sample
      }))
    }

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
