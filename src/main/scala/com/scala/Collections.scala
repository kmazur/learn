package com.scala

import scala.collection.immutable.Seq
import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

object Collections {

  def main(args: Array[String]): Unit = {

    val set1 = Set(1, 2, 3)
    val set2 = Set(2, 3, 1)
    val set3 = Set(2, 3, 1, 4)
    val list = List(1, 2, 3)

    println(set1 == set2) // true
    println(set1 == set3) // false
    println(list == set1) // false
    println(list sameElements set1) // true

    Vector(1, 2, 3) // tree with max 32 children
    val newVector = Vector(1, 2, 3).updated(0, 5) // 5, 2 ,3

    // Ranges
    (1 to    3) // 1, 2 ,3
    (1 until 3) // 1, 2


    // Lists
    val list1 = List(1, 2)
    val list2 = 1 :: 2 :: Nil
    val list3 = 1 :: List(2, 3)
    val list4 = list.head :: list.tail
    val lastElem = list.last

    def sum(list: List[Int]): Int = list match {
      case Nil => 0
      case h :: rest => h + sum(rest)
    }
    val sumElems = list1.sum //max, min, etc.

    // mutable, linked, with efficient adding/removal from the end (pointer to the last)
    val listBuffer = ListBuffer(1, 2 , 3)





    // Sets
    // default to HashSet
    val set4 = Set(1, 2, 3) + 1 // == Set(1, 2, 3)
    val set5 = mutable.LinkedHashSet(1, 2, 3) + 4 // preserves order
    val sortedSet = mutable.SortedSet(5, 3, 1)
    println(sortedSet) // 1, 3, 5

    set4.contains(1) // true
    set5.subsetOf(Set(2, 3)) // true
    set4.union(set5) // 1, 2, 3, 4
    // union        |
    // intersect    &
    // diff         &~
    set4 | set1
    set1 & set2
    set1 &~ set2




    // Stream can be infinite. Values are lazily evaluated
    val stream1 = 1 #:: 2 #:: 3 #:: Stream.empty



    // Operators
    list1(0) // 1
    list :+ 100 // append  at the end
    100 +: list // prepend at the beginning

    val head: Int = list.head
    val headOption: Option[Int] = list.headOption
    list.last
    list.lastOption
    list.isEmpty
    list.nonEmpty
    // map, flatMap, foreach, reduce[Left/Right], fold[Left, Firght]
    // collect -> partial function
    list.collect({ case i: Int => i})
    // list count/forall/exists (predicate)
    // list filter/filterNot/partition
    // list takeWhile/dropWhile, take(n), drop(n), splitAt(n), takeRight(n), dropRight(n)
    // list slice(from, to)
    // list view(from, to)
    val zip: Seq[(Int, String)] = List(1, 2, 3).zip(List("a", "b", "c"))
    val index: Seq[(String, Int)] = List("a", "b", "c").zipWithIndex
    List(1, 2, 3).zipAll(List("a", "b"), 0, "0")    // (1, "a"), (2, "b"), (3, "0")
    List(1, 2).zipAll(List("a", "b", "c"), 0, "0")  // (1, "a"), (2, "b"), (0, "c")

    List(1, 2, 3).grouped(2) // [[1, 2], [3]]
    List(1, 2, 3).sliding(2) // [[1, 2], [2, 3]]
    List(1, 2, 3).groupBy(i => i)
    List(1, 2, 3).mkString(",")
    list.toIndexedSeq.toIterable.toList.toBuffer.toStream.toSet.toVector


    val ints = ArrayBuffer(1, 2, 3)
    // transform is like map but mutates the collection
    ints.transform(_ * 2)
    println(ints) // 2, 4, 6


    // foldLeft(0)(_ + _)
    (0 /: List(1, 2, 3))(_ + _)

    // foldRight(0)(_ + _)
    (List(1, 2, 3) :\ 0)(_ + _)

    val results: Seq[Int] = List(1, 2, 3).scanLeft(0)(_ + _)
    println(results) // List(0, 1, 3, 6)



    val list10 = (1 to 10).toBuffer
    list10.view(0, 5).filter(_ % 2 == 0)
    println(list10) // 1, 2, 3..., 10
  }
}
