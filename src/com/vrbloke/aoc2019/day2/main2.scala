package com.vrbloke.aoc2019.day2

import java.util.Scanner
import java.io.File
import com.vrbloke.aoc2019.IntcodeComputer.IC

object main2 extends App {
  val inputReader = new Scanner(new File("input/input.txt"))
  inputReader.useDelimiter(",")
  val mainDelegate = new IC(inputReader)
  val result = mainDelegate.findOutputWithNounAndVerb(19690720)
  println(result)
  println(100 * result._1 + result._2)
}
