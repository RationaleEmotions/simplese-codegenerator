package com.rationaleemotions;

import org.apache.maven.plugin.logging.Log;

public class Configuration {
  private String basePackage;
  private String baseFolder;
  private String generateSourcesDirectory;
  private String resourcesDirectory;
  private Log logger;

  public Configuration usingLogger(Log logger) {
    this.logger = logger;
    return this;
  }

  public Configuration usingBasePackage(String basePackage) {
    this.basePackage = basePackage;
    return this;
  }

  public Configuration usingBaseFolder(String baseFolder) {
    this.baseFolder = baseFolder;
    return this;
  }

  public Configuration usingGenerateSourcesDirectory(String generateSourcesDirectory) {
    this.generateSourcesDirectory = generateSourcesDirectory;
    return this;
  }

  public Configuration usingResourcesDirectory(String resourcesDirectory) {
    this.resourcesDirectory = resourcesDirectory;
    return this;
  }

  public Log getLogger() {
    return logger;
  }

  public String getBasePackage() {
    return basePackage;
  }

  public String getBaseFolder() {
    return baseFolder;
  }

  public String getGenerateSourcesDirectory() {
    return generateSourcesDirectory;
  }

  public String getResourcesDirectory() {
    return resourcesDirectory;
  }

  @Override
  public String toString() {
    return "Configuration{"
        + "basePackage='"
        + basePackage
        + '\''
        + ", baseFolder='"
        + baseFolder
        + '\''
        + ", generateSourcesDirectory='"
        + generateSourcesDirectory
        + '\''
        + ", resourcesDirectory='"
        + resourcesDirectory
        + '\''
        + '}';
  }
}
