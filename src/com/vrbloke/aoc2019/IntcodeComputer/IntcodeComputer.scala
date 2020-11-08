package com.vrbloke.aoc2019.IntcodeComputer

import java.util.Scanner
import scala.math.pow

class IntcodeComputer(val inputReader: java.util.Scanner) {
  private val memory: Array[Int] = withAnnouncement("Dumping input"){readInput()}
  private var head: Int = 0

  def withAnnouncement[T](msg: String)(x: => T): T = {
    println(msg)
    x
  }

  @scala.annotation.tailrec
  private def readInput(xs: Array[Int] = Array()): Array[Int] = {
    if(inputReader.hasNextInt) readInput(xs :+ inputReader.nextInt)
    else {
      inputReader.close()
      xs
    }
  }

  def dumpMemory(): String = memory.mkString(",")
  def read(position: Int): Int = memory(position)
  def set(position: Int, value: Int): Unit = { memory(position) = value }
  private def readMode0(arg: Int): Int = read(read(arg))
  private def readMode1(arg: Int): Int = read(arg)
  private val readModes = List(readMode0 _, readMode1 _)
  private def checkMode(argno: Int): Int = (read(head) / pow(10, 1+argno).toInt) % 10
  private def parseParam(argno: Int): Int = readModes(checkMode(argno))(head+argno)

  def processOpcode(): Int = {
    read(head) % 100 match {
      case 99 => return 99
      case 1 =>
        set(read(head+3), parseParam(1) + parseParam(2))
        head += 4
      case 2 =>
        set(read(head+3), parseParam(1) * parseParam(2))
        head += 4
      case 3 =>
        print("Provide input:")
        set(read(head+1), scala.io.StdIn.readInt())
        head += 2
      case 4 =>
        println(parseParam(1))
        head += 2
      case 5 =>
        head = if(0 != parseParam(1)) parseParam(2) else head+3
      case 6 =>
        head = if(0 == parseParam(1)) parseParam(2) else head+3
      case 7 =>
        set(read(head+3), if(parseParam(1) < parseParam(2)) 1 else 0)
        head += 4
      case 8 =>
        set(read(head+3), if(parseParam(1) == parseParam(2)) 1 else 0)
        head += 4
      case _ => println("Incorrect opcode!!")
        return 99
    }
    memory(head)
  }

  @scala.annotation.tailrec
  private def runCode(): Unit = {
    if(processOpcode() != 99) runCode()
  }

  def solveForNounAndVerb(noun: Int, verb: Int): Int = {
    withAnnouncement("Solving puzzle...") {
      memory(1) = noun
      memory(2) = verb
      runCode()
      memory(0)
    }
  }

  def execute(): Unit = runCode()
  def findOutputWithNounAndVerb(x: Int): (Int,Int) = {
    for(i <- 0 to 99; j <- 0 to 99) {
      val solver = new IntcodeComputer(new Scanner(memory.mkString(",")).useDelimiter(","))
      if(solver.solveForNounAndVerb(i, j) == x) return (i,j)
    }
    println("Failure")
    (-1,-1)
  }
}
