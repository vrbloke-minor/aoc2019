package com.vrbloke.aoc2019.day4

class PasswordChecker(passwords: Seq[Int]) {

  private def Boolean2Int(x: Boolean): Int = if(x) 1 else 0
  @scala.annotation.tailrec
  private def Int2ListInt(x: Int, xs: List[Int] = List()): List[Int] = if(x == 0) xs else Int2ListInt(x/10, x%10 :: xs)
  private def Int2ListIntPairs(x: Int): Seq[(Int,Int)] = {
    val curr = Int2ListInt(x)
    curr.init zip curr.tail
  }

  private def checkForRepeat(_pass: Int): Boolean = {
    val pairs = Int2ListIntPairs(_pass)
    @scala.annotation.tailrec
    def _checkForRepeat(pass: Seq[(Int,Int)]): Boolean = {
      if(pass.isEmpty) false
      else {
        if(pass.head._1 == pass.head._2) true
        else _checkForRepeat(pass.tail)
      }
    }
    _checkForRepeat(pairs)
  }

  private def checkForTwopeat(_pass: Int): Boolean = {
    val pairs = Int2ListIntPairs(_pass)
    @scala.annotation.tailrec
    def _checkForNoTwopeat(_pass: Seq[(Int,Int)], currRep: Int): Boolean = {
      if(_pass.isEmpty) if(currRep == 2) true else false
      else {
        if(_pass.head._1 == _pass.head._2) _checkForNoTwopeat(_pass.tail, currRep + 1)
        else if(currRep == 2) true
        else _checkForNoTwopeat(_pass.tail, 1)
      }
    }
    _checkForNoTwopeat(pairs, 1)
  }

  private def checkForNonDecreasing(_pass: Int): Boolean = {
    val pairs = Int2ListIntPairs(_pass)
    @scala.annotation.tailrec
    def _checkForNonDecreasing(pass: Seq[(Int,Int)]): Boolean = {
      if(pass.isEmpty) true
      else{
        if(pass.head._2 < pass.head._1) false
        else _checkForNonDecreasing(pass.tail)
      }
    }
    _checkForNonDecreasing(pairs)
  }

  @scala.annotation.tailrec
  private def checkPasswords(xs: Seq[Int], predicates: List[Int => Boolean]): Seq[Int] = {
    if(predicates.isEmpty) xs
    else checkPasswords(xs.filter(predicates.head(_)), predicates.tail)
  }

  private def solvePuzzle(predicates: List[Int => Boolean]): Int = checkPasswords(passwords, predicates).length
  def solvePuzzlePart1(): Int = solvePuzzle(List(checkForRepeat, checkForNonDecreasing))
  def solvePuzzlePart2(): Int = solvePuzzle(List(checkForTwopeat, checkForNonDecreasing))

}
