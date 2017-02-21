package com.hackerrank.contests.w29

object Solution {

  val programmerDay = 256;

  def getYearDays(year: Int): Array[Int] = {
    val isLeap = if(year > 1918) { isLeapGregorian(_) } else { isLeapJulian(_) }
    val leapFebruary = if(isLeap(year)) {29} else {28}
    return Array(31, leapFebruary, 31, 30, 31, 30, 31, 30, 31, 30, 31, 30);
  }

  def isLeapJulian(year: Int): Boolean = {
    return year % 4 == 0
  }

  def isLeapGregorian(year: Int): Boolean = {
    return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)
  }

  def printDate(year: Int): Unit = {
    val monthDays = getYearDays(year)
    var month = 1;
    var nth = 0;
    while(nth < programmerDay) {
      nth += monthDays(month - 1);
      month += 1;
    }
    month -= 1;
    val monthDiff = nth - programmerDay;
    val monthDay = monthDays(month - 1) - monthDiff - 1;
    println(f"${monthDay}%02d.${month}%02d.${year}%04d");
  }

  def main(args: Array[String]) {
    val sc = new java.util.Scanner (System.in);
    val y = sc.nextInt();
    if(y == 1918) {
      println("27.08.1918")
    } else {
      printDate(y)
    }
  }
}
