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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.EnumSet;
import java.util.Set;

import static java.nio.file.attribute.PosixFilePermission.OWNER_READ;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests AsciiDocAnnotationParser.
 */
public class ParserTest {

    public static final String PACKAGE = "com.pconil.restdoc";
    public static final String ADOC_DIR = "target/generated-snippets";
    public static final String JAVA_DIR = "target/generated-test-sources";

    /**
     * Parses java class that contains only description on its fields.
     *
     * @throws Exception if something goes wrong
     */
    @Test
    public void parseClassWithDescription() throws Exception {
        AsciiDocAnnotationParser asciiDocAnnotationParser = new AsciiDocAnnotationParser(PACKAGE, ADOC_DIR, JAVA_DIR,
                "target/classes");
        asciiDocAnnotationParser.parse();
        String filename = "target/generated-snippets/Class1DTO.adoc";
        File adhoc = new File(filename);
        assertThat(adhoc).exists();
        File expected = new File("./src/test/resources/Class1DTOExpected.adoc");
        assertThat(adhoc).hasSameContentAs(expected);
    }

    /**
     * Parses java class that contains description and constraint on its fields.
     *
     * @throws Exception if something goes wrong
     */
    @Test
    public void parseClassWithDescriptionAndConstraints() throws Exception {
        AsciiDocAnnotationParser asciiDocAnnotationParser = new AsciiDocAnnotationParser(PACKAGE, ADOC_DIR, JAVA_DIR,
                "target/classes");
        asciiDocAnnotationParser.parse();
        String filename = "target/generated-snippets/SimilarContent.adoc";
        File adhoc = new File(filename);
        assertThat(adhoc).exists();
        File expected = new File("./src/test/resources/SimilarContent.adoc");
        assertThat(adhoc).hasSameContentAs(expected);
    }

    /**
     * Parses java class that contains no description.
     *
     * @throws Exception if something goes wrong
     */
    @Test
    public void parseClassWithoutDescription() throws Exception {
        AsciiDocAnnotationParser asciiDocAnnotationParser = new AsciiDocAnnotationParser(PACKAGE, ADOC_DIR, JAVA_DIR,
                "target/classes");
        asciiDocAnnotationParser.parse();
        String filename = "target/generated-snippets/Class2DTO.adoc";
        File adhoc = new File(filename);
        assertThat(adhoc).exists();
        File expected = new File("./src/test/resources/Class2DTOExpected.adoc");
        assertThat(adhoc).hasSameContentAs(expected);
    }

    /**
     * Parses java class that contains swagger description.
     *
     * @throws Exception if something goes wrong
     */
    @Test
    public void parseClassWithSwaggerAnnotationOnly() throws Exception {
        AsciiDocAnnotationParser asciiDocAnnotationParser = new AsciiDocAnnotationParser(PACKAGE, ADOC_DIR, JAVA_DIR,
                "target/classes");
        asciiDocAnnotationParser.parse();
        String filename = "target/generated-snippets/ClassSwaggerDTO.adoc";
        File adhoc = new File(filename);
        assertThat(adhoc).exists();
        File expected = new File("./src/test/resources/ClassSwaggerDTOExpected.adoc");
        assertThat(adhoc).hasSameContentAs(expected);
    }

    @Test(expected = ParserException.class)
    public void badPackageName() throws Exception {
        new AsciiDocAnnotationParser("com.pconil/restdoc", ADOC_DIR, JAVA_DIR, "target/classes");
    }
    

    @Test(expected = ParserException.class)
    public void unwritableTargetName() throws Exception {
        createTmpDirWithoutWriteRight("target/unwritableDir");
        new AsciiDocAnnotationParser(PACKAGE, "target/unwritableDir/test", "target/unwritableDir/test", "target/classes");
    }

    /**
     * Creates tmp dir that doesn't permit write access.
     *
     * @param name
     */
    private void createTmpDirWithoutWriteRight(String name) throws IOException {
        Path path = Paths.get(name);
        Set<PosixFilePermission> perms =
                EnumSet.of(OWNER_READ);
        Files.deleteIfExists(path);
        Files.createFile(path, PosixFilePermissions.asFileAttribute(perms));
    }
}