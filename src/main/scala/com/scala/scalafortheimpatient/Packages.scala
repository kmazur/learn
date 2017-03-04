package com.scala

package packages {

    import java.util

    object Packages {
      def add(left: Int, right: Int): Int = left + right

      def main(args: Array[String]): Unit = {
        println(add(1, 2))

        // absolute path to package
        val array = _root_.scala.collection.mutable.ArrayBuffer[Int]()
        println(com.scala.packages.test.defaultName)

        // inaccessible
        // println(com.scala.test.defaultAge)


        // import all
        import com.scala.packages.test._
        println(defaultName)

        import java.awt.{ Color, Point }
        val (c, p) = (Color.RED, new Point(0, 0))

        import java.util.{ HashMap => JHashMap, ArrayList => JArrayList}
        import scala.collection.mutable.HashMap
        val (m, l) = (new JHashMap(), new JArrayList())

        HashMap(1 -> 1)
        new JHashMap()
      }


      def makeScope(): Unit = {
        // all except HashMap
        import java.util.{ HashMap => _, _}
        import scala.collection.immutable._

        val scalaMap = new HashMap[Int, Int]()
        val javaMap = new java.util.HashMap[Int, Int]()
      }

    }

    package object test {
      val defaultName = "Bob"
      private[test] val defaultAge = 5
    }

    package test {
      object Inner {
        def subtract(left: Int, right: Int) = Packages.add(left, -right)
        def show(): Unit = println(defaultName); println(defaultAge)
      }

      package nested {
        object Nested {
          def show(): Unit = println(defaultName); println(defaultAge)
        }
      }
    }

    package inner.nested.chained {

    }


}


