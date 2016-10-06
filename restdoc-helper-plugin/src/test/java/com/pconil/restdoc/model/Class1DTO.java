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

import com.pconil.restdoc.annotation.AsciidocAnnotation;
import com.pconil.restdoc.annotation.InspectToDocument;

/**
 * Annotations to generate restdoc file from pojo classes.
 */
@InspectToDocument(description = "This is the Class1 description")
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
