package com.scala.scalafortheimpatient

object Inheritance {

  class Person(val name: String = "Bob") {
    final var age: Int = 0
    final def sayHi(): Unit = println(s"Hi, I'm $name")
  }

  final class Employee(val salary: Int = 100) extends Person("Alice") {
    // can't override
    // var age: Int = 1

    // can't override
    // override def sayHi(): Unit = println("Howdy")

    def sayHello(): Unit = super.sayHi()
  }

  // can't extends final class
  // case class Manager() extends Employee

  class Student(override val name: String = "Tom") extends Person


  class Equality(val name: String = "Eq", val id: Int = 0) {
    // "eq" for reference equality
    // AnyRef.equals calls eq by default
    // == is equals in Scala

    // Note the Any type of other
    override def equals(other: Any): Boolean = other match {
      case that: Equality => name == that.name && id == that.id
      case _ => false
    }

    // ## is safe for nulls (returns 0)
    override def hashCode(): Int = (name, id).##
  }


  // Value type -> not allocated - just a fast wrapper
  // only one "val" field, and extends AnyVal
  class MilitaryTime private(val time: Int) extends AnyVal {
    def minutes = time % 100
    def hours = time / 100
    override def toString: String = f"${time}04d"
  }
  object MilitaryTime {
    def apply(time: Int): MilitaryTime =
      if(time < 0) { throw new IllegalArgumentException() } else { new MilitaryTime(time) }
  }

  def main(args: Array[String]): Unit = {
    val employee = new Employee()
    println(employee.name)
    println(employee.salary)

    // final doesn't mean it can't change in Scala
    employee.age = 3

    if(employee.isInstanceOf[Person]) {
      println("Is instance of Person: ")
      employee.asInstanceOf[Person].sayHi()
    }
    if(employee.getClass == classOf[Employee]) {
      println("Is Employee")
    }
    if(employee.getClass != classOf[Person]) {
      println("Is not Person exactly")
    }

    val student = new Student()
    student.sayHi() // Hi, I'm Tom


    val student2 = new Student() { def greeting(): Unit = println("Welcome") }
    student2.greeting()
    val typeErasureStudent: Student = student2
    // can't use
    // typeErasureStudent.greeting()

    // Student{def greeting(): Unit} -> this is called structural type
    def greetingMethod(s: Student{ def greeting(): Unit}): Unit =  {
      s.greeting() // reflective call
    }

    greetingMethod(student2)
    greetingMethod(typeErasureStudent.asInstanceOf[Student{def greeting(): Unit}])


    class Animal {
      val sight: Int = 10

      val environment: Array[Boolean] = Array.fill[Boolean](sight)(false)

      println("Created " + this.getClass + " with " + environment.length + " env and sight: " + sight)
    }

    class Ant extends Animal {
      // makes sight == 0 in parent!
      override val sight = 2
    }
    // now it's a problem because environment has 10 cells

    // early definition syntax - initialize subclass fields before superclass
    class Bee extends { override val sight = 2 } with Animal {
    }

    println("Animal env: " + new Animal().environment.length + " sight is: " + new Animal().sight) // 10
    println("Ant env: " + new Ant().environment.length) // 0
    println("Bee env: " + new Bee().environment.length) // 2
  }

}
