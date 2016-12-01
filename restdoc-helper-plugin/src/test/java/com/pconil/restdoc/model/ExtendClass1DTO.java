/*
 * Copyright 2016-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pconil.restdoc.model;

import com.pconil.restdoc.annotation.InspectToDocument;
import io.swagger.annotations.ApiModelProperty;

/**
 * Annotations to generate restdoc file from pojo classes.
 * 
 * @author  patrice_conil
 */
@InspectToDocument(description = "This is the Class1 description")
public class ExtendClass1DTO extends Class1DTO{

    @ApiModelProperty(required = true, value="This is the field field3")
    private String field3 = null;
    
    public ExtendClass1DTO(String field1, String field2, String field3) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }
    public ExtendClass1DTO(){}
}
