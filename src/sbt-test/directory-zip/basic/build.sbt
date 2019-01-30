enablePlugins(VaadinAddonPlugin)

val checkZip = taskKey[Unit]("checkZip")

checkZip := {
  val zip = new java.util.jar.JarFile(target.value / s"basic_${scalaBinaryVersion.value}-0.1.0-SNAPSHOT.zip")
  val attributes = zip.getManifest.getMainAttributes
  def checkAttribute(name: String, value: String): Unit = {
    if (attributes.getValue(name) != value) sys.error(s"Wrong value for '${name}'")
  }
  def fileExists(name: String): Unit = {
    if (zip.getEntry(name) == null) sys.error(s"File '${name}' doesn't exist in the generated zip")
  }
  checkAttribute("Manifest-Version", "1.0")
  checkAttribute("Implementation-Title", "basic")
  checkAttribute("Implementation-Version", "0.1.0-SNAPSHOT")
  checkAttribute("Vaadin-Package-Version", "1")
  checkAttribute("Vaadin-Addon", s"basic_${scalaBinaryVersion.value}-0.1.0-SNAPSHOT.jar")
  fileExists(s"basic_${scalaBinaryVersion.value}-0.1.0-SNAPSHOT.jar")
  fileExists(s"basic_${scalaBinaryVersion.value}-0.1.0-SNAPSHOT-sources.jar")
  fileExists(s"basic_${scalaBinaryVersion.value}-0.1.0-SNAPSHOT-javadoc.jar")
}