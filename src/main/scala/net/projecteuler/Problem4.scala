package net.projecteuler

/**
A palindromic number reads the same both ways. The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.

Find the largest palindrome made from the product of two 3-digit numbers.
 */
object Problem4 {

  def main(args: Array[String]): Unit = {

    def toDigits(n: Int): List[Int] = n match {
      case x if x == 0 => Nil
      case x => (n%10) :: toDigits(n/10)
    }

    def isPalindrome(num: Int): Boolean = {
      val digits = toDigits(num)
      digits.zip(digits.reverse).take(digits.size/2).map(t => t._1 - t._2).count(_ != 0) == 0
    }

    val palindromes = for {
      i <- 999 to 100 by -1
      j <- 999 to i   by -1 if isPalindrome(i * j)
    } yield i * j

    println(palindromes.max)
  }

}
