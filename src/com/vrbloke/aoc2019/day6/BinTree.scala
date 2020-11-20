package com.vrbloke.aoc2019.day6

class BinTree[T]
(val data: T, var parent: Option[BinTree[T]] = None,
 var leftT: Option[BinTree[T]] = None, var rightT: Option[BinTree[T]] = None) {

  override def toString: String = data.toString

  def isChildOf(other: BinTree[T]): Boolean = {
    this == other.leftT.get || this == other.rightT.get
  }

  // Determine how many nodes exist above this node.
  def height(): Int = {
    var here = this
    var height = 0
    while (here.parent.nonEmpty) {
      height += 1
      here = here.parent.get
    }
    height
  }

  def countConnections(): Int = {
    var count = 0
    var stack = List(this)
    while (stack.nonEmpty) {
      val here = stack.head
      stack = stack.tail

      count += here.height()
      if (here.leftT.nonEmpty) stack = here.leftT.get :: stack
      if (here.rightT.nonEmpty) stack = here.rightT.get :: stack
    }
    count
  }

  def traverseInorder(): Unit = {
    var stack: List[BinTree[T]] = List()
    var here = this
    while (here != null || stack.nonEmpty) {
      while (here != null) {
        stack = here :: stack
        here = here.leftT.orNull
      }
      here = stack.head
      stack = stack.tail
      println(here.data)
      here = here.rightT.orNull
    }
  }

  def addChild(newBT: BinTree[T]): Unit = {
    if (newBT.parent.nonEmpty) {
      return
    }
    (leftT, rightT) match {
      case (None, _) => leftT = Some(newBT)
      case (Some(_), None) => rightT = Some(newBT)
      case _ =>
    }
    newBT.setParent(this)
  }

  def addChild(newStr: T): Unit = {
    val newBT = new BinTree[T](newStr)
    addChild(newBT)
  }

  def setParent(newBT: BinTree[T]): Unit = {
    if (isChildOf(newBT)) parent = Some(newBT)
    else newBT.addChild(this)
  }

  def findNodeOfValue(valToFind: T): Option[BinTree[T]] = {
    var stack = List(this)
    while(stack.nonEmpty) {
      val here = stack.head
      stack = stack.tail

      if(valToFind == here.data) return Some(here)
      if(here.leftT.nonEmpty) stack = here.leftT.get :: stack
      if(here.rightT.nonEmpty) stack = here.rightT.get :: stack
    }
    None
  }
}

object BinTree {
  def apply[T](data: T): BinTree[T] = new BinTree[T](data)
  def apply[T](data: T, dataL: T): BinTree[T] = {
    val newBT = new BinTree[T](data)
    newBT.addChild(dataL)
    newBT
  }

  def findCommonAncestor[T](t1: BinTree[T], t2: BinTree[T]): Option[BinTree[T]] = {
    var here = t1
    while(here != null) {
      if(here.findNodeOfValue(t2.data).nonEmpty) return Some(here)
      here = here.parent.orNull
    }
    None
  }
}

