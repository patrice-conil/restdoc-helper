package com.pconil.restdoc;

import com.pconil.restdoc.annotation.MustBeDocumented;
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
 */
@MustBeDocumented(description = "Parser used to generate restdoc files from swagger annotation")
public  abstract class AbstractParser {


    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractParser.class);
    
    /**
     * File where to write restdoc file.
     */
    protected FileOutputStream adocFile = null;

    /**
     * Java dir.
     */
    protected String targetJavaDirName = null;
    
    /**
     * Name of restdoc file to write.
     */
    protected String adocName = null;

    /**
     * Directory where we want to put restdoc files.
     */
    protected String targetAdocDirName = null;

    /**
     *  Directory that contains .class to parse.
     */
    protected File sourceDir;

    /**
     * Directory where we want to put java file.
     */
    protected File targetJavaDir = null;

    /**
     * Directory where we want to put java file.
     */
    protected File targetAdocDir = null;
    
    /**
     * Package name.
     */
    protected String packageName;
    
    /**
     * AsciiDocAnnotationParser Constructor.
     *
     * @param packageName name of package that we want to parse
     * @param target      project for which we want to generate restdoc and java files
     * @param source      directory where .class will be found
     * @throws ParserException if packageName is malformed or target creation isn't possible
     */
    AbstractParser(String packageName, String target, String source) throws ParserException {
        
        this.packageName = packageName;
        targetAdocDirName = target + "/generated-snippets/";
        targetJavaDirName = target + "/generated-test-sources/";
        
        targetJavaDir = new File(targetJavaDirName);
        targetAdocDir = new File(targetAdocDirName);
        
        sourceDir = new File(source);

        if (!sourceDir.isDirectory()) {
            throw new ParserException("Source dir is not a directory");
        }
        if (!validatePackageName(packageName)) {
            throw new ParserException("Invalid parameter found");
        }
        if (!targetJavaDir.exists() && !targetJavaDir.mkdirs()) {
            LOGGER.error("Can't create directory {} for java sources", targetJavaDirName);
            throw new ParserException(String.format("Can't create file %s", targetJavaDirName));
        }
        if (!targetAdocDir.exists() && !targetAdocDir.mkdirs()) {
            LOGGER.error("Can't create directory {} for restdoc files", targetAdocDirName);
            throw new ParserException(String.format("Can't create file %s", targetAdocDirName));
        }
    }


    /**
     * Parses packageName and its sub packages to generate Class.restdoc files for Class tagged @MustBeDocumented.
     * Class.restdoc files are generated in target/generated-snippet/com.pconil.restdoc.model targetJavaDir.
     * @throws ParserException if we can't create Class.adhoc files in target/generated-snippets/com.pconil.restdoc.model.
     */
    public void parse() throws ParserException {
        
        List<Class> classes;
        boolean hasDocumentedField;
        
        ParserClassLoader classLoader = null;
        FileOutputStream classFile = null;

        try {
            // Add source url to local classloader
            classLoader = new ParserClassLoader(sourceDir);
            classes = classLoader.getClasses(sourceDir.getAbsolutePath(), packageName);

            for (Class c : classes) {
                LOGGER.debug("Parsing class {} to generate java code", c.getSimpleName());
                if (c.isAnnotationPresent(MustBeDocumented.class)) {
                    hasDocumentedField = false;
                    // Parse all fields declared in this class
                    initializeFields();
                    for (Field field : c.getDeclaredFields()) {
                        hasDocumentedField = parseField(c, field, hasDocumentedField);
                    }
                    if (hasDocumentedField) {
                        completeField();
                        completeClass();
                        classFile = writeClass();
                    }
                }
            }
            classLoader.restoreLoader();
        } catch (ClassNotFoundException | IOException e) {
            LOGGER.error("Class not found while parsing your model");
            e.printStackTrace();
        } finally {
            try {
                if (classFile != null) {
                    classFile.flush();
                    classFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Parses current field to check if it's a field to document.
     *
     * @param c the class we're parsing
     * @param field the current field to parse
     * @param hasDocumented  tells if class c already have a documented field             
     * @return hasDocumented || this field is documented
     */
    protected abstract boolean parseField(Class c, Field field, boolean hasDocumented);

    /**
     * Initializes header(s) for fields.
     * @param c the classe for which we initialize field header
     */
    protected abstract void writeFieldStart(Class c);

    /**
     * Initializes info buffer for class c.
     * @param c the class we're parsing
     * @throws IOException if write failed
     */
    protected abstract void writeClassStart(Class c) throws IOException;

    /**
     * Initializes writing for fields.
     * @throws IOException if something goes wrong with file creation
     */
    protected abstract void initializeFields() throws IOException;
    

    /**
     * Generates the file.
     * @return the FileOutputStream if all is ok.
     */
    protected abstract FileOutputStream writeClass();


    /**
     * Ends class writing.
     * @throws IOException if write failed
     */
    protected abstract void completeClass() throws IOException;

    /**
     * Ends field writing.
     * @throws IOException if write failed
     * */
    protected abstract void completeField() throws IOException;
    
    
    /**
     * Check if packageName is well formed ... but not if its a java keyword.
     * @param packageName the name of the package to check
     * @return true if packageName is well formed
     */
    static boolean validatePackageName(String packageName) {
        Pattern p = Pattern.compile("^[a-zA-Z_\\$][\\w\\$]*(?:\\.[a-zA-Z_\\$][\\w\\$]*)*$");
        if (!p.matcher(packageName).matches()) {
            LOGGER.error("invalid package name: %s", packageName);
            return false;
        }
        return true;
    }
    

    /**
     * Create file targetDir/simpleName+suffix.
     * @param simpleName the class name
     * @param targetDir the target directory 
     * @param suffix the file suffix to add
     * @return a file descriptor
     * @throws ParserException if the file creation fail
     */
    FileOutputStream createClassFile(String simpleName, String targetDir, String suffix) throws ParserException {
        FileOutputStream file;
        String classFileName = targetDir + "/" + simpleName + suffix;
        try {
            file = new FileOutputStream(classFileName, false);
            LOGGER.debug("File {} created", classFileName);
        } catch (FileNotFoundException e) {
            LOGGER.error("Can't create file %s", classFileName);
            throw new ParserException(String.format("Can't create file %s", classFileName));
        }
        return file;
    }

}
