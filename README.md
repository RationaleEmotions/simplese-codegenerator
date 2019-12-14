[![Build Status](https://travis-ci.org/RationaleEmotions/simplese-codegenerator.svg?branch=master)](https://travis-ci.org/RationaleEmotions/simplese-codegenerator)
# SimpleSe Code Generator

SimpleSe Code generator is a maven plugin that augments your test automation experience when used with [SimpleSe page objects](https://github.com/RationaleEmotions/simplese-codegenerator).

In simple terms you create a json file that is compliant with **SimpleSe** that represents the elements in your web page, and instead of writing all the boiler plate code which is responsible for helping you interact with the page, you can make use of **SimpleSe Code Generator** to generate the code for you.


## Pre-requisites

**SimpleSe Code Generator** requires that you use : 
* **JDK 8**.
* **com.rationaleemotions:simple-se-pageobjects 1.0.5** or higher.
* **Selenium 3.141.59**. 

## How to use.

To use the code generator, you basically need to do the following:

1. Add SimpleSe code generator as a plugin into your pom file under `<build><plugins>` section.
2. Add build helper plugin as a plugin entry as well, because you would like the generated code to be available in your classpath.

Here's how a pom file that has both these plugins look like:

```xml
<build>
<plugins>
  <plugin>
    <groupId>com.rationaleemotions.maven.plugins</groupId>
    <artifactId>simplese-codegenerator</artifactId>
    <version>1.0.1</version>
    <configuration>
      <!-- basePackage represents the default package name prefix for all generated classes -->
      <basePackage>kung.fu.panda</basePackage>
      <!-- outputDirectory represents the location in your build path where the generated code would be writen to. -->
      <outputDirectory>generated-sources</outputDirectory>
    </configuration>
    <executions>
      <execution>
      <!-- We are binding this plugin to the "generate-test-sources" phase.-->
        <phase>generate-test-sources</phase>
        <goals>
          <!-- "generate" is the goal that invokes our plugin" -->
          <goal>generate</goal>
        </goals>
      </execution>
    </executions>
    <dependencies>
     <!-- This plugin requires a dependency on Selenium to be added because
     the generated code refers to some selenium classes -->
      <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>3.14.0</version>
      </dependency>
    </dependencies>
  </plugin>
  <!-- The build helper maven plugin basically adds the generated code back into
  the classpath so that it can be used -->
  <plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>build-helper-maven-plugin</artifactId>
    <version>1.5</version>
    <executions>
      <execution>
        <id>add-source</id>
        <phase>generate-test-sources</phase>
        <goals>
          <goal>add-test-source</goal>
        </goals>
        <configuration>
          <sources>
          <!-- This is where the generated java classes are to be found which
          this plugin will compile and add back to the classpath. 
          The value specified here has to match the value of "outputDirectory" parameter of our code generator plugin entry. 
          -->
            <source>${project.build.directory}/generated-sources</source>
          </sources>
        </configuration>
      </execution>
    </executions>
  </plugin>
</plugins>
```

3. Add your json files that you created into a directory called `json` (if this is not present in your project create this directory) under `src/main/resources`.
4. Now to generate the sources open up a command prompt and run this command ` mvn clean generate-test-sources` (This command needs to be executed everytime you change the contents of your JSON file). Remember that the generated java classes will be found under `target/generated-sources`. Since these classes are generated, you don't need to check in them into your version control system.

## Configuring the plugin

The plugin has the following configuration parameters which can be changed via the `<configuration>` section of the plugin.

1. `basePackage` - Represents the package prefix that is to used when adding package names to the generated classes. If nothing is specified, it defaults to `com.rationaleemotions.pages`.
2. `baseFolder` - Represents the folder under `src/main/resources` within which your json files reside. If nothing is specified, it defaults to `json`. Every sub-directory under `json` (or whatever you are using) would be added up as a package entry to the generated class. So for e.g., if you have a file called `india/one.json` within `src/main/resources/json` and if you provided your `basePackage` as `kung.fu.panda`, then the generated class (The class name is obtained from the `name` attribute of your json file) would have its package name as `kung.fu.panda.india`.
3. `outputDirectory` - Represents the directory that will contain the generated code. This directory is always created under `target` folder (which also happens to be the build output directory of maven and which can be cleaned up by running `mvn clean`). If not specified, it defaults to `generated-sources`. Remember to ensure that whatever value you provide to this parameter, the same value has to be referred to in your `build-helper-maven-plugin`'s configuration section.

To see a live example, please refer to the [sample project here](https://github.com/RationaleEmotions/simplese-codegenerator/tree/master/src/it/simple-it)

## Building the code.

Once the changes are made, the integration tests can be executed using the command `mvn clean -P run-its verify`
