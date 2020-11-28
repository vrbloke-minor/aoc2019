package com.vrbloke.aoc2019.day7

import java.util.Scanner
import java.io.File
import com.vrbloke.aoc2019.IntcodeComputer.AutomaticIC

class AmplifierArray(num: Int, filepath: String, permLower: Int = 0) {
  val amplifiers: List[AutomaticIC] =
    (for {x <- 1 to num} yield new AutomaticIC(new Scanner(new File(filepath)))).toList
  val phasePermutations: Iterator[IndexedSeq[Int]] = (permLower until permLower + num).permutations

  def withAnnouncement[T](msg: String)(x: => T): T = {
    println(msg)
    x
  }

  // Phase settings must be applied before other input is taken!!
  def setPhaseSettings(xs: IndexedSeq[Int]): Unit = {
    (amplifiers zip xs).foreach(x => x._1.inputs = x._1.inputs :+ x._2)
  }

  // Use this method when feedback is guaranteed not to happen!
  def findAllOutputs(generateOutput : () => Int): List[Int] = {
    var outputs: List[Int] = List()
    for (permutation <- phasePermutations) {
      println("findAllOutputs: Searching for new Outputs")
      amplifiers.foreach(_.fullReset())
      setPhaseSettings(permutation)
      outputs = outputs :+ generateOutput()
    }
    outputs
  }

  def runOneLoop(): Int = {
    var previousOutput: Option[Int] = None
    for(amp <- amplifiers) {
      amp.restoreInitialState()
      amp.resetOutputs()
      amp.inputs = amp.inputs :+ (if(previousOutput.isEmpty) 0 else previousOutput.get)
      amp.execute()
      previousOutput = Some(amp.outputs.head)
      amp.inputs = amp.inputs.dropRight(1)
    }
    previousOutput.get
  }
  def runFeedbackLoops(): Int = {
    var lastOutput: Array[Int] = Array(0)
    var sequence = amplifiers
    //var emergencyIterator = 0
    while(sequence.nonEmpty) {
      println(s"Entering sequence of length ${sequence.length}")
      for(amp <- sequence) {
        amp.inputs = amp.inputs concat lastOutput
        amp.execute()
        lastOutput = amp.outputs
        amp.resetOutputs()
        //println(s"As part of sequence, outputting ${lastOutput.mkString(",")}")
        //emergencyIterator = emergencyIterator + 1
      }
      //println(s"Heads at opcodes ${amplifiers.map(_.opcodeAtHead).mkString(",")}")
      sequence = sequence.filter(_.notHalted)
    }
    lastOutput.last
  }

  def findMaxOutput(generateOutput: () => Int): Int = findAllOutputs(generateOutput).max

  def getCode(index: Int): String = amplifiers(index).stringMemory()
  def getCodeOnAll: String = (for (x <- amplifiers) yield x.stringMemory()).mkString("\n")
}
