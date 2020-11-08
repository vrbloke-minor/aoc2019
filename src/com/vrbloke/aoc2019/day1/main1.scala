package com.vrbloke.aoc2019.day1

import java.io.File
import java.util.Scanner

object main1 extends App {
  println(System.getProperty("user.dir"))
  val inputReader = new Scanner(new File("input/input.txt"))
  val mainDelegate = new CounterUpper(inputReader)
  println(mainDelegate.countUp())
}
