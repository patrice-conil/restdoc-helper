package com.pconil.restdoc.model;

import com.pconil.restdoc.annotation.MustBeDocumented;
import io.swagger.annotations.ApiModelProperty;

/**
 * Annotations to generate restdoc file from pojo classes.
 */
@MustBeDocumented(description = "This is the Class1 description")
public class ClassSwaggerDTO {
    @ApiModelProperty(required = true, value="This is the field field1")
    private String field1;

    @ApiModelProperty(value="This is the optional field field2")
    private String field2 = null;

    @ApiModelProperty(required = true, value="This is the field field3")
    private String field3 = null;
    
    public ClassSwaggerDTO(String field1, String field2) {
        this.field1 = field1;
        this.field2 = field2;
    }
}
