package com.scala

object Operators {

  def main(args: Array[String]): Unit = {

    // unicode chars are supported
    val √ = scala.math.sqrt _
    val sqrtOf8 = √ (4) + 1

    println(sqrtOf8) // 3.0

    // -> can be → from Predef.ArrowAssoc
    assert(('a' -> 1) == ('a' → 1))

    // reserved: @ # : = _ => <- <: <% >: ⇒ ←

    // `...` almost anything inside
    // yield is reserved - can do:
    // Thread.yield()
    // instead:
    Thread.`yield`()

    def `super simple method`() = println("method")
    `super simple method`() // method
    val `val` = 5

    class Fraction(val num: Int, val den: Int) {
      require(den != 0)
      // infix operator: f1 * f2 -> f1.*(f2)
      def *(other: Fraction) = new Fraction(num * other.num, den * other.den)
      // unary operator: -f1     -> f1.unary_-
      def unary_-() = new Fraction(-num, den)

      def result = num.asInstanceOf[Float] / den

      override def toString: String = result.toString
    }

    val f1 = new Fraction(1, 2)
    val f2 = new Fraction(2, 3)
    val f3 = f1 * f2
    println(f3) // 0.33333334

    val negated = -f3
    println(negated) // -0.33333334

    // assignment 'a = b' return Unit! -> can't chain
    var a = 0
    var b = 0
    // can't
    // a = b = 2
    // can't
    // a, b = 2
    val c, d = 2
    println(s"$c, $d") // 2, 2

    // operator ending with ':' -> right associativity
    // everything else          -> left  associativity
    val list: List[Int] = Nil.::(2)
    val list2: List[Int] = 2 :: Nil
    assert(list == list2)

    // f(a, b) '==' f.apply(a, b)
    // f(a, b) = ... '==' f.update(a, b)
    val map = collection.mutable.Map[String, Int]()
    map("Bob") = 1
    map.update("Bob", 1)

    val bob = map("Bob")
    val bob2 = map.apply("Bob")

    object Fraction {
      // for creation without 'new'
      def apply(num: Int, den: Int) = new Fraction(num, den)

      // for destructuring and pattern matching
      def unapply(arg: Fraction): Option[(Int, Int)] = if(arg.den == 0) None else Some((arg.num, arg.den))

    }

    val f4 = Fraction(1, 2) // Fraction.apply(1, 2)

    val Fraction(numerator, denominator) = f4 // Fraction.unapply(f4)
    println(s"$numerator, $denominator") // 1, 2

    // prints 'numerator: 1"
    f4 match {
      case Fraction(n, 2) => println(s"numerator: $n")
      case _ => println("None")
    }

    object Name {
      // for sequences
      def unapplySeq(input: String): Option[Seq[String]] = if(input.trim.length == 0) None else Some(input.trim.split(" "))
    }

    // prints '1 2 3'
    "1 2 3" match {
      case Name(x, y, z) => println(s"$x $y $z")
      case Name(x, "2") => println(s"$x 2")
      case _ => println("None")
    }


    // Dynamics
    import scala.language.dynamics
    class DynamicClass extends Dynamic {
      var props: collection.mutable.Map[String, Any] = collection.mutable.Map()
      // this.name = arg
      def updateDynamic(name: String)(arg: Any): Unit = props(name) = arg
      // val x = this.name
      def selectDynamic(name: String): Any = props(name)
      // val x = this.name(args)
      def applyDynamic[T](name: String)(args: String*): Option[T] =
            props.filter(entry => args.contains(entry._1)).toList.lift(0).map(_._2).asInstanceOf[Option[T]]
      // val x = this.name(arg1=a, arg2=b)
      def applyDynamicNamed(name: String)(tuples: (String, Any)*) = None
    }

    val expando = new DynamicClass()
    expando.name = "Bob"

    val bobName = expando.name
    println(bobName) // Bob

    val bobName2: Option[String] = expando.findByName("name")
    println(bobName2.get) // Bob


  }

}
