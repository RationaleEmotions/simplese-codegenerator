package com.rationaleemotions;

import com.rationaleemotions.internal.CodeGenHelper;
import com.rationaleemotions.utils.StringUtils;
import java.util.Objects;
import java.util.Optional;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import org.apache.maven.project.MavenProject;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class SimpleSeCodeGenMojo extends AbstractMojo {

  /** Project the plugin is called from. */
  @Parameter(property = "project", defaultValue = "${project}", required = true)
  private MavenProject project;

  /** Represents the base package used for generated java classes. */
  @Parameter(
      property = "basePackage",
      defaultValue = "com.rationaleemotions.pages",
      required = true)
  private String basePackage;

  /** Represents the base folder used for reading json page files. */
  @Parameter(property = "baseFolder", defaultValue = "json", required = true)
  private String baseFolder;

  /** Location of the file. */
  @Parameter(
      property = "generated-sources",
      defaultValue = "generated-sources",
      required = true)
  private String outputDirectory;

  public void execute() throws MojoExecutionException {

    CodeGenHelper helper = new CodeGenHelper(getConfiguration());
    try {
      helper.generate();
    } catch (IOException e) {
      getLog().error(e);
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }

  private Configuration getConfiguration() {
    return new Configuration()
        .usingLogger(getLog())
        .usingBaseFolder(this.baseFolder)
        .usingBasePackage(this.basePackage)
        .usingGenerateSourcesDirectory(getDirectoryToGenerateSources())
        .usingResourcesDirectory(getResourcesDirectory());
  }

  public String getDirectoryToGenerateSources() {
    if (outputDirectory == null || outputDirectory.trim().isEmpty()) {
      outputDirectory = "generated-sources";
    }
    return project.getBuild().getDirectory() + File.separator + "generated-sources";
  }

  public String getResourcesDirectory() {
    Optional<Resource> first =
        project.getBuild().getResources().stream()
            .filter(Objects::nonNull)
            .filter(resource -> StringUtils.isNotBlank(resource.getDirectory()))
            .findFirst();
    if (first.isPresent()) {
      return first.get().getDirectory();
    }
    return "";
  }
}
