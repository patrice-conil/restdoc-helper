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

import static junit.framework.TestCase.assertEquals;

/**
 * Tests AsciiDocAnnotationParser.
 */
public class ParserTest {

    /**
     * Parses java class that contains only description on its fields.
     * @throws Exception if something goes wrong
     */
    @Test
    public void parseClassWithDescription() throws Exception {
        AsciiDocAnnotationParser asciiDocAnnotationParser = new AsciiDocAnnotationParser("com.pconil.restdoc", "target", "target/classes");
        asciiDocAnnotationParser.parse();
        String filename = "target/generated-snippets/Class1DTO.adoc";
        File adhoc = new File(filename);
        assertEquals(true, adhoc.exists());
        String expected = Utils.loadResource("Class1DTOExpected.adoc").replace("\r", "");
        String result = new String(Files.readAllBytes(Paths.get(filename)), "UTF-8").replace("\r", "");
        assertEquals(result, expected);
    }

    /**
     * Parses java class that contains description and constraint on its fields.
     * @throws Exception if something goes wrong
     */
    @Test
    public void parseClassWithDescriptionAndConstraints() throws Exception {
        AsciiDocAnnotationParser asciiDocAnnotationParser = new AsciiDocAnnotationParser("com.pconil.restdoc", "target", "target/classes");
        asciiDocAnnotationParser.parse();
        String filename = "target/generated-snippets/SimilarContent.adoc";
        File adhoc = new File(filename);
        assertEquals(true, adhoc.exists());
        String expected = Utils.loadResource("SimilarContent.adoc").replace("\r", "");
        String result = new String(Files.readAllBytes(Paths.get(filename)), "UTF-8").replace("\r", "");
        assertEquals(result, expected);
    }

    /**
     * Parses java class that contains no description.
     * @throws Exception if something goes wrong
     */
    @Test
    public void parseClassWithoutDescription() throws Exception {
        AsciiDocAnnotationParser asciiDocAnnotationParser = new AsciiDocAnnotationParser("com.pconil.restdoc", "target", "target/classes");
        asciiDocAnnotationParser.parse();
        String filename = "target/generated-snippets/Class2DTO.adoc";
        File adhoc = new File(filename);
        assertEquals(true, adhoc.exists());
        String expected = Utils.loadResource("Class2DTOExpected.adoc").replace("\r", "");
        String result = new String(Files.readAllBytes(Paths.get(filename)), "UTF-8").replace("\r", "");
        assertEquals(result, expected);
    }

    @Test(expected = ParserException.class)
    public void badPackageName() throws Exception {
        new AsciiDocAnnotationParser("com.pconil/restdoc", "target", "target/classes");
    }

    @Test
    public void badTargetName() throws Exception {
        try {
            new AsciiDocAnnotationParser("com.pconil.restdoc", "/bin", "target/classes");
        } catch (ParserException e) {
            assertEquals(e.getMessage(), "Can't create file /bin/generated-test-sources/");
        }
    }

    @Test(expected = ParserException.class)
    public void unwritableTargetName() throws Exception {
        new AsciiDocAnnotationParser("com.pconil.restdoc", "/Users", "target/classes");
    }

}