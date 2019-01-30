libraryDependencies ++= Seq(
  "com.vaadin" % "vaadin-client-compiler" % System.getProperty("vaadin.version") % "provided"
)

enablePlugins(VaadinWebPlugin)

javaOptions in compileVaadinWidgetsets := Seq("-Xss8M", "-Xmx512M", "-XX:MaxPermSize=512M")

// Testing here that this works:
target in compileVaadinWidgetsets := (sourceDirectory in Compile).value / "custom-dir"