package com.scala

import java.io.IOException

import scala.util.Try

object Syntax {

  def main(args: Array[String]): Unit = {

    // can't change
    val integer: Int = 1
    val integer2 = 1

    // can change
    var integer3 = 1
    integer3 = 2

    "Lazy %s jumps over the lazy %s".format("fox", "dog")
    "dog".formatted("%s is lazy")

    val threeTimes = "a" * 3 // aaa

    def defaultArgumentsMethod(str: String, before: String = "{", after: String = "}"): String = {
      before + str + after
    }
    println(defaultArgumentsMethod("inside"))

    // Named parameters
    println(defaultArgumentsMethod("inside", after = ">>"))

    // varargs
    def varargs(ints: Int*): Int = {
      ints.sum
    }

    println(varargs(1, 2, 3))
    val array = Array(1, 2, 3)
    println(varargs(array:_*))

    // Returns unit
    // def unit1() { 5 }
    // Returns Int
    def unit2(): Int = { 5 }


    def withParen() = 5
    def withoutParen = 5
    withParen()
    withoutParen
    // can't call
    // withoutParen()

    // Lazy val
    def callable() = { println("called"); 5 }
    lazy val lazyVal = callable() + 5
    // It will show:
    // After lazy
    // called
    println("After lazy")
    println(lazyVal)

    // All exceptions are unchecked
    def unchecked() = {
      throw new Exception
    }

    val void = try {
      unchecked()
    } catch {
      case e: IOException => println("IOException")
      case _: Throwable => println("Throwable")
    } finally {
      println("Finished")
    }
    println(void)

    // scala-arm for resource management
    /*
    for(in <- resource(Files.newBufferedReader("");
        out <- resource(Files.newBufferedWriter("")) { ... }
     */

    val tried1: Try[String] = Try { throw new Exception() }
    val tried2 = Try { "Echo" }
    val concatenated: Try[String] = for(t1 <- tried1; t2 <- tried2) yield t1 + t2
    println(concatenated)
  }
}
