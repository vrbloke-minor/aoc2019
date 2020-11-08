package com.vrbloke.aoc2019.IntcodeComputer

import java.util.Scanner
import scala.math.pow

class IntcodeComputer(val inputReader: java.util.Scanner) {
  private val memory: Array[Int] = withAnnouncement("Dumping input"){readInput()}
  private var head: Int = 0
  private val readModes = List(readMode0 _, readMode1 _)

  def withAnnouncement[T](msg: String)(x: => T): T = {
    println(msg)
    x
  }

  def read(position: Int): Int = memory(position)
  def readMode0(arg: Int): Int = read(read(arg))
  def readMode1(arg: Int): Int = read(arg)
  def checkMode(argno: Int): Int = (read(head) / pow(10, 1+argno).toInt) % 10
  def set(position: Int, value: Int): Unit = { memory(position) = value }
  def dumpMemory(): String = memory.mkString(",")

  @scala.annotation.tailrec
  private def readInput(xs: Array[Int] = Array()): Array[Int] = {
    if(inputReader.hasNextInt) readInput(xs :+ inputReader.nextInt)
    else {
      inputReader.close()
      xs
    }
  }

  def processOpcode(): Int = {
    val h = head
    read(h) % 100 match {
      case 99 => return 99
      case 1 =>
        set(read(h+3), readModes(checkMode(1))(h+1) + readModes(checkMode(2))(h+2))
        head += 4
      case 2 =>
        set(read(h+3), readModes(checkMode(1))(h+1) * readModes(checkMode(2))(h+2))
        head += 4
      case 3 =>
        print("Provide input:")
        set(read(h+1), scala.io.StdIn.readInt())
        head += 2
      case 4 =>
        println(readModes(checkMode(1))(h+1))
        head += 2
      case 5 =>
        head = if(0 != readModes(checkMode(1))(h+1)) readModes(checkMode(2))(h+2) else head+3
      case 6 =>
        head = if(0 == readModes(checkMode(1))(h+1)) readModes(checkMode(2))(h+2) else head+3
      case 7 =>
        set(read(h+3), if(readModes(checkMode(1))(h+1) < readModes(checkMode(2))(h+2)) 1 else 0)
        head += 4
      case 8 =>
        set(read(h+3), if(readModes(checkMode(1))(h+1) == readModes(checkMode(2))(h+2)) 1 else 0)
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
  def execute(): Unit = runCode()

  def solveForNounAndVerb(noun: Int, verb: Int): Int = {
    withAnnouncement("Solving puzzle...") {
      memory(1) = noun
      memory(2) = verb
      runCode()
      memory(0)
    }
  }

  def findOutput(x: Int): (Int,Int) = {
    for(i <- 0 to 99; j <- 0 to 99) {
      val solver = new IntcodeComputer(new Scanner(memory.mkString(",")).useDelimiter(","))
      if(solver.solveForNounAndVerb(i, j) == x) return (i,j)
    }
    println("Failure")
    (-1,-1)
  }
}
