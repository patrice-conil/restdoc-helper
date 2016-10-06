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
 */
public class Swagger2JavaParser extends AbstractParser {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Swagger2JavaParser.class);

    /**
     * Used to construct field information.
     */
    private StringBuilder sbField = null;

    /**
     * Used to construct field information for list retrieval.
     */
    private StringBuilder sbListField = null;

    /**
     * Classloader.
     */
    private ParserClassLoader classLoader = null;

    /**
     * File where we write java code.
     */
    private FileOutputStream classFile = null;

    /**
     * Swagger2JavaParser Constructor.
     *
     * @param packageName name of package that we want to parse
     * @param adocDirName project for which we want to generate restdoc and java files
     * @param javaDirName project for which we want to generate restdoc and java files
     * @param source      directory where .class will be found
     * @throws ParserException if packageName is malformed or target creation isn't possible
     */
    public Swagger2JavaParser(String packageName, String adocDirName, String javaDirName, String source) throws ParserException {
        super(packageName, adocDirName, javaDirName, source);
    }

    @Override
    protected boolean parseField(Class c, Field field, boolean hasDocumentation) {
        boolean isDocumented = false;
        boolean firstField = false;
        if (field.isAnnotationPresent(ApiModelProperty.class)) {
            try {
                //Is it the first documented field of the class?
                if (!hasDocumentation) {
                    firstField = true;
                    isDocumented = true;
                    writeClassStart(c);
                    sbField.append(String.format(Constant.FIELD_START, c.getSimpleName()));
                    sbListField.append(String.format(Constant.FIELD_LIST_START, c.getSimpleName()));
                }
                // Iterates all the annotations available in the method
                for (Annotation anno : field.getDeclaredAnnotations()) {
                    LOGGER.debug("\tAnnotation {} in class {} for field {} \n",
                            anno.annotationType().getSimpleName(),
                            c.getSimpleName(), field.getName());
                    if (anno instanceof ApiModelProperty) {
                        sbField.append(writeFieldInfo(field, anno, firstField, false));
                        sbListField.append(writeFieldInfo(field, anno, firstField, true));
                        firstField = false;
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
        byte[] content = String.format(Constant.CLASS_HEADER, packageName, c.getSimpleName()).getBytes(StandardCharsets.UTF_8);
        classFile = createClassFile(c.getSimpleName(), targetJavaDirName, "FieldDescriptor.java");
        classFile.write(content);
        classFile.flush();
    }

    @Override
    protected void initializeFields() {
        sbField = new StringBuilder("");
        sbListField = new StringBuilder("");
    }

    @Override
    protected FileOutputStream writeClass() {
        return null;
    }

    @Override
    protected void completeClass() throws IOException {
        writeClassEnd(classFile);
    }

    @Override
    protected void completeFields() throws IOException {
        sbField.append(Constant.FIELD_END);
        sbListField.append(Constant.FIELD_END);
        writeFieldDescriptors(classFile, sbField.toString(), sbListField.toString());
    }


    /**
     * Write class footer to stream.
     *
     * @param stream file where to write
     * @throws IOException If something goes wrong wile writing to file
     */
    private void writeClassEnd(FileOutputStream stream) throws IOException {
        byte[] content = Constant.CLASS_END.getBytes(StandardCharsets.UTF_8);
        stream.write(content);
    }

    /**
     * Write field footer to stream.
     *
     * @param stream file where to write
     * @param fd     the string that describe fields
     * @param fdList the string that describe fields for list retrieval
     * @throws IOException If something goes wrong wile writing to file
     */
    private void writeFieldDescriptors(FileOutputStream stream, String fd, String fdList) throws IOException {
        byte[] content = (fd + "\n\n" + fdList + "\n\n").getBytes(StandardCharsets.UTF_8);
        stream.write(content);
    }

    /**
     * Construct field info.
     *
     * @param field Field
     * @param anno  An ApiModelProperty annotation
     * @param first is it the first documented field of the class ? 
     * @param list are we writing for list retrieval ?
     * @throws IOException If something goes wrong wile writing to file
     * @return the string completed with field info
     */
    private String writeFieldInfo(Field field, Annotation anno, boolean first, boolean list) throws IOException {
        String fieldName;
        String content = "";

        // We prefix the name with "[]." in case of list to comply with restdoc behavior.
        if (list) {
            fieldName = "[]." + field.getName();
        } else {
            fieldName = field.getName();
        }

        // If it is not the first field we need to write separator.
        if (!first) {
            content += Constant.FIELD_SEPARATOR;
        }
        // This is a required field
        if (((ApiModelProperty) anno).required()) {
            content += String.format(Constant.REQUIRED_FIELD_FORMAT, fieldName, ((ApiModelProperty) anno).value());
        } else {
            content += String.format(Constant.OPTIONAL_FIELD_FORMAT, fieldName, ((ApiModelProperty) anno).value());
        }
        return content;
    }


}
