package PDMMovingAverageFilter32

import bitstream.types._
import bitstream.simulator.units._
import math._
import util.Random

trait Parameters {
  val n: Double
  val delay: Int
}

object DefaultParams extends Parameters {
  val n = 0.03125
  val delay = 1
}

case class Module (params: Parameters) {

  // internal units
  var delay1 = DelayBuffer(params.delay)
  var delay2 = DelayBuffer(params.delay)
  var delay3 = DelayBuffer(params.delay)
  var delay4 = DelayBuffer(params.delay)
  var delay5 = DelayBuffer(params.delay)
  var delay6 = DelayBuffer(params.delay)
  var delay7 = DelayBuffer(params.delay)
  var delay8 = DelayBuffer(params.delay)
  var delay9 = DelayBuffer(params.delay)
  var delay10 = DelayBuffer(params.delay)
  var delay11 = DelayBuffer(params.delay)
  var delay12 = DelayBuffer(params.delay)
  var delay13 = DelayBuffer(params.delay)
  var delay14 = DelayBuffer(params.delay)
  var delay15 = DelayBuffer(params.delay)
  var delay16 = DelayBuffer(params.delay)
  var delay17 = DelayBuffer(params.delay)
  var delay18 = DelayBuffer(params.delay)
  var delay19 = DelayBuffer(params.delay)
  var delay20 = DelayBuffer(params.delay)
  var delay21 = DelayBuffer(params.delay)
  var delay22 = DelayBuffer(params.delay)
  var delay23 = DelayBuffer(params.delay)
  var delay24 = DelayBuffer(params.delay)
  var delay25 = DelayBuffer(params.delay)
  var delay26 = DelayBuffer(params.delay)
  var delay27 = DelayBuffer(params.delay)
  var delay28 = DelayBuffer(params.delay)
  var delay29 = DelayBuffer(params.delay)
  var delay30 = DelayBuffer(params.delay)
  var delay31 = DelayBuffer(params.delay)
  var sdm1 =  SDM()

  // Define outputs
  val outputList = List(("d32", 1, 1))

  // Set feedback mode
  val feedbackMode = 0

  def loop(x: Bit): Int = {
    // Get delay buffer values
    var d1_old =  delay1.pop
    var d2_old =  delay2.pop
    var d3_old =  delay3.pop
    var d4_old =  delay4.pop
    var d5_old =  delay5.pop
    var d6_old =  delay6.pop
    var d7_old =  delay7.pop
    var d8_old =  delay8.pop
    var d9_old =  delay9.pop
    var d10_old =  delay10.pop
    var d11_old =  delay11.pop
    var d12_old =  delay12.pop
    var d13_old =  delay13.pop
    var d14_old =  delay14.pop
    var d15_old =  delay15.pop
    var d16_old =  delay16.pop
    var d17_old =  delay17.pop
    var d18_old =  delay18.pop
    var d19_old =  delay19.pop
    var d20_old =  delay20.pop
    var d21_old =  delay21.pop
    var d22_old =  delay22.pop
    var d23_old =  delay23.pop
    var d24_old =  delay24.pop
    var d25_old =  delay25.pop
    var d26_old =  delay26.pop
    var d27_old =  delay27.pop
    var d28_old =  delay28.pop
    var d29_old =  delay29.pop
    var d30_old =  delay30.pop
    var d31_old =  delay31.pop

    // Calculate new Delay Buffer Values and filter output (d3)
    var d1 = x
    var d2 = d1_old
    var d3 = d2_old
    var d4 = d3_old
    var d5 = d4_old
    var d6 = d5_old
    var d7 = d6_old
    var d8 = d7_old
    var d9 = d8_old
    var d10 = d9_old
    var d11 = d10_old
    var d12 = d11_old
    var d13 = d12_old
    var d14 = d13_old
    var d15 = d14_old
    var d16 = d15_old
    var d17 = d16_old
    var d18 = d17_old
    var d19 = d18_old
    var d20 = d19_old
    var d21 = d20_old
    var d22 = d21_old
    var d23 = d22_old
    var d24 = d23_old
    var d25 = d24_old
    var d26 = d25_old
    var d27 = d26_old
    var d28 = d27_old
    var d29 = d28_old
    var d30 = d29_old
    var d31 = d30_old
    val d32 = sdm1.evaluate(params.n * (d1 +
                                   d1_old +
                                   d2_old +
                                   d3_old +
                                   d4_old +
                                   d5_old +
                                   d6_old +
                                   d7_old +
                                   d8_old +
                                   d9_old +
                                   d10_old +
                                   d11_old +
                                   d12_old +
                                   d13_old +
                                   d14_old +
                                   d15_old +
                                   d16_old +
                                   d17_old +
                                   d18_old +
                                   d19_old +
                                   d20_old +
                                   d21_old +
                                   d22_old +
                                   d23_old +
                                   d24_old +
                                   d25_old +
                                   d26_old +
                                   d27_old +
                                   d28_old +
                                   d29_old +
                                   d30_old +
                                   d31_old))

    // Push new values into delay buffers
    delay1.push(d1)
    delay2.push(d2)
    delay3.push(d3)
    delay4.push(d4)
    delay5.push(d5)
    delay6.push(d6)
    delay7.push(d7)
    delay8.push(d8)
    delay9.push(d9)
    delay10.push(d10)
    delay11.push(d11)
    delay12.push(d12)
    delay13.push(d13)
    delay14.push(d14)
    delay15.push(d15)
    delay16.push(d16)
    delay17.push(d17)
    delay18.push(d18)
    delay19.push(d19)
    delay20.push(d20)
    delay21.push(d21)
    delay22.push(d22)
    delay23.push(d23)
    delay24.push(d24)
    delay25.push(d25)
    delay26.push(d26)
    delay27.push(d27)
    delay28.push(d28)
    delay29.push(d29)
    delay30.push(d30)
    delay31.push(d31)

    d32
  }

}

object PDMMovingAverageFilter32 {

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