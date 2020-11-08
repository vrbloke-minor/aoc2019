package com.vrbloke.aoc2019.day4

object main4 extends App {
  val mainDelegate = new PasswordChecker(246515 to 739105)
  val result = mainDelegate.solvePuzzlePart2()
  println(result)
}
