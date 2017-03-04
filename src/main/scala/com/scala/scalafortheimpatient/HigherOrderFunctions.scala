package com.scala.scalafortheimpatient

import java.awt.event.{ActionEvent, ActionListener}
import javax.swing.JButton

object HigherOrderFunctions {

  def main(args: Array[String]): Unit = {

    // to assign function as is use: "func _" or "func(_)"
    val ceilFunc = math.ceil _
    def printCeil(func: Double => Double): Unit = println(func(2))

    printCeil(ceilFunc) // 2.0
    // where context needs function -> "func _" is not necessary
    printCeil(math.ceil) // 2.0


    val charAt: (String, Int) => Char = (_: String).charAt(_)
    println(charAt("abc", 1)) // b

    // _ is the arg: Int
    Array(1, 2, 3).map(_ + 1) // 2, 3, 4

    val triple: (Int) => Int = 3 * (_:Int)


    // SAM (Single Abstract Method)
    new JButton().addActionListener(event => println("callback!"))

    val callback: (ActionEvent) => Unit = (event:ActionEvent) => println("callback!")
    // doesn't work
    // new JButton().addActionListener(callback)
    val callback2: ActionListener = event => println("callback2!")
    // works
    new JButton().addActionListener(callback2)
    // this also works
    new JButton().addActionListener(callback(_))


    // Currying

    val add = (_:Int) + (_:Int)
    val add2: (Int) => (Int) => Int = add.curried
    add(1, 2) // 3
    add2(1)(2) // 3

    val a1 = Array("a", "b", "c")
    val a2 = Array("a", "B", "C")
    println(a1.corresponds(a2)(_.equalsIgnoreCase(_))) // true

    // call by value
    def runInThread(block: () => Unit) = new Thread {
      override def run(): Unit = block()
    }.start()

    runInThread(() => println("running1"))

    // call by name
    def runInThread2(block: => Unit) = new Thread {
      override def run(): Unit = block
    }
    runInThread2(println("running2"))


    def until(condition: => Boolean)(block: => Unit): Unit = {
      if(condition) {
        block
        until(condition)(block)
      }
    }

    var x = 5
    until(x > 0) {
      x -= 1
      println(x)
    }
  }
}
