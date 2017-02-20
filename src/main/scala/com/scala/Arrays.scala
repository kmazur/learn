package com.scala

import scala.collection.mutable.ArrayBuffer

object Arrays {
  def main(args: Array[String]): Unit = {

    // fixed size 10 elements
    val array = new Array[Int](10)
    val emptyArray = Array.empty[Int]

    val strs = Array("1", "2", "3")
    println(strs(0))

    // variable size array
    val buffer = ArrayBuffer[Int]()
    // mutate buffer operators
    buffer += 1
    buffer += (2, 3, 4)
    buffer ++= Array(5, 6, 7)  // any collection [TraversableOnce]
    buffer.append(8, 9)
    buffer.prepend(-1, 0)
    buffer.insert(0, 1)
    buffer.insert(0, 1, 2, 3)
    buffer.remove(0, 4) // index, count
    buffer.trimEnd(1)
    buffer.trimStart(1)

    buffer.toArray // from [TraversableOnce]
    array.toBuffer // from [TraversableOnce]

    buffer.toTraversable
    buffer.toList
    buffer.toIterable
    buffer.toSeq
    buffer.toIndexedSeq
    buffer.toBuffer
    buffer.toSet
    buffer.toVector
    //buffer.to()
    // buffer.toMap // elements must be Tuple2 (key, value)

    for(i <- 0 until buffer.length)         yield buffer(i) + 1
    for(i <- buffer.length-1 to 0 by -1)    yield buffer(i) + 1

    for(i <- buffer.indices)                yield buffer(i) + 1
    for(i <- buffer.indices.reverse)        yield buffer(i) + 1

    for(elem <- buffer)                     yield elem + 1

    for(i <- buffer.indices if i % 2 == 0)  yield buffer(i) + 1
    buffer.filter(_ % 2 == 0).map(_ + 1)


    buffer.sum
    buffer.min
    buffer.max
    buffer.size
    buffer.count(_ % 2 == 0)

    buffer.sorted
    //buffer.sortBy()
    buffer.sortWith(_ > _)
    buffer.sorted.reverse


    buffer.mkString(", ")
    buffer.mkString("<", ", ", ">")  // <2, 3, 4, 5, 6>
    buffer.toString()                // ArrayBuffer(2, 3, 4, 5, 6)

    buffer.containsSlice(Array(3, 4, 5))


    val zeros = Array.ofDim[Int](2, 2)
    zeros(1)(1) = 5
    val ones = Array.fill[Int](2, 2)(1)


    // binary search
    import scala.collection.Searching._
    val result: SearchResult = buffer.sorted.search(3) // binary or linear


    // Java interoperability

    def listMethod(list: java.util.List[Int]): Int = {
      list.stream().mapToInt(i => i).sum()
    }

    // explicit conversion to Java (aka extension methods on ArrayBuffer
    import scala.collection.JavaConverters._
    val b2 = ArrayBuffer(1, 2, 3)
    listMethod(b2.asJava)

    // implicit conversion to Java
    import scala.collection.convert.ImplicitConversions._
    listMethod(b2)


    val symbols = Array("<", "-", ">")
    val counts = Array(1, 3, 1)
    val zipped = symbols.zip(counts)
    val str = (for((s, c) <- zipped) yield s * c).mkString
    println(str) // <--->

    zipped.toMap

  }
}
