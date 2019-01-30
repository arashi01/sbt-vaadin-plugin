enablePlugins(VaadinAddonPlugin)

vaadinWidgetsets := Seq("com.example.MyWidgetset")

val checkManifestWidgetsetsValue = taskKey[Unit]("checkManifestWidgetsetsValue")

checkManifestWidgetsetsValue := {
    val jar = new java.util.jar.JarFile(crossTarget.value / s"widgetsets-defined_${scalaBinaryVersion.value}-0.1.0-SNAPSHOT.jar")
    val attributes = jar.getManifest.getMainAttributes
    if (attributes.getValue("Vaadin-Widgetsets") != "com.example.MyWidgetset") sys.error("Wrong value for 'Vaadin-Widgetsets'")
}