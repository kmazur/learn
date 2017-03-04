package com.scala.scalafortheimpatient

object Recursion {



  def main(args: Array[String]): Unit = {
/*
    def gcd(a0: Int, b0: Int): Int = {
      @tailrec
      def gcd_i(a: Int, b: Int): Int =
        if (b == 0) a else gcd_i(b, a % b)
      gcd_i(a0, b0)
    }
    gcd(12332, 2332)



    // Trampoline recursion
    import scala.util.control.TailCalls._
    def evenLength(xs: Seq[Int]): TailRec[Boolean] =
      if (xs.isEmpty) done(true) else tailcall(oddLength(xs.tail))

    def oddLength(xs: Seq[Int]): TailRec[Boolean] =
      if (xs.isEmpty) done(false) else tailcall(evenLength(xs.tail))


    evenLength(1 to 1000000).result


    // optimize switch to jump table
    val n = 5
    (n: @switch) match {
      case 0 => "zero"
      case 1 => "one"
      case _ => "?"
    }*/

    // @inline
    // @noinile

    // @elide(500) def dump(props: List[Int]) = ???
  }

}
