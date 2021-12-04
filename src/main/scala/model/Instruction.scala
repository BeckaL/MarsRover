package model

sealed trait Instruction

case object Forward             extends Instruction
case object RotateClockwise     extends Instruction
case object RotateAnticlockwise extends Instruction
