package com.scala.functionalprogramminginscala

/**
  * As an example, implement hasSubsequence for checking whether a List contains another List as a subsequence.
  * For instance, List(1,2,3,4) would have List(1,2), List(2,3), and List(4) as subsequences, among others.
  * You may have some difficulty finding a concise purely functional implementation that is also efficient.
  * That’s okay. Implement the function however comes most naturally.
  * We’ll return to this implementation in chapter 5 and hopefully improve on it.
  * Note: Any two values x and y can be compared for equality in Scala using the expression x == y.
  *
  * def hasSubsequence[A](sup: List[A], sub: List[A]): Boolean
  */
object Exercise3_24 {

  def main(args: Array[String]): Unit = {
    println(hasSubsequence(List(1, 2, 3, 4, 5, 6), List(4, 5)))
    println(hasSubsequence(List(1, 2, 3, 4, 5, 6), List(5, 4)))
    println(hasSubsequence(List(1, 2, 3, 4, 5, 6), List()))
    println(hasSubsequence(List(1, 2, 3, 4, 5, 6), List(1)))
    println(hasSubsequence(List(1, 2, 3, 4, 5, 6), List(6)))
    println(hasSubsequence(List(1, 2, 3, 4, 5, 6), List(7)))
    println(hasSubsequence(List(), List()))
    println(hasSubsequence(List(), List(1)))
    println(hasSubsequence(List(), List(1, 2)))
  }

  def hasSubsequence[A](sup: List[A], sub: List[A]): Boolean = {
    if(sup.isEmpty && sub.isEmpty) {
      return true
    }

    def tryMatch(sup: List[A], sub: List[A]): Boolean = {
      sup match {
        case _ if sub.isEmpty => true
        case Nil => false
        case h :: t if sub.head == h => tryMatch(t, sub.tail)
        case _ => false
      }
    }

    def iterateMatch(sup: List[A], sub: List[A]): Boolean = {
      sup match {
        case Nil => false
        case _ if sup.length < sub.length => false
        case s if tryMatch(s, sub) => true
        case _ :: t => iterateMatch(t, sub)
      }
    }
    iterateMatch(sup, sub)
  }

}
