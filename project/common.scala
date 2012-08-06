import sbt._
import Keys._
import sbtrelease._
import sbtrelease.ReleasePlugin._
import sbtrelease.ReleasePlugin.ReleaseKeys._
import sbtrelease.ReleaseStateTransformations._



trait CommonBuildSetting{
  self : Build =>
  
  lazy val chabiReleaseSettings = releaseSettings ++ Seq(
    releaseProcess := {
      Seq(
        checkSnapshotDependencies,              // : ReleaseStep
        inquireVersions,                        // : ReleaseStep
        runTest,                                // : ReleaseStep
        setReleaseVersion,                      // : ReleaseStep
        commitReleaseVersion,                   // : ReleaseStep, performs the initial git checks
        tagRelease,                             // : ReleaseStep
        //publishArtifacts,                     // skip  // : ReleaseStep, checks whether `publishTo` is properly set up
        setNextVersion,                         // : ReleaseStep
        commitNextVersion,                      // : ReleaseStep
        pushChanges)
    },
    tagName <<= (name,version)( (n,v) => n + "_" + v),
    nextVersion := { version => Version(version).map( _.bumpBugfix.asSnapshot.string).getOrElse(versionFormatError)}
  )
  
  
      
  lazy val switchPublishTo = (version){ version => {
    if(version.endsWith("SNAPSHOT")){
      Some("geishatokyo-nexus-snapshot" at "http://nexus.geishatokyo.com/nexus/content/repositories/geishatokyo-unstable-snapshots")
    }else{
      Some("geishatokyo-nexus-release" at  "http://nexus.geishatokyo.com/nexus/content/repositories/geishatokyo-unstable-releases")
    }
  }}
}