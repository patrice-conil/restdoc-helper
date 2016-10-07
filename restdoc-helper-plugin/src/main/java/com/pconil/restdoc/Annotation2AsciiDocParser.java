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

import com.pconil.restdoc.annotation.AsciidocAnnotation;
import com.pconil.restdoc.annotation.InspectToDocument;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

/**
 * This parser produces target/ClassName.restdoc files for all classes matching package-name or its subpackages.
 * 
 * @author  patrice_conil
 */
@InspectToDocument(description = "Parser used to generate restdoc files from swagger annotation")
public class Annotation2AsciiDocParser extends AbstractParser {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Annotation2AsciiDocParser.class);


    /**
     * Annotation2AsciiDocParser Constructor.
     *
     * @param packageName name of package that we want to parse
     * @param adocDirName project for which we want to generate restdoc and java files
     * @param javaDirName project for which we want to generate restdoc and java files
     * @param source      directory where .class will be found
     * @throws ParserException if packageName is malformed or target creation isn't possible
     */
    public Annotation2AsciiDocParser(String packageName, String adocDirName, String javaDirName,
                                     String source) throws ParserException {
        super(packageName, adocDirName, javaDirName, source);
    }

    @Override
    protected boolean parseField(Class c, Field field, boolean hasDocumentation) {
        boolean isDocumented = false;
        if (field.isAnnotationPresent(AsciidocAnnotation.class) || field.isAnnotationPresent(ApiModelProperty.class)) {
            try {
                //Is it the first documented field of the class?
                if (!hasDocumentation) {
                    isDocumented = true;
                    writeClassStart(c);
                    writeFieldStart(c);
                }
                // Iterates all the annotations available in the method
                for (Annotation anno : field.getDeclaredAnnotations()) {
                    LOGGER.debug("\tAnnotation {} in class {} for field {} \n",
                            anno.annotationType().getSimpleName(),
                            c.getSimpleName(), field.getName());
                    if (anno instanceof AsciidocAnnotation) {
                        writeFieldInfo(field, (AsciidocAnnotation) anno, adocFile);
                    } else if (anno instanceof ApiModelProperty) {
                        writeFieldInfo(field, (ApiModelProperty) anno, adocFile);
                    }
                }
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
        return hasDocumentation || isDocumented;
    }

    @Override
    protected void writeFieldStart(Class c) {

    }

    @Override
    protected void writeClassStart(Class c) throws IOException {
        adocFile = createClassFile(c.getSimpleName(), targetAdocDirName, ".adoc");
        writeClassHeader(c, adocFile);
        writeFieldHeader(adocFile);
    }

    @Override
    protected void initializeFields() throws IOException {
    }

    @Override
    protected FileOutputStream writeClass() {
        return null;
    }

    @Override
    protected void completeClass() throws IOException {

    }

    @Override
    protected void completeFields() throws IOException {
        writeFieldFooter(adocFile);
    }

    /**
     * Write field footer to stream.
     *
     * @param stream file where to write
     * @throws IOException If something goes wrong wile writing to file
     */
    private void writeFieldFooter(FileOutputStream stream) throws IOException {
        byte[] content = Constant.FIELD_FOOTER.getBytes(StandardCharsets.UTF_8);
        stream.write(content);
    }

    /**
     * Writes Field informations contained in anno in restdoc file.
     *
     * @param field  Field
     * @param anno   An AsciidocAnnotation
     * @param stream file where to write
     * @throws IOException If something goes wrong wile writing to file
     */
    private void writeFieldInfo(Field field, AsciidocAnnotation anno, FileOutputStream stream) throws IOException {
        byte[] content = String.format(Constant.FIELD_FORMAT, field.getName(), field.getType().getSimpleName(),
                 anno.description(), anno.constraints())
                .getBytes(StandardCharsets.UTF_8);
        stream.write(content);
        stream.flush();
    }

    /**
     * Writes Field informations contained in anno in restdoc file.
     *
     * @param field  Field
     * @param anno   An ApiModelProperty annotation
     * @param stream file where to write
     * @throws IOException If something goes wrong wile writing to file
     */
    private void writeFieldInfo(Field field, ApiModelProperty anno,
                                                   FileOutputStream stream) throws IOException {
        byte[] content = String.format(Constant.FIELD_FORMAT, field.getName(), field.getType().getSimpleName(),
                anno.value(), anno.required() ? "required" : "optional")
                .getBytes(StandardCharsets.UTF_8);
        stream.write(content);
        stream.flush();
    }

    /**
     * Writes Field header in restdoc file.
     *
     * @param stream file where to write
     * @throws IOException If something goes wrong wile writing to file
     */
    private void writeFieldHeader(FileOutputStream stream) throws IOException {
        byte[] content = Constant.FIELD_HEADER_FORMAT.getBytes(StandardCharsets.UTF_8);
        stream.write(content);
        stream.flush();
    }

    /**
     * Writes Class header in restdoc file.
     *
     * @param c      class targeted
     * @param stream file where to write
     * @throws IOException If something goes wrong wile writing to file
     */
    private void writeClassHeader(Class c, FileOutputStream stream) throws IOException {
        byte[] content;
        InspectToDocument anno = (InspectToDocument) c.getAnnotation(InspectToDocument.class);
        // Write class header
        if ("".equals(anno.description())) {

            content = String.format(Constant.CLASS_HEADER_FORMAT, c.getSimpleName(), c.getSimpleName())
                    .getBytes(StandardCharsets.UTF_8);
        } else {
            content = String.format(Constant.CLASS_HEADER_WITH_DESCRIPTION_FORMAT, c.getSimpleName(), c.getSimpleName(),
                    anno.description()).getBytes(StandardCharsets.UTF_8);
        }
        stream.write(content);
        stream.flush();
    }
}
