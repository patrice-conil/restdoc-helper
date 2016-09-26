package com.orange.otml.restdoc;

import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static com.orange.otml.restdoc.Constant.CLASS_END;
import static com.orange.otml.restdoc.Constant.CLASS_HEADER;
import static com.orange.otml.restdoc.Constant.FIELD_END;
import static com.orange.otml.restdoc.Constant.FIELD_LIST_START;
import static com.orange.otml.restdoc.Constant.FIELD_SEPARATOR;
import static com.orange.otml.restdoc.Constant.FIELD_START;
import static com.orange.otml.restdoc.Constant.OPTIONAL_FIELD_FORMAT;
import static com.orange.otml.restdoc.Constant.REQUIRED_FIELD_FORMAT;


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
     * AsciiDocAnnotationParser Constructor.
     *
     * @param packageName name of package that we want to parse
     * @param target      project directory for which we want to generate restdoc files
     * @param source      directory where .class will be found
     * @throws ParserException if packageName is malformed or target creation isn't possible
     */
    public Swagger2JavaParser(String packageName, String target, String source) throws ParserException {
        super(packageName, target, source);
    }

    @Override
    protected boolean parseField(Class c, Field field, boolean hasDocumented) {
        boolean isDocumented = false;
        boolean firstField = false;
        if (field.isAnnotationPresent(ApiModelProperty.class)) {
            try {
                //Is it the first documented field of the class?
                if (!hasDocumented) {
                    firstField = true;
                    isDocumented = true;
                    writeClassStart(c);
                    sbField.append(String.format(FIELD_START, c.getSimpleName()));
                    sbListField.append(String.format(FIELD_LIST_START, c.getSimpleName()));
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
        return hasDocumented || isDocumented;
    }

    @Override
    protected void writeFieldStart(Class c) {

    }

    @Override
    protected void writeClassStart(Class c) throws IOException {
        byte[] content = String.format(CLASS_HEADER, packageName, c.getSimpleName()).getBytes();
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
    protected void completeField() throws IOException {
        sbField.append(FIELD_END);
        sbListField.append(FIELD_END);
        writeFieldDescriptors(classFile, sbField.toString(), sbListField.toString());
    }


    /**
     * Write class footer to stream.
     *
     * @param stream file where to write
     * @throws IOException If something goes wrong wile writing to file
     */
    private void writeClassEnd(FileOutputStream stream) throws IOException {
        byte[] content = CLASS_END.getBytes();
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
        byte[] content = (fd + "\n\n" + fdList + "\n\n").getBytes();
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
            content += FIELD_SEPARATOR;
        }
        // This is a required field
        if (((ApiModelProperty) anno).required()) {
            content += String.format(REQUIRED_FIELD_FORMAT, fieldName, ((ApiModelProperty) anno).value());
        } else {
            content += String.format(OPTIONAL_FIELD_FORMAT, fieldName, ((ApiModelProperty) anno).value());
        }
        return content;
    }


}
