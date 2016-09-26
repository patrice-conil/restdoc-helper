package com.orange.otml.restdoc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * Annotation to document field.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
@Documented
public @interface AsciidocAnnotation {
    /**
     * Contains description of the annotated field.
     * @return the description of the annotated field
     */
    String description() default "";

    /**
     * Contains constraints to apply to the annotated field.
     * @return the constrains that apply to the annotated field
     */
    String constraints() default "";
}
