package com.scala

import java.io.File

import scala.io.Source

object Implicits {

  case class Fraction(num: Int, denum: Int) {
    def *(other: Fraction) = Fraction(num * other.denum + other.num * denum, denum * other.denum)
  }

  object FractionImplicits {
    implicit def int2Fraction(n: Int): Fraction = Fraction(n, 2)
  }

  // Enriching existing classes
  // must have primary constructor with exactly 1 arg
  // extending with AnyVal won't create new objects (just static call RichFile$.read$extension(file))
  // can't be in main scope
  implicit class RichFile(val from: File) extends AnyVal {
    def read: String = Source.fromFile(from.getPath).mkString
  }
  // or provide this way
  // implicit def file2RichFile(from: File): RichFile = new RichFile(from)

  def main(args: Array[String]): Unit = {

    import FractionImplicits._
    // implicit conversion from 3 to Fraction(3, 1)
    val sum: Fraction = 3 * Fraction(1, 2)
    val denum = 3.denum
    val fum2 = Fraction(1, 2) * 3

    val sum3: Int = 3 * 2
    val sum4: Fraction = 3 * 2
    println(sum4)

    // val read: String = new File("file").read

    case class Delimeters(left: String, right: String)
    implicit val custom = Delimeters("<", ">")

    def printWithDelimeters(str: String)(implicit d: Delimeters) = println(d.left + str + d.right)

    printWithDelimeters("a")(custom)
    printWithDelimeters("a")
  }

}
