package com.vrbloke.aoc2019.day2

import java.util.Scanner

class IntcodeComputer(val inputReader: java.util.Scanner) {
  private var memory: Array[Int] = withAnnouncement("Dumping input"){dumpInput()}
  private var head: Int = 0

  def withAnnouncement[T](msg: String)(x: => T): T = {
    println(msg)
    x
  }

  @scala.annotation.tailrec
  private def dumpInput(xs: Array[Int] = Array()): Array[Int] = {
    if(inputReader.hasNextInt) dumpInput(xs :+ inputReader.nextInt)
    else {
      inputReader.close()
      xs
    }
  }

  def processOpcode(): Int = {
    memory(head) match {
      case 99 =>
      case 1 =>
        memory(memory(head+3)) = memory(memory(head+1)) + memory(memory(head+2))
        head += 4
      case 2 =>
        memory(memory(head+3)) = memory(memory(head+1)) * memory(memory(head+2))
        head += 4
      case _ => println("Incorrect opcode!!")
        memory(head) = 99
    }
    memory(head)
  }

  @scala.annotation.tailrec
  private def runCode(): Unit = {
    if(processOpcode() != 99) runCode()
  }

  def solve(noun: Int, verb: Int): Int = {
    withAnnouncement("Solving puzzle...") {
      memory(1) = noun
      memory(2) = verb
      runCode()
      memory(0)
    }
  }

  def solveTest(): String = {
    runCode()
    println(s"Head at opcode ${memory(head)}")
    memory.mkString(",")
  }

  def findOutput(x: Int): (Int,Int) = {
    for(i <- 0 to 99; j <- 0 to 99) {
      val solver = new IntcodeComputer(new Scanner(memory.mkString(",")).useDelimiter(","))
      if(solver.solve(i, j) == x) return (i,j)
    }
    println("Failure")
    (-1,-1)
  }
}
