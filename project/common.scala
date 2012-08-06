import sbt._
import Keys._
import sbtrelease.ReleasePlugin._
import sbtrelease.ReleasePlugin.ReleaseKeys._
import sbtrelease.ReleaseStateTransformations._



trait CommonBuildSetting{
  self : Build =>
  
  lazy val chabiReleaseSettings = releaseSettings ++ Seq(
    releaseProcess <<= swithReleaseProcess,
    tagName <<= (name,version)( (n,v) => n + "_" + v)
  )
  
  val mainScalaVersion : String
  
   // to support cross compile
  lazy val swithReleaseProcess = (crossScalaVersions,scalaVersion)( (versions,v) => {
    if(versions.size == 1){
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
        pushChanges)
    }else {
      val index = versions.indexOf(v)
      if(index == 0){
        Seq(
         checkSnapshotDependencies,              // : ReleaseStep
         inquireVersions,                        // : ReleaseStep
         runTest,                                // : ReleaseStep
         setReleaseVersion,                      // : ReleaseStep
         commitReleaseVersion,                   // : ReleaseStep, performs the initial git checks
         tagRelease,                             // : ReleaseStep
         publishArtifacts                       // : ReleaseStep, checks whether `publishTo` is properly set up
        )
      }else if(index == versions.size - 1){
        Seq(
          checkSnapshotDependencies,              // : ReleaseStep
          runTest,                                // : ReleaseStep
          setReleaseVersion,                      // : ReleaseStep
          publishArtifacts                       // : ReleaseStep, checks whether `publishTo` is properly set up
        )
      }else{
        Seq(
          checkSnapshotDependencies,              // : ReleaseStep
          runTest,                                // : ReleaseStep
          setReleaseVersion,                      // : ReleaseStep
          publishArtifacts,                       // : ReleaseStep, checks whether `publishTo` is properly set up,
          setNextVersion,                         // : ReleaseStep
          commitNextVersion,                      // : ReleaseStep
          pushChanges)
      }
    }
  })
      
  lazy val switchPublishTo = (version){ version => {
    if(version.endsWith("SNAPSHOT")){
      Some("geishatokyo-nexus-snapshot" at "http://nexus.geishatokyo.com/nexus/content/repositories/geishatokyo-unstable-snapshots")
    }else{
      Some("geishatokyo-nexus-release" at  "http://nexus.geishatokyo.com/nexus/content/repositories/geishatokyo-unstable-releases")
    }
  }}
}