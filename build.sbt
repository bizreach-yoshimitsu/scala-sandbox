lazy val commonSetting = Seq(
  version := "1.0.0",
  scalaVersion := "2.11.11"
)

lazy val macroAnnotationSettings = Seq(
  addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M9" cross CrossVersion.full),
  scalacOptions += "-Xplugin-require:macroparadise",
  scalacOptions in (Compile, console) := Seq() // macroparadise plugin doesn't work in repl yet.
)

lazy val test = (project in file("./"))
  .settings(commonSetting)
  .settings(
    name := "sandbox",
    libraryDependencies ++= Seq(
      "com.typesafe.play"    %% "play-json"                 % "2.6.0-RC2",
      "org.scalaz"           %% "scalaz-core"               % "7.2.13",

      "org.scalameta"        %% "scalameta"                 % "1.8.0"       % Provided,

      "org.scalatest"        %% "scalatest"                 % "3.0.1"       % Test,
      "org.mockito"           % "mockito-core"              % "2.8.9"       % Test
    )
  )
  .settings(macroAnnotationSettings)
