package com.scala.scalafortheimpatient

object PatternMatching {

  def main(args: Array[String]): Unit = {
    val c: Char = 'a'

    val result = c match {
      case '+' | 'a' => 5
      case '*' => 4
      case i if i == '-'  => 1
      case _ => 0
    }

    val CONSTANT = 'b'
    val const = 'c'
    c match {
      case CONSTANT => 'b' // matching 'b' (starts with uppercase letter)
      case const => 'a' // const = 'a' -> variable matching
      case `const` => 'c' // matching to defined above variable const
      case _ => '0'
    }

    val map: Map[Int, String] = Map(1 -> "a")
    map match {
      // types are erased in runtime!
      // case m: Map[Int, String] => 0
      // this is ok
      case m: Map[_, _] => 0
    }

    Array(1, 2, 3) match {
      // but with arrays it's ok!
      case m: Array[Int] => "Ints"
      case Array(0) => "array with one elem = 0"
      case Array(x, y) => "array with two elems: x and y"
      case Array(0, _*) => "array starting with elem = 0, _* is lost"
      case Array(first, rest @ _*) => "first = a(0), rest = a.tail"
      case _ => "???"
    }

    List(1, 2, 3) match {
      case 0 :: Nil => "List(0)"
      case x :: y :: Nil => "List(x, y)"
      case 0 :: tail => "List(0, tail:_*)"
      case x :: tail => "List(x, tail:_*"
    }

    // can't match variables with alternatives
    // case (x, 0) | (0, x)
    // only with _
    // case (_, 0) | (0, _)

    val pattern = "([0-9]+) ([a-z]+)".r
    "99 bottles" match {
      case pattern(num, item) => s"$num and $item"
      // Sets num to "99", item to "bottles"
    }

    val (a, b) = (1, 2)
    val Array(d, e) = Array(1, 2)
    // Runtime MatchError
    // val Array(f, g) = Array(1, 2, 3)

    val Array(h, i, rest @ _*) = Array(1, 2, 3, 4, 5)
    // (1, 2, (3, 4, 5))

    // ok in Scala - it's a pattern matching
    val 2 = i // equivalent to if(!(2 == i)) throw new MatchError
    println("ok")
    val 1 = i


    // matching works with nested structures
    // case Bundle(_, art1 @ Article(_, 20), _) => ...

  }

}
