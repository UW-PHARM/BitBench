package IterativeSVD

import bitstream.types._
import bitstream.simulator.units._
import math._

trait Parameters {
  val m: Int
  val n: Int
}

object DefaultParams extends Parameters {
  val m = 2
  val n = 2
}

case class Module (params: Parameters) {

  // Define outputs
  val outputList = List(("v", 2, 2), ("u", 2, 2), ("sigma", 1, 1))

  // Set feedback mode
  val feedbackMode : Int = 1

  def loop(A: Matrix[SBitstream], v: Matrix[SBitstream]):
      (Matrix[SBitstream], Matrix[SBitstream], SBitstream) = {
    // Update right singular vector
    var w = A * v
    var wScaled = w :/ math.sqrt(params.m)
    var u = wScaled / Matrix.norm(wScaled)

    // Update left singular vector
    var z = A.T * u
    var zScaled = z :/ math.sqrt(params.n)
    var sigma = Matrix.norm(zScaled)
    var _v = zScaled / sigma

    (u, _v, sigma)
  }

}

object IterativeSVD {
  final val epsilon = 0.1

  def main(args: Array[String]) {

    val m = DefaultParams.m
    val n = DefaultParams.n

    // Generate inputs
    var ADouble = 2 * Matrix.rand[Double](m, n) - 1
    var vDouble = Matrix.rand[Double](n, 1)
    vDouble = vDouble / Matrix.norm(vDouble)

    // Calculate scaling
    val alpha = 2 * max(Matrix.norm(ADouble, "inf"), Matrix.norm(ADouble, "L1"))
    ADouble = ADouble / alpha

    // Convert to bitstream
    var A = ADouble.toSBitstream
    var v = vDouble.toSBitstream

    var err = 1.0
    var sigma = SBitstream(1.0)
    var u = Matrix[SBitstream](m, 1)

    // instantiate module
    var dut = Module(DefaultParams)

    do {
      dut.loop(A, v) match {
        case (uNew, vNew, sigmaNew) => {
          u = uNew;
          v = vNew;
          sigma = sigmaNew;
        }
      }

      // Update error
      err = Matrix.norm(alpha * (A.toDouble * v.toDouble - u.toDouble * sigma.value * math.sqrt(n)))
    } while(err > epsilon)

    println("\n  A:" + (ADouble * alpha).mkString)
    println("\n  u:" + u.mkString)
    println("\n  v:" + v.mkString)
    println(f"  sigma: ${sigma.value * alpha * math.sqrt(n)}%.4f")
    println(f"  err: $err%.4f")

  }
}