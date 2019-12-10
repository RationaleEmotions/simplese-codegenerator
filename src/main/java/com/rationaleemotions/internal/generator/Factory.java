package com.rationaleemotions.internal.generator;

import com.rationaleemotions.page.Button;
import com.rationaleemotions.page.Checkbox;
import com.rationaleemotions.page.Form;
import com.rationaleemotions.page.GenericElement;
import com.rationaleemotions.page.Image;
import com.rationaleemotions.page.Label;
import com.rationaleemotions.page.Link;
import com.rationaleemotions.page.RadioButton;
import com.rationaleemotions.page.SelectList;
import com.rationaleemotions.page.TextField;
import com.rationaleemotions.page.WebElementType;
import java.util.HashMap;
import java.util.Map;

public final class Factory {

  private Factory() {}

  private static final Map<WebElementType, IGenerate> mapping = new HashMap<>();
  private static IGenerate defaultGenerator = () -> GenericElement.class;

  static {
    mapping.put(WebElementType.BUTTON, () -> Button.class);
    mapping.put(WebElementType.CHECKBOX, () -> Checkbox.class);
    mapping.put(WebElementType.FORM, () -> Form.class);
    mapping.put(WebElementType.GENERIC, defaultGenerator);
    mapping.put(WebElementType.IMAGE, () -> Image.class);
    mapping.put(WebElementType.LABEL, () -> Label.class);
    mapping.put(WebElementType.LINK, () -> Link.class);
    mapping.put(WebElementType.RADIO, () -> RadioButton.class);
    mapping.put(WebElementType.SELECT, () -> SelectList.class);
    mapping.put(WebElementType.TEXTFIELD, () -> TextField.class);
  }

  public static IGenerate getGenerator(WebElementType type) {
    return mapping.getOrDefault(type, defaultGenerator);
  }
}
