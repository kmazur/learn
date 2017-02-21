package com.scala

import scala.io.Source

object Files {

  def main(args: Array[String]): Unit = {
    // in the same directory or full path
    // val source = Source.fromFile("file.txt", "UTF-8")
    val source = Source.fromInputStream(Files.getClass.getResourceAsStream("/file.txt"), "UTF-8")
    val iterator: Iterator[String] = source.getLines()
    val lines: Array[String] = iterator.toArray
    val contents = source.mkString
    println(contents)

    source.close()


    val peekable = Source.fromInputStream(Files.getClass.getResourceAsStream("/file.txt"), "UTF-8").buffered
    val has = peekable.hasNext
    val firstChar: Char = peekable.head
    val firstCharAgain = peekable.next()

    /*
    io.StdIn.readInt()
    io.StdIn.readLine()
    io.StdIn.readDouble()
    io.StdIn.readFloat()
    */

    /*
    Source.fromURL("http://google.com", "UTF-8")
    Source.fromString("Hello Stream!")
    Source.stdin
    */

    // No binary format reading           -> use Java's FileInputStream
    // No support for writing             -> use Java's PrintWriter, etc.
    // No support for directory traversal -> use Java's Files.list/walk, etc.
  }

  // UID specified
  @SerialVersionUID(1L) class Person extends Serializable

  // UID Auto generated
  class Student extends Serializable

  // Serialization with Java's ObjectInput/OutputStreams

}
