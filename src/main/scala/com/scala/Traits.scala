package com.scala

import java.time.LocalDateTime

import scala.language.reflectiveCalls

object Traits {

  trait Logger {
    def log(message: String)
    // default method
    def check(msg: String) = println(msg)
  }

  // multiple trait/interface inheritance using "with"
  trait ConsoleLogger extends Logger with Serializable {
    // override optional
    def log(message: String) = println(message)
  }

  class EmptyLogger extends Logger {
    def log(message: String) = ()
  }

  // super is invoking either super class or the next trait
  trait TimeLogger extends ConsoleLogger {
    override def log(message: String) = super.log(LocalDateTime.now() + ": " + message)
  }
  trait TruncateLogger extends ConsoleLogger {
    override def log(message: String) = super.log(if(message.length > 15) message.substring(0, 15) else message)
  }
  trait DirectLogger extends ConsoleLogger {
    // use console logger directly - don't pass flow to possibly other traits
    override def log(message: String) = super[ConsoleLogger].log("!!" + message + "!!")
  }

  trait AbstractLogger extends Logger {
    // can only be used with other traits
    abstract override def log(message: String) = {
      super.log("abstract: " + message)
    }
  }


  // traits can't have constructor with parameters!
  trait AbstractField extends Logger {
    val id: Int
    val name: String = "Tom" + id

    override def log(message: String) = println(s"($id, $name): $message")
  }

  class Person extends AbstractField {
    override val id: Int = 1

    def surname = "Bob"
  }

  abstract class Account(val id: Int = 0) extends Logger {
    def useLog(): Unit = {
      log("using the logger")
    }
  }

  // Self Types:

  // enforcing specific types that trait can be used with
  trait Enforcer extends ConsoleLogger {
    this: Person =>

    override def log(message: String) = super.log(s"${this.surname}: $message")
  }

  // enforcing structural types (reflective calls)
  trait EnforcerStruct extends ConsoleLogger {
    this: { def surname: String } =>

    override def log(message: String) = super.log(s"${this.surname}: $message")
  }


  def main(args: Array[String]): Unit = {

    // ad-hoc using console logger
    val acc = new Account() with ConsoleLogger
    acc.useLog() // using the logger


    val acc1 = new Account() with TimeLogger with TruncateLogger
    val acc2 = new Account() with TruncateLogger with TimeLogger
    acc1.useLog() // first truncates, then adds time -> 2017-02-22T20:25:26.595: using the logge
    acc2.useLog() // first adds time, then truncates -> 2017-02-22T20:2

    val acc3 = new Account() with TruncateLogger with TimeLogger with DirectLogger
    val acc4 = new Account() with DirectLogger with TruncateLogger with TimeLogger
    acc3.useLog() // !!using the logger!!
    // can't override the whole trait chain if direct call trait is not at the end (the first invoked)
    acc4.useLog() // !!2017-02-22T20:3!!


    // can't do - super.log is still abstract
    // val acc5 = new Account() with AbstractLogger

    // this also won't compile because TimeLogger uses super.log - no one implemented that
    // val acc5 = new Account() with AbstractLogger with TimeLogger

    // this also - it needs to be before calling abstract
    // val acc5 = new Account() with AbstractLogger with TimeLogger with ConsoleLogger

    val acc5 = new Account() with ConsoleLogger with AbstractLogger with TimeLogger
    acc5.useLog() // abstract: 2017-02-22T20:48:34.691: using the logger


    val person = new Person()
    person.log("Test") // (1, Tom0): Test


    // won't work -> anonymous class is the subclass
    val pitfall = new Account() with AbstractField {
      override val id: Int = 5
    }
    pitfall.log("test") // (5, Tom0): test

    val early = new { override val id: Int = 5} with Account with AbstractField
    early.log("test") // (5, Tom5): test

    val person2 = new Person() with EnforcerStruct
    person2.log("enforced")
  }
}
