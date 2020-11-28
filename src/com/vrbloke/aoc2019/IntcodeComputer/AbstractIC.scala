package com.vrbloke.aoc2019.IntcodeComputer

import java.util.Scanner
import scala.math.pow

abstract class AbstractIC(val inputReader: java.util.Scanner) {
  private val memory: Array[Int] = withAnnouncement("Dumping input"){
    inputReader.useDelimiter(",")
    readInput()
  }
  private val unmodifiedMemory: Array[Int] = memory
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

  // Boolean checks
  def hasHalted: Boolean = try { read(head) % 100 == 99 } catch { case e: java.lang.ArrayIndexOutOfBoundsException => true }
  def notHalted: Boolean = !hasHalted
  // Raw memory access
  def opcodeAtHead: Int = read(head) % 100
  def stringMemory(): String = memory.mkString(",")
  def dumpMemory: Array[Int] = memory
  // Reset methods
  def restoreMemory(): Unit = for(x <- memory.indices) {memory(x) = unmodifiedMemory(x)}
  def resetHead(): Unit = head = 0
  def restoreInitialState(): Unit = {restoreMemory(); resetHead()}
  // Methods for reading and writing to tape
  def read(position: Int): Int = memory(position)
  def set(position: Int, value: Int): Unit = { memory(position) = value }
  // Helper methods for opcode processing
  private def readMode0(arg: Int): Int = read(read(arg))
  private def readMode1(arg: Int): Int = read(arg)
  private val readModes = List(readMode0 _, readMode1 _)
  private def checkMode(argno: Int): Int = (read(head) / pow(10, 1+argno).toInt) % 10
  private def parseParam(argno: Int): Int = readModes(checkMode(argno))(head+argno)
  // Abstract methods for input/output
  def inputFunc(): Option[Int]
  def outputFunc(arg: Int): Unit
  def inputMessage: String
  // Methods for running the code
  def execute(): Unit = runCode()
  @scala.annotation.tailrec
  private def runCode(): Unit = {
    if(processOpcode() != 99) runCode()
  }

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
        print(inputMessage)
        val readInput = inputFunc()
        if(readInput.isEmpty) return 99
        set(read(head+1), readInput.get)
        head += 2
      case 4 =>
        outputFunc(parseParam(1))
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
    try { memory(head) } catch { case e: java.lang.ArrayIndexOutOfBoundsException => 99}
  }


}
