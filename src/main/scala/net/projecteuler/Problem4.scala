package net.projecteuler

/**
A palindromic number reads the same both ways. The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.

Find the largest palindrome made from the product of two 3-digit numbers.
 */
object Problem4 {

  def main(args: Array[String]): Unit = {

    def reverse(num: Int): Int = {
      var (r, n) = (0, num)
      while(n > 0) {
        r = r * 10 + n % 10
        n /= 10
      }
      r
    }

    def isPalindrome(n: Int) = n == reverse(n)

    val palindromes = for {
      i <- 999 to 100 by -1
      j <- 999 to i   by -1 if isPalindrome(i * j)
    } yield i * j

    println(palindromes.max)
  }

}
