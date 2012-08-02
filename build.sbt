
organization := "com.geishatokyo"

varsion := "0.1-SNAPSHOT"

name := "sbt-release-plugin-test"

releaseSettings

executableName := Some( s => s match{
  case "git" -> "git.cmd"
  case _ => s
})
