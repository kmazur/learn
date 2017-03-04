package com.scala.scalafortheimpatient

import java.io.File
import java.net.URL

object System {

  def main(args: Array[String]): Unit = {

    import scala.sys.process._

    // print output to STDOUT

    // "ls -la".!
    val outputCode: Int = "dir".!
    println(outputCode)

    val output = "ls".!!
    println(output)

    // pipe
    val piped = ("ls" #| "grep src").!!
    println(piped)

    // redirect to file,OutputStream,other process
    // redirect, override
    ("ls" #> new File("output.txt")).!
    // redirect, append
    ("ls" #>> new File("output.txt")).!

    // input from file,url,InputStream,other process
    // input from file
    ("grep src" #< new File("output.txt")).!
    // input from url
    ("grep src" #< new URL("http://google.com")).!

    ("false" #|| "true").!!
    ("false" #&& "true").!!


    val contextProcess = Process("grep src", new File("./"), ("JAVA_HOME", "D:/java"), ("HADOOP_HOME", "D:/hadoop"))
    // contextProcess.run()
    ("echo 42" #| contextProcess).!
  }

  // in Shell script
  // -------
  // #!/bin/sh
  // exec scala "$0" "$@"
  // !#
  // -------
}
