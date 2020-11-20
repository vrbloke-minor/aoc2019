package com.vrbloke.aoc2019.day7

import java.util.Scanner
import java.io.File
import com.vrbloke.aoc2019.IntcodeComputer.AutomaticIntcodeComputer

class AmplifierArray(num: Int, filepath: String) {
  val amplifiers: List[AutomaticIntcodeComputer] =
    (for {x <- 1 to num} yield new AutomaticIntcodeComputer(new Scanner(new File(filepath)))).toList
  val permutations: Iterator[IndexedSeq[Int]] = (0 until num).permutations
  var next = 0

  def withAnnouncement[T](msg: String)(x: => T): T = {
    println(msg)
    x
  }

  def setPhaseSettings(xs: IndexedSeq[Int]): Unit = {
    for ((amplifier, phase) <- amplifiers zip xs) {
      //println(amplifier.inputs.length)
      amplifier.inputs.length match {
        case 0 => amplifier.inputs = amplifier.inputs :+ phase
        case 1 | 2 => amplifier.inputs(0) = phase
        case _ => println("Inputs bizarre size.")
      }
      //println(amplifier.inputs.mkString(","))
    }
  }

  def findAllOutputs(): List[Int] = {
    //println(amplifiers(0).inputs.length)
    var outputs: List[Int] = List()
    for (permutation <- permutations) {
      //println(s"New permutation: ${permutation.mkString(",")}")
      setPhaseSettings(permutation)
      for (amplifier <- amplifiers) {
        amplifier.inputs(1) = if (amplifier == amplifiers.head) 0 else next
        //println(s"Input 0 is ${amplifier.inputs(0)}")
        amplifier.execute()
        next = amplifier.outputs(0)
        amplifier.restoreMemory()
        amplifier.resetHead()
        amplifier.resetIO()
        //println(amplifier.inputs.mkString("(", ",", ") "), amplifier.outputs.mkString("(", ",", ")"))
      }
      outputs = withAnnouncement(s"Growing outputs by $next"){outputs :+ next}
    }
    outputs
  }

  def findMaxOutput(): Int = findAllOutputs().max

  def getCode(index: Int): String = amplifiers(index).stringMemory()
  def getCodeOnAll: String = (for (x <- amplifiers) yield x.stringMemory()).mkString("\n")
}
