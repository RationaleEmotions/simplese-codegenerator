package com.rationaleemotions.web;

import kung.fu.panda.india.IndiaPage;

public class SamplePageDemo {

  public static void runDemo() {
    IndiaPage page = new IndiaPage(new FakeDriver());
    if (page.getFoo().getUnderlyingElement() == null) {
      throw new IllegalStateException("Problem");
    }
  }

}
