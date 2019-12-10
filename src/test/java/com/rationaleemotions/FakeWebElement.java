package com.rationaleemotions;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class FakeWebElement implements WebElement {

  @Override
  public void click() {}

  @Override
  public void submit() {}

  @Override
  public void sendKeys(CharSequence... keysToSend) {}

  @Override
  public void clear() {}

  @Override
  public String getTagName() {
    return null;
  }

  @Override
  public String getAttribute(String name) {
    return null;
  }

  @Override
  public boolean isSelected() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String getText() {
    return null;
  }

  @Override
  public List<WebElement> findElements(By by) {
    return null;
  }

  @Override
  public WebElement findElement(By by) {
    return new FakeWebElement();
  }

  @Override
  public boolean isDisplayed() {
    return true;
  }

  @Override
  public Point getLocation() {
    return new Point(0, 0);
  }

  @Override
  public Dimension getSize() {
    return new Dimension(10, 10);
  }

  @Override
  public Rectangle getRect() {
    return new Rectangle(new Point(0, 0), new Dimension(10, 10));
  }

  @Override
  public String getCssValue(String propertyName) {
    return null;
  }

  @Override
  public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
    return null;
  }
}
