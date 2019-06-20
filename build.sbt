autoCompilerPlugins := true
cancelable in Global := true

ThisBuild / organization := "com.github.uw-pharm"
ThisBuild / scalaVersion := "2.12.6"

addCompilerPlugin("com.github.uw-pharm" %% "bitsad-plugin" % "f45b86d0")

lazy val HardwareGeneration = config("HardwareGeneration") extend(Compile)

lazy val topLevelFile = settingKey[String]("Top-level Scala source file to generate into hardware")
lazy val mkHardware = taskKey[Unit]("Generates Verilog for specified project")

lazy val commonSettings = Seq(
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  libraryDependencies += "com.github.uw-pharm" %% "bitsad-libraries" % "f45b86d0",
  libraryDependencies += "com.github.uw-pharm" %% "bitsad-plugin" % "f45b86d0"
)

lazy val root = (project in file("."))
  .settings(
    commonSettings,
    version := "0.5",
    name := "bitbench"
  )
  .aggregate(iterative_svd, linear_solver, svf_filter, bq_filter, ma4_filter, ma32_filter)

lazy val iterative_svd = (project in file("IterativeSVD"))
  .configs(HardwareGeneration)
  .settings(
    commonSettings,
    name := "iterative_svd",
  )
  .settings(
    topLevelFile := "IterativeSVD.scala",
    inConfig(HardwareGeneration)(Defaults.projectTasks ++ Seq(
      HardwareGeneration / scalacOptions += s"-Xplugin:bitsad-plugin_2.12.jar:bitsad-libraries_2.12.jar",
      HardwareGeneration / scalacOptions += "-Xplugin-list")),
    mkHardware := (compile in HardwareGeneration).value,
    // HardwareGeneration / scalacOptions += s"-P:bitsad-plugin:top:${topLevelFile.value}"
  )

lazy val linear_solver = (project in file("LinearSolver"))
  // .configs(HardwareGeneration)
  .settings(
    commonSettings,
    name := "linear_solver",
    topLevelFile := "LinearSolver.scala",
    // mkHardware := (compile in HardwareGeneration).value,
    inConfig(HardwareGeneration)(Defaults.projectTasks),
    HardwareGeneration / scalacOptions += s"-Xplugin:bitsad-plugin_2.12.jar:bitsad-libraries_2.12.jar",
    // HardwareGeneration / scalacOptions += s"-P:bitsad-plugin:top:${topLevelFile.value}"
  )

lazy val svf_filter = (project in file("SVFFilter"))
  // .configs(HardwareGeneration)
  .settings(
    commonSettings,
    name := "svf_filter",
    topLevelFile := "PDMBandpassFilterSVF.scala",
    // mkHardware := (compile in HardwareGeneration).value,
    inConfig(HardwareGeneration)(Defaults.projectTasks),
    HardwareGeneration / scalacOptions += s"-Xplugin:bitsad-plugin_2.12.jar:bitsad-libraries_2.12.jar",
    // HardwareGeneration / scalacOptions += s"-P:bitsad-plugin:top:${topLevelFile.value}"
  )

lazy val bq_filter = (project in file("BQFilter"))
  // .configs(HardwareGeneration)
  .settings(
    commonSettings,
    name := "bq_filter",
    topLevelFile := "PDMBandpassFilterBQ.scala",
    // mkHardware := (compile in HardwareGeneration).value,
    inConfig(HardwareGeneration)(Defaults.projectTasks),
    HardwareGeneration / scalacOptions += s"-Xplugin:bitsad-plugin_2.12.jar:bitsad-libraries_2.12.jar",
    // HardwareGeneration / scalacOptions += s"-P:bitsad-plugin:top:${topLevelFile.value}"
  )

lazy val ma4_filter = (project in file("MA4Filter"))
  // .configs(HardwareGeneration)
  .settings(
    commonSettings,
    name := "ma4_filter",
    topLevelFile := "PDMMovingAverageFilter4.scala",
    // mkHardware := (compile in HardwareGeneration).value,
    inConfig(HardwareGeneration)(Defaults.projectTasks),
    HardwareGeneration / scalacOptions += s"-Xplugin:bitsad-plugin_2.12.jar:bitsad-libraries_2.12.jar",
    // HardwareGeneration / scalacOptions += s"-P:bitsad-plugin:top:${topLevelFile.value}"
  )

lazy val ma32_filter = (project in file("MA32Filter"))
  // .configs(HardwareGeneration)
  .settings(
    commonSettings,
    name := "ma32_filter",
    topLevelFile := "PDMMovingAverageFilter32.scala",
    // mkHardware := (compile in HardwareGeneration).value,
    inConfig(HardwareGeneration)(Defaults.projectTasks),
    HardwareGeneration / scalacOptions += s"-Xplugin:bitsad-plugin_2.12.jar:bitsad-libraries_2.12.jar",
    // HardwareGeneration / scalacOptions += s"-P:bitsad-plugin:top:${topLevelFile.value}"
  )