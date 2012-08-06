import sbt._
import Keys._
import sbtrelease.ReleasePlugin._


object HelloBuild extends Build {


  lazy val root = Project(id = "hello",
    base = file("."),
    settings = Project.defaultSettings ++ releaseSettings ++ Seq(
      organization := "com.geishatokyo",
      name := "root-project",
      //publishTo :=  Some("local" at "file://Users/takezoux3/program/scala/temp")
      publishTo := Some(Resolver.file("localMaven",Path.userHome / ".m2" / "repository"))
  
    ) 
  ) aggregate(subProject)

  lazy val subProject = Project(id = "subproject",
    base = file("subp"),
    settings = Project.defaultSettings ++ releaseSettings ++  Seq(
      organization := "com.geishatokyo",
      name := "sub-project",
      //publishTo := Some("local" at "file://Users/takezoux3/program/scala/temp")
      publishTo := Some(Resolver.file("localMaven",Path.userHome / ".m2" / "repository"))
    )
  )

}
