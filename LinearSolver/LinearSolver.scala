package LinearSolver

import bitstream.types._
import math._

trait Parameters {
  val m: Int // A is mxn
  val n: Int
  val p: Int // X is nxp
}

object DefaultParams extends Parameters {
  val m = 2
  val n = 2
  val p = 1
}

case class Module (params: Parameters) {

  // Define outputs
  val outputList = List(("X", 2, 1))

  // Set feedback mode
  val feedbackMode: Int = 1

  def loop(alpha: SBitstream, etaInv: SBitstream, I: Matrix[SBitstream],
           A: Matrix[SBitstream], B: Matrix[SBitstream], X: Matrix[SBitstream]):
      Matrix[SBitstream] = {
    // Compute weights
    var Whop = I - alpha * A.T * A
    var Wff = alpha * etaInv * A.T

    // Update solution
    var _X = Whop * X + Wff * B

    _X
  }

}

object LinearSolver {
  final val T = 1000

  def main(args: Array[String]) {

    val m = DefaultParams.m
    val n = DefaultParams.n
    val p = DefaultParams.p

    // Generate inputs
    var I = Matrix.eye[SBitstream](n, n)
    var ADouble = 2 * Matrix.rand[Double](m, n) - 1
    var BDouble = Matrix.rand[Double](m, p)
    var alphaDouble = 0.1
    var etaInvDouble = 0.1

    // Calculate scaling
    var Amax = max(Matrix.norm(ADouble, "L1"), Matrix.norm(ADouble, "inf"))
    var Bmax = max(Matrix.norm(BDouble, "L1"), Matrix.norm(BDouble, "inf"))
    var ADoubleScaled = ADouble / Amax
    var BDoubleScaled = BDouble / Bmax

    // Convert to bitstream
    var A = ADoubleScaled.toSBitstream
    var B = BDoubleScaled.toSBitstream
    var alpha = SBitstream(alphaDouble)
    var etaInv = SBitstream(etaInvDouble)

    // instantiate module
    var dut = Module(DefaultParams)

    var X = Matrix.zeros[SBitstream](n, p)
    for (t <- 0 until T)
      X = dut.loop(alpha, etaInv, I, A, B, X)

    println("\n  A:" + ADouble.mkString)
    println("\n  B:" + BDouble.mkString)
    println("\n  X:" + (X.toDouble / etaInvDouble * Bmax / Amax).mkString)
  }
}