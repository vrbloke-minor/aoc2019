package com.vrbloke.aoc2019.day6
import javax.swing.tree.DefaultTreeModel
import util.control.Breaks.{break, breakable}

class UniversalOrbitMap(inputReader: java.util.Scanner) {
  val uoMap: BinTree[String] = scanInput()


  def scanInput(): BinTree[String] = {
    var uoMaps: List[BinTree[String]] = List()
    while(inputReader.hasNext()) {
      val next: Array[String] = inputReader.next().split(')')
      val (leftSide, rightSide) = (next(0), next(1))
      // If no maps, create new map by default.
      if(uoMaps.isEmpty) {
        uoMaps = uoMaps :+ BinTree[String](leftSide, rightSide)
      } else {
        // If maps exist, look for left/right side in each map
        var node1 = BinTree.findNodeOfValueInMany(uoMaps, leftSide)
        if(null != node1) {
          val node2 = BinTree.findNodeOfValueInMany(uoMaps, rightSide)
          if(null != node2) { node1.makeParentOf(node2) }  // Found leftSide AND rightSide
          else { node1.addChild(rightSide) } // Found leftSide but not rightSide
        } else {
          node1 = BinTree.findNodeOfValueInMany(uoMaps, rightSide)
          if(null != node1) {
            node1.addParent(leftSide)  // Found rightSide but not leftSide
          } else {
            uoMaps = uoMaps :+ BinTree[String](leftSide, rightSide)  // Found neither leftSide nor rightSide
          }
        }
      }
    }
    // We expect all lists are now connected
    val COM = BinTree.findNodeOfValueInMany(uoMaps, "COM")
    COM
  }

  def printOrbits(): Unit = uoMap.traverseInorder()
  def countOrbits(): Int = uoMap.countEdges()
  def printCOM(): Unit = println(uoMap.data)
}
