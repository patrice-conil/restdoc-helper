package com.orange.otml.model;

import com.orange.otml.restdoc.annotation.AsciidocAnnotation;
import com.orange.otml.restdoc.annotation.MustBeDocumented;

/**
 * Test class.
 */
@MustBeDocumented
public class Class2DTO {
    @AsciidocAnnotation(description="field 1")
    private String field1;

    @AsciidocAnnotation(description="field 2")
    private String field2 = null;

    public Class2DTO(String field1, String field2) {
        this.field1 = field1;
        this.field2 = field2;
    }
}