package com.scala.functionalprogramminginscala

/**
Write a recursive function to get the nth Fibonacci number (http://mng.bz/C29s).
The first two Fibonacci numbers are 0 and 1.
The nth number is always the sum of the previous two—the sequence begins 0, 1, 1, 2, 3, 5.
Your definition should use a local tail-recursive function.

def fib(n: Int): Int
*/
object Exercise2_1 {

  def main(args: Array[String]): Unit = {
    println((1 to 8).map(fib))
  }

  def fib(n: Int): Int = {
    def loop(a: Int, b: Int, n: Int): Int = {
      if(n <= 1) a
      else loop(b, a+b, n - 1)
    }
    loop(0, 1, n)
  }

}
