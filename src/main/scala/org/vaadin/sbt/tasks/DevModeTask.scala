package org.vaadin.sbt.tasks

import sbt._
import sbt.Keys._
import org.vaadin.sbt.util.ForkUtil._
import org.vaadin.sbt.util.ProjectUtil._
import org.vaadin.sbt.VaadinPlugin.autoImport.{ vaadinDevMode, vaadinOptions, vaadinWidgetsets }

/**
 * @author Henri Kerola / Vaadin
 */
object DevModeTask {

  private def addIfNotInArgs(args: Seq[String], param: String, value: String) =
    if (!args.contains(param)) Seq(param, value) else Nil

  val devModeTask: Def.Initialize[Task[Unit]] = Def.task {
    val classDir = (classDirectory in Compile).value
    val fullCp = (dependencyClasspath in Compile).value
    val resources = (resourceDirectories in Compile).value
    val widgetsets = (vaadinWidgetsets in vaadinDevMode).value
    val args = (vaadinOptions in vaadinDevMode).value
    val jvmArgs = (javaOptions in vaadinDevMode).value
    val target = (Keys.target in vaadinDevMode).value
    val state = Keys.state.value

    implicit val log = streams.value.log

    val cmdArgs = Seq("-noserver") ++
      addIfNotInArgs(args, "-war", target absolutePath) ++
      addIfNotInArgs(args, "-startupUrl", "http://localhost:8080") ++ args

    val result = forkWidgetsetCmd(
      jvmArgs,
      getClassPath(state, Seq(classDir) ++ fullCp.files),
      "com.google.gwt.dev.DevMode",
      cmdArgs,
      widgetsets,
      resources)

    for (error <- result.left) {
      sys.error(error)
    }
  }
}
