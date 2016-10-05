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
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * Test java generation from swagger ApiModelProperty.
 */
public class Swagger2JavaParserTest {

    /**
     * Generates java sources for classes in the com.pconil.restdoc.model package and checks the result.
     * @throws Exception if the result isn't the expected one
     */
    @Test
    public void generateJavaFromSwaggerAnnotation() throws Exception {
        Swagger2JavaParser parser = new Swagger2JavaParser("com.pconil.restdoc", "target", "target/classes");
        parser.parse();
        String filename = "./target/generated-test-sources/ClassSwaggerDTOFieldDescriptor.java";
        File java = new File(filename);
        assertEquals(true, java.exists());
        String expected = Utils.loadResource("ClassSwaggerDTOFieldDescriptor.java.expected").replace("\r","");
        String result = new String(Files.readAllBytes(Paths.get(filename)), "UTF-8").replace("\r","");
        assertEquals(expected, result);
    }
}