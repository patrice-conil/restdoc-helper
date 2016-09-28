package com.orange.otml.restdoc;

import com.orange.otml.restdoc.annotation.AsciidocAnnotation;
import com.orange.otml.restdoc.annotation.MustBeDocumented;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

import static com.orange.otml.restdoc.Constant.CLASS_HEADER_FORMAT;
import static com.orange.otml.restdoc.Constant.CLASS_HEADER_WITH_DESCRIPTION_FORMAT;
import static com.orange.otml.restdoc.Constant.FIELD_FORMAT;
import static com.orange.otml.restdoc.Constant.FIELD_HEADER_FORMAT;

/**
 * This parser produces target/ClassName.restdoc files for all classes matching package-name or its subpackages.
 */
@MustBeDocumented(description = "Parser used to generate restdoc files from swagger annotation")
public class AsciiDocAnnotationParser extends AbstractParser {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AsciidocAnnotation.class);
    

    /**
     * AsciiDocAnnotationParser Constructor.
     *
     * @param packageName name of package we want to parse
     * @param target      project directory for which we want to generate restdoc files
     * @param source      directory where .class will be found
     * @throws ParserException if packageName is malformed or target creation isn't possible
     */
    public AsciiDocAnnotationParser(String packageName, String target, String source) throws ParserException {
        super(packageName, target, source);
    }

    @Override
    protected boolean parseField(Class c, Field field, boolean hasDocumented) {
        boolean isDocumented = false;
        if (field.isAnnotationPresent(AsciidocAnnotation.class)) {
            try {
                //Is it the first documented field of the class?
                if (!hasDocumented)  {
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
                        writeFieldInfo(field, anno, adocFile);
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
    protected void completeField() throws IOException {
        writeFieldFooter(adocFile);
    }

    /**
     * Write field footer to stream.
     * @param stream file where to write
     * @throws IOException If something goes wrong wile writing to file
     */
    private void writeFieldFooter(FileOutputStream stream) throws IOException {
        byte[] content = Constant.FIELD_FOOTER.getBytes(StandardCharsets.UTF_8);
        stream.write(content);
    }
    
    /**
     * Writes Field informations contained in anno in restdoc file.
     * @param field Field
     * @param anno An AsciidocAnnotation
     * @param stream file where to write
     * @throws IOException If something goes wrong wile writing to file
     */
    private void writeFieldInfo(Field field, Annotation anno, FileOutputStream stream) throws IOException {
        byte[] content = String.format(FIELD_FORMAT, field.getName(), field.getType().getSimpleName(),
                ((AsciidocAnnotation) anno).description(), ((AsciidocAnnotation) anno).constraints())
                .getBytes(StandardCharsets.UTF_8);
        stream.write(content);
        stream.flush();
    }

    /**
     * Writes Field header in restdoc file.
     * @param stream file where to write
     * @throws IOException If something goes wrong wile writing to file
     */
    private void writeFieldHeader(FileOutputStream stream) throws IOException {
        byte[] content = FIELD_HEADER_FORMAT.getBytes(StandardCharsets.UTF_8);
        stream.write(content);
        stream.flush();
    }

    /**
     * Writes Class header in restdoc file.
     * @param c class targeted
     * @param stream file where to write
     * @throws IOException If something goes wrong wile writing to file
     */
    private void writeClassHeader(Class c, FileOutputStream stream) throws IOException {
        byte[] content;
        MustBeDocumented anno = (MustBeDocumented) c.getAnnotation(MustBeDocumented.class);
        // Write class header
        if ("".equals(anno.description())) {

            content = String.format(CLASS_HEADER_FORMAT, c.getSimpleName(), c.getSimpleName())
                    .getBytes(StandardCharsets.UTF_8);
        } else {
            content = String.format(CLASS_HEADER_WITH_DESCRIPTION_FORMAT, c.getSimpleName(), c.getSimpleName(),
                    anno.description()).getBytes(StandardCharsets.UTF_8);
        }
        stream.write(content);
        stream.flush();
    }
}
