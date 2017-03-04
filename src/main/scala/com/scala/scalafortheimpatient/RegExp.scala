package com.scala.scalafortheimpatient

import scala.util.matching.Regex

object RegExp {

  def main(args: Array[String]): Unit = {

    val nums = "[0-9]+".r
    val words = "\\w{3,3}".r
    val words2 = """(\w{3,3})""".r

    val matches: Array[String] = words2.findAllIn("aaa, bbb, ccc").toArray

    val numStrs: Iterator[String] = for(m <- "[0-9]".r.findAllIn("1, 2, 3")) yield m

    val optional: Option[String] = "^[abc]+$".r.findFirstIn("defg")

    println("defg".matches("[defg]+")) // true

    val replaced = nums.replaceSomeIn("99 bottles, 98 bottles", m => {
      m.matched.toInt match {
        case i if i % 2 == 0 => Some("XX")
        case _ => None
      }
    })
    println(replaced) // 99 bottles, XX bottles

    // named groups
    val groupsRegex = "([0-9]+) ([a-z]+)".r("num", "item")
    for(m <- groupsRegex.findAllMatchIn("123 zzz")) {
      m.group(1) // first group
      m.group("num") // named
    }

    val countAndName = "([0-9]+)\\s+(\\w+)".r
    val countAndName(productCount, productName) = "99 bottles"

    // prints:
    // 99 -> bottles
    // 98 -> bottles
    for (countAndName(num, item) <- countAndName.findAllIn("99 bottles, 98 bottles")) {
      println(s"$num -> $item")
    }

  }

}
