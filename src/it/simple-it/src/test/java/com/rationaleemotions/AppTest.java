package com.rationaleemotions;


import com.rationaleemotions.page.GenericElement;
import com.rationaleemotions.web.FakeDriver;
import krishnan.mahadevan.india.IndiaPage;
import org.testng.annotations.Test;

public class AppTest {

  @Test
  public void shouldAnswerWithTrue() {
    IndiaPage page = new IndiaPage(new FakeDriver());
    System.err.println("Dragon Lord says ===> " + page.getFoo());
  }
}
