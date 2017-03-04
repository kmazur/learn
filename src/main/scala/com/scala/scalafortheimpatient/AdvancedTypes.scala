package com.scala.scalafortheimpatient

import java.awt.Shape
import java.awt.image.BufferedImage
import javax.swing.JComponent

import scala.collection.immutable.HashMap

object AdvancedTypes {

  def main(args: Array[String]): Unit = {

    // this.type is like Java's: <T extends SomeClass<T>> for returning 'current' class in subclasses
    class Doc {
      var name: String = _
      def withName(name: String): this.type = { this.name = name; this }
    }
    class Book extends Doc {
      var isbn: String = _
      def withIsbn(isbn: String): this.type = {this.isbn = isbn; this}
    }

    // still a Book type
    val book: Book = new Book().withIsbn("123").withName("Doc name")


    // English sentence like api
    object Title
    class Paper {
      var title: String = _
      private var useNextAs: Any = _
      def set(obj: Title.type): this.type = { useNextAs = obj; this }
      def to(arg: String): this.type = {
        if (useNextAs == Title) title = arg
        this
      }
    }

    new Paper set Title to "Scala"
    new Paper().set(Title).to("Scala")


    // Type aliases
    type Index = HashMap[String, (Int, Int)]
    val primaryIndex: Index = new Index()
    // val primaryIndex: Index = Index() // doesn't work
    val secondaryIndex: Index = HashMap.apply()
    val sameIndex: HashMap[String, (Int, Int)] = primaryIndex


    // Structural types
    // uses reflection
    def struct[T <: { def close(): Exception }]() = ???



    trait CompoundType extends Serializable with Shape { def close(): Unit }



    // Infix Types (only with two type parameters)
    val map: String Map Int = Map[String, Int]()

    type ×[A, B] = (A, B)
    val pair: String × Int = ("a", 1)
    val tuple: String × String × Int = (("a", "b"), 1)



    // Existential types (for compatibility with Java wildcards)
    type ComponentArray = Array[T] forSome { type T <: JComponent}
    // is equivalent to
    type ComponentArray2 = Array[_ <: JComponent]

    type SomeArray  = Array[T] forSome { type T }
    type SomeArray2 = Array[_]

    type SomeMap = Map[T, U] forSome { type T; type U <: T}



    // Self types
    trait Logger {
      this: Exception => // this trait can be added to only subclasses of Exception
      def log() = println("logging: " + this.getMessage)
    }

    class CustomException extends RuntimeException with Logger
    // class CustomComponent extends JComponent with Logger // illegal inheritance

    trait SpecializedLogger extends Logger {
      this: RuntimeException => // must redeclare or specialize
    }


    trait LoggerComponent {
      trait Log { def log(msg: String): Unit }
      val logger: Log
      class ConsoleLogger extends Log {
        override def log(msg: String): Unit = println(msg)
      }
    }
    trait AuthComponent {
      this: LoggerComponent =>
      val hash: String
      val SHA1 = "SHA1"
      val SHA2 = "SHA2"
    }

    object App extends AuthComponent with LoggerComponent {
      val logger = new ConsoleLogger()
      val hash = SHA1
    }



    // Abstract Types - types defined in subclasses
    trait Reader {
      type Contents
      def read(file: String): Contents
    }

    class StringReader extends Reader {
      type Contents = String
      def read(file: String): String = s"Contents from file: $file"
    }
    class ImageReader extends Reader {
      type Contents = BufferedImage
      def read(file: String): BufferedImage = ???
    }

    // equivalent is:
    trait Reader2[C] {
      def read(file: String): C
    }

    trait Listener {
      type Event <: Reader
      type Value
    }



    // Higher kinded types
    // C[_] because extending class' (e.g. Buffer[E]) map method couldn't return the Buffer[F] if it was Iterable[F]
    // but we now don't know how to build C[_] -> Scala uses builder for that
    trait Iterable[E, C[_]] {
      def iterator(): Iterator[E]
      def build[F](): C[F]
      def map[F](f : (E) => F) : C[F]
    }
  }

}
