package com.rationaleemotions.internal;

import com.rationaleemotions.Configuration;
import com.rationaleemotions.internal.generator.Factory;
import com.rationaleemotions.internal.parser.PageParser;
import com.rationaleemotions.internal.parser.pojos.Element;
import com.rationaleemotions.internal.parser.pojos.PageElement;
import com.rationaleemotions.page.PageObject;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

/**
 * A utility that helps with code generation.
 */
public class CodeGenHelper {

  private final Configuration cfg;

  public CodeGenHelper(Configuration cfg) {
    this.cfg = cfg;
  }

  public List<Path> generate() throws IOException {
    List<Path> paths = new ArrayList<>();
    Map<String, PageElement> mapping = new HashMap<>();
    String path =
        cfg.getResourcesDirectory()
            + File.separator
            + cfg.getBaseFolder()
            + File.separator;
    if (cfg.getLogger() != null) {
      cfg.getLogger().info("Commencing listing json files in directory [" + path + "]");
    }
    Collection<?> files = FileUtils.listFiles(new File(path), new String[] {"json"}, true);
    files.forEach(
        eachFile -> {
          File f  = (File) eachFile;
          try {
            String p = StringUtils.substringAfter(f.getAbsolutePath(), path);
            mapping.put(p, PageParser.parsePage(f.getAbsolutePath()));
          } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
          }
        });
    for (Entry<String, PageElement> entry : mapping.entrySet()) {
      String key = entry.getKey();
      PageElement value = entry.getValue();
      paths.add(generate(key, value));
    }
    return paths;
  }

  static String constructPackageName(String resourcesDir, String baseFolder, String basePackage, String jsonFile) {
    String file = new File(resourcesDir + File.separator + baseFolder + File.separator + jsonFile).getAbsolutePath();
    String remaining = StringUtils.substringAfter(file, baseFolder);
    StringBuilder packageName = new StringBuilder(basePackage);
    String[] parts = remaining.split(File.separator);
    if (parts.length == 0) {
      return packageName.toString().replaceAll(File.separator, ".");
    }
    int len = parts.length - 1;
    for (int i = 0; i < len; i++) {
      packageName.append(parts[i]).append(File.separator);
    }
    String result = packageName.toString().replaceAll(File.separator, ".");
    if (result.endsWith(".")) {
      result = StringUtils.substring(result, 0, result.length() - 1);
    }
    return result;
  }

  private Path generate(String jsonFile, PageElement element) throws IOException {
    if (cfg.getLogger() != null) {
      cfg.getLogger().info("Working with the jsonFile " + jsonFile);
    }
    String packageName = constructPackageName(cfg.getGenerateSourcesDirectory(), cfg.getBaseFolder(), cfg.getBasePackage(), jsonFile);
    if (cfg.getLogger() != null) {
      cfg.getLogger().info("Package Name : [" + packageName + "]");
    }

    FieldSpec page =
        FieldSpec.builder(PageObject.class, "page", Modifier.PRIVATE, Modifier.FINAL).build();
    TypeSpec clazz =
        TypeSpec.classBuilder(element.getName())
            .addModifiers(Modifier.PUBLIC)
            .addField(page)
            .addMethods(buildConstructors(jsonFile))
            .addMethods(buildGetters(element.getElements()))
            .build();
    String base =
        cfg.getGenerateSourcesDirectory()
            + File.separator
            + packageName.replaceAll("\\Q.\\E", File.separator);

      StringBuilder content = new StringBuilder();
      JavaFile.builder(packageName, clazz).build().writeTo(content);
      File parent = new File(jsonFile).getParentFile();
      String location =
          new File(base).getAbsolutePath()
              + File.separator
              + element.getName()
              + ".java";
      parent = new File(location).getParentFile();
      boolean created = parent.mkdirs();
      if (created && cfg.getLogger() != null) {
        cfg.getLogger().info("Creating Directory : [" + parent.getAbsolutePath() + "]");
      }
      Path path = Paths.get(location);
    if (cfg.getLogger() != null) {
      cfg.getLogger()
          .info("Writing to Java file : [" + path.toFile().getAbsolutePath() + "]");
      }
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
      Files.write(path, Collections.singletonList(content.toString()),
          StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
    return path;
  }

  private List<MethodSpec> buildGetters(List<Element> elements) {
    return elements.stream()
        .map(element -> Factory.getGenerator(element.getType()).generate(element))
        .collect(Collectors.toList());
  }

  private List<MethodSpec> buildConstructors(String jsonFile) {
    List<ParameterSpec> parameters =
        Collections.singletonList(ParameterSpec.builder(WebDriver.class, "driver").build());
    MethodSpec defaultConstructor =
        MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC)
            .addParameters(parameters)
            .addStatement("this(driver, $S)", "")
            .build();

    parameters =
        Arrays.asList(
            ParameterSpec.builder(WebDriver.class, "driver").build(),
            ParameterSpec.builder(String.class, "locale").build());

    String file =
        cfg.getResourcesDirectory()
            + File.separator
            + cfg.getBaseFolder()
            + File.separator
            + jsonFile;
    MethodSpec parameterisedConstructor =
        MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC)
            .addParameters(parameters)
            .addStatement(
                "$T pageObject = new $T(driver, $S)", PageObject.class, PageObject.class, file)
            .beginControlFlow("if (locale != null && !locale.trim().isEmpty()) ")
            .addStatement(
                "pageObject = new $T(driver, $S).forLocale(locale)", PageObject.class, file)
            .endControlFlow()
            .addStatement("this.page = pageObject")
            .build();

    return Arrays.asList(defaultConstructor, parameterisedConstructor);
  }
}
