package com.vrbloke.aoc2019.day2

import java.util.Scanner
import java.io.File

object main2 extends App {
  val inputReader = new Scanner(new File("input/input.txt"))
  inputReader.useDelimiter(",")
  val mainDelegate = new IntcodeComputer(inputReader)
  val result = mainDelegate.findOutput(19690720)
  println(result)
  println(100 * result._1 + result._2)
}
