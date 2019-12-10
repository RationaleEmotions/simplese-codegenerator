package com.rationaleemotions;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FakeDriver implements WebDriver {

  @Override
  public void get(String url) {}

  @Override
  public String getCurrentUrl() {
    return null;
  }

  @Override
  public String getTitle() {
    return null;
  }

  @Override
  public List<WebElement> findElements(By by) {
    return Arrays.asList(new FakeWebElement(), new FakeWebElement());
  }

  @Override
  public WebElement findElement(By by) {
    return new FakeWebElement();
  }

  @Override
  public String getPageSource() {
    return null;
  }

  @Override
  public void close() {}

  @Override
  public void quit() {}

  @Override
  public Set<String> getWindowHandles() {
    return null;
  }

  @Override
  public String getWindowHandle() {
    return null;
  }

  @Override
  public TargetLocator switchTo() {
    return null;
  }

  @Override
  public Navigation navigate() {
    return null;
  }

  @Override
  public Options manage() {
    return null;
  }
}
