package com.scala.scalafortheimpatient

import scala.collection
import scala.collection.SortedMap


object Maps {
  def main(args: Array[String]): Unit = {

    // immutable
    val map = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 3)
      .ensuring(m => m.size == 3)

    val map2 = Map(("Alice", 10), ("Bob", 3))

    val mutable = collection.mutable.Map(map.toSeq:_*)
    mutable("Alice") = 5

    val emptyMap = collection.mutable.Map.empty

    mutable.contains("Alice")
    val maybe: Option[Int] = mutable.get("Alice")
    val value: Int = mutable.getOrElse("Alice", 0)
    mutable.getOrElseUpdate("Tom", 0)

    val newMap = mutable.withDefaultValue(0)
    newMap.get("Tim") // None
    newMap("Tim") // 0


    // mutate
    newMap += ("A" -> 1, "B" -> 2)
    newMap -= "A"

    // create new
    newMap + "A"
    newMap - "A"

    for((k, v) <- newMap) yield v
    map.keys
    map.keySet
    map.values

    val reversedMap: Map[Int, Int] = for((k, v) <- Map(1 -> 2, 2 -> 2, 3 -> 1)) yield v -> k
    println(reversedMap) // Map(2 -> 2, 1 -> 3)

    val linkedMap = collection.mutable.LinkedHashMap(1 -> 2, 2 -> 3, 3 -> 4)
    val sortedMap = SortedMap(1 -> 2, 2 -> 3, 3 -> 4)

  }
}
