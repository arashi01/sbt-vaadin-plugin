libraryDependencies ++= Seq(
  "com.vaadin" % "vaadin-client-compiler" % System.getProperty("vaadin.version") % "provided"
)

enablePlugins(VaadinPlugin)

javaOptions in compileVaadinWidgetsets := Seq("-Xss8M", "-Xmx512M", "-XX:MaxPermSize=512M")