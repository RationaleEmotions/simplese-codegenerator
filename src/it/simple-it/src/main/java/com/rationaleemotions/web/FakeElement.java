package com.rationaleemotions.web;

import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

/**
 *
 */
public class FakeElement implements WebElement {

  @Override
  public void click() {

  }

  @Override
  public void submit() {

  }

  @Override
  public void sendKeys(CharSequence... keysToSend) {

  }

  @Override
  public void clear() {

  }

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
    return Arrays.asList(new FakeElement(), new FakeElement());
  }

  @Override
  public WebElement findElement(By by) {
    return new FakeElement();
  }

  @Override
  public boolean isDisplayed() {
    return true;
  }

  @Override
  public Point getLocation() {
    return null;
  }

  @Override
  public Dimension getSize() {
    return null;
  }

  @Override
  public Rectangle getRect() {
    return null;
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
