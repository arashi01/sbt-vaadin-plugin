package org.vaadin.sbt.tasks

import sbt._
import sbt.Keys._
import java.util.jar.Manifest
import org.vaadin.sbt.util.WidgetsetUtil
import org.vaadin.sbt.VaadinPlugin.autoImport.{ compileVaadinWidgetsets, vaadinWidgetsets }

/**
 * @author Henri Kerola / Vaadin
 */
object AddOnJarManifestTask {

  val addOnJarManifestTask: Def.Initialize[Task[PackageOption]] = Def.task {
    val manifest = new Manifest
    val mainAttributes = manifest.getMainAttributes
    val widgetsetsValue = WidgetsetUtil.findWidgetsets((vaadinWidgetsets in compileVaadinWidgetsets).value, (resourceDirectories in (Compile, vaadinWidgetsets)).value).mkString(",")

    mainAttributes.putValue("Vaadin-Package-Version", "1")
    mainAttributes.putValue("Implementation-Title", (name in Compile).value)
    mainAttributes.putValue("Implementation-Version", (version in Compile).value)
    if (widgetsetsValue != "") {
      mainAttributes.putValue("Vaadin-Widgetsets", widgetsetsValue)
    }

    Package.JarManifest(manifest)
  }
}
