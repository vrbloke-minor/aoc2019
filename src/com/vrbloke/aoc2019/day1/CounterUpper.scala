package com.vrbloke.aoc2019.day1

class CounterUpper(inputReader: java.util.Scanner) {
  def countUp(fuelSum: Int = 0): Int = {

    @scala.annotation.tailrec
    def fuelUp(mass: Int, inter: Int = 0): Int = {
      val fuelReq = mass/3 -2
      if(fuelReq < 0) inter
      else fuelUp(fuelReq, inter + fuelReq)
    }

    if(!inputReader.hasNextInt) fuelSum
    else countUp(fuelSum + fuelUp(inputReader.nextInt))
  }
}
