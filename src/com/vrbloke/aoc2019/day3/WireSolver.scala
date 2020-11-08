package com.vrbloke.aoc2019.day3

import java.util.Scanner

class WireSolver(val inputReader: Scanner) {
  val (wire1, wire2): (List[Point], List[Point]) = scanWires()

  implicit def Tuple22Point(x: (Int,Int)): Point = new Point(x._1, x._2)

  def checkIntersection(horizontal: (Point, Point), vertical: (Point, Point)): Boolean = {
    val (p1,p2) = horizontal
    val (q1,q2) = vertical
    (p1 toH p2).contains(q1.x) && (q1 toV q2).contains(p1.y)
  }

  def checkColinear(p1: Point, p2: Point, p3: Point): Boolean = {
    (p1.x == p2.x, p1.y == p2.y) match {
      case (true, false) => p3.x == p1.x && (p1 toV p2).contains(p3.y)
      case (false, true) => p3.y == p1.y && (p1 toH p2).contains(p3.x)
      case _ =>
        println("Something went wrong! checkColinear")
        false
    }
  }

  @scala.annotation.tailrec
  private def findFirst(xs: List[(Point,Point)], predicate: ((Point,Point)) => Boolean): (Point,Point) = {
    if(predicate(xs.head)) xs.head
    else if(xs.isEmpty) ((0,0), (0,0))
    else findFirst(xs.tail, predicate)
  }

  def scanWires(): (List[Point], List[Point]) = {
    inputReader.useDelimiter("\n")
    val stringList1 = inputReader.next().split(',')
    val stringList2 = inputReader.next().split(',')
    inputReader.close()

    @scala.annotation.tailrec
    def buildPointList(xs: Array[String], inter: List[Point] = List(Point(0,0))): List[Point] = {
      if(xs.isEmpty) inter
      else {
        val change = xs.head.tail.toInt
        buildPointList(xs.tail, inter :+ inter.last + (change,change) * (xs.head.head match {
          case 'R' => (1,0)
          case 'L' => (-1,0)
          case 'U' => (0,1)
          case 'D' => (0,-1)
        }))
      }
    }

    (buildPointList(stringList1), buildPointList(stringList2))
  }

  def findIntersections(): List[Point] = {
    var intsList: List[Point] = List()
    for((p1, p2) <- wire1.init zip wire1.tail) {
      val matchingCoor = (p1.x == p2.x, p1.y == p2.y)
      for((q1, q2) <- wire2.init zip wire2.tail) {
        val matchingCoorQ = (q1.x == q2.x, q1.y == q2.y)
        if(matchingCoorQ != matchingCoor) matchingCoorQ match {
          case (true, false) =>
            intsList = if(checkIntersection((p1, p2), (q1, q2)))
              intsList :+ (q1.x, p1.y)
              else intsList
          case (false, true) =>
            intsList = if(checkIntersection((q1, q2), (p1, p2)))
              intsList :+ (p1.x, q1.y)
              else intsList
          case _ =>
        }
      }
    }
    intsList
  }

  def findSteps(): List[Int] = {
    val intersections = findIntersections()

    @scala.annotation.tailrec
    def findStepsForEach(p3s: List[Point], inter: List[Int] = List()): List[Int] = {
      if(p3s.isEmpty) inter
      else {
        val firstWirePairs: List[(Point, Point)] = wire1.init zip wire1.tail
        val firstValidW1Pair = findFirst(firstWirePairs, (x: (Point,Point)) => checkColinear(x._1, x._2, p3s.head))

        val secondWirePairs = wire2.init zip wire2.tail
        val firstValidW2Pair = findFirst(secondWirePairs, (x: (Point,Point)) => checkColinear(x._1, x._2, p3s.head))

        @scala.annotation.tailrec
        def addUpDistances(xs: List[(Point, Point)], firstValid: (Point,Point), inter: Int = 0): Int = {
          if(xs.head != firstValid) addUpDistances(xs.tail, firstValid, inter + (xs.head._1 mDistance xs.head._2))
          else inter + (xs.head._1 mDistance p3s.head)
        }
        findStepsForEach(p3s.tail, inter :+ addUpDistances(firstWirePairs, firstValidW1Pair) + addUpDistances(secondWirePairs, firstValidW2Pair))
      }
    }

    findStepsForEach(intersections)
  }

  def solvePuzzlePart1(): Int = findIntersections().map(_.mDistance).min

  def solvePuzzlePart2(): Int = findSteps().min

  def printWires(): Unit = {
    println("Printing wire 1: " + wire1.mkString(","))
    println("Printing wire 2: " + wire2.mkString(","))
  }
}
