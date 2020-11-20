package com.vrbloke.aoc2019.day6

class UniversalOrbitMap(inputReader: java.util.Scanner) {
  val uoMap: BinTree[String] = scanInput()


  def scanInput(): BinTree[String] = {
    var nodes: Set[BinTree[String]] = Set()
    while(inputReader.hasNext) {
      val next = inputReader.next.split(')')
      val (pOpt, cOpt) = (nodes.find(_.data == next(0)), nodes.find(_.data == next(1)))
      val (pNode, cNode) = ( pOpt.getOrElse( BinTree(next(0)) ), cOpt.getOrElse( BinTree(next(1)) ) )
      pNode.addChild(cNode)
      nodes = nodes + pNode + cNode
    }
    nodes.find(_.data == "COM").getOrElse{println("CRITICAL INPUT SCAN ERROR"); null}
  }

  def minimumTransfers(): Int = {
    val you = uoMap.findNodeOfValue("YOU").getOrElse{println("Can't find you!!"); null}
    val san = uoMap.findNodeOfValue("SAN").getOrElse{println("Can't find san!!"); null}
    val ancestor = BinTree.findCommonAncestor(you, san).getOrElse{println("Can't find ancestor!!"); null}
    you.height() + san.height() - 2 * ancestor.height() - 2
  }

  def printOrbits(): Unit = uoMap.traverseInorder()
  def countOrbits(): Int = uoMap.countConnections()
  def printCOM(): Unit = println(uoMap.data)
}
