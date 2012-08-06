import sbt._
import Keys._
import sbtrelease.ReleasePlugin._


object HelloBuild extends Build {


  lazy val root = Project(id = "hello",
    base = file("."),
    settings = Project.defaultSettings ++ releaseSettings ++ Seq(
      organization := "com.geishatokyo",
      name := "root-project"  
    ) 
  ) aggregate(subProject)

  lazy val subProject = Project(id = "subproject",
    base = file("subp"),
    settings = Project.defaultSettings ++ releaseSettings ++  Seq(
      organization := "com.geishatokyo",
      name := "sub-project"
    )
  )

}
