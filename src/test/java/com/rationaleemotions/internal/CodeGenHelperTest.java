package com.rationaleemotions.internal;

import com.rationaleemotions.Configuration;
import com.rationaleemotions.FakeDriver;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.List;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CodeGenHelperTest {

  @Test(dataProvider = "dp")
  public void testConstructPackageName(String pathname, String expected) {
    String pkg =
        CodeGenHelper.constructPackageName("src/test/resources", "myBase", "com.foo.bar", pathname);
    Assert.assertEquals(pkg, expected);
  }

  @DataProvider(name = "dp")
  public Object[][] getTestData() {
    return new Object[][] {
      {"panda/bar.json", "com.foo.bar.panda"},
      {"bar.json", "com.foo.bar"},
    };
  }

  @Test
  public void testGenerate() throws IOException {
    String target = System.getProperty("user.dir") + File.separator + "target";
    Configuration cfg =
        new Configuration()
            .usingBaseFolder("json")
            .usingResourcesDirectory("src/test/resources")
            .usingGenerateSourcesDirectory(target)
            .usingBasePackage("com.foo");
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    CodeGenHelper helper = new CodeGenHelper(cfg);
    List<Path> paths = helper.generate();
    paths.forEach(
        path -> {
          int result = compiler.run(null, null, null, path.toFile().getAbsolutePath());
          Assert.assertEquals(result, 0);
        });
    String elementClassName = getClassName(target);
    Assert.assertEquals(elementClassName, "com.rationaleemotions.page.Label");
  }

  private static String getClassName(String target) {
    File root = new File(target);
    try {
      URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] {root.toURI().toURL()});
      Class<?> cls = Class.forName("com.foo.blah.AnotherPage", true, classLoader);
      Constructor<?> constructor = cls.getConstructor(WebDriver.class);
      Object instance = constructor.newInstance(new FakeDriver());
      Object heading = cls.getMethod("getHeading").invoke(instance);
      return heading.getClass().getName();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}
