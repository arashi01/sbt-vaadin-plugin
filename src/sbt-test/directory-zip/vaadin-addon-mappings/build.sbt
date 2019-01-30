enablePlugins(VaadinAddonPlugin)

vaadinAddonMappings in packageVaadinDirectoryZip := {
    val bin = (packageBin in Compile).value

    Seq((bin, "foobar.jar"), (bin, "mydir/2.jar"))
}

val checkZip = taskKey[Unit]("checkZip")

checkZip := {
  val zip = new java.util.jar.JarFile(target.value / "foobar.zip")
  val attributes = zip.getManifest.getMainAttributes
  def checkAttribute(name: String, value: String): Unit = {
    if (attributes.getValue(name) != value) sys.error(s"Wrong value for '${name}'")
  }
  def fileExists(name: String): Unit = {
    if (zip.getEntry(name) == null) sys.error(s"File '${name}' doesn't exist in the generated zip")
  }
  fileExists("foobar.jar")
  fileExists("mydir/2.jar")
}