package com.vrbloke.aoc2019.day6

import scala.collection.mutable

class BinTree[T](val data: T) {
  var parent: BinTree[T] = null
  var leftT: BinTree[T] = null
  var rightT: BinTree[T] = null

  override def toString: String = data.toString

  def height(): Int = {
    var currT = this
    var h = -1
    while(currT != null) {
      h += 1
      currT = currT.parent
    }
    h
  }

  def addChild(newBT: BinTree[T]): Unit = {
    if(leftT == null) {
      leftT = newBT
      leftT.addParent(this)
    }
    else if(rightT == null) {
      rightT = newBT
      rightT.addParent(this)
    }
  }
  def addChild(newStr: T): Unit = {
    val newBT = new BinTree[T](newStr)
    addChild(newBT)
  }

  def addParent(newBT: BinTree[T]): Unit = {
    if(parent == null) {
      parent = newBT
      parent.addChild(this)
    }
  }
  def addParent(newStr: T): Unit = {
    val newBT = new BinTree[T](newStr)
    addParent(newBT)
  }

  def makeParentOf(otherBT: BinTree[T]): Unit = {
    if(null == leftT) leftT = otherBT
    else if(null == rightT) rightT = otherBT
    else { return }
    otherBT.parent = this
  }
  def makeChildOf(otherBT: BinTree[T]): Unit = otherBT.makeParentOf(this)

  def traverseInorder(): Unit = {
    def _traverseInorder(currT: BinTree[T]): Unit = {
      if(currT.leftT != null) _traverseInorder(currT.leftT)
      println(currT.data)
      if(currT.rightT != null) _traverseInorder(currT.rightT)
    }
    _traverseInorder(this)
  }

  def findNodeOfValue(valToFind: T): BinTree[T] = {
    var ref: BinTree[T] = null
    @scala.annotation.tailrec
     def _findNodeOfValue(valToFind: T, currT: BinTree[T]): Unit = {
       if(currT.data == valToFind) ref = currT
       else if(currT.leftT != null) { _findNodeOfValue(valToFind, currT.leftT) }
       else if(currT.rightT != null) { _findNodeOfValue(valToFind, currT.rightT) }
     }
    _findNodeOfValue(valToFind, this)
    ref
  }

  def countEdges(): Int = {
    var count = 0
    def _countEdges(currT: BinTree[T]): Unit = {
      count += currT.height()
      if(currT.leftT != null) _countEdges(currT.leftT)
      if(currT.rightT != null) _countEdges(currT.rightT)
    }
    _countEdges(this)
    count
  }
}

object BinTree {
  @scala.annotation.tailrec
  def findNodeOfValueInMany[T](xs: List[BinTree[T]], valToFind: T): BinTree[T]  = {
    if(xs.isEmpty) null
    else {
      val found = xs.head.findNodeOfValue(valToFind)
      if(found != null) found
      else findNodeOfValueInMany[T](xs.tail, valToFind)
    }
  }

  def apply[T](data: T): BinTree[T] = new BinTree[T](data)

  def apply[T](data: T, dataL: T): BinTree[T] = {
    val newBT = new BinTree[T](data)
    newBT.addChild(dataL)
    newBT
  }

  def unapply[T](tree: BinTree[T]): T,
}
