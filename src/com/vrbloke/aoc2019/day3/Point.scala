package com.vrbloke.aoc2019.day3

import java.lang.Math.{abs, min, max}

class Point(val x: Int, val y: Int) {
  def binaryOp(op: (Int, Int) => Int)(rhs: Point) = new Point(op(x, rhs.x), op(y, rhs.y))
  def +(rhs: Point): Point = binaryOp(_+_)(rhs)
  def -(rhs: Point): Point = binaryOp(_-_)(rhs)
  def *(rhs: Point): Point = binaryOp(_*_)(rhs)
  def /(rhs: Point): Point = binaryOp(_/_)(rhs)
  override def toString: String = s"($x, $y)"
  def mDistance: Int = abs(x) + abs(y)
  def mDistance(rhs: Point): Int = abs(this.x - rhs.x) + abs(this.y - rhs.y)
  def toV(x: Point): Seq[Int] = min(this.y,x.y) to max(this.y,x.y)
  def toH(x: Point): Seq[Int] = min(this.x,x.x) to max(this.x,x.x)
}

object Point {
  def apply(x: Int, y: Int) = new Point(x,y)
}