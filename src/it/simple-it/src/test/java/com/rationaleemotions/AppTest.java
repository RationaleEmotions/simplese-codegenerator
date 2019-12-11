package com.rationaleemotions;


import com.rationaleemotions.page.RadioButton;
import com.rationaleemotions.page.WebElementType;
import com.rationaleemotions.web.FakeDriver;
import kung.fu.panda.india.IndiaPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AppTest {

  @Test
  public void shouldAnswerWithTrue() {
    IndiaPage page = new IndiaPage(new FakeDriver());
    System.err.println(page.getFoo());
    RadioButton fooButton = page.getFoo();
    Assert.assertEquals(fooButton.getType(), WebElementType.RADIO);
  }
}