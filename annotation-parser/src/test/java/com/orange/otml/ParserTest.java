package com.orange.otml;

import com.orange.otml.restdoc.AsciiDocAnnotationParser;
import com.orange.otml.restdoc.ParserException;
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
        AsciiDocAnnotationParser asciiDocAnnotationParser = new AsciiDocAnnotationParser("com.orange.otml", "target", "target/classes");
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
        AsciiDocAnnotationParser asciiDocAnnotationParser = new AsciiDocAnnotationParser("com.orange.otml", "target", "target/classes");
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
        AsciiDocAnnotationParser asciiDocAnnotationParser = new AsciiDocAnnotationParser("com.orange.otml", "target", "target/classes");
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
        new AsciiDocAnnotationParser("com.orange/otml", "target", "target/classes");
    }

    @Test(expected = ParserException.class)
    public void badTargetName() throws Exception {
        new AsciiDocAnnotationParser("com.orange.otml", "/bin", "target/classes");
    }

    @Test(expected = ParserException.class)
    public void unwritableTargetName() throws Exception {
        new AsciiDocAnnotationParser("com.orange.otml", "/Users", "target/classes");
    }

}