package com.vrbloke.aoc2019.day7

import java.io.File
import java.util.Scanner

import com.vrbloke.aoc2019.IntcodeComputer.{AutomaticIC, IC}

object main7 extends App {
  val puzzleSolver = new AmplifierArray(5, "input/input7.txt", 5)
  println(puzzleSolver.findMaxOutput(puzzleSolver.runFeedbackLoops _))
}
