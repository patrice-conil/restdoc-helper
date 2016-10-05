package com.pconil.restdoc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to tell AsciiDocAnnotationParser to generate Class.restdoc file for this class.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
public @interface  MustBeDocumented {
    /**
     * Description of the class in restdoc corresponding file.
     * @return the description of the class
     */
    String description() default "";
}
