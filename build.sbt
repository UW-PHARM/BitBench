autoCompilerPlugins := true
cancelable in Global := true

ThisBuild / organization := "com.github.uw-pharm"
ThisBuild / scalaVersion := "2.12.6"

// lazy val HardwareGeneration = config("HardwareGeneration") extend(Compile)

// lazy val topLevelFile = settingKey[String]("Top-level Scala source file to generate into hardware")
// lazy val mkHardware = taskKey[Unit]("Generates Verilog for specified project")

lazy val commonSettings = Seq(
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  libraryDependencies += "com.github.uw-pharm" % "bitsad-libraries_2.12" % "0.5.0",
)

lazy val root = (project in file("."))
  .settings(
    commonSettings,
    version := "0.5",
    name := "bitbench"
  )
  .aggregate(iterative_svd, linear_solver, svf_filter, bq_filter, ma4_filter, ma32_filter)

lazy val iterative_svd = (project in file("IterativeSVD"))
  // .configs(HardwareGeneration)
  .settings(
    commonSettings,
    name := "iterative_svd",
    libraryDependencies += "com.github.uw-pharm" % "bitsad-plugin_2.12" % "0.5.0",
    libraryDependencies += compilerPlugin("com.github.uw-pharm" % "bitsad-plugin_2.12" % "0.5.0"),
    scalacOptions += s"-Xplugin:bitsad-plugin_2.12.jar:bitsad-libraries_2.12.jar",
    scalacOptions += s"-P:bitsad-plugin:top:IterativeSVD.scala"
  )
  // .settings(
  //   topLevelFile := "IterativeSVD.scala",
  //   inConfig(HardwareGeneration)(Defaults.projectTasks ++ Seq(
  //     HardwareGeneration / scalacOptions += s"-Xplugin:bitsad-plugin_2.12.jar:bitsad-libraries_2.12.jar",
  //     HardwareGeneration / scalacOptions += "-Xplugin-list")),
  //   mkHardware in HardwareGeneration := (compile in HardwareGeneration).value,
  //   // HardwareGeneration / scalacOptions += s"-P:bitsad-plugin:top:${topLevelFile.value}"
  // )

lazy val linear_solver = (project in file("LinearSolver"))
  .settings(
    commonSettings,
    name := "linear_solver",
    libraryDependencies += "com.github.uw-pharm" % "bitsad-plugin_2.12" % "0.5.0",
    libraryDependencies += compilerPlugin("com.github.uw-pharm" % "bitsad-plugin_2.12" % "0.5.0"),
    scalacOptions += s"-Xplugin:bitsad-plugin_2.12.jar:bitsad-libraries_2.12.jar",
    scalacOptions += s"-P:bitsad-plugin:top:LinearSolver.scala"
  )

lazy val svf_filter = (project in file("SVFFilter"))
  .settings(
    commonSettings,
    name := "svf_filter",
    libraryDependencies += "com.github.uw-pharm" % "bitsad-plugin_2.12" % "0.5.0",
    libraryDependencies += compilerPlugin("com.github.uw-pharm" % "bitsad-plugin_2.12" % "0.5.0"),
    scalacOptions += s"-Xplugin:bitsad-plugin_2.12.jar:bitsad-libraries_2.12.jar",
    scalacOptions += s"-P:bitsad-plugin:top:PDMBandpassFilterSVF.scala"
  )

lazy val bq_filter = (project in file("BQFilter"))
  .settings(
    commonSettings,
    name := "bq_filter",
    libraryDependencies += "com.github.uw-pharm" % "bitsad-plugin_2.12" % "0.5.0",
    libraryDependencies += compilerPlugin("com.github.uw-pharm" % "bitsad-plugin_2.12" % "0.5.0"),
    scalacOptions += s"-Xplugin:bitsad-plugin_2.12.jar:bitsad-libraries_2.12.jar",
    scalacOptions += s"-P:bitsad-plugin:top:PDMBandpassFilterBQ.scala"
  )

lazy val ma4_filter = (project in file("MA4Filter"))
  .settings(
    commonSettings,
    name := "ma4_filter",
    libraryDependencies += "com.github.uw-pharm" % "bitsad-plugin_2.12" % "0.5.0",
    libraryDependencies += compilerPlugin("com.github.uw-pharm" % "bitsad-plugin_2.12" % "0.5.0"),
    scalacOptions += s"-Xplugin:bitsad-plugin_2.12.jar:bitsad-libraries_2.12.jar",
    scalacOptions += s"-P:bitsad-plugin:top:PDMMovingAverageFilter4.scala"
  )

lazy val ma32_filter = (project in file("MA32Filter"))
  .settings(
    commonSettings,
    name := "ma32_filter",
    libraryDependencies += "com.github.uw-pharm" % "bitsad-plugin_2.12" % "0.5.0",
    libraryDependencies += compilerPlugin("com.github.uw-pharm" % "bitsad-plugin_2.12" % "0.5.0"),
    scalacOptions += s"-Xplugin:bitsad-plugin_2.12.jar:bitsad-libraries_2.12.jar",
    scalacOptions += s"-P:bitsad-plugin:top:PDMMovingAverageFilter32.scala"
  )