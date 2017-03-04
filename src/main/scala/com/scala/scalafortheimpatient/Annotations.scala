package com.scala.scalafortheimpatient

import java.io.IOException

import scala.annotation.meta.beanGetter
import scala.annotation.{strictfp, varargs}
import scala.beans.BeanProperty

object Annotations {

  def main(args: Array[String]): Unit = {

    /*class Id extends annotation.StaticAnnotation
    class Inject extends annotation.StaticAnnotation
    class NotNull extends annotation.StaticAnnotation
    class Localized extends annotation.StaticAnnotation

    // On primary constructor
    case class Credentials @Inject() (@NotNull username: String, password: String)

    val map = Map(1 -> "a")
    (map.get(2): @unchecked) match {
      case _ => println("unchecked")
    }

    trait Container[@specialized T]
    trait Container2[@specialized(Int, Boolean) T]

    val typeAnnotation: String @Localized = "SVO language"


    @SerialVersionUID(123)
    class TestEntity() {
      // apply @Id to getId() method
      @(Id @beanGetter) @BeanProperty var id = 0
      // @getter, @setter
      // @beanGetter, @beanSetter

      @volatile var done = false
      @transient def getField() = id + 1
      @strictfp def calc() = id.toDouble / 2.0
      // @native def win32RegKeys...

      // without throws Java code can't catch exception in try-catch block!
      @throws(classOf[IOException]) def close(): Unit = {
        println("Closing")
      }

      // for Java T... interoperability
      // without @varargs the method will have "Seq<T> args" in java
      @varargs def join[T](args: T*) = args.toSeq.mkString
    }
*/


    def boxedEquals[T](x: T, y: T) = x == y

    // converts to Integer
    boxedEquals(1, 1) // true

    def unboxedEquals[@specialized T](x: T, y: T) = x == y
    def unboxedEquals2[@specialized(Int, Long) T](x: T, y: T) = x == y
    unboxedEquals(1, 1) // true

    @deprecated(message = "Don't use it!")
    def deprecatedMethod() = ???

    // 'sz is a symbol
    def deprecatedParam(@deprecatedName('sz) size: Int, style: Int = 1) = ???
    // @deprecatedInheritance
    // @deprecatedOverriding
    // @implicitNotFound
    // @implicitAmbiguous
  }

}
