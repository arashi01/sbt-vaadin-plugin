libraryDependencies ++= Seq(
  "com.vaadin" % "vaadin-themes" % System.getProperty("vaadin.version") % "provided",
  "com.vaadin" % "vaadin-sass-compiler" % "0.9.7" % "provided"
)

enablePlugins(VaadinWebPlugin)

// Testing here that this works:
target in compileVaadinThemes := (sourceDirectory in Compile).value / "custom-dir"