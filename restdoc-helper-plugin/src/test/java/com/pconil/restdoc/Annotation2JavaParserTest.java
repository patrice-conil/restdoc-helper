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

import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test java generation from swagger ApiModelProperty.
 * 
 * @author  patrice_conil
 */
public class Annotation2JavaParserTest {
    public static final String PACKAGE = "com.pconil.restdoc";
    public static final String ADOC_DIR = "target/generated-snippets";
    public static final String JAVA_DIR = "target/generated-test-sources";
    /**
     * Generates java sources for classes in the com.pconil.restdoc.model package and checks the result.
     * @throws Exception if the result isn't the expected one
     */
    @Test
    public void generateJavaFromAnnotation() throws Exception {
        Annotation2JavaParser parser = new Annotation2JavaParser(PACKAGE, ADOC_DIR, JAVA_DIR, "target/classes");
        parser.parse();
        
        //Verify generation from ApiModelProperty annotation
        String filename = "./target/generated-test-sources/ClassSwaggerDTOFieldDescriptor.java";
        File java = new File(filename);
        File expected = new File("./src/test/resources/ClassSwaggerDTOFieldDescriptor.java.expected");
        assertThat(java).exists();
        assertThat(java).hasSameContentAs(expected);

        //Verify generation from AsciidocAnnotation annotation
        filename = "./target/generated-test-sources/ClassWithoutApiModelPropertyFieldDescriptor.java";
        java = new File(filename);
        expected = new File("./src/test/resources/ClassWithoutApiModelPropertyFieldDescriptor.java.expected");
        assertThat(java).exists();
        assertThat(java).hasSameContentAs(expected);

        filename = "./target/generated-test-sources/ExtendClass1DTOFieldDescriptor.java";
        java = new File(filename);
        assertThat(java).exists();
        expected = new File("./src/test/resources/ExtendClass1DTOFieldDescriptor.java.expected");
        assertThat(java).hasSameContentAs(expected);
    }
    
}