package com.orange.otml.restdoc;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Generate restdoc files and java fieldDescriptor classes.
 */
@Mojo(name = "restdoc-helper")
public class AdocMojo extends AbstractMojo {

    /**
     * Where we want to start searching sources.
     */
    @Parameter
    private String basePackageName;

    /**
     * Where to find sources (including subdirs).
     */
    @Parameter(defaultValue = "target/classes/")
    private String sourceDir;

    /**
     * Where to find sources (including subdirs).
     */
    @Parameter(defaultValue = "target")
    private String targetDir;

    /**
     * Launches the parsers to generate restdoc and java files.
     * @throws MojoExecutionException if something goes wrong
     */
    public void execute() throws MojoExecutionException {
        
        getLog().info("restdoc-helper starts with basePackageName=" + basePackageName + ", sourceDir=" + sourceDir 
                      + ", targetDir=" + targetDir + "\n");
        Swagger2JavaParser parser = new Swagger2JavaParser(basePackageName, targetDir, sourceDir);
        parser.parse();
        
        AsciiDocAnnotationParser asciiDocAnnotationParser = new AsciiDocAnnotationParser(basePackageName, targetDir, sourceDir);
        asciiDocAnnotationParser.parse();
        getLog().info("restdoc-helper ends.");
    }

}


