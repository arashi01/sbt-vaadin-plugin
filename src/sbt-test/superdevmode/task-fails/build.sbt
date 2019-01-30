enablePlugins(VaadinPlugin)

libraryDependencies ++= Seq(
  "com.vaadin" % "vaadin-client-compiler" % System.getProperty("vaadin.version") % "provided"
)

InputKey[Unit]("contains") := {
  val args = Def.spaceDelimited().parsed
  args match {
    case Seq(contentFile, content) =>
      if (!IO.read(file(contentFile)).contains(content)) {
        sys.error("File %s doesn't contain String %s" format (contentFile, content))
      }
  }
}