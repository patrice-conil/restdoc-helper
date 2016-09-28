package com.orange.otml;

import com.orange.otml.restdoc.Swagger2JavaParser;
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
     * Generates java sources for classes in the com.orange.otml.model package and checks the result.
     * @throws Exception if the result isn't the expected one
     */
    @Test
    public void generateJavaFromSwaggerAnnotation() throws Exception {
        Swagger2JavaParser parser = new Swagger2JavaParser("com.orange.otml", "target", "target/classes");
        parser.parse();
        String filename = "./target/generated-test-sources/ClassSwaggerDTOFieldDescriptor.java";
        File java = new File(filename);
        assertEquals(true, java.exists());
        String expected = Utils.loadResource("ClassSwaggerDTOFieldDescriptor.java.expected").replace("\r","");
        String result = new String(Files.readAllBytes(Paths.get(filename)), "UTF-8").replace("\r","");
        assertEquals(expected, result);
    }
}