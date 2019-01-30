enablePlugins(VaadinAddonPlugin)

val checkJar = taskKey[Unit]("checkJar")

checkJar := {
    val jar = new java.util.jar.JarFile(crossTarget.value / s"no-widgetsets_${scalaBinaryVersion.value}-0.1.0-SNAPSHOT.jar")
    val attributes = jar.getManifest.getMainAttributes
    if (attributes.getValue("Vaadin-Package-Version") != "1") sys.error("Wrong value for 'Vaadin-Package-Version'")
    if (attributes.getValue("Implementation-Title") != "no-widgetsets") sys.error("Wrong value for 'Implementation-Title'")
    if (attributes.getValue("Specification-Version") != "0.1.0-SNAPSHOT") sys.error("Wrong value for 'Specification-Version'")
    if (attributes.getValue("Vaadin-Widgetsets") != null) sys.error("'Vaadin-Widgetsets' should not be defined")
    if (jar.getJarEntry("Test.scala") == null) sys.error("Jar doesn't contain 'Test.scala' source file")
}