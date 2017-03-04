package com.scala.scalafortheimpatient

import Objects.Color.Color

// this is basically lazy evaluated singleton
object Objects {

  private var staticVariable = 0
  def staticMethod(): Int = { staticVariable += 1; staticVariable }

  abstract class UndoableAction(val description: String) {
    def undo(): Unit
    def redo(): Unit
  }

  object DoNothingAction extends UndoableAction("Do nothing") {
    override def undo(): Unit = {}
    override def redo(): Unit = {}
  }

  class Account private(val balance: Int)
  object Account {
    def apply(balance: Int = 0) = new Account(balance)
  }


  // Java enums with scala.Enumeration
  object Color extends Enumeration {
    // type alias for usability
    type Color = Value
    val RED, GREEN, BLUE = Value

    def main(args: Array[String]): Unit = {
      val red = Color.withName("RED")
      val blue = Color(2)
      val colors = Color.values.toList
    }
  }

  // Java enums with case objects
  sealed trait Colour
  case object RED extends Colour
  case object GREEN extends Colour
  case object BLUE extends Colour

  def main(args: Array[String]): Unit = {
    println(staticVariable)
    println(staticMethod())

    val actions = List(DoNothingAction, DoNothingAction)

    // can't do
    // val account = new Account(1)
    val account = Account(5)

    val color: Objects.Color.Value = Color.RED
    // because of type alias
    val color2: Color = Color.BLUE
    val value = color match {
      case Color.RED => "red"
    }
    println(value)

    println(RED match {
      case RED => "red"
    })
  }


}
