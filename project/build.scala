import sbt._
import Keys._
import sbtrelease.ReleasePlugin._
import sbtrelease.ReleasePlugin.ReleaseKeys._
import sbtrelease.ReleaseStateTransformations._


object HelloBuild extends Build with CommonBuildSetting {
  
   val mainScalaVersion = "2.9.1"

  lazy val root = Project(id = "hello",
    base = file("."),
    settings = Project.defaultSettings ++ chabiReleaseSettings ++ Seq(
      organization := "com.geishatokyo",
      crossScalaVersions := List("2.9.1","2.9.0"),
      name := "root-project",
      executableName := Some(s => "git.cmd"),
      //publishTo :=  Some("local" at "file://Users/takezoux3/program/scala/temp")
      publishTo := Some(Resolver.file("localMaven",Path.userHome / ".m2" / "repository"))
    ) 
  ) aggregate(subProject)

  lazy val subProject = Project(id = "subproject",
    base = file("subp"),
    settings = Project.defaultSettings ++ chabiReleaseSettings ++  Seq(
      organization := "com.geishatokyo",
      crossScalaVersions := List("2.9.1","2.9.0"),
      name := "sub-project",
      executableName := Some(s => "git.cmd"),
      //publishTo := Some("local" at "file://Users/takezoux3/program/scala/temp")
      publishTo := Some(Resolver.file("localMaven",Path.userHome / ".m2" / "repository"))
    )
  )

}
