package com.orange.otml.model;

import com.orange.otml.restdoc.annotation.AsciidocAnnotation;
import com.orange.otml.restdoc.annotation.MustBeDocumented;

/**
 * Annotations to generate restdoc file from pojo classes.
 */
@MustBeDocumented(description = "This is the Class1 description")
public class Class1DTO {
    @AsciidocAnnotation(description="This field describe name of Class1DTO", constraints = "Length must be between 4 and 6")
    String field1;

    @AsciidocAnnotation(description="field 2")
    String field2 = null;

    public Class1DTO(String field1, String field2) {
        this.field1 = field1;
        this.field2 = field2;
    }
}
