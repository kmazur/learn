package com.scala

import scala.reflect.ClassTag

object Types {

  def main(args: Array[String]): Unit = {

    class Pair[T, S](val first: T, val second: S)

    val p1: Pair[Long, String] = new Pair(1L, "2")
    val p2: Pair[Long, String] = new Pair[Long, String](1, "2")
    val p3: Pair[Any, Any] = new Pair(1, "2")

    def getMiddle[T](a: Array[T]) = a(a.length / 2)
    getMiddle(Array(1, 2, 3)) // 2, T = Int

    val forcedType = getMiddle[String] _ // (Array[String]) => String

    class Person(val name: String) extends Comparable[Person] {
      override def compareTo(o: Person): Int = name.compareTo(o.name)
    }
    class Student(val id: Int, name: String) extends Person(name)

    // <: like in Java's extends
    class CPair[T <: Comparable[T]](val first: T, val second: T) {
      def smaller = if(first.compareTo(second) < 0) first else second
    }

    class RPair[T](val first: T, val second: T) {
      // R super T -> CPair[Student, Student] => CPair[Person, Peron] if class of elem is Person
      def replaceFirst[R >: T](elem: R) = new RPair[R](elem, second)
    }


    // make sure that implicit exists that maps T to Comparable[T]
    // T <% Comparable[T]
    class IPair[T](val first: T, val second: T)(implicit evidence: T => Comparable[T]) { // T is convertible
      // can use Comparable[T] methods
      def smaller = if(first.compareTo(second) < 0) first else second
    }


    // Context Bounds -> ensure there is an implicit
    class BPair[T : Ordering](val first: T, val second: T) {
      def smaller(implicit ord: Ordering[T]) = if (ord.compare(first, second) < 0) first else second
    }


    // using T:ClassTag the new array can be instantiated
    def makePair[T : ClassTag](first: T, second: T) = {
      val r = new Array[T](2)
      r(0) = first
      r(1) = second
      r
    }
    val ints: Array[Int] = makePair[Int](1, 2)

    class First
    class Second extends First
    class Third extends Second

    class Bounds1[T >: Third <: First]
    class Bounds2[T <: Comparable[T] with Serializable with Cloneable]
    class Bounds3[T : Ordering : ClassTag]


    class P1[T]()(implicit ev: T <:< Comparable[T]) // T is subtype of Comparable[T]
    class P2[T]()(implicit ev: T =:= Comparable[T]) // T is equal to Comparable[T]
    class P3[T]()(implicit ev: T => Comparable[T]) // T can be converted to Comparable[T]


    //new P1[First]() // Cannot prove that First <:< Comparable[First]
    //new P2[First]() // Cannot prove that First =:= Comparable[First]
    //new P3[First]() // No implicit view available from First => Comparable[First]


    new P1[String]()
    // new P2[String]() // Cannot prove that String =:= Comparable[String]
    new P3[String]()


    // covariance -> Container[R] is subtype of Container[T] if R is subtype of T
    // it varies in the same direction
    //
    // Container[T]   =>    Container[R]
    //
    //      |                   |
    //      |                   |
    //
    //      T         =>        R

    case class Container1[+T](value: T)
    val f1: Container1[Second] = new Container1[Second](new Second())
    val f2: Container1[First] = new Container1[First](new First())

    def printContainer(p: Container1[First]) = println(p.value)
    printContainer(f1)
    // Container1[First] is a subtype of Container1[Second]
    printContainer(f2) // would be an error if not +T


    // contravariance -> Container[T] is supertype of Container[R] if R is subtype of T
    // it varies in the oposite direction
    //
    // Container[T]   <=    Container[R]
    //
    //      |                   |
    //      |                   |
    //
    //      T         =>        R

    case class Container2[-T]() {
      // -T can be only in parameters
      def contra(other: T) = println(other.toString)
    }

    Container2[Int]().contra(1) // prints 1

    def makeContainer(f: Second, s: Container2[Second]) = println(f.toString + " -> " + s.toString)
    makeContainer(new Second(), new Container2[First]()) // Container2[First] is super type of Container2[Second]!
    makeContainer(new Second(), new Container2[Second]())
    // makeContainer(new Second(), new Container2[Third]()) // can't do

    // Array[T] are invariant because are mutable
    val seconds = new Array[Second](2)
    // val firsts: Array[First] = seconds
    // firsts(0) = new First() // now seconds has First!

    // covariants     -> can be return types
    // contravariants -> can be parameter types


    object CantBeGeneric extends Container2[Nothing]

    // wildcards like in Java's <? extends First>
    def process(people: List[_ <: First]) = ???


  }

}
