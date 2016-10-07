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

import com.pconil.restdoc.annotation.InspectToDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This parser produces target/ClassName.restdoc files for all classes matching package-name or its subpackages.
 * 
 * @author  patrice_conil
 */
@InspectToDocument(description = "Parser used to generate restdoc files from swagger annotation")
abstract class AbstractParser {


    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractParser.class);

    /**
     * File where to write restdoc file.
     */
    FileOutputStream adocFile = null;

    /**
     * Java dir.
     */
    String targetJavaDirName = null;

    /**
     * Directory where we want to put restdoc files.
     */
    String targetAdocDirName = null;

    /**
     * Directory that contains .class to parse.
     */
    File sourceDir;

    /**
     * Directory where we want to put java file.
     */
    File targetJavaDir = null;

    /**
     * Directory where we want to put adoc file.
     */
    File targetAdocDir = null;

    /**
     * Package name.
     */
    String packageName;

    /**
     * AbstractParser Constructor.
     *
     * @param packageName name of package that we want to parse
     * @param adocDirName project for which we want to generate restdoc and java files
     * @param javaDirName project for which we want to generate restdoc and java files
     * @param source      directory where .class will be found
     * @throws ParserException if packageName is malformed or target creation isn't possible
     */
    AbstractParser(String packageName, String adocDirName, String javaDirName, String source) throws ParserException {

        this.packageName = packageName;
        targetAdocDirName = adocDirName;
        targetJavaDirName = javaDirName;

        targetJavaDir = new File(targetJavaDirName);
        targetAdocDir = new File(targetAdocDirName);

        sourceDir = new File(source);

        if (!sourceDir.isDirectory()) {
            logErrorAndThrowParserException(String.format(" %s is not a directory", targetJavaDirName));

        }
        validatePackageName(packageName);

        if (!targetJavaDir.exists() && !targetJavaDir.mkdirs()) {
            logErrorAndThrowParserException(String.format("Can't create directory %s for java sources",
                    targetJavaDirName));
        }
        if (!targetAdocDir.exists() && !targetAdocDir.mkdirs()) {
            logErrorAndThrowParserException(String.format("Can't create directory %s for restdoc files",
                    targetAdocDirName));
        }
    }


    /**
     * Parses packageName and its sub packages to generate Class.restdoc files for Class tagged @InspectToDocument.
     * Class.restdoc files are generated in target/generated-snippet/com.pconil.restdoc.model targetJavaDir.
     *
     * @throws ParserException if we can't create Class.adhoc files in target/generated-snippets/com.pconil.restdoc
     *                         .model.
     */
    void parse() throws ParserException {

        List<Class> classes;
        boolean hasDocumentedField;

        ParserClassLoader classLoader = null;

        try {
            // Add source url to local classloader
            classLoader = new ParserClassLoader(sourceDir);
            classes = classLoader.getClasses(sourceDir.getAbsolutePath(), packageName);

            for (Class c : classes) {
                LOGGER.debug("Parsing class {} to generate java code", c.getSimpleName());
                if (c.isAnnotationPresent(InspectToDocument.class)) {
                    hasDocumentedField = false;
                    // Parse all fields declared in this class
                    initializeFields();
                    for (Field field : c.getDeclaredFields()) {
                        hasDocumentedField = parseField(c, field, hasDocumentedField);
                    }
                    if (hasDocumentedField) {
                        completeFields();
                        completeClass();
                        writeClass();
                    } else {
                        LOGGER.debug(String.format("You declared your class %s as documented "
                                                                      + "but there is no documented field in it",
                                c.getSimpleName()));
                    }
                }
            }
            classLoader.restoreLoader();
        } catch (ClassNotFoundException | IOException e) {
            logErrorAndThrowParserException("Class not found while parsing your model");
        }
    }

    /**
     * Logs the error message and send a ParserException.
     *
     * @param message the error message
     */
    protected void logErrorAndThrowParserException(String message) {
        LOGGER.error(message);
        throw new ParserException(message);
    }

    /**
     * Parses current field to check if it's a field to document.
     *
     * @param c                the class we're parsing
     * @param field            the current field to parse
     * @param hasDocumentation tells if class c already have a documented field
     * @return hasDocumented || this field is documented
     */
    protected abstract boolean parseField(Class c, Field field, boolean hasDocumentation);

    /**
     * Initializes header(s) for fields.
     *
     * @param c the classe for which we initialize field header
     */
    protected abstract void writeFieldStart(Class c);

    /**
     * Initializes info buffer for class c.
     *
     * @param c the class we're parsing
     * @throws IOException if write failed
     */
    protected abstract void writeClassStart(Class c) throws IOException;

    /**
     * Initializes writing for fields.
     *
     * @throws IOException if something goes wrong with file creation
     */
    protected abstract void initializeFields() throws IOException;

    /**
     * Generates the file.
     *
     * @return the FileOutputStream if all is ok.
     */
    protected abstract FileOutputStream writeClass();


    /**
     * Ends class writing.
     *
     * @throws IOException if write failed
     */
    protected abstract void completeClass() throws IOException;

    /**
     * Ends field writing.
     *
     * @throws IOException if write failed
     */
    protected abstract void completeFields() throws IOException;

    /**
     * Check if packageName is well formed ... but not if its a java keyword.
     *
     * @param packageName the name of the package to check
     * @throws ParserException if the package name is invalid
     */
    private void validatePackageName(String packageName) throws ParserException {
        Pattern p = Pattern.compile("^[a-zA-Z_\\$][\\w\\$]*(?:\\.[a-zA-Z_\\$][\\w\\$]*)*$");
        if (!p.matcher(packageName).matches()) {
            logErrorAndThrowParserException(String.format("invalid package name: %s", packageName));
        }
    }

    /**
     * Create file targetDir/simpleName+suffix.
     *
     * @param simpleName the class name
     * @param targetDir  the target directory
     * @param suffix     the file suffix to add
     * @return a file descriptor
     * @throws ParserException if creation fail
     */
    FileOutputStream createClassFile(String simpleName, String targetDir, String suffix) throws ParserException {
        FileOutputStream file = null;
        String classFileName = targetDir + "/" + simpleName + suffix;
        try {
            file = new FileOutputStream(classFileName, false);
            LOGGER.debug("File {} created", classFileName);
        } catch (FileNotFoundException e) {
            logErrorAndThrowParserException(String.format("Can't create file %s", classFileName));
        }
        return file;
    }

}
