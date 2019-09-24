# BitBench
A benchmark suite for bitstream computing

### Citation

If you use BitBench in your work, please cite the following paper:
```
@INPROCEEDINGS{BitBench2019
	author = {Kyle Daruwalla and Heng Zhuo and Carly Schulz and Mikko Lipasti},
	title = {{BitBench}: A Benchmark for Bitstream Computing},
	booktitle = {Languages, Toolchains, and Compilers for Embedded Systems},
	year = {2019},
	month = {June}
}
```

## Benchmarks

BitBench contains 8 benchmarks:
1. Linear Solver (Optical Flow)
2. Linear Solver (Object Tracking)
3. Linear Solver (Inverse Kinematics)
4. Iterative SVD
5. State-Variable Filter
6. Bi-Quad Filter
7. Moving Average Filter (Width of 4)
8. Moving Average Filter (Width of 32)

### Running the benchmarks

To generate the Verilog for the benchmarks, run the following (e.g. iterative SVD):
```
sbt:bitbench> iterative_svd/clean
sbt:bitbench> iterative_svd/compile
```

To run the test code, run the following (e.g. iterative SVD):
```
sbt:bitbench> iterative_svd/run
```

To disable hardware generation, comment out the plugin `scalacOptions` (e.g. iterative SVD):
```scala
lazy val iterative_svd = (project in file("IterativeSVD"))
  .settings(
    commonSettings,
    name := "iterative_svd",
    // hardwareSettings,
    // scalacOptions += s"-P:bitsad-plugin:top:IterativeSVD.scala"
  )
```

Refer to `build.sbt` for all the kernel SBT project names.