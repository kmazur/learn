package com.scala

object Parsers {

  def main(args: Array[String]): Unit = {

    // Extended Bacus Naur Form

/*
    // separate dependency: org.scala-lang.modules, scala-parser-combinators

    class ExprParser extends RegexParsers {
      val number = "[0-9]+".r

      def expr: Parser[Any] = term ~ opt(("+" | "-") ~ expr)
      def term: Parser[Any] = factor ~ rep("*" ~ factor)
      def factor: Parser[Any] = number | "(" ~ expr ~ ")"
    }

    val parser = new ExprParser
    val result = parser.parseAll(parser.expr, "3-4*5")
    if (result.successful) println(result.get)

    // prints ((3~List())~Some((-~((4~List((*~5)))~None))))
*/

  }


}
