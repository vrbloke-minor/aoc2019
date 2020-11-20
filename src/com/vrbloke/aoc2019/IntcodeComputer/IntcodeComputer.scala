package com.vrbloke.aoc2019.IntcodeComputer

import java.util.Scanner
import scala.math.pow

class IntcodeComputer(inputReader: java.util.Scanner) extends AbstractIntcodeComputer(inputReader) {
  override def inputFunc(): Int = scala.io.StdIn.readInt()
  override def outputFunc(arg: Int): Unit = println(arg)
  override def inputMessage: String = "Provide input: "
}
