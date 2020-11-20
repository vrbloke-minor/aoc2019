package com.vrbloke.aoc2019.IntcodeComputer
import java.util.Scanner

class AutomaticIntcodeComputer(inputReader: java.util.Scanner) extends AbstractIntcodeComputer(inputReader) {
  var inputs: Array[Int] = Array(0,0)
  var outputs: Array[Int] = Array()

  override def inputFunc(): Int = {
    if(inputs.isEmpty) throw new IndexOutOfBoundsException("Program requested input but one was not provided")
    else {
      val ret = inputs.head
      inputs = inputs.tail
      ret
    }
  }
  override def outputFunc(arg: Int): Unit = {
    outputs = outputs :+ arg
    //println(s"Outputting $arg")
  }
  override def inputMessage: String = ""

  def resetIO(): Unit = {
    inputs = Array(0,0)
    outputs = Array()
  }
}
