package com.hackerrank.challenges.algorithms

object AliceAndBobsSillyGame {

  def isPrime(n: Int): Boolean = {
    if(n <= 1) {
      false
    } else if(n <= 3) {
      true
    } else if(n % 2 == 0 || n % 3 == 0) {
      false
    } else {
      var i = 5;
      while(i * i <= n) {
        if(n % i == 0 || n % (i + 2) == 0) {
          return false;
        }
        i += 6;
      }
      true
    }
  }
  def memoize[I, O](f: I => O): I => O = new collection.mutable.HashMap[I, O]() {
    override def apply(key: I) = getOrElseUpdate(key, f(key))
  }

  val isPrimeMemo: Int => Boolean = memoize { isPrime _ }

  def main(args: Array[String]) {
    val sc = new java.util.Scanner (System.in);
    var g = sc.nextInt();
    var a0 = 0;
    var acc = List[String]();
    var maxPrimes = List[Int]()
    while(a0 < g){
      var n = sc.nextInt();

      val primes = if(n > maxPrimes.size) {
        maxPrimes = maxPrimes ++ ((maxPrimes.size-1) to n).filter(isPrimeMemo(_))
        maxPrimes
      } else {
        maxPrimes.view(0, n)
      }
      if(primes.size % 2 == 0) {
        acc = "Bob" :: acc
      } else {
        acc = "Alice" :: acc
      }

      a0+=1;
    }
    println(acc.reverse.mkString("\n"))
  }
}
