package PDMBandpassFilterBQ

import bitstream.types._
import bitstream.simulator.units._
import math._
import util.Random

trait Parameters {
  val b0: Double
  val b1: Double
  val b2: Double
  val a1: Double
  val a2: Double
  val delay: Int
}

object DefaultParams extends Parameters {
  val b0 = 1.0625
  val b1 = 0.4551
  val b2 = 0.9375
  val a1 = 0.0625
  val a2 = -0.0625
  val delay = 1
}

case class Module (params: Parameters) {

  // internal units
  var delay1 = DelayBuffer(params.delay)
  var delay2 = DelayBuffer(params.delay)
  var delay3 = DelayBuffer(params.delay)
  var delay4 = DelayBuffer(params.delay)
  var sdm1 =  SDM()

  // Define outputs
  val outputList = List(("d3", 1, 1))

  // Set feedback mode
  val feedbackMode = 0

  def loop(x: Bit): Int = {
    // Get delay buffer values
    var d1_old = delay1.pop
    var d2_old = delay2.pop
    var d3_old = delay3.pop
    var d4_old = delay4.pop

    // Calculate new Delay Buffer Values and filter output (d3)
    var d1 = x
    var d2 = d1_old
    var d3 = sdm1.evaluate(params.b0 * d1 +
                           params.b1 * d1_old +
                           params.b2 * d2_old -
                           params.a1 * d3_old -
                           params.a2 * d4_old)
    var d4 = d3_old

    // Push new values into delay buffers
    delay1.push(d1)
    delay2.push(d2)
    delay3.push(d3)
    delay4.push(d4)

    d3
  }

}

object PDMBandpassFilterBQ {

  def main(args: Array[String]): Unit = {

    // Generate input bitstream
    var x = DBitstream()
    var sampleRate = 0
    var numChannels = 0
    var T = 0
    DBitstream.preloadBitstream("./examples/Expchirp.dat") match {
      case (stream, rate, channels, length) => {
        x = stream
        sampleRate = rate
        numChannels = channels
        T = length
      }
    }

    // instantiate module
    var dut = Module(DefaultParams)

    var y = DBitstream()
    for (t <- 0 until T) {
      y.push(dut.loop(x.pop))
    }

    DBitstream.exportBitstream("./examples/Expchirp-output.dat", y, sampleRate, numChannels, T)

  }
}