import sbt._
import Keys._
import sbtrelease.ReleasePlugin._
import sbtrelease.ReleasePlugin.ReleaseKeys._
import sbtrelease.ReleaseStateTransformations._


object HelloBuild extends Build {
  
   val mainScalaVersion = "2.9.1"

  lazy val root = Project(id = "hello",
    base = file("."),
    settings = Project.defaultSettings ++ releaseSettings ++ Seq(
      organization := "com.geishatokyo",
      crossScalaVersions := List("2.9.1","2.9.0"),
      name := "root-project",
      executableName := Some(s => "git.cmd"),
      //publishTo :=  Some("local" at "file://Users/takezoux3/program/scala/temp")
      publishTo := Some(Resolver.file("localMaven",Path.userHome / ".m2" / "repository")),
      releaseProcess <<= (scalaVersion)( v => {
        println("scala version:" + v)
        if(v == mainScalaVersion){
          Seq(
  checkSnapshotDependencies,              // : ReleaseStep
  inquireVersions,                        // : ReleaseStep
  runTest,                                // : ReleaseStep
  setReleaseVersion,                      // : ReleaseStep
  commitReleaseVersion,                   // : ReleaseStep, performs the initial git checks
  tagRelease,                             // : ReleaseStep
  publishArtifacts,                       // : ReleaseStep, checks whether `publishTo` is properly set up
  setNextVersion,                         // : ReleaseStep
  commitNextVersion,                      // : ReleaseStep
  pushChanges               
  )              
        }else{
          Seq(
  checkSnapshotDependencies,              // : ReleaseStep
  runTest,                                // : ReleaseStep
  setReleaseVersion,                      // : ReleaseStep
  publishArtifacts                       // : ReleaseStep, checks whether `publishTo` is properly set up
  )
        }
      })
  
    ) 
  ) aggregate(subProject)

  lazy val subProject = Project(id = "subproject",
    base = file("subp"),
    settings = Project.defaultSettings ++ releaseSettings ++  Seq(
      organization := "com.geishatokyo",
      crossScalaVersions := List("2.9.1","2.9.0"),
      name := "sub-project",
      executableName := Some(s => "git.cmd"),
      //publishTo := Some("local" at "file://Users/takezoux3/program/scala/temp")
      publishTo := Some(Resolver.file("localMaven",Path.userHome / ".m2" / "repository")),
      releaseProcess <<= (scalaVersion)( v => {
        if(v == mainScalaVersion){
          Seq(
  checkSnapshotDependencies,              // : ReleaseStep
  inquireVersions,                        // : ReleaseStep
  runTest,                                // : ReleaseStep
  setReleaseVersion,                      // : ReleaseStep
  commitReleaseVersion,                   // : ReleaseStep, performs the initial git checks
  tagRelease,                             // : ReleaseStep
  publishArtifacts,                       // : ReleaseStep, checks whether `publishTo` is properly set up
  setNextVersion,                         // : ReleaseStep
  commitNextVersion,                      // : ReleaseStep
  pushChanges               
  )              
        }else{
          Seq(
  checkSnapshotDependencies,              // : ReleaseStep
  runTest,                                // : ReleaseStep
  setReleaseVersion,                      // : ReleaseStep
  publishArtifacts                       // : ReleaseStep, checks whether `publishTo` is properly set up
  )
        }
      })
  
    )
  )

}
