package com.vrbloke.aoc2019.IntcodeComputer
import java.util.Scanner

class AutomaticIC(inputReader: java.util.Scanner) extends AbstractIC(inputReader) {
  var inputs: Array[Int] = Array()
  var outputs: Array[Int] = Array()

  override def inputFunc(): Option[Int] = {
    if(inputs.isEmpty) None
    else {
      val ret = inputs.head
      inputs = inputs.tail
      Some(ret)
    }
  }
  override def outputFunc(arg: Int): Unit = {
    //println("Outputting...")
    outputs = outputs :+ arg
  }
  override def inputMessage: String = ""

  def resetInputs(): Unit = inputs = Array()
  def resetOutputs(): Unit = outputs = Array()
  def resetIO(): Unit = {resetInputs(); resetOutputs();}
  def fullReset(): Unit = {restoreInitialState(); resetIO();}
}
