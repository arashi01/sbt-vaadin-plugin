package org.vaadin.sbt.tasks

import sbt._
import sbt.Keys._
import org.vaadin.sbt.util.ForkUtil._
import org.vaadin.sbt.util.ProjectUtil._
import org.vaadin.sbt.VaadinPlugin.autoImport.{ vaadinOptions, vaadinSuperDevMode, vaadinWidgetsets }

/**
 * @author Henri Kerola / Vaadin
 */
object SuperDevModeTask {

  val superDevModeTask: Def.Initialize[Task[Unit]] = Def.task {
    val classDir = (classDirectory in Compile).value
    val fullCp = (dependencyClasspath in Compile).value
    val resources = (resourceDirectories in Compile).value
    val widgetsets = (vaadinWidgetsets in vaadinSuperDevMode).value
    val args = (vaadinOptions in vaadinSuperDevMode).value
    val jvmArgs = (javaOptions in vaadinSuperDevMode).value
    val state = Keys.state.value

    implicit val log = state.log

    val result = forkWidgetsetCmd(
      jvmArgs,
      getClassPath(state, Seq(classDir) ++ fullCp.files),
      "com.google.gwt.dev.codeserver.CodeServer",
      args,
      widgetsets,
      resources)

    for (error <- result.left) {
      sys.error(error)
    }
  }
}
