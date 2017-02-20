package com.hackerrank.contests.w29

object Solution {

  def isLeapJulian(year: Int): Boolean = {
    return year % 4 == 0
  }

  def isLeapGregorian(year: Int): Boolean = {
    return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)
  }

  def printDate(isLeap: (Int) => Boolean, year: Int): Unit = {
    val day = 256;
    val leapFebruary = if (isLeap(year)) {
      29
    } else {
      28
    }
    val days = Array(31, leapFebruary, 31, 30, 31, 30, 31, 30, 31, 30, 31, 30);
    var month = 1;
    var nth = 0;
    var i = 0;
    while (nth < day) {
      nth += days(i);
      month += 1;
      i += 1;
    }
    month -= 1;
    i -= 1;
    val monthDiff = nth - day;
    val monthDay = days(i) - monthDiff - 1;
    println(f"${monthDay}%02d.${month}%02d.${year}%04d");
  }

  def main(args: Array[String]) {
    val sc = new java.util.Scanner(System.in);
    val y = sc.nextInt();
    if (y == 1918) {
      println("27.08.1918")
    } else if (y > 1918) {
      printDate(isLeapGregorian, y)
    } else {
      printDate(isLeapJulian, y)
    }
  }
}
