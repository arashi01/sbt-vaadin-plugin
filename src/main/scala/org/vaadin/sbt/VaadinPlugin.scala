package org.vaadin.sbt

import sbt.{ Def, _ }
import sbt.Keys._
import org.vaadin.sbt.tasks._
import sbt.plugins.JvmPlugin

object VaadinWebPlugin extends AutoPlugin {

  override val trigger: PluginTrigger = noTrigger

  override val requires: Plugins = VaadinPlugin

  override lazy val projectSettings: Seq[Setting[_]] = vaadinWebSettings

  def vaadinWebSettings: Seq[Setting[_]] = {
    import VaadinPlugin.autoImport._
    Seq(
      resourceGenerators in Compile += CompileWidgetsetsTask.compileWidgetsetsInResourceGeneratorsTask.taskValue,
      resourceGenerators in Compile += compileVaadinThemes.taskValue)
  }
}

object VaadinAddonPlugin extends AutoPlugin {

  override val trigger: PluginTrigger = noTrigger

  override val requires: Plugins = VaadinPlugin

  override lazy val projectSettings: Seq[Setting[_]] = vaadinAddOnSettings

  def vaadinAddOnSettings: Seq[Setting[_]] = Seq(
    packageOptions in (Compile, packageBin) += AddOnJarManifestTask.addOnJarManifestTask.value,

    // Include source files into the binary jar file. Widgetset compiler needs those.
    mappings in (Compile, packageBin) := {
      import Path._
      val srcs = (unmanagedSources in Compile).value
      val sdirs = (unmanagedSourceDirectories in Compile).value
      val base = (baseDirectory in Compile).value
      (mappings in (Compile, packageBin)).value ++ ((srcs --- sdirs --- base) pair (relativeTo(sdirs) | relativeTo(base) | flat))
    })
}

object VaadinPlugin extends AutoPlugin {

  override val trigger: PluginTrigger = noTrigger

  override val requires: Plugins = JvmPlugin

  override lazy val projectSettings: Seq[Def.Setting[_]] = vaadinSettings

  object autoImport extends VaadinKeys

  import autoImport._

  def vaadinSettings: Seq[Setting[_]] = Seq(

    compileVaadinWidgetsets := CompileWidgetsetsTask.compileWidgetsetsTask.value,
    vaadinWidgetsets := Nil,
    // TODO: refactor to use a value from 'webappDest in webapp')
    target in compileVaadinWidgetsets := (target in Compile).value / "webapp" / "VAADIN" / "widgetsets",
    vaadinOptions in compileVaadinWidgetsets := Nil,
    javaOptions in compileVaadinWidgetsets := Nil,

    vaadinDevMode := DevModeTask.devModeTask.value,
    vaadinWidgetsets in vaadinDevMode := (vaadinWidgetsets in compileVaadinWidgetsets).value,
    target in vaadinDevMode := (target in compileVaadinWidgetsets).value,
    vaadinOptions in vaadinDevMode := Nil,
    javaOptions in vaadinDevMode := (javaOptions in compileVaadinWidgetsets).value,

    vaadinSuperDevMode := SuperDevModeTask.superDevModeTask.value,
    vaadinWidgetsets in vaadinSuperDevMode := (vaadinWidgetsets in compileVaadinWidgetsets).value,
    vaadinOptions in vaadinSuperDevMode := Nil,
    javaOptions in vaadinSuperDevMode := (javaOptions in compileVaadinWidgetsets).value,

    compileVaadinThemes := CompileThemesTask.compileThemesTask.value,
    vaadinThemes := Nil,
    vaadinThemesDir := sourceDirectory(sd => Seq(sd / "main" / "webapp" / "VAADIN" / "themes")).value,
    // TODO: refactor to use a value from 'webappDest in webapp')
    target in compileVaadinThemes := (target in Compile).value / "webapp" / "VAADIN" / "themes",

    packageVaadinDirectoryZip := PackageDirectoryZipTask.packageDirectoryZipTask.value,
    // Include binary jar into the zip file.
    vaadinAddonMappings in packageVaadinDirectoryZip := {
      val bin = (packageBin in Compile).value
      Seq((bin, bin.name))
    },
    // Include sources and javadoc jars into the zip file.
    mappings in packageVaadinDirectoryZip := {
      val src = (packageSrc in Compile).value
      val doc = (packageDoc in Compile).value
      Seq((src, src.name), (doc, doc.name))
    })

}