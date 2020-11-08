package com.vrbloke.aoc2019.day3

import java.util.Scanner
import java.io.File

object main3 extends App {
  val mainDelegate = new WireSolver(new Scanner(new File("input/input.txt")))
  val result1 = mainDelegate.solvePuzzlePart1()
  println(result1)
  val result2 = mainDelegate.solvePuzzlePart2()
  println(result2)
}
