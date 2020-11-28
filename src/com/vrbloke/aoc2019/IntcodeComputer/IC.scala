package com.vrbloke.aoc2019.IntcodeComputer

import java.util.Scanner
import scala.math.pow

class IC(inputReader: java.util.Scanner) extends AbstractIC(inputReader) {
  override def inputFunc(): Option[Int] = try {
    Some(scala.io.StdIn.readInt())
  } catch {
    case e: java.io.EOFException => None
  }
  override def outputFunc(arg: Int): Unit = println(arg)
  override def inputMessage: String = "Provide input: "

  // For Day 1
  def solveForNounAndVerb(noun: Int, verb: Int): Int = {
    withAnnouncement("Solving puzzle...") {
      set(1, noun)
      set(2, verb)
      execute()
      read(0)
    }
  }

  def findOutputWithNounAndVerb(x: Int): (Int,Int) = {
    for(i <- 0 to 99; j <- 0 to 99) {
      restoreInitialState()
      if(solveForNounAndVerb(i, j) == x) return (i,j)
    }
    println("Failure")
    (-1,-1)
  }
}
