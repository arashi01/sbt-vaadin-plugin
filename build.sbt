name := "sbt-vaadin-plugin"

description := "Vaadin Plugin for sbt"

version := "1.3-SNAPSHOT"

organization := "org.vaadin.sbt"

enablePlugins(SbtPlugin)

enablePlugins(SbtScalariform)

// sbt -Dsbt-vaadin-plugin.repository.path=../henrikerola.github.io/repository/releases publish
publishTo := Some(Resolver.file("GitHub", file(Option(System.getProperty("sbt-vaadin-plugin.repository.path")).getOrElse("../henrikerola.github.io/repository/snapshots"))))

//enablePlugins(VaadinPlugin)  //TODO re-enable when there is a published sbt 1.2.x compatible version available

//packageOptions in (Compile, packageBin) <+= org.vaadin.sbt.tasks.AddOnJarManifestTask.addOnJarManifestTask

scriptedBufferLog := false

scriptedLaunchOpts := { scriptedLaunchOpts.value ++
  Seq("-Dplugin.version=" + version.value, "-Dvaadin.version=7.5.7")
}
