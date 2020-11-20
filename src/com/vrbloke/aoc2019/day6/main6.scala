package com.vrbloke.aoc2019.day6

import java.util.Scanner
import java.io.File

object main6 extends App {
  val mainDelegate = new UniversalOrbitMap(new Scanner(new File("input/input6.txt")))
  mainDelegate.printCOM()
  println(mainDelegate.minimumTransfers())
}
