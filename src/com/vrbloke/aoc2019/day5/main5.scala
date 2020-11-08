package com.vrbloke.aoc2019.day5
import com.vrbloke.aoc2019.IntcodeComputer.IntcodeComputer

import java.util.Scanner
import java.io.File

object main5 extends App {
  val mainDelegate = new IntcodeComputer(new Scanner(new File("input/input5.txt")).useDelimiter(","))
  mainDelegate.execute()
}
