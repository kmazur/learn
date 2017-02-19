package com.scala

import scala.beans.{BeanProperty, BooleanBeanProperty}
import scala.collection.mutable.ArrayBuffer

object Classes {

  // default public
  class Counter {
    // _ -> default value for type
    private var value: Int = _
    def increment(): Int = { value += 1; value }
    def current() = value
  }

  class Person {
    // compiled as private with "def age(): Int" and "def age(value: Int): Unit"
    var age: Int = _
  }

  class Person2 {
    private var privateAge: Int = _
    def age = privateAge
    def age_=(newAge: Int) = privateAge = newAge
  }

  class OnlyGetter {
    // private final int
    val age = 1
  }

  class PrivateAll {
    // no getter/setter
    private[this] var age = 1
    // private getter/setter
    private var name = "Alice"

    def act(): Unit = {
      this.name_=("test") // setter
      val testName = this.name // getter
    }
  }

  class TypeAccess {
    // same as private[TypeAccess]
    private var value = 0

    def compare(other: TypeAccess): Int = {
      other.value = 5 // !!!
      other.value.compareTo(this.value)
    }
  }

  class TypeRestrictedAccess {
    private[this] var value = 0

    def getValue(): Int = value

    def compare(other: TypeRestrictedAccess): Int = {
      // can't access
      // other.value = 5
      other.getValue().compareTo(this.value)
    }
  }

  class FriendClass2 {

    class FriendClass {
      // must be enclosing type
      private[FriendClass2] var value = 0

      def getValue(): Int = this.value
    }

    def compare(other: FriendClass): Int = {
      // can access now
      other.value.compareTo(1)
    }
  }

  class BeanPropertyType {
    // getName/setName/name/name_=
    @BeanProperty var name: String = _
    // isBoy/setBoy/boy/boy_=
    @BooleanBeanProperty var boy: Boolean = _
  }

  // default for params is private[this] val
  // with var - field is visible (getter "def age" and "def age_=(Int)"
  // with val - field is visible but not changeable
  class ConstructorType(name: String = "Alice", var age: Int = 0, val id: Int = 5, unused: Int = 6) {
    def this(age: Int) {
      this("Bob") // must call it
      this.age = age
      //this.name = name
    }

    // is not a field - it won't be visible outside
    println(unused)
  }

  // won't be a compilation error ?!
  class PrivateConstructor private() {
  }

  // Parent.this == parentThis
  class Parent { parentThis =>
    // different instances of Parent have different >>Classes<< of Child!
    // so it's better suited to declare it in companion object
    class Child { childThis =>
      // this == childThis
      require(Parent.this == parentThis)
      require(this == childThis)
    }
    val child = new Child()

    // but this works -> child of any Parent (per type - not per instance of Parent)
    val children = ArrayBuffer[Parent#Child]()
    val myChildren = ArrayBuffer[Child]()
  }

  def main(args: Array[String]): Unit = {
    val counter = new Counter
    counter.increment()
    counter.current()

    new Person().age
    new OnlyGetter().age

    val bean = new BeanPropertyType()
    val isBoy = bean.boy
    bean.name = "Bob"
    bean.isBoy
    bean.setBoy(true)

    val bob = new ConstructorType(5)
    bob.age = 5
    println(s"${bob.age}, ${bob.id}")

    // can't instantiate like this
    // val privateConstructor = new PrivateConstructor()

    val p1 = new Parent()
    val p2 = new Parent()
    // can't do -> p1.Child is per instance of p1, p1.child != p2.child
    // p1.child = p2.child
    // p1.myChildren.append(p2.child) // type mismatch expected p1.Child, got p2.Child

    p1.children += p2.child
    p1.children ++= p2.children

  }
}
