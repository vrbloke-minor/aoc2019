package com.vrbloke.aoc2019.day7

import com.vrbloke.aoc2019.IntcodeComputer.AutomaticIntcodeComputer

object main7 extends App {
  val puzzleSolver = new AmplifierArray(5, "input/input7.txt")
  println(puzzleSolver.findMaxOutput())
}
