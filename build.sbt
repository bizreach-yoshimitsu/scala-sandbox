lazy val commonSetting = Seq(
  version := "1.0.0",
  scalaVersion := "2.11.7"
)

lazy val test = (project in file("./"))
  .settings(commonSetting)
  .settings(
    name := "sandbox",
    libraryDependencies ++= Seq(
      "com.typesafe.play"    %% "play-json"                 % "2.4.2",
      "joda-time"             % "joda-time"                 % "2.7",
      "org.scalaz"           %% "scalaz-core"               % "7.1.5",
      "org.scalatestplus"    %% "play"                      % "1.4.0-M3"              % "test",
      "org.mockito"           % "mockito-core"              % "1.9.5"                 % "test"
    )
  )
