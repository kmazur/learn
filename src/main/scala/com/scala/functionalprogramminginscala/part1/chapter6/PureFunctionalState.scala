package com.scala.functionalprogramminginscala.part1.chapter6

object PureFunctionalState {

  trait RNG {
    def nextInt: (Int, RNG)
  }

  case class SimpleRNG(seed: Long) extends RNG {
    def nextInt: (Int, RNG) = {
      val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
      val nextRNG = SimpleRNG(newSeed)
      val n = (newSeed >>> 16).toInt
      (n, nextRNG)
    }
  }

  object RNG {
    /**
      * Exercise 6.1
      * Write a function that uses RNG.nextInt to generate a random integer between 0 and Int.maxValue (inclusive).
      * Make sure to handle the corner case when nextInt returns Int.MinValue, which doesn’t have a non-negative counterpart.
      */
    def nonNegativeInt(rng: RNG): (Int, RNG) = {
      val (i, rng2) = rng.nextInt
      if (i < 0) {
        (-(i + 1), rng2)
      } else {
        (i, rng2)
      }
    }

    /**
      * Exercise 6.2
      * Write a function to generate a Double between 0 and 1, not including 1.
      * Note: You can use Int.MaxValue to obtain the maximum positive integer value, and you can use x.toDouble to convert an x: Int to a Double.
      */
    def double(rng: RNG): (Double, RNG) = {
      val (i, rng2) = nonNegativeInt(rng)
      (i.toDouble / (Int.MaxValue.toDouble + 1), rng2)
    }

    /**
      * Exercise 6.3
      * Write functions to generate an (Int, Double) pair, a (Double, Int) pair, and a (Double, Double, Double) 3-tuple.
      * You should be able to reuse the functions you’ve already written.
      */
    def intDouble(rng: RNG): ((Int, Double), RNG) = {
      val (i, r1) = rng.nextInt
      val (d, r2) = double(r1)
      ((i, d), r2)
    }

    def doubleInt(rng: RNG): ((Double, Int), RNG) = {
      val ((i, d), r) = intDouble(rng)
      ((d, i), r)
    }

    def double3(rng: RNG): ((Double, Double, Double), RNG) = {
      val (d1, r1) = double(rng)
      val (d2, r2) = double(r1)
      val (d3, r3) = double(r2)
      ((d1, d2, d3), r3)
    }

    /**
      * Exercise 6.4
      * Write a function to generate a list of random integers.
      */
    def ints(count: Int)(rng: RNG): (List[Int], RNG) = {
      (0 until count).foldLeft[(List[Int], RNG)]((Nil, rng))((acc, elem) => {
        val (i, r) = acc._2.nextInt
        (i :: acc._1, r)
      })
    }

    type Rand[+A] = RNG => (A, RNG)

    val int: Rand[Int] = _.nextInt

    def unit[A](a: A): Rand[A] = rng => (a, rng)

    def map[A, B](s: Rand[A])(f: A => B): Rand[B] =
      rng => {
        val (a, rng2) = s(rng)
        (f(a), rng2)
      }

    def nonNegativeEven: Rand[Int] = map(nonNegativeInt)(i => i - i % 2)

    /**
      * Exercise 6.5
      * Use map to reimplement double in a more elegant way. See exercise 6.2.
      */
    def double2: Rand[Double] = map(nonNegativeInt)(i => i.toDouble / (Int.MaxValue.toDouble + 1))


    /**
      * Exercise 6.6
      * Write the implementation of map2 based on the following signature.
      * This function takes two actions, ra and rb, and a function f for combining their results, and returns a new action that combines them:
      */
    def map2[A, B, C](ra: Rand[A], rb: Rand[B])(f: (A, B) => C): Rand[C] = {
      rng => {
        val (a, ra1) = ra(rng)
        val (b, ra2) = rb(ra1)
        (f(a, b), ra2)
      }
    }

    def both[A, B](ra: Rand[A], rb: Rand[B]): Rand[(A, B)] =
      map2(ra, rb)((_, _))

    val randIntDouble: Rand[(Int, Double)] =
      both(int, double)

    val randDoubleInt: Rand[(Double, Int)] =
      both(double, int)

    /**
      * Exercise 6.7
      * Hard: If you can combine two RNG transitions, you should be able to combine a whole list of them.
      * Implement sequence for combining a List of transitions into a single transition.
      * Use it to reimplement the ints function you wrote before.
      * For the latter, you can use the standard library function List.fill(n)(x) to make a list with x repeated n times.
      */
    def sequence[A](fs: List[Rand[A]]): Rand[List[A]] = {
      fs.foldRight[Rand[List[A]]](unit(Nil))((elem: Rand[A], acc: Rand[List[A]]) => {
        map2(elem, acc)((a: A, list: List[A]) => a :: list)
      })
    }


    /**
      * Exercise 6.8
      * Implement flatMap, and then use it to implement nonNegativeLessThan.
      */
    def flatMap[A, B](f: Rand[A])(g: A => Rand[B]): Rand[B] = {
      rng => {
        val (a, r1) = f(rng)
        val (b, r2) = g(a)(r1)
        (b, r2)
      }
    }

    def nonNegativeLessThan(n: Int): Rand[Int] = {
      flatMap(nonNegativeInt)(elem => {
        val mod = elem % n
        if (elem + (n - 1) - mod >= 0) {
          unit(mod)
        } else {
          nonNegativeLessThan(n)
        }
      })
    }

  }

  object State {

    type State[S, +A] = S => (A, S)
    // case class State[S,+A](run: S => (A,S))
    // type Rand[A] = State[RNG, A]


  }

  def main(args: Array[String]): Unit = {
    val rng = SimpleRNG(1)
    val ints = RNG.ints(5)(rng)
    println(ints)

    val doubles: (Double, RNG) = RNG.double2(rng)
    println(doubles._1)

  }

}
