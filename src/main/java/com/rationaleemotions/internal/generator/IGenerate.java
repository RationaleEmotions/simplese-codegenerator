package com.rationaleemotions.internal.generator;

import com.rationaleemotions.internal.parser.pojos.Element;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import java.util.List;
import javax.lang.model.element.Modifier;
import org.apache.commons.text.WordUtils;

/**
 * This functional interface represents the capabilities to be possessed by code snippet
 * generators for different types of elements as
 */
@FunctionalInterface
public interface IGenerate {

  /**
   * For a given {@link Element} generates the corresponding getter method
   * @param element - The {@link Element} object
   * @return - A {@link MethodSpec} instance that represents the generated method.
   */
  default MethodSpec generate(Element element) {
    String methodSuffix = WordUtils.capitalizeFully(element.getName());
    String elementText = getReturnTypeForGetElementMethod().getSimpleName();
    if (element.isList()) {
      ParameterizedTypeName returnValue =
          ParameterizedTypeName.get(List.class, getReturnTypeForGetElementMethod());
      return MethodSpec.methodBuilder("get" + methodSuffix + "s")
          .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
          .returns(returnValue)
          .addStatement("return page.get$Ls($S)", elementText, element.getName())
          .build();
    }
    return MethodSpec.methodBuilder("get" + methodSuffix)
        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
        .returns(getReturnTypeForGetElementMethod())
        .addStatement("return page.get$L($S)", elementText, element.getName())
        .build();
  }

  /**
   *
   * @return - A {@link Class} that represents the corresponding object type for the element
   */
  Class<?> getReturnTypeForGetElementMethod();
}
