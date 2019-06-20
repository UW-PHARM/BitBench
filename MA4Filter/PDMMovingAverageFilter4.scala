package PDMMovingAverageFilter4

import bitstream.types._
import bitstream.simulator.units._
import math._
import util.Random

trait Parameters {
  val n: Double
  val delay: Int
}

object DefaultParams extends Parameters {
  val n = 0.25
  val delay = 1
}

case class Module (params: Parameters) {

  // internal units
  var delay1 = DelayBuffer(params.delay)
  var delay2 = DelayBuffer(params.delay)
  var delay3 = DelayBuffer(params.delay)
  var sdm1 =  SDM()

  // Define outputs
  val outputList = List(("d4", 1, 1))

  // Set feedback mode
  val feedbackMode = 0

  def loop(x: Bit): Int = {
    // Get delay buffer values
    var d1_old = delay1.pop
    var d2_old = delay2.pop
    var d3_old = delay3.pop
    // var d4_old = delay4.pop

    // Calculate new Delay Buffer Values and filter output (d3)
    var d1 = x
    var d2 = d1_old
    val d3 = d2_old
    var d4 = sdm1.evaluate(params.n * (d1_old + d2_old + d3_old + d1))

    // Push new values into delay buffers
    delay1.push(d1)
    delay2.push(d2)
    delay3.push(d3)

    d4
  }

}

object PDMMovingAverageFilter4 {

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