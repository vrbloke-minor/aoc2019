package com.vrbloke.aoc2019.day5
import com.vrbloke.aoc2019.IntcodeComputer.IC

import java.util.Scanner
import java.io.File

object main5 extends App {
  val mainDelegate = new IC(new Scanner(new File("input/input5.txt")).useDelimiter(","))
  mainDelegate.execute()
}
