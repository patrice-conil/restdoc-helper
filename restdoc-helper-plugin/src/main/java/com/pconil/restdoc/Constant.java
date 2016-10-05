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
package com.pconil.restdoc;

/**
 * All constants.
 */
public class Constant {
    
    // Constant to generate AsciiDoc generation.
    
    /**
     * Format of line that must be generated as class-CLASS_HEADER_FORMAT header.
     */
    static final String CLASS_HEADER_FORMAT = "[%s]\n= %s\n|===\n";

    /**
     * Format of line that must be generated as class-CLASS_HEADER_FORMAT header.
     */
    static final String CLASS_HEADER_WITH_DESCRIPTION_FORMAT = "[%s]\n= %s\n%s\n|===\n";

    /**
     * Format of line that must be generated as field CLASS_HEADER_FORMAT.
     */
    static final String FIELD_FORMAT = "\n| %s\n| %s\n| %s\n| %s\n";

    /**
     * Format of line that must be generated as field-header CLASS_HEADER_FORMAT.
     */
    static final String FIELD_HEADER_FORMAT = "| Field| Type| Description| Constraints\n";

    /**
     * Line to add to terminate table of field properly.
     */
    static final String FIELD_FOOTER = "|===\n\n";
    
    
    
    // Java classes generation.
    /**
     * Class start.
     */
    static final String CLASS_HEADER = 
            "package %s;\n\n"
            + "import org.springframework.restdocs.payload.FieldDescriptor; \n"
            + "import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;\n\n"
            + "public class %sFieldDescriptor {\n";

    /**
     * Class end.
     */
    static final String CLASS_END = "}\n";

    /**
     * Field start.
     */
    static final String FIELD_LIST_START = "    public static FieldDescriptor[] fd%sList = new FieldDescriptor[] {\n";

    /**
     * Field list start.
     */
    static final String FIELD_START = "    public static FieldDescriptor[] fd%s = new FieldDescriptor[] {\n";

    /**
     * Format for a required field.
     */
    static final String REQUIRED_FIELD_FORMAT = "        fieldWithPath(\"%s\").description(\"%s\")";

    /**
     * Format for an optional field.
     */
    static final String OPTIONAL_FIELD_FORMAT = "        fieldWithPath(\"%s\").description(\"%s\").optional()";

    /**
     * Field end.
     */
    static final String FIELD_END = "    };\n";

    /**
     * Field separator.
     */
    static final String FIELD_SEPARATOR = ",\n";

}
